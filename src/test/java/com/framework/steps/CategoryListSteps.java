package com.framework.steps;

import com.framework.base.BaseTest;
import com.framework.pages.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class CategoryListSteps extends BaseTest {
    private HomePage homePage;

    @Before // Cucumber hook to run before each scenario
    public void setupCategoryList() {
        super.setUp(); // Call to BaseTest's setUp
        homePage = new HomePage(driver);
    }

    @When("I navigate to the Computers category page")
    public void iNavigateToTheComputersCategoryPage() {
        homePage.navigateToComputersCategoryPage();
        assertOnPage("Computers category page", homePage.isInComputersCategoryPage());
    }

    @Then("I should see the categories listed from {string} through {string}")
    public void iShouldSeeTheCategoriesListedFromThrough(String startCategory, String endCategory) {
        Assert.assertTrue(homePage.areCategoriesListedFromThrough(startCategory, endCategory),
                "Categories not listed correctly from " + startCategory + " through " + endCategory);
    }

    @When("I click on the 'View all' link under the 'Manufacturers' block")
    public void iClickOnViewAllLinkUnderManufacturers() {
        homePage.clickOnViewAllManufacturers();
    }

    @Then("I am redirected to a page listing all manufacturers")
    public void iAmRedirectedToPageListingAllManufacturers() {
        assertOnPage("manufacturers list page", homePage.isOnManufacturersListPage());
    }

    @Then("the {string} is displayed")
    public void theManufacturerListIsDisplayed(String title) {
        Assert.assertTrue(homePage.isManufacturerListDisplayed(title),
                "Manufacturer list with title '" + title + "' is not displayed.");
    }

    @Then("manufacturers {string}, {string}, and {string} are displayed")
    public void manufacturersAreDisplayed(String manufacturer1, String manufacturer2, String manufacturer3) {
        Assert.assertTrue(homePage.isManufacturerDisplayed(manufacturer1),
                manufacturer1 + " is not displayed on the manufacturers list page.");
        Assert.assertTrue(homePage.isManufacturerDisplayed(manufacturer2),
                manufacturer2 + " is not displayed on the manufacturers list page.");
        Assert.assertTrue(homePage.isManufacturerDisplayed(manufacturer3),
                manufacturer3 + " is not displayed on the manufacturers list page.");
    }

    private void assertOnPage(String pageDescription, boolean condition) {
        Assert.assertTrue(condition,
                "Expected to be on the " + pageDescription + " but was on " + driver.getCurrentUrl());
    }

    @After
    public void cleanUp() {
        super.tearDown(); // Call to BaseTest's tearDown
    }
}
