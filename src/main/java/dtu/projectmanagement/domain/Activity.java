package dtu.projectmanagement.domain;

import dtu.projectmanagement.app.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity {

    private String activityName;
    private Project parentProject;
    private Employee parentEmployee;

    public static final int PROJECT_TYPE = 0;
    public static final int EMPLOYEE_TYPE = 1;
    private final int type;

    private float expectedWorkHours;
    private int startWeek;
    private int endWeek;

    private HashMap<Employee, Float> employeeWorkHoursMap = new HashMap<>();
    private List<Employee> assignedEmployees = new ArrayList<>();

    public Activity(String activityName, Project parentProject) {
        this.activityName = activityName;
        this.parentProject = parentProject;
        type = PROJECT_TYPE;
    }
    public Activity(String activityName, Employee employee) {
        this.activityName = activityName;
        this.parentEmployee = employee;
        type = EMPLOYEE_TYPE;
    }



    // Info
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public Project getParentProject() {
        return parentProject;
    }
    public Employee getParentEmployee() {
        return parentEmployee;
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
    public int getActivityType() {
        return type;
    }

    // Work-info
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
    }
    public float getWorkedHours(Employee employee){
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
    public float getExpectedWorkHours() {
        return expectedWorkHours;
    }
    public void setExpectedWorkHours(float expectedWorkHours) {
        this.expectedWorkHours = expectedWorkHours;
    }
    public float getRemainingHours() {
        return getExpectedWorkHours() - getSpendHours();
    }
    public void assignEmployee(Employee employee) throws OperationNotAllowedException {
        assignedEmployees.add(employee);
        employee.assignActivity(this);
    }
    public void assignEmployeeForUserActivity(Employee employee) {
        assignedEmployees.add(employee);
    }
    public void unassignEmployee(Employee employee) {
        assignedEmployees.remove(employee);
    }
    public void unassignAllEmployees() {
        assignedEmployees.forEach(employee -> employee.unassignActivity(this));
        assignedEmployees.clear();
    }
    public List<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }
    public HashMap<Employee, Float> getEmployeeWorkHoursMap() {
        return employeeWorkHoursMap;
    }
}
