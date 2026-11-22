
## Project overview

Multi-stage Kotlin pipeline for analysing German railway accident investigation reports (BEU open data).

Each of the steps is useful in itself: reading the (step 2) summaries of the accident reports is interesting.

And the data from step 3 is tabular, so it can be aggregated, grouped, etc.

Pipeline in order:
1. PDF → Markdown – done via datalab.io – I only had a subscription there for one month, so this step won't be repeated.
2. Summarization (`ExtractSummaries.kt`) — reads `documents/*.md`, writes `results/<provider>/summaries.md`
3. Classification (`Classifier.kt`) — reads `results/bedrock/summaries.md`, writes `results/bedrock/classifications.csv`

Both LLM steps only process the reports listed in `testReports` (`model.kt`),
so prompt changes can be evaluated on a varied sample before paying for a full run.

Each step can be run with Gradle, using the name of the file's JVM class (file name plus `Kt`),
e.g. `./gradlew run "-DmainClass=ebuparser.ExtractSummariesKt"`.
The quotes are needed in PowerShell.

## Preprocessing: PDF to Markdown

This is in a separate project, because I used datalab.io which has a Python API client.

`documents/originals/` and `documents/cleaned/` hold PDFs and a plain-text extraction (pdfminer.six) of them.
The pipeline doesn't read them.

## Step 2: getting summaries from the reports

Prompt: [extraction-prompt.txt](src/main/resources/extraction-prompt.txt)
Code: [ExtractSummaries.kt](src/main/kotlin/ebuparser/ExtractSummaries.kt)
Resulting file: `results/<provider>/summaries.md` and `results/<provider>/stats.csv`

The console output is also appended to `results/<provider>/extraction-log.txt`.
After each report it contains an automatic assessment of the summary,
to help with reviewing a run: the sentences that aren't verbatim from the report,
the sections with "keine Angabe im Bericht", and a warning when the model got stuck repeating table padding.

To compare runs, `ScoreSummariesKt` writes the same assessment for existing summary files
(`--args="<summaries.md> ..."`, each to `<name>-assessment.txt`),
and `CompareSummariesKt` compares two or more summary files pairwise, including the sentences only one run has,
and writes `comparison.md` next to the first file.

Uses a local Lemonade Server if one is running, otherwise AWS Bedrock. It should work with any other LLM provider.
Reports already in `summaries.md` are skipped, so an interrupted run can just be restarted.

Setting up all the permissions for Bedrock is a bit of work in the AWS console.
If you don't have AWS experience, you might want to use another provider.
Models on the bedrock-mantle endpoint (Gemma 4) additionally need `bedrock-mantle:CreateInference` and
`bedrock-mantle:CallWithBearerToken`, because the SSO credentials are turned into short-term Bedrock API keys.
`runTestAws` doesn't check these.
Use `./gradlew runTestAws` to verify your AWS credentials are working before a Bedrock run.

## Step 3: classifying accidents

Prompt: [classification-prompt.txt](src/main/resources/classification-prompt.txt)
Code: [Classifier.kt](src/main/kotlin/ebuparser/Classifier.kt), [ClassificationCsv.kt](src/main/kotlin/ebuparser/ClassificationCsv.kt)
Input: `results/bedrock/summaries.md`
Output: `results/classification/classifications.jsonl` and `results/classification/classifications.csv`

Uses the Converse API of Bedrock, so only for models that Converse serves.
The classification categories are the annotated fields of `Classification` in `model.kt`;
they become both the tool schema for the LLM and the CSV columns.

`ClassifierKt` appends the model's answer for each report to `classifications.jsonl`
and skips reports that are already in it, so an interrupted run can just be restarted.
To reclassify, move the file away.
`ClassificationCsvKt` then turns the answers into the CSV, without calling the LLM,
so changes to the parsing or the columns don't need a new paid run.
Event type, location and date come from the summary's title; list fields are joined with "; ".

Ideas for better classification results: [classification-improvements.md](classification-improvements.md).

## Damages and causes: extracting sections from the summaries

Not part of the pipeline; the classification step reads `summaries.md` directly.

Code: [ExtractDamagesAndCauses.kt](src/main/kotlin/ebuparser/ExtractDamagesAndCauses.kt)

Reads `results/bedrock/summaries-Nova-2-Lite.md`, extracts specific sections by heading keyword matching (German):
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

Our cheap workhorse model on Bedrock might not know those abbreviations, maybe they need to go into its prompts.


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

**StatsCompare.kt** (`src/main/kotlin/llmstats/`): Compares token counts per report between two Bedrock runs
(`old-stats.csv` vs. `stats.csv`), to see how a prompt change affects input and output size.

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
