package dtu.projectmanagement.app;

import dtu.projectmanagement.domain.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class ManagementApp {

    private Project selectedProject;
    private Activity selectedActivity;
    private Employee user;

    private final List<Project> projectRepo = new ArrayList<>();
    private final List<Employee> employeeRepo = new ArrayList<>();

    private final Map<Integer, Integer> serialNum = new HashMap<>();

    PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void addObserver(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    // Selection
    public void selectProject(Project project) {
        selectedProject = project;

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
    }
    public void selectActivity(Activity activity) {
        selectedActivity = activity;

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }



    /*
        PROJECT
    */
    // Project - creation, deletion, repo
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

        support.firePropertyChange(NotificationType.UPDATE_PROJECT_REPO, null, null);
    }
    public void deleteProject(Project project) {
        project.getActivityRepo().forEach(Activity::unassignAllEmployees);
        projectRepo.remove(project);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT_REPO, null, null);
    }
    public Project getProject(String projectNum) {
        for (Project project : projectRepo) {
            if (project.getProjectNum().equals(projectNum))
                return project;
        }
        return null;
    }
    public List<Project> getProjectRepo() {
        return projectRepo;
    }

    // Project - project leader
    public void assignProjectLeader(Employee employee) {
        selectedProject.setProjectLeader(employee);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
    }
    public Employee getProjectLeader() {
        return selectedProject.getProjectLeader();
    }
    public void checkIsProjectLeader() throws OperationNotAllowedException {
        if (!user.equals(selectedProject.getProjectLeader()))
            throw new OperationNotAllowedException("Only the project leader is allow to perform that action");
    }
    public String getProjectLeaderUsername() {
        return selectedProject.getProjectLeaderUsername();
    }

    // Project - info
    public String getProjectNum() {
        return selectedProject.getProjectNum();
    }
    public String getProjectName() {
        return selectedProject.getProjectName();
    }
    public void setProjectName(String projectName) {
        selectedProject.setProjectName(projectName);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
    }
    public void setProjectStartWeek(int startWeek) throws OperationNotAllowedException {
        checkIsProjectLeader();
        selectedProject.setStartWeek(startWeek);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
    }
    public int getProjectStartWeek() {
        return selectedProject.getStartWeek();
    }
    public void setProjectEndWeek(int endWeek) throws OperationNotAllowedException {
        checkIsProjectLeader();
        selectedProject.setEndWeek(endWeek);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
    }
    public int getProjectEndWeek() {
        return selectedProject.getEndWeek();
    }

    // Project - work-info
    public float getSpendHoursOnProject() throws OperationNotAllowedException {
        checkIsProjectLeader();

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);

        return selectedProject.getSpendHours();
    }
    public float getExpectedHoursOnProject() throws OperationNotAllowedException {
        checkIsProjectLeader();

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);

        return selectedProject.getExpectedHours();
    }
    public float getRemainingHoursOnProject() throws OperationNotAllowedException {
        checkIsProjectLeader();

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);

        return selectedProject.getRemainingHours();
    }
    public void generateProjectReport() throws OperationNotAllowedException {
        checkIsProjectLeader();
        selectedProject.generateReport();
    }



    /*
        ACTIVITY
    */
    // Activity - creation, deletion, repo
    public void addNewProjectActivity(String activityName) {
        selectedProject.addNewActivity(activityName);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY_REPO, null, null);
    }
    public void deleteProjectActivity(Activity activity) {
        selectedProject.deleteActivity(activity);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY_REPO, null, null);
    }
    public void addNewUserActivity(String activityName) throws OperationNotAllowedException {
        user.addNewActivity(activityName);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY_REPO, null, null);
    }
    public void deleteUserActivity(Activity activity) {
        user.unassignActivity(activity);
        activity.unassignEmployee(user);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY_REPO, null, null);
    }
    public Activity getProjectActivity(String activityName) {
        return selectedProject.getActivity(activityName);
    }
    public List<Activity> getProjectActivityRepo() {
        return selectedProject.getActivityRepo();
    }
    public Activity getUserActivity(String activityName) {
        return user.getActivity(activityName);
    }
    public List<Activity> getUserActivities() {
        return user.getAssignedActivities();
    }

    // Activity - assigned employees
    public void assignEmployeeToActivity(Employee employee) throws OperationNotAllowedException {
        selectedActivity.assignEmployee(employee);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public void unassignEmployeeFromActivity(Employee employee) {
        if (selectedActivity.getActivityType() == Activity.PROJECT_TYPE) {
            employee.unassignActivity(selectedActivity);
            selectedActivity.unassignEmployee(employee);
        } else if (selectedActivity.getActivityType() == Activity.EMPLOYEE_TYPE)
            deleteUserActivity(selectedActivity);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public List<Employee> getAssignedEmployees() {
        return selectedActivity.getAssignedEmployees();
    }

    // Activity - info
    public String getActivityName() {
        return selectedActivity.getActivityName();
    }
    public void setActivityName(String activityName) {
        selectedActivity.setActivityName(activityName);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public void setActivityStartWeek(int startWeek) throws OperationNotAllowedException {
        if (selectedActivity.getParentEmployee() != user) {
            checkIsProjectLeader();
        }
        selectedActivity.setStartWeek(startWeek);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public int getActivityStartWeek() {
        return selectedActivity.getStartWeek();
    }
    public void setActivityEndWeek(int endWeek) throws OperationNotAllowedException {
        // TODO: or parent to the activity
        if (selectedActivity.getParentEmployee() != user) {
            checkIsProjectLeader();
        }
        selectedActivity.setEndWeek(endWeek);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public int getActivityEndWeek() {
        return selectedActivity.getEndWeek();
    }
    public int getActivityType() {
        return selectedActivity.getActivityType();
    }

    // Activity - work-info
    public float getSpendHoursOnActivity() throws OperationNotAllowedException {
        if (selectedActivity.getParentEmployee() != user) {
            checkIsProjectLeader();
        }
        return selectedActivity.getSpendHours();
    }
    public float getExpectedWorkHoursOnActivity() throws OperationNotAllowedException {
        // TODO: test
        if (selectedActivity.getParentEmployee() != user) {
            checkIsProjectLeader();
        }
        return selectedActivity.getExpectedWorkHours();
    }
    public void setExpectedWorkHoursOnActivity(float hours) throws OperationNotAllowedException {
        if (selectedActivity.getParentEmployee() != user) {
            checkIsProjectLeader();
        }
        selectedActivity.setExpectedWorkHours(hours);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public float getRemainingHoursOnActivity() throws OperationNotAllowedException {
        if (selectedActivity.getParentEmployee() != user) {
            checkIsProjectLeader();
        }
        return selectedActivity.getRemainingHours();
    }
    public List<Employee> listAvailableEmployeesForActivity() throws OperationNotAllowedException {
        checkIsProjectLeader();
        List<Employee> employeesAvailable = new ArrayList<>();

        int startWeek = getActivityStartWeek();
        int endWeek = getActivityEndWeek();

        for (Employee employee : employeeRepo) {
            if (employee.availableInPeriod(startWeek,endWeek)){
                employeesAvailable.add(employee);
            }
        }

        return employeesAvailable;
    }



    /*
        EMPLOYEE
    */
    // Employee - creation, deletion, repo
    public void login(Employee employee) {
        user = employee;
    }
    public Employee getUser() {
        return user;
    }
    public void addEmployee(String username) throws OperationNotAllowedException {
        boolean duplicate = employeeRepo.stream().anyMatch(employee -> employee.getUsername().equals(username));
        if (duplicate)
            throw new OperationNotAllowedException("An employee with that username already exists.");
        employeeRepo.add(new Employee(username));

        support.firePropertyChange(NotificationType.UPDATE_EMPLOYEE_REPO, null, null);
    }
    public void removeEmployee(Employee employee) throws OperationNotAllowedException {
        if (employee.equals(user))
            throw new OperationNotAllowedException("Cannot delete the current user of the application");

        projectRepo.forEach(project -> {
            if (project.getProjectLeader() == employee)
                project.setProjectLeader(null);
            project.getActivityRepo().forEach(activity -> activity.unassignEmployee(employee));
        });
        employeeRepo.remove(employee);

        support.firePropertyChange(NotificationType.UPDATE_EMPLOYEE_REPO, null, null);
    }
    public Employee getEmployee(String username) {
        return employeeRepo.stream()
                .filter(employee -> employee.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }
    public List<Employee> getEmployeeRepo() {
        return employeeRepo;
    }

    // Employee - info
    public String getUserUsername() {
        return user.getUsername();
    }

    // Employee - work-info
    public void registerWorkHoursOnActivity(float hours) throws OperationNotAllowedException {
        selectedActivity.registerWorkHours(user, hours);
    }
    public float getWorkedHoursOnActivity() {
        return selectedActivity.getWorkedHours(user);
    }
}


