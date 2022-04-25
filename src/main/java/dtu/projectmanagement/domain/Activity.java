package dtu.projectmanagement.domain;
import dtu.projectmanagement.app.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Activity {

    private int activityNum;
    private String activityName;
    private int startWeek;
    private int endWeek;
    private float expectedWorkHours;
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

    public int getEndWeek() {
        return endWeek;
    }

    public float getExpectedWorkHours() {
        return expectedWorkHours;
    }
    public void setExpectedWorkHours(int expectedWorkHours) {
        this.expectedWorkHours = expectedWorkHours;
    }

    public HashMap<Employee, Float> getEmployeeWorkHoursMap() {
        return employeeWorkHoursMap;
    }

    public void setStartAndEndWeek(int startWeek, int endWeek) throws OperationNotAllowedException {
        if (endWeek < startWeek)
            throw new OperationNotAllowedException("The start week cannot be after the end week");

        this.startWeek = startWeek;
        this.endWeek = endWeek;
    }

    public boolean equals( Object other) {
        Activity act = (Activity) other;
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

    public void modifyWorkHours(Employee employee, float hours) throws OperationNotAllowedException {
        if (hours < 0f){
            throw new OperationNotAllowedException("Time must be positive or 0");
        }
        if (hours % 0.5f != 0f){
            throw new OperationNotAllowedException("Time must be given in half hours");
        }
        if (!this.employeeWorkHoursMap.containsKey(employee)){
            this.employeeWorkHoursMap.put(employee, 0f);
        }
        this.employeeWorkHoursMap.put(employee, hours);
    }

    public float getWorkHours(Employee employee){
        if (employeeWorkHoursMap.get(employee) != null) {
            return employeeWorkHoursMap.get(employee);
        }
        else {
            return 0;
        }
    }

    public float getSpendHours() {
        return employeeWorkHoursMap.values().stream().reduce(0f , Float::sum);
    }
}
