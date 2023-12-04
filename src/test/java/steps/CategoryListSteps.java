package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.HomePage;
import utilities.WebDriverFactory;

public class CategoryListSteps {

    private WebDriver driver;
    private HomePage homePage;

    public CategoryListSteps() {
        driver = WebDriverFactory.getDriver();
        homePage = new HomePage(driver);
    }

    @Given("I navigate to the Computers category page")
    public void iNavigateToTheComputersCategoryPage() {
        homePage.navigateToComputersCategoryPage();
        Assert.assertTrue(homePage.isInComputersCategoryPage(),
                "Expected to be on the Computers category page but was on " + driver.getCurrentUrl());
    }

    @Given("I should see the categories listed from {string} through {string}")
    public void iShouldSeeTheCategoriesListedFromThrough(String startCategory, String endCategory) {
        Assert.assertTrue(homePage.areCategoriesListedFromThrough(startCategory, endCategory),
                "Categories not listed correctly from " + startCategory + " through " + endCategory);
    }

    @When("I click on the 'View all' link under the 'Manufacturers' block")
    public void iClickOnViewAllLinkUnderManufacturers() {
        homePage.navigateToComputersCategoryPage();
        Assert.assertTrue(homePage.isInComputersCategoryPage(),
                "Expected to be on the Computers category page but was on " + driver.getCurrentUrl());

        homePage.clickOnViewAllManufacturers();
    }

    @Then("I am redirected to a page listing all manufacturers")
    public void iAmRedirectedToPageListingAllManufacturers() {
        Assert.assertTrue(homePage.isOnManufacturersListPage(),
                "Expected to be on the manufacturers list page but was on " + driver.getCurrentUrl());
    }

    @Then("I verify the {string} is displayed")
    public void iVerifyTheManufacturerListIsDisplayed(String title) {
        Assert.assertTrue(homePage.isManufacturerListDisplayed(title),
                "Manufacturer list with title '" + title + "' is not displayed.");
    }

    @Given("I verify that manufacturers {string}, {string}, and {string} are displayed")
    public void iVerifyThatManufacturersAreDisplayed(String manufacturer1, String manufacturer2, String manufacturer3) {
        Assert.assertTrue(homePage.isManufacturerDisplayed(manufacturer1),
                manufacturer1 + " is not displayed on the manufacturers list page.");
        Assert.assertTrue(homePage.isManufacturerDisplayed(manufacturer2),
                manufacturer2 + " is not displayed on the manufacturers list page.");
        Assert.assertTrue(homePage.isManufacturerDisplayed(manufacturer3),
                manufacturer3 + " is not displayed on the manufacturers list page.");
    }



}
