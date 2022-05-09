package dtu.projectmanagement.white_box_tests;

import dtu.projectmanagement.acceptance_tests.ProjectHelper;
import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import org.junit.Test;


public class SetExpectedWorkHoursOnActivityWhiteBoxTest {

//    private ManagementApp managementApp;
    ManagementApp managementApp = new ManagementApp();

    ProjectHelper projectHelper = new ProjectHelper(managementApp);

//    public WhiteBoxTest(ManagementApp managementApp){
//        this.managementApp = managementApp;
//    }

    @Test(expected = OperationNotAllowedException.class)
    public void testDataSetA() throws OperationNotAllowedException {


        projectHelper.addProject();
        managementApp.addNewProjectActivity(projectHelper.getProject(), "Lav mad");
        managementApp.addEmployee("theo");
        managementApp.login(managementApp.getEmployee("theo"));
//        managementApp.assignProjectLeader(projectHelper.getProject(), managementApp.getEmployee("Theo"));
        managementApp.assignEmployeeToActivity(projectHelper.getActivity("Lav mad"), managementApp.getEmployee("theo"));
        managementApp.setExpectedWorkHoursOnActivity(projectHelper.getActivity("Lav mad"),10);

//        System.out.println(projectHelper.getProject().getActivity("Lav mad").getExpectedWorkHours());
    }

    @Test(expected = OperationNotAllowedException.class)
    public void testDataSetB() throws OperationNotAllowedException {

        projectHelper.addProject();
        managementApp.addNewProjectActivity(projectHelper.getProject(), "Lav mad");
        managementApp.addEmployee("theo");
        managementApp.login(managementApp.getEmployee("theo"));
        managementApp.assignProjectLeader(projectHelper.getProject(), managementApp.getEmployee("theo"));
        managementApp.assignEmployeeToActivity(projectHelper.getActivity("Lav mad"), managementApp.getEmployee("theo"));
        managementApp.setExpectedWorkHoursOnActivity(projectHelper.getActivity("Lav mad"),-1);


//        System.out.println(projectHelper.getProject().getActivity("Lav mad").getExpectedWorkHours());

    }


    @Test()
    public void testDataSetC() throws OperationNotAllowedException {

        projectHelper.addProject();
        managementApp.addNewProjectActivity(projectHelper.getProject(), "Lav mad");
        managementApp.addEmployee("theo");
        managementApp.login(managementApp.getEmployee("theo"));
        managementApp.assignProjectLeader(projectHelper.getProject(), managementApp.getEmployee("theo"));
        managementApp.assignEmployeeToActivity(projectHelper.getActivity("Lav mad"), managementApp.getEmployee("theo"));
        managementApp.setExpectedWorkHoursOnActivity(projectHelper.getActivity("Lav mad"),10);


//        System.out.println(projectHelper.getProject().getActivity("Lav mad").getExpectedWorkHours());

    }





}
