package dtu.projectmanagement;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en_old.Ac;

import static org.junit.Assert.assertNotNull;

public class ActivitySteps {
    ManagementApp managementApp;

    public ActivitySteps(ManagementApp managementApp){
        this.managementApp = managementApp;
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
        activity.setStartWeek(startWeek);
        activity.setEndWeek(endWeek);
    }

    @And("employee {string} is attached to activity {string}")
    public void employeeIsAttachedToActivity(String employee, String activityName) {
        Project project = managementApp.getProject("220001");
        Activity activity = project.getActivity(activityName);
        activity.assignEmployee(employee);
    }

}
