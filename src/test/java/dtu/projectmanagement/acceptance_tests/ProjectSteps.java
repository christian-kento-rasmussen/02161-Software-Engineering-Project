package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import io.cucumber.java.en.*;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ProjectSteps {

    private float spendWorkHours;
    private float expectedHours;
    private float remainingWorkHours;
    private String projectNum;
    private String projectLeaderUsername;

    private ManagementApp managementApp;
    private ProjectHelper projectHelper;
    private ErrorMessageHolder errorMessage;


    public ProjectSteps(ManagementApp managementApp, ProjectHelper projectHelper, ErrorMessageHolder errorMessage){
        this.managementApp = managementApp;
        this.projectHelper = projectHelper;
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
        projectHelper.addProject();
    }

    @Given("two projects are created")
    public void twoProjectsAreCreated() {
        projectHelper.addProject();
        projectHelper.addProject();
    }

    @Then("There exist a project with the project number in format yy-{int} where yy is the last two digits of the current year")
    public void existsProjectWithCorrectProjNum(Integer projCount) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String projectNum = String.format("%02d%04d", year % 100, projCount);
        assertNotNull(managementApp.getProject(projectNum));
    }

    @Then("the start time of the project is {int} weeks from now.")
    public void theStartTimeOfTheProjectIsDaysFromNow(int weeks) {
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        assertEquals(projectHelper.getProject().getStartWeek(), week + weeks);
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

    @When("the project is deleted")
    public void theProjectIsDeleted() {
        managementApp.deleteProject(projectHelper.getProject());
    }

    @Then("the project is no longer in the projectRepo")
    public void theProjectIsNoLongerInTheProjectRepo() {
        assertFalse(managementApp.getProjectRepo().stream().anyMatch(project -> project.equals(projectHelper.getProject())));
    }

    @And("the name of the project is {string}")
    public void theNameOfTheProjectIs(String projectName) {
        assertEquals(projectName, managementApp.getProjectName(projectHelper.getProject()));
    }

    @When("the user sets the name of the project to be {string}")
    public void theUserSetsTheNameOfTheProjectToBe(String projectName) {
        managementApp.setProjectName(projectHelper.getProject(), projectName);
    }

    @Then("the expected hours on the project is {int} hours")
    public void theExpectedHoursOnTheProjectIsHours(int hours) {
        assertEquals(hours, expectedHours, 0f);
    }

    @When("the user queries for the expected hours on the project")
    public void theUserQueriesForTheExpectedHoursOnTheProject() {
        try {
            expectedHours = managementApp.getExpectedHoursOnProject(projectHelper.getProject());
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @When("the user queries for the project num of the project")
    public void theUserQueriesForTheProjectNumOfTheProject() {
        projectNum = managementApp.getProjectNum(projectHelper.getProject());
    }

    @Then("the user gets the project num of the project which is yy{int} where yy is the last two digits of the current year")
    public void theUserGetsTheProjectNumOfTheProjectWhichIsYyWhereYyIsTheLastTwoDigitsOfTheCurrentYear(int projCount) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String projNum = String.format("%02d%04d", year % 100, projCount);
        assertEquals(projectNum, projNum);
    }

    @When("the user queries for the username of the project leader")
    public void theUserQueriesForTheUsernameOfTheProjectLeader() {
        try {
            projectLeaderUsername = managementApp.getProjectLeaderUsername(projectHelper.getProject());
        } catch (OperationNotAllowedException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the username of the project leader is {string}")
    public void theUsernameOfTheProjectLeaderIs(String username) {
        assertEquals(projectLeaderUsername, username);
    }

    @And("the project leader is null")
    public void theProjectLeaderIsNull() {
        managementApp.getProjectLeader(projectHelper.getProject());
    }
}
