package dtu.projectmanagement.domain;
import dtu.projectmanagement.domain.Project;
import dtu.projectmanagement.app.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Activity {

    private int activityNum;
    String activityName;
    int startWeek;
    int endWeek;
    double expectedWorkHours;
    private List<Employee> assignedEmployees = new ArrayList<>();
    private HashMap<Employee, Float> employeeWorkHoursMap = new HashMap<>();

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

    public List<Employee> getAssignedEmployees() {
        return assignedEmployees;
        }

    public void registerWorkHours(Employee employee, float hours) throws OperationNotAllowedException {
        if (hours < 0f){
            throw new OperationNotAllowedException("Time must be positive or 0");
        }
        if (hours % 0.5f != 0f){
            throw new OperationNotAllowedException("Time must be given in half hours");
        }
        if (!this.employeeWorkHoursMap.containsKey(employee)){
            this.employeeWorkHoursMap.put(employee, 0f);
        }
        float currentHours = this.employeeWorkHoursMap.get(employee);
        currentHours = currentHours + hours;
        this.employeeWorkHoursMap.put(employee, currentHours);
    }

    public float getWorkHours(Employee employee){
        return employeeWorkHoursMap.get(employee);
    }

}
