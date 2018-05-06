# TerningSpillet_Snyd

Velkommen til terningespillet "Snyd".

Terningespillet er et klassisk terningespil, hvor det ligesom i Meyer gælder om at gætte på kombinationer af terninger.
Spillet spilles typisk af 2-5 spillere, hvor hver spiller starter med 6 terninger i hver af deres raflebærger. Runden starter ved, at alle ryster deres terninger og kigger på dem.
Spilleren skal udfra sine terninger og alle andre terninger der er med i spillet vurdere, hvor mange af terningerne der mindst viser en bestemt værdi.
Spilleren kan så lave et gæt, hvor man gætter på hvor mange terninger der mindst viser en bestemt værdi.
Her kan reglerne om, hvordan man tæller terningerne op variere udfra hvem du spørger, men reglerne for vores java version af spillet er som følgende:

- Jokere: Terninger der viser '1' må tælles som joker og kan derfor bruges som en vilkårlig terning.
- Trappe reglen: Hvis man har terninger der viser fra 1 til antal terninger man har, så har man en "trappe".
Hvis man har en trappe og har x terninger må man tælle sine terninger som x+1 antal 1'ere.


Eksempel på trappe reglen: Spiller 1 har følgende 5 terninger [1,2,3,4,5] og har derfor en trappe. Spiller 1 ved derfor at han selv har f.eks. 6 6'ere eller 6 2'ere.
Efter at Spiller 1 har gættet går turen videre til Spiller 2 og han kan nu to følgende ting:

- Lave et gæt ligesom spiller 1, hvor han enten går en op på antal eller værdien.
- Kalde "Snyd" på Spiller 1 da han ikke tror på hans gæt.

Runden slutter når en spiller der har turen kalder snyd på den forrige spiller. Når dette sker løfter alle spillere bærgerne og terningerne tælles op.
Hvis der er det antal som der blev gættet på, taber ham der kaldte snyd og omvendt, hvis der ikke er det antal der blev gættet på taber ham der gætter.
Alle andre end taberen kan nu fjerne en terning fra deres bærger og runden starter igen ved, at taberen af forrige runde starter.
Spillet slutter når der kun er en spiller tilbage med terninger i raflebærgeret og han har så tabt spillet.


Serveren skal køres fra terminalen da man skal angive port, antal spillere og terninger med tastaturet. Når man er i den mappe, hvor filen ligger i kan man bruge kommandoen "Java -jar TerningSpillet_Snyd_Server.jar" for at starte serveren.


Lavet af Mads og Christian 
