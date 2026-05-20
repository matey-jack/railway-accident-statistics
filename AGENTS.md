# Agent Instructions

## Documentation style

Do not document things that are already clear from reading the code — model names, cost constants,
file paths, enum values, function signatures. These go stale and add noise.
Document *why* something is done, not *what* the code does.

## Project overview

Multi-stage Kotlin pipeline for analysing German railway accident investigation reports (BEU open data).

Pipeline in order:
1. PDF to Markdown is done by datalab.io and the client code is Python and in another project
3. Summarisation (`ExtractSummaries.kt`) — reads `documents/cleaned/`, writes `results/<provider>/summaries.md`
4. Classification (`Classifier.kt`) — reads `summaries.md`, writes `results/bedrock/classifications.csv`

`ExtractDamagesAndCauses.kt` (Stage Two) is no longer actively used; classification now reads summaries directly.

## Running things

Don't add extra Gradle run tasks. Use the existing one like this:
`./gradlew run "-DmainClass=ebuparser.ExtractSummaries"` — put the class name with package as shown.

## Language

All prompts and domain text are in German. Key abbreviations:
- Fdl = Fahrdienstleiter (signaller/dispatcher)
- Tf = Triebfahrzeugführer (train driver)
- BÜ = Bahnübergang (level crossing)
