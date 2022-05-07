package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Employee;
import io.cucumber.java.en.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ManagementAppSteps {

    private final ManagementApp managementApp;
    private final ErrorMessageHolder errorMessage;

    public ManagementAppSteps(ManagementApp managementApp, ErrorMessageHolder errorMessage){
        this.managementApp = managementApp;
        this.errorMessage = errorMessage;
    }



    @And("there is an employee with username {string}")
    public void thereIsAnEmployeeWithUsername(String username) throws OperationNotAllowedException {
        managementApp.addEmployee(username);
    }

    @And("the employee with username {string} is logged in")
    public void theEmployeeWithUsernameIsSignedIn(String username) {
        managementApp.login(managementApp.getEmployee(username));
    }

    @Then("test projectnum {string} and activity name {string}")
    public void testProjectnumAndActivityName(String string1, String string2) throws OperationNotAllowedException {
        //managementApp.listAvailableEmployeesForActivity();
    }
}
