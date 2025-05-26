package minesweeper;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Random;

public class Minefield extends VBox {
    private Field[][] buttons;
    private GameHeader gameHeader;
    private GridPane gridPane;
    private int rows, cols, bombCount;
    private boolean gameOver = false;

    public Minefield(
            final int ROWS,
            final int COLS,
            final int BOMB_COUNT,
            final int CELL_SIZE
    ){
        super();
        this.rows = ROWS;
        this.cols = COLS;
        this.bombCount = BOMB_COUNT;

        this.setPrefWidth(COLS * CELL_SIZE);
        this.setPrefHeight(ROWS * CELL_SIZE + 60); // +60 dla headera

        // Stwórz header
        gameHeader = new GameHeader(BOMB_COUNT, this::resetGame);

        // Stwórz GridPane dla pól
        gridPane = new GridPane();

        buttons = new Field[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // Initialize respective button
                buttons[i][j] = new Field(
                        CELL_SIZE,
                        this.buttons,
                        i, j,
                        this); // Przekaż referencję do Minefield

                if((i+j) % 2 == 1){
                    buttons[i][j].getStyleClass().add("base-field-light");
                }
                else{
                    buttons[i][j].getStyleClass().add("base-field-dark");
                }
                gridPane.add(buttons[i][j], j, i);
            }
        }

        setupMinefield(ROWS, COLS, BOMB_COUNT);

        // Dodaj header i gridPane do VBox
        this.getChildren().addAll(gameHeader, gridPane);
    }

    void setupMinefield (
            final int rows,
            final int cols,
            final int BOMB_COUNT
    ){

        Random rand = new Random();
        for (int i = 0; i < BOMB_COUNT; i++){
            int randRow = rand.nextInt(rows);
            int randCol = rand.nextInt(cols);

            if(buttons[randRow][randCol].getContainsMine()){
                i--;
            }
            else{
                buttons[randRow][randCol].setContainsMine(true);
                incrementSurrounding(randRow, randCol, rows, cols);
            }
        }
    }

    private void incrementSurrounding(
            final int x,
            final int y,
            final int rows,
            final int cols){
        for(int i = -1; i < 2; i++){

            if((x+i) < 0 || (x+i) >= rows){
                continue;
            }

            for(int j = -1; j < 2; j++){

                if((y+j) < 0 || (y+j) >= cols || (i == 0 && j == 0)){
                    continue;
                }

                buttons[x+i][y+j].surroundingBombs++;
            }
        }
    }

    public void onFlagToggled(boolean flagAdded) {
        if (!gameOver) {
            gameHeader.updateFlagCount(flagAdded ? 1 : -1);
        }
    }

    public void onGameOver(boolean won) {
        gameOver = true;
        gameHeader.setGameOver(won);
    }

    private void resetGame() {
        gameOver = false;
        gameHeader.resetGame();

        // Reset wszystkich pól
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].reset();
            }
        }

        // Ponownie ustaw miny
        setupMinefield(rows, cols, bombCount);
    }
}