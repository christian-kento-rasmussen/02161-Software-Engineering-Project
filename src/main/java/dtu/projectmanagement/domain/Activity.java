package dtu.projectmanagement.domain;
import dtu.projectmanagement.domain.Project;
import dtu.projectmanagement.app.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;

public class Activity {

    private int activityNum;
    String activityName;
    int startWeek;
    int endWeek;
    double expectedWorkHours;
    private List<Employee> assignedEmployees = new ArrayList<>();

    public Activity(int activityNum, String activityName) {
        this.activityName = activityName;
        this.activityNum = activityNum;

    }

    public void assignEmployee(Employee employee) throws OperationNotAllowedException {
        try{
            assignedEmployees.add(employee);
            employee.assignActivity(this);
        } catch (OperationNotAllowedException e) {
            throw new OperationNotAllowedException(e.getMessage());
        }

    }

    public String getActivityName() {
        return activityName;
    }

    public int getActivityNum() {
        return activityNum;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public boolean equals( Object other) {
        Activity act= (Activity) other;
        return ((this.activityNum == act.activityNum) && (this.activityName.equals(act.activityName)) );
    }

    public List<Employee> getAssignedEmployees() {return assignedEmployees;}
}
