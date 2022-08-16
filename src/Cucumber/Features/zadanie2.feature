Feature: zadanie2

  Scenario Outline: zamówienie z obowiązkiem zapłaty wybranej ilości sztuk Hummingbird Printed Sweater
  w wybranym rozmiarze

    Given użytkownik zaloguje się
    When użytkownik wybierze <produkt> w ilości <sztuk> i <rozmiar> oraz doda do koszyka gdzie rozmiar 1-S, 2-M, 3-L, 4-XL
    And przejdzie do checkout i dokona zakupu poprzez zamówienie z obowiązkiem zapłaty
    Then zrobi screenshot z testu <nr> z potwierdzeniem zamówienia <produkt> i kwotą
    And sprawdzi czy zamówienie znajduje się w historii zamówień z odpowiednim statusem i kwotą
    And zamknie przeglądarkę

    Examples:
      | nr | produkt                     | sztuk | rozmiar |
      | 1  | Hummingbird Printed Sweater | 5     | 2       |
      | 2  | Hummingbird Printed Sweater | 10    | 1       |
      | 3  | Hummingbird Printed Sweater | 15    | 3       |
      | 4  | Hummingbird Printed Sweater | 20    | 4       |