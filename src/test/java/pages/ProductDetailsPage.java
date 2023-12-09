package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductDetailsPage extends PageBase {

    @FindBy(css = ".add-to-cart-panel button[type='button']")
    private WebElement addToCartButton;

    @FindBy(css = ".cart-qty")
    private WebElement cartQuantity;

    @FindBy(css = ".product-name h1")
    private WebElement productName;

    @FindBy(linkText = "Add your review")
    private WebElement addYourReviewLink;


    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void addToCart() {
        waitForAddToCartButton();
        addToCartButton.click();
    }

    public void waitForAddToCartButton() {
        waitForElementToBeClickable(addToCartButton);
    }

    public void waitForCartUpdate() {
        // Assuming the cart quantity is indicated by some number in the cartQuantity element
        waitForNonZeroCartQuantity();
    }

    public boolean isProductDisplayedOnDetailsPage(String expectedProductName) {
        // Wait for the product name element to be visible
        wait.until(ExpectedConditions.visibilityOf(productName));
        // Return true if the product name matches the expected name
        return productName.getText().equalsIgnoreCase(expectedProductName);
    }

    public ReviewFormPage navigateToReviewForm() {
        waitForElementToBeClickable(addYourReviewLink);
        addYourReviewLink.click();
        return new ReviewFormPage(driver);
    }


    public void waitForNotificationBannerToAppear() {
        By notificationBannerLocator = By.id("bar-notification");
        wait.until(ExpectedConditions.visibilityOfElementLocated(notificationBannerLocator));
    }


    public String getProductName() {
        return productName.getText();
    }


    private void waitForNonZeroCartQuantity() {
        // This method will wait until the text of cartQuantity matches a non-zero digit, indicating the cart is not empty.
        wait.until(ExpectedConditions.textMatches(
                By.cssSelector(".cart-qty"), java.util.regex.Pattern.compile("[1-9][0-9]*")
        ));
    }
}
