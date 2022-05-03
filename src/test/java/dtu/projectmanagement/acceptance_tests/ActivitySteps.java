package dtu.projectmanagement.acceptance_tests;

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


public class ActivitySteps {

    ManagementApp managementApp;
    Activity activity;
    Project selectedProject;

    private float hoursRegistered;
    private float totalHoursSpend;
    private float remainingWorkHours;
    private float totalExpectedWorkHours;
    private float employeeTotalHours;

    private ProjectHelper projectHelper;
    private EmployeeHelper employeeHelper;
    private ErrorMessageHolder errorMessage;

    public ActivitySteps(ManagementApp managementApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper, ErrorMessageHolder errorMessage){
        this.managementApp = managementApp;
        this.projectHelper = projectHelper;
        this.employeeHelper = employeeHelper;
        this.errorMessage = errorMessage;
    }



    @When("the current employee using the system sets the start and end time of the activity to {int} and {int}, respectively")
    public void theCurrentEmployeeUsingTheSystemSetsTheStartAndEndTimeOfTheActivityToAndRespectively(Integer startWeek, Integer endWeek) {
        try {
            managementApp.setActivityStartAndEndWeek(projectHelper.getProject(), projectHelper.getActivity(), startWeek, endWeek);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the start and end time of the activity is {int} and {int}, respectively")
    public void theStartAndEndTimeOfTheActivityIsAndRespectively(Integer startWeek, Integer endWeek) {
        assertEquals(projectHelper.getActivity().getStartWeek(), (int) startWeek);
        assertEquals(projectHelper.getActivity().getEndWeek(), (int) endWeek);
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
        managementApp.addNewProjectActivity(selectedProject,string);
        activity=selectedProject.getActivity(string);
    }

    @When("the activity is deleted from project")
    public void the_activity_is_deleted_from_project() {
        managementApp.deleteProjectActivity(selectedProject, activity);
        //selectedProject.removeActivity("save the world");
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

    @Given("an employee registers {float} hours spend on the activity")
    public void anEmployeeRegistersHoursSpendOnTheActivity(float hours) throws OperationNotAllowedException {
        hoursRegistered += hours;
        employeeHelper.login();
        // Todo : how does this work in the final app?
        try {
            projectHelper.getActivity().registerWorkHours(managementApp.getUser(), hours);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Given("the activity gets {int} hours allocated to it")
    public void theActivityGetsHoursAllocatedToIt(int hours)  throws OperationNotAllowedException {
        projectHelper.setActivityExpectedWorkHours(hours);
        totalExpectedWorkHours = totalExpectedWorkHours + hours;
    }

    @When("the employee queries for the total spend hours on the activity")
    public void theEmployeeQueriesForTheTotalSpendHoursOnTheActivity() throws OperationNotAllowedException {
        try {
            totalHoursSpend = managementApp.getSpendHoursOnActivity(projectHelper.getProject(), projectHelper.getActivity());
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @When("the employee queries for the total spend hours on the project")
    public void theEmployeeQueriesForTheTotalSpendHoursOnTheProject() {
        try {
            totalHoursSpend = managementApp.getSpendHoursOnProject(projectHelper.getProject());
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the total spend hours on the activity matches the registered hours")
    public void theTotalSpendHoursOnTheProjectIsHours() {
        assertEquals(totalHoursSpend, hoursRegistered, 0f);
    }

    @Then("the employees work hours on the activity is {float} hours")
    public void theEmployeesWorkHoursOnTheActivityIsHours(float hours) {
        assertEquals(hours, activity.getWorkHours(managementApp.getUser()), 0.001f);
    }

    @When("the employee queries for the total remaining work hours on the project")
    public void theEmployeeQueriesForTheTotalRemainingWorkHoursOnTheProject() {
        try {
            remainingWorkHours = managementApp.getRemainingHoursOnProject(projectHelper.getProject());
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the total remaining work hours on the project matches the missing registered hours")
    public void theTotalRemainingWorkHoursOnTheProjectMatchesTheMissingRegisteredHours() {
        assertEquals(remainingWorkHours, totalExpectedWorkHours-hoursRegistered, 0f);
    }

    @When("the employee modifies their work hours on the activity to {float} hours")
    public void theEmployeeModifiesTheirWorkHoursOnTheActivityToHours(float hours) throws OperationNotAllowedException {
        try {
            activity.modifyWorkHours(managementApp.getUser(), hours);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }



    @Then("the expected work hours on the activity is {int}")
    public void theExpectedWorkHoursOnTheActivityIs(int hours) {
        assertTrue(projectHelper.getActivity().getExpectedWorkHours()==hours);
    }

    @When("the user sets the expected work hours of the activity to {int} hours")
    public void theUserSetsTheExpectedWorkHoursOfTheActivityToHours(int hours)  throws OperationNotAllowedException {
        try {
            managementApp.setExpectedWorkHoursOnActivity(projectHelper.getProject(), projectHelper.getActivity(), hours);
//            projectHelper.getActivity().setExpectedWorkHours(hours);}
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }




    @And("the the activity has expected work hours set to {int}")
    public void theTheActivityHasExpectedWorkHoursSetTo(int hours)   throws OperationNotAllowedException {
        projectHelper.getActivity().setExpectedWorkHours(hours);
    }

    @When("the employee queries their registered work hours on an activity")
    public void theEmployeeQueriesTheirRegisteredWorkHoursOnAnActivity() {
        employeeTotalHours = activity.getWorkHours(managementApp.getUser());
    }

    @Then("the result of the query is {float} work hours")
    public void theResultOfTheQueryIsWorkHours(float hours) {
        assertEquals(hours, employeeTotalHours, 0f);
    }

    @Then("the remaining work remaining hours on the activity is {int} hours")
    public void theRemainingWorkRemainingHoursOnTheActivityIsHours(int hours) throws OperationNotAllowedException {
        assertEquals(managementApp.seeRemainingWorkHoursOnActivity(projectHelper.getProject(),projectHelper.getActivity()),hours,0f);



    }


    @When("get the remaining workhours on the activity")
    public void theRemainingWorkhoursOnTheActivity() throws OperationNotAllowedException {
        try {
            System.out.print(managementApp.seeRemainingWorkHoursOnActivity(projectHelper.getProject(), projectHelper.getActivity()));
    } catch (OperationNotAllowedException e) {
        errorMessage.setErrorMessage(e.getMessage());
    }
    }
}
