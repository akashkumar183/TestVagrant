package cucumber.Options;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", glue = { "stepDefinations" }, plugin = { "pretty",
		"json:target/report.json", "html:target/report.html" }, monochrome = true, tags = { "@Test" })
public class TestRunner {

}
