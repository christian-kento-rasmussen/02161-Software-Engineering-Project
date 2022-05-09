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
import javafx.util.StringConverter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * @author William Steffens (s185369)
 */
public class HomeController implements PropertyChangeListener {

    ManagementApp managementApp;

    private Project selectedProject;
    private Activity selectedActivity;

    Stage stagePopUp;
    private int back;

    @FXML private TabPane tabPane;

    // Navbar
    @FXML private Button btnLogOut;
    @FXML private Label lblCurrentUser;

    // ProjectRepo Pane
    @FXML private ListView<Project> lvProjects;
    @FXML private Button btnViewProject;
    @FXML private Button btnDeleteProject;

    // ActivityRepo Pane
    @FXML private ListView<Activity> lvActivities;
    @FXML private Label lblActivityNameError;
    @FXML private TextField tfActivityName;

    @FXML private Button btnViewActivity;
    @FXML private Button btnUnassignActivity;

    // EmployeeRepo Pane
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
    @FXML private Label lblParent;
    @FXML private Label lblActivityNameHeader;

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

    @FXML private Label lblActivityStatsError;
    @FXML private Label lblActivitySpendHours;
    @FXML private Label lblActivityExpectedHoursStat;
    @FXML private Label lblActivityRemainingHours;


    @FXML private Label lblProjectActivityAssignError;
    @FXML private ComboBox<Employee> cbAssignEmployee;
    @FXML private Button btnAssignEmployee;
    @FXML private ListView<Employee> lvAssignedEmployees;
    @FXML private Button btnUnassignEmployee;

    @FXML private Button btnFindAvailableEmployees;
    @FXML private Label lblFindAvailableEmployeesError;
    @FXML private ListView<Employee> lvAvailableEmployees;

    @FXML
    public void initialize() {
        managementApp = ManagementApp.getInstance();
        managementApp.addObserver(this);

        // Navbar
        try {
            lblCurrentUser.setText(managementApp.getUserUsername());
        } catch (OperationNotAllowedException e) {
            lblCurrentUser.setText("N/A");
        }

        // ProjectRepo Pane
        loadProjectRepo();

        // ActivityRepo Pane
        loadActivityRepo();

        // EmployeeRepo Pane
        loadEmployeeRepo();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String type = evt.getPropertyName();
        switch (type) {
            case NotificationType.UPDATE_PROJECT -> loadProject();
            case NotificationType.UPDATE_PROJECT_REPO -> loadProjectRepo();
            case NotificationType.UPDATE_ACTIVITY -> loadActivity();
            case NotificationType.UPDATE_ACTIVITY_REPO -> loadActivityRepo();
            case NotificationType.UPDATE_EMPLOYEE_REPO -> loadEmployeeRepo();
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
        lblProjectNum.setText(managementApp.getProjectNum(selectedProject));

        if (managementApp.getProjectLeader(selectedProject) == null)
            lblProjectLeaderUsername.setText("N/A");
        else {
            try {
                lblProjectLeaderUsername.setText(managementApp.getProjectLeaderUsername(selectedProject));
            } catch (OperationNotAllowedException e) {
                lblProjectLeaderUsername.setText("N/A");
            }
        }

        if (managementApp.getProjectStartWeek(selectedProject) == 0)
            lblProjectStartWeek.setText("N/A");
        else
            lblProjectStartWeek.setText(String.valueOf(managementApp.getProjectStartWeek(selectedProject)));

        if (managementApp.getProjectEndWeek(selectedProject) == 0)
            lblProjectEndWeek.setText("N/A");
        else
            lblProjectEndWeek.setText(String.valueOf(managementApp.getProjectEndWeek(selectedProject)));

        lblProjectName.setText(managementApp.getProjectName(selectedProject));
        cbPickProjectLeader.getSelectionModel().clearSelection();

        // clear fields
        tfChangeProjectName.setText("");
        tfProjectStartWeek.setText("");
        tfProjectEndWeek.setText("");
        tfProjectActivityName.setText("");

        // clear stats
        lblProjectSpendHours.setText("N/A");
        lblProjectExpectedHours.setText("N/A");
        lblProjectRemainingHours.setText("N/A");

        // clear err msgs
        lblProjectNameError.setText("");
        lblProjectStartWeekError.setText("");
        lblProjectEndWeekError.setText("");
        lblProjectStatsError.setText("");
        lblProjectActivityNameError.setText("");


        // ProjectView Pane
        cbPickProjectLeader.setItems(FXCollections.observableArrayList(managementApp.getEmployeeRepo()));
        cbPickProjectLeader.setCellFactory(employeeListView -> new EmployeeListViewCell());
        cbPickProjectLeader.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee employee) {
                return employee.getUsername();
            }

            @Override
            public Employee fromString(String s) {
                return cbPickProjectLeader.getItems().stream().filter(emp -> emp.getUsername().equals(s)).findAny().orElse(null);
            }
        });
        cbPickProjectLeader.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (cbPickProjectLeader.getSelectionModel().getSelectedItem() == null) {
                btnChangeProjectLeader.setDisable(true);
                return;
            }
            btnChangeProjectLeader.setDisable(false);
        });

        lvProjectActivities.setItems(FXCollections.observableArrayList(managementApp.getProjectActivityRepo(selectedProject)));
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

        loadProjectRepo();
    }

    private void loadProjectRepo() {
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
    }

    private void loadActivity() {
        if (managementApp.getActivityType(selectedActivity) == Activity.PROJECT_TYPE) {
            lblParent.setText(managementApp.getProjectNum(selectedProject));

            cbAssignEmployee.setDisable(false);
            lvAssignedEmployees.setDisable(false);
            btnFindAvailableEmployees.setDisable(false);
            lvAvailableEmployees.setDisable(false);

            loadProject();
        } else if (managementApp.getActivityType(selectedActivity) == Activity.EMPLOYEE_TYPE) {
            try {
                lblParent.setText(managementApp.getUserUsername());
            } catch (OperationNotAllowedException e) {
                lblParent.setText("N/A");
            }

            cbAssignEmployee.setDisable(true);
            lvAssignedEmployees.setDisable(true);
            btnFindAvailableEmployees.setDisable(true);
            lvAvailableEmployees.setDisable(true);
        }

        lblActivityNameHeader.setText(managementApp.getActivityName(selectedActivity));
        lblActivityName.setText(managementApp.getActivityName(selectedActivity));
        lblRegisteredHours.setText(String.valueOf(managementApp.getWorkedHoursOnActivity(selectedActivity)));

        if (managementApp.getActivityStartWeek(selectedActivity) == 0)
            lblActivityStartWeek.setText("N/A");
        else
            lblActivityStartWeek.setText(String.valueOf(managementApp.getActivityStartWeek(selectedActivity)));

        if (managementApp.getActivityEndWeek(selectedActivity) == 0)
            lblActivityEndWeek.setText("N/A");
        else
            lblActivityEndWeek.setText(String.valueOf(managementApp.getActivityEndWeek(selectedActivity)));


        try {
            lblActivityExpectedHours.setText(String.valueOf(managementApp.getExpectedWorkHoursOnActivity(selectedActivity)));
        } catch (OperationNotAllowedException e) {
            lblActivityExpectedHours.setText("N/A");
        }

        cbAssignEmployee.getSelectionModel().clearSelection();

        cbAssignEmployee.setItems(FXCollections.observableArrayList(managementApp.getEmployeeRepo()));
        cbAssignEmployee.setCellFactory(employeeListView -> new EmployeeListViewCell());
        cbAssignEmployee.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee employee) {
                return employee.getUsername();
            }

            @Override
            public Employee fromString(String s) {
                return cbAssignEmployee.getItems().stream().filter(emp -> emp.getUsername().equals(s)).findAny().orElse(null);
            }
        });
        cbAssignEmployee.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (cbAssignEmployee.getSelectionModel().getSelectedItem() == null) {
                btnAssignEmployee.setDisable(true);
                return;
            }
            btnAssignEmployee.setDisable(false);
        });

        lvAssignedEmployees.setItems(FXCollections.observableArrayList(managementApp.getAssignedEmployees(selectedActivity)));
        lvAssignedEmployees.setCellFactory(employeeListView -> new EmployeeListViewCell());
        lvAssignedEmployees.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (lvAssignedEmployees.getSelectionModel().getSelectedItem() == null) {
                btnUnassignEmployee.setDisable(true);
                return;
            }
            btnUnassignEmployee.setDisable(false);
        });

        lvAvailableEmployees.setCellFactory(employeeListView -> new EmployeeListViewCell());
        lvAssignedEmployees.getSelectionModel().clearSelection();

        lvAvailableEmployees.getItems().clear();

        // clear fields
        tfChangeActivityName.setText("");
        tfRegisterHours.setText("");
        tfSetExpectedHours.setText("");
        tfActivityStartWeek.setText("");
        tfActivityEndWeek.setText("");

        // clear stats
        lblActivitySpendHours.setText("N/A");
        lblActivityExpectedHoursStat.setText("N/A");
        lblActivityRemainingHours.setText("N/A");

        // clear err msgs
        lblActivityStatsError.setText("");
        lblChangeActivityNameError.setText("");
        lblRegisterHoursError.setText("");
        lblActivityExpectedHoursError.setText("");
        lblActivityStartWeekError.setText("");
        lblActivityEndWeekError.setText("");
        lblProjectActivityAssignError.setText("");
        lblFindAvailableEmployeesError.setText("");

        loadActivityRepo();
    }

    private void loadActivityRepo() {
        lblActivityNameError.setText("");

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
    }

    private void loadEmployeeRepo() {
        lvEmp.setItems(FXCollections.observableArrayList(managementApp.getEmployeeRepo()));
        lvEmp.setCellFactory(employeeListView -> new EmployeeListViewCell());
        lvEmp.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (lvEmp.getSelectionModel().getSelectedItem() == null) {
                btnRemoveEmployee.setDisable(true);
                return;
            }
            btnRemoveEmployee.setDisable(false);
        });
    }

    // Navbar
    @FXML
    public void onBtnProjects() {
        lvProjects.getSelectionModel().clearSelection();
        loadProjectRepo();
        tabPane.getSelectionModel().select(0);
    }
    @FXML
    public void onBtnActivities() {
        lvActivities.getSelectionModel().clearSelection();
        loadActivityRepo();
        tabPane.getSelectionModel().select(3);
    }
    @FXML
    public void onBtnEmployees() {
        lvEmp.getSelectionModel().clearSelection();
        loadEmployeeRepo();
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



    // ProjectRepo Pane
    @FXML
    public void onBtnAddNewProject() {
        managementApp.createNewProject();
    }

    @FXML
    public void onBtnViewProject() {
        selectedProject = lvProjects.getSelectionModel().getSelectedItem();
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

        lblProjectNameError.setText("");
        tfChangeProjectName.setText("");
    }
    @FXML
    public void onBtnChangeProjectLeader() {
        Employee selectedEmployee = cbPickProjectLeader.getSelectionModel().getSelectedItem();
        managementApp.assignProjectLeader(selectedProject, selectedEmployee);
    }
    @FXML
    public void onBtnSetProjectStartWeek() {
        if (!tfProjectStartWeek.getText().matches("^\\d{6}$")) {
            lblProjectStartWeekError.setText("Wrong format; the correct format is yyyyww");
            return;
        }

        int startWeek = Integer.parseInt(tfProjectStartWeek.getText());

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

        lblProjectStartWeekError.setText("");
        tfProjectStartWeek.setText("");
    }
    @FXML
    public void onBtnSetProjectEndWeek() {
        if (!tfProjectEndWeek.getText().matches("^\\d{6}$")) {
            lblProjectEndWeekError.setText("Wrong format; the correct format is yyyyww");
            return;
        }

        int endWeek = Integer.parseInt(tfProjectEndWeek.getText());

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

        lblProjectEndWeekError.setText("");
        tfProjectEndWeek.setText("");
    }

    @FXML
    public void onBtnGetProjectStats() {
        try {
            lblProjectSpendHours.setText(String.valueOf(managementApp.getSpendHoursOnProject(selectedProject)));
            lblProjectExpectedHours.setText(String.valueOf(managementApp.getExpectedHoursOnProject(selectedProject)));
            lblProjectRemainingHours.setText(String.valueOf(managementApp.getRemainingHoursOnProject(selectedProject)));
        } catch(OperationNotAllowedException e) {
            lblProjectStatsError.setText(e.getMessage());
        }
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

        try {
            managementApp.addNewProjectActivity(selectedProject, tfProjectActivityName.getText());
        } catch (OperationNotAllowedException e) {
            lblProjectActivityNameError.setText(e.getMessage());
        }

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
            lblChangeActivityNameError.setText("Activity names can only contain alphanumeric characters.");
            tfChangeActivityName.requestFocus();
            return;
        }

        if ((tfChangeActivityName.getLength() == 0) || tfChangeActivityName.getLength() > 20) {
            lblChangeActivityNameError.setText("Activity names need to be between 1 and 20 characters");
            tfChangeActivityName.requestFocus();
            return;
        }

        try {
            managementApp.setActivityName(selectedActivity, tfChangeActivityName.getText());
        } catch (OperationNotAllowedException e) {
            lblChangeActivityNameError.setText(e.getMessage());
        }

        lblChangeActivityNameError.setText("");
        tfChangeActivityName.setText("");
    }
    @FXML
    public void onBtnRegisterHours() {
        if (!tfRegisterHours.getText().matches("^\\d+(.\\d+)?$")) {
            lblRegisterHoursError.setText("The amount of hours must be a positive number.");
            return;
        }

        float registeredHours = Float.parseFloat(tfRegisterHours.getText().replace(",","."));

        try {
            managementApp.registerWorkHoursOnActivity(selectedActivity, registeredHours);
        } catch (OperationNotAllowedException e) {
            lblRegisterHoursError.setText(e.getMessage());
        }
    }
    @FXML
    public void onBtnSetActivityExpectedHours() {
        if (!tfSetExpectedHours.getText().matches("^\\d+(.\\d+)?$")) {
            lblActivityExpectedHoursError.setText("The amount of hours must be a positive number.");
            return;
        }

        float expectedHours = Float.parseFloat(tfSetExpectedHours.getText());

        try {
            managementApp.setExpectedWorkHoursOnActivity(selectedActivity, expectedHours);
        } catch (OperationNotAllowedException e) {
            lblActivityExpectedHoursError.setText(e.getMessage());
        }
    }
    @FXML
    public void onBtnSetActivityStartWeek() {
        if (!tfActivityStartWeek.getText().matches("^\\d{6}$")) {
            lblActivityStartWeekError.setText("Wrong format; the correct format is yyyyww");
            return;
        }

        int startWeek = Integer.parseInt(tfActivityStartWeek.getText());

        if (startWeek % 100 > 52) {
            lblActivityStartWeekError.setText("the week number must be valid.");
            return;
        }

        try {
            managementApp.setActivityStartWeek(selectedActivity, startWeek);
        } catch (OperationNotAllowedException e) {
            lblActivityStartWeekError.setText(e.getMessage());
            return;
        }

        lblActivityStartWeekError.setText("");
        tfActivityStartWeek.setText("");
    }
    @FXML
    public void onBtnSetActivityEndWeek() {
        if (!tfActivityEndWeek.getText().matches("^\\d{6}$")) {
            lblActivityEndWeekError.setText("Wrong format; the correct format is yyyyww");
            return;
        }

        int endWeek = Integer.parseInt(tfActivityEndWeek.getText());

        if (endWeek % 100 > 52) {
            lblActivityEndWeekError.setText("the week number must be valid.");
            return;
        }

        try {
            managementApp.setActivityEndWeek(selectedActivity, endWeek);
        } catch (OperationNotAllowedException e) {
            lblActivityEndWeekError.setText(e.getMessage());
            return;
        }

        lblActivityEndWeekError.setText("");
        tfActivityEndWeek.setText("");
    }

    @FXML
    public void onBtnGetActivityStats() {
        try {
            lblActivitySpendHours.setText(String.valueOf(managementApp.getSpendHoursOnActivity(selectedActivity)));
            lblActivityExpectedHours.setText(String.valueOf(managementApp.getExpectedWorkHoursOnActivity(selectedActivity)));
            lblActivityExpectedHoursStat.setText(String.valueOf(managementApp.getExpectedWorkHoursOnActivity(selectedActivity)));
            lblActivityRemainingHours.setText(String.valueOf(managementApp.getRemainingHoursOnActivity(selectedActivity)));
        } catch(OperationNotAllowedException e) {
            lblActivityStatsError.setText(e.getMessage());
        }
    }

    @FXML
    public void onBtnAssignEmployee() {
        Employee selectedEmployee = cbAssignEmployee.getSelectionModel().getSelectedItem();
        try {
            managementApp.assignEmployeeToActivity(selectedActivity, selectedEmployee);
        } catch(OperationNotAllowedException e) {
            lblProjectActivityAssignError.setText(e.getMessage());
        }
    }
    @FXML
    public void onBtnUnassignEmployee() {
        Employee selectedEmployee = lvAssignedEmployees.getSelectionModel().getSelectedItem();
        try {
            managementApp.unassignEmployeeFromActivity(selectedActivity, selectedEmployee);
        } catch (OperationNotAllowedException ignore) {}
    }
    @FXML
    public void onBtnFindAvailableEmployees() {
        try {
            lvAvailableEmployees.setItems(FXCollections.observableArrayList(managementApp.listAvailableEmployeesForActivity(selectedActivity)));
        } catch (OperationNotAllowedException e) {
            lblFindAvailableEmployeesError.setText(e.getMessage());
        }
    }



    // ActivityRepo Pane
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
    public void onBtnViewActivity() {
        back = tabPane.getSelectionModel().getSelectedIndex();
        selectedActivity = lvActivities.getSelectionModel().getSelectedItem();
        loadActivity();
        tabPane.getSelectionModel().select(2);
    }
    @FXML
    public void onBtnUnassignActivity() {
        selectedActivity = lvActivities.getSelectionModel().getSelectedItem();
        loadActivityRepo();
        try {
            managementApp.unassignEmployeeFromActivity(selectedActivity, managementApp.getUser());
        } catch (OperationNotAllowedException ignore) {}
    }

    // EmployeeRepo Pane
    @FXML
    public void onBtnAddNewEmployee() throws IOException {
        // Close the pop-up if it's already open
        if (stagePopUp != null)
            stagePopUp.close();

        // Create and display popup
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
    public void onBtnRemoveEmployee() throws IOException, OperationNotAllowedException {
        Employee selectedEmployee = lvEmp.getSelectionModel().getSelectedItem();

        if (managementApp.getUser() == selectedEmployee)
            switchToLogInScene();

        managementApp.removeEmployee(selectedEmployee);
    }
}
