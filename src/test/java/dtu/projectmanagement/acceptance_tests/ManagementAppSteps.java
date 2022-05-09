package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Employee;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

public class ManagementAppSteps {

    private final ManagementApp managementApp;
    private final ErrorMessageHolder errorMessage;

    private String userUsername;

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

    @Given("there is {int} projects in the management application")
    public void thereIsProjectsInTheManagementApplication(int numberOfProjects) {
        for (int i = 0; i < numberOfProjects; ++i) {
            managementApp.createNewProject();
        }
    }

    @Then("the size of the project repo is {int}")
    public void theSizeOfTheProjectRepoIs(int numberOfProjects) {
        assertEquals(numberOfProjects, managementApp.getProjectRepo().size());
    }

    @Given("the employeeRepo does not contain an employee with the username {string}")
    public void theEmployeeRepoDoesNotContainAnEmployeeWithTheUsername(String username) {
        assertFalse(managementApp.getEmployeeRepo().stream().anyMatch(employee -> employee.getUsername().equals(username)));
    }

    @When("the employee adds a new employee with the username {string}")
    public void theEmployeeAddsANewEmployeeWithTheUsername(String username) {
        try {
            managementApp.addEmployee(username);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the employeeRepo contains and employee with the username {string}")
    public void theEmployeeRepoContainsAndEmployeeWithTheUsername(String username) {
        assertTrue(managementApp.getEmployeeRepo().stream().anyMatch(employee -> employee.getUsername().equals(username)));
    }

    @When("the employee removes and employee that is not in the system")
    public void theEmployeeRemovesAndEmployeeThatIsNotInTheSystem() {
        Employee notExistent = new Employee("test");
        try {
            managementApp.removeEmployee(notExistent);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @When("the employee removes the employee with the username {string}")
    public void theEmployeeRemovesTheEmployeeWithTheUsername(String username) {
        try {
            managementApp.removeEmployee(managementApp.getEmployee(username));
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @When("the user queries for the users username")
    public void theUserQueriesForTheUsersUsername() {
        try {
            userUsername = managementApp.getUserUsername();
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the user gets the username {string}")
    public void theUserGetsTheUsername(String username) {
        assertEquals(userUsername, username);
    }

    @Given("there is five employee in the management application")
    public void thereIsEmployeeInTheManagementApplication() throws OperationNotAllowedException {
        managementApp.addEmployee("test");
        managementApp.addEmployee("tes");
        managementApp.addEmployee("te");
        managementApp.addEmployee("foo");
        managementApp.addEmployee("bar");

    }

    @Given("there is zero employee in the management application")
    public void thereIsEmployeeInTheManagementApplicationZero() {
        assertEquals(0, managementApp.getEmployeeRepo().size());
    }

    @Then("the size of the employee repo is {int}")
    public void theSizeOfTheEmployeeRepoIs(int numberOfEmployees) {
        assertEquals(numberOfEmployees, managementApp.getEmployeeRepo().size());
    }
}
