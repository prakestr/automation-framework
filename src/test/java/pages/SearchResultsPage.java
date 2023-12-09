package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultsPage extends PageBase {

    @FindBy(css = ".actual-price")
    private List<WebElement> productPrices;

    @FindBy(css = ".product-item .product-title a")
    private List<WebElement> productLinks;

    @FindBy(id = "products-orderby")
    private WebElement sortByDropdown;

    // Constructor
    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }


    // Method to click on a product by its name
    public ProductDetailsPage clickOnProductByName(String productName) {
        for (WebElement productLink : productLinks) {
            if (productLink.getText().equalsIgnoreCase(productName)) {
                waitForElementToBeClickable(productLink);
                productLink.click();
                return new ProductDetailsPage(driver);
            }
        }
        throw new IllegalStateException("Product with name '" + productName + "' not found.");
    }


    public void clickOnProductWithLowestPrice() {
        // Retry the operation to handle potential stale element references
        retryingFindClick(By.cssSelector(".item-grid .product-item .product-title a"));
    }

    // Method that retries finding and clicking on the element to avoid stale references
    private void retryingFindClick(By by) {
        final int retries = 5;
        for (int i = 0; i < retries; i++) {
            try {
                List<WebElement> productTitles = driver.findElements(by);
                if (!productTitles.isEmpty()) {
                    WebElement firstProductTitle = productTitles.get(0);
                    waitForElementToBeClickable(firstProductTitle); // Ensure the element is clickable
                    firstProductTitle.click();
                    return; // Click successful, exit method
                }
            } catch (StaleElementReferenceException e) {
                // The element reference is stale, retry finding the element
                System.out.println("Trying to recover from a stale element :" + e.getMessage());
            }
            // Wait a bit before retrying
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }
        throw new IllegalStateException("Could not click on the first product title after multiple retries.");
    }

    private List<Float> getPriceValues() {
        // Ensure the productPrices elements are visible before extracting text
        wait.until(ExpectedConditions.visibilityOfAllElements(productPrices));

        // Extract the price values and convert them to Floats
        return productPrices.stream()
                .map(WebElement::getText)
                .map(text -> text.replace("$", "").replace(",", "").trim())
                .map(Float::parseFloat)
                .collect(Collectors.toList());
    }


    // Method to sort search results by price from low to high
    public void sortByPriceLowToHigh() {
        Select sortBy = new Select(sortByDropdown);
        sortBy.selectByValue("10"); // Assuming '10' is the value for 'Price: Low to High'

        // Wait for the sort operation to reflect on the UI and assert the sorted order
        wait.until(driver -> {
            // Retrieve fresh references to the price elements
            List<WebElement> priceElements = driver.findElements(By.cssSelector(".actual-price"));

            // Extract the price values and convert them to Floats
            List<Float> priceValues = priceElements.stream()
                    .map(WebElement::getText)
                    .map(text -> text.replace("$", "").replace(",", "").trim())
                    .map(Float::parseFloat)
                    .collect(Collectors.toList());

            // Check if the prices are sorted
            for (int i = 0; i < priceValues.size() - 1; i++) {
                if (priceValues.get(i) > priceValues.get(i + 1)) {
                    return false; // Prices are not sorted, so wait
                }
            }
            return true; // Prices are sorted
        });
    }


}
