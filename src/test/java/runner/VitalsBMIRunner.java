package runner;

import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;



@CucumberOptions(
    features = "src/test/resources/features/OpenMrsVitalsAndBMI.feature",
    //features = "@rerun.txt",
    glue = "stepDefinitions",
    tags = "@VitalsBMI",  
    monochrome = true,
	dryRun = false,
    plugin = { "pretty",
    		"html:target/cucumber-reports/vitalsBMI",
    		"json:target/cucumber-reports/vitalsBMI.json",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)


public class VitalsBMIRunner extends AbstractTestNGCucumberTests {


}
