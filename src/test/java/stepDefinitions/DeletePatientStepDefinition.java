package stepDefinitions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import baseUtils.ScreenshotUtils;
import baseUtils.WaitUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageElements.OpenMRSPageElements;
import utils.DriverManager;

public class DeletePatientStepDefinition extends BaseSteps {

    OpenMRSPageElements elements;
    private static final Logger logger = LogManager.getLogger(DeletePatientStepDefinition.class);

    private String deletedPatientID; 

    public DeletePatientStepDefinition(OpenMRSPageElements elements) {
        this.elements = elements;
    }

	
    @Given("I am on the patient details page")
    public void i_am_on_the_patient_details_page() {
        logger.info("Patient registration...");

        String expectedUrlPart = "patient.page?patientId=";

        try {
            logger.info("Waiting for redirect to patient details page...");
            boolean redirected = WaitUtils.waitForUrlToContain(driver, expectedUrlPart);
            String actualUrl = driver.getCurrentUrl();

            Assert.assertTrue(redirected && actualUrl.contains(expectedUrlPart),
                    "Redirection to patient details page failed");

            logger.info("Redirect successful. Current URL: " + actualUrl);

        } catch (AssertionError e) {
            logger.error("Redirect failed. URL did not contain expected part: " + expectedUrlPart);
            ScreenshotUtils.takeScreenshot(driver, "redirect_failure");
            throw e;
        }

        WebElement patientIDElement = driver.findElement(elements.patientId);
        deletedPatientID = patientIDElement.getText(); 
        logger.info("Captured Patient ID: " + deletedPatientID);
    }

	@When("I click on {string}")
	public void i_click_on(String buttonName) {
	    try {
	        
	        driver.findElement(elements.deletePatient).click();
	        logger.info("Clicked on delete patient button.");

	        
	        WaitUtils.waitForElementVisible(driver, elements.deleteReason, 10).sendKeys("Test");
	        logger.info("Entered reason for deletion.");

	        
	        driver.findElement(elements.confirmDelete).click();
	        logger.info("Clicked confirm delete.");

	        
	        WebElement toaster = WaitUtils.waitForElementVisible(driver, elements.deletedToasterMessage, 10);
	        Assert.assertTrue(toaster.isDisplayed(), "Toaster message not displayed after deletion.");
	        logger.info("Toaster message displayed after deleting the patient.");

	    } catch (Exception e) {
	        logger.error("Failed during patient delete flow: " + e.getMessage());
	        ScreenshotUtils.takeScreenshot(driver, "delete_patient_error");
	        throw new AssertionError("Failed to verify toaster message after deleting patient", e);
	    }
	}


	@Then("I should verify the patient is deleted and not visible in the search results")
	public void i_should_verify_the_patient_is_deleted_and_not_visible_in_the_search_results() {
	    try {
	        WebElement searchBox = WaitUtils.waitForElementVisible(driver, elements.searchPatient, 10);
	        searchBox.clear();
	        searchBox.sendKeys(deletedPatientID); 
	        logger.info("Searched for deleted patient ID: " + deletedPatientID);

	        WebElement noRecordMessage = WaitUtils.waitForElementVisible(driver, elements.noRecordsBar, 10);
	        Assert.assertTrue(noRecordMessage.isDisplayed(), "Deleted patient is still visible in search results.");
	        logger.info("Verified patient is deleted and not in search results.");

	    } catch (Exception e) {
	        logger.error("Patient deletion verification failed: " + e.getMessage());
	        ScreenshotUtils.takeScreenshot(driver, "deleted_patient_still_visible");
	        throw new AssertionError("Deleted patient is still visible in search results", e);
	    }
	}

	@AfterClass
	public static void tearDown() 
	{
		DriverManager.quitDriver();
	}
}
