package dtu.projectmanagement.domain;

import dtu.projectmanagement.app.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String projectNum;
    private String projectName;

    private Employee projectLeader;

    private int startWeek;
    private int endWeek;

    private final List<Activity> activities = new ArrayList<>();

    public Project(String projectNum) {
        this.projectNum = projectNum;
        this.projectName = "Unnamed project";
    }



    // Info
    /**
     * @author William Steffens (s185369)
     */
    public String getProjectNum() {
        return projectNum;
    }
    /**
     * @author William Steffens (s185369)
     */
    public String getProjectName() {
        return projectName;
    }
    /**
     * @author William Steffens (s185369)
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    /**
     * @author Christian Raasteen (s204148)
     */
    public void setProjectLeader(Employee employee) {
        projectLeader = employee;
    }
    /**
     * @author Christian Raasteen (s204148)
     */
    public Employee getProjectLeader() {
        return projectLeader;
    }
    /**
     * @author William Steffens (s185369)
     */
    public String getProjectLeaderUsername() throws OperationNotAllowedException {
        if (projectLeader == null)
            throw new OperationNotAllowedException("No project leader selected.");
        return projectLeader.getUsername();
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public int getStartWeek() {
        return startWeek;
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public void setStartWeek(int startWeek) throws OperationNotAllowedException {
        // Todo: test for this other case?
        if (endWeek != 0 && endWeek <= startWeek)
            throw new OperationNotAllowedException("The start week cannot be the same as or after the end week");
        else
            this.startWeek = startWeek;
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public int getEndWeek() {
        return endWeek;
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public void setEndWeek(int endWeek) throws OperationNotAllowedException {
        if (startWeek != 0 && startWeek >= endWeek)
            throw new OperationNotAllowedException("The start week cannot be the same as or after the end week");
        else
            this.endWeek = endWeek;
    }

    /**
     * @author Christian Raasteen (s204148)
     */
    public void addNewActivity(String activityName) throws OperationNotAllowedException {
        if (activities.stream().anyMatch(activity -> activity.getActivityName().equals(activityName)))
            throw new OperationNotAllowedException("An activity with that name already exists.");

        activities.add(new Activity(activityName, this));
    }

    /**
     * @author Christian Raasteen (s204148)
     */
    public void deleteActivity(Activity activity) {
        activity.unassignAllEmployees();
        activities.remove(activity);
    }
    /**
     * @author Mathias Daniel Frosz Nielsen (s201968)
     */
    public Activity getActivity(String activityName) {
        return activities.stream()
                .filter(activity -> activity.getActivityName().equals(activityName))
                .findAny()
                .orElse(null);
    }
    /**
     * @author Mathias Daniel Frosz Nielsen (s201968)
     */
    public List<Activity> getActivityRepo() {
        return activities;
    }


    // Work-info
    /**
     * @author William Steffens (s185369)
     */
    public float getSpendHours() {
        return activities.stream().reduce(0f, (acc, val) -> acc + val.getSpendHours(), Float::sum);
    }
    /**
     * @author William Steffens (s185369)
     */
    public float getExpectedHours() {
        return activities.stream().reduce(0f, (acc, val) -> acc + val.getExpectedWorkHours(), Float::sum);
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public float getRemainingHours() {
        return getExpectedHours() - getSpendHours();
    }
    /**
     * @author Christian Kento Rasmussen (s204159)
     */
    public void generateReport(String filelocation) {

    }
}
