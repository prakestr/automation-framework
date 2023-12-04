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
