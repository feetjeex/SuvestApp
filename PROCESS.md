# Day 2 (Dinsdag)
PROCESS.md afgemaakt. Nagedacht over welke classes te gebruiken. Vind het op dit moment nog lastig om goed te overzien welke classes
ik verder nog nodig zal hebben. Redelijk duidelijk hoe de 'flow' van de App gaat zijn. Grootste obstakels op dit moment waarschijnlijk de 
OCR Api en het opslaan van de screenshots.

# Day 3 (Woensdag)
Met Darian overlegd over m'n App. Aantal punten naar voren gekomen: 1. De OCR Helper zal waarschijnlijk moeite hebben om behalve de prijs
veel bruikbare informatie uit een screenshot te krijgen. 2. Het is vrij bewerkelijk handmatig een URL toetevoegen bij elke screenshot. Een
oplossing zou kunnen zijn, bij een druk op de screenshot naar de site van de retailer te gaan en wellicht hard coded een search query achter
de URL te plakken. 3. De app moet bij het toevoegen van screenshots naar de foto rol gaan, deze niet zelf eerst importeren en weergeven.

Het derde punt is inmiddels gerealiseerd. Vandaag ook bijna alle activities geimplementeerd en de navigatie werkt al redelijk. 

# Day 4 (Donderdag)

Vandaag moet ik nadenken over in welke vorm ik de screenshots ga gebruiken. Ga ik kopiëeen maken of laat ik ze rechtstreeks vanuit de foto's app?
Ik neig meer naar het gebruiken van de URI's en de juiste foto's vervolgens te laden.

# Day 5 (Vrijdag)

Veel progress gemaakt vandaag. Prototype werkt. Er is wel een nieuw probleem ontstaan: De intent die ik tot nu toe gebruikte geeft geen long term
permission om de foto's te gebruiken. Ik heb de intent aangepast, alleen laat deze gebruikers nu slechts 1 foto per keer selecteren.
De multi select is wel een belangrijk onderdeel van de App.

# Day 6 (Dinsdag)

Vandaag progress gemaakt met Google Vision. De OCR werkt goed. Ik heb besloten om te blijven werken met Uri's. Dit omdat deze makkelijker op te slaan
zijn als (semi) String in een SQLite database. Helaas nog geen progressie gemaakt met de Multi-Select bij foto's uitkiezen.

Over de Stand Up: Darian wil gebruik maken van een 'preview' bij het klikken op een node op zijn kaart (Google Maps API). Ik vertelde hem dat deze functionaliteit standaard
al in de API zit. Bart is z'n code aan het omschrijven om ook de Google Maps API te gebruiken. Hij had verder geen vragen / open issues. Ikzelf: Ik
twijfelde over hoe ik keywords wilde gebruiken in m'n code. Ik wil dat de OCR een text teruggeeft, die vervolgens wordt vergeleken met een aantal
Keywords (Zoals nike of zalando). Bart en Darian waren van mening dat het gebruiken van een CSV met deze keywords de beste manier was.

# Day 7 (Woensdag)

Eerste implementatie van de OCR is gelukt. Een aantal screenshots toegevoegd en hier de OCR op los gelaten. De OCR herkent een aantal teksten op
de screenshot. Helaas lijkt de OCR een deel niet te lezen, mogelijk omdat het font in het lichtgrijs is. Ook een aantal enums aangemaakt die de
Keywords bevatten waarmee de strings uit de OCR vergeleken worden. Ook nog een categorie aan de product klasse toegevoegd: de kleur.

Stand Up: Darian is erin geslaagd z'n liftplekken als CSV in z'n app te laden. Hij gaat nu verder met het aanmaken van accounts en die opslaan
in een database. Ik vroeg me af of er geen API was om makkelijk CSV's te laden.

# Day 8 (Donderdag)

Besloten nog een extra veld aan m'n product / screenshot toe te voegen: de kleur van het product. Tijd besteed aan het implementeren van de multi-select. Deze werkt echter nog steeds niet goed. De oude manier van screenshots toevoegen (multi-select) is deprecated sinds Android versie 19.

# Day 9 (Vrijdag)

Gewerkt aan het implementeren van Enums met Keywords waarmee de string uit de OCR mee vergeleken wordt. Ik merkte dat ik betere resultaten behaalde
(bij het vergelijken) nadat ik de String opknipte in substrings (na elke whitespace). Extra code toegevoegd om elke Textblock in stukjes op te knippen en deze een voor een met alle Enums te vergelijken. Ik was eerst bang dat dit tot vertraging in de App zou leiden, maar dit valt mee (geen verschil te merken).

# Day 10 (Maandag)

Vandaag gewerkt aan het implementeren van een knop om de resultaten in de listview te filteren en te sorteren. Ik denk het het handigst is als ik voor elke aanpassing die de gebruiker doet, ik een nieuwe SQLite rawQuery uitvoer. Ik zou dan ook de Adapter weer moeten verversen voordat de nieuwe volgorde of nieuwe selectie aan screenshots zichtbaar zijn.

# Day 11 (Dinsdag)

Gewerkt aan de filter knoppen. Ik ga popupMenu's gebruiken waarmee de gebruiker kan kiezen welke screenshot hij of zij wil zien, op basis van kleur en retailer. Ik begin met de knop om gebruikers de volgorde van de screenshots te laten veranderen. Probleem waar ik tegen aan liep was dat de datum die ik per screenshot toevoegde aan de database een String was. Op het moment dat ik deze opvroeg met die ingebouwde SQLite functionaliteit "ORDER BY Timestamp ASC (ascending) of DESC (descending)" veranderde er niks aan de volgorde van m'n resultaten. Ga hier morgen verder mee aan de slag.

# Day 12 (Woensdag)

Het probleem waar ik dinsdag tegen aan liep opgelost. De timestamp per screenshot wordt nu toegevoegd als een "sqltime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)", waardoor de SQLite functionaliteit "ORDER BY ASC / DESC" wel goed werkt.

# Day 13 (Donderdag)

Darian moet nog implementeren dat lifters en chauffeurs zich op een liftplek kunnen aanmelden. Hij denkt eraan een knop te maken bij elke liftplek.
Bij aanmelden wordt de chauffeur / lifter in een arraylist lokaal opgeslagen. Bij de echte versie moet dit natuurlijk online. Ik vroeg me af
of dat niet kon met de Rester server. Nagedacht over hoe ik de SQLite rawQueries ga vormgeven. Het handigst is waarschijnlijk om voor alle mogelijke combinaties een aparte SQLite rawQuery aan te maken (Zoals bijvoorbeeld wel een bepaalde kleur en volgorde, maar van alle retailers). Daarnaast dienen ook de popupMenu's van kleur en retailer dynamisch te worden gevuld met SQLite queries, aangezien gebruikers alleen moeten kunnen kiezen uit kleuren of retailers die daadwerkelijk dat type product hebben.

# Day 14 (Vrijdag)

De SQLite rawQuery's afgemaakt. Een deel van m'n oude code veranderd. Ik heb ervoor gekozen om een nieuwe klasse aan te maken (PreferenceHelper) deze heeft een aantal velden (Preferences) zoals bijvoorbeeld de kleur en de retailer. Bij het starten van de TypeActivity staat deze nog op blanco (behalve het type screenshot). De gebruiker kan dan in de popupMenu's aangeven welke voorwaarden (zoals de kleur) in de preferenceHelper worden ingevuld. Vervolgens word de gridView opnieuw gevuld met screenshots, maar alleen die screenshots die voldoen aan de voorwaarden in de preferenceHelper. 

# Day 15 (Maandag)

AndroidX toegevoegd aan de dependencies van m'n App. Ik heb deze keuze gemaakt zodat ik gebruik kon maken van Google Materials om bijvoorbeeld de styling van de buttons wat mooier te maken.

# Day 16 (Dinsdag)

Comments toegevoegd. Daarnaast ook een license toegevoegd. Uiteindelijk voor de Apache 2.0 license gekozen, vooral vanwege het onderdeel over patenten. Als ik het goed begrijp kunnen gebruikers bij een Apache 2.0 license niet aangeklaagd worden vanwege patent inbreuk.

# Day 17 (Woensdag)

Header files toegevoegd bij alle methods en classes van de App. Laatste comments toegevoegd en code opgeruimd. README aangepast.



