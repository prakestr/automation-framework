package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.WebDriverFactory;

public class CucumberHooks {

    @After
    public void afterEachScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take a screenshot...
            WebDriver driver = WebDriverFactory.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName() + "_screenshot");
        }

        // Close the driver
        if (WebDriverFactory.isDriverActive()) {
            WebDriverFactory.closeDriver();
        } else {
            System.out.println("No active driver found on teardown, skipping driver closure.");
        }
    }
}
