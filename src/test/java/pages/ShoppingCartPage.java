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

    // Constructor that accepts CustomWebDriverManager

    public void checkTermsOfService() {
        waitForVisibilityOfElement(termsOfServiceCheckbox);
        WebElement checkbox = driver.findElement(termsOfServiceCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void clickCheckoutButton() {
        waitForVisibilityOfElement(checkoutButton);
        driver.findElement(checkoutButton).click();
    }

    public String getCartTitle() {
        waitForVisibilityOfElement(shoppingCartTitle);
        return driver.findElement(shoppingCartTitle).getText();
    }

    public List<String> getAllProductPrices() {
        waitForVisibilityOfElement(productPrices);
        List<WebElement> elements = driver.findElements(productPrices);
        return getTextFromElements(elements);
    }

    public List<String> getAllProductQuantities() {
        waitForVisibilityOfElement(productQuantities);
        List<WebElement> elements = driver.findElements(productQuantities);
        return getTextFromElements(elements);
    }

    public List<String> getAllTotalPrices() {
        waitForVisibilityOfElement(totalPrices);
        List<WebElement> elements = driver.findElements(totalPrices);
        return getTextFromElements(elements);
    }

    public void clickRemoveButton(int index) {
        waitForVisibilityOfElement(removeButtons);
        List<WebElement> elements = driver.findElements(removeButtons);
        elements.get(index).click();
    }

    public boolean isProductInCartWithSku(String sku) {
        return skuElements.stream().anyMatch(e -> e.getText().equals(sku));
    }

    public boolean isProductInCartWithName(String productName) {
        return productElements.stream().anyMatch(e -> e.getText().equals(productName));
    }

    // Utility methods to get text from elements if needed
    private List<String> getTextFromElements(List<WebElement> elements) {
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
