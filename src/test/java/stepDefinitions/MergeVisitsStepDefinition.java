package stepDefinitions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import baseUtils.ScreenshotUtils;
import baseUtils.WaitUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageElements.OpenMRSPageElements;
public class MergeVisitsStepDefinition extends BaseSteps{
	
	OpenMRSPageElements elements;
    private static final Logger logger = LogManager.getLogger(MergeVisitsStepDefinition.class);

    
    public MergeVisitsStepDefinition(OpenMRSPageElements elements) {
        this.elements = elements;
    }
	
	
    @Given("Recent visit has two entries")
    public void recent_visit_has_two_entries() {
        try {
            WaitUtils.waitForElementVisible(driver, elements.confirmRecentVisit2, 10);
            logger.info("Recent visit with two entries is visible as expected.");
        } catch (Exception e) {
            logger.error("Recent visit with two entries is not visible: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "recent_visit_not_visible");
            throw new AssertionError("Recent visit with two entries not found", e);
        }
    }
	
	
    @When("I select two visits to merge")
    public void i_select_two_visits_to_merge() {
        try {
            driver.findElement(elements.mergeVisits).click();
            logger.info("Clicked on Merge Visits.");

            WaitUtils.waitForElementVisible(driver, elements.visitCheckbox1, 10).click();
            logger.info("Selected first visit checkbox.");

            WaitUtils.waitForElementVisible(driver, elements.visitCheckbox2, 10).click();
            logger.info("Selected second visit checkbox.");

            WaitUtils.waitForElementVisible(driver, elements.mergeSelectedVisitButton, 10).click();
            logger.info("Clicked on Merge Selected Visits button.");

            WaitUtils.waitForElementVisible(driver, elements.returnButton, 10).click();
            logger.info("Clicked on Return button after merging visits.");
            
        } catch (Exception e) {
            logger.error("Failed to merge visits: " + e.getMessage());
            ScreenshotUtils.takeScreenshot(driver, "merge_visits_error");
            throw new AssertionError("Visit merge process failed", e);
        }
    }


	@Then("I should see the visits merged")
	public void i_should_see_the_visits_merged() {
	    try {
	        WaitUtils.waitForElementVisible(driver, elements.confirmMergedVisitsTag, 10);
	        logger.info("Visits have been successfully merged and are visible on the page.");
	    } catch (Exception e) {
	        logger.error("Merged visits confirmation tag is not visible: " + e.getMessage());
	        ScreenshotUtils.takeScreenshot(driver, "merged_visits_not_visible");
	        throw new AssertionError("Merged visits tag not found on the page", e);
	    }
	}
	
}
