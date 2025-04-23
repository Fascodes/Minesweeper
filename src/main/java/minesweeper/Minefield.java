package minesweeper;

import javafx.scene.layout.GridPane;

import java.util.Random;

public class Minefield extends GridPane {
    private final int rows = 10;
    private final int cols = 10;
    private final int numberOfMines = 10;
    private Field[][] buttons;

    Minefield(){
        super();
        buttons = new Field[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new Field();
                this.add(buttons[i][j], j, i);
            }
        }

        setupMinefield();
    }

    void setupMinefield (){
        // TODO: Increment value of surroundingBombs in Fields that surround the bomb
        Random rand = new Random();
        for (int i = 0; i < numberOfMines; i++){
            int randRow = rand.nextInt(rows);
            int randCol = rand.nextInt(cols);

            if(buttons[randRow][randCol].getContainsMine()){
                i--;
            }
            else{
                buttons[randRow][randCol].setContainsMine(true);
            }
        }
    }

}
