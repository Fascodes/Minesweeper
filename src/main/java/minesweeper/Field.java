package minesweeper;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Field extends Button{
    private boolean containsMine = false;
    public int surroundingBombs = 0;
    public boolean revealed = false;
    private final Field[][] minefield;
    public final int xPos;
    public final int yPos;



    Field(int width, int height, Field[][] minefield, int xPos, int yPos){
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setOnAction(event -> handleButtonClick());
        this.minefield = minefield;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    // TODO: Dodac oznaczanie podejrzanych pol, pola odkryte moga zmieniac kolor + wyswietlac wartosc jezeli surroundingbombs > 0
    void handleButtonClick(){
        if(this.getContainsMine()){
            this.setText("💥");
        }
        else{
            // Mozna zaimplementowac algorytm DFS(Depth First Search) do odkrywania pol niezawierajacych bomb, dodano zmienna  boolean revealed
            // prawdopodobnie niezbedne bedzie dodanie referencji do minefield w konstruktorze oraz koordynatow pola jako zmienna klasy
            revealFields(this.minefield, this.xPos, this.yPos);
        }
    }



    // Implementacja DFS - zatrzymuje sie gdy napotka pole z surroundingbombs > 0
    public void revealFields(Field[][] minefield, int xPos, int yPos){
        if(xPos < 0 || xPos >= minefield.length || yPos < 0 || yPos >= minefield[0].length){
            return;
        }

        if(minefield[xPos][yPos].revealed || minefield[xPos][yPos].getContainsMine()){
            return;
        }
        else{
            minefield[xPos][yPos].revealed = true;
            minefield[xPos][yPos].setText(String.valueOf(minefield[xPos][yPos].surroundingBombs));

        }
        if(minefield[xPos][yPos].surroundingBombs > 0){
            return;
        }

        revealFields(minefield, xPos+1, yPos);
        revealFields(minefield, xPos-1, yPos);
        revealFields(minefield, xPos, yPos+1);
        revealFields(minefield, xPos, yPos-1);
    }



    public boolean getContainsMine() {
        return containsMine;
    }

    public void setContainsMine(boolean containsMine) {
        this.containsMine = containsMine;
    }
}
