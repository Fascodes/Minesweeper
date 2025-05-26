package minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;

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

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperWindow.class.getResource("start-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 350);

            // Ikona
            try (InputStream iconStream = getClass().getResourceAsStream("/images/icon.png")) {
                if (iconStream != null) {
                    stage.getIcons().add(new Image(iconStream));
                }
            } catch (Exception e) {
                System.err.println("Failed to load icon: " + e.getMessage());
            }

            stage.setTitle("Minesweeper - Menu");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}