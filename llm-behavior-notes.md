# LLM behavior notes

Observed quirks of the models used in the pipeline, with the evidence behind them.

## Nova 2 Lite drops nullable numbers declared as a type list

Observed 2026-11-23 with `amazon.nova-2-lite-v1:0` through Converse (tool use without `strict`, reasoning effort medium).

When a tool schema declares optional numbers as `"type": ["integer", "null"]`, Nova returns `null` for some of these
fields in every call, even though the input states the number and Nova's own `reasoning` property repeats it
("Es gab 6 Tote und 24 Verletzte"). Which fields are hit depends on the schema, not on the input:

- With all counts, speed and damage amount declared that way, `deaths` and `slightlyInjured` were null in all 241
  reports, while `injured`, `seriouslyInjured`, `speedKmh` and `propertyDamageAmount` were filled.
- Rewording the field descriptions and the prompt ("Zahl der getöteten Personen", "tödlich verletzt") or renaming the
  properties (`killedPersons`, `lightlyInjuredPersons`) changed nothing.
- Declaring only the person counts as plain `integer` filled them, but then `propertyDamageAmount`, the remaining
  nullable number, was null in all test reports.
- `"anyOf": [{"type": "integer"}, {"type": "null"}]` filled every field correctly in two runs over the same eight
  reports (002, 007, 008, 032, 042, 114, 191, 231), so the classification schema uses that form.

Gemma 4 31B (bedrock-mantle, OpenAI-style function tools) filled all fields with either form.
