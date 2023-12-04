package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends PageBase {

    @FindBy(css = ".product-item .product-title a")
    private List<WebElement> productLinks;

    // Constructor
    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

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
}
