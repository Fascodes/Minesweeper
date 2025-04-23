package minesweeper;

import javafx.scene.control.Button;

public class Field extends Button{
    private boolean containsMine = false;
    private int surroundingBombs = 0;

    Field(){
        super();
        // TUTAJ TRZEBA DODAC SKALOWANIE PROCENTOWE ROZMIARU ( JAKO ARGUMENT KONSTRUKTORA )
        this.setPrefWidth(30);
        this.setPrefHeight(30);
        this.setOnAction(event -> handleButtonClick());
    }

    void handleButtonClick(){
        if(this.getContainsMine()){
            this.setText("💥");
        }
        else{
            this.setText("🔳");
        }
    }

    public boolean getContainsMine() {
        return containsMine;
    }

    public void setContainsMine(boolean containsMine) {
        this.containsMine = containsMine;
    }
}
