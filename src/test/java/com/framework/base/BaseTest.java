package com.framework.base;

import com.framework.manager.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.framework.utilities.ConfigLoader;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    private ConfigLoader configLoader;

    public void setUp() {
        configLoader = new ConfigLoader();
        String browser = configLoader.getBrowser();
        String testUrl = configLoader.getTestUrl();
        driver = new WebDriverManager().getDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);
        navigateTo(testUrl);
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit(); // This will close the browser window
            driver = null; // Set the driver to null to clean up the driver instance
        }
    }


    // Navigate to a specific URL
    protected void navigateTo(String url) {
        driver.get(url);
    }

    // Wait for a specific element to be visible
    protected WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Click on an element using Actions
    protected void clickUsingActions(WebElement element) {
        actions.click(element).perform();
    }

    // Send keys to an element using Actions
    protected void sendKeysUsingActions(WebElement element, String keysToSend) {
        actions.sendKeys(element, keysToSend).perform();
    }

    // Perform a hover-over action on an element
    protected void hoverOverElement(WebElement element) {
        actions.moveToElement(element).perform();
    }

    // Add a method to click on elements with timeout and exception handling
    protected void clickElement(WebElement element) {
        try {
            waitForElementVisible(element);
            element.click();
        } catch (Exception e) {
            // Log the exception or handle it according to your framework's guidelines
            throw e; // Re-throwing the exception if it needs to be handled upstream
        }
    }

    // More utility methods can be added as needed...
}
