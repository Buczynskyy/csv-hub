Czym jest ten projekt?

Jest to prosta web aplikacja, która umożliwia upload pliku tekstowego (CSV) zawierającego dane użytkowników. 
Zawartość pliku jest, przetwarzana i przechowywana pamięciowej bazie danych H2.

Rest api umożliwia odczytanie takich danych jak:
- ile jest użytkowników łącznie
- lista użytkowników posortowana po wieku + paginacja wyników
- najstarszy użytkownik z numerem telefonu

Dodatkowe założenia:
-Upload kolejnego pliku powinien dodawać użytkowników do istniejącego zbioru.
-Jest opcja usunięcia z pamięci wybranego użytkownika bądź całej ich listy.
-Jest możliwość wyszukiwania użytkowników po nazwisku.

Przykładowy plik: https://pastebin.com/raw/miWfmfVX

Plik jest bardzo kiepskiej jakości. Przyjęte założenia:
- imię, nazwisko i data urodzenia są danymi 'obowiązkowymi' dla każdego użytkownika
- numer telefonu składa się z dziewięciu cyfr
- numer telefonu jest unikalny (Jeden użytkownik - jeden numer telefonu)

Aplikacja to "gołe" REST API prezentujące dane w postaci json.