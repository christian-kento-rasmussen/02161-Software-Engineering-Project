package dtu.projectmanagement.domain;

import dtu.projectmanagement.app.OperationNotAllowedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;

public class Activity {

    private static final int PROJECT_ACTIVITY = 0;
    private static final int EMPLOYEE_ACTIVITY = 1;

    private int activityNum;
    private String activityName;
    private Project parentProject;

    private int startWeek;
    private int endWeek;
    private float expectedWorkHours;
    private ObservableList<Employee> assignedEmployees = FXCollections.observableArrayList();
    private HashMap<Employee, Float> employeeWorkHoursMap = new HashMap<>();

    public Activity(int activityNum, String activityName) {
        this.activityNum = activityNum;
        this.activityName = activityName;
    }

    public Activity(int activityNum, String activityName, Project parentProject) {
        this.activityNum = activityNum;
        this.activityName = activityName;
        this.parentProject = parentProject;
    }



    public void assignEmployee(Employee employee) throws OperationNotAllowedException {
        assignedEmployees.add(employee);
        employee.assignActivity(this);
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
    public void setExpectedWorkHours(float expectedWorkHours) throws OperationNotAllowedException {
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

    // TODO: What is this?
    /*public boolean equals( Object other) {
        Activity act = (Activity) other;
        return ((this.activityNum == act.activityNum) && (this.activityName.equals(act.activityName)) );
    }*/

    public ObservableList<Employee> getAssignedEmployees() {
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

        employeeWorkHoursMap.put(employee, hours);

        // TODO: see gui functionality for correction
        /*float currentHours = this.employeeWorkHoursMap.get(employee);
        currentHours = currentHours + hours;
        this.employeeWorkHoursMap.put(employee, currentHours);*/
    }

    // TODO: don't need this; the registerWorkHours func does this
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

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public float getSpendHours() {
        return employeeWorkHoursMap.values().stream().reduce(0f , Float::sum);
    }

    public float getRemainingHours() {
        return getExpectedWorkHours() - getSpendHours();
    }

    public void unassignAllEmployees() {
        assignedEmployees.forEach(employee -> employee.unassignActivity(this));
    }
}
