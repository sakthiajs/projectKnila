package stepDefinitions;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import baseUtils.ScreenshotUtils;
import baseUtils.WaitUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageElements.OpenMRSPageElements;



public class PastVisitStepDefinition extends BaseSteps{

	OpenMRSPageElements elements;
	private static final Logger logger = LogManager.getLogger(PastVisitStepDefinition.class);
	

	public PastVisitStepDefinition(OpenMRSPageElements elements) {
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
    }
    
    @When("I click on the {string} menu")
    public void i_click_on_the_menu(String string)
    {
    	driver.findElement(elements.addPastVisit).click();
    	
    }
    
	@Then("I should verify the future date is not clickable in the date picker")
	public void i_should_verify_the_future_date_is_not_clickable_in_the_date_picker() 
	{
		try {
	        
	        WaitUtils.waitForElementVisible(driver, elements.addPastVisitButton, 10).click();
	        logger.info("Clicked on Add Past Visit.");

	        
	        WebElement datePicker = WaitUtils.waitForElementVisible(driver, elements.datePickerWidget, 10);
	        logger.info("Datepicker is visible.");

	        
	        LocalDate today = LocalDate.now();
	        
	        List<WebElement> selectableDates = driver.findElements(elements.futureDateSelector);

	        boolean futureDateClickable = false;

	        for (WebElement dateElement : selectableDates) {
	            String dateText = dateElement.getText().trim();
	            if (!dateText.matches("\\d+")) continue;

	            int day = Integer.parseInt(dateText);
	            String displayedMonthYear = driver.findElement(elements.datePickerMonthHeader).getText().trim();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
	            YearMonth yearMonth = YearMonth.parse(displayedMonthYear, formatter);
	            LocalDate fullDate = yearMonth.atDay(day);

	            if (fullDate.isAfter(today)) {
	                futureDateClickable = true;
	                logger.warn("Future date is clickable: " + fullDate);
	                break;
	            }
	        }

	        if (futureDateClickable) {
	            throw new AssertionError("A future date is selectable in the datepicker.");
	        } else {
	            logger.info("All future dates are correctly disabled in the datepicker.");
	        }

	        
	        WaitUtils.waitForElementVisible(driver, elements.cancelDateButton, 10).click();
	        logger.info("Clicked on Cancel to close the datepicker.");

	    } catch (Exception e) {
	        logger.error("Datepicker validation failed: " + e.getMessage());
	        ScreenshotUtils.takeScreenshot(driver, "datepicker_future_date_error");
	        throw new AssertionError("Failed to verify datepicker future date restriction", e);
	    }
	}
}
