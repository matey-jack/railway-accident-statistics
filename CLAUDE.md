# Agent Instructions

## Documentation style

Do not document things that are already clear from reading the code — model names, cost constants,
file paths, enum values, function signatures. These go stale and add noise.
Document *why* something is done, not *what* the code does.

When you make changes, any new or changed comment should only refer to the current state. 
Differences to the previous state belong only in the commit message (if at all).

## Project documentation

[ReadMe.md](ReadMe.md) describes the pipeline, how to run each step, and the German domain abbreviations.

## Working in this project

Don't add Gradle run tasks for the main classes; use `./gradlew run "-DmainClass=<package>.<File>Kt"` as described in the ReadMe.

`ExtractSummaries` and `Classifier` call paid LLM APIs (Bedrock) and append to or overwrite files under `results/`.
Only run them when asked. `./gradlew runTestAws` is a free check that the AWS SSO login is still valid.

Tests: `./gradlew test`.
