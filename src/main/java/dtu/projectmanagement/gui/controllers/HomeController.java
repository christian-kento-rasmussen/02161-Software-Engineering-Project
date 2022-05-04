package dtu.projectmanagement.gui.controllers;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.NotificationType;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;
import dtu.projectmanagement.gui.ManagementAppGUI;
import dtu.projectmanagement.gui.controllers.listviewcell.ActivityListViewCell;
import dtu.projectmanagement.gui.controllers.listviewcell.EmployeeListViewCell;
import dtu.projectmanagement.gui.controllers.listviewcell.ProjectListViewCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class HomeController implements PropertyChangeListener {

    ManagementApp managementApp;

    Stage stagePopUp;
    private int back;

    @FXML private TabPane tabPane;

    // Navbar
    @FXML private Button btnLogOut;
    @FXML private Label lblCurrentUser;

    // Projects Pane
    @FXML private ListView<Project> lvProjects;
    @FXML private Button btnViewProject;
    @FXML private Button btnDeleteProject;

    // Activities Pane
    @FXML private ListView<Activity> lvActivities;
    @FXML private Label lblActivityNameError;
    @FXML private TextField tfActivityName;

    @FXML private Button btnViewActivity;
    @FXML private Button btnUnassignActivity;

    // Employees Pane
    @FXML private ListView<Employee> lvEmp;
    @FXML private Button btnRemoveEmployee;

    // ProjectView Pane
    @FXML private Label lblProjectNum;
    @FXML private Label lblProjectName;
    @FXML private Label lblProjectNameError;
    @FXML private TextField tfChangeProjectName;
    @FXML private Label lblProjectLeaderUsername;
    @FXML private ComboBox<Employee> cbPickProjectLeader;
    @FXML private Button btnChangeProjectLeader;
    @FXML private Label lblProjectStartWeek;
    @FXML private Label lblProjectStartWeekError;
    @FXML private TextField tfProjectStartWeek;
    @FXML private Label lblProjectEndWeek;
    @FXML private Label lblProjectEndWeekError;
    @FXML private TextField tfProjectEndWeek;

    @FXML private Label lblProjectStatsError;
    @FXML private Label lblProjectSpendHours;
    @FXML private Label lblProjectExpectedHours;
    @FXML private Label lblProjectRemainingHours;

    @FXML private Label lblProjectActivityNameError;
    @FXML private TextField tfProjectActivityName;
    @FXML private ListView<Activity> lvProjectActivities;
    @FXML private Button btnViewProjectActivity;
    @FXML private Button btnDeleteProjectActivity;

    // ActivityView Pane
    @FXML private Label lblActivityProjectNum;
    @FXML private Label lblActivityNum;

    @FXML private Label lblActivityName;
    @FXML private Label lblChangeActivityNameError;
    @FXML private TextField tfChangeActivityName;
    @FXML private Label lblRegisteredHours;
    @FXML private Label lblRegisterHoursError;
    @FXML private TextField tfRegisterHours;
    @FXML private Label lblActivityExpectedHours;
    @FXML private Label lblActivityExpectedHoursError;
    @FXML private TextField tfSetExpectedHours;
    @FXML private Label lblActivityStartWeek;
    @FXML private Label lblActivityStartWeekError;
    @FXML private TextField tfActivityStartWeek;
    @FXML private Label lblActivityEndWeek;
    @FXML private Label lblActivityEndWeekError;
    @FXML private TextField tfActivityEndWeek;

    @FXML private Label lblProjectActivityAssignError;
    @FXML private ComboBox<Employee> cbAssignEmployee;
    @FXML private Button btnAssignEmployee;
    @FXML private ListView<Employee> lvAssignedEmployees;

    @FXML
    public void initialize() {
        managementApp = ManagementAppGUI.managementApp;
        managementApp.addObserver(this);

        // TODO: do the thing with textformat

        // Navbar
        lblCurrentUser.setText(managementApp.getUserUsername());

        // Project Pane
        lvProjects.setItems(FXCollections.observableArrayList(managementApp.getProjectRepo()));
        lvProjects.setCellFactory(projectListView -> new ProjectListViewCell());
        lvProjects.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (lvProjects.getSelectionModel().getSelectedItem() == null) {
                btnViewProject.setDisable(true);
                btnDeleteProject.setDisable(true);
                return;
            }
            btnViewProject.setDisable(false);
            btnDeleteProject.setDisable(false);
        });

        // Activities Pane
        lvActivities.setItems(FXCollections.observableArrayList(managementApp.getUserActivities()));
        lvActivities.setCellFactory(activityListView -> new ActivityListViewCell());
        lvActivities.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (lvActivities.getSelectionModel().getSelectedItem() == null) {
                btnViewActivity.setDisable(true);
                btnUnassignActivity.setDisable(true);
                return;
            }
            btnViewActivity.setDisable(false);
            btnUnassignActivity.setDisable(false);
        });

        // Employee Pane
        lvEmp.setItems(FXCollections.observableArrayList(managementApp.getEmployeeRepo()));
        lvEmp.setCellFactory(employeeListView -> new EmployeeListViewCell());
        lvEmp.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (lvEmp.getSelectionModel().getSelectedItem() == null) {
                btnRemoveEmployee.setDisable(true);
                return;
            }
            btnRemoveEmployee.setDisable(false);
        });

        // ProjectView Pane
        cbPickProjectLeader.setItems(FXCollections.observableArrayList(managementApp.getEmployeeRepo()));
        cbPickProjectLeader.setCellFactory(employeeListView -> new EmployeeListViewCell());
        cbPickProjectLeader.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (cbPickProjectLeader.getSelectionModel().getSelectedItem() == null) {
                btnChangeProjectLeader.setDisable(true);
                return;
            }
            btnChangeProjectLeader.setDisable(false);
        });

        lvProjectActivities.setCellFactory(activityListView -> new ActivityListViewCell());
        lvProjectActivities.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (lvProjectActivities.getSelectionModel().getSelectedItem() == null) {
                btnViewProjectActivity.setDisable(true);
                btnDeleteProjectActivity.setDisable(true);
                return;
            }
            btnViewProjectActivity.setDisable(false);
            btnDeleteProjectActivity.setDisable(false);
        });

        // ActivityView Pane

        cbAssignEmployee.setItems(FXCollections.observableArrayList(managementApp.getEmployeeRepo()));
        cbAssignEmployee.setCellFactory(employeeListView -> new EmployeeListViewCell());
        cbAssignEmployee.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (cbAssignEmployee.getSelectionModel().getSelectedItem() == null) {
                btnAssignEmployee.setDisable(true);
                return;
            }
            btnAssignEmployee.setDisable(false);
        });

        lvAssignedEmployees.setCellFactory(employeeListView -> new EmployeeListViewCell());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String type = evt.getPropertyName();
        System.out.println("changed");
        switch (type) {
            case NotificationType.UPDATE_PROJECT -> loadProject();
        }
    }



    // Utility Methods
    @FXML
    public void onBtnActivityBack() {
        if (back == 1)
            loadProject();

        tabPane.getSelectionModel().select(back);
    }

    private void loadProject() {
        lblProjectNum.setText(managementApp.getProjectNum());

        if (managementApp.getProjectLeader() == null)
            lblProjectLeaderUsername.setText("N/A");
        else
            lblProjectLeaderUsername.setText(managementApp.getProjectLeaderUsername());

        if (managementApp.getProjectStartWeek() == 0)
            lblProjectStartWeek.setText("N/A");
        else
            lblProjectStartWeek.setText(String.valueOf(managementApp.getProjectStartWeek()));

        if (managementApp.getProjectEndWeek() == 0)
            lblProjectStartWeek.setText("N/A");
        else
            lblProjectStartWeek.setText(String.valueOf(managementApp.getProjectEndWeek()));

        lblProjectName.setText(managementApp.getProjectName());
        cbPickProjectLeader.getSelectionModel().clearSelection();

        lblProjectSpendHours.setText("N/A");
        lblProjectExpectedHours.setText("N/A");
        lblProjectRemainingHours.setText("N/A");

        //lvProjectActivities.setItems(managementApp.getProjectActivityRepo());
    }

    private void loadActivity() throws OperationNotAllowedException {

        // TODO: DO TYPE BASED LOADING HERE AFTER REFACTOR

        lblActivityProjectNum.setText(managementApp.getProjectNum());

        lblActivityName.setText(managementApp.getActivityName());
        lblRegisteredHours.setText(String.valueOf(managementApp.getWorkedHoursOnActivity()));

        if (managementApp.getUser() != managementApp.getProjectLeader())
            lblActivityExpectedHours.setText("N/A");
        else
            lblActivityExpectedHours.setText(String.valueOf(managementApp.getExpectedWorkHoursOnActivity()));

        if (managementApp.getActivityStartWeek() == 0)
            lblProjectStartWeek.setText("N/A");
        else
            lblProjectStartWeek.setText(String.valueOf(managementApp.getActivityStartWeek()));

        if (managementApp.getActivityEndWeek() == 0)
            lblProjectStartWeek.setText("N/A");
        else
            lblProjectStartWeek.setText(String.valueOf(managementApp.getActivityEndWeek()));


        cbAssignEmployee.getSelectionModel().clearSelection();
        //lvAssignedEmployees.setItems(managementApp.getAssignedEmployees());
    }

    // Navbar
    @FXML
    public void onBtnProjects() {
        lvProjects.getSelectionModel().clearSelection();
        tabPane.getSelectionModel().select(0);
    }
    @FXML
    public void onBtnActivities() {
        lvActivities.getSelectionModel().clearSelection();
        tabPane.getSelectionModel().select(3);
    }
    @FXML
    public void onBtnEmployees() {
        lvEmp.getSelectionModel().clearSelection();
        tabPane.getSelectionModel().select(4);
    }
    @FXML
    public void onBtnLogOut() throws IOException {
        switchToLogInScene();
    }

    private void switchToLogInScene() throws IOException {
        Stage logInStage = (Stage) btnLogOut.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("views/logIn-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.setIcon();
        logInStage.setFullScreen(false);
        logInStage.setResizable(false);
        logInStage.setScene(scene);
        logInStage.setOnCloseRequest(e -> Platform.exit());
        logInStage.centerOnScreen();
        logInStage.show();
    }



    // Projects Pane
    @FXML
    public void onBtnAddNewProject() {
        managementApp.createNewProject();
    }

    @FXML
    public void onBtnViewProject() {
        Project selectedProject = lvProjects.getSelectionModel().getSelectedItem();
        managementApp.selectProject(selectedProject);
        loadProject();
        tabPane.getSelectionModel().select(1);
    }
    @FXML
    public void onBtnDeleteProject() {
        Project selectedProject = lvProjects.getSelectionModel().getSelectedItem();
        managementApp.deleteProject(selectedProject);
    }



    // ProjectView Pane
    @FXML
    public void onBtnProjectBack() {
        tabPane.getSelectionModel().select(0);
    }
    @FXML
    public void onBtnGenerateReport() {

    }
    @FXML
    public void onBtnChangeProjectName() {
        if (!tfChangeProjectName.getText().matches("^[a-zA-Z0-9]+$")) {
            lblProjectNameError.setText("Project names can only contain alphanumeric characters.");
            tfChangeProjectName.requestFocus();
            return;
        }

        if ((tfChangeProjectName.getLength() == 0) || tfChangeProjectName.getLength() > 20) {
            lblProjectNameError.setText("Project names need to be between 1 and 20 characters");
            tfChangeProjectName.requestFocus();
            return;
        }

        managementApp.setProjectName(tfChangeProjectName.getText());
        lblProjectName.setText(tfChangeProjectName.getText());

        lblProjectNameError.setText("");
        tfChangeProjectName.setText("");

        // TODO: implement changeListener
        //lvProjects.refresh();
    }
    @FXML
    public void onBtnChangeProjectLeader() {
        Employee selectedEmployee = cbPickProjectLeader.getSelectionModel().getSelectedItem();

        managementApp.assignProjectLeader(selectedEmployee);
        lblProjectLeaderUsername.setText(managementApp.getProjectLeaderUsername());
    }
    @FXML
    public void onBtnSetProjectStartWeek() {
        if (!tfProjectStartWeek.getText().matches("^\\d{4}-\\d{2}$")) {
            lblProjectStartWeekError.setText("Wrong format; the correct format is yyyy-ww");
            return;
        }

        int startWeek = Integer.parseInt(tfProjectStartWeek.getText().replaceAll("-",""));

        if (startWeek % 100 > 52) {
            lblProjectStartWeekError.setText("the week number must be valid.");
            return;
        }

        try {
            managementApp.setProjectStartWeek(startWeek);
        } catch (OperationNotAllowedException e) {
            lblProjectStartWeekError.setText(e.getMessage());
            return;
        }

        lblProjectStartWeek.setText(tfProjectStartWeek.getText());
        lblProjectStartWeekError.setText("");
    }
    @FXML
    public void onBtnSetProjectEndWeek() {
        if (!tfProjectEndWeek.getText().matches("^\\d{4}-\\d{2}$")) {
            lblProjectEndWeekError.setText("Wrong format; the correct format is yyyy-ww");
            return;
        }

        int endWeek = Integer.parseInt(tfProjectEndWeek.getText().replaceAll("-",""));

        if (endWeek % 100 > 52) {
            lblProjectEndWeekError.setText("the week number must be valid.");
            return;
        }

        try {
            managementApp.setProjectEndWeek(endWeek);
        } catch (OperationNotAllowedException e) {
            lblProjectEndWeekError.setText(e.getMessage());
            return;
        }

        lblProjectEndWeek.setText(tfProjectEndWeek.getText());
        lblProjectEndWeekError.setText("");
    }

    @FXML
    public void onBtnGetProjectStats() {
        try {
            lblProjectSpendHours.setText(String.valueOf(managementApp.getSpendHoursOnProject()));
            lblProjectExpectedHours.setText(String.valueOf(managementApp.getExpectedHoursOnProject()));
            lblProjectRemainingHours.setText(String.valueOf(managementApp.getRemainingHoursOnProject()));
        } catch(OperationNotAllowedException e) {
            lblProjectStatsError.setText(e.getMessage());
            return;
        }

        lblProjectStatsError.setText("");
    }

    @FXML
    public void onBtnAddNewProjectActivity() {
        if ((tfProjectActivityName.getLength() == 0) || tfProjectActivityName.getLength() > 20) {
            lblProjectActivityNameError.setText("Activity names need to be between 1 and 20 characters");
            tfProjectActivityName.requestFocus();
            return;
        }

        if (!tfProjectActivityName.getText().matches("^[a-zA-Z0-9]+$")) {
            lblProjectActivityNameError.setText("Activity names can only contain alphanumeric characters.");
            tfProjectActivityName.requestFocus();
            return;
        }

        managementApp.addNewProjectActivity(tfProjectActivityName.getText());

        lblProjectActivityNameError.setText("");
        tfProjectActivityName.setText("");
    }
    @FXML
    public void onBtnViewProjectActivity() throws OperationNotAllowedException {
        back = tabPane.getSelectionModel().getSelectedIndex();
        Activity selectedActivity = lvProjectActivities.getSelectionModel().getSelectedItem();
        managementApp.selectActivity(selectedActivity);
        loadActivity();
        tabPane.getSelectionModel().select(2);
    }
    @FXML
    public void onBtnDeleteProjectActivity() {
        Activity selectedActivity = lvProjectActivities.getSelectionModel().getSelectedItem();
        managementApp.deleteProjectActivity(selectedActivity);
    }



    // ActivityView Pane
    @FXML
    public void onBtnChangeActivityName() {
        if (!tfChangeActivityName.getText().matches("^[a-zA-Z0-9]+$")) {
            lblChangeActivityNameError.setText("Project names can only contain alphanumeric characters.");
            tfChangeActivityName.requestFocus();
            return;
        }

        if ((tfChangeActivityName.getLength() == 0) || tfChangeActivityName.getLength() > 20) {
            lblChangeActivityNameError.setText("Project names need to be between 1 and 20 characters");
            tfChangeActivityName.requestFocus();
            return;
        }

        managementApp.setActivityName(tfChangeActivityName.getText());
        lblActivityName.setText(tfChangeActivityName.getText());

        lblChangeActivityNameError.setText("");
        tfChangeActivityName.setText("");

        lvProjectActivities.refresh();
    }
    @FXML
    public void onBtnRegisterHours() {
        if (!tfRegisterHours.getText().matches("^\\d+(.\\d+)?$")) {
            lblRegisterHoursError.setText("The amount of hours must be a positive number.");
            return;
        }

        float registeredHours = Float.parseFloat(tfRegisterHours.getText());

        try {
            managementApp.registerWorkHoursOnActivity(registeredHours);
        } catch (OperationNotAllowedException e) {
            lblRegisterHoursError.setText(e.getMessage());
            return;
        }

        lblRegisteredHours.setText(tfRegisterHours.getText());

        lblRegisterHoursError.setText("");
        tfRegisterHours.setText("");
    }
    @FXML
    public void onBtnSetActivityExpectedHours() {
        if (!tfSetExpectedHours.getText().matches("^\\d+(.\\d+)?$")) {
            lblActivityExpectedHoursError.setText("The amount of hours must be a positive number.");
            return;
        }

        float expectedHours = Float.parseFloat(tfSetExpectedHours.getText());

        try {
            managementApp.setExpectedWorkHoursOnActivity(expectedHours);
        } catch (OperationNotAllowedException e) {
            lblActivityExpectedHoursError.setText(e.getMessage());
            return;
        }
    }
    @FXML
    public void onBtnSetActivityStartWeek() {

    }
    @FXML
    public void onBtnSetActivityEndWeek() {

    }

    @FXML
    public void onBtnGetActivityStats() {

    }

    @FXML
    public void onBtnAssignEmployee() {
        Employee selectedEmployee = cbAssignEmployee.getSelectionModel().getSelectedItem();
        try {
            managementApp.assignEmployeeToActivity(selectedEmployee);
        } catch(OperationNotAllowedException e) {
            lblProjectActivityAssignError.setText(e.getMessage());
        }
    }
    @FXML
    public void onBtnUnassignEmployee() {

    }
    @FXML
    public void onBtnFindAvailableEmployees() {

    }



    // Activities Pane
    @FXML
    public void onBtnAddNewUserActivity() throws OperationNotAllowedException {
        if ((tfActivityName.getLength() == 0) || tfActivityName.getLength() > 20) {
            lblActivityNameError.setText("Activity names need to be between 1 and 20 characters");
            tfActivityName.requestFocus();
            return;
        }

        if (!tfActivityName.getText().matches("^[a-zA-Z0-9]+$")) {
            lblActivityNameError.setText("Activity names can only contain alphanumeric characters.");
            tfActivityName.requestFocus();
            return;
        }

        managementApp.addNewUserActivity(tfActivityName.getText());

        lblActivityNameError.setText("");
        tfActivityName.setText("");
    }

    @FXML
    public void onBtnViewActivity() throws OperationNotAllowedException {
        back = tabPane.getSelectionModel().getSelectedIndex();
        Activity selectedActivity = lvActivities.getSelectionModel().getSelectedItem();
        managementApp.selectActivity(selectedActivity);
        loadActivity();
        tabPane.getSelectionModel().select(2);
    }
    @FXML
    public void onBtnUnassignActivity() {
        Activity selectedActivity = lvActivities.getSelectionModel().getSelectedItem();
        managementApp.selectActivity(selectedActivity);
        managementApp.unassignEmployeeFromActivity(managementApp.getUser());

    }

    // Employees Pane
    @FXML
    public void onBtnAddNewEmployee() throws IOException {
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

    @FXML
    public void onBtnRemoveEmployee() {
        Employee selectedEmployee = lvEmp.getSelectionModel().getSelectedItem();
        try {
            managementApp.removeEmployee(selectedEmployee);
        } catch (OperationNotAllowedException e) {
            // TODO: add popup
        }
    }
}
