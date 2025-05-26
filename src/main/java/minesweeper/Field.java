package minesweeper;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Field extends Button{
    private boolean containsMine = false;
    public int surroundingBombs = 0;
    public boolean revealed = false;
    private boolean marked = false;
    private final Field[][] minefield;
    private final Minefield parentMinefield;
    public final int xPos;
    public final int yPos;

    private static final String STYLE_LIGHT = "base-field-light";
    private static final String STYLE_DARK = "base-field-dark";
    private static final String STYLE_MARKED = "marked-field";
    private static final String STYLE_REVEALED = "revealed-field";

    Field(
            final int CELL_SIZE,
            Field[][] minefield,
            int xPos,
            int yPos,
            Minefield parentMinefield)
    {
        super();
        this.setPrefWidth(CELL_SIZE);
        this.setPrefHeight(CELL_SIZE);
        this.minefield = minefield;
        this.parentMinefield = parentMinefield;
        this.xPos = xPos;
        this.yPos = yPos;
        this.setOnMouseClicked(this::handleButtonClick);
    }

    void handleButtonClick(MouseEvent event){
        // Left-click the tile and check it
        if(event.getButton() == MouseButton.PRIMARY){
            if(this.marked) return; // If field is marked does nothing
            if(this.getContainsMine()){
                this.setText("💥");
                this.getStyleClass().setAll("button", "mine-field");
                parentMinefield.onGameOver(false);
                revealAllMines();
            }
            else if(!this.marked){
                revealFields(this.minefield, this.xPos, this.yPos);
                parentMinefield.checkWinCondition();
            }
        }
        // Right-click the tile and mark it
        else if(event.getButton() == MouseButton.SECONDARY && !this.revealed){
            if(this.marked){
                if((xPos+yPos) % 2 == 1){
                    this.getStyleClass().setAll("button", STYLE_LIGHT);
                }
                else{
                    this.getStyleClass().setAll("button", STYLE_DARK);
                }
                this.marked = false;
                parentMinefield.onFlagToggled(false);
            }
            else{
                this.getStyleClass().setAll("button", STYLE_MARKED);
                this.marked = true;
                parentMinefield.onFlagToggled(true);
            }
        }
    }

    // Implementacja DFS - zatrzymuje sie gdy napotka pole z surroundingbombs > 0
    public void revealFields(Field[][] minefield, int xPos, int yPos){
        if(xPos < 0 || xPos >= minefield.length || yPos < 0 || yPos >= minefield[0].length){
            return;
        }

        if(minefield[xPos][yPos].revealed || minefield[xPos][yPos].marked || minefield[xPos][yPos].getContainsMine()){
            return;
        }
        else{
            minefield[xPos][yPos].revealed = true;
            minefield[xPos][yPos].getStyleClass().setAll("button", STYLE_REVEALED);
            parentMinefield.incrementRevealedCount();
            if(minefield[xPos][yPos].surroundingBombs > 0) {
                minefield[xPos][yPos].setText(String.valueOf(minefield[xPos][yPos].surroundingBombs));
            }
        }
        if(minefield[xPos][yPos].surroundingBombs > 0){
            return;
        }

        // Recursively checks all surrounding fields
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if (i == 0 && j == 0) continue; // Skips itself
                revealFields(minefield, xPos + i, yPos + j);
            }
        }
    }

    private void revealAllMines() {
        for (int i = 0; i < minefield.length; i++) {
            for (int j = 0; j < minefield[0].length; j++) {
                if (minefield[i][j].getContainsMine() && !minefield[i][j].revealed) {
                    minefield[i][j].setText("💣");
                    minefield[i][j].getStyleClass().setAll("button", "mine-field");
                }
            }
        }
    }



    public void reset() {
        this.containsMine = false;
        this.surroundingBombs = 0;
        this.revealed = false;
        this.marked = false;
        this.setText("");

        // Przywróć oryginalny styl
        if((xPos+yPos) % 2 == 1){
            this.getStyleClass().setAll("button", STYLE_LIGHT);
        }
        else{
            this.getStyleClass().setAll("button", STYLE_DARK);
        }
    }

    public boolean getContainsMine() {
        return containsMine;
    }

    public void setContainsMine(boolean containsMine) {
        this.containsMine = containsMine;
    }
}