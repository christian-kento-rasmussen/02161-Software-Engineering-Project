package dtu.projectmanagement.domain;

import dtu.projectmanagement.app.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;


public class Project {

    private String projectNum;
    private String projectName;


    private int startWeek;
    private int endWeek;
    private int activityCnt = 0;

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

    public void removeActivity(String activityName) throws OperationNotAllowedException {
            if (this.getActivity(activityName).getAssignedEmployees().size()>0) {
            throw new OperationNotAllowedException("employee(s) attached to activity. Please remove activity from employee");
        }

            activities.remove(new Activity(getActivity(activityName).getActivityNum(), activityName));
            activityCnt--;
    }

    public void setActivityStartAndEndWeek(Activity activity, int startWeek, int endWeek) throws OperationNotAllowedException {
        activity.setStartAndEndWeek(startWeek, endWeek);
    }

    public Activity getActivity(String activityName){
        return activities.stream()
                .filter(activity -> activity.getActivityName().equals(activityName))
                .findAny()
                .orElse(null);
    }

    public String getProjectNum() {
        return projectNum;
    }

    public Employee getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(Employee employee) {
        projectLeader = employee;
    }

    public List getActivityList() {
        return this.activities;
    }

    public float getSpendHoursOnActivity(Activity activity) {
        return activity.getSpendHours();
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) throws OperationNotAllowedException {
        if (endWeek != 0 && endWeek < startWeek)
            throw new OperationNotAllowedException("The start week cannot be after the end week");
        else
            this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) throws OperationNotAllowedException {
        if (startWeek != 0 && startWeek > endWeek)
            throw new OperationNotAllowedException("The start week cannot be after the end week");
        else
            this.endWeek = endWeek;
    }

    public float getRemainingHoursOnActivity(Activity activity) {
        return activity.getSpendHours();
    }

    public float getSpendHours() {
        return activities.stream().reduce(0f, (acc, val) -> acc + val.getSpendHours(), Float::sum);
    }

    public float getExpectedHours() {
        return activities.stream().reduce(0f, (acc, val) -> acc + val.getExpectedWorkHours(), Float::sum);
    }

    public void generateReport() {
        float spendHours = getSpendHours();
        float expectedHours = getExpectedHours();
        //float remainingHours = getRemainingHours();
    }

    public float getExpectedRemainingWorkHours() {
        return getExpectedHours() - getSpendHours();
    }
}
