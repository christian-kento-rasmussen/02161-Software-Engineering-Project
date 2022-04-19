package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import io.cucumber.java.en.*;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ProjectSteps {

    private ManagementApp managementApp;
    private ProjectHelper projectHelper;
    private EmployeeHelper employeeHelper;
    private ErrorMessageHolder errorMessage;

    public ProjectSteps(ManagementApp managementApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper,ErrorMessageHolder errorMessage){
        this.managementApp = managementApp;
        this.projectHelper = projectHelper;
        this.errorMessage = errorMessage;
        this.employeeHelper = employeeHelper;
    }



    @Given("there is a project")
    public void thereIsAProject() {
        projectHelper.addProject();
    }

    @Given("there is a given employee in the system")
    public void thereIsAGivenEmployeeInTheSystem() {
        employeeHelper.addEmployee();
    }

    @When("the employee assigns the given employee to be project leader of the given project")
    public void theEmployeeAssignsTheGivenEmployeeToBeProjectLeaderOfTheGivenProject() {
        managementApp.assignProjectLeader(projectHelper.getProject(), employeeHelper.getEmployee());
    }

    @Then("the given employee is the project leader of the given project")
    public void theGivenEmployeeIsTheProjectLeaderOfTheGivenProject() {
        assertEquals(projectHelper.getProject().getProjectLeader(), employeeHelper.getEmployee());
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

    @Given("the project has an activity in it")
    public void theProjectHasAnActivityInIt() {
        projectHelper.addActivity();
    }

    @Given("the employee using the system is the project leader of the project")
    public void theCurrentEmployeeUsingTheSystemIsTheProjectLeaderOfTheProject() {
        projectHelper.setUserToProjectLeader();
        assertEquals(projectHelper.getProject().getProjectLeader(), managementApp.getUser());
    }

    @Given("the employee using the system is not the project leader of the project")
    public void theCurrentEmployeeUsingTheSystemIsNotTheProjectLeaderOfTheProject() {
        assertNull(projectHelper.getProject().getProjectLeader());
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

}
