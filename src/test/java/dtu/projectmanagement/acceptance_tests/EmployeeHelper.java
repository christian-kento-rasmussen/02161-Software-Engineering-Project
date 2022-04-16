package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Employee;

public class EmployeeHelper {

    private String username;
    private ManagementApp managementApp;

    public EmployeeHelper(ManagementApp managementApp) {
        this.managementApp = managementApp;
    }



    public Employee getEmployee() {
        if (username == null)
            addEmployee();
        return managementApp.getEmployee(username);
    }

    public void addEmployee() {
        username = "test";
        managementApp.addEmployee(username);
    }

    public void login() {
        addEmployee();
        managementApp.login(username);
    }
}
