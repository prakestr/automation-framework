package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class RegistrationPage extends BasePage {

    // Locators with FindBy annotations
    @FindBy(className = "login-button")
    private WebElement loginButton;

    @FindBy(css = ".button-1.register-continue-button")
    private WebElement continueButton;

    @FindBy(linkText = "Log out")
    private WebElement logoutLink;

    @FindBy(id = "register-link")
    private WebElement registerLink;

    @FindBy(css = ".message-error.validation-summary-errors")
    private WebElement errorMessage;

    @FindBy(id = "gender-male")
    private WebElement genderMaleRadio;

    @FindBy(id = "gender-female")
    private WebElement genderFemaleRadio;

    @FindBy(id = "FirstName")
    private WebElement firstNameInput;

    @FindBy(id = "LastName")
    private WebElement lastNameInput;

    @FindBy(name = "DateOfBirthDay")
    private WebElement dateOfBirthDayDropdown;

    @FindBy(name = "DateOfBirthMonth")
    private WebElement dateOfBirthMonthDropdown;

    @FindBy(name = "DateOfBirthYear")
    private WebElement dateOfBirthYearDropdown;

    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Company")
    private WebElement companyInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(id = "ConfirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(id = "register-button")
    private WebElement registerButton;

    // Add the missing FindBy annotations
    @FindBy(className = "result")
    private WebElement registrationResult;

    @FindBy(xpath = "//div[@class='page-title']/h1")
    private WebElement registerHeader;

    // Constructor
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // Methods
    public void navigateToRegistrationPage() {
        clickButton(registerLink);
    }

    public void fillRegistrationForm(
            String gender,
            String firstName,
            String lastName,
            String email,
            String company,
            String password,
            String confirmPassword,
            String day,
            String month,
            String year
    ) {
        // Set the gender radio button
        if (gender != null) {
            WebElement genderElement = "male".equalsIgnoreCase(gender) ? genderMaleRadio : genderFemaleRadio;
            genderElement.click();
        }

        // Fill in the text fields
        setTextElementText(firstNameInput, firstName);
        setTextElementText(lastNameInput, lastName);
        setTextElementText(emailInput, email);
        setTextElementText(companyInput, company);
        setTextElementText(passwordInput, password);
        setTextElementText(confirmPasswordInput, confirmPassword);

        // Use Select for dropdowns with explicit waits
        if (day != null && !day.trim().isEmpty()) {
            WebElement dayDropdownVisible = waitForVisibilityOfElement(dateOfBirthDayDropdown);
            new Select(dayDropdownVisible).selectByValue(day);
        }

        if (month != null && !month.trim().isEmpty()) {
            WebElement monthDropdownVisible = waitForVisibilityOfElement(dateOfBirthMonthDropdown);
            new Select(monthDropdownVisible).selectByVisibleText(month);
        }

        if (year != null && !year.trim().isEmpty()) {
            WebElement yearDropdownVisible = waitForVisibilityOfElement(dateOfBirthYearDropdown);
            new Select(yearDropdownVisible).selectByValue(year);
        }
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public void submitRegistration() {
        clickButton(registerButton);
    }

    public String getRegistrationResult() {
        return getText(registrationResult);
    }

    public boolean isRegisterPageDisplayed() {
        return getText(registerHeader).equalsIgnoreCase("Register");
    }

    public void clickRegisterButton() {
        clickButton(registerButton);
    }

    public boolean isRegistrationSuccessful() {
        return isElementDisplayed(registrationResult) && getRegistrationResult().contains("Your registration completed");
    }

    public void clickLoginButton() {
        clickButton(loginButton);
    }

    public void enterCredentials(String email, String password) {
        setTextElementText(emailInput, email);
        setTextElementText(passwordInput, password);
    }

    // Method to click on a link by its text
    public void clickOnLinkByText(String linkText) {
        WebElement link = driver.findElement(By.linkText(linkText));
        clickButton(link);
    }

    // Method to click on the "Continue" button
    public void clickContinueButton() {
        clickButton(continueButton);
    }

    public boolean isLogoutLinkDisplayed() {
        return isElementDisplayed(logoutLink);
    }

    private boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private String getText(WebElement element) {
        return element.isDisplayed() ? element.getText() : "";
    }
}
