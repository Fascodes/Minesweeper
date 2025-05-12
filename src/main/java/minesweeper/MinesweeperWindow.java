package minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MinesweeperWindow extends Application {

    public void gameOverScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperWindow.class.getResource("sweeper-view.fxml"));
        Scene popupWindow = new Scene(fxmlLoader.load(), 320, 240);
        Stage popup = new Stage();
        popup.setScene(popupWindow);
        popup.setResizable(false);
        popup.centerOnScreen();
        popup.show();
    }

    public void restart(Stage stage) {
        Minefield minefield = new Minefield(1280, 720);
        Scene scene = new Scene(minefield, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sweeper-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // TODO: Dodac skalowanie pola wedlug rozmiaru okna, mozna wysrodkowac pole
        Minefield minefield = new Minefield(1280, 720);
        Scene scene = new Scene(minefield, 1280, 720);


        scene.getStylesheets().add(getClass().getResource("/minesweeper/styles.css").toExternalForm());
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}