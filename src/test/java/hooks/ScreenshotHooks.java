package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.WebDriverFactory;

import java.io.ByteArrayInputStream;

public class ScreenshotHooks {

    @After
    public void afterEachScenario(Scenario scenario) {
        WebDriver driver = WebDriverFactory.getDriver();

        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName() + "_screenshot");

            // Add the screenshot to the Allure report
            Allure.addAttachment(scenario.getName() + "_screenshot", new ByteArrayInputStream(screenshot));
        }

        if (WebDriverFactory.isDriverActive()) {
            WebDriverFactory.closeDriver();
        } else {
            System.out.println("No active driver found on teardown, skipping driver closure.");
        }
    }
}
