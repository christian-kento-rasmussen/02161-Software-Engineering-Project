package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Project;

import java.util.Calendar;

public class ProjectHelper {

    private String projectNum;
    private String lastAct;
    private int projectCnt = 1;
    private int actCnt;
    private final ManagementApp managementApp;

    public ProjectHelper(ManagementApp managementApp) {
        this.managementApp = managementApp;
    }



    public void addProject() {
        managementApp.createNewProject();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        projectNum = String.format("%02d%04d", year % 100, projectCnt++);

        try {
            managementApp.selectProject(getProject());
        } catch (OperationNotAllowedException e) {
            e.printStackTrace();
        }
    }

    public Project getProject() {
        return managementApp.getProject(projectNum);
    }

    public Project getProject(String projectNum) {
        return managementApp.getProject(projectNum);
    }

    public void addActivity() {
        lastAct = "testActivity" + actCnt++;
        managementApp.addNewProjectActivity(lastAct);
    }

    public void setActivityExpectedWorkHours(int hours) throws OperationNotAllowedException {
        getActivity().setExpectedWorkHours(hours);
    }

    public void setUserToProjectLeader() {
        managementApp.assignProjectLeader(managementApp.getUser());
    }

    public Activity getActivity() {
        return getProject().getActivity(lastAct);
    }

    public Activity getActivity(String activityName) {
        return getProject().getActivity(activityName);
    }
}
