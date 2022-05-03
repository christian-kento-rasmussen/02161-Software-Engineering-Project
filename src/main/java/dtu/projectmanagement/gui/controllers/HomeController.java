package dtu.projectmanagement.gui.controllers;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;
import dtu.projectmanagement.gui.ManagementAppGUI;
import dtu.projectmanagement.gui.controllers.listviewcell.ActivityListViewCell;
import dtu.projectmanagement.gui.controllers.listviewcell.EmployeeListViewCell;
import dtu.projectmanagement.gui.controllers.listviewcell.ProjectListViewCell;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    ManagementApp managementApp;
    Project selectedProject;
    Activity selectedActivity;

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

    // Employees Pane
    @FXML private ListView<Employee> lvEmp;

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

        // Navbar
        lblCurrentUser.setText(managementApp.getUserUsername());

        // Project Pane
        lvProjects.setItems(managementApp.getProjectRepo());
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
        lvActivities.setItems(managementApp.getUserActivities());
        lvActivities.setCellFactory(activityListView -> new ActivityListViewCell());
        lvActivities.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (lvActivities.getSelectionModel().getSelectedItem() == null) {
                btnViewActivity.setDisable(true);
                return;
            }
            btnViewActivity.setDisable(false);
        });

        // Employee Pane
        lvEmp.setItems(managementApp.getEmployeeRepo());
        lvEmp.setCellFactory(employeeListView -> new EmployeeListViewCell());

        // ProjectView Pane
        cbPickProjectLeader.setItems(managementApp.getEmployeeRepo());
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
        cbAssignEmployee.setItems(managementApp.getEmployeeRepo());
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



    // Utility Methods

    @FXML
    public void onBtnActivityBack() {
        if (back == 1)
            loadProject();

        tabPane.getSelectionModel().select(back);
    }

    private void loadProject() {
        selectedProject = lvProjects.getSelectionModel().getSelectedItem();

        lblProjectNum.setText(managementApp.getProjectNum(selectedProject));

        if (managementApp.getProjectLeader(selectedProject) == null)
            lblProjectLeaderUsername.setText("N/A");
        else
            lblProjectLeaderUsername.setText(managementApp.getProjectLeaderUsername(selectedProject));

        if (managementApp.getProjectStartWeek(selectedProject) == 0)
            lblProjectStartWeek.setText("N/A");
        else
            lblProjectStartWeek.setText(String.valueOf(managementApp.getProjectStartWeek(selectedProject)));

        if (managementApp.getProjectEndWeek(selectedProject) == 0)
            lblProjectStartWeek.setText("N/A");
        else
            lblProjectStartWeek.setText(String.valueOf(managementApp.getProjectEndWeek(selectedProject)));

        lblProjectName.setText(managementApp.getProjectName(selectedProject));
        cbPickProjectLeader.getSelectionModel().clearSelection();

        lblProjectSpendHours.setText("N/A");
        lblProjectExpectedHours.setText("N/A");
        lblProjectRemainingHours.setText("N/A");

        lvProjectActivities.setItems(managementApp.getProjectActivityRepo(selectedProject));
    }

    private void loadActivity() {

        // TODO: DO TYPE BASED LOADING HERE AFTER REFACTOR

        lblActivityProjectNum.setText(managementApp.getProjectNum(selectedProject));
        lblActivityNum.setText(managementApp.getActivityNum(selectedProject, selectedActivity));

        lblActivityName.setText(managementApp.getActivityName(selectedProject, selectedActivity));
        lblRegisteredHours.setText(String.valueOf(managementApp.getWorkHoursOnActivity(selectedProject, selectedActivity)));

        if (managementApp.getUser() != managementApp.getProjectLeader(selectedProject))
            lblActivityExpectedHours.setText("N/A");
        else
            lblActivityExpectedHours.setText(String.valueOf(managementApp.getActivityExpectedHours(selectedProject, selectedActivity)));

        if (managementApp.getActivityStartWeek(selectedProject, selectedActivity) == 0)
            lblProjectStartWeek.setText("N/A");
        else
            lblProjectStartWeek.setText(String.valueOf(managementApp.getActivityStartWeek(selectedProject, selectedActivity)));

        if (managementApp.getActivityEndWeek(selectedProject, selectedActivity) == 0)
            lblProjectStartWeek.setText("N/A");
        else
            lblProjectStartWeek.setText(String.valueOf(managementApp.getActivityEndWeek(selectedProject, selectedActivity)));


        cbAssignEmployee.getSelectionModel().clearSelection();
        lvAssignedEmployees.setItems(managementApp.getAssignedEmployees(selectedProject, selectedActivity));
    }

    // Navbar
    @FXML
    public void onBtnProjects() {
        tabPane.getSelectionModel().select(0);
    }
    @FXML
    public void onBtnActivities() {
        tabPane.getSelectionModel().select(3);
    }
    @FXML
    public void onBtnEmployees() {
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
        loadProject();
        tabPane.getSelectionModel().select(1);
    }

    @FXML
    public void onBtnDeleteProject() {
        selectedProject = lvProjects.getSelectionModel().getSelectedItem();
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

        managementApp.setProjectName(selectedProject, tfChangeProjectName.getText());
        lblProjectName.setText(tfChangeProjectName.getText());

        lblProjectNameError.setText("");
        tfChangeProjectName.setText("");

        lvProjects.refresh();
    }
    @FXML
    public void onBtnChangeProjectLeader() {
        Employee selectedEmployee = cbPickProjectLeader.getSelectionModel().getSelectedItem();

        managementApp.assignProjectLeader(selectedProject, selectedEmployee);
        lblProjectLeaderUsername.setText(managementApp.getProjectLeaderUsername(selectedProject));
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
            managementApp.setProjectStartWeek(selectedProject, startWeek);
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
            managementApp.setProjectEndWeek(selectedProject, endWeek);
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
            lblProjectSpendHours.setText(String.valueOf(managementApp.getSpendHoursOnProject(selectedProject)));
            lblProjectExpectedHours.setText(String.valueOf(managementApp.getExpectedHoursOnProject(selectedProject)));
            lblProjectRemainingHours.setText(String.valueOf(managementApp.getRemainingHoursOnProject(selectedProject)));
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

        managementApp.addNewProjectActivity(selectedProject, tfProjectActivityName.getText());

        lblProjectActivityNameError.setText("");
        tfProjectActivityName.setText("");
    }
    @FXML
    public void onBtnViewProjectActivity() {
        back = tabPane.getSelectionModel().getSelectedIndex();
        selectedActivity = lvProjectActivities.getSelectionModel().getSelectedItem();
        loadActivity();
        tabPane.getSelectionModel().select(2);
    }
    @FXML
    public void onBtnDeleteProjectActivity() {
        selectedActivity = lvProjectActivities.getSelectionModel().getSelectedItem();
        managementApp.deleteProjectActivity(selectedProject, selectedActivity);
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

        managementApp.setProjectActivityName(selectedProject, selectedActivity, tfChangeActivityName.getText());
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
            managementApp.registerWorkHoursOnProjectActivity(selectedProject, selectedActivity, registeredHours);
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
            managementApp.setExpectedWorkHoursOnActivity(selectedProject, selectedActivity, expectedHours);
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
            managementApp.assignEmployeeToActivity(selectedProject, selectedActivity, selectedEmployee);
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
    public void onBtnViewActivity() throws OperationNotAllowedException {
        back = tabPane.getSelectionModel().getSelectedIndex();
        selectedActivity = lvActivities.getSelectionModel().getSelectedItem();
        loadActivity();
        tabPane.getSelectionModel().select(2);
    }

    @FXML
    public void onBtnAddNewNonProjectActivity() {
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
}
