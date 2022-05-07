package dtu.projectmanagement.white_box_tests;

import dtu.projectmanagement.app.ManagementApp;
import org.junit.Test;


import dtu.projectmanagement.domain.Project;


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
