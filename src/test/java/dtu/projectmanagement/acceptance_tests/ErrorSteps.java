package dtu.projectmanagement.acceptance_tests;

import dtu.projectmanagement.app.ManagementApp;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertEquals;

public class ErrorSteps {

    private ManagementApp managementApp;
    private ErrorMessageHolder errorMessage;

    public ErrorSteps(ManagementApp managementApp, ErrorMessageHolder errorMessage) {
        this.managementApp = managementApp;
        this.errorMessage = errorMessage;
    }



    @Then("the error message {string} is given")
    public void theErrorMessageIsGiven(String errorMessage) {
        assertEquals(errorMessage, this.errorMessage.getErrorMessage());
    }
}
