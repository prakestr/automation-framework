package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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
        List<WebElement> productTitles = driver.findElements(By.cssSelector(".item-grid .product-item .product-title a"));
        if (!productTitles.isEmpty()) {
            WebElement firstProductTitle = productTitles.get(0);
            scrollToElementAndClick(firstProductTitle); // Custom method to scroll and click
        } else {
            throw new IllegalStateException("No products found to select the lowest price.");
        }
    }

    // Scrolls to the element and uses JavaScript to click
    private void scrollToElementAndClick(WebElement element) {
        waitForElementToBeClickable(element); // Wait for the element to be clickable
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            element.click(); // Attempt to click normally
        } catch (ElementClickInterceptedException e) {
            // If normal click fails, use JavaScript to click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
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

        new WebDriverWait(driver, Duration.ofSeconds(30)).until((WebDriver d) -> {
            try {
                return arePricesSorted(); // Refactored into a separate method
            } catch (StaleElementReferenceException e) {
                return false; // If caught, the DOM has updated and the wait will retry.
            }
        });
    }

    // Helper method to check if prices are sorted low to high
    private boolean arePricesSorted() {
        // Retrieve fresh references to the price elements
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".actual-price"));
        List<Float> priceValues = priceElements.stream()
                .map(WebElement::getText)
                .map(text -> text.replace("$", "").replace(",", "").trim())
                .map(Float::parseFloat)
                .collect(Collectors.toList());

        // Check if the prices are sorted
        for (int i = 0; i < priceValues.size() - 1; i++) {
            if (priceValues.get(i) > priceValues.get(i + 1)) {
                return false; // Prices are not sorted
            }
        }
        return true; // Prices are sorted
    }
}