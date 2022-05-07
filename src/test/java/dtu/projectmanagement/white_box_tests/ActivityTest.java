package dtu.projectmanagement.white_box_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private ManagementApp managementApp = new ManagementApp();
    private Activity activity;

    @BeforeEach
    void setUp() {
        managementApp.createNewProject();
        /*activity = new Activity("save the world");
        managementApp.addEmployee("BLIB");*/
    }

    @Test
    void registerWorkHoursSetA() {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> activity.registerWorkHours(managementApp.getUser(), -1));
        assertEquals("Time must be positive or 0", exception.getMessage());
    }
    @Test
    void registerWorkHoursSetB() {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> activity.registerWorkHours(managementApp.getUser(), 1.25f));
        assertEquals("Time must be given in half hours", exception.getMessage());
    }
    @Test
    void registerWorkHoursSetC() throws OperationNotAllowedException {
        HashMap<Employee, Float> employeeWorkHoursMap = activity.getEmployeeWorkHoursMap();
        employeeWorkHoursMap.put(managementApp.getUser(),0f);
        activity.registerWorkHours(managementApp.getUser(), 2f);
    }

    @Test
    void registerWorkHoursSetD() throws OperationNotAllowedException {
        activity.registerWorkHours(managementApp.getUser(), 2f);
    }
}