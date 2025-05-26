package minesweeper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartMenuController {
    @FXML
    private ComboBox<String> boardSizeComboBox;

    @FXML
    private ComboBox<String> difficultyComboBox;

    @FXML
    private ComboBox<String> cellSizeComboBox;

    @FXML
    private Button startGameButton;

    @FXML
    private void initialize() {
        // Dodaj opcje do ComboBox
        boardSizeComboBox.getItems().addAll("Mała", "Średnia", "Duża");
        difficultyComboBox.getItems().addAll("Niski", "Średni", "Wysoki");
        cellSizeComboBox.getItems().addAll("Małe", "Średnie", "Duże");

        // Ustaw domyślne wartości
        boardSizeComboBox.setValue("Średnia");
        difficultyComboBox.setValue("Średni");
        cellSizeComboBox.setValue("Średnie");
    }

    @FXML
    private void onStartGameClick() {
        String selectedSize = boardSizeComboBox.getValue();
        String selectedDifficulty = difficultyComboBox.getValue();
        String selectedCellSize = cellSizeComboBox.getValue();

        // Konwertuj na parametry gry
        int[] boardParams = getBoardParameters(selectedSize);
        int bombCount = getBombCount(selectedDifficulty, boardParams);
        int cellSize = getCellSize(selectedCellSize);

        // Stwórz nową grę
        Minefield minefield = new Minefield(boardParams[0], boardParams[1], bombCount, cellSize);
        Scene gameScene = new Scene(minefield, boardParams[1] * cellSize, boardParams[0] * cellSize);

        // Zastosuj style
        gameScene.getStylesheets().add(getClass().getResource("/minesweeper/styles.css").toExternalForm());

        // Pobierz obecne okno i zmień scenę
        Stage stage = (Stage) startGameButton.getScene().getWindow();
        stage.setTitle("Minesweeper");
        stage.setScene(gameScene);
        stage.centerOnScreen();
    }

    private int[] getBoardParameters(String size) {
        switch (size) {
            case "Mała": return new int[]{10, 10};
            case "Średnia": return new int[]{16, 16};
            case "Duża": return new int[]{20, 30};
            default: return new int[]{16, 16};
        }
    }

    private int getBombCount(String difficulty, int[] boardParams) {
        int totalCells = boardParams[0] * boardParams[1];

        switch (difficulty) {
            case "Niski": return (int)(totalCells * 0.10);
            case "Średni": return (int)(totalCells * 0.15);
            case "Wysoki": return (int)(totalCells * 0.20);
            default: return (int)(totalCells * 0.15);
        }
    }


    private int getCellSize(String size) {
        switch (size) {
            case "Małe": return 20;
            case "Średnie": return 30;
            case "Duże": return 40;
            default: return 30;
        }
    }
}