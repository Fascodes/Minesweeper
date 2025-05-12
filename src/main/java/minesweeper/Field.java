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
    public final int xPos;
    public final int yPos;

    private static final String STYLE_LIGHT = "base-field-light";
    private static final String STYLE_DARK = "base-field-dark";
    private static final String STYLE_MARKED = "marked-field";
    private static final String STYLE_REVEALED = "revealed-field";



    Field(int width, int height, Field[][] minefield, int xPos, int yPos){
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.minefield = minefield;
        this.xPos = xPos;
        this.yPos = yPos;
        this.setOnMouseClicked(this::handleButtonClick);
    }

    // TODO: Dodac oznaczanie podejrzanych pol, pola odkryte moga zmieniac kolor + wyswietlac wartosc jezeli surroundingbombs > 0
    void handleButtonClick(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
            if(this.getContainsMine()){
                this.setText("💥");
            }
            else if(!this.marked){
                // Mozna zaimplementowac algorytm DFS(Depth First Search) do odkrywania pol niezawierajacych bomb, dodano zmienna  boolean revealed
                // prawdopodobnie niezbedne bedzie dodanie referencji do minefield w konstruktorze oraz koordynatow pola jako zmienna klasy
                revealFields(this.minefield, this.xPos, this.yPos);
            }
        } else if(event.getButton() == MouseButton.SECONDARY && !this.revealed){
            if(this.marked){
                if((xPos+yPos) % 2 == 1){
                    this.getStyleClass().setAll("button", STYLE_LIGHT);
                }
                else{
                    this.getStyleClass().setAll("button", STYLE_DARK);
                }
                this.marked = false;
            }
            else{
                this.getStyleClass().setAll("button", STYLE_MARKED);
                this.marked = true;
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
