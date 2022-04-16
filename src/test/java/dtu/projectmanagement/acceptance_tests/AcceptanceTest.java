package dtu.projectmanagement.acceptance_tests.steps;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = "features",
	plugin = { "html:target/cucumber/wikipedia.html"}, 
	monochrome = true,
	snippets = SnippetType.CAMELCASE,
	glue = {"steps"}
	)
public class AcceptanceTest {}
