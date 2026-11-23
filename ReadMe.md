
## Project overview

Multi-stage Kotlin pipeline for analysing German railway accident investigation reports (BEU open data).

Each of the steps is useful in itself: reading the (step 2) summaries of the accident reports is interesting.

And the data from step 3 is tabular, so it can be aggregated, grouped, etc.

Pipeline in order:
1. PDF → Markdown – done via datalab.io – I only had a subscription there for one month, so this step won't be repeated.
2. Summarization (`ExtractSummaries.kt`) — reads `documents/*.md`, writes `results/<model>/summaries.md`
3. Classification (`Classifier.kt`) — reads `results/<model>/summaries.md` of the summarization model, writes `results/classification/classifications.csv`

Both LLM steps only process the reports listed in `testReports` (`model.kt`),
so prompt changes can be evaluated on a varied sample before paying for a full run.

Each step can be run with Gradle, using the name of the file's JVM class (file name plus `Kt`),
e.g. `./gradlew run "-DmainClass=ebuparser.ExtractSummariesKt"`.
The quotes are needed in PowerShell.

## Preprocessing: PDF to Markdown

This is in a separate project, because I used datalab.io which has a Python API client.

`documents/originals/` has the original PDFs just for manual verification.
The programs here directly start from `documents/*.md`.

## Step 2: getting summaries from the reports

This has been implemented to use LLMs served on a local Lemonade Server (which will be preferred if one is running), 
otherwise the configured model on AWS Bedrock or the Anthropic API.
Lemonade did fine for the smaller documents, but I never had the patience to let my computer run for many hours to 
process all documents. And for the Anthropic API I never bought tokens, so the regular work is done on the AWS code 
path. Log in with `aws sso login --profile llm-user --no-browser`. If you use another profile, replace the value in 
`AwsConfig.kt`.

Prompt: [extraction-prompt.txt](src/main/resources/extraction-prompt.txt)
Code: [ExtractSummaries.kt](src/main/kotlin/ebuparser/ExtractSummaries.kt)
Resulting file: `results/<model>/summaries.md` and `results/<model>/stats.csv`.
Each model has its own directory, so that a run with another model can be compared with the earlier ones.
`results/bedrock/` holds the files of runs from before that.

The console output is also appended to `results/<model>/extraction-log.txt`.
After each report it contains an automatic assessment of the summary,
to help with reviewing a run: the sentences that aren't verbatim from the report,
the sections with "keine Angabe im Bericht", and a warning when the model got stuck repeating table padding.

To compare runs, `ScoreSummariesKt` writes the same assessment for existing summary files
(`--args="<summaries.md> ..."`, each to `<name>-assessment.txt`),
and `CompareSummariesKt` compares two or more summary files pairwise, including the sentences only one run has,
and writes `comparison.md` next to the first file.

Reports already in `summaries.md` are skipped, so an interrupted run can just be restarted.

Setting up all the permissions for Bedrock is a bit of work in the AWS console.
If you don't have AWS experience, you might want to use another provider.
Models on the bedrock-mantle endpoint (Gemma 4) additionally need `bedrock-mantle:CreateInference` and
`bedrock-mantle:CallWithBearerToken`, because the SSO credentials are turned into short-term Bedrock API keys.
`runTestAws` doesn't check these.
Use `./gradlew runTestAws` to verify your AWS credentials are working before a Bedrock run.

At the start, the programs fetch a Bedrock model's current token prices from the AWS price list
([BedrockPrices.kt](src/main/kotlin/ebuparser/BedrockPrices.kt)) and print them.
Anthropic has no such price list, so the prices of its models are copied into
[LlmModels.kt](src/main/kotlin/ebuparser/LlmModels.kt) and need updating when Anthropic changes them.
The costs in `stats.csv` are calculated from these prices.

The Anthropic API needs an API key from https://platform.claude.com in the environment variable `ANTHROPIC_API_KEY`.

## Step 3: classifying accidents

Prompt: [classification-prompt.txt](src/main/resources/classification-prompt.txt)
Code: [Classifier.kt](src/main/kotlin/ebuparser/Classifier.kt), [ClassificationCsv.kt](src/main/kotlin/ebuparser/ClassificationCsv.kt)
Input: `results/<model>/summaries.md` of the summarization model
Output: `results/classification/classifications.jsonl` and `results/classification/classifications.csv`

`ClassifierKt` appends the token prices and each run's total input, output and overall cost to
`results/classification/cost-log.txt`, and one row per call to `results/classification/stats.csv`,
with the same columns as in step 2.
The call durations there show when the flex tier is busy.
After several failures in a row, the run pauses for a few minutes, and longer each time, rather than failing the rest.

Uses the Anthropic API or the Bedrock Converse API, whichever the configured model is served by.
One call per report to a capable model, with all sections that describe the accident:
the summaries don't always put information in the section meant for it,
and the step needs judgement (causal or contributing, who was responsible, fitting wording to a fixed list).

The classification categories are the annotated fields of `Classification` in
[Classification.kt](src/main/kotlin/ebuparser/Classification.kt);
they become the strict tool schema for the LLM and the CSV columns.
Everything that is to be aggregated has a closed list of values, with `unbekannt` wherever the summary may not say,
with a free-text field next to it where detail matters (signal box type, vehicle series).

`ClassifierKt` appends the model's answer for each report to `classifications.jsonl`
and skips reports that are already in it, so an interrupted run can just be restarted.
To reclassify, move the file away.
`ClassificationCsvKt` then turns the answers into CSV files, without calling the LLM,
so changes to the parsing or the columns don't need a new paid run:
- `classifications.csv`: one row per report.
  Yes/no fields are `1`/`0`, so that `SUM` and `AVERAGE` work in pivot tables; unknown is empty.
  The cause columns grade the factors (nein / beitragend / ursächlich) by actor and category.
  Property damage is converted to EUR.
  `Prüfhinweise` lists invalid answers and inconsistencies, e.g. a road vehicle for an accident away from a level crossing;
  an invalid value is left empty rather than replaced by a default.
- `factors.csv`, `signal-boxes.csv`, `rail-vehicles.csv`: one row per factor, signal box or vehicle,
  with a few columns of the report, for pivot tables by vehicle or signal box type.

The code normalises the spellings of signal box types and vehicle series (e.g. "Sp Dr S 60" → "SpDrS 60",
"612 123-4" → "BR 612").
Event type, location, line number and kilometre come from the EBU list in `EBU-lists/`,
found by the date in the summary's title; without a unique match, event type and location come from the title.

To measure the accuracy, label some reports by hand in `classification-labels.csv`:
a column `Bericht` and any columns of `classifications.csv` with the expected values; empty cells are not compared.
`EvaluateClassificationsKt` prints the accuracy per column and the mismatches.
Once the results are good enough on the test reports, classify all reports, not only `testReports`.

## Damages and causes: extracting sections from the summaries

Not part of the pipeline; the classification step reads `summaries.md` directly.

Code: [ExtractDamagesAndCauses.kt](src/main/kotlin/ebuparser/ExtractDamagesAndCauses.kt)

Reads `results/<model>/summaries.md` of the summarization model, extracts specific sections by heading keyword matching (German):
 - Damages sections: headings containing "schaden", "tote", and "verletzte"
 - Causes sections: headings containing "ursachen" and "unfall"

Outputs: `results/damages.md` and `results/causes.md`

Also extracts dates (dd.mm.yyyy) found within each section.

First result of this – but insufficient quality: 
https://docs.google.com/spreadsheets/d/17yDhgDWlU_UmKKtV8QbFC6YdbPXS_EWGbCWQX6XlItQ/edit?usp=sharing


## Language

All prompts and domain text are in German. Key abbreviations:
- Fdl = Fahrdienstleiter (signaller/dispatcher)
- Tf = Triebfahrzeugführer (train driver)
- BÜ = Bahnübergang (level crossing)
- Bf = Bahnhof (station)

The models might not know these abbreviations, so the extraction prompt has a list of them,
which the classifier inserts into its own prompt.


## Notes on the data

Scheinbar wurde im Jahr 2005 bzw. 2006 mit den regelmäßigen Untersuchungen und Veröffentlichungen begonnen.
Lediglich die Berichte 1, 2, sowie 4 bis 8 stammen aus den Jahren 2000 bis 2004 und sind scheinbar besonders schwere Unfälle.
1. Bad Münder, 2002 – keine Toten, aber Feuer und Gefahrstoffe betroffen.
2. Brühl, 2000 – 9 Tote, 150 Verletzte
3. Fichtenberg, 2005** – 1 Toter Fahrgast im Zug, Ursache LKW und der Fahrer rannte vorher noch weg.
4. Hannover-Langenhagen, 2000 – 16 Verletzte
5. Holzdorf, 2003 – 1 Toter, Kollision durch Fehlhandlungen Fdl und Tf.
6. Osnabrück, 2004 – nur Sachschaden, aber ebenfalls Gefahrgut beteiligt.
7. Schrozberg, 2003 – 6 Tote durch Kollision auf eingleisiger Strecke, Fehlhandlungen Fdl.
8. Vilseck, 2001 – 3 Tote, davon 2 Fahrgäste im Zug, wegen LKW im BÜ. Außerdem Brand des Tfz, einer BR 612.
9. Berlin Südkreuz, 2006** – 37 Verletzte (Claude hat wohl doppelt gezählt.)
10. Gremberg, 2006** – Güterzüge leicht angeeckt ;)

** erste "regulär" durchgeführte Untersuchungen.

Beachte, dass die Nummerierung nach dem Veröffentlichungsdatum erfolgt, da manche Untersuchungen auch wegen Geringfügigkeit eingestellt werden 
und das viele Lücken in der Nummerierung hinterließe.
Möglicherweise aber auch nur deshalb veröffentlicht, weil sie länger dauerten und bei Fertigstellung schon die Veröffentlichung Regel war.
Der erste Bericht zur Entgleisung in Brühl im Jahr 2000 gehört zu den größten Unfällen seit 1990. Ebenfalls Schrozberg. 
Damit liegen zwei der sechs schwersten Unfälle in der Anfangszeit der Untersuchungen.
Der bekannteste und schwerste Unfall dieser Zeit (und sogar der schwerste seit 1939) war Eschede 1998 und ist nicht im Datensatz enthalten.

Siehe auch: https://www.wikiwand.com/de/Bundesstelle_f%C3%BCr_Eisenbahnunfalluntersuchung#Geschichte

Übrigens gab es die meisten Untersuchungen im Jahr 2015: https://docs.google.com/spreadsheets/d/1AKGIQ_Rnb0rPJ3cBZlvwLmmHchG7lMsiYXamlT_GnHU/edit?usp=sharing
Es hängt wohl auch mit den Personalkapazitäten der Behörde zusammen.


## Utilities

**DeduplicateSummaries.kt**: Removes duplicate file entries from a `summaries.md` file
in the working directory and writes `distinct-summaries.md`.

**StatsCompare.kt** (`src/main/kotlin/llmstats/`): Compares token counts per report between two runs
(`--args="<old stats.csv> <new stats.csv>"`), to see how a prompt change affects input and output size.

**SleepPreventer.kt** (`src/main/kotlin/windows/`): JNA wrapper around Windows `SetThreadExecutionState`
to prevent the OS from sleeping during long LLM processing runs.

## ebustats: Statistical Analysis of EBU Open Data

Code: `src/main/kotlin/ebustats/`
Input: `EBU-lists/` CSVs (open data from BEU — Bundesstelle für Eisenbahnunfalluntersuchung)

 - **EventTypeStats.kt**: Groups events by quarter and normalises event-type aliases
   (e.g., "Zugentgleisung" → "Entgleisung"). 
   Output: `grouped-events-by-quarter.csv`

 - **RecommendationStats.kt**: Groups safety recommendations by key columns with counts. 
   Output: `SicherheitsempfehlungenGrouped.csv`
