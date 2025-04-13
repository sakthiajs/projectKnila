package stepDefinitions;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import baseUtils.ScreenshotUtils;
import baseUtils.TestFileUtils;
import baseUtils.WaitUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageElements.OpenMRSPageElements;

public class AddAttatchmentStepDefinition extends BaseSteps {
	
	OpenMRSPageElements elements;
    private static final Logger logger = LogManager.getLogger(PatientRegistrationStepDefinition.class);
    

    public AddAttatchmentStepDefinition(OpenMRSPageElements elements) {
        this.elements = elements;
    }

	
	@Given("Start visit and confirm visit")
	public void start_visit_and_confirm_visit() 
	{
	    driver.findElement(elements.startVisit).click();
	    driver.findElement(elements.confirmVisitButton).clear();
	}

	@When("I click on the {string} menu")
	public void i_click_on_the_menu(String string) {
		driver.findElement(elements.addAttachment).click();
		
	}

	@When("I upload a file")
	public void i_upload_a_file() {
	    logger.info("Uploading file using Robot from Desktop...");

	    
	    String filePath = System.getProperty("user.home") + "\\Desktop\\test-image.jpg";

	    try {
	       
	        WebElement uploadTrigger = WaitUtils.waitForElementClickable(driver, elements.dropAFileField, 10);
	        uploadTrigger.click();

	        
	        StringSelection selection = new StringSelection(filePath);
	        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

	        
	        Robot robot = new Robot();
	        robot.setAutoDelay(500);

	        
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);

	        
	        robot.keyPress(KeyEvent.VK_ENTER);
	        robot.keyRelease(KeyEvent.VK_ENTER);

	        logger.info("File uploaded successfully: " + filePath);

	    } catch (Exception e) {
	        logger.error("Upload failed: " + e.getMessage());
	        ScreenshotUtils.takeScreenshot(driver, "robot_upload_failure");
	        throw new RuntimeException(e);
	    }
	    
	    driver.findElement(elements.attatchmentCaption).sendKeys("AjFile");
	    driver.findElement(elements.uploadButton).click();
	    
	    
	}

	@Then("I should see a success message for the attachment")
	public void i_should_see_a_success_message_for_the_attachment() {
	    try {
	        
	        WebElement toaster = WaitUtils.waitForElementVisible(driver, elements.toasterMessage, 10); // 10 seconds timeout

	        
	        String actualMessage = toaster.getText().trim();
	        logger.info("Toaster message displayed: " + actualMessage);

	        
	        String expectedMessage = "The Attachment was successfully uploaded";

	        
	        Assert.assertTrue(actualMessage.contains(expectedMessage), 
	            "Expected success message but found: " + actualMessage);

	    } catch (Exception e) {
	        logger.error("Toaster message not displayed or incorrect: " + e.getMessage());
	        ScreenshotUtils.takeScreenshot(driver, "attachment_upload_toast_failure");
	        throw new AssertionError("Toaster message validation failed", e);
	    }
	}
	
	

	}


