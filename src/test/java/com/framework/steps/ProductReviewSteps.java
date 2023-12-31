package com.framework.steps;

import com.framework.base.BaseTest;
import com.framework.pages.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class ProductReviewSteps extends BaseTest {
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductDetailsPage productDetailsPage;
    private ReviewFormPage reviewFormPage;

    @Before // Cucumber hook to run before each scenario
    public void setup() {
        super.setUp(); // Call to BaseTest's setUp
        // Initialize page objects here
        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        reviewFormPage = new ReviewFormPage(driver);
    }

    @Given("I have registered a new user")
    public void iHaveRegisteredANewUser() {
        // Registration steps...
    }

    @When("I search for {string} products and sort the results by price from low to high")
    public void iSearchForProductsAndSortByPriceFromLowToHigh(String brand) {
        homePage.searchForProduct(brand);
        searchResultsPage.sortByPriceLowToHigh();
    }

    @When("I select the product with the lowest price")
    public void iSelectTheProductWithTheLowestPrice() {
        searchResultsPage.clickOnProductWithLowestPrice();
    }

    @And("I verify the product details for {string}")
    public void iVerifyTheProductDetails(String productName) {
        Assert.assertTrue(productDetailsPage.isProductDisplayedOnDetailsPage(productName),
                "Expected product name is not displayed on the product details page.");
    }

    @And("I submit a review with title {string}, text {string}, and rating {string}")
    public void iSubmitAReviewWithTitleTextAndRating(String title, String text, String rating) {
        productDetailsPage.navigateToReviewForm();
        reviewFormPage.submitReview(title, text, rating);
    }

    @Then("the review submission should be successful")
    public void theReviewSubmissionShouldBeSuccessful() {
        Assert.assertTrue(reviewFormPage.isReviewSubmittedSuccessfully());
    }

    @And("my review with title {string} and text {string} should be displayed on the product details page")
    public void myReviewShouldBeDisplayedOnTheProductDetailsPage(String title, String text) {
        Assert.assertTrue(reviewFormPage.isReviewDisplayed(title, text),
                "Review with the specified title and text should be displayed.");
    }

    @After
    public void cleanUp() {

    }
}