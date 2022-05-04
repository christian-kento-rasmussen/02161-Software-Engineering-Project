package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Employee;

public class EmployeeHelper {

    private final String username = "test";
    private final ManagementApp managementApp;

    public EmployeeHelper(ManagementApp managementApp) {
        this.managementApp = managementApp;
    }



    public Employee getEmployee() {
        return managementApp.getEmployee(username);
    }

    public Employee getEmployee(String username) {
        return managementApp.getEmployee(username);
    }


    public void addEmployee() throws OperationNotAllowedException {
        managementApp.addEmployee(username);
    }

    public void login() throws OperationNotAllowedException {
        addEmployee();
        managementApp.login(getEmployee(username));
    }
}
