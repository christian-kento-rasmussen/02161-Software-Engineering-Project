package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import io.cucumber.java.en.*;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ProjectSteps {

    private float spendWorkHours;
    private float hoursRegistered;
    private float remainingWorkHours;

    private ManagementApp managementApp;
    private ProjectHelper projectHelper;
    private EmployeeHelper employeeHelper;
    private ErrorMessageHolder errorMessage;


    public ProjectSteps(ManagementApp managementApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper,ErrorMessageHolder errorMessage){
        this.managementApp = managementApp;
        this.projectHelper = projectHelper;
        this.employeeHelper = employeeHelper;
        this.errorMessage = errorMessage;
    }



    @Given("there is a project")
    public void thereIsAProjectAndItIsSelected() {
        projectHelper.addProject();
    }

    @When("the employee assigns the employee with the username {string} to be project leader of the given project")
    public void theEmployeeAssignsTheGivenEmployeeToBeProjectLeaderOfTheGivenProject(String username) throws OperationNotAllowedException {
        managementApp.assignProjectLeader(projectHelper.getProject(), managementApp.getEmployee(username));
    }

    @Given("a project is created")
    public void aProjectIsCreated() {
        managementApp.createNewProject();
    }

    @Given("two projects are created")
    public void twoProjectsAreCreated() {
        managementApp.createNewProject();
        managementApp.createNewProject();
    }

    @Then("There exist a project with the project number in format yy-{int} where yy is the last two digits of the current year")
    public void existsProjectWithCorrectProjNum(Integer projCount) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String projectNum = String.format("%02d%04d", year % 100, projCount);
        assertNotNull(managementApp.getProject(projectNum));
    }

    @When("the given employee sets the start time of the project to {int} weeks from now.")
    public void theGivenEmployeeSetsTheStartTimeOfTheProjectToDaysFromNow(int weeks) {
        // managementApp.assignProjectLeader(projectHelper.getProject(), employeeHelper.getEmployee());
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

        try {
            projectHelper.getProject().setStartWeek(week + weeks);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }

    }

    @Then("the start time of the project is {int} weeks from now.")
    public void theStartTimeOfTheProjectIsDaysFromNow(int weeks) {
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        assertEquals(projectHelper.getProject().getStartWeek(), week + weeks);
    }

    @When("the given employee sets the end time of the project to {int} weeks from now.")
    public void theGivenEmployeeSetsTheEndTimeOfTheProjectToDaysFromNow(int weeks) {
        // managementApp.assignProjectLeader(projectHelper.getProject(), employeeHelper.getEmployee());
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

        try {
            projectHelper.getProject().setEndWeek(week + weeks);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }

    }

    @Then("the end time of the project is {int} weeks from now.")
    public void theEndTimeOfTheProjectIsDaysFromNow(int weeks) {
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        assertEquals(projectHelper.getProject().getEndWeek(), week + weeks);
    }


    @And("the employee with the username {string} is assigned as the project leader of the given project")
    public void theEmployeeWithTheUsernameIsTheProjectLeaderOfTheSelectedProject(String username) {
        managementApp.getProjectLeader(projectHelper.getProject());
    }

    @When("the user queries for the remaining hours on the project")
    public void theUserQueriesForTheRemainingHoursOnTheProject() {
        try {
            remainingWorkHours = managementApp.getRemainingHoursOnProject(projectHelper.getProject());
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the remaining work hours on the project is {int} hours")
    public void theRemainingWorkRemainingHoursOnTheProjectIsHours(int hours) {
        assertEquals(hours, remainingWorkHours, 0f);
    }

    @When("the user queries for the spend hours on the project")
    public void theUserQueriesForTheSpendHoursOnTheProject() {
        try {
            spendWorkHours = managementApp.getSpendHoursOnProject(projectHelper.getProject());
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the spend on the project is {int} hours")
    public void theSpendOnTheProjectIsHours(int hours) {
        assertEquals(hours, spendWorkHours, 0f);
    }

    @When("the user sets the start and end time of the project to {int} and {int}, respectively")
    public void theUserSetsTheStartAndEndTimeOfTheProjectToAndRespectively(int startWeek, int endWeek) {
        try {
            managementApp.setProjectStartWeek(projectHelper.getProject(), startWeek);
            managementApp.setProjectEndWeek(projectHelper.getProject(), endWeek);
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the start and end time of the project is {int} and {int}, respectively")
    public void theStartAndEndTimeOfTheProjectIsAndRespectively(int startWeek, int endWeek) {
        assertEquals(managementApp.getProjectStartWeek(projectHelper.getProject()), startWeek);
        assertEquals(managementApp.getProjectEndWeek(projectHelper.getProject()), endWeek);
    }

    @And("the employee with the username {string} is the project leader of the given project")
    public void theEmployeeWithTheUsernameIsTheProjectLeaderOfTheGivenProject(String username) {
        managementApp.assignProjectLeader(projectHelper.getProject(), managementApp.getEmployee(username));
    }
}
