package minesweeper;

import javafx.scene.layout.GridPane;

import java.util.Random;

public class Minefield extends GridPane {
    private final int numberOfMines = 30;
    private Field[][] buttons;

    public Minefield(
            final int rows,
            final int cols,
            final int CELL_SIZE
    ){
        super();
        this.setPrefWidth(cols * CELL_SIZE);
        this.setPrefHeight(rows * CELL_SIZE);

        buttons = new Field[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Initialize respective button
                buttons[i][j] = new Field(
                        CELL_SIZE,
                        this.buttons,
                        i, j);

                if((i+j) % 2 == 1){
                    buttons[i][j].getStyleClass().add("base-field-light");
                }
                else{
                    buttons[i][j].getStyleClass().add("base-field-dark");
                }
                this.add(buttons[i][j], j, i);
            }
        }

        setupMinefield(rows, cols);
    }

    void setupMinefield (
            final int rows,
            final int cols
    ){

        Random rand = new Random();
        for (int i = 0; i < numberOfMines; i++){
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

}
