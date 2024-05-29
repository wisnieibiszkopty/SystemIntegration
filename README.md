Projekt Integracja systemow

SteamCharts

Trzeba wyselekcjonowac gry do analizy, wyrzucic te które nie maja danych na twitchu albo maja ich za mało.
Problem przy tworzeniu bazydanych zwiazany z id, najpewniej cos spowodowane relacją miedzy tabelami
TODO Dokonczyc jsonParser i xmlParser.

Pierwszy rekord SteamCharts nie zawiera wartości gain, gdyż nie jest porównywany z danymi z poprzedniego miesiąca
Wartość ta została zamieniona na 0. Podobny problem wystepował również przy odchylenie standardowe.

Początkowo struktura bazy danych odpowiadała plikom csv - występowały dwie tabele dla danych z Twitcha i Steama.
Nie było to jednak optymalne rozwiązanie do dalszego użytku w aplikacji, dlatego od teraz istnieje ogólny obieg gra,
do którego przypisane są rekordy zawierające informacje na temat statystyk z danego miesiąca.
