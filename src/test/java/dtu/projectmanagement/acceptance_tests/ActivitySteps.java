package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;

import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class ActivitySteps {

    ManagementApp managementApp;

    private float spendWorkHours;
    private float remainingWorkHours;
    private float totalExpectedWorkHours;
    private float employeeTotalHours;

    private List<Employee> availableEmployeesForActivity;

    private final ProjectHelper projectHelper;
    private final ErrorMessageHolder errorMessage;

    public ActivitySteps(ManagementApp managementApp, ProjectHelper projectHelper, ErrorMessageHolder errorMessage){
        this.managementApp = managementApp;
        this.projectHelper = projectHelper;
        this.errorMessage = errorMessage;
    }



    @Then("the start and end time of the activity is {int} and {int}, respectively")
    public void theStartAndEndTimeOfTheActivityIsAndRespectively(int startWeek, int endWeek) {
        //assertEquals(managementApp.getActivityStartWeek(), startWeek);
        //assertEquals(managementApp.getActivityEndWeek(), endWeek);
    }



    @And("there is an activity named {string} in the project with start week {int} and end week {int}")
    public void thereIsAnActivityWithActivityNameStartWeekAndEndWeekContainedInTheProject(String activityName, int startWeek, int endWeek) throws OperationNotAllowedException {
        managementApp.addNewProjectActivity(projectHelper.getProject(), activityName);
        managementApp.setActivityStartWeek(projectHelper.getActivity(activityName), startWeek);
        managementApp.setActivityEndWeek(projectHelper.getActivity(activityName), endWeek);
    }

    @Given("the project does not have an activity named {string}")
    public void thereIsAnActivityWithActivityName(String activityName) {
        assertFalse(managementApp.getProjectActivityRepo(projectHelper.getProject()).stream().anyMatch(activity -> activity.getActivityName().equals(activityName)));
    }

    @When("an activity named {string} is added to the project")
    public void theActivityIsAddedToTheProject(String activityName) {
        managementApp.addNewProjectActivity(projectHelper.getProject(), activityName);
    }

    @And("there is an activity named {string} in the project")
    public void thereIsAnActivityNamedInTheProject(String activityName) {
        managementApp.addNewProjectActivity(projectHelper.getProject(), activityName);
    }

    @Then("the project has an activity named {string}")
    public void theActivityWithActivityNameIsContainedInProject(String activityName) {
        assertTrue(managementApp.getProjectActivityRepo(projectHelper.getProject()).stream().anyMatch(activity -> activity.getActivityName().equals(activityName)));
    }

    @When("the activity named {string} is deleted from the project")
    public void theActivityIsDeletedFromProject(String activityName) {
        managementApp.deleteProjectActivity(projectHelper.getProject(), managementApp.getProjectActivity(projectHelper.getProject(), activityName));
    }

    @When("the employee assigns the other employee with username {string} to the selected activity")
    public void theEmployeeAssignsTheOtherEmployeeWithInitialsToTheActivity(String username) {
        /*try {
            managementApp.assignEmployeeToActivity(managementApp.getEmployee(username));
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }*/
    }

    @Then("the employee with username {string} is assigned to the activity named {string} and vice versa")
    public void theOtherEmployeeWithUsernameIsAssignedToTheActivity(String username, String activityName) {
        assertTrue(managementApp.getEmployee(username)
                .getAssignedActivities()
                .stream()
                .anyMatch(activity -> activity.getActivityName().equals(activityName)));
        assertTrue(managementApp.getAssignedEmployees(managementApp.getProjectActivity(projectHelper.getProject(), activityName))
                .stream()
                .anyMatch(employee -> employee.getUsername().equals(username)));
    }

    @When("the user registers {float} hours spent on the activity named {string} in the project")
    public void theEmployeeRegistersHoursSpentOnTheActivity(float hours, String activityName) {
        try {
            managementApp.registerWorkHoursOnActivity(projectHelper.getActivity(activityName), hours);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the users registered hours on the activity is {float} hours")
    public void theEmployeesWorkHoursOnTheActivityIsHours(float hours) {
        //assertEquals(hours, managementApp.getWorkedHoursOnActivity(), 0f);
    }

    @Then("the expected hours on the activity named {string} is {int} hours")
    public void theExpectedWorkHoursOnTheActivityIs(String activityName, int hours) throws OperationNotAllowedException {
        assertEquals(hours, managementApp.getExpectedWorkHoursOnActivity(projectHelper.getActivity(activityName)), 0.0);
    }

    @When("the user queries their registered work hours on the activity named {string} in the project")
    public void theEmployeeQueriesTheirRegisteredWorkHoursOnAnActivity(String activityName) {
        employeeTotalHours = managementApp.getWorkedHoursOnActivity(projectHelper.getProject().getActivity(activityName));
    }

    @Then("the result of the query is {float} work hours")
    public void theResultOfTheQueryIsWorkHours(float hours) {
        assertEquals(hours, employeeTotalHours, 0f);
    }

    @Then("the project does not have an activity named {string} and the employee with username {string} is not assigned to the activity")
    public void theProjectDoesNotHaveAnActivityNamedAndTheEmployeeWithUsernameIsNotAssignedToTheActivity(String activityName, String username) {
        /*assertFalse(managementApp.getProjectActivityRepo()
                .stream()
                .anyMatch(activity -> activity.getActivityName().equals(activityName)));
        assertFalse(managementApp.getEmployee(username)
                .getAssignedActivities()
                .stream()
                .anyMatch(activity -> activity.getActivityName().equals(activityName)));*/
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

    @When("the user queries for the remaining hours on the activity named {string} in the project")
    public void theUserQueriesForTheRemainingHoursOnTheActivity(String activityName) {
        try {
            remainingWorkHours = managementApp.getRemainingHoursOnActivity(projectHelper.getActivity(activityName));
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the remaining work hours on the activity is {int} hours")
    public void theRemainingWorkRemainingHoursOnTheActivityIsHours(int hours) {
        assertEquals(hours, remainingWorkHours, 0f);
    }

    @And("the user sets the expected hours on the activity named {string} to {int} hours")
    public void theUserSetsTheExpectedHoursOnTheActivityTo(String activityName, int hours) {
        try {
            managementApp.setExpectedWorkHoursOnActivity(projectHelper.getActivity(activityName), hours);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @When("the user queries for the spend hours on the activity named {string} in the project")
    public void theUserQueriesForTheSpendHoursOnTheActivity(String activityName) {
        try {
            spendWorkHours = managementApp.getSpendHoursOnActivity(projectHelper.getActivity(activityName));
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the spend hours on the activity is {int} hours")
    public void theSpendWorkHoursOnTheActivityIsHours(int hours) {
        assertEquals(hours, spendWorkHours, 0f);
    }

    @When("the user sets the start and end time of the activity named {string} in the project to {int} and {int}, respectively")
    public void theUserSetsTheStartAndEndTimeOfTheActivityToAndRespectively(String activityName, int startWeek, int endWeek) {
        try {
            managementApp.setActivityStartWeek(projectHelper.getActivity(activityName), startWeek);
            managementApp.setActivityEndWeek(projectHelper.getActivity(activityName), endWeek);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @When("the employee with username {string} is assigned to the activity named {string} in the project")
    public void theEmployeeAssignsTheOtherEmployeeWithUsernameToTheActivityNamedInTheProject(String username, String activityName) {
        try {
            managementApp.assignEmployeeToActivity(projectHelper.getActivity(activityName), managementApp.getEmployee(username));
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @When("the user queries for the available employees for the activity named {string} in the project")
    public void theProjectLeaderQueriesForTheAvailableEmployeesForTheSelectedActivity(String activityName) throws OperationNotAllowedException {
        try {
            availableEmployeesForActivity = managementApp.listAvailableEmployeesForActivity(projectHelper.getActivity(activityName));
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the available employee for the selected activity is the employee with the username {string}")
    public void availableEmployeesForActivityNameIs (String username) {
        assertTrue(availableEmployeesForActivity.stream().anyMatch(employee -> employee.getUsername().equals(username)));
    }

    @Then("there is no available employees for the selected activity")
    public void thereIsNoAvailableEmployeesForActivityName() {
        assertEquals(0, availableEmployeesForActivity.size());
    }
}
