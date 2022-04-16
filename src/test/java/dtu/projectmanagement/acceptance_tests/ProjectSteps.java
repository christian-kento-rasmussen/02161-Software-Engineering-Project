package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import io.cucumber.java.en.*;

import java.util.Calendar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProjectSteps {

    private ManagementApp managementApp;

    public ProjectSteps(ManagementApp managementApp){
        this.managementApp = managementApp;
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

}
