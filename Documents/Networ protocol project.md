# Protokół sieciowy PROZE

### Model przetwarzania
Jest to model klient-serwer. Jego cechy:
* rozproszony
* dwuwarstwowy:
    * pierwszą warstwą jest gruby klient, co oznacza, że cała logika biznesowa zawarta jest w aplikacji klienckiej
    * drugą warstwą jest serwer, który przechowuje dane
### Działanie serwera
* Serwer jest niezależną jednostką, działającą bezobsługowo w postaci oddzielnej aplikacji
* Przechowuje wszystkie swoje pliki lokalnie
* Po uruchomieniu serwer wczytuje dane ze swoich plików konfiguracyjnych
* Serwer oczekuje na zgłoszenia od klienta
* Po otrzymaniu zgłoszenia serwer obsługuje to zgłoszenie, tzn. analizuje, przygotowuje i odsyła
* Serwer może obsługiwać wiele zgłoszeń na raz, ponieważ ma strukturę wielowątkową
### Działanie klienta
* Jest to aplikacja przystosowana do działania w dwóch trybach:
    * online – klient wysyła żądania do serwera w celu uzyskania plików konfiguracyjnych
    * offline – klient korzysta z własnych, lokalnie umieszczonych plików konfiguracyjnych
* Po uruchomieniu aplikacji użytkownik wprowadza adres IP i numer portu oraz wybiera w jakim trybie aplikacja ma korzystać z plików konfiguracyjnych. Gdy połączenie nie zostanie nawiązane, pojawia się komunikat (zgłoszony jest wyjątek) o tym, że serwer o podanych parametrach jest offline
* Klient po wybraniu żądania nawiązuje połączenie z serwerem, następnie wysyła żądanie, czeka na odpowiedź, a po otrzymaniu przetworzonego przez serwer zgłoszenia sam przetwarza je na własny użytek
### Architektura
* Po stronie klienta: aplikacja zawiera klasę Client, która jest połączona z główną klasą programu relacją agregacji całkowitej
* Po stronie serwera: aplikacja zawiera klasę Service, która również połączona jest z główną klasą programu relacją agregacji całkowitej

W obu podrzędnych klasach znajdują się metody związane z komunikacją między sobą przez sieć.
### Protokół
* Jest to typ tekstowy
* Jednostką przesyłu informacji jest obiekt klasy String lub też struktura o zmiennej długości, np. Vector

### Działanie protokołu

* Klient wysyła żądanie o właściwości dla okien oraz ich elementów\
`C: getConfig => S`
* Serwer zwraca obiekt `response` **(String)**, który zawiera:
    * Tytuł gry (do okien): `title = Dyna Blaster`
    * Nagłówek okna logowania: `heading = Enter Your nickname`
    * Tekst przycisku wejścia: `enter = Enter`
    * Tekst etykiety pseudonimu: `nickname = Nickname`
    * Tekst przycisku wyjścia: `exit = Exit`
    * Tekst przycisku zatwierdzenia: `ok = OK`
    * Tekst przycisku poziomu trudności "łatwy": `easy = Easy`
    * Tekst przycisku poziomu trudności "średni": `medium = Medium`
    * Tekst przycisku poziomu trudności "trudny": `hard = Hard`
    * Tekst przycisku pokazania rankingu: `scores = Show scoreboard`
    * Tekst etykiety wyboru poziomu trudności: `difficulty = Choose the level of difficulty:`
    * Tytuł okna rankingu: `scoreboard = Top 10 scores`
    * Czas animacji gracza: `time = 500`
    * Czas animacji ruchu gracza: `move_time = 3`
    * Czas animacji ruchu bomby: `bomb_time = 3`
    * Początkowa szerokość obiektu mapy: `start_width = 1792`
    * Początkowa wysokość obiektu mapy: `start_height = 988`
    * Port serwera: `port = 990` (używany tylko dla samego serwera)
    
    Wszystkie wartości są rozdzielone znakiem **-**.\
    `S: giveConfig title heading enter nickname exit ok easy miedum hard scores difficulty scoreboard time move_time
     bomb_time start_width start_height port => C`
* Klient wysyła żądanie o scenariusz gry\
`C: getScenario => S`
* Serwer zwraca scenariusz w postaci obiektu `scenario` **(String)**, gdzie wartości między znakami **-** są kolejnymi poziomami 
scenariusza **(int)** \
`S: giveScenario scenario => C`
* Klient wysyła żądanie o konfigurację poziomów trudności gry, wraz z wybranym przez użytkownika poziomem trudności
w postaci liczby `difficulty_level` **(int)**, gdzie poziom 'Easy' = 1, 'Medium' = 2, 'Hard' = 3\
`C: getDifficulties difficulty_level => S`
* Serwer zwraca obiekt typu String, który składa się z dwóch wartości rozdzielonych znakiem **;**, gdzie pierwsza jest początkową 
ilością punktów życia gracza `HP` **(int)**, a druga początkową ilością bomb gracza `startBombs` **(int)**\
`S: giveDifficulties startBombs HP => C`
* Klient wysyła żądanie o konfigurację konkretnego poziomu, gdzie konkretyzacja wyboru zachodzi dzięki parametrowi `levelIndex`
**(int)**\
`C: getLevel levelIndex => S`
* Serwer zwraca konfigurację wybranego poziomu w postaci obiektu `configuration` **(String)**, gdzie każda kolejna linijka 
mapy oddzielona jest znakiem **-**\
`S: giveLevel configuration => C`
* Klient wysyła żądanie o aktualną listę wyników (tych na serwerze)\
`C: getRanking => S`
* Serwer odsyła listę wyników w postaci obiektu `ranking` **(String)**, gdzie struktura wygląda tak: *score*-*nick*.
Wszystkie takie struktury są od siebie oddzielone znakiem **_**\
`S: giveRanking ranking => C`
* Klient wysyła uzyskany wynik przez gracza `score` **(int)** oraz jego pseudonim `nick` **(String)** aby te informacje
zostały zapisane na serwerze\
`C: saveScore score nick => S`
