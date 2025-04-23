module com.minesweeper.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens minesweeper to javafx.fxml;
    exports minesweeper;
}