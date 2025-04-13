package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageElements.OpenMRSPageElements;

import static org.testng.Assert.assertEquals;

import baseUtils.WaitUtils;
import baseUtils.ScreenshotUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.BeforeSuite;

public class LoginStepDefinition extends BaseSteps {

    OpenMRSPageElements elements;
    private static final Logger logger = LogManager.getLogger(LoginStepDefinition.class);

    public LoginStepDefinition(OpenMRSPageElements elements) {
        this.elements = elements;
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        logger.info("Navigating to login page: " + elements.URL);
        driver.get(elements.URL);
    }

   @BeforeSuite
    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        logger.info("Entering credentials: username = " + username);
        driver.findElement(elements.userName).sendKeys(username);
        driver.findElement(elements.password).sendKeys(password);
        driver.findElement(elements.location).click();
        driver.findElement(elements.loginButton).click();
    }

    @Then("I should be redirected to the dashboard")
    public void i_should_be_redirected_to_the_dashboard() {
        String expectedUrl = "https://o2.openmrs.org/openmrs/referenceapplication/home.page";
        logger.info("Waiting for redirect to dashboard...");
        boolean redirected = WaitUtils.waitForUrlToContain(driver, "home.page");

        String actualUrl = driver.getCurrentUrl();

        try {
            assertEquals(actualUrl, expectedUrl, "User should be on the dashboard");
            logger.info("Redirect successful. Current URL: " + actualUrl);
        } catch (AssertionError e) {
            logger.error("Redirect failed. Expected: " + expectedUrl + ", but was: " + actualUrl);
            ScreenshotUtils.takeScreenshot(driver, "redirect_failure");
            throw e;
        }
    }
}
