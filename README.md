# Minesweeper

Klasyczna gra Saper napisana w Javie z graficznym interfejsem opartym na JavaFX.

## Funkcje

- Menu startowe z konfigurowalnymi parametrami gry
- Trzy rozmiary planszy, trzy poziomy trudności, trzy rozmiary komórek
- Oznaczanie pól flagami (prawy przycisk myszy)
- Automatyczne odkrywanie pustych pól (algorytm DFS)
- Licznik flag, licznik czasu i przycisk resetu w nagłówku gry
- Wizualna informacja o wyniku (emotka w przycisku resetu)

## Wymagania

- Java 23+
- Maven 3.6+

## Uruchomienie

```bash
mvn clean javafx:run
```

## Rozgrywka

| Akcja | Opis |
|---|---|
| Lewy klik | Odkryj pole |
| Prawy klik | Postaw / usuń flagę |
| Przycisk 🙂 | Zresetuj grę |

Kliknięcie na minę kończy grę (😵). Odkrycie wszystkich bezpiecznych pól oznacza zwycięstwo (😎).

## Konfiguracja gry

**Rozmiar planszy:**

| Opcja | Wymiary |
|---|---|
| Mała | 10 × 10 |
| Średnia | 16 × 16 |
| Duża | 20 × 30 |

**Trudność** (procent min na planszy):

| Opcja | Miny |
|---|---|
| Niski | 1% |
| Średni | 15% |
| Wysoki | 20% |

**Rozmiar komórek:** Małe (20px) / Średnie (30px) / Duże (40px)

## Struktura projektu

```
src/main/java/minesweeper/
├── MinesweeperWindow.java    # Punkt wejścia aplikacji (JavaFX Application)
├── StartMenuController.java  # Kontroler menu startowego (FXML)
├── Game.java                 # Główny kontener gry
├── GameHeader.java           # Nagłówek z licznikami i przyciskiem resetu
├── Minefield.java            # Plansza gry (logika i rendering)
└── Field.java                # Pojedyncze pole planszy
```

## Technologie

- Java 23
- JavaFX 17
- Maven
- JUnit 5
