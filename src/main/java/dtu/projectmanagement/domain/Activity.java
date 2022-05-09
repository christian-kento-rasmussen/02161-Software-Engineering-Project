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

    private final HashMap<Employee, Float> employeeWorkHoursMap = new HashMap<>();
    private final List<Employee> assignedEmployees = new ArrayList<>();

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
    /**
     * @author William Steffens (s185369)
     */
    public String getActivityName() {
        return activityName;
    }
    /**
     * @author William Steffens (s185369)
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    /**
     * @author William Steffens (s185369)
     */
    public Project getParentProject() {
        return parentProject;
    }
    /**
     * @author William Steffens (s185369)
     */
    public Employee getParentEmployee() {
        return parentEmployee;
    }
    /**
     * @author William Steffens (s185369)
     */
    public int getStartWeek() {
        return startWeek;
    }
    /**
     * @author William Steffens (s185369)
     */
    public void setStartWeek(int startWeek) throws OperationNotAllowedException {
        if (endWeek != 0 && endWeek <= startWeek)
            throw new OperationNotAllowedException("The start week cannot be the same as or after the end week");
        else
            this.startWeek = startWeek;
    }
    /**
     * @author William Steffens (s185369)
     */
    public int getEndWeek() {
        return endWeek;
    }
    /**
     * @author William Steffens (s185369)
     */
    public void setEndWeek(int endWeek) throws OperationNotAllowedException {
        if (startWeek != 0 && startWeek >= endWeek)
            throw new OperationNotAllowedException("The start week cannot be the same as or after the end week");
        else
            this.endWeek = endWeek;
    }
    public void setStartEndWeek(int startWeek, int endWeek) throws OperationNotAllowedException {

        if (startWeek == 0) {
            throw new OperationNotAllowedException("Start week can not be 0");
        }

        if (endWeek == 0) {
            throw new OperationNotAllowedException("End week can not be 0");
        }

        if (startWeek >= endWeek) {
            throw new OperationNotAllowedException("The end week needs to be after the start week");
        }

        // Precondition
        assert startWeek != 0
                && endWeek != 0
                && startWeek < endWeek : "Precondition";

        this.endWeek = endWeek;
        this.startWeek = startWeek;

        assert (this.endWeek - this.startWeek) > 0 : "Postcondition";
    }
    public int getActivityType() {
        return type;
    }

    // Work-info
    public void registerWorkHours(Employee employee, float hours) throws OperationNotAllowedException {
        // Pre-condition
        assert employee != null : " Pre - condition violation " ;

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
        // Post-condition
        assert employeeWorkHoursMap.get(employee) == hours;
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
    /**
     * @author Christian Raasteen (s204148)
     */
    public void setExpectedWorkHours(float expectedWorkHours) throws OperationNotAllowedException {
        if (expectedWorkHours>=0) {
            this.expectedWorkHours = (expectedWorkHours);
        } else {
            throw new OperationNotAllowedException("Expected Work hours must be positive");
        }
    }
    /**
     * @author Christian Raasteen (s204148)
     */
    public float getRemainingHours() {
        return getExpectedWorkHours() - getSpendHours();
    }
    /**
     * @author William Steffens (s185369)
     */
    public void assignEmployee(Employee employee) throws OperationNotAllowedException {
        employee.assignActivity(this);
        assignedEmployees.add(employee);
    }
    /**
     * @author William Steffens (s185369)
     */
    public void assignEmployeeForUserActivity(Employee employee) {
        assignedEmployees.add(employee);
    }
    public void unassignEmployee(Employee employee) {
        assignedEmployees.remove(employee);
    }
    /**
     * @author William Steffens (s185369)
     */
    public void unassignAllEmployees() {
        assignedEmployees.forEach(employee -> employee.unassignActivity(this));
        assignedEmployees.clear();
    }
    /**
     * @author William Steffens (s185369)
     */
    public List<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }
    public HashMap<Employee, Float> getEmployeeWorkHoursMap() {
        return employeeWorkHoursMap;
    }
}
