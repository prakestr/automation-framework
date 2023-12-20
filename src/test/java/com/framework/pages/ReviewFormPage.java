package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewFormPage extends BasePage {

    @FindBy(id = "AddProductReview_Title")
    private WebElement reviewTitleInput;

    @FindBy(id = "AddProductReview_ReviewText")
    private WebElement reviewTextInput;

    @FindBy(name = "AddProductReview.Rating")
    private List<WebElement> ratingOptions;

    @FindBy(name = "add-review")
    private WebElement submitReviewButton;

    @FindBy(css = ".result")
    private WebElement reviewSuccessMessage;

    @FindBy(css = ".review-title strong")
    private WebElement displayedReviewTitle;

    @FindBy(css = ".review-text div.text-body")
    private WebElement displayedReviewText;

    // Constructor
    public ReviewFormPage(WebDriver driver) {
        super(driver);
    }

    // Method to submit a review
    // In ReviewFormPage.java

    public void submitReview(String title, String text, String rating) {
        // Wait for the title input to be visible and interactable
        waitForVisibilityOfElement(reviewTitleInput);
        waitForElementToBeClickable(reviewTitleInput);

        setTextElementText(reviewTitleInput, title);
        setTextElementText(reviewTextInput, text);
        selectRating(rating); // Call the new method to select the rating

        // Wait for the submit button to be visible and interactable
        waitForVisibilityOfElement(submitReviewButton);
        waitForElementToBeClickable(submitReviewButton);

        clickButton(submitReviewButton);
    }



    public void selectRating(String ratingLabel) {
        // Map the string labels to numerical values
        Map<String, String> ratingsMap = new HashMap<>();
        ratingsMap.put("Bad", "1");
        ratingsMap.put("Not good", "2");
        ratingsMap.put("Not bad but also not excellent", "3");
        ratingsMap.put("Good", "4");
        ratingsMap.put("Excellent", "5");

        // Get the corresponding value for the label
        String ratingValue = ratingsMap.getOrDefault(ratingLabel, "0"); // Defaults to "0" if not found

        // Select the radio button with that value
        WebElement radioButton = driver.findElement(By.cssSelector("input[name='AddProductReview.Rating'][value='" + ratingValue + "']"));
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
    }


    public boolean isReviewSubmittedSuccessfully() {
        waitForVisibilityOfElement(reviewSuccessMessage);
        return reviewSuccessMessage.getText().contains("Product review is successfully added.");
    }

    public boolean isReviewDisplayed(String title, String text) {
        try {
            waitForVisibilityOfElement(displayedReviewTitle);
            waitForVisibilityOfElement(displayedReviewText);
            return displayedReviewTitle.getText().equalsIgnoreCase(title) && displayedReviewText.getText().equalsIgnoreCase(text);
        } catch (Exception e) {
            // Handle the exception if elements are not found or any other issue
            return false;
        }
    }
}
