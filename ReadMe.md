
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

## Preprocessing: PDF to Text

For downloading the PDFs see `documents/originals/download-all.ps1`
It's done in Powershell by accident; your local AI agent can surely rewrite it for any other environment.

For pdf to text conversion, I tried poppler-utils first (because it easily installs using `apt`), but it's not unpacking ligatures and hyphenation.
Therefore, I switched to pdfminer.six:

    sudo apt install pipx
    pipx install pdfminer.six
    for p in originals/*.pdf
    do 
        pdf2txt.py "$p" --outfile "$(basename "$p" .pdf)".txt
    done

Note that:
 - I need `pipx`, because the local Python installation is locked.
   Luckily, it's just one line more, no big deal.
 - all the quotes are needed because some filenames contain spaces.
   (At least Bash manages to handle the quotes-in-command-in-quotes correctly.)

## Stage One Processing: getting summaries from text files.

Prompt: [extraction-prompt.txt](src/main/resources/extraction-prompt.txt)
Code: [ExtractSummaries.kt](src/main/kotlin/ebuparser/ExtractSummaries.kt))
Resulting file: results/.../summaries.md 

Works with local Lemonade Server or AWS Bedrock and should work with any other LLM provider.
See Main.kt for the models used.

Setting up all the permissions for Bedrock is a bit of work in the AWS console. 
If you don't have AWS experience, you might want to use another provider. 

To extract damages.csv I gave the complete damages.md to the consumer Claude app (using Opus 4.6 and the basic monthly plan). 
I asked it to chop the data up and give it to subagents.

Result with ability to sum and filter: https://docs.google.com/spreadsheets/d/17yDhgDWlU_UmKKtV8QbFC6YdbPXS_EWGbCWQX6XlItQ/edit?usp=sharing

## Stage Two Processing: summarizing the summaries.

Code: [ExtractDamagesAndCauses.kt](src/main/kotlin/ebuparser/ExtractDamagesAndCauses.kt)

First pass for damages: I fed the resulting file to Claude Opus 4.6 in an interactive session. 
Telling it to use subagents, to actually read and understand the text.

