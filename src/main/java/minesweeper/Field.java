package minesweeper;

import javafx.scene.control.Button;

public class Field extends Button{
    private boolean containsMine = false;
    public int surroundingBombs = 0;
    private boolean revealed = false;
    private boolean marked = false;
    public final int xPos;
    public final int yPos;

    private static final String STYLE_MARKED = "marked-field";
    private static final String STYLE_REVEALED = "revealed-field";
    private static final String STYLE_LIGHT = "base-field-light";
    private static final String STYLE_DARK = "base-field-dark";


    Field(
            final int CELL_SIZE,
            int xPos,
            int yPos)
    {
        super();
        this.xPos = xPos;
        this.yPos = yPos;
        this.setPrefWidth(CELL_SIZE);
        this.setPrefHeight(CELL_SIZE);
    }


    public void reset() {
        this.containsMine = false;
        this.surroundingBombs = 0;
        this.revealed = false;
        this.marked = false;
        this.setText("");

        // Przywróć oryginalny styl
        styleUnmarked();
    }

    public void styleUnmarked() {
        // Przywróć oryginalny styl
        if((this.xPos+this.yPos) % 2 == 1){
            this.getStyleClass().setAll("button", STYLE_LIGHT);
        }
        else{
            this.getStyleClass().setAll("button", STYLE_DARK);
        }
    }

    public void styleMarked() {
        this.getStyleClass().setAll("button", STYLE_MARKED);
    }

    public void styleRevealed(){
        this.getStyleClass().setAll("button", STYLE_REVEALED);
    }

    public void styleMine(){
        this.setText("💣");
        this.getStyleClass().setAll("button", "mine-field");
    }

    public void setMarked(boolean marked){
        this.marked = marked;
    }

    public void setRevealed(boolean revealed){
        this.revealed = revealed;
    }

    public boolean isMarked(){
        return this.marked;
    }

    public boolean isRevealed(){
        return this.revealed;
    }



    public boolean getContainsMine() {
        return containsMine;
    }

    public void setContainsMine(boolean containsMine) {
        this.containsMine = containsMine;
    }
}