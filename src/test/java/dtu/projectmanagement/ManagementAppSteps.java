package dtu.projectmanagement;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Project;
import io.cucumber.java.en.Then;

import java.util.List;

public class ManagementAppSteps {
    ManagementApp managementApp;

    public ManagementAppSteps(ManagementApp managementApp){
        this.managementApp = managementApp;
    }

    @Then("available employees for activityName {string} is {string}")
    public void availableEmployeesForActivityNameIs (String activityName, String employee) {
        Project project = managementApp.getProject("220001");
        Activity activity = project.getActivity(activityName);
        List<String> employees = managementApp.ListAvailableEmployeesForActivity("220001",activityName);
    }

    @Then("there is no available employees for activityName {string}")
    public void thereIsNoAvailableEmployeesForActivityName(String activityName) {
        Project project = managementApp.getProject("220001");
        Activity activity = project.getActivity(activityName);
        List<String> employees = managementApp.ListAvailableEmployeesForActivity("220001",activityName);
    }
}
