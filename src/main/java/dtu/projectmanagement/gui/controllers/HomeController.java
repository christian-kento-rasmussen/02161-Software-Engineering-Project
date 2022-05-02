package dtu.projectmanagement.gui.controllers;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;
import dtu.projectmanagement.gui.ManagementAppGUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
    @FXML private Button btnEditProject;

    // Activities Pane
    @FXML private ListView<Activity> lvActivities;
    @FXML private Label lblActivityNameError;
    @FXML private TextField tfActivityName;
    @FXML private Button btnEditActivity;

    // Employees Pane
    @FXML private ListView<Employee> lvEmp;

    // ProjectEdit Pane
    @FXML private Label lblProjectNum;
    @FXML private Label lblProjectName;
    @FXML private Label lblProjectNameError;
    @FXML private TextField tfChangeProjectName;
    @FXML private Label lblProjectLeaderUsername;
    @FXML private ComboBox<Employee> cbPickProjectLeader;
    @FXML private Button btnChangeProjectLeader;
    @FXML private Label lblProjectActivityNameError;
    @FXML private TextField tfProjectActivityName;
    @FXML private ListView<Activity> lvProjectActivities;
    @FXML private Button btnEditProjectActivity;

    // ActivityEdit Pane
    @FXML private Label lblActivityProjectNum;
    @FXML private Label lblActivityNum;

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
                btnEditProject.setDisable(true);
                return;
            }
            btnEditProject.setDisable(false);
        });

        // Activities Pane
        lvActivities.setItems(managementApp.getUserActivities());
        lvActivities.setCellFactory(activityListView -> new ActivityListViewCell());
        lvActivities.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (lvActivities.getSelectionModel().getSelectedItem() == null) {
                btnEditActivity.setDisable(true);
                return;
            }
            btnEditActivity.setDisable(false);
        });

        // Employee Pane
        lvEmp.setItems(managementApp.getEmployeeRepo());
        lvEmp.setCellFactory(employeeListView -> new EmployeeListViewCell());

        // ProjectEdit Pane
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
                btnEditProjectActivity.setDisable(true);
                return;
            }
            btnEditProjectActivity.setDisable(false);
        });

        // ActivityEdit Pane
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

    class EmployeeListViewCell extends ListCell<Employee> {

        //@FXML private FontAwesomeIconView
        @FXML private Label lblUsername;
        @FXML private HBox cell;

        public EmployeeListViewCell() {
            super();
        }

        @Override
        protected void updateItem(Employee emp, boolean empty) {
            super.updateItem(emp, empty);

            if (empty || emp == null) {
                setText(null);
                setGraphic(null);
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("components/empListCell-comp.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                lblUsername.setText(emp.getUsername());

                cell.setPrefWidth(lvEmp.getWidth() - 16);

                setText(null);
                setGraphic(cell);
            }
        }
    }

    class ProjectListViewCell extends ListCell<Project> {

        //@FXML private FontAwesomeIconView
        @FXML private Label lblCellProjectName;
        @FXML private HBox cell;

        public ProjectListViewCell() {
            super();
        }

        @Override
        protected void updateItem(Project proj, boolean empty) {
            super.updateItem(proj, empty);

            if (empty || proj == null) {
                setText(null);
                setGraphic(null);
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("components/projListCell-comp.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                lblCellProjectName.setText(proj.getProjectNum());

                cell.setPrefWidth(lvEmp.getWidth() - 16);

                setText(null);
                setGraphic(cell);
            }
        }
    }

    class ActivityListViewCell extends ListCell<Activity> {

        //@FXML private FontAwesomeIconView
        @FXML private Label lblCellActivityName;
        @FXML private HBox cell;

        public ActivityListViewCell() {
            super();
        }

        @Override
        protected void updateItem(Activity act, boolean empty) {
            super.updateItem(act, empty);

            if (empty || act == null) {
                setText(null);
                setGraphic(null);
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("components/actListCell-comp.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                lblCellActivityName.setText(act.getActivityName());

                cell.setPrefWidth(lvEmp.getWidth() - 16);

                setText(null);
                setGraphic(cell);
            }
        }
    }



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

        lblProjectName.setText(managementApp.getProjectName(selectedProject));
        cbPickProjectLeader.getSelectionModel().clearSelection();
        lvProjectActivities.setItems(managementApp.getProjectActivityRepo(selectedProject));
    }

    private void loadActivity() {
        lblActivityProjectNum.setText(managementApp.getProjectNum(selectedProject));
        lblActivityNum.setText(managementApp.getActivityNum(selectedProject, selectedActivity));

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
    public void onBtnEditProject() {
        loadProject();
        tabPane.getSelectionModel().select(1);
    }



    // ProjectEdit Pane
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
    }

    @FXML
    public void onBtnChangeProjectLeader() {
        Employee selectedEmployee = cbPickProjectLeader.getSelectionModel().getSelectedItem();

        managementApp.assignProjectLeader(selectedProject, selectedEmployee);
        lblProjectLeaderUsername.setText(managementApp.getProjectLeaderUsername(selectedProject));
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
    public void onBtnEditProjectActivity() {
        back = tabPane.getSelectionModel().getSelectedIndex();
        selectedActivity = lvProjectActivities.getSelectionModel().getSelectedItem();
        loadActivity();
        tabPane.getSelectionModel().select(2);
    }


    // ActivityEdit Pane
    @FXML
    public void onBtnAssignEmployee() throws OperationNotAllowedException {
        Employee selectedEmployee = cbAssignEmployee.getSelectionModel().getSelectedItem();
        // add duplicate check
        managementApp.assignEmployeeToActivity(selectedProject, selectedActivity, selectedEmployee);
    }


    // Activities Pane
    @FXML
    public void onBtnEditActivity() {
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
        if (!Objects.isNull(stagePopUp))
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
