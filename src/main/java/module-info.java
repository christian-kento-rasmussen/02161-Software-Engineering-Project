module dtu.projectmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens dtu.projectmanagement.gui to javafx.fxml;
    exports dtu.projectmanagement.gui to javafx.fxml, javafx.graphics;

    opens dtu.projectmanagement.gui.controllers to javafx.fxml;
    exports dtu.projectmanagement.gui.controllers to javafx.fxml;
    exports dtu.projectmanagement.app;
    exports dtu.projectmanagement.domain;
    opens dtu.projectmanagement.gui.controllers.listviewcell to javafx.fxml;
    exports dtu.projectmanagement.gui.controllers.listviewcell to javafx.fxml;
}