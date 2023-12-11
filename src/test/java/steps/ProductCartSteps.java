package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.HomePage;
import pages.ProductDetailsPage;
import pages.ShoppingCartPage;
import utilities.WebDriverFactory;

public class ProductCartSteps {
    private WebDriver driver;
    private HomePage homePage;
    private ShoppingCartPage shoppingCartPage;
    private ProductDetailsPage productDetailsPage;

    @Before
    public void setup() {
        driver = WebDriverFactory.getDriver();
        homePage = new HomePage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
    }

    @After
    public void teardown() {
        WebDriverFactory.closeDriver();
    }

    @When("I search for {string} and add the product with name {string} to the cart")
    public void iSearchForAndAddTheProductToTheCart(String shortProductName, String productName) {
        productDetailsPage = homePage.searchForProduct(shortProductName)
                .clickOnProductByName(productName);
        addProductToCart();
    }

    private void addProductToCart() {
        productDetailsPage.addToCart();
        productDetailsPage.waitForCartUpdate();
        homePage.clickOnShoppingCart();
    }

    @Then("I validate that the product with SKU {string} and name {string} is in the cart")
    public void iValidateThatTheProductWithSkuAndNameIsInTheCart(String expectedSku, String expectedProductName) {
        assertProductInCart(expectedSku, expectedProductName);
    }

    private void assertProductInCart(String expectedSku, String expectedProductName) {
        Assert.assertTrue(shoppingCartPage.isProductInCartWithSku(expectedSku),
                "SKU '" + expectedSku + "' not found in the cart.");
        Assert.assertTrue(shoppingCartPage.isProductInCartWithName(expectedProductName),
                "Product name '" + expectedProductName + "' not found in the cart.");
    }

    @When("I proceed to checkout as a guest")
    public void iProceedToCheckoutAsAGuest() {
        shoppingCartPage.proceedToCheckoutAsGuest();
    }
}
