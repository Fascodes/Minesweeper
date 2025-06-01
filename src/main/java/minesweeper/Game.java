package minesweeper;

import javafx.scene.layout.VBox;

public class Game extends VBox{
    private final Minefield gameMinefield;
    private final GameHeader gameHeader;
    private final int totalFields, bombCount;
    private boolean gameOver = false;

    Game(
            final int ROWS,
            final int COLS,
            final int BOMB_COUNT,
            final int CELL_SIZE
    ){
        super();
        this.gameMinefield = new Minefield(
                ROWS,
                COLS,
                BOMB_COUNT,
                CELL_SIZE,
                this::onGameOver,
                this::checkWinCondition,
                this::onFlagToggled
        );
        this.gameHeader = new GameHeader(BOMB_COUNT, this::resetGame);
        this.totalFields = ROWS * COLS;
        this.bombCount = BOMB_COUNT;

        this.getChildren().addAll(gameHeader, gameMinefield);
    }

    public void checkWinCondition() {
        if (gameMinefield.getRevealedCount() == this.totalFields - this.bombCount) {
            this.onGameOver(true);
        }
    }


    public void onFlagToggled(boolean flagAdded) {
        if (!gameOver) {
            gameHeader.updateFlagCount(flagAdded ? 1 : -1);
        }
    }

    public void onGameOver(boolean won) {
        gameMinefield.disableAllFields();

        gameOver = true;
        gameHeader.setGameOver(won);
    }

    public void resetGame() {
        gameOver = false;
        gameHeader.resetGame();

        gameMinefield.setRevealedCount(0);
        // Ponownie ustaw miny
        gameMinefield.resetFields();

    }
}
