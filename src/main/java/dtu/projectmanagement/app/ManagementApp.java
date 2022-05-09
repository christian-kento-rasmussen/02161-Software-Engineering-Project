package dtu.projectmanagement.app;

import dtu.projectmanagement.domain.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class ManagementApp {

    private static ManagementApp instance;

    private Employee user;

    private final List<Project> projectRepo = new ArrayList<>();
    private final List<Employee> employeeRepo = new ArrayList<>();

    private final Map<Integer, Integer> serialNum = new HashMap<>();

    PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void addObserver(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public static ManagementApp getInstance() {
        if (instance == null)
            instance = new ManagementApp();
        return instance;
    }



    /*
        PROJECT
    */
    // Project - creation, deletion, repo
    /**
     * This function creates a new project, with correct project number
     * @author Christian Kento Rasmussen (s204159)
     * @author Christian Raasten
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
    /**
     * @author William Steffens (s185369)
     */
    public void deleteProject(Project project) {
        project.getActivityRepo().forEach(Activity::unassignAllEmployees);
        projectRepo.remove(project);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT_REPO, null, null);
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
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
    /**
     * @author William Steffens (s185369)
     */
    public void assignProjectLeader(Project project, Employee employee) {
        project.setProjectLeader(employee);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
    }
    public Employee getProjectLeader(Project project) {
        return project.getProjectLeader();
    }
    /**
     * @author William Steffens (s185369)
     */
    public void checkIsProjectLeader(Project project) throws OperationNotAllowedException {
        if (!user.equals(project.getProjectLeader()))
            throw new OperationNotAllowedException("Only the project leader is allow to perform that action");
    }
    public String getProjectLeaderUsername(Project project) throws OperationNotAllowedException {
        return project.getProjectLeaderUsername();
    }

    // Project - info
    public String getProjectNum(Project project) {
        return project.getProjectNum();
    }
    public String getProjectName(Project project) {
        return project.getProjectName();
    }
    public void setProjectName(Project project, String projectName) {
        project.setProjectName(projectName);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public void setProjectStartWeek(Project project, int startWeek) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        project.setStartWeek(startWeek);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public int getProjectStartWeek(Project project) {
        return project.getStartWeek();
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public void setProjectEndWeek(Project project, int endWeek) throws OperationNotAllowedException {
        checkIsProjectLeader(project);
        project.setEndWeek(endWeek);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
    }
    public int getProjectEndWeek(Project project) {
        return project.getEndWeek();
    }

    // Project - work-info
    /**
     * @author William Steffens (s185369)
     */
    public float getSpendHoursOnProject(Project project) throws OperationNotAllowedException {
        checkIsProjectLeader(project);

        return project.getSpendHours();
    }
    public float getExpectedHoursOnProject(Project project) throws OperationNotAllowedException {
        checkIsProjectLeader(project);

        return project.getExpectedHours();
    }
    public float getRemainingHoursOnProject(Project project) throws OperationNotAllowedException {
        checkIsProjectLeader(project);

        return project.getRemainingHours();
    }



    /*
        ACTIVITY
    */
    // Activity - creation, deletion, repo
    public void addNewProjectActivity(Project project, String activityName) throws OperationNotAllowedException {
        project.addNewActivity(activityName);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY_REPO, null, null);
    }
    public void deleteProjectActivity(Project project, Activity activity) {
        project.deleteActivity(activity);

        support.firePropertyChange(NotificationType.UPDATE_PROJECT, null, null);
        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY_REPO, null, null);
    }
    public void addNewUserActivity(String activityName) throws OperationNotAllowedException {
        user.addNewActivity(activityName);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY_REPO, null, null);
    }
    public List<Activity> getProjectActivityRepo(Project project) {
        return project.getActivityRepo();
    }
    public Activity getUserActivity(String activityName) {
        return user.getActivity(activityName);
    }
    public List<Activity> getUserActivities() {
        return user.getAssignedActivities();
    }
    public void authorizeActivity(Activity activity) throws OperationNotAllowedException {
        if (user != activity.getParentEmployee())
            checkIsProjectLeader(activity.getParentProject());
    }

    // Activity - assigned employees
    public void assignEmployeeToActivity(Activity activity, Employee employee) throws OperationNotAllowedException {
        activity.assignEmployee(employee);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    /**
     * @author William Steffens (s185369)
     */
    public void unassignEmployeeFromActivity(Activity activity, Employee employee) throws OperationNotAllowedException {
        employee.unassignActivity(activity);
        activity.unassignEmployee(employee);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public List<Employee> getAssignedEmployees(Activity activity) {
        return activity.getAssignedEmployees();
    }

    // Activity - info
    public String getActivityName(Activity activity) {
        return activity.getActivityName();
    }
    public void setActivityName(Activity activity, String activityName) throws OperationNotAllowedException {
        if (activity.getActivityType() == Activity.PROJECT_TYPE) {
            if (activity.getParentProject().getActivityRepo().stream().anyMatch(act -> act.getActivityName().equals(activityName)))
                throw new OperationNotAllowedException("The project already contains an activity with that name");
        } else if (activity.getActivityType() == Activity.EMPLOYEE_TYPE) {
            if (activity.getParentEmployee().getAssignedActivities().stream().anyMatch(act -> act.getActivityName().equals(activityName)))
                throw new OperationNotAllowedException("The current user already has an activity with that name");
        }

        activity.setActivityName(activityName);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public void setActivityStartWeek(Activity activity, int startWeek) throws OperationNotAllowedException {
        authorizeActivity(activity);

        activity.setStartWeek(startWeek);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }

    public void setActivityStartEndWeek(Activity activity, int startWeek, int endWeek) throws OperationNotAllowedException {
        activity.setStartEndWeek(startWeek,endWeek);
        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public int getActivityStartWeek(Activity activity) {
        return activity.getStartWeek();
    }
    public void setActivityEndWeek(Activity activity, int endWeek) throws OperationNotAllowedException {
        authorizeActivity(activity);

        activity.setEndWeek(endWeek);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public int getActivityEndWeek(Activity activity) {
        return activity.getEndWeek();
    }
    /**
     * @author William Steffens (s185369)
     */
    public int getActivityType(Activity activity) {
        return activity.getActivityType();
    }

    // Activity - work-info
    public float getSpendHoursOnActivity(Activity activity) throws OperationNotAllowedException {
        authorizeActivity(activity);

        return activity.getSpendHours();
    }
    public float getExpectedWorkHoursOnActivity(Activity activity) throws OperationNotAllowedException {
        authorizeActivity(activity);

        return activity.getExpectedWorkHours();
    }
    public void setExpectedWorkHoursOnActivity(Activity activity, float hours) throws OperationNotAllowedException {

        // Pre-condition
        assert activity != null : " Pre - condition violation " ;

        authorizeActivity(activity);

        if (hours>=0) {
            activity.setExpectedWorkHours(hours);}
        else {
            throw new OperationNotAllowedException("Expected Work hours must be positive"); }

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);

        // Post-condition
        assert activity.getExpectedWorkHours() == hours : " post - condition violation ";

    }
    public float getRemainingHoursOnActivity(Activity activity) throws OperationNotAllowedException {
        authorizeActivity(activity);

        return activity.getRemainingHours();
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public List<Employee> listAvailableEmployeesForActivity(Activity activity) throws OperationNotAllowedException {
        checkIsProjectLeader(activity.getParentProject());
        List<Employee> employeesAvailable = new ArrayList<>();

        int startWeek = getActivityStartWeek(activity);
        int endWeek = getActivityEndWeek(activity);

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
    /**
     * @author William Steffens (s185369)
     */
    public void addEmployee(String username) throws OperationNotAllowedException {
        String newUsername = username.toLowerCase(); // 1

        if ((newUsername.length() <= 0) || newUsername.length() > 4) // 2
            throw new OperationNotAllowedException("The username needs to be between one and four letters long."); // 3

        if (!newUsername.matches("^[a-z]+$")) // 4
            throw new OperationNotAllowedException("The username cannot contain non-alphabetical characters (a-zA-Z)."); // 5

        if (getEmployee(newUsername) != null) // 6
            throw new OperationNotAllowedException("An employee with that username already exists."); // 7

        assert newUsername != null
                && newUsername.length() > 0 && newUsername.length() <= 4
                && newUsername.matches("^[a-zA-Z]+$")
                && employeeRepo.stream().noneMatch(employee -> employee.getUsername().equals(newUsername)) : "Pre-condition";

        employeeRepo.add(new Employee(newUsername)); // 8

        assert employeeRepo.stream().anyMatch(employee -> employee.getUsername().equals(newUsername)) : "Post-condition";

        support.firePropertyChange(NotificationType.UPDATE_EMPLOYEE_REPO, null, null);
    }
    /**
     * @author William Steffens (s185369)
     */
    public void removeEmployee(Employee employee) throws OperationNotAllowedException {
        if (!employeeRepo.contains(employee) || employee == null)
            throw new OperationNotAllowedException("Should not remove employee that is not in the system");

        projectRepo.forEach(project -> {
            if (project.getProjectLeader() == employee)
                project.setProjectLeader(null);
            project.getActivityRepo().forEach(activity -> {
                if (activity.getAssignedEmployees().contains(employee))
                    activity.unassignEmployee(employee);
            });
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
    /**
     * @author William Steffens (s185369)
     */
    public String getUserUsername() throws OperationNotAllowedException {
        if (user == null)
            throw new OperationNotAllowedException("No employee is logged in");

        return user.getUsername();
    }

    // Employee - work-info
    public void registerWorkHoursOnActivity(Activity activity, float hours) throws OperationNotAllowedException {
        activity.registerWorkHours(user, hours);

        support.firePropertyChange(NotificationType.UPDATE_ACTIVITY, null, null);
    }
    public float getWorkedHoursOnActivity(Activity activity) {
        return activity.getWorkedHours(user);
    }
}


