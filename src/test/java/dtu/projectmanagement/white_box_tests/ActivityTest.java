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
    void setUp() throws OperationNotAllowedException {
        managementApp.createNewProject();
        managementApp.addEmployee("BLIB");
        activity = new Activity("save the world",managementApp.getEmployee("BLIB"));
    }

    @Test
    void registerWorkHoursSetA() {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> activity.registerWorkHours(managementApp.getEmployee("BLIB"), -1));
        assertEquals("Time must be positive or 0", exception.getMessage());
    }
    @Test
    void registerWorkHoursSetB() {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> activity.registerWorkHours(managementApp.getEmployee("BLIB"), 1.25f));
        assertEquals("Time must be given in half hours", exception.getMessage());
    }
    @Test
    void registerWorkHoursSetC() throws OperationNotAllowedException {
        HashMap<Employee, Float> employeeWorkHoursMap = activity.getEmployeeWorkHoursMap();
        employeeWorkHoursMap.put(managementApp.getEmployee("BLIB"),0f);
        activity.registerWorkHours(managementApp.getEmployee("BLIB"), 2f);
    }

    @Test
    void registerWorkHoursSetD() throws OperationNotAllowedException {
        activity.registerWorkHours(managementApp.getEmployee("BLIB"), 2f);
    }

    @Test
    void setStartEndWeekA() throws OperationNotAllowedException {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> activity.setStartEndWeek(0, 1));
        assertEquals("Start week can not be 0", exception.getMessage());
    }

    @Test
    void setStartEndWeekB() throws OperationNotAllowedException {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> activity.setStartEndWeek(1, 0));
        assertEquals("End week can not be 0", exception.getMessage());
    }

    @Test
    void setStartEndWeekC() throws OperationNotAllowedException {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> activity.setStartEndWeek(2, 1));
        assertEquals("The end week needs to be after the start week", exception.getMessage());
    }

    @Test
    void setStartEndWeekD() throws OperationNotAllowedException {
        activity.setStartEndWeek(1, 2);
    }

}