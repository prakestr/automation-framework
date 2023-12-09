package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartPage extends PageBase {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "td.sku .sku-number")
    private List<WebElement> skuElements;

    @FindBy(css = "td.product .product-name")
    private List<WebElement> productElements;

    private By termsOfServiceCheckbox = By.id("termsofservice");
    private By checkoutButton = By.id("checkout");
    private By shoppingCartTitle = By.xpath("//div[@class='page-title']/h1");
    private By productPrices = By.cssSelector("td.unit-price");
    private By productQuantities = By.cssSelector("td.quantity");
    private By totalPrices = By.cssSelector("td.subtotal");
    private By removeButtons = By.cssSelector("td.remove-from-cart button");

    public void checkTermsOfService() {
        waitForElementToBeClickable(termsOfServiceCheckbox);
        driver.findElement(termsOfServiceCheckbox).click();
    }

    public void clickCheckoutButton() {
        waitForElementToBeClickable(checkoutButton);
        moveToElementAndClick(checkoutButton);
    }

    public String getCartTitle() {
        waitForVisibilityOfElement(shoppingCartTitle);
        return driver.findElement(shoppingCartTitle).getText();
    }

    public List<String> getAllProductPrices() {
        return getTextFromElements(productPrices);
    }

    public List<String> getAllProductQuantities() {
        return getTextFromElements(productQuantities);
    }

    public List<String> getAllTotalPrices() {
        return getTextFromElements(totalPrices);
    }

    public void clickRemoveButton(int index) {
        List<WebElement> elements = driver.findElements(removeButtons);
        waitForElementToBeClickable(elements.get(index));
        moveToElementAndClick(elements.get(index));
    }

    public boolean isProductInCartWithSku(String sku) {
        return skuElements.stream().anyMatch(e -> e.getText().equals(sku));
    }

    public boolean isProductInCartWithName(String productName) {
        return productElements.stream().anyMatch(e -> e.getText().equals(productName));
    }

    // Utility methods to get text from elements if needed
    private List<String> getTextFromElements(By locator) {
        waitForVisibilityOfElement(locator);
        List<WebElement> elements = driver.findElements(locator);
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
