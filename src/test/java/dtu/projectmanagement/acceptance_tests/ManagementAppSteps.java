package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;
import io.cucumber.java.en.*;


import java.util.List;

import static org.junit.Assert.*;

public class ManagementAppSteps {

    private ManagementApp managementApp;
    private EmployeeHelper employeeHelper;

    public ManagementAppSteps(ManagementApp managementApp, EmployeeHelper employeeHelper){
        this.managementApp = managementApp;
        this.employeeHelper = employeeHelper;
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

    @Given("there is an employee logged in to the system")
    public void thereIsAnEmployeeLoggedInToTheSystem() {
        employeeHelper.addEmployee();
        employeeHelper.login();
    }
}
