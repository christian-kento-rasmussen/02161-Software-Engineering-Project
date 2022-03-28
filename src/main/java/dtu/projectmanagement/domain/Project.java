package dtu.projectmanagement.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Project {

    private String projectNum;
    private String projectName;
    private int startWeek;
    private int endWeek;
    private int activityCnt = 1;

    private Employee projectLeader;

    private List<Activity> activities = new ArrayList<>();



    public Project(String projectNum) {
        this.projectNum = projectNum;
        this.projectName="Unnamed project";
    }

    public void addNewActivity(String activityName) {
        activities.add(new Activity(activityCnt, activityName));
        activityCnt++;
    }

    public String getProjectNum() {
        return projectNum;
    }


    public void setProjectLeader(Employee employee) {
        projectLeader = employee;
    }


}
