Szanowni Studenci,


- punktacja: wszelkie konkretne wartości, takie jak punkty za czas, za monety, itd., 
a przy okazji także początkowa liczba żyć, bomb, itp., powinny wynikać z plików konfiguracyjnych 
(serwera konfiguracyjnego w wersji sieciowej) - jeśli różne poziomy trudności: to kilka zestawów parametrów? (tak sugerują przykłady w sekcji 'Poziomy').

- funkcjonalność sieciowa: "(...)ponieważ i tak nasz serwer będzie uruchamiany lokalnie" - a to niby dlaczego? Sprawdzanie projektu będzie się
odbywało na dwóch komputerach, gra będzie musiała znać adres serwera (plik konfiguracyjny, ewentualnie okno gdzie gracz wprowadza - ale to mniej wygodne),
można więc sprawdzać dostęp do tego konkretnego serwera; ponadto "funkcjionalność sieciowa" jest jednym z wymagań projektu (za okreśłoną liczbę punktów), 
więc nie należy tego tratować jako "elementu dodatkowego"

- elementy dodatkwoe: chodzi przed wszystkim o pomysły typu: dodatkowe życie, moneta (które "ukrył" Pan bez szczegolnego uwypuklenia w tekście :-)), 
i tu przydałoby się kilka (3-5, zależnie od ich skomplikowania); poziomy trudności mogą zostać uznane jako jeden z elementów dodatkowych, 
realizacja sieci (w zakresie serwera konfiguracyjnego) - nie jest elementem dodatkowym, 
zakładanie konta i logowanie - mogłoby być, tylko co z tego ma wynikać dla gry? Jeśli tylko możliwość pobrania konfiguracji - to patrz wyżej :-)

Etap zaliczony.

Zwracam uwagę na (komentarz ogólny, do wszystkich):

1. wszelkie "PARAMETRY" gry, takie jak: liczba punktów za określone elementy (wroga, poziom, czas, itd.), początkowa liczba żyć czy bomb, cza na przejście... 
oraz wszystkie inne tego typu parametry związane z rozgrywką (nie GUI!) powinny być odczytywane z konfiguracji a NIE zapisane na stałe w kodzie;<br/>
  wygląd plansz (w tym m.in. ich wielkość - liczba wierszy/kolumn, ale także liczba i początkowe rozmieszczenie wrogów) oczywiście też z konfiguracji - jeśli 
różne poziomy trudności: to różne opisy? albo chociaż 'modyfikatory' zapisane w konfiguracji; <br/>
  w szczególności liczba plansz składających się na grę (-> scenariusz gry) MUSI wynikać z konfiguracji (np. jawny parametr; 
albo określona konwencja nazywania plików z definicją planszy - brak kolejnego pliku oznacza zakończenie gry; 
albo taki zapis plansz, z którego wynika ich liczba; albo...);

2. dokumentację należy tworzyć na bieżąco;

3. zapoznać się z wszystkimi wymaganiami (uwagi ogólne - 11 punktów; co wymagane na zaliczeniu projektu; co na kolejne etapy...) - nie wszystkie wymagania "szczegółowe" mają swoje bezpośrednie odzwierciedlenie w etapach, są sprawdzane dopiero przy zaliczeniu projektu, a ich brak skutkuje obniżeniem oceny;

4. na kolejny etap przygotować "moduł odczytu i parsowania plików konfiguracyjnych": MODUŁ, czyli jakąś odrębną klasę (pakiet... interfejs... - myśleć od razu przyszłościowo o funkcjonalności sieciowej!) odczytującą konfigurację i udostępniającą wartości parametrów poprzez wygodny interfejs, a nie jakąś tam "metodę pomocniczą" działającą "tu i teraz";
  a także wszystkie inne elementy zgodnie z opisem na stronie projektu


Z poważaniem,<br/>
Grzegorz Galiński
