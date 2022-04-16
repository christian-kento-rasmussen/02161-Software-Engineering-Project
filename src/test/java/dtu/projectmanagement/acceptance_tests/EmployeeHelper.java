package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Employee;

public class EmployeeHelper {

    private Employee employee;
    private ManagementApp managementApp;

    public EmployeeHelper(ManagementApp managementApp) {
        this.managementApp = managementApp;
    }



    public void addEmployee() {
        managementApp.addEmployee(getEmployee().getUsername());
    }

    public Employee createTestEmployee() {
        return new Employee("test");
    }

    public Employee getEmployee() {
        if (employee == null)
            employee = createTestEmployee();
        return employee;
    }
}
