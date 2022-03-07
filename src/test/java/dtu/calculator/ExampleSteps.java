package dtu.calculator;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ExampleSteps {
	
	UpnCalculator calc = new UpnCalculator();

	@Given("the first input is {int}")
	public void theFirstInputIs(Integer int1) {
	    calc.input(int1);
	}

	@Given("the second input is {int}")
	public void theSecondInputIs(Integer int1) {
	    calc.input(int1);
	}

	@When("the add button is pressed")
	public void theAddButtonIsPressed() {
	    calc.add();
	}

	@Then("{int} is shown on the display.")
	public void theIsShown(Integer number) {
	    assertEquals((int)number,calc.display());
	}
}

