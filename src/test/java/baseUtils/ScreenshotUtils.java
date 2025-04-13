package baseUtils;

import org.openqa.selenium.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    // Base directory where screenshots will be saved
    private static final String SCREENSHOT_DIR = "screenshots/";

    // Static method to take screenshot with a custom name
    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);

            // Append timestamp to avoid overwriting
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = SCREENSHOT_DIR + screenshotName + "_" + timestamp + ".png";

            File destFile = new File(filename);
            FileUtils.copyFile(srcFile, destFile);

            System.out.println("Screenshot saved: " + filename);
        } catch (IOException | WebDriverException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
