package dtu.projectmanagement.gui.controllers;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.gui.ManagementAppGUI;
import dtu.projectmanagement.gui.controllers.listviewcell.EmployeeListViewCell;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;


public class LogInController {

    ManagementApp managementApp;

    Stage stagePopUp;

    @FXML private ListView<Employee> lvEmp;
    @FXML private Button btnContinue;

    @FXML
    public void initialize() {
        managementApp = ManagementAppGUI.managementApp;

        lvEmp.setItems(managementApp.getEmployeeRepo());
        lvEmp.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (lvEmp.getSelectionModel().getSelectedItem() == null) {
                btnContinue.setDisable(true);
                return;
            }
            btnContinue.setDisable(false);
        });

        lvEmp.setCellFactory(employeeListView -> new EmployeeListViewCell());
    }



    @FXML
    public void onBtnContinue() throws IOException {
        Employee selectedUser = lvEmp.getSelectionModel().getSelectedItem();
        managementApp.login(selectedUser.getUsername());

        if (stagePopUp != null)
            stagePopUp.close();
        switchToHomeScene();
    }

    private void switchToHomeScene() throws IOException {
        Stage homeStage = (Stage) btnContinue.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("views/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        homeStage.setTitle("Project Management Application");
        //stage.setIcon();
        homeStage.setResizable(true);
        homeStage.setScene(scene);
        homeStage.setOnCloseRequest(e -> Platform.exit());
        homeStage.centerOnScreen();
        homeStage.setMinWidth(1200);
        homeStage.setMinHeight(800);
        homeStage.show();
    }

    @FXML
    public void onBtnAddNewEmp() throws IOException {
        // Close the pop-up if it's already open
        if (stagePopUp != null)
            stagePopUp.close();

        // Create and display popup
        // TODO: maybe do the same function call thing for the creation of the popup
        FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("views/addNewEmp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stagePopUp = new Stage();
        stagePopUp.setResizable(false);
        stagePopUp.setAlwaysOnTop(true);
        stagePopUp.setTitle("Add New Employee");
        //stage.setIcon();
        stagePopUp.setScene(scene);
        stagePopUp.show();
    }
}