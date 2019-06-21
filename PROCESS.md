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

Vandaag progress gemaakt met Google Vision. De OCR werkt goed. Ik heb besloten om te blijven werken met Uri's. Dit omdat deze makkelijekr op te slaan
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

# Day 9 (Vrijdag)

Gewerkt aan het implementeren van Enums met Keywords waarmee de string uit de OCR mee vergeleken wordt. Ik merkte dat ik betere resultaten behaalde
(bij het vergelijken) nadat ik de String opknipte in substrings (na elke whitespace).

# Day 10 (Maandag)

Vandaag gewerkt aan het implementeren van een knop om de resultaten in de listview te filteren en te sorteren.

# Day 11 (Dinsdag)

Gewerkt aan de filter knoppen

# Day 12 (Woensdag)

# Day 13 (Donderdag)

Darian moet nog implementeren dat lifters en chauffeurs zich op een liftplek kunnen aanmelden. Hij denkt eraan een knop te maken bij elke liftplek.
Bij aanmelden wordt de chauffeur / lifter in een arraylist lokaal opgeslagen. Bij de echte versie moet dit natuurlijk online. Ik vroeg me af
of dat niet kon met de Rester server.



