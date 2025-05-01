package minesweeper;

import javafx.scene.layout.GridPane;

import java.util.Random;

public class Minefield extends GridPane {
    private final int rows = 20;
    private final int cols = 20;
    private final int numberOfMines = 80;
    private Field[][] buttons;

    Minefield(int width, int height){
        super();
        buttons = new Field[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new Field(width/cols, height/rows, this.buttons, i, j);
                this.add(buttons[i][j], j, i);
            }
        }

        setupMinefield();

    }

    void setupMinefield (){

        Random rand = new Random();
        for (int i = 0; i < numberOfMines; i++){
            int randRow = rand.nextInt(rows);
            int randCol = rand.nextInt(cols);

            if(buttons[randRow][randCol].getContainsMine()){
                i--;
            }
            else{
                buttons[randRow][randCol].setContainsMine(true);
                incrementSurrounding(randRow, randCol);
            }
        }
    }

    private void incrementSurrounding(int x, int y){
        for(int i = -1; i < 2; i++){

            if((x+i) < 0 || (x+i) >= cols){
                continue;
            }

            for(int j = -1; j < 2; j++){

                if((y+j) < 0 || (y+j) >= rows || (i == 0 && j == 0)){
                    continue;
                }

                buttons[x+i][y+j].surroundingBombs++;
            }
        }
    }

}
