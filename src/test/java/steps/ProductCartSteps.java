package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
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

    public ProductCartSteps() {
        driver = WebDriverFactory.getDriver();
        homePage = new HomePage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
    }

    @When("I search for {string} and add the product with name {string} to the cart")
    public void iSearchForAndAddTheProductToTheCart(String shortProductName, String productName) {
        // Search for the product and open the product details page
        ProductDetailsPage productDetailsPage = homePage.searchForProduct(shortProductName)
                .clickOnProductByName(productName);

        // Wait for the Add to Cart button to be clickable and then click it
        productDetailsPage.waitForAddToCartButton();
        productDetailsPage.addToCart();

        // Wait for the notification banner to appear and then disappear
        productDetailsPage.waitForNotificationBannerToAppear(); // If needed
        homePage.waitForNotificationBannerToDisappear();

        // Wait for the cart to update with the new product quantity
        productDetailsPage.waitForCartUpdate();

        // Click on the Shopping Cart link after ensuring the notification banner is gone
        // and the cart has been updated
        homePage.clickOnShoppingCart();
    }

    @Then("I validate that the product with SKU {string} and name {string} is in the cart")
    public void iValidateThatTheProductWithSkuAndNameIsInTheCart(String expectedSku, String expectedProductName) {
        Assert.assertTrue(shoppingCartPage.isProductInCartWithSku(expectedSku),
                "SKU '" + expectedSku + "' not found in the cart.");
        Assert.assertTrue(shoppingCartPage.isProductInCartWithName(expectedProductName),
                "Product name '" + expectedProductName + "' not found in the cart.");
    }

    @When("I proceed to checkout as a guest")
    public void iProceedToCheckoutAsAGuest() {
        shoppingCartPage.checkTermsOfService();
        shoppingCartPage.clickCheckoutButton();
    }



}
