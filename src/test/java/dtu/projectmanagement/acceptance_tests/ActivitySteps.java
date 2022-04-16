package dtu.projectmanagement.acceptance_tests.steps;

import dtu.projectmanagement.acceptance_tests.helpersAndHolders.ErrorMessageHolder;
import dtu.projectmanagement.app.ManagementApp;

import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;




public class ActivitySteps {
    ManagementApp managementApp;
    Activity activity;
    Project selectedProject;
    private ErrorMessageHolder errorMessage;


    public ActivitySteps(ManagementApp managementApp, ErrorMessageHolder errorMessage){
        this.managementApp = managementApp;
        this.errorMessage = errorMessage;
    }


    @And("there is an employee with username {string}")
    public void thereIsAnEmployeeWithUsername(String username) {
        managementApp.addEmployee(username);
    }

    @And("there is an activity with activityName {string}, start week {int}, and end week {int} contained in the project")
    public void thereIsAnActivityWithActivityNameStartWeekAndEndWeekContainedInTheProject(String activityName, int startWeek, int endWeek) {
        Project project = managementApp.getProject("220001");
        project.addNewActivity(activityName);
        Activity activity = project.getActivity(activityName);
        //activity.setStartWeek(startWeek);
        //activity.setEndWeek(endWeek);
    }

    @And("employee {string} is attached to activity {string}")
    public void employeeIsAttachedToActivity(String employee, String activityName) throws OperationNotAllowedException {
        Project project = managementApp.getProject("220001");
        Activity activity = project.getActivity(activityName);

        activity.assignEmployee(managementApp.getEmployee(employee));
    }



    @Given("there is an activity with activityName {string}")
    public void there_is_an_activity_with_activity_name(String actname) {
        activity = new Activity(123,actname);
    }


    @Given("activity is not already in the project")
    public void activity_is_not_already_in_the_project() {
        selectedProject=managementApp.getProject("220001"); //Temporary solution.
        assertNull(selectedProject.getActivity(activity.getActivityName()));

    }


    @When("the activity is added to the project")
    public void theActivityIsAddedToTheProject() {

        selectedProject.addNewActivity(activity.getActivityName());
    }

    @Then("the activity with activityName {string} is contained in project")
    public void theActivityWithActivityNameIsContainedInProject(String actString) {
        assertNotNull(selectedProject.getActivity(actString));

    }

    @Given("there is an activity with activityName {string} contained in the project")
    public void there_is_an_activity_with_activity_name_contained_in_the_project(String string) {
        selectedProject=managementApp.getProject("220001"); //Temporary solution.
        managementApp.addNewActivity(selectedProject,string);
        activity=selectedProject.getActivity(string);
    }

    @When("the activity is deleted from project")
    public void the_activity_is_deleted_from_project() throws OperationNotAllowedException {
        try {
        managementApp.removeActivity(selectedProject,activity.getActivityName());}
        catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
//        selectedProject.removeActivity("save the world");
    }

    @Then("the activity with activityName {string} is not contained in the project")
    public void the_activity_with_activity_name_is_not_contained_in_the_project(String string) {
        selectedProject=managementApp.getProject("220001"); //Temporary solution.L
        assertNull(selectedProject.getActivity("save the world"));
    }

    @When("the employee assigns the other employee with initials {string} to the activity")
    public void theEmployeeAssignsTheOtherEmployeeWithInitialsToTheActivity(String username) throws OperationNotAllowedException {
        try {
            activity.assignEmployee(managementApp.getEmployee(username));
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the other employee with initials {string} is assigned to the activity")
    public void theOtherEmployeeWithInitialsIsAssignedToTheActivity(String username) {
        assertTrue(activity.getAssignedEmployees().contains(managementApp.getEmployee(username)));
        assertTrue(managementApp.getEmployee(username).getActivities().contains(activity));
    }

    @Then("the error message {string} is given")
    public void theErrorMessageIsGiven(String errorMessage) {
        assertEquals(errorMessage, this.errorMessage.getErrorMessage());
    }

    @And("the other employee with initials {string} is already assigned to the activity")
    public void theOtherEmployeeWithInitialsIsAlreadyAssignedToTheActivity(String username) throws OperationNotAllowedException {
        activity.assignEmployee(managementApp.getEmployee(username));
    }

    @And("the employee has spent {float} hours on the activity")
    public void theEmployeeHasSpentHoursOnTheActivity(float hours) throws OperationNotAllowedException {
        activity.registerWorkHours(managementApp.getUser(), hours);
    }

    @When("the employee registers {float} hours spent on the activity")
    public void theEmployeeRegistersHoursSpentOnTheActivity(float hours) throws OperationNotAllowedException {
        try {
            activity.registerWorkHours(managementApp.getUser(), hours);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the employees work hours on the activity is {float} hours")
    public void theEmployeesWorkHoursOnTheActivityIsHours(float hours) {
        assertEquals(hours, activity.getWorkHours(managementApp.getUser()), 0.001f);
    }
}
