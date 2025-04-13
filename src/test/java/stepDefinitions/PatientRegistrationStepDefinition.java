package stepDefinitions;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Period;

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

public class PatientRegistrationStepDefinition extends BaseSteps {

    OpenMRSPageElements elements;
    private static final Logger logger = LogManager.getLogger(PatientRegistrationStepDefinition.class);
    Select select;

    public PatientRegistrationStepDefinition(OpenMRSPageElements elements) {
        this.elements = elements;
    }

    @Given("I am on the dashboard")
    public void i_am_on_the_dashboard() {
        logger.info("Navigating to Patient Registration page...");
        driver.findElement(elements.registerPatient).click();
    }

    @When("I enter patient details")
    public void i_enter_patient_details() {
        logger.info("Filling out patient registration form...");

        driver.findElement(elements.nameLabel).click();
        driver.findElement(elements.givenName).sendKeys("Sakthivel");
        driver.findElement(elements.familyName).sendKeys("Äj");

        driver.findElement(elements.genderLabel).click();
        WebElement genderDropdown = driver.findElement(elements.genderField);
        select = new Select(genderDropdown);
        select.selectByValue("M");

        driver.findElement(elements.birthdateLabel).click();
        driver.findElement(elements.birthdateDay).sendKeys("05");
        WebElement birthMonthDropdown = driver.findElement(elements.birthdateMonth);
        select = new Select(birthMonthDropdown);
        select.selectByValue("6");
        driver.findElement(elements.birthdateYear).sendKeys("1997");

        driver.findElement(elements.addressLabel).click();
        driver.findElement(elements.addressField1).sendKeys("Guga Street");
        driver.findElement(elements.cityVillage).sendKeys("Thirukumaran Nagar");
        driver.findElement(elements.stateProvince).sendKeys("TN");
        driver.findElement(elements.country).sendKeys("IND");
        driver.findElement(elements.postalCode).sendKeys("641057");

        driver.findElement(elements.phoneNumberLabel).click();
        driver.findElement(elements.phoneNumberField).sendKeys("6677556765");

        driver.findElement(elements.relativesLabel).click();
        WebElement relationshipDropdown = driver.findElement(elements.relationshipType);
        select = new Select(relationshipDropdown);
        select.selectByVisibleText("Parent");
        driver.findElement(elements.personName).sendKeys("Ram");

        driver.findElement(elements.confirmationLabel).click();
        logger.info("Form filled. Moving to confirmation page.");
    }

    @Then("I should confirm patient information")
    public void i_should_confirm_patient_information() {
        logger.info("Verifying patient confirmation details...");

        try {
            WaitUtils.waitForElementVisible(driver, elements.confirmName, 5);
            String displayedName = driver.findElement(elements.confirmName).getText();
            logger.info("Displayed Name: " + displayedName);
            Assert.assertEquals(displayedName, "Sakthivel, Äj", "Name Mismatch");

            WaitUtils.waitForElementVisible(driver, elements.confirmGender, 5);
            String displayedGender = driver.findElement(elements.confirmGender).getText();
            logger.info("Displayed Gender: " + displayedGender);
            Assert.assertEquals(displayedGender, "Male", "Gender Mismatch");

            WaitUtils.waitForElementVisible(driver, elements.confirmBirthdate, 5);
            String displayedBirthDate = driver.findElement(elements.confirmBirthdate).getText();
            logger.info("Displayed Birthdate: " + displayedBirthDate);
            Assert.assertEquals(displayedBirthDate, "05, June, 1997", "BirthDate Mismatch");

            WaitUtils.waitForElementVisible(driver, elements.confirmAddress, 5);
            String displayedAddress = driver.findElement(elements.confirmAddress).getText();
            logger.info("Displayed Address: " + displayedAddress);
            Assert.assertEquals(displayedAddress, "Guga Street, Thirukumaran Nagar, TN, IND, 641057", "Address Mismatch");

            WaitUtils.waitForElementVisible(driver, elements.confirmPhoneNumber, 5);
            String displayedPhoneNumber = driver.findElement(elements.confirmPhoneNumber).getText();
            logger.info("Displayed Phone Number: " + displayedPhoneNumber);
            Assert.assertEquals(displayedPhoneNumber, "6677556765", "Phone Number Mismatch");

            logger.info("All confirmation details matched successfully.");

        } catch (AssertionError e) {
            logger.error("Patient confirmation mismatch detected: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "patient_confirmation_mismatch");
            throw e;
        } catch (Exception e) {
            logger.error("Exception while verifying patient confirmation: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "patient_confirmation_exception");
            throw new RuntimeException(e);
        }
    }

    @Then("I should be redirected to patient details page")
    public void i_should_be_redirected_to_patient_details_page() {
        logger.info("Submitting patient registration...");
        driver.findElement(elements.confirmButton).click();

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

    @Then("The system should calculate the age correctly")
    public void The_system_should_calculate_the_age_correctly() {
        logger.info("Verifying calculated age...");

        try {
            WebElement ageElement = WaitUtils.waitForElementVisible(driver, elements.calculatedAge);
            String calculatedAgeText = ageElement.getText().trim();
            int calculatedAge = Integer.parseInt(calculatedAgeText);

            LocalDate birthdate = LocalDate.of(1997, 6, 5); // Same DOB used in form
            LocalDate today = LocalDate.now();
            int expectedAge = Period.between(birthdate, today).getYears();

            assertEquals(calculatedAge, expectedAge, "Age is not calculated correctly.");
            logger.info("Age calculated correctly: " + calculatedAge);

        } catch (AssertionError e) {
            logger.error("Age mismatch. Expected: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "age_calculation_error");
            throw e;
        } catch (Exception e) {
            logger.error("Exception during age verification: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "age_verification_exception");
            throw new RuntimeException(e);
        }
    }
}