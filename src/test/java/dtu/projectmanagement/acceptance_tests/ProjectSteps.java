package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import io.cucumber.java.en.*;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ProjectSteps {

    private ManagementApp managementApp;
    private ProjectHelper projectHelper;
    private EmployeeHelper employeeHelper;

    public ProjectSteps(ManagementApp managementApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper){
        this.managementApp = managementApp;
        this.projectHelper = projectHelper;
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

}
