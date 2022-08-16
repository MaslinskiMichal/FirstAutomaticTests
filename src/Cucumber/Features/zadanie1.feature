Feature: zadanie1

  Scenario Outline: logowanie i edycja adresów

    Given otwarta przeglądarka na stronie mystore-testlab.coderslab.pl
    When użytkownik zaloguje się na wcześniej stworzone konto
    And wejdzie klikając w kafelek Adresses
    And użytkownik kliknie w + Create new address
    And wypełni formularz New adresses danymi pobranymi z tabeli Examples w Gherkinie <alias> <address> <city> <postcode> <phone>
    And sprawdzi czy dane w dodanym adresie są poprawne <alias> <address> <city> <postcode> <country> <phone>
    And usunie powyższy adres klikając w delete
    Then sprawdzi czy adres został usunięty <alias>
    And zamknie przeglądarkę

    Examples:
      | alias         | address       | city     | postcode | country | phone       |
      | Dom           | Marszalkowska | Warszawa | 00-624   | 17      | 123-456-789 |
      | Dom Wakacyjny | Kilinskiego   | Slupsk   | 76-200   | 17      | 134-456-789 |
      | Praca         | Zwyciestwa    | Lodz     | 90-047   | 17      | 134-345-789 |