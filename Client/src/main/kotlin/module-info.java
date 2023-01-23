module study.project.battleships {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.desktop;

    opens study.project.battleships to javafx.fxml;
    opens study.project.battleships.controllers to javafx.fxml;
    exports study.project.battleships;
    exports study.project.battleships.controllers;
}