# Ideas for improving the classification

Goal: a table with fine-grained information to find patterns
(which vehicle or signal box types have more accidents, what goes along with high damage or loss of life),
but with uniform values so that a spreadsheet can group and aggregate them.

## Decision: several calls to a small model, or one call to a larger model?

The input is small: `summaries.md` has about 250k tokens for all 241 reports,
so with Gemma 4 31B a full run costs about 2 cents, and even a model 30–50 times as expensive costs a few dollars.

- **Several calls to a small model:** one call per topic (context and damages, causes, vehicles and signal boxes),
  each with only the summary sections for that topic. This keeps each task simple for a small model.
  But the summaries don't always put information where the extraction prompt asks for it:
  a vehicle class may appear only in "Hergang", a casualty count only in "Ursachen".
  A call that only gets the expected section misses this.

- **One call to a larger model with all relevant sections:** this is better at finding information in an unexpected section.
  It is also better at the judgement this step needs:
  - telling causal from contributing factors
  - assigning responsibility
  - fitting varied wording to a fixed list of values
  - domain knowledge (BR 612 is a diesel multiple unit, SpDrS 60 is a relay signal box)
  - reading amounts like "ca. 50.000.000.- DM"

DECISION: use a bigger model and a single call.

## Uniform but detailed values

- Use a closed list of values for everything that is to be aggregated, always including `unbekannt`.
  Where detail matters, add a short free-text field next to it:
  e.g. a fixed `Stellwerksbauform` (mechanisch / elektromechanisch / Relais / elektronisch / ferngesteuert / keines)
  plus a free-text `Stellwerkstyp` ("SpDrS 60").
  The fixed field makes grouping work and the free text keeps the detail.
- Two-level lists where useful (coarse class plus fine class), so a pivot table can use either level.
- Normalise in code what code can normalise. The summaries spell the same signal box "Sp Dr S59", "SpDrS 60",
  "Sp Dr S 60", "Siemens Sp Dr S600" and "Spurplan-Drucktastenstellwerk, Bauform Siemens 60".
  Vehicle classes appear as "BR 612", "Baureihe 612" or "612 xxx-x".
- Instead of plain text, the array fields could hold objects:
  - vehicles: Zugart, Fahrzeugart, Baureihe
  - signal boxes: Bauform, Typ
  - contributing factors: role (ursächlich / beitragend / systemisch), actor, category, a short quote as evidence

  The cause categories could be:
  - Fehlhandlung → Signal missachtet, Geschwindigkeit, Fahrstraße, Befehl
  - Fahrzeug → Bremse, Radsatz, Fahrwerk, Ladung
  - Infrastruktur → Gleislage, Schienenbruch, Weiche, Bauwerk
  - LST → Stellwerk, Signal, BÜ-Technik, Zugbeeinflussung
  - Umwelt → Sturm/Baum, Hochwasser, Hitze, Hangrutsch, Tiere
  - Organisation → Regelwerk, La/Betra, Ausbildung, Instandhaltung
- Instead of yes/no, grade the cause fields (nein / beitragend / ursächlich):
  same number of columns, more information.

## More context fields

- Type of level-crossing protection (Vollschranke / Halbschranke / Lichtzeichen / nur Andreaskreuz / Posten)
- Road vehicle with more values (Pkw / Lkw / Bus / landwirtschaftlich / Zweirad / Fußgänger / sonstige)
- Substitute signal or written order (Befehl), dangerous goods
- Speed at the time of the accident in km/h
- Train protection (keine / PZB / LZB / ETCS)
- Zugleitbetrieb, single-track line
- Infrastructure operator (DB or non-federal railway)

## Numbers

- Sachschaden as a number in EUR, with DM converted in code (1.95583), and a flag for estimated amounts.
- Separate the seriously and slightly injured, where the report does.

## Less silent guessing

- Booleans as `ja` / `nein` / `unbekannt`. Currently a missing or invalid answer becomes `nein`,
  and any unrecognised road vehicle becomes `OTHER`.
  An invalid value should be reported, not turned into a default.
- Check consistency in code: a road vehicle only for level-crossing accidents,
  a description only when its flag is set.
- Use `strict` / `additionalProperties: false` in the tool schema, if the endpoint supports it.

## Prompt

- Add the abbreviations block from `extraction-prompt.txt`.
- Add short examples for the difficult boundaries: speeding at a switch is a Tf error, not an infrastructure defect;
  a collision with a construction vehicle is related to construction work.

## Data from outside the summaries

`EBU-lists/EBU 01_Open_Data_abgeschlossene_Untersuchungen.csv` has the official date, location, event type,
line number and kilometre. Joining it would give these without relying on the summary's title.

## Spreadsheet convenience

Booleans as `1` / `0` instead of `x`, so that `SUM` and `AVERAGE` work directly in pivot tables.

## Checking the results

- Label 10–15 reports by hand and measure the accuracy per field.
  The well-known accidents in the ReadMe's notes on the data are a start (e.g. Brühl: 9 dead, 149 injured, speeding by the Tf).
- Once the results are good enough on the test reports, classify all reports, not only `testReports`.
