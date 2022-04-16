package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Project;

import java.util.Calendar;

public class ProjectHelper {

    private String projectNum;
    private ManagementApp managementApp;

    public ProjectHelper(ManagementApp managementApp) {
        this.managementApp = managementApp;
    }



    public void addProject() {
        managementApp.createNewProject();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        projectNum = String.format("%02d%04d", year % 100, 1);
    }

    public Project getProject() {
        return managementApp.getProject(projectNum);
    }
}
