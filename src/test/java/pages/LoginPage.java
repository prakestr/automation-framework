package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(css = ".button-1.checkout-as-guest-button")
    private WebElement checkoutAsGuestButton;


    public CheckoutPage clickCheckoutAsGuest() {
        checkoutAsGuestButton.click();
        // ... other actions ...
        return new CheckoutPage(driver);
    }
    }


