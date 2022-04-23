package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Project;

import java.util.Calendar;

public class ProjectHelper {

    private String projectNum;
    private String lastAct;
    private String actNameBase = "testActivity";
    private int actCnt;
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
        if (projectNum == null)
            addProject();
        return managementApp.getProject(projectNum);
    }

    public void addActivity() {
        lastAct = actNameBase + actCnt++;
        managementApp.addNewActivity(getProject(), lastAct);
    }

    public void setActivityExpectedWorkHours(int hours){
        getActivity().setExpectedWorkHours(hours);
    }

    public void setUserToProjectLeader() {
        managementApp.assignProjectLeader(getProject(), managementApp.getUser());
    }

    public Activity getActivity() {
        return getProject().getActivity(lastAct);
    }
}
