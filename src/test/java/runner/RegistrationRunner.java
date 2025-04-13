package runner;

import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;



@CucumberOptions(
    features = "src/test/resources/features/OpenMrsRegistration.feature",
    //features = "@rerun.txt",
    glue = "stepDefinitions",
    tags = "@Registration",  
    monochrome = true,
	dryRun = false,
    plugin = { "pretty",
    		"html:target/cucumber-reports/registration",
    		"json:target/cucumber-reports/registration.json",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)


public class RegistrationRunner extends AbstractTestNGCucumberTests {


}
