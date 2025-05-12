package minesweeper;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MineSweeperController {
    @FXML
    private Label gameOver;

    @FXML
    protected void onQuitButtonClick(){
        gameOver.setText("Do nastepnego razu!");
        Platform.exit();
    }


    @FXML
    protected void onTryAgainButtonClick(){gameOver.setText("Powodzenia!");}

}

