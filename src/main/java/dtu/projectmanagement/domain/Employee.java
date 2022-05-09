package dtu.projectmanagement.domain;

import dtu.projectmanagement.app.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private String username;
    private final List<Activity> assignedActivities = new ArrayList<>();

    public Employee(String username) {
        this.username = username;
    }



    public String getUsername() {
        return username;
    }
    public Boolean availableInPeriod(int startWeek, int endWeek){
        for (Activity activity : assignedActivities) {
            if (startWeek <= activity.getEndWeek() && endWeek >= activity.getStartWeek()){
                return false;
            }
        }

        return true;
    }


    // Activity
    public void addNewActivity(String activityName) throws OperationNotAllowedException {
        if (assignedActivities.stream().anyMatch(activity -> activity.getActivityName().equals(activityName)))
            throw new OperationNotAllowedException("An activity with that name already exists.");

        Activity activity = new Activity(activityName, this);
        assignedActivities.add(activity);
        activity.assignEmployeeForUserActivity(this);
    }
    public void unassignActivity(Activity activity) {
        assignedActivities.remove(activity);
    }
    public void assignActivity(Activity activity) throws OperationNotAllowedException {
        if (assignedActivities.contains(activity)){
            throw new OperationNotAllowedException("Employee is already assigned to the activity");
        }
        assignedActivities.add(activity);
    }
    public Activity getActivity(String activityName) {
        return assignedActivities.stream()
                .filter(activity -> activity.getActivityName().equals(activityName))
                .findAny()
                .orElse(null);
    }
    public List<Activity> getAssignedActivities() {
        return assignedActivities;
    }
}
