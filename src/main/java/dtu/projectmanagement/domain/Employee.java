package dtu.projectmanagement.domain;

import dtu.projectmanagement.app.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private String username;
    private List<Activity> activities = new ArrayList<>();

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

    public Boolean availableInPeriod(int startWeek, int endWeek){
        for (Activity activity : activities) {
            if (startWeek <= activity.getEndWeek() && endWeek >= activity.getStartWeek()){
                return false;
            }
        }

        return true;
    }

    public List<Activity> getActivities() { return activities; }

}
