package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageBase {
    protected WebDriver driver;
    public JavascriptExecutor jse;
    public Actions actions;
    public WebDriverWait wait;

    // Create constructor
    public PageBase(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jse = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    protected static void clickButton(WebElement button) {
        button.click();
    }

    public void clickOnLoginLink() {
        By loginLinkLocator = By.cssSelector("a.ico-login");
        waitForVisibilityOfElement(loginLinkLocator); // Wait for the login link to be visible
        waitForElementToBeClickable(loginLinkLocator); // Additionally, wait for it to be clickable
        WebElement loginLink = driver.findElement(loginLinkLocator);
        loginLink.click();
    }



    protected static void setTextElementText(WebElement textElement, String value) {
        textElement.sendKeys(value);
    }

    public void scrollToBottom() {
        jse.executeScript("scrollBy(0,2500)");
    }

    public void clearText(WebElement element) {
        element.clear();
    }

    public void waitForVisibilityOfElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForVisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
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
