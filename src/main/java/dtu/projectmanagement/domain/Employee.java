package dtu.projectmanagement.domain;

import dtu.projectmanagement.app.OperationNotAllowedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Employee {

    private String username;
    private ObservableList<Activity> assignedActivities = FXCollections.observableArrayList();

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
    public ObservableList<Activity> getAssignedActivities() {
        return assignedActivities;
    }
}
