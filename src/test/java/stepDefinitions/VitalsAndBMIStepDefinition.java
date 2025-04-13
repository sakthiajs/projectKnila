package stepDefinitions;

import baseUtils.ScreenshotUtils;
import baseUtils.WaitUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageElements.OpenMRSPageElements;

public class VitalsAndBMIStepDefinition extends BaseSteps {

    OpenMRSPageElements elements;
    private static final Logger logger = LogManager.getLogger(VitalsAndBMIStepDefinition.class);

    double expectedHeight = 175.0;
    double expectedWeight = 65.0;
    double expectedBMI = Math.round((expectedWeight / Math.pow(expectedHeight / 100.0, 2)) * 10.0) / 10.0;

    public VitalsAndBMIStepDefinition(OpenMRSPageElements elements) {
        this.elements = elements;
    }

    @Given("Start Visit Again")
    public void start_visit_again() {
        try {
            WebElement startVisitBtn = WaitUtils.waitForElementClickable(driver, elements.startVisit, 10);
            startVisitBtn.click();
            WebElement confirmBtn = WaitUtils.waitForElementClickable(driver, elements.confirmVisitButton, 10);
            confirmBtn.click();
            logger.info("Visit started successfully.");
        } catch (Exception e) {
            logger.error("Failed to start visit: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "start_visit_error");
            throw new RuntimeException("Visit could not be started", e);
        }
    }

    @When("Click on {string} menu")
    public void click_on_capture_vitals_menu(String menu) {
        try {
            WebElement captureVitalsMenu = WaitUtils.waitForElementClickable(driver, elements.captureVitals, 10);
            captureVitalsMenu.click();
            logger.info("Clicked on menu: " + menu);
        } catch (Exception e) {
            logger.error("Failed to click on menu: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "click_capture_vitals_menu_error");
            throw new RuntimeException("Could not click menu", e);
        }
    }

    @When("I enter vitals data")
    public void i_enter_vitals_data() {
        try {
            WaitUtils.waitForElementVisible(driver, elements.heightLabel, 10).click();
            WaitUtils.waitForElementVisible(driver, elements.heightField, 10).sendKeys(String.valueOf((int) expectedHeight));

            WaitUtils.waitForElementVisible(driver, elements.weightLabel, 10).click();
            WaitUtils.waitForElementVisible(driver, elements.weightField, 10).sendKeys(String.valueOf((int) expectedWeight));

            logger.info("Entered height and weight successfully.");

        } catch (Exception e) {
            logger.error("Failed to enter vitals: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "enter_vitals_error");
            throw new RuntimeException("Vitals input failed", e);
        }
    }

    @Then("I should verify the BMI calculation and save the form")
    public void i_should_verify_the_bmi_calculation_and_save_the_form() {
        try {
            WaitUtils.waitForElementVisible(driver, elements.calclatedBMILabel, 10).click();

            String bmiText = WaitUtils.waitForElementVisible(driver, elements.confirmBMIValue, 10).getText().trim();
            double actualBMI = Double.parseDouble(bmiText);

            Assert.assertEquals(actualBMI, expectedBMI, "BMI calculation mismatch");
            logger.info("BMI is calculated correctly: " + actualBMI);

            WaitUtils.waitForElementClickable(driver, elements.saveForm, 10).click();
            WaitUtils.waitForElementClickable(driver, elements.saveButton, 10).click();
            logger.info("Form saved successfully.");

        } catch (Exception e) {
            logger.error("BMI verification or form submission failed: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "bmi_save_error");
            throw new RuntimeException("BMI validation or save failed", e);
        }
    }

    @Then("Click End Visit and redirect to patient details page")
    public void click_end_visit_and_redirect_to_patient_details_page() {
        try {
            WaitUtils.waitForElementClickable(driver, elements.endVisit, 10).click();
            WaitUtils.waitForElementClickable(driver, elements.confirmEndVisit, 10).click();
            logger.info("Clicked End Visit.");

            WaitUtils.waitForElementClickable(driver, elements.backPatientDetails, 6).click();

            boolean redirected = WaitUtils.waitForUrlToContain(driver, "patient.page?patientId", 10);
            String currentUrl = driver.getCurrentUrl();

            Assert.assertTrue(redirected && currentUrl.contains("patient.page?patientId"),
                    "Redirection to patient details page failed.");
            logger.info("Redirected to patient details page: " + currentUrl);

        } catch (Exception e) {
            logger.error("End Visit or redirection failed: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "end_visit_redirection_error");
            throw new AssertionError("End Visit and redirect failed", e);
        }
    }

    @And("Verify height and weight and BMI")
    public void verify_height_weight_and_bmi() {
        try {
            String actualHeight = WaitUtils.waitForElementVisible(driver, elements.compareHeight, 10).getText().replaceAll("[^\\d.]", "");
            String actualWeight = WaitUtils.waitForElementVisible(driver, elements.compareWeight, 10).getText().replaceAll("[^\\d.]", "");
            String actualBMI = WaitUtils.waitForElementVisible(driver, elements.compareBMI, 10).getText().replaceAll("[^\\d.]", "");

            Assert.assertEquals(Double.parseDouble(actualHeight), expectedHeight, "Height mismatch");
            Assert.assertEquals(Double.parseDouble(actualWeight), expectedWeight, "Weight mismatch");
            Assert.assertEquals(Double.parseDouble(actualBMI), expectedBMI, "BMI mismatch");

            logger.info("Height, Weight, and BMI are all displayed correctly.");

        } catch (Exception e) {
            logger.error("Vitals verification on patient details page failed: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "vitals_verification_error");
            throw new AssertionError("Vitals (height/weight/BMI) check failed", e);
        }
    }
}
