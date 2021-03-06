package dtu.projectmanagement.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author William Steffens (s185369)
 */
public class ManagementAppGUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("views/logIn-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Project Management Application");
        //stage.setIcon();
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}