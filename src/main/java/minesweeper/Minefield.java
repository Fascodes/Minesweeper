package minesweeper;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class Minefield extends GridPane {
    private final Field[][] buttons;
    private int  revealedCount;
    private final int rows, cols, bombCount;
    private final GameOverCallback gameOverCallback;
    private final WinCheckCallback winCheckCallback;
    private final FlagToggleCallback flagToggleCallback;


    public Minefield(
            final int ROWS,
            final int COLS,
            final int BOMB_COUNT,
            final int CELL_SIZE,
            GameOverCallback gameOverCallback,
            WinCheckCallback winCheckCallback,
            FlagToggleCallback flagToggleCallback
    ) {
        super();
        this.rows = ROWS;
        this.cols = COLS;
        this.bombCount = BOMB_COUNT;
        this.revealedCount = 0;

        this.gameOverCallback = gameOverCallback;
        this.winCheckCallback = winCheckCallback;
        this.flagToggleCallback = flagToggleCallback;

        this.setPrefWidth(COLS * CELL_SIZE);
        this.setPrefHeight(ROWS * CELL_SIZE + 60);

        // Stwórz GridPane dla pól
        buttons = new Field[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Field field = new Field(CELL_SIZE, i, j);
                field.setOnMouseClicked(event -> handleClick(event, field));
                buttons[i][j] = field;

                if ((i + j) % 2 == 1) {
                    field.getStyleClass().add("base-field-light");
                } else {
                    field.getStyleClass().add("base-field-dark");
                }

                this.add(field, j, i);
            }
        }

        setupMinefield(ROWS, COLS, BOMB_COUNT);
    }

    private void handleClick(MouseEvent event, Field field) {
        //if (gameOver) return;

        if (event.getButton() == MouseButton.PRIMARY) {
            if (field.isMarked()) return;

            if (field.getContainsMine()) {
                gameOverCallback.onGameOver(false);
                revealAllMines();
            } else {
                revealFields(field.xPos, field.yPos);
                winCheckCallback.checkWin();
            }

        } else if (event.getButton() == MouseButton.SECONDARY && !field.isRevealed()) {
            if (field.isMarked()) {
                // Odznacz
                field.styleUnmarked();
                field.setMarked(false);
                flagToggleCallback.onFlagToggled(false);
            } else {
                // Zaznacz
                field.styleMarked();
                field.setMarked(true);
                flagToggleCallback.onFlagToggled(true);
            }
        }
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


    // Implementacja DFS - zatrzymuje sie gdy napotka pole z surroundingbombs > 0
    public void revealFields(int xPos, int yPos){
        if(xPos < 0 || xPos >= this.buttons.length || yPos < 0 || yPos >= this.buttons[0].length){
            return;
        }

        if(this.buttons[xPos][yPos].isRevealed() || this.buttons[xPos][yPos].isMarked() || this.buttons[xPos][yPos].getContainsMine()){
            return;
        }
        else{
            this.buttons[xPos][yPos].setRevealed(true);
            this.buttons[xPos][yPos].styleRevealed();
            this.revealedCount++;
            if(this.buttons[xPos][yPos].surroundingBombs > 0) {
                this.buttons[xPos][yPos].setText(String.valueOf(this.buttons[xPos][yPos].surroundingBombs));
            }
        }
        if(this.buttons[xPos][yPos].surroundingBombs > 0){
            return;
        }
        // Recursively checks all surrounding fields
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if (i == 0 && j == 0) continue; // Skips itself
                revealFields(xPos + i, yPos + j);
            }
        }
    }


    public void disableAllFields() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].setDisable(true);
            }
        }
    }

    private void revealAllMines() {
        for (int i = 0; i < this.buttons.length; i++) {
            for (int j = 0; j < this.buttons[0].length; j++) {
                if (this.buttons[i][j].getContainsMine() && !this.buttons[i][j].isRevealed()) {
                    this.buttons[i][j].styleMine();
                }
            }
        }
    }

    public void resetFields() {
        // Reset wszystkich pól
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].reset();
                buttons[i][j].setDisable(false);
            }
        }
        setupMinefield(rows, cols, bombCount);
    }

    public int getRevealedCount() {
        return revealedCount;
    }

    public void setRevealedCount(int revealedCount) {
        this.revealedCount = revealedCount;
    }

    @FunctionalInterface
    public interface GameOverCallback {
        void onGameOver(boolean won);
    }

    @FunctionalInterface
    public interface WinCheckCallback {
        void checkWin();
    }

    @FunctionalInterface
    public interface FlagToggleCallback {
        void onFlagToggled(boolean flagAdded);
    }
}