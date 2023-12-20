package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected JavascriptExecutor jse;
    protected Actions actions;
    protected WebDriverWait wait;

    // Create constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jse = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    // Make methods protected so they can be accessed by subclasses
    protected void clickButton(WebElement button) {
        waitForElementToBeClickable(button);
        button.click();
    }

    public void clickOnLoginLink() {
        By loginLinkLocator = By.cssSelector("a.ico-login");
        waitForVisibilityOfElement(loginLinkLocator);
        waitForElementToBeClickable(loginLinkLocator);
        WebElement loginLink = driver.findElement(loginLinkLocator);
        loginLink.click();
    }

    protected void setTextElementText(WebElement textElement, String value) {
        clearText(textElement); // Clear the text before sending new text
        textElement.sendKeys(value);
    }

    public void scrollToBottom() {
        jse.executeScript("scrollBy(0,2500)");
    }

    public void clearText(WebElement element) {
        element.clear();
    }

    protected void clickWhenClickable(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public WebElement waitForVisibilityOfElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForVisibilityOfElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void moveToElementAndClick(By locator) {
        WebElement element = driver.findElement(locator);
        actions.moveToElement(element).click().perform();
    }

    public void moveToElementAndClick(WebElement element) {
        actions.moveToElement(element).click().perform();
    }
}
