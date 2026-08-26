Advanced Programming Techniques
Coding Conventions

## 1. Belangrijk:

* Geef steeds de voorkeur aan de technieken die je geleerd hebt tijdens de cursus. Als
  je een andere manier van werken gebruikt moet je kunnen argumenteren waarom  
  je dat doet.
* Schrijf geen code zonder het onmiddellijk uit te proberen! Werk in kleine stapjes!
* Alle code in het engels, data/ui/website in het nederlands
* Formatteer je code (ctrl-alt-l in IntelliJ, shift-alt-f in VsCode)
* als je een goede reden hebt om niet volgens deze conventies te werken dan moet er een comment in de code staat met
  uitleg daarover

## 2. Specifiek voor het vak ServerSide:

* Gebruik h2-mem om te developen (profile dev)
* Gebruik h2-file om db-update te verifieren (profile h2file)

## 3. Naming conventions

### Algemeen

* Denk na over het gebruik van enkelvoud en meervoud!!!

### Java

* Java is case-sensitive
* Variabelen kleine letter, camelCase
* Functions kleine letter, camelCase
* Packages, kleine letters 
* Test packages voor controllers worden in meervoud benoemd, analoog aan de endpoints en requests die ze testen, bijv. `books` i.p.v. `book`
* Classes hoofdletters, CamelCase

### SQL

* SQL is case-insensitive
* Underscores om woorden te scheiden
* Conversie CamelCase -- underscores gebeurt automatisch door Spring

### Html

* Url alleen kleine letters en -
* html files alleen kleine letters en -

## 4. Java

### Algemeen

* Variabelen: scope zo klein mogelijk
* Splits lange functies op
* Niet uit een loop springen met break, continue of return
* Return in een functie voor pre-condities is ok (of in heel simpele functies)

### Classes

* Optimaliseer imports: ctrl-alt-o (IntelliJ) of shift-alt-o (VsCode)
* Private fields in een class bij elkaar en bovenaan in de class
* Dan constructors
* Dan eigen methodes
* Dan getters en setters
* Dan toString, hashCode en equals

### DTO

* Gebruik Java records als DTO

### Moderne toepassing van Java

* Use pattern matching for instanceof in services (maar bij voorkeur geen instanceof)
* Apply switch expressions and pattern matching in switch
* Use text blocks for multi-line strings
* Apply sealed classes where appropriate
* Use String templates (JDK 21+ preview, JDK 25 stable)
* Apply sequenced collections (JDK 21+)
* Use Stream.toList() instead of Collectors.toList()
* Apply constructor injection instead of field injection (@Autowired on fields is allowed in test classes)

## 5 Spring Boot

### Entity classes

* Naam Entity is altijd met een hoofdletter
* Geen methods toevoegen in Entity Classes (alleen getters en setters). Waarom: Single Responsibility Principe. Deze
  classes dienen om data uit te wisselen met de db
* Booleans: geen isXxxx als kolomnaam (geeft issues in queries)
* Gebruik altijd de default table names en column names. Dat wil zeggen: geen @Table en @Column annotations 
* De tabellen in de DB moeten automatisch opgebouwd worden, dus geen create tables in schema.sql behalve voor de 2
  security tables (USERS en AUTHORITIES)

### Controller classes

* Geen state in Controller (state = private var die verandert omwille van user acties of tijd)
* Geen data deleten maar archiven/inactiveren. Dus voor een gebruiker is het alsof hij een item kan deleten, maar het
  blijft wel zitten in de db met een speciale status zodat de app-owners het nog wel kunnen gebruiken voor
  statistieken (uitzondering: REST APIs mogen DELETE endpoints hebben; het deactiveren/archiveren gebeurt in de UI)

### Request Handlers

* Url in mapping altijd met /, maar houd rekening met controller-wide @RequestMapping  (anders mogelijk problemen met deploy) 
* Niet filteren of sorteren in java, laat dat doen door de database!
* Correct gebruik van Request Param (filteren, sorteren, extra input fields doorsturen naar server ) en Path Variable (
  id van een specifieke entity)
* Request Handlers zijn altijd correct gedocumenteerd in Swagger
* Voor elke Request Handler is er een voorbeeldje in een van de HTTP files 

### html (react/JSX)

* Responsive!
* Geen form als je dit met een simpele `<a>` kan doen
* Geen `<hr>` gebruiken om te stylen
* Geen `<br>` gebruiken om te stylen
* Geen `<table>` gebruiken om te stylen
* Geen Javascript alerts
* Form: altijd expliciet de action vermelden. Als je dat niet doet stuur je GET naar de huidige url, maar dat is niet
  altijd duidelijk als je naar de code kijkt

### Repositories

* Maak een Repository voor elk businessobject. Waarom: makkelijker om data in/uit database te halen en data persistent
  te maken

### Relations

* Definieer altijd beide kanten van de relatie in de Entity classes. Bij de ene kant staat mappedBy, bij de andere kant
  niet. Die laatste is de "owner" van de relatie.
* Maak het meest stabiele object owner van een relatie. Waarom: update via repository werkt alleen aan de owner kant.
* Zorg dat mappedBy er staat aan de andere kant, anders zijn dit 2 verschillende relaties en worden er 2 relation
  tabellen gemaakt
* Altijd beide kanten lazy

### Db Write

* Load-and-save pattern: altijd eerst het object laden uit de db, dan overschrijven met waardes uit de form, dan saven.
  Als je @ModelAttribute correct gebruikt gebeurt dat automatisch. Waarom: een field dat niet in de form voorkomt wordt
  anders overschreven met null.

### Security

* Welke request door wie mag toegepast worden: in SecurityConfig (dus niet testen op Principal daarvoor)
* Houd de Security-tabellen (USERS en AUTHORITY) gescheiden van de BU-tabellen (bvb Animal in Party). Definieer een 1-1
  relatie tss BOOKSUSER en USERS (met FK userName)
* Geen Entity voor tables USERS en AUTHORITY.
* Niet vertrouwen op de frontend om te voorkomen dat bepaalde requests niet door bepaalde rollen/users mogen uitgevoerd
  worden 
