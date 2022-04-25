package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import org.junit.Test;



import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.domain.Project;
import io.cucumber.java.en.*;


import java.util.List;

import static org.junit.Assert.*;


public class WhiteBoxTest {

//    private ManagementApp managementApp;
    ManagementApp managementApp = new ManagementApp();


//    public WhiteBoxTest(ManagementApp managementApp){
//        this.managementApp = managementApp;
//    }

    @Test()
    public void testDataSetA() {
        managementApp.createNewProject();
        Project project=managementApp.getProject("220001");
        System.out.print(project);
    }

    @Test()
    public void testDataSetB() {
        managementApp.createNewProject();
        Project project=managementApp.getProject("000000");
        System.out.print(project);
    }









}
