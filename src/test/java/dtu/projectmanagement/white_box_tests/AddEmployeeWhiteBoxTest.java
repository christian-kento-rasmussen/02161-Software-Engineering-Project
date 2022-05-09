package dtu.projectmanagement.white_box_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class AddEmployeeWhiteBoxTest {

    ManagementApp managementApp;

    @BeforeEach
    public void setUp() {
        managementApp = new ManagementApp();
    }

    @Test
    public void testInputDataSetA() {
        assertThrows("The username needs to be between one and four letters long.", OperationNotAllowedException.class, () -> {
            managementApp.addEmployee("username");
        });
    }


    @Test
    public void testInputDataSetB() {
        assertThrows("The username cannot contain non-alphabetical characters (a-zA-Z).", OperationNotAllowedException.class, () -> {
            managementApp.addEmployee("1234");
        });
    }

    @Test
    public void testInputDataSetC() throws OperationNotAllowedException {
        assertThrows("An employee with that username already exists.", OperationNotAllowedException.class, () -> {
            managementApp.addEmployee("foo");
            managementApp.addEmployee("foo");
        });
    }

    @Test
    public void testInputDataSetD() throws OperationNotAllowedException {
        managementApp.addEmployee("foo");
        assertTrue(managementApp.getEmployeeRepo().stream().anyMatch(employee -> employee.getUsername().equals("foo")));
    }

}
