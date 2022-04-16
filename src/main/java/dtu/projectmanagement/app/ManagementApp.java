package dtu.projectmanagement.app;

import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;

import java.util.*;

public class ManagementApp {

    private Employee user;

    private List<Project> projectRepo = new ArrayList<>();
    private List<Employee> employeeRepo = new ArrayList<>();

    private Map<Integer, Integer> serialNum = new HashMap<>();



    // Project

    /**
     * This function creates a new project, with correct project number
     */
    public void createNewProject()  {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int yearCounter = serialNum.getOrDefault(year,0);

        serialNum.put(year,yearCounter+1);

        String projectNum = String.format("%02d%04d", year % 100, yearCounter+1);
        Project project = new Project(projectNum);

        projectRepo.add(project);
    }

    public Project getProject(String project_number){
        return projectRepo.stream()
                .filter(project -> project.getProjectNum().equals(project_number))
                .findAny()
                .orElse(null);
    }

    public Employee getEmployee(String username) {
        return employeeRepo.stream()
                .filter(emp -> emp.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    public void assignProjectLeader(Project project, Employee employee) {
        project.setProjectLeader(employee);
    }


    // Activity

    public void addNewActivity(Project project, String activityName) {
        project.addNewActivity(activityName);
    }

    public void removeActivity(Project project, String activityName) throws OperationNotAllowedException {

        try {
        project.removeActivity(activityName);
        }
        catch (OperationNotAllowedException e) {
            throw new OperationNotAllowedException(e.getMessage());
        }

    }

    public void setActivityStartAndEndWeek(Project project, Activity activity, int startWeek, int endWeek) throws OperationNotAllowedException {
        if (!user.equals(project.getProjectLeader()))
            throw new OperationNotAllowedException("Only the project leader can set the start and end week of an activity");

        project.setActivityStartAndEndWeek(activity, startWeek, endWeek);
    }


    // Employee

    public void login(String username) {
        user = getEmployee(username);
    }

    public void addEmployee(String username) {
        employeeRepo.add(new Employee(username));
    }

    public void removeEmployee(Employee employee) {
        employeeRepo.remove(employee);
    }

    public List<Employee> ListAvailableEmployeesForActivity(String projectNum, String activityName) {
        List<Employee> employeesAvailable = new ArrayList<>();
        Project project = getProject(projectNum);
        Activity activity = project.getActivity(activityName);
        int startWeek = activity.getStartWeek();
        int endWeek = activity.getEndWeek();

        for (Employee employee : employeeRepo) {
            if (employee.availableInPeriod(startWeek,endWeek)){
                employeesAvailable.add(employee);
            }
        }

        return employeesAvailable;
    }

    public Employee getUser() {
        return user;
    }
}


