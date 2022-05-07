package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;

import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class ActivitySteps {

    ManagementApp managementApp;

    private float spendWorkHours;
    private float remainingWorkHours;
    private float totalExpectedWorkHours;
    private float employeeTotalHours;

    private final ProjectHelper projectHelper;
    private final ErrorMessageHolder errorMessage;

    public ActivitySteps(ManagementApp managementApp, ProjectHelper projectHelper, ErrorMessageHolder errorMessage){
        this.managementApp = managementApp;
        this.projectHelper = projectHelper;
        this.errorMessage = errorMessage;
    }



    @Then("the start and end time of the activity is {int} and {int}, respectively")
    public void theStartAndEndTimeOfTheActivityIsAndRespectively(int startWeek, int endWeek) {
        assertEquals(managementApp.getActivityStartWeek(), startWeek);
        assertEquals(managementApp.getActivityEndWeek(), endWeek);
    }



    @And("there is an activity named {string} in the project with start week {int} and end week {int}")
    public void thereIsAnActivityWithActivityNameStartWeekAndEndWeekContainedInTheProject(String activityName, int startWeek, int endWeek) throws OperationNotAllowedException {
        managementApp.addNewProjectActivity(activityName);
        managementApp.selectActivity(managementApp.getProjectActivity(activityName));
        managementApp.setActivityStartWeek(startWeek);
        managementApp.setActivityEndWeek(endWeek);
    }

    @And("the employee with username {string} is assigned to the activity named {string}")
    public void employeeIsAttachedToActivity(String username, String activityName) throws OperationNotAllowedException {
        managementApp.selectActivity(managementApp.getProjectActivity(activityName));
        managementApp.assignEmployeeToActivity(managementApp.getEmployee(username));
    }

    @Given("the project does not have an activity named {string}")
    public void thereIsAnActivityWithActivityName(String activityName) {
        assertFalse(managementApp.getProjectActivityRepo().stream().anyMatch(activity -> activity.getActivityName().equals(activityName)));
    }

    @When("an activity named {string} is added to the project")
    public void theActivityIsAddedToTheProject(String activityName) {
        managementApp.addNewProjectActivity(activityName);
    }

    @And("there is an activity named {string} in the project")
    public void thereIsAnActivityNamedInTheProject(String activityName) {
        managementApp.addNewProjectActivity(activityName);
    }

    @Then("the project has an activity named {string}")
    public void theActivityWithActivityNameIsContainedInProject(String activityName) {
        assertTrue(managementApp.getProjectActivityRepo().stream().anyMatch(activity -> activity.getActivityName().equals(activityName)));
    }

    @When("the activity named {string} is deleted from the project")
    public void theActivityIsDeletedFromProject(String activityName) {
        managementApp.deleteProjectActivity(managementApp.getProjectActivity(activityName));
    }

    @When("the employee assigns the other employee with username {string} to the selected activity")
    public void theEmployeeAssignsTheOtherEmployeeWithInitialsToTheActivity(String username) {
        try {
            managementApp.assignEmployeeToActivity(managementApp.getEmployee(username));
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the other employee with username {string} is assigned to the activity named {string} and vice versa")
    public void theOtherEmployeeWithUsernameIsAssignedToTheActivity(String username, String activityName) {
        assertTrue(managementApp.getEmployee(username)
                .getAssignedActivities()
                .stream()
                .anyMatch(activity -> activity.getActivityName().equals(activityName)));
        assertTrue(managementApp.getAssignedEmployees()
                .stream()
                .anyMatch(employee -> employee.getUsername().equals(username)));
    }

    @And("the other employee with username {string} is already assigned to the activity")
    public void theOtherEmployeeWithInitialsIsAlreadyAssignedToTheActivity(String username) throws OperationNotAllowedException {
        managementApp.assignEmployeeToActivity(managementApp.getEmployee(username));
    }

    @When("the user registers {float} hours spent on the activity")
    public void theEmployeeRegistersHoursSpentOnTheActivity(float hours) {
        try {
            managementApp.registerWorkHoursOnActivity(hours);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the users registered hours on the activity is {float} hours")
    public void theEmployeesWorkHoursOnTheActivityIsHours(float hours) {
        assertEquals(hours, managementApp.getWorkedHoursOnActivity(), 0f);
    }

    @Then("the expected hours on the activity is {int} hours")
    public void theExpectedWorkHoursOnTheActivityIs(int hours) throws OperationNotAllowedException {
        assertEquals(hours, managementApp.getExpectedWorkHoursOnActivity(), 0.0);
    }

    @When("the employee queries their registered work hours on an activity")
    public void theEmployeeQueriesTheirRegisteredWorkHoursOnAnActivity() {
        employeeTotalHours = managementApp.getWorkedHoursOnActivity();
    }

    @Then("the result of the query is {float} work hours")
    public void theResultOfTheQueryIsWorkHours(float hours) {
        assertEquals(hours, employeeTotalHours, 0f);
    }

    @And("the activity named {string} is selected")
    public void theActivityNamedIsSelected(String activityName) {
        try {
            managementApp.selectActivity(managementApp.getProjectActivity(activityName));
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project does not have an activity named {string} and the employee with username {string} is not assigned to the activity")
    public void theProjectDoesNotHaveAnActivityNamedAndTheEmployeeWithUsernameIsNotAssignedToTheActivity(String activityName, String username) {
        assertFalse(managementApp.getProjectActivityRepo()
                .stream()
                .anyMatch(activity -> activity.getActivityName().equals(activityName)));
        assertFalse(managementApp.getEmployee(username)
                .getAssignedActivities()
                .stream()
                .anyMatch(activity -> activity.getActivityName().equals(activityName)));
    }

    @And("the employee has an activity named {string}")
    public void theCurrentUserHasAnActivityNamed(String activityName) throws OperationNotAllowedException {
        managementApp.addNewUserActivity(activityName);
    }

    @When("the employee activity named {string} is deleted")
    public void theUserActivityNamedIsDeleted(String activityName) {
        managementApp.deleteUserActivity(managementApp.getUserActivity(activityName));
    }

    @Then("the employee does not have an activity named {string}")
    public void theUserDoesNotHaveAnActivityNamed(String activityName) {
        assertFalse(managementApp.getUserActivities().stream().anyMatch(activity -> activity.getActivityName().equals(activityName)));
    }

    @When("the user queries for the remaining hours on the activity")
    public void theUserQueriesForTheRemainingHoursOnTheActivity() {
        try {
            remainingWorkHours = managementApp.getRemainingHoursOnActivity();
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the remaining work hours on the activity is {int} hours")
    public void theRemainingWorkRemainingHoursOnTheActivityIsHours(int hours) {
        assertEquals(hours, remainingWorkHours, 0f);
    }

    @And("the user sets the expected hours on the activity to {int} hours")
    public void theUserSetsTheExpectedHoursOnTheActivityTo(int hours) {
        try {
            managementApp.setExpectedWorkHoursOnActivity(hours);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @When("the user queries for the spend hours on the activity")
    public void theUserQueriesForTheSpendHoursOnTheActivity() {
        try {
            spendWorkHours = managementApp.getSpendHoursOnActivity();
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the spend hours on the activity is {int} hours")
    public void theSpendWorkHoursOnTheActivityIsHours(int hours) {
        assertEquals(hours, spendWorkHours, 0f);
    }

    @When("the user sets the start and end time of the activity to {int} and {int}, respectively")
    public void theUserSetsTheStartAndEndTimeOfTheActivityToAndRespectively(int startWeek, int endWeek) {
        try {
            managementApp.setActivityStartWeek(startWeek);
            managementApp.setActivityEndWeek(endWeek);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }
}
