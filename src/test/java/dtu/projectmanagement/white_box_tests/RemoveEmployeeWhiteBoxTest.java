package dtu.projectmanagement.white_box_tests;

import dtu.projectmanagement.acceptance_tests.ProjectHelper;
import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class RemoveEmployeeWhiteBoxTest {

    ManagementApp managementApp;
    ProjectHelper projectHelper;

    @BeforeEach
    public void setUp() {
        managementApp = new ManagementApp();
        projectHelper = new ProjectHelper(managementApp);
    }

    @AfterEach
    public void after() {
        assertFalse(managementApp.getEmployeeRepo().stream().anyMatch(employee ->
                employee.getUsername().equals("test")));
        assertFalse(managementApp.getProjectRepo().stream().anyMatch(project -> {
                if (project.getProjectLeader() != null)
                    return project.getProjectLeader().getUsername().equals("test");
                return false;
        }));
        assertFalse(managementApp.getProjectRepo().stream().anyMatch(project ->
                project.getActivityRepo().stream().anyMatch(activity ->
                        activity.getAssignedEmployees().stream().anyMatch(employee ->
                                employee.getUsername().equals("test")
                        )
                ))
        );
    }

    @Test
    @DisplayName("Employee is not in the system")
    public void testInputDataSetA() {
        assertThrows(OperationNotAllowedException.class, () -> {
           managementApp.removeEmployee(managementApp.getEmployee("test"));
        });
    }


    @Test
    @DisplayName("Employee is in the system, 0 projects in projectRepo")
    public void testInputDataSetB() throws OperationNotAllowedException {
        managementApp.addEmployee("test");
        managementApp.removeEmployee(managementApp.getEmployee("test"));
    }

    @Test
    @DisplayName("Employee is in the system, 1 project in projectRepo, not project leader, 0 activities")
    public void testInputDataSetC() throws OperationNotAllowedException {
        managementApp.addEmployee("test");
        projectHelper.addProject();
        managementApp.removeEmployee(managementApp.getEmployee("test"));
    }

    @Test
    @DisplayName("Employee is in the system, 1 project in projectRepo, is project leader, 1 activity, no activities assigned")
    public void testInputDataSetD() throws OperationNotAllowedException {
        managementApp.addEmployee("test");
        projectHelper.addProject();
        managementApp.assignProjectLeader(projectHelper.getProject(), managementApp.getEmployee("test"));
        managementApp.addNewProjectActivity(projectHelper.getProject(), "activity1");
        managementApp.removeEmployee(managementApp.getEmployee("test"));
    }

    @Test
    @DisplayName("Employee is in the system, 2 projects in projectRepo, is not project leader, 2 activities, assigned activity")
    public void testInputDataSetE() throws OperationNotAllowedException {
        managementApp.addEmployee("test");
        projectHelper.addProject();
        projectHelper.addProject();
        managementApp.addNewProjectActivity(projectHelper.getProject(), "activity1");
        managementApp.addNewProjectActivity(projectHelper.getProject(), "activity2");
        managementApp.assignEmployeeToActivity(projectHelper.getActivity("activity1"), managementApp.getEmployee("test"));
        managementApp.removeEmployee(managementApp.getEmployee("test"));
    }
}
