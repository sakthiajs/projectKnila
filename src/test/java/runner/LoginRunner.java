package runner;

import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;



@CucumberOptions(
    features = "src/test/resources/features/OpenMrsLogin.feature",
    //features = "@rerun.txt",
    glue = "stepDefinitions",
    tags = "@Login",  
    monochrome = true,
	dryRun = false,
    plugin = { "pretty",
    		"html:target/cucumber-reports/login",
    		"json:target/cucumber-reports/login.json",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)


public class LoginRunner extends AbstractTestNGCucumberTests {


}
