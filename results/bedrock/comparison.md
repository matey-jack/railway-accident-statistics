# Comparison of summaries

- Nova-2-Lite: `results\bedrock\summaries-Nova-2-Lite.md`
- Gemma-001: `results\bedrock\summaries-Gemma-001.md`
- summaries: `results\bedrock\summaries.md`

## Overview

| run | reports | sentences | verbatim | runaways | with missing sections | sections "keine Angabe im Bericht" |
|---|---|---|---|---|---|---|
| Nova-2-Lite | 36 | 797 | 84% | 0 | 13 | 0 |
| Gemma-001 | 35 | 686 | 95% | 6 | 3 | 17 |
| summaries | 36 | 766 | 93% | 0 | 0 | 19 |

## Per report

Verbatim share of the sentences; R = runaway, −n = n sections missing.

| report | Nova-2-Lite | Gemma-001 | summaries |
|---|---|---|---|
| 001.md | 93% of 32 | 91% of 24 | 86% of 29 |
| 002.md | 100% of 10 −1 | 83% of 18 | 83% of 18 |
| 007.md | 91% of 23 | 88% of 25 | 92% of 28 |
| 008.md | 92% of 14 | 100% of 17 | 94% of 17 |
| 009.md | 60% of 23 | 94% of 36 | 94% of 37 |
| 012.md | 86% of 15 | 100% of 17 | 100% of 20 |
| 015.md | 95% of 21 −1 | 92% of 28 | 89% of 28 |
| 023.md | 95% of 24 | 93% of 16 R | 100% of 15 |
| 032.md | 82% of 17 −3 | 90% of 11 | 90% of 11 |
| 034.md | 85% of 14 −1 | 100% of 17 | 100% of 17 |
| 042.md | 94% of 19 | 93% of 15 | 93% of 16 |
| 047.md | 16% of 18 | 100% of 13 | 100% of 13 |
| 055.md | 88% of 18 | 90% of 22 | 90% of 21 |
| 082.md | 91% of 24 | 100% of 36 | 92% of 40 |
| 089.md | 100% of 12 −1 | 94% of 18 R −1 | 100% of 17 |
| 096.md | 93% of 16 | 100% of 21 | 94% of 19 |
| 101.md | 94% of 17 −1 | 95% of 20 | 95% of 21 |
| 114.md | 76% of 26 −1 | – | 82% of 35 |
| 116.md | 92% of 40 | 100% of 21 R | 100% of 19 |
| 123.md | 100% of 23 | 95% of 22 | 95% of 23 |
| 125.md | 88% of 54 | 89% of 19 R | 85% of 21 |
| 126.md | 93% of 15 −1 | 93% of 29 | 100% of 26 |
| 179.md | 100% of 20 −2 | 100% of 20 | 100% of 17 |
| 180.md | 80% of 20 | 100% of 16 | 93% of 16 |
| 181.md | 5% of 19 −2 | 100% of 19 | 89% of 19 |
| 191.md | 76% of 13 | 92% of 13 | 100% of 12 |
| 196.md | 100% of 16 −3 | 100% of 13 | 100% of 15 |
| 199.md | 13% of 22 | 93% of 29 | 82% of 29 |
| 200.md | 92% of 13 −1 | 100% of 13 | 100% of 13 |
| 201.md | 84% of 13 | 93% of 15 | 93% of 16 |
| 203.md | 76% of 17 −2 | 100% of 19 | 100% of 19 |
| 226.md | 92% of 26 | 85% of 7 R −4 | 95% of 20 |
| 231.md | 98% of 54 | 100% of 8 R −4 | 95% of 22 |
| 234.md | 93% of 32 | 90% of 31 | 87% of 31 |
| 237.md | 97% of 34 | 100% of 18 | 100% of 24 |
| 238.md | 78% of 23 | 100% of 20 | 100% of 22 |

## Nova-2-Lite vs. Gemma-001

35 reports in both. Sentences of one run that the other run has in the same section:

| section | Nova-2-Lite | also in Gemma-001 | Gemma-001 | also in Nova-2-Lite |
|---|---|---|---|---|
| Hergang des Unfalls | 223 | 34% | 177 | 53% |
| Höhe des Schadens, Anzahl Tote und Verletzte | 108 | 54% | 83 | 75% |
| Ursachen des Unfalls | 105 | 52% | 176 | 32% |
| Bauart der beteiligten Stellwerke | 44 | 54% | 38 | 73% |
| Baureihe der beteiligten Schienenfahrzeuge | 64 | 29% | 56 | 42% |
| Sicherheitsempfehlungen zur Verhinderung zukünftiger Unfälle | 72 | 63% | 37 | 48% |
| bereits umgesetzte Maßnahmen | 155 | 47% | 119 | 84% |

### Sentences only in one run

<details><summary>001.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: "KC 62848 war um 20:33 Uhr aus dem benachbarten Bf Hameln ausgefahren (*Anmerkung: plm.
- only Nova-2-Lite: Fahrzeit bis Bad Münder ca. 8 Minuten*).
- only Nova-2-Lite: Der Ein- und Ausfahrt des KC 62848 wurde fahrdienstlich durch Einstellen der Durchfahrt durch Gleis 2 zugestimmt.
- only Nova-2-Lite: Hierfür war seitens des Ww Ms das Esig F und seitens des Fdl Mnf das Asig P2 nach Festlegung der zugehörigen Einfahr- bzw.
- only Nova-2-Lite: Ausfahrstraße bedient, d.h. in Stellung Hp 2 „Langsamfahrt“ gebracht worden.
- only Nova-2-Lite: IRC 51219 war um 20:37 Uhr aus dem benachbarten Bf Springe ausgefahren.
- only Nova-2-Lite: Während der Annäherung an den Bf Bad Münder (*Anmerkung: plm.
- only Nova-2-Lite: Fahrzeit ca. 5 Minuten*) konnte aufgrund des bestehenden Fahrstraßenausschlusses mit dem kreuzenden KC 62848 keine fahrdienstliche Zustimmung am Esig A erfolgen, welches folglich in Stellung Hp 0 „Halt“ verblieb.
- only Gemma-001: Infolge des Zusammenstoßes kam es zum Brand an der Unfallstelle und es explodierte ein mit Gefahrgut (Epichlorhydrin; UN-Nr. 2023) beladener Kesselwagen.

**Ursachen des Unfalls**

- only Gemma-001: Hinsichtlich der Erklärung für das unzureichende Bremsvermögen des IRC 51219 ist unter Berücksichtigung und Gewichtung aller gem.
- only Gemma-001: Ziffer 4.6 gewonnenen Erkenntnisse eine im vorderen Zugteil unterbrochene und damit nicht durchgehende Hauptluftleitung des Zuges anzusehen, wobei die Untersuchungen hinsichtlich der Lokalisierung der „Trennstelle“ deutliche Anhaltspunkte auf die Verbindung zwischen den Wagen Nr. 4 und 5 ergeben haben.
- only Gemma-001: Die Fahrt des IRC 51219 nach dem Ereignis „Empelde“ wurde jedoch ohne Veranlassung der erforderlichen „vollen Bremsprobe“, die den Grund für die ungenügende Bremswirkung offenbart hätte, fortgesetzt.
- only Gemma-001: Der Sachverhalt ist dem fachlichen Verantwortungsbereich des Triebfahrzeugführers des IRC 51219 zuzuordnen, dessen individueller Wahrnehmung das schlechte Bremsverhalten des Zuges nicht entgangen sein konnte und offenkundig auch nicht war, wie aus dem Inhalt des Zugfunkgesprächs gefolgert werden muss (Wortlaut: „Genau.
- only Gemma-001: Voll dran vorbeigerutscht.“; vgl.
- only Gemma-001: Dieser Sachverhalt spricht gegen eine ordnungsgemäß durchgeführte volle Bremsprobe im Bf Seelze Rbf.

</details>

<details><summary>002.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Die Einfahrt in den Bf Brühl Gbf vom Gegengleis aus Richtung Hürth-Kalscheuren mußte deshalb auf Ersatzsignal erfolgen.
- only Gemma-001: Etwa 300 m nach der Vorbeifahrt am Einfahrsignal wurde der Zug allmählich wieder beschleunigt.
- only Gemma-001: Der Zug beschleunigte im Bereich des Bahnhofs Brühls auf einem Weg von 1820 m bis auf eine registrierte Spitzengeschwindigkeit von ca. 122 km/h.
- only Gemma-001: Der Bereich der Weiche 48 im Bft Brühl Pbf wurde mit ca. 121 – 122 km/h durchfahren.
- only Gemma-001: Das Triebfahrzeug und fünf Wagen stürzten eine Böschung hinunter, wobei die Lokomotive etwa 150 m hinter der Entgleisungsstelle in einem Wohnhaus zum Stehen kam.

**Ursachen des Unfalls**

- only Gemma-001: Bei dem Unglück kann begünstigend gewirkt haben, dass eine Unterrichtung des Triebfahrzeugführers über die Betriebsführung im Bahnhof Brühl durch La-Eintrag oder Zugfunk nicht erfolgt ist, zugleich aber die La einen nicht relevanten Eintrag für eine Langsamfahrstelle mit einer Geschwindigkeit von 120 km/h enthalten hat.
- only Gemma-001: Die Entgleisung des D 203 am 06. Februar 2000 wäre vermieden worden, wenn innerhalb des Bahnhofs Brühl die zulässige Geschwindigkeit von 40 km/h eingehalten, oder im Zusammenhang mit der Baumaßnahme eine technische Sicherung zur Geschwindigkeitsüberwachung der Züge vorgesehen worden wäre.

</details>

<details><summary>007.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der Zusammenstoß der Regionalexpresszüge RE 19533 und RE 19534 ereignete sich am 11.06.2003 gegen 12:03 Uhr in einer unübersichtlichen Kurve auf dem eingleisigen, nicht elektrifiziertem Streckenabschnitt Niederstetten - Schrozberg in km 30,360 der Strecke Crailsheim - Bad Mergentheim.
- only Nova-2-Lite: Die Unfallstelle in km 30,360 liegt zwischen dem Einfahrtsignal und dem Einfahrsignal des Bahnhofs Schrozberg.
- only Nova-2-Lite: Die Strecke verläuft dort auf einem ca. 8 m hohen Bahndamm in einer (aus Richtung Schrozberg gesehen) Linkskurve, die beidseitig der Bahn teilweise mit Bäumen und hohem Strauchwerk bewachsen ist.
- only Nova-2-Lite: Hierdurch war in beiden Fahrtrichtungen die Sichtweite stark eingeschränkt.
- only Nova-2-Lite: Die beteiligten Triebfahrzeugführer hatten somit keine Möglichkeit, den drohenden Zusammenstoß zu erkennen und abzuwenden.
- only Gemma-001: Die Kausalkette, die letztlich zum Zusammenstoß der Züge RE 19533 und RE 19534 führte, beginnt mit dem Ausfall des Vorsignalwiederholers Wvp2 im Bahnhof Niederstetten.
- only Gemma-001: Der Fahrdienstleiter Niederstetten erkannte diese Ursache der Auflösestörung nicht, obwohl eine solche Störung mehrmals zuvor im Störungsbuch vermerkt war.
- only Gemma-001: Stattdessen vermutete er eine Störung an den Sicherungseinrichtungen des Bahnübergangs in Kilometer 38,058 und ließ die Zugfahrt RE 19533 über die nicht aufgelöste Fahrstraße auf Ersatzsignal zu, nachdem er vom Fahrdienstleiter Schrozberg für Zug IRC 52245 die Rückmeldung angefordert und erhalten hatte.
- only Gemma-001: Weshalb er um 11:59 Uhr diese Rückmeldung für RE 19533 abgab, für die keinerlei Grundlage vorhanden war, ist nicht nachvollziehbar.
- only Gemma-001: Danach erteilte der Fahrdienstleiter Schrozberg durch Ersatzsignal die Zustimmung zur Ausfahrt von Zug 19534 nach Niederstetten.
- only Gemma-001: Hierdurch wurde der Zusammenstoß unvermeidlich.

**Ursachen des Unfalls**

- only Nova-2-Lite: Damit wurden die Voraussetzungen für das weitere Handeln des Fahrdienstleiters Schrozberg geschaffen.
- only Gemma-001: Der Unfall wurde unvermeidlich, als er das Ersatzsignal betätigte und dadurch RE 19534 die Erlaubnis gab, auf die von RE 19533 belegte Strecke auszufahren.
- only Gemma-001: Kritisch erscheint im Zusammenhang mit dem Unglück, dass – bedingt von der Bauform des Stellwerks - die Störung eines Vorsignalwiederholers eine weitere, weitaus gravierendere Störung erzeugt, indem zur Fehleroffenbarung das selbsttätige Auflösen der zugehörigen Fahrstraße verhindert wird.
- only Gemma-001: So hat der Fahrdienstleiter Niederstetten die Ursache der Störung der Fahrstraßenauflösung nicht erkannt, obwohl solche Störungen in den Tagen vor dem Unglück mehrmals aufgetreten und im Störungsbuch verzeichnet waren.
- only Gemma-001: Die Handlungsweise des Fahrdienstleiters Schrozberg, der eine Rückmeldung für RE 19533 abgegeben hat, ohne das Zugschlusssignal gesehen zu haben, stellt einen groben Verstoß gegen grundlegende Betriebsvorschriften dar.

</details>

<details><summary>008.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der Aufprall erfolgte mit einer Geschwindigkeit von annähernd 150 km/h.
- only Nova-2-Lite: Unterstellt man eine Reaktionszeit von ca. 1 Sekunde, so hat der Triebfahrzeugführer den US-LKW etwa 4 Sekunden und ca. 170 Meter vor dem Zusammenprall als Hindernis auf dem BÜ erkannt und eine Schnellbremsung eingeleitet, die etwa 130 m vor dem Kollisionspunkt wirksam wurde.
- only Gemma-001: Beim Zusammenprall traf die Mittelpufferkupplung des Triebzugs den Kraftstofftank des LKW.
- only Gemma-001: Durch Funkenbildung nach dem Zusammenprall entzündete sich das Kraftstoffgemisch und setzte den teilweise zerstörten Triebzug in Brand.

**Ursachen des Unfalls**

- only Gemma-001: Nach Auswertung der sichergestellten Daten aus dem Rechner der Bahnübergangssicherungsanlage kann ein technisches Versagen als Unfallursache ausgeschlossen werden.
- only Gemma-001: Der Ausbruch des Brandes und dessen außergewöhnlich schnelle Ausbreitung sind auf die Betriebsstoffe des LKW (Kraftstoffvorrat und Hydrauliköl) zurückzuführen, die sich feinst verteilt auf dem Triebzug niedergeschlagen und wie ein Brandbeschleuniger gewirkt haben.

</details>

<details><summary>009.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 20. November 2006 kam es um 10:23 Uhr auf dem S-Bahnhof Berlin-Südkreuz (Vorort) zu einem Zusammenstoß zwischen dem Zug S 25066 und dem Ultraschallschienenprüfzug Dsts(B) 96114. Der Ultraschallschienenprüfzug stand zum Zeitpunkt des Unfalls auf dem Bahnsteiggleis 502 des S-Bahnhofs Berlin-Südkreuz (Vorort) vor dem Halt zeigenden Zwischensignal 276. Der Zug S 25066 befand sich auf der Fahrt von Teltow nach Hennigsdorf über Berlin-Südkreuz (Vorort), Gleis 502. Trotz eingeleiteter Betriebs- und Schnellbremsung überfuhr er unzulässig das Halt zeigenden Zwischensignal 278 und prallte mit ca. 37 km/h auf den stehenden Ultraschallschienenprüfzug auf.
- only Gemma-001: Trotz eingeleiteter Betriebs- und Schnellbremsung überfuhr er unzulässig das Halt zeigende Zwischensignal 278 und prallte mit ca. 37 km/h auf den stehenden Ultraschallschienenprüfzug auf.
- only Gemma-001: Im S-Bahnhof Berlin-Südkreuz (Vorort) fuhr der Zug S 25066 unzulässig am Halt zeigenden Zwischensignal 278 vorbei in das besetzte Bahnhofsgleis 502 und prallte um 10:23 Uhr auf den dort stehenden Ultraschallschienenprüfzug Dsts(B) 96114.

**Ursachen des Unfalls**

- only Nova-2-Lite: Ursache für den Zusammenstoß der beiden Züge war die ungenügende Bremswirkung des Zuges S 25066. Ein stark verringerter Reibwert zwischen Rad und Schiene durch Feuchtigkeit und Schmutz, die fehlende Information für den Triebfahrzeugführer über verminderte Reibwerte in Folge des vorauffahrenden Ultraschallschienenprüfzuges, ein für diesen Schienenzustand nicht optimierter Gleitschutz der Bremse und die ungenügende Wirkung der Sandstreuereinrichtungen des Triebzuges der S-Bahn Berlin GmbH haben maßgeblich zu der unzureichenden Bremswirkung beigetragen.
- only Gemma-001: Ein stark verringerter Reibwert zwischen Rad und Schiene durch Feuchtigkeit und Schmutz, die fehlende Information für den Triebfahrzeugführer über verminderte Reibwerte in Folge des voraufahrenden Ultraschallschienenprüfzuges, ein für diesen Schienenzustand nicht optimierter Gleitschutz der Bremse und die ungenügende Wirkung der Sandstreuereinrichtungen des Triebzuges der S-Bahn Berlin GmbH haben maßgeblich zu der unzureichenden Bremswirkung beigetragen.
- only Gemma-001: Verursacht wurde die ungenügende Bremswirkung durch einen stark verminderten Haftwert Rad-Schiene infolge der kontinuierlichen Befeuchtung durch den Ultraschallschienenprüfzug in Verbindung mit der auf den Schienen vorhandenen Schmutzschicht.
- only Gemma-001: Der Triebfahrzeugführer des Zuges S 25066 hatte keine Kenntnis über den verminderten Haftwert im Streckenabschnitt Berlin-Priesterweg – Berlin-Südkreuz (Vorort).
- only Gemma-001: Aufgrund der geringen Füllung der Sandbehälter war die Funktionsfähigkeit der Sandstreuereinrichtungen der Fahrzeuge des Zuges S 25066 nicht in vollem Umfang gegeben.
- only Gemma-001: Die Konzeption der Bremsanlage der Fahrzeuge der BR 481 erfüllt bei niedrigen Haftwerten weder mit der Betriebsbremse noch mit der Schnellbremse die aus dem UIC Merkblatt 541-05 abgeleiteten Bedingungen.

</details>

<details><summary>012.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Von den entgleisen Wagen waren 4 umgestürzt.
- only Nova-2-Lite: Die Unfallstelle befindet sich auf einer über die Bundesautobahn (BAB 93) München - Kufstein führenden Brücke.
- only Gemma-001: Danach verspürte er etwa bei km 10,9 einen Schlag, leitete eine Betriebsbremsung ein und setzte einen Notruf ab.
- only Gemma-001: Von den entgleisten Wagen waren 4 umgestürzt.
- only Gemma-001: Zum Zeitpunkt des Unfalles herrschte strenger Frost von minus 12 Grad Celsius.

**Ursachen des Unfalls**

- only Nova-2-Lite: Hier konnte ein Einzelbruch nachgewiesen werden der sich unter der Schienenkopfoberfläche in Richtung Steg entwickelte und später in vertikaler Richtung abknickte.
- only Nova-2-Lite: Diese Rissfläche bildete den Dauerbruch.
- only Gemma-001: Die Zugspannungen aufgrund der niedrigen Außentemperaturen begünstigten das Rissverhalten.
- only Gemma-001: Inwieweit hierbei auch das Durchbiege- und Endtangentenwinkelverhalten der Brücke im Übergangsbereich und der hierdurch ggf. erzeugten Erhöhung der Zugspannung mit ursächlich zum Schienenbruch beigetragen haben könnte ist mit vertretbarem Aufwand nicht abklärbar.
- only Gemma-001: Ursächlich für die Entgleisung des Güterzuges ist ein Schienenbruch, der bereits vor dieser Zugfahrt vorhanden war und schon Schäden an der voraufahrenden RB 30116 verursachte.

</details>

<details><summary>015.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 16.04.2009 kam es um ca. 22:17 Uhr auf dem Bahnhof Berlin-Karow im durchgehenden Hauptgleis (Gleis 2) zu einem Zusammenstoß zwischen dem in Richtung Berlin-Blankenburg durchfahrenden Zug RE 38399 und dem ebenfalls in Richtung Berlin-Blankenburg ausfahrenden Zug FTZ 53185. Der FTZ 53185 war nach Fplo 10710 nur an diesem Tag in Richtung Berlin-Blankenburg durchfahrend.
- only Gemma-001: Der Fdl löste die Fahrstraße hinter dem Asig 132 auf, obwohl der Güterzug FTZ 53185 nicht zum Halten gebracht wurde.
- only Gemma-001: Das Einfahrsignal 136 wurde also erneut auf Fahrt gestellt und das Ausfahrsignal 132 stand noch für den FTZ 53185 auf Fahrt.
- only Gemma-001: Kurz darauf kam es zum Zusammenstoß des mit ca. 80 km/h auffahrenden RE 38399 auf den mit ca. 41 km/h ausfahrenden Zuges FZT 53185.

**Ursachen des Unfalls**

- only Gemma-001: Darüber hinaus hat die Änderung der sicherungstechnischen Anlage in Bezug auf den Signalhaltfall des Ausfahrtsignals 132 den Zusammenstoß begünstigt.

</details>

<details><summary>023.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 29.01.2011 kam es um ca. 22:28 Uhr in der Überleitstelle Hordorf im Bahn-Kilometer 42,702 zur frontalen Zugkollision zwischen dem in Richtung Oschersleben fahrenden Güterzug DGS 69192 und dem in Richtung Halberstadt fahrenden Personenzug DPN 80876.
- only Nova-2-Lite: Der Personenzug DPN 80876, VT 810 des Typs LINT 41, wurde durch den Güterzug DGS 69192 in Richtung Oschersleben geschoben und entgleiste vollständig.

**Ursachen des Unfalls**

- only Nova-2-Lite: Bei Strecken, auf denen technisch wirkende Zugbeeinflussungssysteme gemäß § 15 EBO nicht explizit gefordert werden, obliegt die Sicherheitsverantwortung hinsichtlich unzulässiger Vorbeifahrt an Halt zeigenden Signalen ausschließlich unter der menschlichen Sicherheitsverantwortung des Triebfahrzeugführers.
- only Gemma-001: Der Güterzug fuhr in einen besetzten Gleisabschnitt und kollidierte in Folge mit dem im eingleisigen Streckenabschnitt entgegenkommenden Personenzug.

</details>

<details><summary>032.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Nach dem ein Pkw-Fahrer vor dem BÜ (Bauart: RBÜT-LZH-Fü) als erstes Fahrzeug anhielt (Lichtzeichen blinkte, Schranken noch geöffnet) fuhr ein Transporter auf diesen auf und schob den Pkw in den Gleisbereich.
- only Nova-2-Lite: Die Schranken wurden für die Zugfahrt geschlossen.
- only Nova-2-Lite: Der Zug RE 3736 (Chemnitz Hbf - Leipzig Hbf) prallte nun auf dem gesicherten Bahnübergang auf den unbesetzten Pkw.

</details>

<details><summary>034.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Durch Arbeiten der DB-Telematik im Stellwerksraum (Auswechslung des GSM-R Gerätes) sei er abgelenkt worden und habe vergessen, die Gleissperrung im Fernsprechbuch einzutragen.
- only Nova-2-Lite: Die beiden Personen an der Baustelle versuchten aus den Gleisen zu springen, wobei eine Person vom Zug erfasst wurde.
- only Gemma-001: Die Gruppe führte in planmäßigen Zugpausen Schweißarbeiten an der Weiche W 227 aus.
- only Gemma-001: Dieser Gleisbereich ist durch sehr enge Kurven, die einander anschließen geprägt.
- only Gemma-001: Hierdurch sei es ihm unmöglich gewesen, die Arbeiter im Durchfahrgleis früher zu bemerken.

**Ursachen des Unfalls**

- only Gemma-001: Es handelt sich um ein Fehlverhalten des Fahrdienstleiters durch Verstoß gegen Ril 408.0472 Abschn. 5 und Abschn. 8.

</details>

<details><summary>042.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 13.01.2012 um 17:40 Uhr kollidierte der Zug DPN 81817 mit einer auf dem Gleis befindlichen Rinderherde im km 188,300.
- only Nova-2-Lite: Die Rinderherde ist nach der Untersuchung der Spuren am Bahnübergang im km 189,076 auf das Gleis gelangt.
- only Nova-2-Lite: Der Zug DPN 81817 war auf der Fahrt von Westerland nach Hamburg (Streckennummer 1210) und befand sich im Streckenbereich Stedesand – Langenhorn in der Nähe der Ortschaft Bargum.
- only Nova-2-Lite: In diesem Streckenbereich beträgt die zugelassene Geschwindigkeit 140 km/h.
- only Nova-2-Lite: Der Personenzug der Nord-Ostsee-Bahn GmbH, bestehend aus sechs Wagen plus Triebfahrzeug, fuhr mit dem Steuerwagen voraus.
- only Nova-2-Lite: In der Dunkelheit konnte der Triebfahrzeugführer die Rinderherde nicht rechtzeitig erkennen.
- only Nova-2-Lite: Trotz eingeleiteter Schnellbremsung konnte die Kollision nicht verhindert werden.
- only Gemma-001: Die Rinderherde ist aus einem Stall entlaufen und unmittelbar in der Nähe des Bahnüberganges Sande, im km 189,076 auf die Gleise geraten und hat sich dann entlang der Gleise in Fahrtrichtung Hamburg bewegt.
- only Gemma-001: Auf Grund der Dunkelheit wurden die Tiere durch den Triebfahrzeugführer nicht rechtzeitig erkannt.
- only Gemma-001: Trotz eingeleiteter Schnellbremsung konnte eine Kollision mit der Rinderherde nicht verhindert werden.
- only Gemma-001: Am 13.01.2012 um 17:40 Uhr kollidierte der DPN 81817, trotz eingeleiteter Schnellbremsung, auf der Fahrt von Westerland nach Hamburg im km 188,300 zwischen Stedesand und Langenhorn mit einer Rinderherde.
- only Gemma-001: Der Steuerwagen kippte um und blieb neben dem Gleis liegen, der zweite Wagen entgleiste ebenfalls.

**Ursachen des Unfalls**

- only Nova-2-Lite: Trotz eingeleiteter Schnellbremsung konnte eine Kollision mit der Rinderherde nicht verhindert werden.
- only Gemma-001: Sowohl die technische Untersuchung der beteiligten Fahrzeuge, als auch die Auswertung des Fahrtverlaufes lassen den Schluss zu, dass die Ursache für die Zugkollision nicht auf Mängel aus dem Eisenbahnbetrieb zurück zuführen sind.

</details>

<details><summary>047.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: + Aufgrund eines Blitzeinschlages am Vortag des Ereignisses waren die Gleisfreimeldeanlage sowie die Bahnübergangssicherungsanlage an der Gorkistraße gestört, so dass die S 25068 (Linie S 25) im Bf Berlin-Tegel von Gleis 1 auf Signal Zs 1 und mit Befehl 8 zur Sicherung des BÜ Po 5 in Richtung Bf Hennigsdorf ausfuhr.
- only Nova-2-Lite: + Nach dem Befahren der Weiche 74 mit den ersten beiden Wagen lief die Weiche 74 unter dem fahrenden Zug vollständig in die Linkslage um, wodurch die nachfolgenden Fahrzeuge in Richtung Stumpfgleis der Niederbarnimer Eisenbahn abgeleitet wurden.
- only Nova-2-Lite: + Dabei entgleisen der 3. und 4. Wagen des Zuges mit allen Radsätzen und der 3. Wagen kam in Schräglage.
- only Nova-2-Lite: + Der Triebfahrzeugführer setzte einen Notruf ab.
- only Gemma-001: Dabei entgleisten der 3. und 4. Wagen des Zuges mit allen Radsätzen und der 3. Wagen kam in Schräglage.

**Ursachen des Unfalls**

- only Nova-2-Lite: + Ursächlich für die Entgleisung im Gleis 1 des Bahnhofs Tegel war das Umstellen der Weiche 74 unter dem S-Bahnzug S25068.
- only Nova-2-Lite: + Die Weiche 74 wurde von der Spitze aus befahren und unter dem fahrenden Zug umgestellt.
- only Nova-2-Lite: + Da die Zugfahrt nicht auf Signal erfolgte, sondern auf Ersatzsignal – Zs1 –, wegen gestörter Gleisfreimeldeanlage, war für die Auflösung des Zuges die Mitwirkung des Fahrdienstleiters erforderlich.
- only Nova-2-Lite: + Im Nachweis der Zählwerke des Stellwerks Tegel fehlten sehr viele Eintragungen.
- only Nova-2-Lite: Der letzte Eintrag war um 04.39 Uhr für das Bedienen des Zs 1 am Einfahrsignal T mit Hinweis auf die Störung Nr. 49 vom Vortag vorgenommen worden.
- only Nova-2-Lite: Weitere Hilfsbedienungen nachweispflichtiger Einrichtungen waren nicht vorhanden, so dass kein Abgleich auf die erfolgten nachweispflichtigen Bedienhandlungen in Bezug auf die Zugentgleisung der S-Bahn S 25068 möglich war.

</details>

<details><summary>055.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 26.07.2012 kam es im Bahnhof Hosena auf der Weiche 48, die sich etwa in Höhe des Weichenwärterstellwerkes W3 befindet, zu einer Zugkollision des DGS 49325 mit dem DGS 92505. Dabei fuhr der aus Richtung Schwarzkollm kommende DGS 49325, dem aus Richtung Schwarzbach kommenden und nach Gleis 1West (Hosena Nord) einfahrenden DGS 92505 im letzten Zugdrittel in die Flanke.
- only Nova-2-Lite: Der Fahrdienstleiter erhielt einen Anruf und hörte eine männliche Stimme, die sagte „nicht bremsen, ich kann nicht bremsen, Signal halt“.
- only Nova-2-Lite: Kurz darauf hörte er ein dumpfes lautes langgezogenes Geräusch und eine Staubwolke stieg in die Höhe.
- only Gemma-001: Der Güterzug DGS 49325 fuhr im Bahnhof Hosena dem nach Gleis 7 aus Schwarzbach einfahrenden Güterzug DGS 92505 im hinteren Teil des Wagenzuges in die Flanke.
- only Gemma-001: Durch die Flankenfahrt des DGS 49325 entgleisen mehrere Güterwagen sowie das Triebfahrzeug des DGS 49325.
- only Gemma-001: Einige entgleiste Güterwagen kollidierten mit dem Weichenwärterstellwerk W 3 und brachten das Stellwerk zum Einsturz.

**Ursachen des Unfalls**

- only Gemma-001: Es ist davon auszugehen, dass das vorhandene Zeitfenster von ca. 20 - 25 min zur Durchführung dieser nicht ausreichend war, da zur Bewältigung der Strecke von 3.300 m eine Gehgeschwindigkeit von mehr als 2,75 m/s (9,9 km/h) – 2,22 m/s (7,922 km/h) erforderlich wäre.
- only Gemma-001: Auch ist festzustellen, dass nach ordnungsgemäßer Durchführung der vollen Bremsprobe vor erstmaliger Fertigmeldung es nicht mehr zu dem geschilderten Fehler im hinteren Zugdrittel hätte kommen dürfen, da sich dies bei der vorangegangenen Bremsprobe hätte offenbaren müssen.

</details>

<details><summary>082.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 11.04.2014 gegen 20:29 Uhr entgleiste die Zugfahrt S 39198 auf der spitz befahrenen Weiche 7 des Bahnhofs (Bf) Forchheim.
- only Nova-2-Lite: Die S-Bahn 39198 erhielt die Zustimmung des Fahrdienstleiters (Fdl) Forchheim zur Einfahrt in den Bf durch Ersatzsignal (Signal Zs 1 der ESO) am Einfahrsignal A.
- only Nova-2-Lite: Das erste Drehgestell des Triebwagens der S 39198 (Baureihe 442) befuhr die Weiche 7 in der Stellung Fahrweg nach rechts, das zweite Drehgestell des Triebwagens befuhr die Weiche 7 in der Stellung Fahrweg nach links.
- only Nova-2-Lite: Dieser zweispurige Lauf führte zur Entgleisung des zweiten Drehgestells mit beiden Achsen.
- only Gemma-001: Der Fdl stimmte der Einfahrt der S 39198 in den Bf um ca. 20:27 Uhr, wahrscheinlicher etwa 20:28 Uhr, durch Signal Zs 1 – Ersatzsignal – am Esig A zu.
- only Gemma-001: Zug 39198 befuhr nach Entgleisungslage die Weiche 7 während eines Umstellvorgangs.
- only Gemma-001: Das erste Drehgestell (DG) des Triebwagens der S-Bahn wurde auf der Weiche 7 bei Stellung Fahrweg nach rechts in Richtung Weiche 8/Gleis 4 oder 5 und das zweite Drehgestell nun bei Stellung Fahrweg nach links in Richtung Weiche 1801/Gleis 3 geführt.
- only Gemma-001: Durch das Umlaufen der Weiche 7 unter dem Zug kam es zu einem zweispurigen Lauf mit der Folge einer Entgleisung der Zugfahrt, das zweite Drehgestell entgleiste mit beiden Achsen.
- only Gemma-001: Nach Stillstand des Zuges befand sich das Drehgestell 1 auf der stumpf befahrenen Weiche 8, das Drehgestell 3 auf der Weiche 7, die nun in Stellung Fahrweg nach links in Richtung Gleis 3 stand.

**Ursachen des Unfalls**

- only Gemma-001: Die Ausgangsfehler für die Entgleisung der auf Ersatzsignal in den Bf Forchheim einfahrenden S-Bahn 39198 liegen in der mangelhaften Einstellung, Prüfung und Sicherung des Fahrweges durch den FdI.
- only Gemma-001: Ursache des Unfalls sind Arbeitsfehler des diensthabenden FdI Bf Forchheim.
- only Gemma-001: Zu berücksichtigen sind hierbei jedoch sicherlich folgende Aspekte:
- only Gemma-001: evtl. mangelhafte Ausbildung bzw. mangelnde Praxis (die Grundkenntnisse und Erfahrungen für einen FdI waren sicher gegeben, ob die Einarbeitungszeit für die tech- nisch andere und praktisch neue Stellwerksform Gleisbildstellwerk ausreichend war, kann nicht beurteilt werden);
- only Gemma-001: andere Art der Fahrweineinstellung, -prüfung und insbesondere -sicherung als auf einem mechanischen Stellwerk;
- only Gemma-001: Zeitdruck (obwohl die beiden bisher betroffenen Züge 87092 und 39198 nur eine Verspätung von ca. 6 Minuten erhielten bzw. erhalten hätten, war der Fdl sicher bemüht die Verzögerungen zu minimieren);
- only Gemma-001: Panik (plötzliches, aber zu spätes Erkennen, dass der Zug einen unzulässigen Fahrweg nimmt und untauglicher Versuch, diesen Fehler zu beheben);
- only Gemma-001: Probleme bei der Umsetzung der Vorgaben/Vorschriften (wie die vorstehende Untersuchung belegt, sind die zu beachtenden Regeln in mehreren Ril und dort an verschiedenen Stellen verteilt, aber im Zusammenhang vom Fdl anzuwenden);
- only Gemma-001: Belastung aus der Störung der Stellwerkstechnik;
- only Gemma-001: alle diese Faktoren dürften zu Stress beim Fdl geführt haben (ob die falsche Ortsangabe im schriftlichen Befehl für Zug 87092 bereits als Anzeichen dafür zu werten ist, sei dahin gestellt).

</details>

<details><summary>089.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 09.06.2013 um 5:21 Uhr entgleist DGS 90636 zwischen den Betriebsstellen Kaub und Lorch (Rhein) Üst mit dem am Schluss des Zuges laufenden Autotransportwagen 23-88 4371 080-3 in km 76,950. Im Verlauf der Weiterfahrt des Zuges entgleist in Folge auch der vorletzte Autotransportwagen 23 88 4371 098-5. Nachdem der Triebfahrzeugführer einen Nothaltauftrag (Kreissignal) im Bf Rüdesheim (Rh.) erhalten hat, kommt der Zug in km 65,300 im Gleis 2 des Bf Rüdesheim (Rh.) zum Halten.
- only Gemma-001: In km 76,950 entgleist das am Schluss laufende Fahrzeug 23-88 4371 080-3.
- only Gemma-001: Während der Fahrt durch die Blockabschnitte der freien Strecke, der Überleitstelle sowie der Weichen und Gleisabschnitte des Bf Assmannshausen werden zahlreiche technische Einrichtungen an Weichen, Signalanlagen und oberbautechnischen Einrichtungen beschädigt bzw. zerstört.
- only Gemma-001: Bei der Einfahrt nach Gleis 2 in den Bf Rüdesheim (Rh.) werden die Einfahrweichen stark beschädigt und das Ausfahrtsignal N3 umgerissen.

**Ursachen des Unfalls**

- only Gemma-001: Ursächlich für die Entgleisung sind mehrere kurz hintereinander liegende zyklische Gleislagefehler in der Längshöhe.
- only Gemma-001: Ausgehend durch eine gestörte Entwässerung des Gleiskörpers kommt es abschnittsweise zu Einschränkungen bei der Tragfähigkeit des Gleisunterbaus, woraus sich durch den Eisenbahnverkehr Gleislagefehler entwickeln.
- only Gemma-001: Die Autotransportwagen der Gattung Laaers konnten diese zusätzlich wirkenden Anregungen aus den vorgefundenen Gleislagefehlern bei der gefahrenen Geschwindigkeit von 104 km/h aufgrund ihrer konstruktiven Eigenschaften nicht mehr ausreichend kompensieren und entgleisen.
- only Gemma-001: Eine weitere Ursache ist in der Fehleinschätzung des Gefahrenpotentials der aufgetretenen kombinierten (zyklischen) Einzelfehler durch das Inspektionspersonal und der fehlenden Regelung mit deren Umgang in den Richtlinien der DB AG zu sehen.
- only Gemma-001: Die flach verlegte Tiefenentwässerung zwischen beiden Gleisen sollte vom ALV KIB gemäß Ril 836.8002 Abschn. 3 Abs. 2 regelmäßig untersucht werden.
- only Gemma-001: Aufgrund der vorliegenden TV-Inspektion hätte offensichtlich erkennbar sein müssen, dass der Großteil der Haltungen defekt und der Anschluss an eine Vorflut nicht gegeben war.
- only Gemma-001: Die Fehlinterpretierung der Mängelursache durch den ALV Oberbau bei den häufig durchgeführten Inspektionen lässt darauf schließen, dass Defizite beim Erkennen der Gesamtzusammenhänge vorlagen.

</details>

<details><summary>096.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 01.02.2017 gegen 22:54 Uhr fuhr ICE 1522 des Eisenbahnverkehrsunternehmens (EVU) DB Fernverkehr AG in das durch Zug DPN 32490 des EVU National Express Rail GmbH besetzte Gleis 507 des Bf Gruiten ein.
- only Nova-2-Lite: Der für den Bahnhof Gruiten zuständige Fahrdienstleiter (Fdl) hat vor Einfahrt des ICE nach Gleis 507 eine von der Gleisfreimeldeanlage angezeigte Besetztanzeige mittels Achszählergrundstellungstaste (AzGrT) Bedienung aufgehoben, ohne die vorgeschriebene betriebliche Voraussetzung, den betroffenen Abschnitt durch Hinsehen zu prüfen, erfüllt zu haben.
- only Nova-2-Lite: Anschließend stimmte der Fdl der Einfahrt des ICE 1522 durch Fahrstellen des Einfahrsignals (Esig) B517 zu.
- only Nova-2-Lite: Einen Auftrag zum Fahren auf Sicht hatte der Tf des ICE nicht erhalten.
- only Nova-2-Lite: Nach Durchfahren eines Rechtsbogens erkannte er das Zugschlusssignal des nach wie vor in Gleis 507 stehenden DPN 32490 und leitete bei einer Geschwindigkeit von ca. 78 km/h eine Schnellbremsung ein.
- only Gemma-001: Nachdem der Fdl den an seinem Stw vorbeifahrenden DPN 32486 mit Zugschlussignal beobachtet hatte, stellte er fest, dass die Rotaulichtung für Gleis 507 auf seiner Stelltafel nach wie vor vorhanden war.
- only Gemma-001: Aus diesem Grund deutete er die nach wie vor vorhandene Rotaulichtung als Störung und stellte mittels Bedienung der AzGrt die Grundstellung der Gleisfreimeldeeeinrichtung her.
- only Gemma-001: Anschließend stellte er die Fahrstraße für den wartenden ICE 1522 ein und brachte das Esig B517 in die Fahrtstellung.
- only Gemma-001: Nach Durchfahren eines Rechtsbogens erkannte er das Zugschlussssignal des nach wie vor in Gleis 507 stehenden DPN 32490 und leitete bei einer Geschwindigkeit von ca. 78 km/h eine Schnellbremsung ein.
- only Gemma-001: Der ICE kam etwa 15 m vor dem Zugschluss des DPN 32490 zum Stillstand.

**Ursachen des Unfalls**

- only Gemma-001: Die unzulässige Einfahrt des ICE 1522 in das besetzte Gleis 507 des Bf.
- only Gemma-001: Gruitens wurde durch den für Gruitens zuständigen Fdl in Wuppertal-Vohwinkel verursacht.
- only Gemma-001: Seinen Ausgangspunkt nahm die betriebliche Fehlhandlung in der Tatsache, dass der Fdl über die Fahrplanregelungen zu Betra F325016 17 / F325023 17 nicht informiert war.
- only Gemma-001: Anhand der vom Fdl geführten GSM-R – Gespräche steht für die BEU fest, dass der Fdl keine Kenntnis von der geplanten Zugtrennung des 32486 in Gruitens hatte.
- only Gemma-001: Der Fdl prüfte das Freisein des betroffenen Gleisabschnitts in Gruiten somit mittelbar.
- only Gemma-001: Das Hinsehen war ihm wegen der Entfernung zwischen seinem Stellwerk in Wuppertal-Vohwinkel und dem Bahnhof Gruiten natürlich nicht möglich.
- only Gemma-001: Die Gründe für die falsche Entscheidung der Fdl kennt die BEU nicht.
- only Gemma-001: Möglicherweise kannte der Fdl diese Bestimmung in seinem Betriebsstellenbuch nicht.
- only Gemma-001: Möglicherweise war aber auch der Fdl von seinem Handeln so überzeugt, dass das Beobachten des Zugschlusses und damit das mittelbare Feststellen das richtige Mittel zur Lösung seines Problems sei und er deshalb andere Möglichkeiten gar nicht in Erwägung zog.

</details>

<details><summary>101.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Gegen 15:47 Uhr meldete sich der Triebfahrzeugführer der RE 21073 über Zugfunk beim Fahrdienstleiter Pinneberg und teilte ihm mit, dass vor ihm ein anderer Zug (DPN 83825) stünde.

**Ursachen des Unfalls**

- only Nova-2-Lite: Bei Störzuständen an Blockeinrichtungen ist laut Regelwerk der DB AG (Ril 408) der Block zu sperren und die Einzelräumungsprüfung durchzuführen.
- only Nova-2-Lite: Im vorliegenden Fall geschah weder die Sperrung, noch die Kontaktaufnahme oder diese Dokumentation in vorgeschriebener Art und Weise.
- only Gemma-001: Der Fdl ließ mehrere Fahrten auf Zs1 zu ohne eine vorherige Räumungsprüfung der Streckenabschnitte durchzuführen.
- only Gemma-001: Im Rahmen der Unfalluntersuchung wurde deutlich, dass es zu fehlerhaften fahrdienstlichen Bedienhandlungen kam, insbesondere ab dem Zeitpunkt, zu dem die Stellwerkstechnik in den signaltechnisch sicheren Störbetrieb ging.
- only Gemma-001: Erforderliche betriebliche Ersatzmaßnahmen, beispielsweise die Einzelräumungsprüfung, wurden nicht durchgeführt.
- only Gemma-001: Auf die vorgeschriebenen Handlungen im Fehlerfall laut Ril 408 bzw.
- only Gemma-001: Ril 482 (genaue Bezeichnung der relevanten Kapitel siehe voranstehende tabellarische Darstellung) wurde verzichtet.
- only Gemma-001: Der Fdl orientierte sich hinsichtlich des Standortes der Züge vermutlich unzulässiger Weise an der Zugnummernmeldeanlage, um den Raumabstand zwischen den Zügen zu gewährleisten.
- only Gemma-001: Mit dem Ausfall dieser Anlage (gegen 15:20 Uhr), der in keinem technischen Zusammenhang mit der Störung der Achszählanlagen stand, verlor dieser vermutlich den Überblick über die Standorte der jeweiligen Züge und den Besetztzustand der gestörten Blockabschnitte, vergaß den vor Sbk 34 stehenden Zug DPN 83825 und ließ Zug RE 21073 in den Abschnitt ein.
- only Gemma-001: Es war ein starker Mangel in der Handlungssicherheit hinsichtlich der Durchführung betrieblicher Ersatzmaßnahmen erkennbar, ebenso wie ein fehlerhafter Umgang mit der Dokumentation durchgeführter Hilfsbedienungen.

</details>

<details><summary>116.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 01.05.2017 um ca. 18:46 Uhr entgleiste Zug ICE 945 des EVU DB Fernverkehr AG bei der Einfahrt in Gleis 8 des Bf Dortmund Hbf auf Höhe der DKW 36. Gleis 8 ist ein durchgehendes Hauptgleis und gehört zur Strecke 2158. Der Zug der Baureihe ICE 2 war auf dem Weg von Düsseldorf Hbf nach Berlin Ostbahnhof.
- only Nova-2-Lite: Der nächste planmäßige Halt war Dortmund Hbf.
- only Nova-2-Lite: Der Ereignisort gehört zum Streckennetz des EIU DB Netz AG. Der Betrieb wird nach dem Regelwerk der DB AG, Richtlinie (Ril) 408 „Fahrdienstvorschrift“, durchgeführt.
- only Nova-2-Lite: Die betroffene Strecke 2158 ist als zweigleisige Hauptbahn ausgeführt.
- only Nova-2-Lite: Sowohl die freie Strecke als auch der Bf Dortmund Hbf sind mit Haupt-/Vorsignal (H/V) und punktförmiger Zugbeeinflussung (PZB) ausgerüstet.
- only Nova-2-Lite: Der Bf Dortmund Hbf beginnt am Einfahrsignal (Esig) D540 in km 163,2. Ab Esig D540 gilt bei Signalstellung Hp 1 gemäß Verzeichnis der zulässigen Geschwindigkeiten (VzG) eine zulässige Höchstgeschwindigkeit (Hg) von 80 km/h.
- only Nova-2-Lite: ICE 945 war planmäßig unterwegs und sollte laut Fahrplan um 18:46 Uhr im Bf Dortmund Hbf in Gleis 8 ankommen.
- only Nova-2-Lite: Bis zum Zeitpunkt des Ereignisses gab es keine Störungen im Betriebsablauf.
- only Nova-2-Lite: Der ICE 945 bestand aus einem TK, sechs MW und einem SW. Die Zuglänge betrug 205 m.
- only Nova-2-Lite: Laut EFR-Datenauswertung lag die Geschwindigkeit zum Zeitpunkt der Entgleisung bei ca. 70 km/h und damit unter der laut Signalisierung zulässigen Hg von 80 km/h.
- only Nova-2-Lite: Aufgrund der großen Zerstörung der Gleisanlagen und der sich teilweise überlagernden Entgleisungsspuren war es nicht möglich, eine durchgängige Rekonstruktion des Ereignisses herzustellen.
- only Gemma-001: Am 01.05.2017 entgleiste um ca. 18:46 Uhr der Zug ICE 945 des Eisenbahnverkehrsunternehmens (EVU) DB Fernverkehr AG bei der Einfahrt nach Gleis 8 des Bahnhofs (Bf) Dortmund Hauptbahnhof (Hbf) im Bereich der Doppelten Kreuzungsweiche (DKW) 36.
- only Gemma-001: Ursache für die Entgleisung war das unbeabsichtigte Anheben eines Rades des vorletzten Wagens im Bereich der linken Weichenzungenvorrichtung der DKW 36 a.
- only Gemma-001: Das unbeabsichtigte Anheben des Rades wurde durch einen impulsartigen Stoß in Kombination mit Veränderungen an der Kontaktfläche Rad/Weichenzunge ausgelöst.
- only Gemma-001: Nach der Entgleisung und der Bedienung einer Notbremse kam der Zug nach ca. 300 m mit TK und vier MW am Bahnsteig zum Halten.
- only Gemma-001: Wegen des zweispurigen Laufs wurden der Oberbau und insgesamt acht Weichen sowie dazugehörige LST-Anlagen zerstört.

**Ursachen des Unfalls**

- only Gemma-001: Als wahrscheinliche Ursache wird das Aufklettern des in Fahrtrichtung linken Rades (RS 4) des führenden DG (DG D) des vorletzten Wagen 806 629-2 an der Weichenzunge aufgrund eines vergrößerten Anlaufwinkels in Verbindung mit einer ungewöhnlichen Materialverformung an der Weichenzunge angenommen.
- only Gemma-001: Als maßgebende Ursachen der Zugentgleisung sind die gestörte Gleislage im Einfahrbereich der DKW 36, dass daraus resultierende dynamische harte Anfahren an die linke Weichenzunge und die sich ausbildende punktuelle Verformung an der linken Zunge im gerade befahrenen Fahrweg a/c zu nennen.
- only Gemma-001: Entstehen konnte diese punktuelle Verformung, weil das Instandhaltungspersonal zwar termingerecht inspizierte, den Verschleißzustand an der linken Zunge jedoch falsch einschätzte oder nicht erkannte.

</details>

<details><summary>123.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Zum Absetzen von Auszubildenden hielt der Zug kurzzeitig am Bahnsteig im Gleis 1 des Bahnhofs Bremen Hbf und setzte danach seine Fahrt, vorbei am Zwischensignal (Zsig) 11ZV1 durch das Gleis 201, fort.
- only Nova-2-Lite: Bei der Annäherung an das haltzeigende Ausfahrsignal (Asig) 11P1 kam der Zug erneut zum Stillstand.
- only Nova-2-Lite: Zum Zeitpunkt des Halts am Asig 11P1 befand sich der Wagen ca. in Kilometer 44,560. Nur wenige Meter davon entfernt wurden später Teile des abgescherten Radsatzlagers und Tragfedern gefunden.
- only Nova-2-Lite: Zeitgleich verständigte der technische Berechtigte der Betra F 216244, der sich zufällig im Bereich der Unfallstelle aufhielt, den örtlich zuständigen Fahrdienstleiter (FdI) des ESTW Bremen Süd über eine Unregelmäßigkeit am ausfahrenden DGS 88963. Daraufhin forderte der FdI den Triebfahrzeugführer (Tf) des DGS 88963 über GSM-R Zugfunk auf, wegen des Verdachts auf eine feste Bremse sofort anzuhalten.
- only Nova-2-Lite: Der Tf beschrieb in seiner Stellungnahme während des Gesprächs mit dem FdI das Entweichen der Luft aus der Hauptluftleitung wahrgenommen zu haben.
- only Gemma-001: Ermittlungen ergaben, dass die Zugtrennung durch eine weitere Entgleisung des 43. Wagens maßgeblich verursacht wurde.

**Ursachen des Unfalls**

- only Nova-2-Lite: Das Radsatzlager fiel dabei ab und wurde wenige Meter nach den ersten Entgleisungsspuren rechts neben dem Gleis aufgefunden.
- only Gemma-001: Die Zugentgleisung des DGS 88963 am 30.11.2017 wurde durch ein heiß gelaufenes Radsatzlager, ein s.g.
- only Gemma-001: Dieser führte zum Bruch der vorlaufenden Radsatzwelle im hinteren Drehgestell des an 42. Stelle laufenden Wagens.
- only Gemma-001: Möglicherweise wäre das Ereignis durch eine ordnungsgemäß funktionierende HOA/FBOA „Bookholzberg“, ca. 20 Kilometer vor der späteren Entgleisungsstelle, zu verhindern gewesen.
- only Gemma-001: Es ist davon auszugehen, dass der Fdl Delmenhorst deshalb nicht alarmiert wurde, weil offensichtlich ein Fehler in der Anlagensoftware zugrunde lag.
- only Gemma-001: Die technische Ursache am Fahrzeug, die in einer Kausalkette zum Versagen der Funktion des Radsatzlagers und somit zur Entgleisung geführt hatte, war nicht nachweisbar.

</details>

<details><summary>125.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 29.06.2013 befuhr DGS 83799, bestehend aus einem Triebfahrzeug und 23 Güterwagen, auf der Fahrt von Abelitz nach Magdeburg-Rothensee die Strecke 1520 (Oldenburg – Leer).
- only Nova-2-Lite: Auch hier wurde die Fahrt im entgleisen Zustand weiter fortgesetzt.
- only Nova-2-Lite: Durch den auf mehrere Kilometer entgleisen Radsatz wurden der Oberbau und Schaltmittel am Gleis beschädigt oder zerstört, was zu betrieblichen Störmeldungen beim Fdl Oldenburg führte.
- only Nova-2-Lite: Offensichtlich sind zwischenzeitlich mehrere Stunden vergangen bis die in Oldenburg aufgetretenen Störmeldungen als Folgeschäden einer Entgleisung identifiziert und dem DGS 83799 zugeordnet werden konnten.
- only Nova-2-Lite: Nur so erklärt sich, dass erst gegen 06:30 Uhr der Zug ca. 15 km südlich von Stendal im Bf Tangerhütte zur Überprüfung gestellt wurde.
- only Nova-2-Lite: Hierbei wurde der Zug jedoch nicht vollständig untersucht, da die Untersuchung am vierten Wagen abgebrochen wurde.
- only Nova-2-Lite: Dadurch konnten die infolge der Entgleisungen entstandenen Schäden am 17. Wagen nicht festgestellt werden.
- only Nova-2-Lite: Gegen 7:48 Uhr fuhr der Zug in Tangerhütte weiter.
- only Nova-2-Lite: Eine vollständige Untersuchung des Zuges wurde erst nach der Ankunft im Bf Magdeburg-Rothensee durchgeführt.
- only Nova-2-Lite: Hierbei wurde festgestellt, dass der an 17. Stelle hinter dem Triebfahrzeug (Tfz) laufende, unbeladene Güterwagen der Gattung Uiks an dem in Fahrtrichtung ersten Radsatz Entgleisungsschäden aufwies.
- only Gemma-001: Durch den auf mehrere Kilometer entgleisten Radsatz wurden der Oberbau und Schaltmittel am Gleis beschädigt oder zerstört, was zu betrieblichen Störmeldungen beim Fdl Oldenburg führte.

**Ursachen des Unfalls**

- only Gemma-001: Die vorgefundene Entwässerungssituation sowie die begleitende Vegetation am BÜ und die dadurch herabgesetzte Tragfähigkeit des Gleises haben ebenfalls die Entstehung der Gleislagefehler begünstigt.
- only Gemma-001: Jedoch erst durch die Vernachlässigung der Instandsetzungsmaßnahmen konnten sich die periodischen Gleislagefehler bei der Länghöhe derart entwickeln, dass sie letztendlich zur Entgleisung des Fahrzeugs führten.
- only Gemma-001: Die wesentliche Ursache für die Entgleisung liegt in der Gleislageanregung.
- only Gemma-001: Obwohl keine Grenzwertüberschreitungen der Einzelfehler der Gleislage vorliegen, ergibt sich aufgrund des periodischen Auftretens von SR 100 Fehlern eine Anregung des Fahrzeugs, die zu erheblichen Schwingungen des Wagenkastens und damit zur Entgleisung geführt hat.

</details>

<details><summary>126.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Es kommt zu keiner Zwangsbremsung.
- only Gemma-001: Bei der Durchfahrt über Gleis 1 im Bf Burgstall klettert der linke Radkranz der letzten Achse, ca. in km 4,866 auf den Schienenkopf auf.
- only Gemma-001: Dort läuft er wenige Meter auf dem Schienenkopf weiter und gleitet in km 4,872 unter Einwirkung der Kurvenfahrt nach bogenaußen endgültig auf die Betonschwellen nach links ab.

**Ursachen des Unfalls**

- only Nova-2-Lite: Bei der Durchfahrt über Gleis 1 im Bf Burgstall klettert der linke Radkranz der letzten Achse, ca. in km 4,866 auf den Schienenkopf auf.
- only Nova-2-Lite: Dort läuft er wenige Meter auf dem Schienenkopf weiter und gleitet in km 4,872 unter Einwirkung der Kurvenfahrt nach bogenaußen endgültig auf die Betonschwellen nach links ab.
- only Gemma-001: Trotz diverser Hinweise in den vorliegenden Gutachten, wurde die flach verlegte Tiefenentwässerung nicht auf ihre Funktionstüchtigkeit untersucht, um sie gegebenenfalls zu ergänzen oder Instand zu setzen.
- only Gemma-001: Aus fachlicher Sicht ist es nicht nachvollziehbar, dass die Empfehlungen des Gutachtens, den Einbau einer PSS aufgrund der Untergrundbeschaffenheit vorzunehmen, nicht umgesetzt wurden.
- only Gemma-001: Zudem wurde entgegen der fachtechnischen Beurteilung im Gutachten auf einen baubegleitenden Bodengutachter durch die Projektleitung verzichtet.
- only Gemma-001: Aufgrund mehrerer Baumaßnahmen, wie z.B. Fundamenterstellung sowie dem Einsatz schwerer Baumaschinen im Bf Burgstall kam es mit hoher Wahrscheinlichkeit zu Beschädigungen an der Tiefenentwässerung.
- only Gemma-001: Die Beschädigungen an der Tiefenentwässerung blieben unerkannt, da die Überwachungstätigkeiten nicht mit der nötigen Sorgfalt und Präsenz durch die Bauüberwachung durchgeführt wurden.
- only Gemma-001: Trotz fehlender Nachweise zur Gebrauchstauglichkeit der TE wurden die Baumaßnahmen durch den IBV freigegeben.
- only Gemma-001: Die Inspektion der Tiefenentwässerung obliegt dem ALV KIB.
- only Gemma-001: Grundlage der Inspektion der Tiefenentwässerung bildet das Entwässerungsheft und die Generierung eines Untersuchungsauftrages aus dem SAP-System.
- only Gemma-001: Beides war nicht vorhanden und somit konnten die aus der Baumaßnahme entstandenen Mängel nicht entdeckt werden.
- only Gemma-001: Da die Ursache für den Gleislagefehler nicht beseitigt wurde, konnten sich diese innerhalb kurzer Zeit neu ausbilden.
- only Gemma-001: Dies belegt, dass es sich bei der Mängelbeseitigung nicht um eine nachhaltige Instandsetzung handelte.
- only Gemma-001: Ursache für die vorgefundenen Schlammstellen ist eine nicht funktionierende Entwässerung des Gleisbereiches.
- only Gemma-001: Durch die fehlende Nachhaltigkeit in der Instandhaltung der Gleislage wurde die Ursache für die Gleislagefehler nicht beseitigt und die Ausbildung von mehreren hintereinanderliegenden zyklischen Länghöhenfehlern parallel auf beiden Schienensträngen ermöglicht.

</details>

<details><summary>179.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 06.05.2015 gegen 11:09 Uhr kollidierte der ICE 728 des Eisenbahnverkehrsunternehmens (EVU) DB Fernverkehr AG, auf dem Weg von Nürnberg Hbf nach Dortmund Hbf, mit Bruchstücken der Schallabsorber der festen Fahrbahn.
- only Nova-2-Lite: Der ICE 728 befuhr den Streckenabschnitt zwischen der Üst Willroth und Siegburg/Bonn mit einer Geschwindigkeit von ca. 300 km/h fallend.
- only Nova-2-Lite: Gegen 11:09 Uhr kam es bei einer Geschwindigkeit von ca. 260 km/h in km 30,66, kurz vor dem Siegburg Tunnel, zur Kollision mit Bruchstücken der Schallabsorber der Bauart Liakustik.
- only Nova-2-Lite: Wenige Tage zuvor wurden im genannten Streckenabschnitt Schienen gewechselt.
- only Nova-2-Lite: Nach dem durchgeführten Schienenwechsel erfolgte am 04.05.2015 die oberbautechnische Freigabe durch die örtliche Bauüberwachung, obwohl die defekten Schallabsorber zuvor nicht im erforderlichen Umfang ausgetauscht werden konnten.
- only Gemma-001: Bei mehreren dieser auf der festen Fahrbahn verbauten Schallabsorber aus Porenbeton kam es unter Einfluss der Witterung zu horizontalen Abbrüchen, die im Zusammenhang mit Gleisbauarbeiten zu weiteren vertikalen Abbrüchen führten.
- only Gemma-001: Bei der Zugüberfahrt wurden, durch die Sogwirkung des Zuges, Bruchstücke hochgeschleudert.
- only Gemma-001: Nachdem die vorgeschriebenen Messfahrten durchgeführt wurden und der Einbau der Schienen somit als mangelfrei zu bewerten war, durfte der Abschnitt am 06.05.2015 gegen 10:00 Uhr nun wieder mit 300 km/h befahren werden.
- only Gemma-001: Etwa 70 Minuten nach Aufhebung der Langsamfahrstelle, gegen 11:09 Uhr, kam es zur Kollision des ICE mit den Bruchstücken der Absorberplatten.

</details>

<details><summary>180.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 01.06.2018 gegen 05:13 Uhr entgleiste der Zug RB-G 11301 auf der Strecke 2631 zwischen den Bahnhöfen (Bf) Kyllburg und Bitburg-Erdorf nach der Kollision mit einem Murgang am Südportal des Wilsecker Tunnels in km 128,267.
- only Nova-2-Lite: Im Tunnel habe er Äste und Gegenstände in seinem Fahrweg bemerkt.
- only Nova-2-Lite: Er habe daraufhin sofort eine Schnellbremsung eingeleitet.
- only Nova-2-Lite: Während des Bremsvorgangs habe er festgestellt, dass das Gleis unter Wasser stand.
- only Nova-2-Lite: Er sei am Tunnelausgang Richtung Bitburg-Erdorf in einer Gerölllawine zum Stehen gekommen.
- only Nova-2-Lite: Anschließend habe er einen Nothaltauftrag abgesetzt und den Zug nach Reisenden durchsucht, es seien jedoch keine anwesend gewesen.
- only Gemma-001: Ursächlich für die Zugkollision war ein Murgang, der in Folge eines Unwetters mit zum Teil sehr starkem Niederschlag entstand und das Gleisbett am Südportal des Wilsecker Tunnels während der Nachtbetriebsruhe mit Erd- und Geröllmassen bedeckte.
- only Gemma-001: Als der Tf innerhalb des Wilsecker Tunnels Äste und Gegenstände im Fahrweg wahrnahm, leitete er bei km 128,132 eine Schnellbremsung ein und setzte einen Nothaltauftrag ab.
- only Gemma-001: Der Zug kam anschließend nach einem Bremsweg von 135 m um ca. 05:13 Uhr nach der Kollision mit dem Murgang und der Entgleisung der ersten vier Achsen zum Stehen.

**Ursachen des Unfalls**

- only Gemma-001: Die in Kapitel 4.5.2 aufgezeigten Regelungen der Ril 408 zum Umgang mit gefährdrohenden Umständen hätten, bei einer entsprechenden Umsetzung, die Kollision der RB-G 11301 mit dem Murgang höchstwahrscheinlich verhindern können.
- only Gemma-001: Voraussetzung zur Umsetzung dieser Regeln wären Erkenntnisse eines Fdl über regionale Wetterphänomene, wie beispielweise Starkregen, sowie das Wissen von potenziell gefährdeten Streckenabschnitten in seinem Stellbereich.
- only Gemma-001: Aufgrund der Nachtbetriebsruhe auf dem Stw Kyllburg konnten weder der Fdl noch das Zugpersonal die in der Nacht eingetretene extreme Wettersituation wahrnehmen.
- only Gemma-001: Die gewählten Vergleichsparameter waren somit unzureichend um die Gefährdung zu identifizieren.
- only Gemma-001: Gemäß den Anforderungen an das SMS eines Infrastrukturbetreibers nach Verordnung (EU) 2018/762 Anhang II Nr. 3.1., muss eine Organisation alle betrieblichen, organisatorischen und technischen Risiken erfassen und analysieren.

</details>

<details><summary>181.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: + Am 18.02.2015 erhielt der Personenzug RB 58043 des Eisenbahnverkehrsunternehmens (EVU) DB Regio AG gegen 13:00 Uhr vom Fahrdienstleiter (FdI) Jossa die Zustimmung in den Zugfolgeabschnitt 941 einzufahren, obwohl dieser noch durch den Güterzug DGS 69235 des EVU boxXpress.de GmbH besetzt war.
- only Nova-2-Lite: + Der Triebfahrzeugführer (Tf) des Personenzuges RB 58043 erkannte den Güterzug DGS 69235 in seinem Fahrweg und leitete eine Schnellbremsung ein.
- only Nova-2-Lite: + Die Fdl Jossa und Burgsinn bemerkten während eines Gespräches über den digitalen Zugfunk die Fehlnummer im Zugfolgeabschnitt 941 sowie die Rotausleuchtung des entsprechenden Streckenabschnitts.
- only Nova-2-Lite: + Der Fdl Jossa ließ daraufhin, nach der Zugschlussmeldung des Fdl Burgsinn, die Fahrt der RB 58043 in den Zugfolgeabschnitt 941 durch Bedienung des Ersatzsignales zu.
- only Nova-2-Lite: + Der Tf beschleunigte den Zug im Zugfolgeabschnitt 941 auf eine Höchstgeschwindigkeit von 136 km/h.
- only Nova-2-Lite: + RB 58043 kam nach einem Bremsweg von ca. 430 m in einem Abstand von ca. 70 m vor dem bereits wieder anfahrenden DGS 69235 zum Halten.
- only Gemma-001: Einen nach Ril 408.0622 Abschn. 1 erforderlichen schriftlichen Befehl 9 zum Fahren auf Sicht diktierte der Fdl Jossa dem Tf der RB 58043 nicht.
- only Gemma-001: Da sich der Zugschluss des DGS 69235 in einer Rechtskurve befand, erkannte der Tf der RB 58043 diesen erst spät und leitete umgehend eine Schnellbremsung ein.

**Ursachen des Unfalls**

- only Nova-2-Lite: + Im vorliegenden Fall gingen die Fdl Jossa und Burgsinn bei dem besetzten Zugfolgeabschnitt 941 irrtümlicherweise von einer Störung des selbsttätigen Streckenblocks aus.
- only Nova-2-Lite: + Einem nach Ril 408.0622 Abschn. 1 erforderlichen schriftlichen Befehl 9 zum Fahren auf Sicht diktierte der Fdl Jossa dem Tf der RB 58043 nicht.
- only Nova-2-Lite: + Durch den Befehl 9 wäre eine Einfahrt in den besetzten Zugfolgeabschnitt nicht verhindert worden, jedoch hätte sich die Gefahr einer Kollision verringert.
- only Nova-2-Lite: + Im vorliegenden Fall konnte nur durch die schnelle Reaktion des Tf der RB 58043 i.
- only Nova-2-Lite: V. m. dem bereits anfahrenden DGS 69235 eine Kollision verhindert werden.
- only Gemma-001: Es ist hinreichend wahrscheinlich, dass zumindest die Zugfahrt DGS 69235 in Richtung Burgsinn von dem Fdl und dem Auszubildenden nicht bewusst wahrgenommen wurde.
- only Gemma-001: Da der Fdl Jossa die Fortschaltstörungen vermutlich nicht erkannte und somit nicht durch Handeinwahl korrigierte, generierten sich im weiteren Verlauf entsprechende Fehlnummern in der ZNL800 des Stw Jossa.
- only Gemma-001: Eine weitere Überprüfung durch den Fdl Jossa, ob sich in diesem tatsächlich eine Zugfahrt befand, fand augenscheinlich nicht statt.
- only Gemma-001: Im Anschluss an den erfolglosen Grundstellungsversuch ließ der Fdl Jossa jedoch entgegen den weiteren Bestimmungen die Zugfahrt RB 58043, als erste Zugfahrt nach Beginn der Störung, ohne einen schriftlichen Befehl 9 zum Fahren auf Sicht in den noch als besetzt angezeigten Zugfolgeabschnitt 941 zu.

</details>

<details><summary>191.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der Zug GA 52805 kam an der Üst Treben-Lehma am Signal Ne 1 auf Höhe des Blocksignals A etwa in km 32,1 zum Halten.
- only Nova-2-Lite: Die Fdl muss die Hilfssperren zur Sicherung der Weichen für die Hilfsfahrstraße demnach entweder nicht angebracht oder sie entfernt haben, bevor der Zug die Weiche 21 passiert hatte.
- only Gemma-001: Alle Zugfahrten in der Relation Regis-Breitingen – Treben-Lehma, so auch die Zugfahrt GA 52805, erfolgten daher als Fahrten im besonderen Auftrag mit Befehl und im Gegengleis.

**Ursachen des Unfalls**

- only Nova-2-Lite: Darum war der Fahrweg des Zuges nicht mehr gesichert und das anschließende unzeitige Umstellen der Weiche 20 unter dem Zug möglich.
- only Gemma-001: Deshalb war der Fahrweg des Zuges nicht mehr gesichert und das anschließende unzeitige Umstellen der Weiche 20 unter dem Zug möglich.

</details>

<details><summary>196.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der Zug RB 29870 fuhr gegen 06:38 Uhr über die Weiche W 2 in den Bf Mellenbach-Glasbach ein.
- only Nova-2-Lite: Der Ablauf bei diesem Vorfall und die Ergebnisse der Überprüfung der Funktion der Weiche und des Signals Ne 13 nach dem Ereignis legen den Schluss nahe, dass das Überwachungssignal auch bei der Zugfahrt RB 29870, entgegen der Aussage des Tf des Zuges RB 29870, Ne 13b gezeigt haben könnte, dieser die Signalstellung jedoch zu spät oder überhaupt nicht wahrnahm und daher nicht rechtzeitig bremste.

**Ursachen des Unfalls**

- only Nova-2-Lite: Die Weiche W 2 im Bf Mellenbach-Glasbach war eine Rückfallweiche der Bauart 49-190-1:9. Rückfallweichen sind so konstruiert, dass sie aufgefahren werden können und dann selbsttätig mit Hilfe einer hydraulisch-mechanischen Rückstelleinrichtung in die Grundstellung zurücklaufen.
- only Nova-2-Lite: Die Rückstelleinrichtung der Firma FEW entsprach zur Zeit ihres Einbaus den anerkannten Regeln der Technik und war für den Eisenbahnbetrieb zugelassen.
- only Nova-2-Lite: Deren Rückstellkräfte lagen jedoch konstruktionsbedingt im unteren Toleranzbereich des Anwendungsspektrums und wurden zudem an der Weiche W 2 nicht vollständig ausgeschöpft.
- only Nova-2-Lite: Eine fehlerhafte Anzeige am Signal Ne 13 soll durch die Konstruktion des Endlagenprüfers ELP 319 verhindert werden.
- only Nova-2-Lite: Eine spontan auftretende Fehlfunktion ist jedoch aufgrund der Abhängigkeit vom Funktionieren eines einzigen Kontaktes ohne redundante Absicherung nicht vollständig auszuschließen.
- only Gemma-001: Es ist davon auszugehen, dass die Hydraulikflüssigkeit der Rückstelleinrichtung aufgrund der niedrigen Außentemperaturen von ca. -14°C sehr zähflüssig war und dadurch die Reversion der Weiche stark verzögert wurde.
- only Gemma-001: Der Ablauf bei diesem Vorfall und die Ergebnisse der Überprüfung der Funktion der Weiche und des Signals Ne 13 nach dem Ereignis legen den Schluss nahe, dass das Überwachungssignal auch bei der Zugfahrt RB 29870, entgegen der Aussage des Tf des Zuges RB 29870, Ne 13b gezeigt haben könnte, dieser die Signalstellung jedoch zu spät oder überhaupt nicht wahrnahm und daher nicht rechtzeitig bremste.

</details>

<details><summary>199.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: + Am 05.12.2017 gegen 19:27 Uhr kollidierte der Personenzug DPN 32547, auf der Fahrt von Rheine nach Krefeld Hbf, zwischen der Abzweigstelle (Abzw) Weißenberg und dem Bahnhof Meerbusch-Osterath mit dem Güterzug GM 48714.
- only Nova-2-Lite: + Eine Fehleingabe der Fdl Weißenberg in das ZN Sammelfeld 700 führte dazu, dass Zug 95307 unter der Zugnummer 66365 ausfuhr.
- only Nova-2-Lite: + Die Fdl Meerbusch-Osterath überschrieb die in der ZN erscheinende Fehlnummer F36 12 mit der Zugnummer 48714.
- only Nova-2-Lite: + Beide Fdl gingen irrtümlich von einer Störung der Sicherungsanlagen aus.
- only Nova-2-Lite: + Zug 48714 wird durch die Fdl Meerbusch-Osterath zurückgemeldet.
- only Nova-2-Lite: + Zug DPN 32547 erhält Ersatzauftrag zur Vorbeifahrt am Blocksignal ohne den Befehl zum Fahren auf Sicht.
- only Gemma-001: Sie überschrieb die Fehlnummer mit der Zugnummer des folgenden Zuges 48714, der zu diesem Zeitpunkt noch im Abschnitt 200 auf dem ZN-Monitor angezeigt wurde.
- only Gemma-001: Und so zögerte sie nicht, den kurz zuvor bei ihr vorbeifahrenden Zug 95307 als Zug 48714 an die Fdl in Weißenberg zurückzumelden.
- only Gemma-001: Als dies nicht gelang, wurde die Zugfahrt DPN 32547 in Weißenberg mit Fahrtstellung des Blocksig-nals A und, aufgrund der Rotausleuchtung, am folgenden Sbk 201 mittels Ersatzsignal Zs 1 durch die Fdl Weißenberg zugelassen.
- only Gemma-001: Da zu diesem Zeitpunkt noch immer der 48714 vor dem Esig A stand, nahm der Tf die Fahrtstellung des Signals auf und setzte sei-nen Zug in Bewegung.
- only Gemma-001: Mit dieser Geschwindigkeit prallte der Personenzug DPN 32547 um 19:27 Uhr auf den anfahrenen Güterzug 48714.

**Ursachen des Unfalls**

- only Nova-2-Lite: + Der Personenzug DPN 32547 wurde unzulässig in den noch durch den Güterzug GM 48714 in identischer Fahrtrichtung beanspruchten Blockabschnitt eingelassen.
- only Nova-2-Lite: + Die Handlungen der Fdl in Weißenberg und Meerbusch-Osterath waren ursächlich für die Entstehung des Unfalls.
- only Nova-2-Lite: + So führten zunächst eine Reihe nicht sicherheitsrelevanter Fehler im Zusammenhang mit der Bedienung der ZN zum Verlust des Überblicks auf die Betriebssituation.
- only Gemma-001: Die Handlungen der Fdl in Weißenberg und Meerbusch-Osterath waren ursächlich für die Ent-stehung des Unfalls.
- only Gemma-001: Die Fdl der Abzw Weißenberg ließ den Zug 95307 durch eine Fehleingabe in der ZN mit falscher Zugnummer 66365 in Richtung Meerbusch-Osterath abfahren.
- only Gemma-001: Irrtümliche erneute Eingabe der Zugnummer 66365 durch die Fdl Abzw Weißenberg.
- only Gemma-001: Die im Bahnhof Meerbusch-Osterath in der Folge erscheinende Fehlnummer wurde durch die Fdl Meerbusch-Osterath mit der nachfolgenden Zugnummer 48714 in die ZN überschrieben.
- only Gemma-001: Beide Fdl gingen irrtümlich von einer Störung der Sicherungsanlagen aus.
- only Gemma-001: Zug 48714 wird durch die Fdl Meerbusch-Osterath zurückgemeldet.
- only Gemma-001: Zug DPN 32547 erhält Ersatzauftrag zur Vorbeifahrt am Blocksignal ohne den Befehl zum Fahren auf Sicht.
- only Gemma-001: Hinsichtlich der Streckenkenntnis des Tf wurden Defizite sichtbar.

</details>

<details><summary>200.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der Zugverband, aus dem Triebfahrzeug der Bauart Gravita 10 BB mit der Fahrzeugnummer 92 80 1 261 006-1 und 23 beladenen Güterwagen bestehend, war ab 08:52 Uhr als Zugfahrt 49691 von Bf Frankfurt (Oder) Pbf nach Bf Frankfurt (Oder) Oderbrücke unterwegs.
- only Nova-2-Lite: Bei der Einfahrt in Bf Frankfurt (Oder) Oderbrücke entgleiste das erste Drehgestell des letzten Wagens zwischen der vom durchgehenden Hauptgleis (204) ins Bereitstellungsgleis (101) abzweigenden Weiche 115 und der folgenden Weiche 116, etwa in km 2,3.

</details>

<details><summary>201.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Das für das zu untersuchende Ereignis relevante Stellwerk B1 war eine Mischbauform aus Bruchsal (unbestimmter Bauform) und Gleisbildstellwerk der Bauform II der ehem.
- only Nova-2-Lite: Das Ereignis war auf eine Vorbeifahrt des DGS 42722 am Halt zeigenden Signal 206 ohne Zustimmung des Fdl des Stellwerkbezirks B1 zurückzuführen.
- only Nova-2-Lite: Der Tf begann die Fahrt am Signal 206, obwohl der Befehl 2 in Verbindung mit den Befehlen 4 und 7 des Fdl noch keine Gültigkeit hatte.
- only Gemma-001: Seit dem 17.12.2019 ca. 08:11 Uhr lag im Bf Leipzig-Engelsdorf eine Störung der Weiche 11 vor, so dass Fahrten nach Leipzig-Schönefeld im Gegengleis stattfinden mussten.
- only Gemma-001: Der Fdl B1 diktierte dem Tf der Zugfahrt DGS 42722 somit die Inhalte der Befehle 2, 4 und 7 ohne Übermittlungscode, Uhrzeit und weitere Details.
- only Gemma-001: Zum Übermittlungszeitpunkt hatte der Befehl somit noch keine Gültigkeit.
- only Gemma-001: Etwa zeitgleich mit dem Passieren des Einfahrsignals des Bf Leipzig-Engelsdorf durch die S-Bahn S 37395, setzte sich der DGS 42722 in Bewegung.
- only Gemma-001: Er passierte das Signal 206 und fuhr die Weiche 20 auf, was entsprechende Störungsmeldungen beim Fdl B1 aktivierte.

**Ursachen des Unfalls**

- only Gemma-001: Der Tf begann die Fahrt am Signal 206, obwohl der Befehl 2 in Verbindung mit den Befehlen 4 und 7 des Fdl noch keine Gültigkeit hatte.

</details>

<details><summary>203.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 06.04.2017 gegen 14:43 Uhr ereignete sich auf der Fahrt von Au (Siegburg) nach Horrem im Bahnhof (Bf) Blankenberg (Siegburg) eine technische Störung am Zug S 33211 des Eisenbahnverkehrsunternehmens DB Regio AG Region NRW.
- only Nova-2-Lite: Die Untersuchungen am Fahrzeug zeigten, dass ein Radsatzlager beschädigt war und sich ein Teil der Primärfederung an der in Fahrtrichtung linken Seite des siebten Radsatzes gelöst hatte.
- only Nova-2-Lite: Das schadhafte Radsatzlager war auf dem Treibradsatz (Radsatznummer 27006084) montiert und in einem Jakobsdrehgestell eingebaut.
- only Gemma-001: Es ist davon auszugehen, dass in diesem Augenblick das Radsatzlager blockierte.
- only Gemma-001: Die Untersuchungen am Fahrzeug zeigten, dass ein Radsatzlager beschädigt war und sich ein Teil der Primärfederung an der in Fahrtrichtung linken Seite des siebten Radsatzes gelöst hatte, siehe Abbildung 2.

**Ursachen des Unfalls**

- only Nova-2-Lite: Das Radsatzlager war trocken gelaufen.
- only Nova-2-Lite: Darüber hinaus ereigneten sich im Zeitraum Februar 2017 bis April 2017 insgesamt drei Ereignisse mit nahezu identischen Schadbildern.
- only Nova-2-Lite: Die Untersuchungen [...] ergaben, dass es in allen Fällen durch einen verbogenen Lammellenring zum Versagen der Radsatzlager gekommen war.
- only Nova-2-Lite: Bei der Montage des Lagerdeckels, insbesondere bei Lagerdeckeln mit Erdungskontakten, kam es zu einem fehlerhaften Einbau der Lammellenringe.
- only Gemma-001: Das Lager war trocken gelaufen.
- only Gemma-001: Warum der Lamellenring ausfiel, konnte anhand des Schadbildes nicht mehr zweifelsfrei nachvollzogen werden.
- only Gemma-001: Die Untersuchungen, die auf Betreiben des Fahrzeughalters durchgeführt wurden, ergaben, dass es in allen Fällen durch einen verbogenen Lammellenring zum Versagen der Radsatzlager gekommen war.
- only Gemma-001: Dabei wurde festgestellt, dass es bei der Montage des Lagerdeckels, insbesondere bei Lagerdeckeln mit Erdungskontakten, zu einem fehlerhaften Einbau der Lammellenringe gekommen war.
- only Gemma-001: Der fehlerhafte Einbau von Lammellenringen im Radsatzlager führte in der Folge zur Undichtigkeit und zum Verlassen der vorgesehenen Position.

</details>

<details><summary>226.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der anzeigegeführte Güterzug DGS 90977 kam zufolgebedingt vor dem LZB-Blockkennzeichen (LZB-Bk) 86 auf der Strecke 6107 vor dem Hp Leiferde (b Gifhorn) zum Halten.
- only Nova-2-Lite: Der ebenfalls anzeigegeführte Güterzug DGS 42593 folgte dem DGS 90977 auf dieser Strecke.
- only Nova-2-Lite: Der Zug wurde hierdurch zum Stillstand geführt und kam in km 210,4 der Strecke zum Halten.
- only Nova-2-Lite: Der Tf des DGS 42593 meldete sich über GSM-R Zugfunk bei der zuständigen Fdl-in Fallersleben West und informierte sie, dass er einen LZB-Übertragungsausfall habe und zum Halten gekommen sei.
- only Nova-2-Lite: Die Fdl-in erteilte dem Tf für die signalgeführte Weiterfahrt den schriftlichen Befehl 10 und 10.1. Der DGS 42593 sollte seine Fahrt demnach die nächsten 2.000 m mit einer Höchstgeschwindigkeit von 40 km/h fortsetzen.

**Ursachen des Unfalls**

- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritischer Faktoren ist in den folgenden Kapiteln gegeben.
- only Nova-2-Lite: Zur direkten Zuordnung wird den sicherheitskritischen Faktoren eine Kurzbezeichnung zugewiesen.
- only Nova-2-Lite: Jedem ursächlichen oder beitragenden Faktor wird der Buchstabe F und einer fortlaufenden Nummerierung beigestellt.
- only Nova-2-Lite: Beispiel: [F1]. Zugehörige systemische Faktoren erhalten bei derselben Nummer den Buchstabe S; hier können ggf. weitere Unterscheidungen mittels eines Kleinbuchstabens erforderlich werden.
- only Nova-2-Lite: Beispiel: [S1a] und [S1b] zu [F1] Erkannte Aspekte, welche nach dem Ereigniseintritt die Raschheit oder Leistungsfähigkeit des Notfallmanagements beeinträchtigt und dadurch das Schadensausmaß vergrößert haben, werden mit dem Buchstaben N fortlaufend nummeriert.

</details>

<details><summary>231.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 03.06.2022 verließ der Personenzug RB-D 59458 um 12:14 Uhr, mit einer Verspätung von ca. sieben Minuten, den Bf Garmisch-Partenkirchen in Richtung München Hbf.
- only Nova-2-Lite: Der lokbespannte Wendezug RB-D 59458 war aus fünf Doppelstockwagen sowie einer elektrischen Lokomotive der BR 111 am Schluss des Zuges gebildet.
- only Nova-2-Lite: Nach etwa zwei Kilometern Fahrt erreichte der Zug die im Streckenabschnitt zugelassene Geschwindigkeit von 100 km/h.
- only Nova-2-Lite: In Höhe des Gemeindeteils Burgrain befuhr der Zug nahezu mit dieser Geschwindigkeit einen Linksbogen.
- only Nova-2-Lite: Innerhalb dieses Bogens mit dem Radius 407 m entgleiste in km 97,676 gegen 12:16 Uhr der Zug mit allen Fahrzeugen.
- only Nova-2-Lite: Aufgrund der vorhandenen Trassierung mit maximaler Ausnutzung des zulässigen Überhöhungsfehlbetrages $uf = 130$ mm wirkten bei Überfahrt eines Zuges hohe Kräfte auf das Gleis.
- only Nova-2-Lite: Unmittelbar danach folgten wieder intakte Spannbetonschwellen, wodurch sich die Spurweite wieder in Richtung des zulässigen Maßes verkleinerte.
- only Nova-2-Lite: Die anderen Fahrzeuge des Zuges kamen im entgleisen Zustand auf dem Gleis zum Stehen.

**Ursachen des Unfalls**

- only Nova-2-Lite: Im Rahmen der Untersuchung des Ereignisses wurden die folgenden Handlungen, Unterlassungen, Vorkommnisse oder Umstände als sicherheitskritische Faktoren identifiziert.
- only Nova-2-Lite: Diese werden gemäß Durchführungsverordnung (EU) 2020/572 in ursächliche oder beitragende und systemische Faktoren unterschieden.
- only Nova-2-Lite: Zusätzlich werden ermittelte Defizite des Notfallmanagements behandelt.
- only Nova-2-Lite: Zur besseren Übersichtlichkeit der Faktoren und Aspekte des Notfallmanagements wird eine Systematik mit Kennzeichnungen in eckigen Klammern verwendet.
- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritische Faktoren wird in den folgenden Kapiteln gegeben.

</details>

<details><summary>234.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Im Bf Hannover-Hainholz erfolgte der planmäßige Fahrtrichtungswechsel, der in dispositiven Gründen bezüglich der benötigten Arbeitsrichtung der mitgeführten Wagen für die Baumaßnahme in Padborg begründet war.
- only Nova-2-Lite: Kurz vor der Einfahrt in den Bf Seelze Ost nahm der Tf den Signalbegriff Vr 0 „Halt erwarten“ am Einfahrtsignal (Esig) wahr und leitete eine Betriebsbremsung ein.
- only Nova-2-Lite: Am 500 Hz Gleismagneten des Esig Z<sup>II</sup> wurde bei einer Geschwindigkeit von ca. 62,8 km/h eine Zwangsbremsung ausgelöst, die der Tf mit einer Schnellbremsung unterstützte.
- only Nova-2-Lite: Die weiterhin unzureichende Bremswirkung des Güterzuges führte zu einer Vorbeifahrt am Haltbegriff des Esig Z<sup>II</sup> und anschließend zur Kollision mit denen im Gleis 815 stehenden Wagen aus Güterzuges EZ 51593.
- only Gemma-001: Am 500 Hz Gleismagneten des Esig ZII wurde bei einer Geschwindigkeit von ca. 62,8 km/h eine Zwangsbremsung ausgelöst, die der Tf mit einer Schnellbremsung unterstützte.
- only Gemma-001: Die weiterhin unzureichende Bremswirkung des Güterzuges führte zu einer Vorbeifahrt am Haltbegriff des Esig ZII und anschließend zur Kollision mit denen im Gleis 815 stehenden Wagen aus Güterzuges EZ 51593.

**Ursachen des Unfalls**

- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritische Faktoren wird in den folgenden Kapiteln gegeben.
- only Nova-2-Lite: Tabelle 1: Zusammenfassung Einflussfaktoren
- only Gemma-001: Das Unterlassen, eine Durchgängigkeit der HL herzustellen, wurde durch die BEU als ein ursächlicher Faktor des Ereignisses gewertet [F1].
- only Gemma-001: Das Unterlassen einer vereinfachten Bremsprobe als ein unterbliebenes Prüfverfahren zum Offenbaren von Arbeitsfehlern wurde durch die BEU zusätzlich als ein ursächlicher Faktor des Ereignisses eingestuft [F2].
- only Gemma-001: Im Ergebnis wurde festgestellt, dass der zum Ereigniszeitpunkt zuständige Tf trotz der nachweislich durchgeführten Überwachungen und regelmäßigen Fortbildungen kein Situationsbewusstsein für das Erfordernis der Durchführung einer vereinfachten Bremsprobe hatte.
- only Gemma-001: Der ohnehin schon durch den Tf spürbare Zeitdruck, den zeitkritischen Fahrplan einzuhalten um den Grenzübergang in Richtung Dänemark und in der Folge die Baustelle zu erreichen, wurde durch Anrufe des EVU während der Fahrt des Tf verstärkt.
- only Gemma-001: Es ist davon auszugehen, dass diese auf den Tf wirkende Situation zu den festgestellten Arbeitsfehlern führte und eventuell auch die sorgfältige Beachtung der gemäß Regelwerk obligatorischen Tätigkeiten beim Richtungswechsel und bzgl. einer gebotenen Bremsprobe schmälerte.

</details>

<details><summary>237.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 21.11.2024 sollten im Bf Dorsfeld von 21:30 Uhr bis 05:30 Uhr des Folgetages Arbeiten gemäß Betra Nr.
- only Nova-2-Lite: F35 6634 24 ausgeführt werden.
- only Nova-2-Lite: Zur Durchführung der Betra – und um die Arbeiten beginnen zu können – waren ein Bauzug, bestehend aus einem Tfz und zwei mit Schotter beladenen offenen Schüttgutwagen sowie eine Stopfmachine erforderlich.
- only Nova-2-Lite: Diese sollten dem Bf Dorsfeld aus Richtung Horrem zugeführt werden.
- only Nova-2-Lite: Zwecks Durchführung der Arbeiten gemäß Betra sollten in vier Phasen nacheinander bestimmte Gleisabschnitte und Weichen im Bf Dorsfeld gesperrt werden.
- only Nova-2-Lite: Die vorgesehenen Stopfarbeiten konnten witterungsbedingt nicht durchgeführt werden.
- only Nova-2-Lite: Als Ergebnis der bis zu diesem Zeitpunkt durchgeführten Arbeiten befanden sich sowohl der Bauzug als auch die Stopfmachine im Gleis 2 des Bf Dorsfeld, um von dort als Zugfahrt wieder ausfahren zu können.
- only Nova-2-Lite: Der Technisch Berechtigte meldete der zuständigen Fdl-in Horrem I um 00:49 Uhr das Ende der Arbeiten und die Befahrbarkeit der zuvor gesperrten Gleisabschnitte und Weichen per GSM-R. Die Stopfmachine fuhr kurz darauf in Richtung Horrem aus dem Bf Dorsfeld aus.
- only Nova-2-Lite: Der Bauzug blieb noch in Gleis 2 zurück, da die geplante Ausfahrt erst für 01:46 Uhr vorgesehen war.
- only Nova-2-Lite: Grund für diese Rotausleuchtung war der dort noch stehende Bauzug.
- only Nova-2-Lite: Daraufhin rief sie den ihr durch die stattgefundenen Betra-Arbeiten bekannten BÜB an und forderte von diesem eine Meldung über das Freisein des Gleises 2 des Bf Dorsfeld an.

**Ursachen des Unfalls**

- only Nova-2-Lite: Zur besseren Übersichtlichkeit der Faktoren und Aspekte des Notfallmanagements wird eine Systematik mit Kennzeichnungen in eckigen Klammern verwendet.
- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritische Faktoren wird in den folgenden Kapiteln gegeben.

</details>

<details><summary>238.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Aufgrund einer Störung an einem im Fahrtverlauf liegenden BÜ kam es bei der von München kommenden Zugfahrt S 6776 zu einer Verspätung von ca. zehn Minuten.
- only Nova-2-Lite: Der Fdl Wolfratshausen entschied sich daher, die eigentlich planmäßig im Bf Icking vorgesehene Zugkreuzung der Personenzüge S 6785 und S 6776 in den Bf Ebenhausen-Schäftlarn zu verlegen.
- only Nova-2-Lite: Nachdem der Personenzug S 6785 zum Stillstand gekommen war, hob der Tf die Zwangsbremsung auf und setzte die Fahrt in Richtung des durch den entgegenkommenden Personenzug belegten eingleisigen Streckenabschnittes fort.
- only Nova-2-Lite: Durch die Weiterfahrt des Personenzuges S 6785 kam es zu einer Belegung des Abschnittes 1<sup>3</sup> und dadurch zu einem vorzeitigen Signalhaltfall des Esig 1A ca. 50 m vor der Vorbeifahrt des sich zur gleichen Zeit dem Bf Ebenhausen-Schäftlarn nähernden Personenzuges S 6776.
- only Nova-2-Lite: Um ca. 16:35 Uhr kam es zur Zugkollision mit dem ausfahrenden, zu diesem Zeitpunkt noch 57 km/h schnellen Personenzug S 6785.
- only Gemma-001: Nachdem der Personenzug S 6785 zum Stillstand gekommen war, hob der Tf die Zwangsbremsung auf und setzte die Fahrt in Richtung des durch den entgegenkommenden Personenzuges S 6776 belegten eingleisigen Streckenabschnittes fort.
- only Gemma-001: Der Tf dieses Personenzuges leitete umgehend nach Erkennen des Signalhaltfalls eine Schnellbremsung ein und der Zugverband kam ca. im km 17,76 zum Stillstand.
- only Gemma-001: Während der Tf sich beim Fdl über den Grund des Signalhaltfalls erkundigte, kam es um ca. 16:35 Uhr zur Zugkollision mit dem ausfahrenden, zu diesem Zeitpunkt noch 57 km/h schnellen Personenzug S 6785.

**Ursachen des Unfalls**

- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritische Faktoren wird in den folgenden Kapiteln gegeben.

</details>

## Nova-2-Lite vs. summaries

36 reports in both. Sentences of one run that the other run has in the same section:

| section | Nova-2-Lite | also in summaries | summaries | also in Nova-2-Lite |
|---|---|---|---|---|
| Hergang des Unfalls | 229 | 32% | 184 | 50% |
| Höhe des Schadens, Anzahl Tote und Verletzte | 112 | 54% | 83 | 79% |
| Ursachen des Unfalls | 108 | 55% | 200 | 32% |
| Bauart der beteiligten Stellwerke | 45 | 66% | 43 | 79% |
| Baureihe der beteiligten Schienenfahrzeuge | 66 | 28% | 63 | 39% |
| Sicherheitsempfehlungen zur Verhinderung zukünftiger Unfälle | 81 | 62% | 48 | 47% |
| bereits umgesetzte Maßnahmen | 156 | 55% | 145 | 77% |

### Sentences only in one run

<details><summary>001.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: "KC 62848 war um 20:33 Uhr aus dem benachbarten Bf Hameln ausgefahren (*Anmerkung: plm.
- only Nova-2-Lite: Fahrzeit bis Bad Münder ca. 8 Minuten*).
- only Nova-2-Lite: Der Ein- und Ausfahrt des KC 62848 wurde fahrdienstlich durch Einstellen der Durchfahrt durch Gleis 2 zugestimmt.
- only Nova-2-Lite: Hierfür war seitens des Ww Ms das Esig F und seitens des Fdl Mnf das Asig P2 nach Festlegung der zugehörigen Einfahr- bzw.
- only Nova-2-Lite: Ausfahrstraße bedient, d.h. in Stellung Hp 2 „Langsamfahrt“ gebracht worden.
- only Nova-2-Lite: IRC 51219 war um 20:37 Uhr aus dem benachbarten Bf Springe ausgefahren.
- only Nova-2-Lite: Während der Annäherung an den Bf Bad Münder (*Anmerkung: plm.
- only Nova-2-Lite: Fahrzeit ca. 5 Minuten*) konnte aufgrund des bestehenden Fahrstraßenausschlusses mit dem kreuzenden KC 62848 keine fahrdienstliche Zustimmung am Esig A erfolgen, welches folglich in Stellung Hp 0 „Halt“ verblieb.
- only summaries: Infolge des Zusammenstoßes kam es zum Brand an der Unfallstelle und es explodierte ein mit Gefahrgut (Epichlorhydrin; UN-Nr. 2023) beladener Kesselwagen.

**Ursachen des Unfalls**

- only summaries: Hinsichtlich der Erklärung für das unzureichende Bremsvermögen des IRC 51219 ist unter Berücksichtigung und Gewichtung aller gem.
- only summaries: Ziffer 4.6 gewonnenen Erkenntnisse eine im vorderen Zugteil unterbrochene und damit nicht durchgehende Hauptluftleitung des Zuges anzusehen, wobei die Untersuchungen hinsichtlich der Lokalisierung der „Trennstelle“ deutliche Anhaltspunkte auf die Verbindung zwischen den Wagen Nr. 4 und 5 ergeben haben.
- only summaries: Die im Gutachten vorgenommene theoretische Bremsberechnung ergab, dass der Zug lediglich über das Bremsvermögen des Triebfahrzeugs und der Wagen Nr. 1 bis 4 verfügt haben konnte.
- only summaries: Der Luftabsperrhahn polnischer Güterwagen (ähnlich des Typs AK 8), wie er u.a. beim Wagen Nr. 5 vorhanden war, die technische Erklärung für das ungewöhnliche Bremsverhalten liefert.
- only summaries: Dieser konnte bei geschlossener bzw. teilgeschlossener Stellung die Druckverhältnisse in der Hauptluftleitung des Zuges entsprechend negativ beeinflussen.
- only summaries: Bei regelgerechter Abarbeitung des Ereignisses „Empelde“ durch den Triebfahrzeugführer des IRC 51219 wäre insoweit das spätere Unfallereignis „Bad Münder“ vermeidbar gewesen.
- only summaries: Der Sachverhalt ist dem fachlichen Verantwortungsbereich des Triebfahrzeugführers des IRC 51219 zuzuordnen, dessen individueller Wahrnehmung das schlechte Bremsverhalten des Zuges nicht entgangen sein konnte und offenkundig auch nicht war, wie aus dem Inhalt des Zugfunkgesprächs gefolgert werden muss (Wortlaut: „Genau.
- only summaries: Voll dran vorbeigerutscht.“; vgl.
- only summaries: Die Fahrt des IRC 51219 nach dem Ereignis „Empelde“ wurde jedoch ohne Veranlassung der erforderlichen „volle Bremsprobe“, die den Grund für die ungenügende Bremswirkung offenbart hätte, fortgesetzt.

</details>

<details><summary>002.md</summary>

**Hergang des Unfalls**

- only summaries: Die Einfahrt in den Bf Brühl Gbf vom Gegengleis aus Richtung Hürth-Kalscheuren mußte deshalb auf Ersatzsignal erfolgen.
- only summaries: Etwa 300 m nach der Vorbeifahrt am Einfahrsignal wurde der Zug allmählich wieder beschleunigt.
- only summaries: Der Zug beschleunigte im Bereich des Bahnhofs Brühls auf einem Weg von 1820 m bis auf eine registrierte Spitzengeschwindigkeit von ca. 122 km/h.
- only summaries: Der Bereich der Weiche 48 im Bft Brühl Pbf wurde mit ca. 121 – 122 km/h durchfahren.
- only summaries: Das Triebfahrzeug und fünf Wagen stürzten eine Böschung hinunter, wobei die Lokomotive etwa 150 m hinter der Entgleisungsstelle in einem Wohnhaus zum Stehen kam.

**Ursachen des Unfalls**

- only summaries: Bei dem Unglück kann begünstigend gewirkt haben, dass eine Unterrichtung des Triebfahrzeugführers über die Betriebsführung im Bahnhof Brühl durch La-Eintrag oder Zugfunk nicht erfolgt ist, zugleich aber die La einen nicht relevanten Eintrag für eine Langsamfahrstelle mit einer Geschwindigkeit von 120 km/h enthalten hat.
- only summaries: Die Entgleisung des D 203 am 06. Februar 2000 wäre vermieden worden, wenn innerhalb des Bahnhofs Brühl die zulässige Geschwindigkeit von 40 km/h eingehalten, oder im Zusammenhang mit der Baumaßnahme eine technische Sicherung zur Geschwindigkeitsüberwachung der Züge vorgesehen worden wäre.

</details>

<details><summary>007.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Die Unfallstelle in km 30,360 liegt zwischen dem Einfahrtsignal und dem Einfahrsignal des Bahnhofs Schrozberg.
- only Nova-2-Lite: Die Strecke verläuft dort auf einem ca. 8 m hohen Bahndamm in einer (aus Richtung Schrozberg gesehen) Linkskurve, die beidseitig der Bahn teilweise mit Bäumen und hohem Strauchwerk bewachsen ist.
- only Nova-2-Lite: Hierdurch war in beiden Fahrtrichtungen die Sichtweite stark eingeschränkt.
- only Nova-2-Lite: Die beteiligten Triebfahrzeugführer hatten somit keine Möglichkeit, den drohenden Zusammenstoß zu erkennen und abzuwenden.
- only summaries: Die Kausalkette, die letztlich zum Zusammenstoß der Züge RE 19533 und RE 19534 führte, beginnt mit dem Ausfall des Vorsignalwiederholers Wvp2 im Bahnhof Niederstetten.
- only summaries: Der Fahrdienstleiter Niederstetten erkannte diese Ursache der Auflösestörung nicht, obwohl eine solche Störung mehrmals zuvor im Störungsbuch vermerkt war.
- only summaries: Stattdessen vermutete er eine Störung an den Sicherungseinrichtungen des Bahnübergangs in Kilometer 38,058 und ließ die Zugfahrt RE 19533 über die nicht aufgelöste Fahrstraße auf Ersatzsignal zu, nachdem er vom Fahrdienstleiter Schrozberg für Zug IRC 52245 die Rückmeldung angefordert und erhalten hatte.
- only summaries: Weshalb er um 11:59 Uhr diese Rückmeldung für RE 19533 abgab, für die keinerlei Grundlage vorhanden war, ist nicht nachvollziehbar.
- only summaries: Danach erteilte der Fahrdienstleiter Schrozberg durch Ersatzsignal die Zustimmung zur Ausfahrt von Zug 19534 nach Niederstetten.

**Ursachen des Unfalls**

- only Nova-2-Lite: Damit wurden die Voraussetzungen für das weitere Handeln des Fahrdienstleiters Schrozberg geschaffen.
- only summaries: Der Zusammenstoß wäre vermieden worden, wenn der Fahrdienstleiter Schrozberg die Räumungsprüfung für den Abschnitt Niederstetten – Schrozberg korrekt durchgeführt hätte.
- only summaries: Der Unfall wurde unvermeidlich, als er das Ersatzsignal betätigte und dadurch RE 19534 die Erlaubnis gab, auf die von RE 19533 belegte Strecke auszufahren.
- only summaries: Offenbar war dem Fahrdienstleiter die Tatsache, dass sich als Besonderheit seines Stellwerks ein Ausfall des Vorsignalwiederholers auf die selbsttätige Fahrstraßenauflösung auswirkt, nicht geläufig.
- only summaries: Dies lässt auf eine unzureichende Aus- bzw.
- only summaries: Weiterbildung schließen, zumal in keiner dem Fahrdienstleiter zugänglichen Unterlage, auf diesem Umstand hingewiesen wird.
- only summaries: Bemerkenswert ist weiterhin der Umstand, dass der Projektierungsfehler, der bei einer Störung der selbsttätigen Fahrstraßenauflösung das manuelle Vorblocken des Streckenblocks verhindert (vgl.
- only summaries: Ziff 3.2.3.1) offenbar seit Bestehen des Stellwerks nicht bemerkt, sondern durch nicht regelkonformes Handeln der Fahrdienstleiter kompensiert wurde.

</details>

<details><summary>008.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der Triebfahrzeugführer des RE 3560 konnte den Zusammenprall mit dem Lkw nicht abwenden, weil er das Hindernis in seinem Fahrweg erst aus einer Entfernung von ca. 180 Meter erkannt haben dürfte.
- only Nova-2-Lite: Eine Schnellbremsung bewirkte lediglich eine marginale Reduzierung der Geschwindigkeit von 156 km/h auf 150 km/h.
- only Nova-2-Lite: Unterstellt man eine Reaktionszeit von ca. 1 Sekunde, so hat der Triebfahrzeugführer den US-LKW etwa 4 Sekunden und ca. 170 Meter vor dem Zusammenprall als Hindernis auf dem BÜ erkannt und eine Schnellbremsung eingeleitet, die etwa 130 m vor dem Kollisionspunkt wirksam wurde.
- only summaries: Unterstellt man eine Reaktionszeit von ca 1 Sekunde, so hat der Triebfahrzeugführer den US-LKW etwa 4 Sekunden und ca. 170 Meter vor dem Zusammenprall als Hindernis auf dem BÜ erkannt und eine Schnellbremsung eingeleitet, die etwa 130 m vor dem Kollisionspunkt wirksam wurde.
- only summaries: Eine nennenswerte Geschwindigkeitsabsenkung konnte er hierdurch nicht mehr erreichen.
- only summaries: Durch Funkenbildung nach dem Zusammenprall entzündete sich das Kraftstoffgemisch und setzte den teilweise zerstörten Triebzug in Brand.

**Ursachen des Unfalls**

- only summaries: Nach Auswertung der sichergestellten Daten aus dem Rechner der Bahnübergangssicherungsanlage kann ein technisches Versagen als Unfallursache ausgeschlossen werden.
- only summaries: Der Ausbruch des Brandes und dessen außergewöhnlich schnelle Ausbreitung sind auf die Betriebsstoffe des LKW (Kraftstoffvorrat und Hydrauliköl) zurückzuführen, die sich feinst verteilt auf dem Triebzug niedergeschlagen und wie ein Brandbeschleuniger gewirkt haben.

</details>

<details><summary>009.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 20. November 2006 kam es um 10:23 Uhr auf dem S-Bahnhof Berlin-Südkreuz (Vorort) zu einem Zusammenstoß zwischen dem Zug S 25066 und dem Ultraschallschienenprüfzug Dsts(B) 96114. Der Ultraschallschienenprüfzug stand zum Zeitpunkt des Unfalls auf dem Bahnsteiggleis 502 des S-Bahnhofs Berlin-Südkreuz (Vorort) vor dem Halt zeigenden Zwischensignal 276. Der Zug S 25066 befand sich auf der Fahrt von Teltow nach Hennigsdorf über Berlin-Südkreuz (Vorort), Gleis 502. Trotz eingeleiteter Betriebs- und Schnellbremsung überfuhr er unzulässig das Halt zeigenden Zwischensignal 278 und prallte mit ca. 37 km/h auf den stehenden Ultraschallschienenprüfzug auf.
- only summaries: Ursache für den Zusammenstoß der beiden Züge war die ungenügende Bremswirkung des Zuges S 25066.
- only summaries: Trotz eingeleiteter Betriebs- und Schnellbremsung überfuhr er unzulässig das Halt zeigende Zwischensignal 278 und prallte mit ca. 37 km/h auf den stehenden Ultraschallschienenprüfzug auf.
- only summaries: Im S-Bahnhof Berlin-Südkreuz (Vorort) fuhr der Zug S 25066 unzulässig am Halt zeigenden Zwischensignal 278 vorbei in das besetzte Bahnhofsgleis 502 und prallte um 10:23 Uhr auf den dort stehenden Ultraschallschienenprüfzug Dsts(B) 96114.

**Ursachen des Unfalls**

- only Nova-2-Lite: Ursache für den Zusammenstoß der beiden Züge war die ungenügende Bremswirkung des Zuges S 25066. Ein stark verringerter Reibwert zwischen Rad und Schiene durch Feuchtigkeit und Schmutz, die fehlende Information für den Triebfahrzeugführer über verminderte Reibwerte in Folge des vorauffahrenden Ultraschallschienenprüfzuges, ein für diesen Schienenzustand nicht optimierter Gleitschutz der Bremse und die ungenügende Wirkung der Sandstreuereinrichtungen des Triebzuges der S-Bahn Berlin GmbH haben maßgeblich zu der unzureichenden Bremswirkung beigetragen.
- only summaries: Ein stark verringerter Reibwert zwischen Rad und Schiene durch Feuchtigkeit und Schmutz, die fehlende Information für den Triebfahrzeugführer über verminderte Reibwerte in Folge des voraufahrenden Ultraschallschienenprüfzuges, ein für diesen Schienenzustand nicht optimierter Gleitschutz der Bremse und die ungenügende Wirkung der Sandstreuereinrichtungen des Triebzuges der S-Bahn Berlin GmbH haben maßgeblich zu der unzureichenden Bremswirkung beigetragen.
- only summaries: Verursacht wurde die ungenügende Bremswirkung durch einen stark verminderten Haftwert Rad-Schiene infolge der kontinuierlichen Befeuchtung durch den Ultraschallschienenprüfzug in Verbindung mit der auf den Schienen vorhandenen Schmutzschicht.
- only summaries: Der Triebfahrzeugführer des Zuges S 25066 hatte keine Kenntnis über den verminderten Haftwert im Streckenabschnitt Berlin-Priesterweg – Berlin-Südkreuz (Vorort).
- only summaries: Aufgrund der geringen Füllung der Sandbehälter war die Funktionsfähigkeit der Sandstreuereinrichtungen der Fahrzeuge des Zuges S 25066 nicht in vollem Umfang gegeben.
- only summaries: Die Konzeption der Bremsanlage der Fahrzeuge der BR 481 erfüllt bei niedrigen Haftwerten weder mit der Betriebsbremse noch mit der Schnellbremse die aus dem UIC Merkblatt 541-05 abgeleiteten Bedingungen.

</details>

<details><summary>012.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Von den entgleisen Wagen waren 4 umgestürzt.
- only Nova-2-Lite: Die Unfallstelle befindet sich auf einer über die Bundesautobahn (BAB 93) München - Kufstein führenden Brücke.
- only summaries: Danach verspürte er etwa bei km 10,9 einen Schlag, leitete eine Betriebsbremsung ein und setzte einen Notruf ab.
- only summaries: Von den entgleisten Wagen waren 4 umgestürzt.
- only summaries: Zum Zeitpunkt des Unfalles herrschte strenger Frost von minus 12 Grad Celsius.

**Ursachen des Unfalls**

- only summaries: Nach dem Erreichen einer kritischen Risstiefe von ca. 17 mm erfolgte bei der Zugüberfahrt der Restgewaltbruch.
- only summaries: Die Zugspannungen aufgrund der niedrigen Außentemperaturen begünstigten das Rissverhalten.
- only summaries: Inwieweit hierbei auch das Durchbiege- und Endtangentenwinkelverhalten der Brücke im Übergangsbereich und der hierdurch ggf. erzeugten Erhöhung der Zugspannung mit ursächlich zum Schienenbruch beigetragen haben könnte ist mit vertretbarem Aufwand nicht abklärbar.
- only summaries: Ursächlich für die Entgleisung des Güterzuges ist ein Schienenbruch, der bereits vor dieser Zugfahrt vorhanden war und schon Schäden an der voraufahrenden RB 30116 verursachte.

</details>

<details><summary>015.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 16.04.2009 kam es um ca. 22:17 Uhr auf dem Bahnhof Berlin-Karow im durchgehenden Hauptgleis (Gleis 2) zu einem Zusammenstoß zwischen dem in Richtung Berlin-Blankenburg durchfahrenden Zug RE 38399 und dem ebenfalls in Richtung Berlin-Blankenburg ausfahrenden Zug FTZ 53185. Der FTZ 53185 war nach Fplo 10710 nur an diesem Tag in Richtung Berlin-Blankenburg durchfahrend.
- only Nova-2-Lite: In der Folge kam es zur Entgleisung des zweiten Drehgestells vom Triebfahrzeug 114 019 und des 1. Wagens des RE 38399.
- only summaries: Bei Annäherung an das Einfahrsignal des Bahnhofs Berlin-Karow um ca. 22:11 Uhr erkannte der Triebfahrzeugführer des FZT 53185, dass der Regelfahrweg und nicht der gemäß Fplo 10710 vorgegebene Fahrweg eingestellt war und teilte dies dem Fahrdienstleiter per Zugfunk mit.
- only summaries: Der Fdl löste die Fahrstraße hinter dem Asig 132 auf, obwohl der Güterzug FTZ 53185 nicht zum Halten gebracht wurde.
- only summaries: Das Einfahrsignal 136 wurde also erneut auf Fahrt gestellt und das Ausfahrsignal 132 stand noch für den FTZ 53185 auf Fahrt.
- only summaries: Diese Fahrtstellungen werden aber nun vom folgenden RE 38399 aufgenommen, der auf Grund der Signalisierung Hl 1 am Einfahrtsignal 136 und der Signalisierung Hl 1 am Ausfahrtsignal 132 seine Geschwindigkeit erhöht, bis er etwa in Höhe des Ausfahrtsignals eine Schnellbremsung einleitete, da er den Zugschluss eines Kesselwagenzuges bemerkte.
- only summaries: Kurz darauf kam es zum Zusammenstoß des mit ca. 80 km/h auffahrenden RE 38399 auf den mit ca. 41 km/h ausfahrenden Zuges FZT 53185.

**Ursachen des Unfalls**

- only summaries: Darüber hinaus hat die Änderung der sicherungstechnischen Anlage in Bezug auf den Signalhaltfall des Ausfahrtsignals 132 den Zusammenstoß begünstigt.

</details>

<details><summary>023.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 29.01.2011 kam es um ca. 22:28 Uhr in der Überleitstelle Hordorf im Bahn-Kilometer 42,702 zur frontalen Zugkollision zwischen dem in Richtung Oschersleben fahrenden Güterzug DGS 69192 und dem in Richtung Halberstadt fahrenden Personenzug DPN 80876.
- only Nova-2-Lite: Der Personenzug DPN 80876, VT 810 des Typs LINT 41, wurde durch den Güterzug DGS 69192 in Richtung Oschersleben geschoben und entgleiste vollständig.

**Ursachen des Unfalls**

- only Nova-2-Lite: Die Untersuchung unmittelbar nach Eintreffen an der Unfallstelle, ca. 04:00 Uhr, ergab, dass die vom Güterzug DGS 69192 passierten Signale, das Vorsignal Vb, „Halt erwarten“ und das Blocksignal, „Halt“(Hp0), zeigten und die Weiche 1 in der Überleitstelle Hordorf aufgefahren wurde.
- only Nova-2-Lite: Bei Strecken, auf denen technisch wirkende Zugbeeinflussungssysteme gemäß § 15 EBO nicht explizit gefordert werden, obliegt die Sicherheitsverantwortung hinsichtlich unzulässiger Vorbeifahrt an Halt zeigenden Signalen ausschließlich unter der menschlichen Sicherheitsverantwortung des Triebfahrzeugführers.
- only summaries: Die nach dem Ausschlussverfahren durchgeführte Untersuchung lässt keinen anderen Schluss zu, da keine belastbaren Hinweise auf weitere Ursachen ermittelt werden konnten.

</details>

<details><summary>032.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Nach dem ein Pkw-Fahrer vor dem BÜ (Bauart: RBÜT-LZH-Fü) als erstes Fahrzeug anhielt (Lichtzeichen blinkte, Schranken noch geöffnet) fuhr ein Transporter auf diesen auf und schob den Pkw in den Gleisbereich.
- only Nova-2-Lite: Die Schranken wurden für die Zugfahrt geschlossen.
- only Nova-2-Lite: Der Zug RE 3736 (Chemnitz Hbf - Leipzig Hbf) prallte nun auf dem gesicherten Bahnübergang auf den unbesetzten Pkw.

</details>

<details><summary>034.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Durch Arbeiten der DB-Telematik im Stellwerksraum (Auswechslung des GSM-R Gerätes) sei er abgelenkt worden und habe vergessen, die Gleissperrung im Fernsprechbuch einzutragen.
- only summaries: Die Gruppe führte in planmäßigen Zugpausen Schweißarbeiten an der Weiche W 227 aus.
- only summaries: Der Fahrdienstleiter erteilte die Zustimmung zur Fahrt für ICE 75, obwohl die Weiche 227 noch gesperrt war.

**Ursachen des Unfalls**

- only summaries: Es handelt sich um ein Fehlverhalten des Fahrdienstleiters durch Verstoß gegen Ril 408.0472 Abschn. 5 und Abschn. 8.

</details>

<details><summary>042.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 13.01.2012 um 17:40 Uhr kollidierte der Zug DPN 81817 mit einer auf dem Gleis befindlichen Rinderherde im km 188,300.
- only Nova-2-Lite: Die Rinderherde ist nach der Untersuchung der Spuren am Bahnübergang im km 189,076 auf das Gleis gelangt.
- only Nova-2-Lite: Der Zug DPN 81817 war auf der Fahrt von Westerland nach Hamburg (Streckennummer 1210) und befand sich im Streckenbereich Stedesand – Langenhorn in der Nähe der Ortschaft Bargum.
- only Nova-2-Lite: In diesem Streckenbereich beträgt die zugelassene Geschwindigkeit 140 km/h.
- only Nova-2-Lite: Der Personenzug der Nord-Ostsee-Bahn GmbH, bestehend aus sechs Wagen plus Triebfahrzeug, fuhr mit dem Steuerwagen voraus.
- only Nova-2-Lite: In der Dunkelheit konnte der Triebfahrzeugführer die Rinderherde nicht rechtzeitig erkennen.
- only Nova-2-Lite: Trotz eingeleiteter Schnellbremsung konnte die Kollision nicht verhindert werden.
- only summaries: Die Rinderherde ist aus einem Stall entlaufen und unmittelbar in der Nähe des Bahnüberganges Sande, im km 189,076 auf die Gleise geraten und hat sich dann entlang der Gleise in Fahrtrichtung Hamburg bewegt.
- only summaries: Auf Grund der Dunkelheit wurden die Tiere durch den Triebfahrzeugführer nicht rechtzeitig erkannt.
- only summaries: Trotz eingeleiteter Schnellbremsung konnte eine Kollision mit der Rinderherde nicht verhindert werden.
- only summaries: Am 13.01.2012 um 17:40 Uhr kollidierte der DPN 81817, trotz eingeleiteter Schnellbremsung, auf der Fahrt von Westerland nach Hamburg im km 188,300 zwischen Stedesand und Langenhorn mit einer Rinderherde.
- only summaries: Der Steuerwagen kippte um und blieb neben dem Gleis liegen, der zweite Wagen entgleiste ebenfalls.

**Ursachen des Unfalls**

- only summaries: Sowohl die technische Untersuchung der beteiligten Fahrzeuge, als auch die Auswertung des Fahrtverlaufes lassen den Schluss zu, dass die Ursache für die Zugkollision nicht auf Mängel aus dem Eisenbahnbetrieb zurück zuführen sind.

</details>

<details><summary>047.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: + Aufgrund eines Blitzeinschlages am Vortag des Ereignisses waren die Gleisfreimeldeanlage sowie die Bahnübergangssicherungsanlage an der Gorkistraße gestört, so dass die S 25068 (Linie S 25) im Bf Berlin-Tegel von Gleis 1 auf Signal Zs 1 und mit Befehl 8 zur Sicherung des BÜ Po 5 in Richtung Bf Hennigsdorf ausfuhr.
- only Nova-2-Lite: + Nach dem Befahren der Weiche 74 mit den ersten beiden Wagen lief die Weiche 74 unter dem fahrenden Zug vollständig in die Linkslage um, wodurch die nachfolgenden Fahrzeuge in Richtung Stumpfgleis der Niederbarnimer Eisenbahn abgeleitet wurden.
- only Nova-2-Lite: + Dabei entgleisen der 3. und 4. Wagen des Zuges mit allen Radsätzen und der 3. Wagen kam in Schräglage.
- only Nova-2-Lite: + Der Triebfahrzeugführer setzte einen Notruf ab.
- only summaries: Dabei entgleisten der 3. und 4. Wagen des Zuges mit allen Radsätzen und der 3. Wagen kam in Schräglage.

**Ursachen des Unfalls**

- only Nova-2-Lite: + Ursächlich für die Entgleisung im Gleis 1 des Bahnhofs Tegel war das Umstellen der Weiche 74 unter dem S-Bahnzug S25068.
- only Nova-2-Lite: + Die Weiche 74 wurde von der Spitze aus befahren und unter dem fahrenden Zug umgestellt.
- only Nova-2-Lite: + Da die Zugfahrt nicht auf Signal erfolgte, sondern auf Ersatzsignal – Zs1 –, wegen gestörter Gleisfreimeldeanlage, war für die Auflösung des Zuges die Mitwirkung des Fahrdienstleiters erforderlich.
- only Nova-2-Lite: + Im Nachweis der Zählwerke des Stellwerks Tegel fehlten sehr viele Eintragungen.
- only Nova-2-Lite: Der letzte Eintrag war um 04.39 Uhr für das Bedienen des Zs 1 am Einfahrsignal T mit Hinweis auf die Störung Nr. 49 vom Vortag vorgenommen worden.
- only Nova-2-Lite: Weitere Hilfsbedienungen nachweispflichtiger Einrichtungen waren nicht vorhanden, so dass kein Abgleich auf die erfolgten nachweispflichtigen Bedienhandlungen in Bezug auf die Zugentgleisung der S-Bahn S 25068 möglich war.

</details>

<details><summary>055.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 26.07.2012 kam es im Bahnhof Hosena auf der Weiche 48, die sich etwa in Höhe des Weichenwärterstellwerkes W3 befindet, zu einer Zugkollision des DGS 49325 mit dem DGS 92505. Dabei fuhr der aus Richtung Schwarzkollm kommende DGS 49325, dem aus Richtung Schwarzbach kommenden und nach Gleis 1West (Hosena Nord) einfahrenden DGS 92505 im letzten Zugdrittel in die Flanke.
- only Nova-2-Lite: Der Fahrdienstleiter erhielt einen Anruf und hörte eine männliche Stimme, die sagte „nicht bremsen, ich kann nicht bremsen, Signal halt“.
- only Nova-2-Lite: Kurz darauf hörte er ein dumpfes lautes langgezogenes Geräusch und eine Staubwolke stieg in die Höhe.
- only summaries: Durch die Flankenfahrt des DGS 49325 entgleisen mehrere Güterwagen sowie das Triebfahrzeug des DGS 49325.
- only summaries: Einige entgleiste Güterwagen kollidierten mit dem Weichenwärterstellwerk W 3 und brachten das Stellwerk zum Einsturz.

**Ursachen des Unfalls**

- only summaries: Es ist davon auszugehen, dass das vorhandene Zeitfenster von ca. 20 - 25 min zur Durchführung dieser nicht ausreichend war, da zur Bewältigung der Strecke von 3.300 m eine Gehgeschwindigkeit von mehr als 2,75 m/s (9,9 km/h) – 2,22 m/s (7,922 km/h) erforderlich wäre.

</details>

<details><summary>082.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 11.04.2014 gegen 20:29 Uhr entgleiste die Zugfahrt S 39198 auf der spitz befahrenen Weiche 7 des Bahnhofs (Bf) Forchheim.
- only Nova-2-Lite: Das erste Drehgestell des Triebwagens der S 39198 (Baureihe 442) befuhr die Weiche 7 in der Stellung Fahrweg nach rechts, das zweite Drehgestell des Triebwagens befuhr die Weiche 7 in der Stellung Fahrweg nach links.
- only Nova-2-Lite: Dieser zweispurige Lauf führte zur Entgleisung des zweiten Drehgestells mit beiden Achsen.
- only summaries: Zug 39198 befuhr nach Entgleisungslage die Weiche 7 während eines Umstellvorgangs.
- only summaries: Das erste Drehgestell (DG) des Triebwagens der S-Bahn wurde auf der Weiche 7 bei Stellung Fahrweg nach rechts in Richtung Weiche 8/Gleis 4 oder 5 und das zweite Drehgestell nun bei Stellung Fahrweg nach links in Richtung Weiche 1801/Gleis 3 geführt.
- only summaries: Durch das Umlaufen der Weiche 7 unter dem Zug kam es zu einem zweispurigen Lauf mit der Folge einer Entgleisung der Zugfahrt, das zweite Drehgestell entgleiste mit beiden Achsen.
- only summaries: Es ist wahrscheinlich anzunehmen, dass der Fdl Forchheim die Fehlleitung nach Zulassung der Einfahrt 39198 auf Ersatzsignal bemerkte und die Weiche 7 dabei noch nicht als belegt angezeigt war.
- only summaries: Unzulässig entschied sich der Fdl die Weiche 7 umzustellen, um die S-Bahn doch noch in das vorgesehene Gleis 3 einzufahren.

**Ursachen des Unfalls**

- only summaries: Die Ausgangsfehler für die Entgleisung der auf Ersatzsignal in den Bf Forchheim einfahrenden S-Bahn 39198 liegen in der mangelhaften Einstellung, Prüfung und Sicherung des Fahrweges durch den FdI.
- only summaries: Deswegen wurde die unzulässige Umstellung der spitz befahrenen Weiche 7 unmittelbar vor (Stellbefehl erteilt) bzw. unter dem Zug (zwischen Drehgestell 1 und Drehgestell 2) überhaupt erst möglich.
- only summaries: Fest steht, dass er fehlerhaft reagierte und nach Zustimmung zur Zugfahrt mit Signal Zs 1 unzulässig versuchte, durch Umstellen der Weiche 7, die S-Bahn 39198 doch noch in das vorgesehene und einzig zulässige Einfahrgleis 3 zu leiten – diese falsche Reaktion ist der eigentliche Auslöser der Entgleisung.
- only summaries: Ursache des Unfalls sind Arbeitsfehler des diensthabenden FdI Bf Forchheim.
- only summaries: Zu berücksichtigen sind hierbei jedoch sicherlich folgende Aspekte:
- only summaries: evtl. mangelhafte Ausbildung bzw. mangelnde Praxis (die Grundkenntnisse und Erfahrungen für ein FdI waren sicher gegeben, ob die Einarbeitungszeit für die tech- nisch andere und praktisch neue Stellwerksform Gleisbildstellwerk ausreichend war, kann nicht beurteilt werden);
- only summaries: andere Art der Fahrweineinstellung, -prüfung und insbesondere -sicherung als auf einem mechanischen Stellwerk;
- only summaries: Zeitdruck (obwohl die beiden bisher betroffenen Züge 87092 und 39198 nur eine Verspätung von ca. 6 Minuten erhielten bzw. erhalten hätten, war der Fdl sicher bemüht die Verzögerungen zu minimieren);
- only summaries: Panik (plötzliches, aber zu spätes Erkennen, dass der Zug einen unzulässigen Fahrweg nimmt und untauglicher Versuch, diesen Fehler zu beheben);
- only summaries: Probleme bei der Umsetzung der Vorgaben/Vorschriften (wie die vorstehende Untersuchung belegt, sind die zu beachtenden Regeln in mehreren Ril und dort an verschiedenen Stellen verteilt, aber im Zusammenhang vom Fdl anzuwenden);
- only summaries: Belastung aus der Störung der Stellwerkstechnik;
- only summaries: alle diese Faktoren dürften zu Stress beim Fdl geführt haben (ob die falsche Ortsangabe im schriftlichen Befehl für Zug 87092 bereits als Anzeichen dafür zu werten ist, sei dahin gestellt).

</details>

<details><summary>089.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 09.06.2013 um 5:21 Uhr entgleist DGS 90636 zwischen den Betriebsstellen Kaub und Lorch (Rhein) Üst mit dem am Schluss des Zuges laufenden Autotransportwagen 23-88 4371 080-3 in km 76,950. Im Verlauf der Weiterfahrt des Zuges entgleist in Folge auch der vorletzte Autotransportwagen 23 88 4371 098-5. Nachdem der Triebfahrzeugführer einen Nothaltauftrag (Kreissignal) im Bf Rüdesheim (Rh.) erhalten hat, kommt der Zug in km 65,300 im Gleis 2 des Bf Rüdesheim (Rh.) zum Halten.
- only summaries: Während der Fahrt durch die Blockabschnitte der freien Strecke, der Überleitstelle sowie der Weichen und Gleisabschnitte des Bf Assmannshausen werden zahlreiche technische Einrichtungen an Weichen, Signalanlagen und oberbautechnischen Einrichtungen beschädigt bzw. zerstört.

**Ursachen des Unfalls**

- only summaries: Ursächlich für die Entgleisung sind mehrere kurz hintereinander liegende zyklische Gleislagefehler in der Längshöhe.
- only summaries: Ausgehend durch eine gestörte Entwässerung des Gleiskörpers kommt es abschnittsweise zu Einschränkungen bei der Tragfähigkeit des Gleisunterbaus, woraus sich durch den Eisenbahnverkehr Gleislagefehler entwickeln.
- only summaries: Die Autotransportwagen der Gattung Laaers konnten diese zusätzlich wirkenden Anregungen aus den vorgefundenen Gleislagefehlern bei der gefahrenen Geschwindigkeit von 104 km/h aufgrund ihrer konstruktiven Eigenschaften nicht mehr ausreichend kompensieren und entgleisen.
- only summaries: Eine weitere Ursache ist in der Fehleinschätzung des Gefahrenpotentials der aufgetretenen kombinierten (zyklischen) Einzelfehler durch das Inspektionspersonal und der fehlenden Regelung mit deren Umgang in den Richtlinien der DB AG zu sehen.

</details>

<details><summary>096.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 01.02.2017 gegen 22:54 Uhr fuhr ICE 1522 des Eisenbahnverkehrsunternehmens (EVU) DB Fernverkehr AG in das durch Zug DPN 32490 des EVU National Express Rail GmbH besetzte Gleis 507 des Bf Gruiten ein.
- only Nova-2-Lite: Der für den Bahnhof Gruiten zuständige Fahrdienstleiter (Fdl) hat vor Einfahrt des ICE nach Gleis 507 eine von der Gleisfreimeldeanlage angezeigte Besetztanzeige mittels Achszählergrundstellungstaste (AzGrT) Bedienung aufgehoben, ohne die vorgeschriebene betriebliche Voraussetzung, den betroffenen Abschnitt durch Hinsehen zu prüfen, erfüllt zu haben.
- only Nova-2-Lite: Anschließend stimmte der Fdl der Einfahrt des ICE 1522 durch Fahrstellen des Einfahrsignals (Esig) B517 zu.
- only Nova-2-Lite: Einen Auftrag zum Fahren auf Sicht hatte der Tf des ICE nicht erhalten.
- only Nova-2-Lite: Der Tf des ICE nahm daraufhin die Fahrt wieder auf und beschleunigte seinen Zug.
- only summaries: Der hintere, abgekuppelte Zugteil, verblieb in Gleis 507.
- only summaries: Nachdem der Fdl den an seinem Stw vorbeifahrenden DPN 32486 mit Zugschlussignal beobachtet hatte, stellte er fest, dass die Rotaulichtung für Gleis 507 auf seiner Stelltafel nach wie vor vorhanden war.
- only summaries: Aus diesem Grund deutete er die nach wie vor vorhandene Rotaulichtung als Störung und stellte mittels Bedienung der AzGrt die Grundstellung der Gleisfreimeldeeeinrichtung her.
- only summaries: Anschließend stellte er die Fahrstraße für den wartenden ICE 1522 ein und brachte das Esig B517 in die Fahrtstellung.
- only summaries: Der ICE kam etwa 15 m vor dem Zugschluss des DPN 32490 zum Stillstand.

**Ursachen des Unfalls**

- only summaries: Die unzulässige Einfahrt des ICE 1522 in das besetzte Gleis 507 des Bf.
- only summaries: Gruitens wurde durch den für Gruitens zuständigen Fdl in Wuppertal-Vohwinkel verursacht.
- only summaries: Seinen Ausgangspunkt nahm die betriebliche Fehlhandlung in der Tatsache, dass der Fdl über die Fahrplanregelungen zu Betra F325016 17 / F325023 17 nicht informiert war.
- only summaries: Anhand der vom Fdl geführten GSM-R – Gespräche steht für die BEU fest, dass der Fdl keine Kenntnis von der geplanten Zugtrennung des 32486 in Gruitens hatte.
- only summaries: Warum diese wichtigen Fahrplanregelungen den Fdl nicht erreichten, konnte auch durch die DB Netz AG im Rahmen ihrer Untersuchungen nicht geklärt werden.
- only summaries: Möglicherweise kannte der Fdl diese Bestimmung in seinem Betriebsstellenbuch nicht.
- only summaries: Möglicherweise war aber auch der Fdl von seinem Handeln so überzeugt, dass das Beobachten des Zugschlusses und damit das mittelbare Feststellen das richtige Mittel zur Lösung seines Problems sei und er deshalb andere Möglichkeiten gar nicht in Erwägung zog.

</details>

<details><summary>101.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Gegen 15:47 Uhr meldete sich der Triebfahrzeugführer der RE 21073 über Zugfunk beim Fahrdienstleiter Pinneberg und teilte ihm mit, dass vor ihm ein anderer Zug (DPN 83825) stünde.
- only summaries: Die nachfolgenden 4 Zugfahrten in Richtung Hamburg-Eidelstedt wurden auf Ersatzsignal durchgeführt.

**Ursachen des Unfalls**

- only Nova-2-Lite: Bei Störzuständen an Blockeinrichtungen ist laut Regelwerk der DB AG (Ril 408) der Block zu sperren und die Einzelräumungsprüfung durchzuführen.
- only Nova-2-Lite: Im vorliegenden Fall geschah weder die Sperrung, noch die Kontaktaufnahme oder diese Dokumentation in vorgeschriebener Art und Weise.
- only Nova-2-Lite: Die im Rahmen der Untersuchung gewonnenen Erkenntnisse lassen erkennen, dass trotz längerer Einweisung und mehrerer Einsätze auf der Örtlichkeit keine Handlungssicherheit bei Störungen bestand.
- only summaries: Im Rahmen der Unfalluntersuchung wurde deutlich, dass es zu fehlerhaften fahrdienstlichen Bedienhandlungen kam, insbesondere ab dem Zeitpunkt, zu dem die Stellwerkstechnik in den signaltechnisch sicheren Störbetrieb ging.
- only summaries: Erforderliche betriebliche Ersatzmaßnahmen, beispielsweise die Einzelräumungsprüfung, wurden nicht durchgeführt.
- only summaries: Auf die vorgeschriebenen Handlungen im Fehlerfall laut Ril 408 bzw.
- only summaries: Ril 482 (genaue Bezeichnung der relevanten Kapitel siehe voranstehende tabellarische Darstellung) wurde verzichtet.
- only summaries: Der Fdl orientierte sich hinsichtlich des Standortes der Züge vermutlich unzulässiger Weise an der Zugnummernmeldeanlage, um den Raumabstand zwischen den Zügen zu gewährleisten.
- only summaries: Mit dem Ausfall dieser Anlage (gegen 15:20 Uhr), der in keinem technischen Zusammenhang mit der Störung der Achszählanlagen stand, verlor dieser vermutlich den Überblick über die Standorte der jeweiligen Züge und den Besetztzustand der gestörten Blockabschnitte, vergaß den vor Sbk 34 stehenden Zug DPN 83825 und ließ Zug RE 21073 in den Abschnitt ein.
- only summaries: Es war ein starker Mangel in der Handlungssicherheit hinsichtlich der Durchführung betrieblicher Ersatzmaßnahmen erkennbar, ebenso wie ein fehlerhafter Umgang mit der Dokumentation durchgeführter Hilfsbedienungen.
- only summaries: Dies äußert sich in einer Vielzahl nicht dokumentierter Hilfsbedienungen und dem unzureichenden Führen relevanter, für einen sicheren Bahnbetrieb notwendiger Unterlagen (Arbeits- und Störungsbuch, Zugmeldebuch).
- only summaries: Eine, auf fundiertem Wissen aufsetzende Kenntnis der betrieblichen Abläufe hätte die notwendige Handlungssicherheit gegeben, um auch im Störungsfall den Eisenbahnbetrieb sicher durchführen zu können und hätte dadurch zur Vermeidung des Ereignisses beigetragen.

</details>

<details><summary>114.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 09.02.2016 gegen 06:47 Uhr kollidierten auf der Bahnstrecke Holzkirchen–Rosenheim zwischen den Bf Bad Aibling und Kolbermoor die Züge DPN 79505 und DPN 79506 der Bayerischen Oberlandbahn etwa in Streckenkilometer 30,29.
- only Nova-2-Lite: Der Zug DPN 79505 (Elektrotriebzug (ET) 325) kam von München über Holzkirchen mit Fahrtziel Rosenheim, der von Rosenheim kommende Zug DPN 79506 (ET 355) fuhr in Richtung Holzkirchen.
- only Nova-2-Lite: Die beiden an jenem Faschingsdienstag nur schwach besetzten Züge sollten planmäßig in Kolbermoor kreuzen.
- only Nova-2-Lite: Der Fdl Bad Aibling hatte irrtümlich angenommen, die Kreuzung finde in Bad Aibling statt.
- only Nova-2-Lite: Er stellte die Ausfahrtzugstraße für DPN 79506 aus Kolbermoor nach Bad Aibling mit Ersatzsignal (Zs 1) ein, obwohl die Zugstraßen sich ausschlossen.
- only Nova-2-Lite: Dies führte dazu, dass beide Züge gleichzeitig die Strecke befahren konnten.
- only summaries: Am 09.02.2016 kollidierten gegen 06:47 Uhr auf der Bahnstrecke Holzkirchen–Rosenheim (Streckennummer 5622) zwischen den Bahnhöfen (Bf) Bad Aibling und Kolbermoor die beiden Züge DPN 79505 und DPN 79506 der Bayerischen Oberlandbahn (BOB) etwa in Streckenkilometer 30,29.
- only summaries: Der Fdl wich vom Fahrplan für die Zugmeldestelle ab (vorgegeben war für Zug 79505 im Bf Bad Aibling das Gleis 1), da er irrtümlich von einer Kreuzung mit Zug 79506 in Bad Aibling ausging.
- only summaries: Der Fdl erkannte nach 06:38 Uhr, dass die Kreuzung der Züge 79505 und 79506 planmäßig im Bf Kolbermoor erfolgen sollte und nahm für Zug 79505 einen Fahrstraßenwechsel für die Einfahrt in den Bf Bad Aibling vor.
- only summaries: Da der Fdl die Festlegung des Zb nicht hilfsauflösen konnte und eine signalmäßige Ausfahrt für Zug 79505 in Bad Aibling weiterhin nicht stellbar war, ging er von einer vermeintlichen Störung aus.
- only summaries: Die Zugkollision der beiden Regionalzüge ist auf mehrere betriebliche Fehlhandlungen des Fdl Bad Aibling bei der Abwicklung der Fahrten auf Ersatzsignal (Zs 1) zurückzuführen.
- only summaries: Wie durch die Gesprächsaufzeichnungen belegt und auch aus dem folgenden Gesprächsprotokoll ersichtlich, wurden die Tf nicht erreicht, da der Fdl anstelle von „Zugfunknotruf“ zweimal „Notruf-Strecke“ auslöste.
- only summaries: Kollision der beiden Züge am 09.02.2016 gegen 06:47 Uhr.

**Ursachen des Unfalls**

- only Nova-2-Lite: Zudem beeinflusste die Handhabung der Bedieneinrichtung des Notrufsystems durch den Fdl die Zugkollision.
- only summaries: Zudem beeinflusste die Handhabung der Bedieneinrichtung des Notrufsystems durch den Fdl die Zugkollision und deren Folgen.
- only summaries: Das unregelmäßige Beschäftigen mit dem Handy als aktivierende Maßnahme ist zu unterscheiden von einer Handy- bzw.
- only summaries: Es wird davon ausgegangen, dass die Handnutzung direkt beitragend zu dem dispositiven Fehler der fälschlichen Einstellung der Fahrstraße nach Gleis 2 anstelle von Gleis 1 war.
- only summaries: Es wird eingeschätzt, dass die Handnutzung indirekt dazu beigetragen hat, dass der Fdl in der weiteren Bedienung offensichtlich „vergessen“ hat, dass er bereits die Ausfahrt aus Kolbermoor eingestellt hatte.
- only summaries: Der Fdl war zum Zeitpunkt der Einstellung der Fahrstraße derart mit dem Handy beschäftigt, dass er die Handlung nicht bewusst wahrgenommen hat.
- only summaries: Es wird eingeschätzt, indirekt beeinflusst hat, dass der Fdl die Melder, die ihm die eingestellte Fahrstraße anzeigen, nicht wahrgenommen hat.
- only summaries: Der Fdl ging vorschnell von einer technischen Störung aus.
- only summaries: Er erkannte nicht die wahre Ursache (= freigegebene Ausfahrt in Kolbermoor), weshalb er das Asig in Bad Aibling und das Blocksignal 313 nicht in Fahrtstellung bringen konnte.
- only summaries: Die Regelung für Merkhinweise/Hilfssperren, die Räumungsprüfung, Blockabschnittsprüfung und Blockbedienung sind in der Handhabung nicht immer einfach und auf mehrere Ril und Abschnitte (§§ bzw.
- only summaries: Die Stelltilschanzeigen sind unzureichend.
- only summaries: Die Verwechslungsgefahr der beiden Notrufarten ist, insbesondere unter Stress, zu hoch.
- only summaries: Die Gestaltung des Notrufs auf dem Display ist ergonomisch ungünstig und begünstigt in einem tatsächlichen Notfall, bei dem der Fdl unter Stress steht, Fehlbedienungen.
- only summaries: Die Gleisausleuchtung bei einer Blockabschnittsprüfung vom Signal 313 bis Kolbermoor ist unvollständig.
- only summaries: Sowohl die Erlaubnisabgabetasten, die eine aktive Handlung des Fdl erfordern, als auch die fahrtrichtungsbezogenen Melder (Richtungspfeile) hätten das betriebliche Handeln beeinflusst und folglich zur Vermeidung des Unfalls beigetragen.

</details>

<details><summary>116.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 01.05.2017 um ca. 18:46 Uhr entgleiste Zug ICE 945 des EVU DB Fernverkehr AG bei der Einfahrt in Gleis 8 des Bf Dortmund Hbf auf Höhe der DKW 36. Gleis 8 ist ein durchgehendes Hauptgleis und gehört zur Strecke 2158. Der Zug der Baureihe ICE 2 war auf dem Weg von Düsseldorf Hbf nach Berlin Ostbahnhof.
- only Nova-2-Lite: Der nächste planmäßige Halt war Dortmund Hbf.
- only Nova-2-Lite: Der Ereignisort gehört zum Streckennetz des EIU DB Netz AG. Der Betrieb wird nach dem Regelwerk der DB AG, Richtlinie (Ril) 408 „Fahrdienstvorschrift“, durchgeführt.
- only Nova-2-Lite: Die betroffene Strecke 2158 ist als zweigleisige Hauptbahn ausgeführt.
- only Nova-2-Lite: Sowohl die freie Strecke als auch der Bf Dortmund Hbf sind mit Haupt-/Vorsignal (H/V) und punktförmiger Zugbeeinflussung (PZB) ausgerüstet.
- only Nova-2-Lite: Der Bf Dortmund Hbf beginnt am Einfahrsignal (Esig) D540 in km 163,2. Ab Esig D540 gilt bei Signalstellung Hp 1 gemäß Verzeichnis der zulässigen Geschwindigkeiten (VzG) eine zulässige Höchstgeschwindigkeit (Hg) von 80 km/h.
- only Nova-2-Lite: ICE 945 war planmäßig unterwegs und sollte laut Fahrplan um 18:46 Uhr im Bf Dortmund Hbf in Gleis 8 ankommen.
- only Nova-2-Lite: Bis zum Zeitpunkt des Ereignisses gab es keine Störungen im Betriebsablauf.
- only Nova-2-Lite: Der ICE 945 bestand aus einem TK, sechs MW und einem SW. Die Zuglänge betrug 205 m.
- only Nova-2-Lite: Laut EFR-Datenauswertung lag die Geschwindigkeit zum Zeitpunkt der Entgleisung bei ca. 70 km/h und damit unter der laut Signalisierung zulässigen Hg von 80 km/h.
- only summaries: Am 01.05.2017 entgleiste um ca. 18:46 Uhr der Zug ICE 945 des Eisenbahnverkehrsunternehmens (EVU) DB Fernverkehr AG bei der Einfahrt nach Gleis 8 des Bahnhofs (Bf) Dortmund Hauptbahnhof (Hbf) im Bereich der Doppelten Kreuzungsweiche (DKW) 36.
- only summaries: Nach der Entgleisung und der Bedienung einer Notbremse kam der Zug nach ca. 300 m mit TK und vier MW am Bahnsteig zum Halten.
- only summaries: Wegen des zweispurigen Laufs wurden der Oberbau und insgesamt acht Weichen sowie dazugehörige LST-Anlagen zerstört.

**Ursachen des Unfalls**

- only summaries: Als maßgebende Ursachen der Zugentgleisung sind die gestörte Gleislage im Einfahrbereich der DKW 36, dass daraus resultierende dynamische harte Anfahren an die linke Weichenzunge und die sich ausbildende punktuelle Verformung an der linken Zunge im gerade befahrenen Fahrweg a/c zu nennen.
- only summaries: Entstehen konnte diese punktuelle Verformung, weil das Instandhaltungspersonal zwar termingerecht inspizierte, den Verschleißzustand an der linken Zunge jedoch falsch einschätzte oder nicht erkannte.
- only summaries: Unter Einhaltung der Vorgaben aus Ril 821.2005 hätten die zur Entgleisung führenden Verschleißmängel an der Zungenspitze durch das Inspektionspersonal erkannt werden müssen.

</details>

<details><summary>123.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Zum Absetzen von Auszubildenden hielt der Zug kurzzeitig am Bahnsteig im Gleis 1 des Bahnhofs Bremen Hbf und setzte danach seine Fahrt, vorbei am Zwischensignal (Zsig) 11ZV1 durch das Gleis 201, fort.
- only Nova-2-Lite: Bei der Annäherung an das haltzeigende Ausfahrsignal (Asig) 11P1 kam der Zug erneut zum Stillstand.
- only Nova-2-Lite: Zum Zeitpunkt des Halts am Asig 11P1 befand sich der Wagen ca. in Kilometer 44,560. Nur wenige Meter davon entfernt wurden später Teile des abgescherten Radsatzlagers und Tragfedern gefunden.
- only Nova-2-Lite: Zeitgleich verständigte der technische Berechtigte der Betra F 216244, der sich zufällig im Bereich der Unfallstelle aufhielt, den örtlich zuständigen Fahrdienstleiter (FdI) des ESTW Bremen Süd über eine Unregelmäßigkeit am ausfahrenden DGS 88963. Daraufhin forderte der FdI den Triebfahrzeugführer (Tf) des DGS 88963 über GSM-R Zugfunk auf, wegen des Verdachts auf eine feste Bremse sofort anzuhalten.
- only Nova-2-Lite: Der Tf beschrieb in seiner Stellungnahme während des Gesprächs mit dem FdI das Entweichen der Luft aus der Hauptluftleitung wahrgenommen zu haben.
- only summaries: Ermittlungen ergaben, dass die Zugtrennung durch eine weitere Entgleisung des 43. Wagens maßgeblich verursacht wurde.

**Ursachen des Unfalls**

- only Nova-2-Lite: Das Radsatzlager fiel dabei ab und wurde wenige Meter nach den ersten Entgleisungsspuren rechts neben dem Gleis aufgefunden.
- only summaries: Die Zugentgleisung des DGS 88963 am 30.11.2017 wurde durch ein heiß gelaufenes Radsatzlager, ein s.g.
- only summaries: Möglicherweise wäre das Ereignis durch eine ordnungsgemäß funktionierende HOA/FBOA „Bookholzberg“, ca. 20 Kilometer vor der späteren Entgleisungsstelle, zu verhindern gewesen.
- only summaries: Es ist davon auszugehen, dass der Fdl Delmenhorst deshalb nicht alarmiert wurde, weil offensichtlich ein Fehler in der Anlagensoftware zugrunde lag.
- only summaries: Den Angaben der DB Netz AG zufolge habe die Software der Anlage den gemessenen Wert als Fehler klassifiziert und diesen auf 2° C gesetzt.
- only summaries: Ursächlich sei hierfür der s.g.
- only summaries: Sonnenklassifikator, also die Möglichkeit, die Anlagensoftware auf gewisse äußere Faktoren, wie Sonneneinstrahlung oder häufiges Bremsen der Züge zu programmieren.

</details>

<details><summary>125.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 29.06.2013 befuhr DGS 83799, bestehend aus einem Triebfahrzeug und 23 Güterwagen, auf der Fahrt von Abelitz nach Magdeburg-Rothensee die Strecke 1520 (Oldenburg – Leer).
- only Nova-2-Lite: Auch hier wurde die Fahrt im entgleisen Zustand weiter fortgesetzt.
- only Nova-2-Lite: Durch den auf mehrere Kilometer entgleisen Radsatz wurden der Oberbau und Schaltmittel am Gleis beschädigt oder zerstört, was zu betrieblichen Störmeldungen beim Fdl Oldenburg führte.
- only Nova-2-Lite: Offensichtlich sind zwischenzeitlich mehrere Stunden vergangen bis die in Oldenburg aufgetretenen Störmeldungen als Folgeschäden einer Entgleisung identifiziert und dem DGS 83799 zugeordnet werden konnten.
- only Nova-2-Lite: Nur so erklärt sich, dass erst gegen 06:30 Uhr der Zug ca. 15 km südlich von Stendal im Bf Tangerhütte zur Überprüfung gestellt wurde.
- only Nova-2-Lite: Hierbei wurde der Zug jedoch nicht vollständig untersucht, da die Untersuchung am vierten Wagen abgebrochen wurde.
- only Nova-2-Lite: Dadurch konnten die infolge der Entgleisungen entstandenen Schäden am 17. Wagen nicht festgestellt werden.
- only Nova-2-Lite: Gegen 7:48 Uhr fuhr der Zug in Tangerhütte weiter.
- only Nova-2-Lite: Eine vollständige Untersuchung des Zuges wurde erst nach der Ankunft im Bf Magdeburg-Rothensee durchgeführt.
- only Nova-2-Lite: Hierbei wurde festgestellt, dass der an 17. Stelle hinter dem Triebfahrzeug (Tfz) laufende, unbeladene Güterwagen der Gattung Uiks an dem in Fahrtrichtung ersten Radsatz Entgleisungsschäden aufwies.
- only summaries: Durch den auf mehrere Kilometer entgleisten Radsatz wurden der Oberbau und Schaltmittel am Gleis beschädigt oder zerstört, was zu betrieblichen Störmeldungen beim Fdl Oldenburg führte.

**Ursachen des Unfalls**

- only summaries: Die vorgefundene Entwässerungssituation sowie die begleitende Vegetation am BÜ und die dadurch herabgesetzte Tragfähigkeit des Gleises haben ebenfalls die Entstehung der Gleislagefehler begünstigt.
- only summaries: Jedoch erst durch die Vernachlässigung der Instandsetzungsmaßnahmen konnten sich die periodischen Gleislagefehler bei der Länghöhe derart entwickeln, dass sie letztendlich zur Entgleisung des Fahrzeugs führten.
- only summaries: Zu derartigen in annähernd gleichem Abstand auftretenden, also periodischen, Gleislagefehlern macht die Ril 821 dem ALV zum Unfallzeitpunkt allerdings keine Vorgaben, wie damit umzugehen ist.

</details>

<details><summary>126.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Es kommt zu keiner Zwangsbremsung.
- only summaries: Bei der Durchfahrt über Gleis 1 im Bf Burgstall klettert der linke Radkranz der letzten Achse, ca. in km 4,866 auf den Schienenkopf auf.
- only summaries: Dort läuft er wenige Meter auf dem Schienenkopf weiter und gleitet in km 4,872 unter Einwirkung der Kurvenfahrt nach bogenaußen endgültig auf die Betonschwellen nach links ab.

**Ursachen des Unfalls**

- only Nova-2-Lite: Bei der Durchfahrt über Gleis 1 im Bf Burgstall klettert der linke Radkranz der letzten Achse, ca. in km 4,866 auf den Schienenkopf auf.
- only Nova-2-Lite: Dort läuft er wenige Meter auf dem Schienenkopf weiter und gleitet in km 4,872 unter Einwirkung der Kurvenfahrt nach bogenaußen endgültig auf die Betonschwellen nach links ab.
- only summaries: Trotz diverser Hinweise in den vorliegenden Gutachten, wurde die flach verlegte Tiefenentwässerung nicht auf ihre Funktionstüchtigkeit untersucht, um sie gegebenenfalls zu ergänzen oder Instand zu setzen.
- only summaries: Zudem wurde entgegen der fachtechnischen Beurteilung im Gutachten auf einen baubegleitenden Bodengutachter durch die Projektleitung verzichtet.
- only summaries: Aufgrund mehrerer Baumaßnahmen, wie z.B. Fundamenterstellung sowie dem Einsatz schwerer Baumaschinen im Bf Burgstall kam es mit hoher Wahrscheinlichkeit zu Beschädigungen an der Tiefenentwässerung.
- only summaries: Die Beschädigungen an der Tiefenentwässerung blieben unerkannt, da die Überwachungstätigkeiten nicht mit der nötigen Sorgfalt und Präsenz durch die Bauüberwachung durchgeführt wurden.
- only summaries: Grundlage der Inspektion der Tiefenentwässerung bildet das Entwässerungsheft und die Generierung eines Untersuchungsauftrages aus dem SAP-System.
- only summaries: Beides war nicht vorhanden und somit konnten die aus der Baumaßnahme entstandenen Mängel nicht entdeckt werden.
- only summaries: Da die Ursache für den Gleislagefehler nicht beseitigt wurde, konnten sich diese innerhalb kurzer Zeit neu ausbilden.
- only summaries: Dies belegt, dass es sich bei der Mängelbeseitigung nicht um eine nachhaltige Instandsetzung handelte.
- only summaries: Ursache für die vorgefundenen Schlammstellen ist eine nicht funktionierende Entwässerung des Gleisbereiches.
- only summaries: Durch diese Länghöhenfehler hat die Frequenzhöhe der Erregerfrequenz (Anregungsfrequenz) der Gleisanlage bei einer Geschwindigkeit von ca. 80 km/h sich der Eigenfrequenz des Fahrzeuges Hbbill 305 soweit angenähert, dass es zu einer Anregung der Eigenfrequenz des Fz gekommen ist.

</details>

<details><summary>179.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 06.05.2015 gegen 11:09 Uhr kollidierte der ICE 728 des Eisenbahnverkehrsunternehmens (EVU) DB Fernverkehr AG, auf dem Weg von Nürnberg Hbf nach Dortmund Hbf, mit Bruchstücken der Schallabsorber der festen Fahrbahn.
- only Nova-2-Lite: Der ICE 728 befuhr den Streckenabschnitt zwischen der Üst Willroth und Siegburg/Bonn mit einer Geschwindigkeit von ca. 300 km/h fallend.
- only Nova-2-Lite: Gegen 11:09 Uhr kam es bei einer Geschwindigkeit von ca. 260 km/h in km 30,66, kurz vor dem Siegburg Tunnel, zur Kollision mit Bruchstücken der Schallabsorber der Bauart Liakustik.
- only Nova-2-Lite: Wenige Tage zuvor wurden im genannten Streckenabschnitt Schienen gewechselt.
- only Nova-2-Lite: Nach dem durchgeführten Schienenwechsel erfolgte am 04.05.2015 die oberbautechnische Freigabe durch die örtliche Bauüberwachung, obwohl die defekten Schallabsorber zuvor nicht im erforderlichen Umfang ausgetauscht werden konnten.
- only summaries: Bei mehreren dieser auf der festen Fahrbahn verbauten Schallabsorber aus Porenbeton kam es unter Einfluss der Witterung zu horizontalen Abbrüchen, die im Zusammenhang mit Gleisbauarbeiten zu weiteren vertikalen Abbrüchen führten.
- only summaries: Bei der Zugüberfahrt wurden, durch die Sogwirkung des Zuges, Bruchstücke hochgeschleudert.

</details>

<details><summary>180.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 01.06.2018 gegen 05:13 Uhr entgleiste der Zug RB-G 11301 auf der Strecke 2631 zwischen den Bahnhöfen (Bf) Kyllburg und Bitburg-Erdorf nach der Kollision mit einem Murgang am Südportal des Wilsecker Tunnels in km 128,267.
- only Nova-2-Lite: Im Tunnel habe er Äste und Gegenstände in seinem Fahrweg bemerkt.
- only Nova-2-Lite: Er habe daraufhin sofort eine Schnellbremsung eingeleitet.
- only Nova-2-Lite: Während des Bremsvorgangs habe er festgestellt, dass das Gleis unter Wasser stand.
- only Nova-2-Lite: Er sei am Tunnelausgang Richtung Bitburg-Erdorf in einer Gerölllawine zum Stehen gekommen.
- only Nova-2-Lite: Anschließend habe er einen Nothaltauftrag abgesetzt und den Zug nach Reisenden durchsucht, es seien jedoch keine anwesend gewesen.
- only summaries: Ursächlich für die Zugkollision war ein Murgang, der in Folge eines Unwetters mit zum Teil sehr starkem Niederschlag entstand und das Gleisbett am Südportal des Wilsecker Tunnels während der Nachtbetriebsruhe mit Erd- und Geröllmassen bedeckte.
- only summaries: Als der Tf innerhalb des Wilsecker Tunnels Äste und Gegenstände im Fahrweg wahrnahm, leitete er bei km 128,132 eine Schnellbremsung ein und setzte einen Nothaltauftrag ab.
- only summaries: Der Zug kam anschließend nach einem Bremsweg von 135 m um ca. 05:13 Uhr nach der Kollision mit dem Murgang und der Entgleisung der ersten vier Achsen zum Stehen.

**Ursachen des Unfalls**

- only summaries: Die in Kapitel 4.5.2 aufgezeigten Regelungen der Ril 408 zum Umgang mit gefährdrohenden Umständen hätten, bei einer entsprechenden Umsetzung, die Kollision der RB-G 11301 mit dem Murgang höchstwahrscheinlich verhindern können.
- only summaries: Aufgrund der Nachtbetriebsruhe auf dem Stw Kyllburg konnten weder der Fdl noch das Zugpersonal die in der Nacht eingetretene extreme Wettersituation wahrnehmen.
- only summaries: Die gewählten Vergleichsparameter waren somit unzureichend um die Gefährdung zu identifizieren.
- only summaries: Es ist davon auszugehen, dass für Fdl eine gewisse Hemmschwelle besteht, die Maßnahmen bei gefahrdrohenden Umständen nach Ril 408 umzusetzen.

</details>

<details><summary>181.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: + Am 18.02.2015 erhielt der Personenzug RB 58043 des Eisenbahnverkehrsunternehmens (EVU) DB Regio AG gegen 13:00 Uhr vom Fahrdienstleiter (FdI) Jossa die Zustimmung in den Zugfolgeabschnitt 941 einzufahren, obwohl dieser noch durch den Güterzug DGS 69235 des EVU boxXpress.de GmbH besetzt war.
- only Nova-2-Lite: + Der Triebfahrzeugführer (Tf) des Personenzuges RB 58043 erkannte den Güterzug DGS 69235 in seinem Fahrweg und leitete eine Schnellbremsung ein.
- only Nova-2-Lite: + Die Fdl Jossa und Burgsinn bemerkten während eines Gespräches über den digitalen Zugfunk die Fehlnummer im Zugfolgeabschnitt 941 sowie die Rotausleuchtung des entsprechenden Streckenabschnitts.
- only Nova-2-Lite: + Der Fdl Jossa ließ daraufhin, nach der Zugschlussmeldung des Fdl Burgsinn, die Fahrt der RB 58043 in den Zugfolgeabschnitt 941 durch Bedienung des Ersatzsignales zu.
- only Nova-2-Lite: + Der Tf beschleunigte den Zug im Zugfolgeabschnitt 941 auf eine Höchstgeschwindigkeit von 136 km/h.
- only Nova-2-Lite: + RB 58043 kam nach einem Bremsweg von ca. 430 m in einem Abstand von ca. 70 m vor dem bereits wieder anfahrenden DGS 69235 zum Halten.
- only summaries: Die Zugnummern der beiden Güterzüge wurden aufgrund der Fortschaltstörung weiterhin in den jeweiligen ursprünglichen Zugnummernfeldern des Bf Jossa angezeigt, obwohl sich diese bereits auf der freien Strecke befanden.
- only summaries: Einen nach Ril 408.0622 Abschn. 1 erforderlichen schriftlichen Befehl 9 zum Fahren auf Sicht diktierte der Fdl Jossa dem Tf der RB 58043 nicht.
- only summaries: Da sich der Zugschluss des DGS 69235 in einer Rechtskurve befand, erkannte der Tf der RB 58043 diesen erst spät und leitete umgehend eine Schnellbremsung ein.

**Ursachen des Unfalls**

- only Nova-2-Lite: + Im vorliegenden Fall gingen die Fdl Jossa und Burgsinn bei dem besetzten Zugfolgeabschnitt 941 irrtümlicherweise von einer Störung des selbsttätigen Streckenblocks aus.
- only Nova-2-Lite: + Einem nach Ril 408.0622 Abschn. 1 erforderlichen schriftlichen Befehl 9 zum Fahren auf Sicht diktierte der Fdl Jossa dem Tf der RB 58043 nicht.
- only Nova-2-Lite: + Durch den Befehl 9 wäre eine Einfahrt in den besetzten Zugfolgeabschnitt nicht verhindert worden, jedoch hätte sich die Gefahr einer Kollision verringert.
- only Nova-2-Lite: + Im vorliegenden Fall konnte nur durch die schnelle Reaktion des Tf der RB 58043 i.
- only Nova-2-Lite: V. m. dem bereits anfahrenden DGS 69235 eine Kollision verhindert werden.
- only summaries: Eine weitere Überprüfung durch den Fdl Jossa, ob sich in diesem tatsächlich eine Zugfahrt befand, fand augenscheinlich nicht statt.
- only summaries: Da der Fdl Jossa die Fortschaltstörungen vermutlich nicht erkannte und somit nicht durch Handeinwahl korrigierte, generierten sich im weiteren Verlauf entsprechende Fehlnummern in der ZNL800 des Stw Jossa.
- only summaries: Im Anschluss an den erfolglosen Grundstellungsversuch ließ der Fdl Jossa jedoch entgegen den weiteren Bestimmungen die Zugfahrt RB 58043, als erste Zugfahrt nach Beginn der Störung, ohne einen schriftlichen Befehl 9 zum Fahren auf Sicht in den noch als besetzt angezeigten Zugfolgeabschnitt 941 zu.

</details>

<details><summary>191.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der Zug GA 52805 kam an der Üst Treben-Lehma am Signal Ne 1 auf Höhe des Blocksignals A etwa in km 32,1 zum Halten.
- only Nova-2-Lite: Daraufhin entgleisen der elfte und auch der zwölfte Wagen, ebenfalls ein Fahrzeug der Gattung Laaeke, mit der Fahrzeugnummer 25 80 4367 127-4.
- only summaries: Alle Zugfahrten in der Relation Regis-Breitingen – Treben-Lehma, so auch die Zugfahrt GA 52805, erfolgten daher als Fahrten im besonderen Auftrag mit Befehl und im Gegengleis.
- only summaries: Daraufhin entgleisten der elfte und auch der zwölfte Wagen, ebenfalls ein Fahrzeug der Gattung Laaeke, mit der Fahrzeugnummer 25 80 4367 127-4.

**Ursachen des Unfalls**

- only Nova-2-Lite: Darum war der Fahrweg des Zuges nicht mehr gesichert und das anschließende unzeitige Umstellen der Weiche 20 unter dem Zug möglich.
- only summaries: Deshalb war der Fahrweg des Zuges nicht mehr gesichert und das anschließende unzeitige Umstellen der Weiche 20 unter dem Zug möglich.

</details>

<details><summary>196.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der Zug RB 29870 fuhr gegen 06:38 Uhr über die Weiche W 2 in den Bf Mellenbach-Glasbach ein.
- only Nova-2-Lite: Der Ablauf bei diesem Vorfall und die Ergebnisse der Überprüfung der Funktion der Weiche und des Signals Ne 13 nach dem Ereignis legen den Schluss nahe, dass das Überwachungssignal auch bei der Zugfahrt RB 29870, entgegen der Aussage des Tf des Zuges RB 29870, Ne 13b gezeigt haben könnte, dieser die Signalstellung jedoch zu spät oder überhaupt nicht wahrnahm und daher nicht rechtzeitig bremste.

**Ursachen des Unfalls**

- only Nova-2-Lite: Die Weiche W 2 im Bf Mellenbach-Glasbach war eine Rückfallweiche der Bauart 49-190-1:9. Rückfallweichen sind so konstruiert, dass sie aufgefahren werden können und dann selbsttätig mit Hilfe einer hydraulisch-mechanischen Rückstelleinrichtung in die Grundstellung zurücklaufen.
- only Nova-2-Lite: Die Rückstelleinrichtung der Firma FEW entsprach zur Zeit ihres Einbaus den anerkannten Regeln der Technik und war für den Eisenbahnbetrieb zugelassen.
- only Nova-2-Lite: Eine fehlerhafte Anzeige am Signal Ne 13 soll durch die Konstruktion des Endlagenprüfers ELP 319 verhindert werden.
- only summaries: Es ist davon auszugehen, dass die Hydraulikflüssigkeit der Rückstelleinrichtung aufgrund der niedrigen Außentemperaturen von ca. -14°C sehr zähflüssig war und dadurch die Reversion der Weiche stark verzögert wurde.
- only summaries: Der Ablauf bei diesem Vorfall und die Ergebnisse der Überprüfung der Funktion der Weiche und des Signals Ne 13 nach dem Ereignis legen den Schluss nahe, dass das Überwachungssignal auch bei der Zugfahrt RB 29870, entgegen der Aussage des Tf des Zuges RB 29870, Ne 13b gezeigt haben könnte, dieser die Signalstellung jedoch zu spät oder überhaupt nicht wahrnahm und daher nicht rechtzeitig bremste.

</details>

<details><summary>199.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: + Am 05.12.2017 gegen 19:27 Uhr kollidierte der Personenzug DPN 32547, auf der Fahrt von Rheine nach Krefeld Hbf, zwischen der Abzweigstelle (Abzw) Weißenberg und dem Bahnhof Meerbusch-Osterath mit dem Güterzug GM 48714.
- only Nova-2-Lite: + Eine Fehleingabe der Fdl Weißenberg in das ZN Sammelfeld 700 führte dazu, dass Zug 95307 unter der Zugnummer 66365 ausfuhr.
- only Nova-2-Lite: + Die Fdl Meerbusch-Osterath überschrieb die in der ZN erscheinende Fehlnummer F36 12 mit der Zugnummer 48714.
- only Nova-2-Lite: + Beide Fdl gingen irrtümlich von einer Störung der Sicherungsanlagen aus.
- only Nova-2-Lite: + Zug 48714 wird durch die Fdl Meerbusch-Osterath zurückgemeldet.
- only Nova-2-Lite: + Zug DPN 32547 erhält Ersatzauftrag zur Vorbeifahrt am Blocksignal ohne den Befehl zum Fahren auf Sicht.
- only summaries: Die Fdl Meerbusch-Osterath überschrieb die Fehlnummer mit der Zugnummer des folgenden Zuges 48714, der zu diesem Zeitpunkt noch im Abschnitt 200 auf dem ZN-Monitor angezeigt wurde.
- only summaries: Die Fdl in Meerbusch-Osterath meldete Zug 48714 auch irrtümlicher Weise zurück, obwohl dieser noch immer vor dem Esig stand.
- only summaries: Die Fdl in Weißenberg ließ die Zugfahrt DPN 32547 mit Fahrtstellung des Blocksignals A und, aufgrund der Rotausleuchtung, am folgenden Sbk 201 mittels Ersatzsignal Zs 1 zu.
- only summaries: Sie ließ die Zugfahrt zu, ohne den Tf zuvor durch schriftlichen Befehl aufzufordern, entsprechend den Regeln der Ril 408.0622, den Zugfolgeabschnitt hinter dem Sbk 201 auf Sicht zu befahren.
- only summaries: Mit dieser Geschwindigkeit prallte der Personenzug DPN 32547 um 19:27 Uhr auf den anfahrenen Güterzug 48714.

**Ursachen des Unfalls**

- only Nova-2-Lite: + Der Personenzug DPN 32547 wurde unzulässig in den noch durch den Güterzug GM 48714 in identischer Fahrtrichtung beanspruchten Blockabschnitt eingelassen.
- only Nova-2-Lite: + Die Handlungen der Fdl in Weißenberg und Meerbusch-Osterath waren ursächlich für die Entstehung des Unfalls.
- only Nova-2-Lite: + So führten zunächst eine Reihe nicht sicherheitsrelevanter Fehler im Zusammenhang mit der Bedienung der ZN zum Verlust des Überblicks auf die Betriebssituation.
- only summaries: Die Fdl der Abzw Weißenberg ließ den Zug 95307 durch eine Fehleingabe in die ZN mit falscher Zugnummer 66365 in Richtung Meerbusch-Osterath abfahren.
- only summaries: Irrtümliche erneute Eingabe der Zugnummer 66365 durch die Fdl Abzw Weißenberg.
- only summaries: Die im Bahnhof Meerbusch-Osterath in der Folge erscheinende Fehlnummer wurde durch die Fdl Meerbusch-Osterath mit der nachfolgenden Zugnummer 48714 in die ZN überschrieben.
- only summaries: Beide Fdl gingen irrtümlich von einer Störung der Sicherungsanlagen aus.
- only summaries: Zug 48714 wird durch die Fdl Meerbusch-Osterath zurückgemeldet.
- only summaries: Zug DPN 32547 erhält Ersatzauftrag zur Vorbeifahrt am Blocksignal ohne den Befehl zum Fahren auf Sicht.
- only summaries: Hinsichtlich der Streckenkenntnis des Tf wurden Defizite sichtbar.

</details>

<details><summary>200.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der Zugverband, aus dem Triebfahrzeug der Bauart Gravita 10 BB mit der Fahrzeugnummer 92 80 1 261 006-1 und 23 beladenen Güterwagen bestehend, war ab 08:52 Uhr als Zugfahrt 49691 von Bf Frankfurt (Oder) Pbf nach Bf Frankfurt (Oder) Oderbrücke unterwegs.
- only Nova-2-Lite: Wie aus den elektronischen Fahrdaten und den Protokollen des Stellwerks (Historiendruck) hervorging, fuhr der Zug 49691 mit einer Geschwindigkeit von etwas weniger als 30 km/h, fahrstraßentechnisch gesichert in Bf Frankfurt (Oder) Oderbrücke ein.

</details>

<details><summary>201.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Das für das zu untersuchende Ereignis relevante Stellwerk B1 war eine Mischbauform aus Bruchsal (unbestimmter Bauform) und Gleisbildstellwerk der Bauform II der ehem.
- only Nova-2-Lite: Das Ereignis war auf eine Vorbeifahrt des DGS 42722 am Halt zeigenden Signal 206 ohne Zustimmung des Fdl des Stellwerkbezirks B1 zurückzuführen.
- only Nova-2-Lite: Der Tf begann die Fahrt am Signal 206, obwohl der Befehl 2 in Verbindung mit den Befehlen 4 und 7 des Fdl noch keine Gültigkeit hatte.
- only summaries: Seit dem 17.12.2019 ca. 08:11 Uhr lag im Bf Leipzig-Engelsdorf eine Störung der Weiche 11 vor, so dass Fahrten nach Leipzig-Schönefeld im Gegengleis stattfinden mussten.
- only summaries: Der Fdl B1 diktierte dem Tf der Zugfahrt DGS 42722 somit die Inhalte der Befehle 2, 4 und 7 ohne Übermittlungscode, Uhrzeit und weitere Details.
- only summaries: Zum Übermittlungszeitpunkt hatte der Befehl somit noch keine Gültigkeit.
- only summaries: Etwa zeitgleich mit dem Passieren des Einfahrsignals des Bf Leipzig-Engelsdorf durch die S-Bahn S 37395, setzte sich der DGS 42722 in Bewegung.
- only summaries: Er passierte das Signal 206 und fuhr die Weiche 20 auf, was entsprechende Störungsmeldungen beim Fdl B1 aktivierte.

**Ursachen des Unfalls**

- only summaries: Der Tf begann die Fahrt am Signal 206, obwohl der Befehl 2 in Verbindung mit den Befehlen 4 und 7 des Fdl noch keine Gültigkeit hatte.

</details>

<details><summary>203.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 06.04.2017 gegen 14:43 Uhr ereignete sich auf der Fahrt von Au (Siegburg) nach Horrem im Bahnhof (Bf) Blankenberg (Siegburg) eine technische Störung am Zug S 33211 des Eisenbahnverkehrsunternehmens DB Regio AG Region NRW.
- only Nova-2-Lite: Die Untersuchungen am Fahrzeug zeigten, dass ein Radsatzlager beschädigt war und sich ein Teil der Primärfederung an der in Fahrtrichtung linken Seite des siebten Radsatzes gelöst hatte.
- only Nova-2-Lite: Das schadhafte Radsatzlager war auf dem Treibradsatz (Radsatznummer 27006084) montiert und in einem Jakobsdrehgestell eingebaut.
- only summaries: Es ist davon auszugehen, dass in diesem Augenblick das Radsatzlager blockierte.
- only summaries: Die Untersuchungen am Fahrzeug zeigten, dass ein Radsatzlager beschädigt war und sich ein Teil der Primärfederung an der in Fahrtrichtung linken Seite des siebten Radsatzes gelöst hatte, siehe Abbildung 2.

**Ursachen des Unfalls**

- only Nova-2-Lite: Das Radsatzlager war trocken gelaufen.
- only Nova-2-Lite: Darüber hinaus ereigneten sich im Zeitraum Februar 2017 bis April 2017 insgesamt drei Ereignisse mit nahezu identischen Schadbildern.
- only Nova-2-Lite: Die Untersuchungen [...] ergaben, dass es in allen Fällen durch einen verbogenen Lammellenring zum Versagen der Radsatzlager gekommen war.
- only Nova-2-Lite: Bei der Montage des Lagerdeckels, insbesondere bei Lagerdeckeln mit Erdungskontakten, kam es zu einem fehlerhaften Einbau der Lammellenringe.
- only summaries: Das Lager war trocken gelaufen.
- only summaries: Warum der Lamellenring ausfiel, konnte anhand des Schadbildes nicht mehr zweifelsfrei nachvollzogen werden.
- only summaries: Die Untersuchungen, die auf Betreiben des Fahrzeughalters durchgeführt wurden, ergaben, dass es in allen Fällen durch einen verbogenen Lammellenring zum Versagen der Radsatzlager gekommen war.
- only summaries: Dabei wurde festgestellt, dass es bei der Montage des Lagerdeckels, insbesondere bei Lagerdeckeln mit Erdungskontakten, zu einem fehlerhaften Einbau der Lammellenringe gekommen war.
- only summaries: Der fehlerhafte Einbau von Lammellenringen im Radsatzlager führte in der Folge zur Undichtigkeit und zum Verlassen der vorgesehenen Position.

</details>

<details><summary>226.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Der anzeigegeführte Güterzug DGS 90977 kam zufolgebedingt vor dem LZB-Blockkennzeichen (LZB-Bk) 86 auf der Strecke 6107 vor dem Hp Leiferde (b Gifhorn) zum Halten.
- only Nova-2-Lite: Der ebenfalls anzeigegeführte Güterzug DGS 42593 folgte dem DGS 90977 auf dieser Strecke.
- only Nova-2-Lite: Der Zug wurde hierdurch zum Stillstand geführt und kam in km 210,4 der Strecke zum Halten.
- only Nova-2-Lite: Der Tf des DGS 42593 meldete sich über GSM-R Zugfunk bei der zuständigen Fdl-in Fallersleben West und informierte sie, dass er einen LZB-Übertragungsausfall habe und zum Halten gekommen sei.
- only Nova-2-Lite: Die Fdl-in erteilte dem Tf für die signalgeführte Weiterfahrt den schriftlichen Befehl 10 und 10.1. Der DGS 42593 sollte seine Fahrt demnach die nächsten 2.000 m mit einer Höchstgeschwindigkeit von 40 km/h fortsetzen.

**Ursachen des Unfalls**

- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritischer Faktoren ist in den folgenden Kapiteln gegeben.
- only Nova-2-Lite: Zur direkten Zuordnung wird den sicherheitskritischen Faktoren eine Kurzbezeichnung zugewiesen.
- only Nova-2-Lite: Jedem ursächlichen oder beitragenden Faktor wird der Buchstabe F und einer fortlaufenden Nummerierung beigestellt.
- only Nova-2-Lite: Beispiel: [F1]. Zugehörige systemische Faktoren erhalten bei derselben Nummer den Buchstabe S; hier können ggf. weitere Unterscheidungen mittels eines Kleinbuchstabens erforderlich werden.
- only Nova-2-Lite: Beispiel: [S1a] und [S1b] zu [F1] Erkannte Aspekte, welche nach dem Ereigniseintritt die Raschheit oder Leistungsfähigkeit des Notfallmanagements beeinträchtigt und dadurch das Schadensausmaß vergrößert haben, werden mit dem Buchstaben N fortlaufend nummeriert.

</details>

<details><summary>231.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 03.06.2022 verließ der Personenzug RB-D 59458 um 12:14 Uhr, mit einer Verspätung von ca. sieben Minuten, den Bf Garmisch-Partenkirchen in Richtung München Hbf.
- only Nova-2-Lite: Der lokbespannte Wendezug RB-D 59458 war aus fünf Doppelstockwagen sowie einer elektrischen Lokomotive der BR 111 am Schluss des Zuges gebildet.
- only Nova-2-Lite: Nach etwa zwei Kilometern Fahrt erreichte der Zug die im Streckenabschnitt zugelassene Geschwindigkeit von 100 km/h.
- only Nova-2-Lite: In Höhe des Gemeindeteils Burgrain befuhr der Zug nahezu mit dieser Geschwindigkeit einen Linksbogen.
- only Nova-2-Lite: Innerhalb dieses Bogens mit dem Radius 407 m entgleiste in km 97,676 gegen 12:16 Uhr der Zug mit allen Fahrzeugen.
- only Nova-2-Lite: Aufgrund der vorhandenen Trassierung mit maximaler Ausnutzung des zulässigen Überhöhungsfehlbetrages $uf = 130$ mm wirkten bei Überfahrt eines Zuges hohe Kräfte auf das Gleis.
- only Nova-2-Lite: Diese durch den Reisezug eingetragenen Kräfte, konnten von den im Bogen verbauten vorgeschädigten Spannbetonschwellen nicht mehr aufgenommen werden und führten im Bereich der Schienenauflager / Sicken schließlich zu einem Versagen der Struktur.
- only Nova-2-Lite: Infolgedessen kam es zu einer einseitigen Verschiebung des gesamten äußeren Schwellenauflagers zur Bogenaußenseite.
- only Nova-2-Lite: Hierdurch kam es zu einer unzulässigen Spurerweiterung und der Zug verlor zunächst den Kontakt zu der in Fahrtrichtung linken Schiene, während die in Fahrtrichtung rechte Schiene weiter befahren wurde.
- only Nova-2-Lite: Unmittelbar danach folgten wieder intakte Spannbetonschwellen, wodurch sich die Spurweite wieder in Richtung des zulässigen Maßes verkleinerte.
- only Nova-2-Lite: An dieser Stelle kam es zu einem mechanischen Zwang, so dass auch die noch auf der rechten Schiene laufenden Räder zur Bogenaußenseite gedrückt wurden und die Schiene überkletterten.
- only Nova-2-Lite: In Folge der Entgleisung rutschten der dritte und vierte Wagen die Böschung in Richtung der parallel verlaufenden Bundesstraße herunter.
- only Nova-2-Lite: Die anderen Fahrzeuge des Zuges kamen im entgleisen Zustand auf dem Gleis zum Stehen.
- only summaries: Beim Befahren der im Bogen verbauten Spannbetonschwellen kam es zum Versagen der Struktur und zu einer Verschiebung des gesamten bogenäußeren Schwellenauflagers zur Außenseite.
- only summaries: Dies führte zu einer Spurerweiterung und in Folge dessen zur Entgleisung des Zuges.
- only summaries: Infolge der Entgleisung rutschten der dritte und vierte Wagen die Böschung in Richtung der parallel verlaufenden Bundesstraße herunter.
- only summaries: Die anderen Fahrzeuge des Zuges kamen im entgleisten Zustand auf dem Gleis zum Stehen.

**Ursachen des Unfalls**

- only Nova-2-Lite: Im Rahmen der Untersuchung des Ereignisses wurden die folgenden Handlungen, Unterlassungen, Vorkommnisse oder Umstände als sicherheitskritische Faktoren identifiziert.
- only Nova-2-Lite: Diese werden gemäß Durchführungsverordnung (EU) 2020/572 in ursächliche oder beitragende und systemische Faktoren unterschieden.
- only Nova-2-Lite: Zusätzlich werden ermittelte Defizite des Notfallmanagements behandelt.
- only Nova-2-Lite: Zur besseren Übersichtlichkeit der Faktoren und Aspekte des Notfallmanagements wird eine Systematik mit Kennzeichnungen in eckigen Klammern verwendet.
- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritische Faktoren wird in den folgenden Kapiteln gegeben.

</details>

<details><summary>234.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Im Bf Hannover-Hainholz erfolgte der planmäßige Fahrtrichtungswechsel, der in dispositiven Gründen bezüglich der benötigten Arbeitsrichtung der mitgeführten Wagen für die Baumaßnahme in Padborg begründet war.
- only Nova-2-Lite: Am 500 Hz Gleismagneten des Esig Z<sup>II</sup> wurde bei einer Geschwindigkeit von ca. 62,8 km/h eine Zwangsbremsung ausgelöst, die der Tf mit einer Schnellbremsung unterstützte.
- only Nova-2-Lite: Die weiterhin unzureichende Bremswirkung des Güterzuges führte zu einer Vorbeifahrt am Haltbegriff des Esig Z<sup>II</sup> und anschließend zur Kollision mit denen im Gleis 815 stehenden Wagen aus Güterzuges EZ 51593.
- only summaries: Am 500 Hz Gleismagneten des Esig ZII wurde bei einer Geschwindigkeit von ca. 62,8 km/h eine Zwangsbremsung ausgelöst, die der Tf mit einer Schnellbremsung unterstützte.
- only summaries: Die weiterhin unzureichende Bremswirkung des Güterzuges führte zu einer Vorbeifahrt am Haltbegriff des Esig ZII und anschließend zur Kollision mit denen im Gleis 815 stehenden Wagen aus Güterzuges EZ 51593.

**Ursachen des Unfalls**

- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritische Faktoren wird in den folgenden Kapiteln gegeben.
- only Nova-2-Lite: Tabelle 1: Zusammenfassung Einflussfaktoren
- only summaries: Das Unterlassen, eine Durchgängigkeit der HL herzustellen, wurde durch die BEU als ein ursächlicher Faktor des Ereignisses gewertet [F1].
- only summaries: Das Unterlassen einer vereinfachten Bremsprobe als ein unterbliebenes Prüfverfahren zum Offenbaren von Arbeitsfehlern wurde durch die BEU zusätzlich als ein ursächlicher Faktor des Ereignisses eingestuft [F2].
- only summaries: Im Ergebnis wurde festgestellt, dass der zum Ereigniszeitpunkt zuständige Tf trotz der nachweisweislich durchgeführten Überwachungen und regelmäßigen Fortbildungen kein Situationsbewusstsein für das Erfordernis der Durchführung einer vereinfachten Bremsprobe hatte.
- only summaries: Der ohnehin schon durch den Tf spürbare Zeitdruck, den zeitkritischen Fahrplan einzuhalten um den Grenzübergang in Richtung Dänemark und in der Folge die Baustelle zu erreichen, wurde durch Anrufe des EVU während der Fahrt des Tf verstärkt.
- only summaries: Es ist davon auszugehen, dass diese auf den Tf wirkende Situation zu den festgestellten Arbeitsfehlern führte und eventuell auch die sorgfältige Beachtung der gemäß Regelwerk obligatorischen Tätigkeiten beim Richtungswechsel und bzgl. einer gebotenen Bremsprobe schmälerte.

</details>

<details><summary>237.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Am 21.11.2024 sollten im Bf Dorsfeld von 21:30 Uhr bis 05:30 Uhr des Folgetages Arbeiten gemäß Betra Nr.
- only Nova-2-Lite: F35 6634 24 ausgeführt werden.
- only Nova-2-Lite: Zur Durchführung der Betra – und um die Arbeiten beginnen zu können – waren ein Bauzug, bestehend aus einem Tfz und zwei mit Schotter beladenen offenen Schüttgutwagen sowie eine Stopfmachine erforderlich.
- only Nova-2-Lite: Diese sollten dem Bf Dorsfeld aus Richtung Horrem zugeführt werden.
- only Nova-2-Lite: Zwecks Durchführung der Arbeiten gemäß Betra sollten in vier Phasen nacheinander bestimmte Gleisabschnitte und Weichen im Bf Dorsfeld gesperrt werden.
- only Nova-2-Lite: Die vorgesehenen Stopfarbeiten konnten witterungsbedingt nicht durchgeführt werden.
- only Nova-2-Lite: Als Ergebnis der bis zu diesem Zeitpunkt durchgeführten Arbeiten befanden sich sowohl der Bauzug als auch die Stopfmachine im Gleis 2 des Bf Dorsfeld, um von dort als Zugfahrt wieder ausfahren zu können.
- only Nova-2-Lite: Der Technisch Berechtigte meldete der zuständigen Fdl-in Horrem I um 00:49 Uhr das Ende der Arbeiten und die Befahrbarkeit der zuvor gesperrten Gleisabschnitte und Weichen per GSM-R. Die Stopfmachine fuhr kurz darauf in Richtung Horrem aus dem Bf Dorsfeld aus.
- only Nova-2-Lite: Der Bauzug blieb noch in Gleis 2 zurück, da die geplante Ausfahrt erst für 01:46 Uhr vorgesehen war.
- only Nova-2-Lite: Grund für diese Rotausleuchtung war der dort noch stehende Bauzug.
- only Nova-2-Lite: Daraufhin rief sie den ihr durch die stattgefundenen Betra-Arbeiten bekannten BÜB an und forderte von diesem eine Meldung über das Freisein des Gleises 2 des Bf Dorsfeld an.

**Ursachen des Unfalls**

- only Nova-2-Lite: Zur besseren Übersichtlichkeit der Faktoren und Aspekte des Notfallmanagements wird eine Systematik mit Kennzeichnungen in eckigen Klammern verwendet.
- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritische Faktoren wird in den folgenden Kapiteln gegeben.
- only summaries: Nach Abmeldung der Arbeiten gemäß Betra und vor Aufhebung der zugehörigen Sperrungen lagen der Fdl-in keine eindeutigen Informationen über den tatsächlichen Belegungszustand des Gleises 2 im Bf Dorsfeld vor.
- only summaries: Dieser Umstand wurde durch die BEU als ursächlicher Faktor [F1] gewertet, da keine eindeutige Übersicht über die Gleisbelegungen vor dem Aufheben der Gleissperrung mehr bestand.
- only summaries: Die Fdl-in führte die nach Regelwerk Ril 408.0471 Abschnitt 2 Abs. 4c) obligatorische Abschnittsprüfung nicht unmittelbar im zeitlichen Zusammenhang mit der Meldung über den Wegfall der Anlässe für die Gleissperrungen durch, sondern erst nachdem die Stopfmachine 24146 um 00:54:29 Uhr aus dem Bf Dorsfeld ausgefahren war.
- only summaries: Die Durchführung der Abschnittsprüfung durch die Fdl-in wurde durch die BEU als ursächlicher Faktor mit Bezug auf den Eintritt des Ereignisses gewertet [F2].
- only summaries: Der BÜB bestätigte ihr daraufhin unverzüglich, dass das gesamte Gleis 2 neben der Befahrbarkeit auch frei von Fahrzeugen sei, was die Fdl-in wiederholte.
- only summaries: Die getroffene Feststellung des „Freiseins“ und Abgabe der Meldung durch den BÜB wurde durch die BEU als ursächlicher Faktor [F3] gewertet, dessen Ausschluss das Ereignis aller Wahrscheinlichkeit nach verhindert hätte.

</details>

<details><summary>238.md</summary>

**Hergang des Unfalls**

- only Nova-2-Lite: Aufgrund einer Störung an einem im Fahrtverlauf liegenden BÜ kam es bei der von München kommenden Zugfahrt S 6776 zu einer Verspätung von ca. zehn Minuten.
- only Nova-2-Lite: Der Fdl Wolfratshausen entschied sich daher, die eigentlich planmäßig im Bf Icking vorgesehene Zugkreuzung der Personenzüge S 6785 und S 6776 in den Bf Ebenhausen-Schäftlarn zu verlegen.
- only Nova-2-Lite: Nachdem der Personenzug S 6785 zum Stillstand gekommen war, hob der Tf die Zwangsbremsung auf und setzte die Fahrt in Richtung des durch den entgegenkommenden Personenzug belegten eingleisigen Streckenabschnittes fort.
- only Nova-2-Lite: Durch die Weiterfahrt des Personenzuges S 6785 kam es zu einer Belegung des Abschnittes 1<sup>3</sup> und dadurch zu einem vorzeitigen Signalhaltfall des Esig 1A ca. 50 m vor der Vorbeifahrt des sich zur gleichen Zeit dem Bf Ebenhausen-Schäftlarn nähernden Personenzuges S 6776.
- only Nova-2-Lite: Um ca. 16:35 Uhr kam es zur Zugkollision mit dem ausfahrenden, zu diesem Zeitpunkt noch 57 km/h schnellen Personenzug S 6785.
- only summaries: Nachdem der Personenzug S 6785 zum Stillstand gekommen war, hob der Tf die Zwangsbremsung auf und setzte die Fahrt in Richtung des durch den entgegenkommenden Personenzuges S 6776 belegten eingleisigen Streckenabschnittes fort.
- only summaries: Der Tf dieses Personenzuges leitete umgehend nach Erkennen des Signalhaltfalls eine Schnellbremsung ein und der Zugverband kam ca. im km 17,76 zum Stillstand.
- only summaries: Während der Tf sich beim Fdl über den Grund des Signalhaltfalls erkundigte, kam es um ca. 16:35 Uhr zur Zugkollision mit dem ausfahrenden, zu diesem Zeitpunkt noch 57 km/h schnellen Personenzug S 6785.

**Ursachen des Unfalls**

- only Nova-2-Lite: Eine detaillierte Auswertung des Ereignisses unter Einordnung als sicherheitskritische Faktoren wird in den folgenden Kapiteln gegeben.

</details>

## Gemma-001 vs. summaries

35 reports in both. Sentences of one run that the other run has in the same section:

| section | Gemma-001 | also in summaries | summaries | also in Gemma-001 |
|---|---|---|---|---|
| Hergang des Unfalls | 177 | 82% | 177 | 82% |
| Höhe des Schadens, Anzahl Tote und Verletzte | 83 | 95% | 81 | 97% |
| Ursachen des Unfalls | 176 | 81% | 184 | 78% |
| Bauart der beteiligten Stellwerke | 38 | 97% | 42 | 88% |
| Baureihe der beteiligten Schienenfahrzeuge | 56 | 78% | 60 | 70% |
| Sicherheitsempfehlungen zur Verhinderung zukünftiger Unfälle | 37 | 94% | 48 | 75% |
| bereits umgesetzte Maßnahmen | 119 | 97% | 139 | 82% |

### Sentences only in one run

<details><summary>001.md</summary>

**Ursachen des Unfalls**

- only Gemma-001: Die Fahrt des IRC 51219 nach dem Ereignis „Empelde“ wurde jedoch ohne Veranlassung der erforderlichen „vollen Bremsprobe“, die den Grund für die ungenügende Bremswirkung offenbart hätte, fortgesetzt.
- only Gemma-001: Dieser Sachverhalt spricht gegen eine ordnungsgemäß durchgeführte volle Bremsprobe im Bf Seelze Rbf.
- only summaries: Die im Gutachten vorgenommene theoretische Bremsberechnung ergab, dass der Zug lediglich über das Bremsvermögen des Triebfahrzeugs und der Wagen Nr. 1 bis 4 verfügt haben konnte.
- only summaries: Der Luftabsperrhahn polnischer Güterwagen (ähnlich des Typs AK 8), wie er u.a. beim Wagen Nr. 5 vorhanden war, die technische Erklärung für das ungewöhnliche Bremsverhalten liefert.
- only summaries: Dieser konnte bei geschlossener bzw. teilgeschlossener Stellung die Druckverhältnisse in der Hauptluftleitung des Zuges entsprechend negativ beeinflussen.
- only summaries: Bei regelgerechter Abarbeitung des Ereignisses „Empelde“ durch den Triebfahrzeugführer des IRC 51219 wäre insoweit das spätere Unfallereignis „Bad Münder“ vermeidbar gewesen.
- only summaries: Die Fahrt des IRC 51219 nach dem Ereignis „Empelde“ wurde jedoch ohne Veranlassung der erforderlichen „volle Bremsprobe“, die den Grund für die ungenügende Bremswirkung offenbart hätte, fortgesetzt.

</details>

<details><summary>007.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Hierdurch wurde der Zusammenstoß unvermeidlich.
- only summaries: Der Zusammenstoß der Regionalexpresszüge RE 19533 und RE 19534 ereignete sich am 11.06.2003 gegen 12:03 Uhr in einer unübersichtlichen Kurve auf dem eingleisigen, nicht elektrifiziertem Streckenabschnitt Niederstetten - Schrozberg in km 30,360 der Strecke Crailsheim - Bad Mergentheim.

**Ursachen des Unfalls**

- only Gemma-001: Kritisch erscheint im Zusammenhang mit dem Unglück, dass – bedingt von der Bauform des Stellwerks - die Störung eines Vorsignalwiederholers eine weitere, weitaus gravierendere Störung erzeugt, indem zur Fehleroffenbarung das selbsttätige Auflösen der zugehörigen Fahrstraße verhindert wird.
- only Gemma-001: So hat der Fahrdienstleiter Niederstetten die Ursache der Störung der Fahrstraßenauflösung nicht erkannt, obwohl solche Störungen in den Tagen vor dem Unglück mehrmals aufgetreten und im Störungsbuch verzeichnet waren.
- only Gemma-001: Die Handlungsweise des Fahrdienstleiters Schrozberg, der eine Rückmeldung für RE 19533 abgegeben hat, ohne das Zugschlusssignal gesehen zu haben, stellt einen groben Verstoß gegen grundlegende Betriebsvorschriften dar.
- only summaries: Der Zusammenstoß wäre vermieden worden, wenn der Fahrdienstleiter Schrozberg die Räumungsprüfung für den Abschnitt Niederstetten – Schrozberg korrekt durchgeführt hätte.
- only summaries: Offenbar war dem Fahrdienstleiter die Tatsache, dass sich als Besonderheit seines Stellwerks ein Ausfall des Vorsignalwiederholers auf die selbsttätige Fahrstraßenauflösung auswirkt, nicht geläufig.
- only summaries: Dies lässt auf eine unzureichende Aus- bzw.
- only summaries: Weiterbildung schließen, zumal in keiner dem Fahrdienstleiter zugänglichen Unterlage, auf diesem Umstand hingewiesen wird.
- only summaries: Bemerkenswert ist weiterhin der Umstand, dass der Projektierungsfehler, der bei einer Störung der selbsttätigen Fahrstraßenauflösung das manuelle Vorblocken des Streckenblocks verhindert (vgl.
- only summaries: Ziff 3.2.3.1) offenbar seit Bestehen des Stellwerks nicht bemerkt, sondern durch nicht regelkonformes Handeln der Fahrdienstleiter kompensiert wurde.

</details>

<details><summary>008.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Der Triebfahrzeugführer des RE 3560 konnte den Zusammenprall mit dem Lkw nicht abwenden, weil er das Hindernis in seinem Fahrweg erst aus einer Entfernung von ca. 180 Meter erkannt haben dürfte.
- only Gemma-001: Eine Schnellbremsung bewirkte lediglich eine marginale Reduzierung der Geschwindigkeit von 156 km/h auf 150 km/h.
- only Gemma-001: Beim Zusammenprall traf die Mittelpufferkupplung des Triebzugs den Kraftstofftank des LKW.
- only summaries: Unterstellt man eine Reaktionszeit von ca 1 Sekunde, so hat der Triebfahrzeugführer den US-LKW etwa 4 Sekunden und ca. 170 Meter vor dem Zusammenprall als Hindernis auf dem BÜ erkannt und eine Schnellbremsung eingeleitet, die etwa 130 m vor dem Kollisionspunkt wirksam wurde.
- only summaries: Eine nennenswerte Geschwindigkeitsabsenkung konnte er hierdurch nicht mehr erreichen.
- only summaries: Der Aufprall erfolgte mit einer Geschwindigkeit von annähernd 150 km/h.

</details>

<details><summary>009.md</summary>

**Hergang des Unfalls**

- only summaries: Ursache für den Zusammenstoß der beiden Züge war die ungenügende Bremswirkung des Zuges S 25066.

</details>

<details><summary>012.md</summary>

**Ursachen des Unfalls**

- only summaries: Hier konnte ein Einzelbruch nachgewiesen werden der sich unter der Schienenkopfoberfläche in Richtung Steg entwickelte und später in vertikaler Richtung abknickte.
- only summaries: Diese Rissfläche bildete den Dauerbruch.
- only summaries: Nach dem Erreichen einer kritischen Risstiefe von ca. 17 mm erfolgte bei der Zugüberfahrt der Restgewaltbruch.

</details>

<details><summary>015.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Am 16.04.2009 kam es um ca. 22:17 Uhr auf dem Bahnhof Berlin-Karow im durchgehenden Hauptgleis (Gleis 2) zu einem Zusammenstoß zwischen dem in Richtung Berlin-Blankenburg durchfahrenden Zug RE 38399 und dem ebenfalls in Richtung Berlin-Blankenburg ausfahrenden Zug FTZ 53185.
- only Gemma-001: In der Folge kam es zur Entgleisung des zweiten Drehgestells vom Triebfahrzeug 114 019 und des 1. Wagens des RE 38399.
- only summaries: Bei Annäherung an das Einfahrsignal des Bahnhofs Berlin-Karow um ca. 22:11 Uhr erkannte der Triebfahrzeugführer des FZT 53185, dass der Regelfahrweg und nicht der gemäß Fplo 10710 vorgegebene Fahrweg eingestellt war und teilte dies dem Fahrdienstleiter per Zugfunk mit.
- only summaries: Diese Fahrtstellungen werden aber nun vom folgenden RE 38399 aufgenommen, der auf Grund der Signalisierung Hl 1 am Einfahrtsignal 136 und der Signalisierung Hl 1 am Ausfahrtsignal 132 seine Geschwindigkeit erhöht, bis er etwa in Höhe des Ausfahrtsignals eine Schnellbremsung einleitete, da er den Zugschluss eines Kesselwagenzuges bemerkte.

</details>

<details><summary>023.md</summary>

**Ursachen des Unfalls**

- only Gemma-001: Die Untersuchung unmittelbar nach Eintreffen an der Unfallstelle, ca. 04:00 Uhr, ergab, dass die vom Güterzug DGS 69192 passierten Signale, das Vorsignal Vb, „Halt erwarten“ und das Blocksignal, „Halt“(Hp0), zeigten und die Weiche 1 in der Überleitstelle Hordorf aufgefahren wurde.
- only Gemma-001: Der Güterzug fuhr in einen besetzten Gleisabschnitt und kollidierte in Folge mit dem im eingleisigen Streckenabschnitt entgegenkommenden Personenzug.
- only summaries: Die nach dem Ausschlussverfahren durchgeführte Untersuchung lässt keinen anderen Schluss zu, da keine belastbaren Hinweise auf weitere Ursachen ermittelt werden konnten.

</details>

<details><summary>034.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Dieser Gleisbereich ist durch sehr enge Kurven, die einander anschließen geprägt.
- only Gemma-001: Hierdurch sei es ihm unmöglich gewesen, die Arbeiter im Durchfahrgleis früher zu bemerken.
- only summaries: Der Fahrdienstleiter erteilte die Zustimmung zur Fahrt für ICE 75, obwohl die Weiche 227 noch gesperrt war.
- only summaries: Die beiden Personen an der Baustelle versuchten aus den Gleisen zu springen, wobei eine Person vom Zug erfasst wurde.

</details>

<details><summary>042.md</summary>

**Ursachen des Unfalls**

- only summaries: Trotz eingeleiteter Schnellbremsung konnte eine Kollision mit der Rinderherde nicht verhindert werden.

</details>

<details><summary>055.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Der Güterzug DGS 49325 fuhr im Bahnhof Hosena dem nach Gleis 7 aus Schwarzbach einfahrenden Güterzug DGS 92505 im hinteren Teil des Wagenzuges in die Flanke.
- only summaries: Dabei fuhr der aus Richtung Schwarzkollm kommende DGS 49325, dem aus Richtung Schwarzbach kommenden und nach Gleis 1West (Hosena Nord) einfahrenden DGS 92505 im letzten Zugdrittel in die Flanke.

**Ursachen des Unfalls**

- only Gemma-001: Auch ist festzustellen, dass nach ordnungsgemäßer Durchführung der vollen Bremsprobe vor erstmaliger Fertigmeldung es nicht mehr zu dem geschilderten Fehler im hinteren Zugdrittel hätte kommen dürfen, da sich dies bei der vorangegangenen Bremsprobe hätte offenbaren müssen.

</details>

<details><summary>082.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Der Fdl stimmte der Einfahrt der S 39198 in den Bf um ca. 20:27 Uhr, wahrscheinlicher etwa 20:28 Uhr, durch Signal Zs 1 – Ersatzsignal – am Esig A zu.
- only Gemma-001: Nach Stillstand des Zuges befand sich das Drehgestell 1 auf der stumpf befahrenen Weiche 8, das Drehgestell 3 auf der Weiche 7, die nun in Stellung Fahrweg nach links in Richtung Gleis 3 stand.
- only summaries: Die S-Bahn 39198 erhielt die Zustimmung des Fahrdienstleiters (Fdl) Forchheim zur Einfahrt in den Bf durch Ersatzsignal (Signal Zs 1 der ESO) am Einfahrsignal A.
- only summaries: Es ist wahrscheinlich anzunehmen, dass der Fdl Forchheim die Fehlleitung nach Zulassung der Einfahrt 39198 auf Ersatzsignal bemerkte und die Weiche 7 dabei noch nicht als belegt angezeigt war.
- only summaries: Unzulässig entschied sich der Fdl die Weiche 7 umzustellen, um die S-Bahn doch noch in das vorgesehene Gleis 3 einzufahren.

**Ursachen des Unfalls**

- only Gemma-001: evtl. mangelhafte Ausbildung bzw. mangelnde Praxis (die Grundkenntnisse und Erfahrungen für einen FdI waren sicher gegeben, ob die Einarbeitungszeit für die tech- nisch andere und praktisch neue Stellwerksform Gleisbildstellwerk ausreichend war, kann nicht beurteilt werden);
- only summaries: Deswegen wurde die unzulässige Umstellung der spitz befahrenen Weiche 7 unmittelbar vor (Stellbefehl erteilt) bzw. unter dem Zug (zwischen Drehgestell 1 und Drehgestell 2) überhaupt erst möglich.
- only summaries: Fest steht, dass er fehlerhaft reagierte und nach Zustimmung zur Zugfahrt mit Signal Zs 1 unzulässig versuchte, durch Umstellen der Weiche 7, die S-Bahn 39198 doch noch in das vorgesehene und einzig zulässige Einfahrgleis 3 zu leiten – diese falsche Reaktion ist der eigentliche Auslöser der Entgleisung.
- only summaries: evtl. mangelhafte Ausbildung bzw. mangelnde Praxis (die Grundkenntnisse und Erfahrungen für ein FdI waren sicher gegeben, ob die Einarbeitungszeit für die tech- nisch andere und praktisch neue Stellwerksform Gleisbildstellwerk ausreichend war, kann nicht beurteilt werden);

</details>

<details><summary>089.md</summary>

**Hergang des Unfalls**

- only Gemma-001: In km 76,950 entgleist das am Schluss laufende Fahrzeug 23-88 4371 080-3.
- only Gemma-001: Bei der Einfahrt nach Gleis 2 in den Bf Rüdesheim (Rh.) werden die Einfahrweichen stark beschädigt und das Ausfahrtsignal N3 umgerissen.
- only summaries: Am 09.06.2013 um 5:21 Uhr entgleist DGS 90636 zwischen den Betriebsstellen Kaub und Lorch (Rhein) Üst mit dem am Schluss des Zuges laufenden Autotransportwagen 23-88 4371 080-3 in km 76,950.

**Ursachen des Unfalls**

- only Gemma-001: Die flach verlegte Tiefenentwässerung zwischen beiden Gleisen sollte vom ALV KIB gemäß Ril 836.8002 Abschn. 3 Abs. 2 regelmäßig untersucht werden.
- only Gemma-001: Aufgrund der vorliegenden TV-Inspektion hätte offensichtlich erkennbar sein müssen, dass der Großteil der Haltungen defekt und der Anschluss an eine Vorflut nicht gegeben war.
- only Gemma-001: Die Fehlinterpretierung der Mängelursache durch den ALV Oberbau bei den häufig durchgeführten Inspektionen lässt darauf schließen, dass Defizite beim Erkennen der Gesamtzusammenhänge vorlagen.

</details>

<details><summary>096.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Der Tf des ICE nahm daraufhin die Fahrt wieder auf und beschleunigte seinen Zug.
- only Gemma-001: Nach Durchfahren eines Rechtsbogens erkannte er das Zugschlussssignal des nach wie vor in Gleis 507 stehenden DPN 32490 und leitete bei einer Geschwindigkeit von ca. 78 km/h eine Schnellbremsung ein.
- only summaries: Der hintere, abgekuppelte Zugteil, verblieb in Gleis 507.
- only summaries: Nach Durchfahren eines Rechtsbogens erkannte er das Zugschlusssignal des nach wie vor in Gleis 507 stehenden DPN 32490 und leitete bei einer Geschwindigkeit von ca. 78 km/h eine Schnellbremsung ein.

**Ursachen des Unfalls**

- only Gemma-001: Der Fdl prüfte das Freisein des betroffenen Gleisabschnitts in Gruiten somit mittelbar.
- only Gemma-001: Das Hinsehen war ihm wegen der Entfernung zwischen seinem Stellwerk in Wuppertal-Vohwinkel und dem Bahnhof Gruiten natürlich nicht möglich.
- only Gemma-001: Die Gründe für die falsche Entscheidung der Fdl kennt die BEU nicht.
- only summaries: Warum diese wichtigen Fahrplanregelungen den Fdl nicht erreichten, konnte auch durch die DB Netz AG im Rahmen ihrer Untersuchungen nicht geklärt werden.

</details>

<details><summary>101.md</summary>

**Hergang des Unfalls**

- only summaries: Die nachfolgenden 4 Zugfahrten in Richtung Hamburg-Eidelstedt wurden auf Ersatzsignal durchgeführt.

**Ursachen des Unfalls**

- only Gemma-001: Der Fdl ließ mehrere Fahrten auf Zs1 zu ohne eine vorherige Räumungsprüfung der Streckenabschnitte durchzuführen.
- only Gemma-001: Die im Rahmen der Untersuchung gewonnenen Erkenntnisse lassen erkennen, dass trotz längerer Einweisung und mehrerer Einsätze auf der Örtlichkeit keine Handlungssicherheit bei Störungen bestand.
- only summaries: Dies äußert sich in einer Vielzahl nicht dokumentierter Hilfsbedienungen und dem unzureichenden Führen relevanter, für einen sicheren Bahnbetrieb notwendiger Unterlagen (Arbeits- und Störungsbuch, Zugmeldebuch).
- only summaries: Eine, auf fundiertem Wissen aufsetzende Kenntnis der betrieblichen Abläufe hätte die notwendige Handlungssicherheit gegeben, um auch im Störungsfall den Eisenbahnbetrieb sicher durchführen zu können und hätte dadurch zur Vermeidung des Ereignisses beigetragen.

</details>

<details><summary>116.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Ursache für die Entgleisung war das unbeabsichtigte Anheben eines Rades des vorletzten Wagens im Bereich der linken Weichenzungenvorrichtung der DKW 36 a.
- only Gemma-001: Das unbeabsichtigte Anheben des Rades wurde durch einen impulsartigen Stoß in Kombination mit Veränderungen an der Kontaktfläche Rad/Weichenzunge ausgelöst.
- only summaries: Aufgrund der großen Zerstörung der Gleisanlagen und der sich teilweise überlagernden Entgleisungsspuren war es nicht möglich, eine durchgängige Rekonstruktion des Ereignisses herzustellen.

**Ursachen des Unfalls**

- only Gemma-001: Als wahrscheinliche Ursache wird das Aufklettern des in Fahrtrichtung linken Rades (RS 4) des führenden DG (DG D) des vorletzten Wagen 806 629-2 an der Weichenzunge aufgrund eines vergrößerten Anlaufwinkels in Verbindung mit einer ungewöhnlichen Materialverformung an der Weichenzunge angenommen.
- only summaries: Unter Einhaltung der Vorgaben aus Ril 821.2005 hätten die zur Entgleisung führenden Verschleißmängel an der Zungenspitze durch das Inspektionspersonal erkannt werden müssen.

</details>

<details><summary>123.md</summary>

**Ursachen des Unfalls**

- only Gemma-001: Dieser führte zum Bruch der vorlaufenden Radsatzwelle im hinteren Drehgestell des an 42. Stelle laufenden Wagens.
- only Gemma-001: Die technische Ursache am Fahrzeug, die in einer Kausalkette zum Versagen der Funktion des Radsatzlagers und somit zur Entgleisung geführt hatte, war nicht nachweisbar.
- only summaries: Den Angaben der DB Netz AG zufolge habe die Software der Anlage den gemessenen Wert als Fehler klassifiziert und diesen auf 2° C gesetzt.
- only summaries: Ursächlich sei hierfür der s.g.
- only summaries: Sonnenklassifikator, also die Möglichkeit, die Anlagensoftware auf gewisse äußere Faktoren, wie Sonneneinstrahlung oder häufiges Bremsen der Züge zu programmieren.

</details>

<details><summary>125.md</summary>

**Ursachen des Unfalls**

- only Gemma-001: Die wesentliche Ursache für die Entgleisung liegt in der Gleislageanregung.
- only Gemma-001: Obwohl keine Grenzwertüberschreitungen der Einzelfehler der Gleislage vorliegen, ergibt sich aufgrund des periodischen Auftretens von SR 100 Fehlern eine Anregung des Fahrzeugs, die zu erheblichen Schwingungen des Wagenkastens und damit zur Entgleisung geführt hat.
- only summaries: Zu derartigen in annähernd gleichem Abstand auftretenden, also periodischen, Gleislagefehlern macht die Ril 821 dem ALV zum Unfallzeitpunkt allerdings keine Vorgaben, wie damit umzugehen ist.

</details>

<details><summary>126.md</summary>

**Ursachen des Unfalls**

- only Gemma-001: Aus fachlicher Sicht ist es nicht nachvollziehbar, dass die Empfehlungen des Gutachtens, den Einbau einer PSS aufgrund der Untergrundbeschaffenheit vorzunehmen, nicht umgesetzt wurden.
- only Gemma-001: Trotz fehlender Nachweise zur Gebrauchstauglichkeit der TE wurden die Baumaßnahmen durch den IBV freigegeben.
- only Gemma-001: Die Inspektion der Tiefenentwässerung obliegt dem ALV KIB.
- only Gemma-001: Durch die fehlende Nachhaltigkeit in der Instandhaltung der Gleislage wurde die Ursache für die Gleislagefehler nicht beseitigt und die Ausbildung von mehreren hintereinanderliegenden zyklischen Länghöhenfehlern parallel auf beiden Schienensträngen ermöglicht.
- only summaries: Durch diese Länghöhenfehler hat die Frequenzhöhe der Erregerfrequenz (Anregungsfrequenz) der Gleisanlage bei einer Geschwindigkeit von ca. 80 km/h sich der Eigenfrequenz des Fahrzeuges Hbbill 305 soweit angenähert, dass es zu einer Anregung der Eigenfrequenz des Fz gekommen ist.

</details>

<details><summary>179.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Nachdem die vorgeschriebenen Messfahrten durchgeführt wurden und der Einbau der Schienen somit als mangelfrei zu bewerten war, durfte der Abschnitt am 06.05.2015 gegen 10:00 Uhr nun wieder mit 300 km/h befahren werden.
- only Gemma-001: Etwa 70 Minuten nach Aufhebung der Langsamfahrstelle, gegen 11:09 Uhr, kam es zur Kollision des ICE mit den Bruchstücken der Absorberplatten.

</details>

<details><summary>180.md</summary>

**Ursachen des Unfalls**

- only Gemma-001: Voraussetzung zur Umsetzung dieser Regeln wären Erkenntnisse eines Fdl über regionale Wetterphänomene, wie beispielweise Starkregen, sowie das Wissen von potenziell gefährdeten Streckenabschnitten in seinem Stellbereich.
- only Gemma-001: Gemäß den Anforderungen an das SMS eines Infrastrukturbetreibers nach Verordnung (EU) 2018/762 Anhang II Nr. 3.1., muss eine Organisation alle betrieblichen, organisatorischen und technischen Risiken erfassen und analysieren.
- only summaries: Es ist davon auszugehen, dass für Fdl eine gewisse Hemmschwelle besteht, die Maßnahmen bei gefahrdrohenden Umständen nach Ril 408 umzusetzen.

</details>

<details><summary>181.md</summary>

**Hergang des Unfalls**

- only Gemma-001: RB 58043 kam nach einem Bremsweg von ca. 430 m in einem Abstand von ca. 70 m vor dem bereits wieder anfahrenden DGS 69235 zum Halten.
- only summaries: Die Zugnummern der beiden Güterzüge wurden aufgrund der Fortschaltstörung weiterhin in den jeweiligen ursprünglichen Zugnummernfeldern des Bf Jossa angezeigt, obwohl sich diese bereits auf der freien Strecke befanden.

**Ursachen des Unfalls**

- only Gemma-001: Es ist hinreichend wahrscheinlich, dass zumindest die Zugfahrt DGS 69235 in Richtung Burgsinn von dem Fdl und dem Auszubildenden nicht bewusst wahrgenommen wurde.

</details>

<details><summary>191.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Daraufhin entgleisen der elfte und auch der zwölfte Wagen, ebenfalls ein Fahrzeug der Gattung Laaeke, mit der Fahrzeugnummer 25 80 4367 127-4.
- only summaries: Die Fdl muss die Hilfssperren zur Sicherung der Weichen für die Hilfsfahrstraße demnach entweder nicht angebracht oder sie entfernt haben, bevor der Zug die Weiche 21 passiert hatte.
- only summaries: Daraufhin entgleisten der elfte und auch der zwölfte Wagen, ebenfalls ein Fahrzeug der Gattung Laaeke, mit der Fahrzeugnummer 25 80 4367 127-4.

</details>

<details><summary>196.md</summary>

**Ursachen des Unfalls**

- only summaries: Deren Rückstellkräfte lagen jedoch konstruktionsbedingt im unteren Toleranzbereich des Anwendungsspektrums und wurden zudem an der Weiche W 2 nicht vollständig ausgeschöpft.
- only summaries: Eine spontan auftretende Fehlfunktion ist jedoch aufgrund der Abhängigkeit vom Funktionieren eines einzigen Kontaktes ohne redundante Absicherung nicht vollständig auszuschließen.

</details>

<details><summary>199.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Sie überschrieb die Fehlnummer mit der Zugnummer des folgenden Zuges 48714, der zu diesem Zeitpunkt noch im Abschnitt 200 auf dem ZN-Monitor angezeigt wurde.
- only Gemma-001: Und so zögerte sie nicht, den kurz zuvor bei ihr vorbeifahrenden Zug 95307 als Zug 48714 an die Fdl in Weißenberg zurückzumelden.
- only Gemma-001: Als dies nicht gelang, wurde die Zugfahrt DPN 32547 in Weißenberg mit Fahrtstellung des Blocksig-nals A und, aufgrund der Rotausleuchtung, am folgenden Sbk 201 mittels Ersatzsignal Zs 1 durch die Fdl Weißenberg zugelassen.
- only Gemma-001: Da zu diesem Zeitpunkt noch immer der 48714 vor dem Esig A stand, nahm der Tf die Fahrtstellung des Signals auf und setzte sei-nen Zug in Bewegung.
- only summaries: Die Fdl Meerbusch-Osterath überschrieb die Fehlnummer mit der Zugnummer des folgenden Zuges 48714, der zu diesem Zeitpunkt noch im Abschnitt 200 auf dem ZN-Monitor angezeigt wurde.
- only summaries: Die Fdl in Meerbusch-Osterath meldete Zug 48714 auch irrtümlicher Weise zurück, obwohl dieser noch immer vor dem Esig stand.
- only summaries: Die Fdl in Weißenberg ließ die Zugfahrt DPN 32547 mit Fahrtstellung des Blocksignals A und, aufgrund der Rotausleuchtung, am folgenden Sbk 201 mittels Ersatzsignal Zs 1 zu.
- only summaries: Sie ließ die Zugfahrt zu, ohne den Tf zuvor durch schriftlichen Befehl aufzufordern, entsprechend den Regeln der Ril 408.0622, den Zugfolgeabschnitt hinter dem Sbk 201 auf Sicht zu befahren.

**Ursachen des Unfalls**

- only Gemma-001: Die Handlungen der Fdl in Weißenberg und Meerbusch-Osterath waren ursächlich für die Ent-stehung des Unfalls.
- only Gemma-001: Die Fdl der Abzw Weißenberg ließ den Zug 95307 durch eine Fehleingabe in der ZN mit falscher Zugnummer 66365 in Richtung Meerbusch-Osterath abfahren.
- only summaries: Die Handlungen der Fdl in Weißenberg und Meerbusch-Osterath waren ursächlich für die Entstehung des Unfalls.
- only summaries: Die Fdl der Abzw Weißenberg ließ den Zug 95307 durch eine Fehleingabe in die ZN mit falscher Zugnummer 66365 in Richtung Meerbusch-Osterath abfahren.

</details>

<details><summary>200.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Wie aus den elektronischen Fahrdaten und den Protokollen des Stellwerks (Historiendruck) hervorging, fuhr der Zug 49691 mit einer Geschwindigkeit von etwas weniger als 30 km/h, fahrstraßentechnisch gesichert in Bf Frankfurt (Oder) Oderbrücke ein.
- only summaries: Bei der Einfahrt in Bf Frankfurt (Oder) Oderbrücke entgleiste das erste Drehgestell des letzten Wagens zwischen der vom durchgehenden Hauptgleis (204) ins Bereitstellungsgleis (101) abzweigenden Weiche 115 und der folgenden Weiche 116, etwa in km 2,3.

</details>

<details><summary>231.md</summary>

**Hergang des Unfalls**

- only Gemma-001: Diese durch den Reisezug eingetragenen Kräfte, konnten von den im Bogen verbauten vorgeschädigten Spannbetonschwellen nicht mehr aufgenommen werden und führten im Bereich der Schienenauflager / Sicken schließlich zu einem Versagen der Struktur.
- only Gemma-001: Infolgedessen kam es zu einer einseitigen Verschiebung des gesamten äußeren Schwellenauflagers zur Bogenaußenseite.
- only Gemma-001: Hierdurch kam es zu einer unzulässigen Spurerweiterung und der Zug verlor zunächst den Kontakt zu der in Fahrtrichtung linken Schiene, während die in Fahrtrichtung rechte Schiene weiter befahren wurde.
- only Gemma-001: An dieser Stelle kam es zu einem mechanischen Zwang, so dass auch die noch auf der rechten Schiene laufenden Räder zur Bogenaußenseite gedrückt wurden und die Schiene überkletterten.
- only Gemma-001: In Folge der Entgleisung rutschten der dritte und vierte Wagen die Böschung in Richtung der parallel verlaufenden Bundesstraße herunter.
- only summaries: Beim Befahren der im Bogen verbauten Spannbetonschwellen kam es zum Versagen der Struktur und zu einer Verschiebung des gesamten bogenäußeren Schwellenauflagers zur Außenseite.
- only summaries: Dies führte zu einer Spurerweiterung und in Folge dessen zur Entgleisung des Zuges.
- only summaries: Infolge der Entgleisung rutschten der dritte und vierte Wagen die Böschung in Richtung der parallel verlaufenden Bundesstraße herunter.
- only summaries: Die anderen Fahrzeuge des Zuges kamen im entgleisten Zustand auf dem Gleis zum Stehen.

</details>

<details><summary>234.md</summary>

**Hergang des Unfalls**

- only summaries: Kurz vor der Einfahrt in den Bf Seelze Ost nahm der Tf den Signalbegriff Vr 0 „Halt erwarten“ am Einfahrtsignal (Esig) wahr und leitete eine Betriebsbremsung ein.

**Ursachen des Unfalls**

- only Gemma-001: Im Ergebnis wurde festgestellt, dass der zum Ereigniszeitpunkt zuständige Tf trotz der nachweislich durchgeführten Überwachungen und regelmäßigen Fortbildungen kein Situationsbewusstsein für das Erfordernis der Durchführung einer vereinfachten Bremsprobe hatte.
- only summaries: Im Ergebnis wurde festgestellt, dass der zum Ereigniszeitpunkt zuständige Tf trotz der nachweisweislich durchgeführten Überwachungen und regelmäßigen Fortbildungen kein Situationsbewusstsein für das Erfordernis der Durchführung einer vereinfachten Bremsprobe hatte.

</details>

<details><summary>237.md</summary>

**Ursachen des Unfalls**

- only summaries: Nach Abmeldung der Arbeiten gemäß Betra und vor Aufhebung der zugehörigen Sperrungen lagen der Fdl-in keine eindeutigen Informationen über den tatsächlichen Belegungszustand des Gleises 2 im Bf Dorsfeld vor.
- only summaries: Dieser Umstand wurde durch die BEU als ursächlicher Faktor [F1] gewertet, da keine eindeutige Übersicht über die Gleisbelegungen vor dem Aufheben der Gleissperrung mehr bestand.
- only summaries: Die Fdl-in führte die nach Regelwerk Ril 408.0471 Abschnitt 2 Abs. 4c) obligatorische Abschnittsprüfung nicht unmittelbar im zeitlichen Zusammenhang mit der Meldung über den Wegfall der Anlässe für die Gleissperrungen durch, sondern erst nachdem die Stopfmachine 24146 um 00:54:29 Uhr aus dem Bf Dorsfeld ausgefahren war.
- only summaries: Die Durchführung der Abschnittsprüfung durch die Fdl-in wurde durch die BEU als ursächlicher Faktor mit Bezug auf den Eintritt des Ereignisses gewertet [F2].
- only summaries: Der BÜB bestätigte ihr daraufhin unverzüglich, dass das gesamte Gleis 2 neben der Befahrbarkeit auch frei von Fahrzeugen sei, was die Fdl-in wiederholte.
- only summaries: Die getroffene Feststellung des „Freiseins“ und Abgabe der Meldung durch den BÜB wurde durch die BEU als ursächlicher Faktor [F3] gewertet, dessen Ausschluss das Ereignis aller Wahrscheinlichkeit nach verhindert hätte.

</details>

