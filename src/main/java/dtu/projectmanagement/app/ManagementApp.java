package dtu.projectmanagement.app;

import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class ManagementApp {

    private Project selectedProject;
    private Activity selectedActivity;
    private Employee user;

    private ObservableList<Project> projectRepo = FXCollections.observableArrayList();
    private ObservableList<Employee> employeeRepo = FXCollections.observableArrayList();

    private Map<Integer, Integer> serialNum = new HashMap<>();



    public void checkIsProjectLeader(Project project) throws OperationNotAllowedException {
        if (!user.equals(project.getProjectLeader()))
            throw new OperationNotAllowedException("Only the project leader is allow to perform that action");
    }


    // Project

    /**
     * This function creates a new project, with correct project number
     */
    public void createNewProject() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int yearCounter = serialNum.getOrDefault(year,0);

        serialNum.put(year,yearCounter+1);

        String projectNum = String.format("%02d%04d", year % 100, yearCounter+1);
        Project project = new Project(projectNum);

        projectRepo.add(project);
    }

    public void deleteProject(Project project) {
        project.getActivityRepo().forEach(Activity::unassignAllEmployees);
        projectRepo.remove(project);
    }

    public Project getProject(String project_number) {

        for (Project project : projectRepo) {
            if (project.getProjectNum().equals(project_number)) {
                return project;}
            }
        return null;
    }

// return projectRepo.stream()
// .filter(project -> project.getProjectNum().equals(project_number))
// .findAny()
// .orElse(null);

    public void assignProjectLeader(Project project, Employee employee) {
        project.setProjectLeader(employee);
    }

    public float getSpendHoursOnProject(Project project) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        return project.getSpendHours();
    }

    public String getProjectLeaderUsername(Project project) {
        return project.getProjectLeader().getUsername();
    }

    public String getProjectNum(Project project) {
        return project.getProjectNum();
    }

    public String getProjectName(Project project) {
        return project.getProjectName();
    }

    public Employee getProjectLeader(Project project) {
        return project.getProjectLeader();
    }

    public ObservableList<Project> getProjectRepo() {
        return projectRepo;
    }

    public void setProjectName(Project project, String projectName) {
        project.setProjectName(projectName);
    }

    public void generateReport(Project project) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        project.generateReport();
    }

    public void setProjectStartWeek(Project project, int startWeek) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        project.setStartWeek(startWeek);
    }

    public void setProjectEndWeek(Project project, int endWeek) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        project.setEndWeek(endWeek);
    }

    public float getRemainingHoursOnProject(Project project) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        return project.getRemainingWorkHours();
    }

    public float getExpectedHoursOnProject(Project project) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        return project.getExpectedHours();
    }


    // Activity

    public void addNewProjectActivity(Project project, String activityName) {
        project.addNewActivity(activityName);
    }

    public void deleteProjectActivity(Project project, Activity activity) {
        project.deleteActivity(activity);
        /*try {
        project.removeActivity(activity);
        }
        catch (OperationNotAllowedException e) {
            throw new OperationNotAllowedException(e.getMessage());
        }*/

    }

    public void assignEmployeeToActivity(Project project, Activity activity, Employee employee) throws OperationNotAllowedException {
        project.assignEmployeeToActivity(activity, employee);
    }

    public void setActivityStartAndEndWeek(Project project, Activity activity, int startWeek, int endWeek) throws OperationNotAllowedException {
        checkIsProjectLeader(project);

        project.setActivityStartAndEndWeek(activity, startWeek, endWeek);
    }

    public float getSpendHoursOnActivity(Project project, Activity activity) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        return project.getSpendHoursOnActivity(activity);
    }

    public float getRemainingHoursOnActivity(Project project, Activity activity) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        return project.getRemainingHoursOnActivity(activity);
    }

    public String getActivityName(Project project, Activity activity) {
        return project.getActivityName(activity);
    }

    public void setProjectActivityName(Project project, Activity activity, String activityName) {
        project.setActivityName(activity, activityName);
    }

    public ObservableList<Activity> getProjectActivityRepo(Project project) {
        return project.getActivityRepo();
    }

    public void setExpectedWorkHoursOnActivity(Project project, Activity activity, float hours) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        project.getActivity(activity.getActivityName()).setExpectedWorkHours(hours);
    }

    // TODO: what is this? see getRemaining func
    public float seeRemainingWorkHoursOnActivity(Project project, Activity activity) throws OperationNotAllowedException {

        float expectedHours = activity.getExpectedWorkHours();
        float spendHours = getRemainingHoursOnActivity(project, activity);
        float remainingHours = expectedHours - spendHours;
        return remainingHours;


    }

    public ObservableList<Employee> getAssignedEmployees(Project project, Activity activity) {
        return project.getAssignedEmployees(activity);
    }

    public String getActivityNum(Project project, Activity activity) {
        return project.getActivityNum(activity);
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

    public Employee getEmployee(String username) {
        return employeeRepo.stream()
                .filter(emp -> emp.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    public ObservableList<Employee> getEmployeeRepo() {
        return employeeRepo;
    }

    public String getUserUsername() {
        return user.getUsername();
    }

    public void addNewUserActivity(String activityName) {
        user.addNewActivity(activityName);
    }

    public ObservableList<Activity> getUserActivities() {
        return user.getActivities();
    }

    public void registerWorkHoursOnProjectActivity(Project project, Activity activity, float hours) throws OperationNotAllowedException {
        project.registerWorkHoursOnActivity(user, activity, hours);
    }

    public float getWorkHoursOnActivity(Project project, Activity activity) {
        return project.getWorkHoursOnActivity(user, activity);
    }

    public float getActivityExpectedHours(Project project, Activity activity) {
        return project.getExpectedHoursOnActivity(activity);
    }

    public int getProjectStartWeek(Project project) {
        return project.getStartWeek();
    }

    public int getProjectEndWeek(Project project) {
        return project.getEndWeek();
    }

    public int getActivityStartWeek(Project project, Activity activity) {
        return project.getActivityStartWeek(activity);
    }

    public int getActivityEndWeek(Project project, Activity activity) {
        return project.getActivityEndWeek(activity);
    }
}


