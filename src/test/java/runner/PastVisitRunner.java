package runner;

import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;



@CucumberOptions(
    features = "src/test/resources/features/OpenMrsPastVisit.feature",
    //features = "@rerun.txt",
    glue = "stepDefinitions",
    tags = "@PastVisit",  
    monochrome = true,
	dryRun = false,
    plugin = { "pretty",
    		"html:target/cucumber-reports/pastvisit",
    		"json:target/cucumber-reports/pastvisit.json",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)


public class PastVisitRunner extends AbstractTestNGCucumberTests {


}
