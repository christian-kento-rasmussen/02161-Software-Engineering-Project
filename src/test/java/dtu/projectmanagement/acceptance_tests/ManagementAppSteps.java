package dtu.projectmanagement.acceptance_tests.steps;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.junit.Assert.*;

public class ManagementAppSteps {
    ManagementApp managementApp;

    public ManagementAppSteps(ManagementApp managementApp){
        this.managementApp = managementApp;
    }

    @Then("available employees for activityName {string} is {string}")
    public void availableEmployeesForActivityNameIs (String activityName, String employee) {
        Project project = managementApp.getProject("220001");
        Activity activity = project.getActivity(activityName);
        List<Employee> employees = managementApp.ListAvailableEmployeesForActivity("220001",activityName);
        assertNotNull(employees);
    }

    @Then("there is no available employees for activityName {string}")
    public void thereIsNoAvailableEmployeesForActivityName(String activityName) {
        Project project = managementApp.getProject("220001");
        Activity activity = project.getActivity(activityName);
        List<Employee> employees = managementApp.ListAvailableEmployeesForActivity("220001",activityName);
        assertTrue(employees.size() == 0);
    }

    @And("the employee with username {string} is signed in")
    public void theEmployeeWithUsernameIsSignedIn(String username) {
        managementApp.login(username);
    }
}
