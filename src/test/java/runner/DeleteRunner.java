package runner;

import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;



@CucumberOptions(
    features = "src/test/resources/features/OpenMrsDeletePatient.feature",
    //features = "@rerun.txt",
    glue = "stepDefinitions",
    tags = "@LastScenario",  
    monochrome = true,
	dryRun = false,
    plugin = { "pretty",
    		"html:target/cucumber-reports/delete",
    		"json:target/cucumber-reports/delete.json",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)


public class DeleteRunner extends AbstractTestNGCucumberTests {


}
