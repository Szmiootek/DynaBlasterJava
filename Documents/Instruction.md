# Instrukcja użytkownika
### Podstawowe informacje
Celem gry jest zdobycie jak największej ilości punktów, jednocześnie unikając potworów, które próbują zabić użytkownika
strzelając bomby. Przejście do następnego poziomu następuje po wejściu w portal, który znajduje się gdzieś na mapie.
Przed rozpoczęciem rozgrywki gracz może połączyć się z serwerem lub grać w trybie offline, następnie wpisuje swój 
pseudonim oraz wybiera poziom trudności. Gracz zawsze rozpoczyna grę w lewym górnym rogu mapy.
### Uruchomienie gry
Grę należy uruchomić poprzez plik `DynaBlaster_App.jar` za pomocą skryptu powłoki bash `Run App.sh`.

### Zasady
Gracz steruje postacią po mapie. Mapa składa się ze ścian (niewrażliwych na strzał gracza) oraz z jednego przejścia (nie 
zawsze), przez które gracz bądź potwór może przejść (przejście jest bardzo podobne do ściany, co czyni je niemalże ukrytym). 
Na mapie znajdują się również monety oraz serduszka. Zazwyczaj na końcu korytarza bądź po drugiej stronie mapy znajduje się 
przejście do następnego poziomu. Gdy gracz straci wszystkie życia bądź przejdzie przez przejście ostatniego poziomu, gra 
kończy się i pokazuje się okno końca gry.

### Punktacja
Gracz otrzymuje punkty za:
* Zniszczenie potwora - **500** punktów
* Przejście do następnego poziomu - **1000** punktów
* Zdobycie serduszka - **25** punktów
* Zdobycie monety - **100** punktów
* Na końcu gry gracz otrzymuje dodatkowe **25** punktów za każdy pozostały punkt życia

### Sterowanie
Gracz porusza się za pomocą strzałek. `↑` porusza gracza w górę, `←` porusza gracza w lewo, `→` porusza gracza w prawo, 
`↓` porusza gracza w dół. Strzał odbywa się za pomocą `spacji`. Po kliknięciu `P` lub `esc` gra zostaje zapauzowana. 
Po ponownym naciśnięciu któregoś z tych przycisków gra zostaje odpauzowana.

### Poziomy trudności
Każdy poziom trudności daje graczowi różne parametry startowe. Oto lista:
* Poziom łatwy `Easy`: `HP` = 10, `StartBombs` = 15
* Poziom średni `Medium`: `HP` = 5, `StartBombs` = 25
* Poziom trudny `Hard`: `HP` = 3, `StartBombs` = 15

Dodatkowo wybranie poziomów **średni** bądź **trudny** skutkuje brakiem uzupełniania bomb przy przejściu do następnego
poziomu. Gdy gracz wybierze poziom **łatwy**, to przy przejściu do następnego poziomu jego obecna ilość bomb 
uzupełniana jest o startową ilość.

### Zachowanie potworów
Potwory poruszają się same po mapie. Ich początkowy kierunek jest losowy. Gdy potwór wejdzie w ścianę bądź w krawędź mapy
to zmienia kierunek na przeciwny do obecnego. Potwory strzelają czterema bombami na raz, w cztery różne strony, gdy gracz
wejdzie w obszar wykrywania tzn. w okrąg o środku w miejscu potwora i promieniu *8* razy większym niż aktualna szerokość 
potwora (w pikselach). Potwory po zniknięciu z mapy mogą wypuścić jeszcze jedną serię bomb.

### Tryby online/offline
* Gracz może grać w trybie offline — powoduje to wczytywanie plików konfiguracyjnych gry z lokalnego dysku
* Gracz może grać w trybie online — powoduje to wczytywanie plików konfiguracyjnych gry z serwera o podanym adresie `IP` oraz 
`porcie` (serwer musi być włączony, w przeciwnym razie gracz musi grać offline)