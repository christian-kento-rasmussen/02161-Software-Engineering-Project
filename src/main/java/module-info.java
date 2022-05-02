module dtu.projectmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens dtu.projectmanagement.gui to javafx.fxml;
    exports dtu.projectmanagement.gui to javafx.fxml, javafx.graphics;

    opens dtu.projectmanagement.gui.controllers to javafx.fxml;
    exports dtu.projectmanagement.gui.controllers to javafx.fxml;
}