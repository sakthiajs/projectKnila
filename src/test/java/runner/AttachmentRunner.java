package runner;

import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;



@CucumberOptions(
    features = "src/test/resources/features/OpenMrsAttachment.feature",
    //features = "@rerun.txt",
    glue = "stepDefinitions",
    tags = "@Attachment",  
    monochrome = true,
	dryRun = false,
    plugin = { "pretty",
    		"html:target/cucumber-reports/attachment",
    		"json:target/cucumber-reports/attachment.json",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)


public class AttachmentRunner extends AbstractTestNGCucumberTests {


}
