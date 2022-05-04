package dtu.projectmanagement.domain;

import dtu.projectmanagement.app.OperationNotAllowedException;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Project {

    private String projectNum;
    private String projectName;

    private Employee projectLeader;

    private int startWeek;
    private int endWeek;

    private ObservableList<Activity> activities = FXCollections.observableArrayList();

    public Project(String projectNum) {
        this.projectNum = projectNum;
        this.projectName="Unnamed project";
    }



    // Info
    public String getProjectNum() {
        return projectNum;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public void setProjectLeader(Employee employee) {
        projectLeader = employee;
    }
    public Employee getProjectLeader() {
        return projectLeader;
    }
    public String getProjectLeaderUsername() {
        return projectLeader.getUsername();
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


    // Activity - creation, deletion, repo
    public void addNewActivity(String activityName) {
        activities.add(new Activity(activityName, this));
    }
    public void deleteActivity(Activity activity) {
        activity.unassignAllEmployees();
        activities.remove(activity);
    }
    public Activity getActivity(String activityName) {
        return activities.stream()
                .filter(activity -> activity.getActivityName().equals(activityName))
                .findAny()
                .orElse(null);
    }
    public ObservableList<Activity> getActivityRepo() {
        return activities;
    }


    // Work-info
    public float getSpendHours() {
        return activities.stream().reduce(0f, (acc, val) -> acc + val.getSpendHours(), Float::sum);
    }
    public float getExpectedHours() {
        return activities.stream().reduce(0f, (acc, val) -> acc + val.getExpectedWorkHours(), Float::sum);
    }
    public float getRemainingHours() {
        return getExpectedHours() - getSpendHours();
    }
    public void generateReport() {
    }


}
