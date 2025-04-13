package runner;

import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;



@CucumberOptions(
    features = "src/test/resources/features/OpenMrsMergeVisits.feature",
    //features = "@rerun.txt",
    glue = "stepDefinitions",
    tags = "@Merge",  
    monochrome = true,
	dryRun = false,
    plugin = { "pretty",
    		"html:target/cucumber-reports/mergevisit",
    		"json:target/cucumber-reports/mergevisit.json",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)


public class MergeRunner extends AbstractTestNGCucumberTests {


}
