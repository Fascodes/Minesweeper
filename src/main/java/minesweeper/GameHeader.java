package minesweeper;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class GameHeader extends HBox {
    private Label flagCounter;
    private Button resetButton;
    private Label timeCounter;
    private Timeline gameTimer;
    private int seconds = 0;
    private int totalBombs;
    private int flagsUsed = 0;

    public GameHeader(int bombCount, Runnable onReset) {
        super();
        this.totalBombs = bombCount;

        setupHeader(onReset);
        startTimer();
    }

    private void setupHeader(Runnable onReset) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPrefHeight(50);
        this.getStyleClass().add("game-header");

        // Licznik flag
        flagCounter = new Label(String.format("%03d", totalBombs));
        flagCounter.getStyleClass().add("counter-label");

        // Spacer
        Region leftSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);

        // Przycisk reset
        resetButton = new Button("🙂");
        resetButton.getStyleClass().add("reset-button");
        resetButton.setOnAction(e -> onReset.run());

        // Spacer
        Region rightSpacer = new Region();
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        // Licznik czasu
        timeCounter = new Label("000");
        timeCounter.getStyleClass().add("counter-label");

        this.getChildren().addAll(flagCounter, leftSpacer, resetButton, rightSpacer, timeCounter);
    }

    private void startTimer() {
        gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            timeCounter.setText(String.format("%03d", Math.min(seconds, 999)));
        }));
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();
    }

    public void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    public void resetTimer() {
        stopTimer();
        seconds = 0;
        timeCounter.setText("000");
        startTimer();
    }

    public void updateFlagCount(int change) {
        flagsUsed += change;
        int remaining = Math.max(0, totalBombs - flagsUsed);
        flagCounter.setText(String.format("%03d", remaining));
    }

    public void setGameOver(boolean won) {
        stopTimer();
        resetButton.setText(won ? "😎" : "😵");
    }

    public void resetGame() {
        flagsUsed = 0;
        flagCounter.setText(String.format("%03d", totalBombs));
        resetButton.setText("🙂");
        resetTimer();
    }
}