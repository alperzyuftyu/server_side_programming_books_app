# Project Context en Codeerrichtlijnen
## 1. Technisch Profiel
- **Stack:** Java 24, Spring Boot 3.x, H2 Database.
- **UI:** React (REST API).
## 2. Codeerstandaarden
- **Naming:** Gebruik beschrijvende namen (bijv. `processUserRegistrationRequest` in plaats van `processReg`).
- **Clean Code:** Geen nieuwe inline comments. Code moet zelfdocumenterend zijn. Bestaande comments behouden en controleren op correctheid.
- **Conventies:** Zie `docs/CONVENTIONS.md` voor gedetailleerde projectafspraken.
## 3. Testdata & Locatie
- **Minimum:** Collecties/arrays moeten altijd minimaal 10 items bevatten. Engelse testdata voor boeken en auteurs is toegestaan mits de meerderheid Nederlands is. Genres moeten in het Nederlands zijn.
## 4. Instructies voor AI-gedrag
- Wees direct en professioneel. Vermijd excuses, overmatige beleefdheid en conversational fluff (zoals "U heeft volledig gelijk" of "Mijn excuses"). Reageer puur technisch, feitelijk en zakelijk.
- Geen conversational filler of beleefdheidsvormen.
- Gebruik altijd het volgende commando om unit tests uit te voeren: `mvn test "-Dskip.installnodedeps=true" "-Dskip.npm=true" "-Dmaven.antrun.skip=true"`
- Bij UI-componenten: Gebruik uitsluitend Nederlands.
- Code moet altijd production-ready en modulair zijn.
- Stel altijd eerst een oplossing voor, pas niet onmiddellijk de code aan. Dit geldt zonder uitzondering voor elke codewijziging, inclusief kleine aanpassingen, bugfixes, en feedbackrondes binnen een lopende taak.
- **Planning Mode:** Maak bij grotere of complexere wijzigingen altijd gebruik van een gedetailleerd `temp/implementation_plan.md`. Zodra dit plan door de gebruiker is goedgekeurd, voert u de stappen zoveel mogelijk in grotere blokken achter elkaar uit om tussentijdse feedbackvragen te minimaliseren.
- **Plan-presentatie:** Sla het implementatieplan altijd op als artifact (`temp/implementation_plan.md`). Vermeld in de response dat het plan klaar staat met een link naar het bestand.
- **IDE / onderwijs:** Gebruik standaard Cursor/VS Code-shortcuts en -instellingen. Stel geen custom keymaps, IntelliJ-keymap-extensies of afwijkende IDE-configuratie voor.
- **Uitleg aan gebruiker:** Vermeld IntelliJ-equivalenten hoogstens ter orientatie; geef altijd eerst de standaard Cursor-shortcut (Windows).
## 5. Beperkingen en Scope (Cruciaal)
- **Minimale invasie:** Pas uitsluitend de code aan die expliciet gevraagd is.
- **Geen ongevraagde refactoring:** Wijzig geen bestaande code, methoden of imports die niet direct gerelateerd zijn aan de gevraagde wijziging.
- **Geen verlies van commentaar:** Behoud bestaande documentatie en comments in de bestanden, tenzij ze direct geüpdatet moeten worden door de wijziging.
- **Geen wijzigingen buiten scope:** Als een wijziging invloed heeft op andere bestanden, meld dit dan eerst voordat je de wijziging doorvoert.
- **Geen applicatieherstart:** Start de applicatie nooit zelf opnieuw op. Er wordt gewerkt met hot-deploy. Geef aan wanneer een handmatige herstart vereist is.
- **Tijdelijke bestanden:** Sla alle tijdelijke bestanden, gegenereerde mappen of werkbestanden die niet in Git horen (zoals implementatieplannen of uitgepakte afbeeldingen) altijd op in de centrale map `temp/`.
## 6. Override Systeem Planning-Mode
- Dit project overschrijft de ingebouwde "trivial change = no plan" heuristiek van het systeem.
- Elke codewijziging — ongeacht de omvang — vereist voorafgaande goedkeuring van de gebruiker.
- Voer **nooit** code-aanpassingen uit op basis van een eigen inschatting van "eenvoudigheid".
- Wacht altijd op expliciete bevestiging alvorens te implementeren.