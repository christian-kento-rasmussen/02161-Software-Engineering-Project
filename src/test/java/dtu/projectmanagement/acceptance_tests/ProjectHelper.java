package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Project;

import java.util.Calendar;

public class ProjectHelper {

    private String projectNum;
    private int projectCnt = 1;
    public final ManagementApp managementApp;

    public ProjectHelper(ManagementApp managementApp) {
        this.managementApp = managementApp;
    }



    public void addProject() {
        managementApp.createNewProject();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        projectNum = String.format("%02d%04d", year % 100, projectCnt++);
    }

    public Project getProject() {
        return managementApp.getProject(projectNum);
    }

    public Activity getActivity(String activityName) {
        return getProject().getActivity(activityName);
    }
}
