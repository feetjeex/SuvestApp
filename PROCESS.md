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
