package dtu.projectmanagement.app;

import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;

import java.util.*;

public class ManagementApp {

    private Employee user;

    private List<Project> projectRepo = new ArrayList<>();
    private List<Employee> employeeRepo = new ArrayList<>();

    private Map<Integer, Integer> serialNum = new HashMap<>();

    /**
     * This function creates a new project, with correct project number
     */
    public void createNewProject()  {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int yearCounter = serialNum.getOrDefault(year,0);

        serialNum.put(year,yearCounter+1);

        String projectNum = String.format("%02d%04d", year % 100, yearCounter+1);
        Project project= new Project(projectNum);

        projectRepo.add(project);
    }

    public Project getProject(String project_number){

        for (Project project : projectRepo) {
            if (project.getProjectNum().equals(project_number)){
                return project;
            }
        }

        return null;
    }

    public void login(String username) {
        user = getEmployee(username);
    }

    public Employee getEmployee(String username) {
        return employeeRepo.stream()
                .filter(emp -> emp.getUsername() == username)
                .findAny()
                .orElse(null);
    }

    public void addEmployee(String username) {
        employeeRepo.add(new Employee(username));
    }

    public void removeEmployee(Employee employee) {
        employeeRepo.remove(employee);
    }

    public void assignProjectLeader(Project project, Employee employee) {
        project.setProjectLeader(employee);
    }


    public void addNewActivity(Project project, String activityName) {
        project.addNewActivity(activityName);
    }



}


