package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends PageBase {

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='topic-block-title']/h2")
    private WebElement welcomeMessage;

    @FindBy(id = "small-searchterms")
    private WebElement searchField;

    @FindBy(linkText = "Computers")
    private WebElement computersMenuItem;

    @FindBy(css = "a.ico-cart")
    private WebElement shoppingCartLink;

    @FindBy(linkText = "View all")
    private WebElement viewAllManufacturersLink;

    @FindBy(className = "manufacturer-list-page")
    private WebElement manufacturersListPage;

    @FindBy(xpath = "//div[@class='manufacturer-item']/h2/a[contains(text(), 'Apple')]")
    private WebElement appleManufacturer;

    @FindBy(xpath = "//div[@class='manufacturer-item']/h2/a[contains(text(), 'HP')]")
    private WebElement hpManufacturer;

    @FindBy(xpath = "//div[@class='manufacturer-item']/h2/a[contains(text(), 'Nike')]")
    private WebElement nikeManufacturer;

    @FindBy(css = ".listbox ul.list li a")
    private List<WebElement> categoryLinks;

    @FindBy(css = ".manufacturer-item h2 a")
    private List<WebElement> manufacturerNames;


    // Method to search for a product by its name
    public SearchResultsPage searchForProduct(String productName) {
        searchField.sendKeys(productName);
        searchField.submit();
        return new SearchResultsPage(driver);
    }

    // Method to click on Computers menu item
    public void clickOnComputersMenuItem() {
        computersMenuItem.click();
    }

    public boolean isWelcomeMessageDisplayed() {
        return welcomeMessage.getText().trim().equals("Welcome to our store");
    }

    // Method to navigate to Computers category page
    public void navigateToComputersCategoryPage() {
        clickOnComputersMenuItem();
    }

    public WebElement getShoppingCartLink() {
        return shoppingCartLink;
    }


    // Method to verify if the user is on the Computers category page
    public boolean isInComputersCategoryPage() {
        return this.driver.getCurrentUrl().endsWith("/computers");
    }

    // Method to get the list of categories from the sidebar
    public List<String> getCategoriesList() {
        List<WebElement> categoryElements = this.driver.findElements(By.cssSelector(".listbox ul.list li a"));
        return categoryElements.stream()
                .map(WebElement::getText)
                .map(String::trim) // Trim the text to avoid whitespace issues
                .collect(Collectors.toList());
    }

    // Method to click on a product by its name
    public ProductDetailsPage clickOnProductByName(String productName) {
        // Implement the logic to click on the product by name
        // This might involve finding the product link by its text and clicking on it
        WebElement productLink = this.driver.findElements(By.xpath("//a[text()='" + productName + "']")).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Product with name '" + productName + "' not found."));
        productLink.click();
        return new ProductDetailsPage(driver);
    }

    public ShoppingCartPage clickOnShoppingCart() {
        waitForNotificationBannerToDisappear(); // Ensure the notification banner is not present
        shoppingCartLink.click();
        return new ShoppingCartPage(driver);
    }

    public void waitForNotificationBannerToDisappear() {
        By notificationBannerLocator = By.id("bar-notification");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(notificationBannerLocator));
    }


    public void waitForElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void clickOnViewAllManufacturers() {
        viewAllManufacturersLink.click();
    }
    public boolean isOnManufacturersListPage() {
        return driver.getCurrentUrl().endsWith("/manufacturer/all");
    }

    public boolean isManufacturerListDisplayed() {
        return manufacturersListPage.isDisplayed();
    }

    public boolean isOnHomePage() {
        // Check the URL or a unique element that identifies the homepage
        return driver.getCurrentUrl().equals("https://demo.nopcommerce.com/");
    }

    // New method to check if categories are listed from a starting category to an ending category
    public boolean areCategoriesListedFromThrough(String startCategory, String endCategory) {
        List<String> categories = categoryLinks.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        return categories.contains(startCategory) && categories.contains(endCategory);
    }

    // New method to verify if the manufacturer list page with the title is displayed
    public boolean isManufacturerListDisplayed(String title) {
        return manufacturersListPage.isDisplayed() && driver.findElement(By.cssSelector(".page-title h1")).getText().equals(title);
    }


    public boolean isManufacturerDisplayed(String manufacturerName) {
        switch (manufacturerName.toLowerCase()) {
            case "apple":
                return appleManufacturer.isDisplayed();
            case "hp":
                return hpManufacturer.isDisplayed();
            case "nike":
                return nikeManufacturer.isDisplayed();
            default:
                return false;
        }
    }
}
