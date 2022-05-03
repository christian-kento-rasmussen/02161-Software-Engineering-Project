package dtu.projectmanagement.domain;

import dtu.projectmanagement.app.OperationNotAllowedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Employee {

    private String username;
    private ObservableList<Activity> activities = FXCollections.observableArrayList();

    public Employee(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void assignActivity(Activity activity) throws OperationNotAllowedException {
        if (activities.contains(activity)){
            throw new OperationNotAllowedException("Employee is already assigned to the activity");
        }
        activities.add(activity);
    }

    public void unassignActivity(Activity activity) {
        activities.remove(activity);
    }

    public void addNewActivity(String activityName) {
        activities.add(new Activity(-1, activityName));
    }

    public Boolean availableInPeriod(int startWeek, int endWeek){
        for (Activity activity : activities) {
            if (startWeek <= activity.getEndWeek() && endWeek >= activity.getStartWeek()){
                return false;
            }
        }

        return true;
    }

    public ObservableList<Activity> getActivities() {
        return activities;
    }
}
