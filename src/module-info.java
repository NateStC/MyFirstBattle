module MyFirstBattle {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.Stclair;

    opens com.Stclair.battlePane;
}