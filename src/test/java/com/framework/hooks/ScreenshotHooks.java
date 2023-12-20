package com.framework.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.framework.manager.WebDriverManager;

import java.io.ByteArrayInputStream;

public class ScreenshotHooks {

    private WebDriverManager webDriverManager = new WebDriverManager();

    @After
    public void afterEachScenario(Scenario scenario) {
        WebDriver driver = webDriverManager.getDriver("chrome"); // Default to Chrome, adjust as needed

        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName() + "_screenshot");

            // Add the screenshot to the Allure report
            Allure.addAttachment(scenario.getName() + "_screenshot", new ByteArrayInputStream(screenshot));
        }

        if (webDriverManager.isDriverActive()) {
            webDriverManager.closeDriver();
        } else {
            System.out.println("No active driver found on teardown, skipping driver closure.");
        }
    }
}
