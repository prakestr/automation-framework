package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends PageBase {

    // Locators
    @FindBy(className = "login-button")
    private WebElement loginButton;

    @FindBy(linkText = "Log out")
    private WebElement logoutLink;
    @FindBy(linkText = "Register")
    private WebElement registerLink;

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

    @FindBy(className = "register-continue-button")
    private WebElement continueButton;

    @FindBy(className = "message-error")
    private WebElement errorMessage;

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
        waitForVisibilityOfElement(registerHeader);
    }

    public void fillRegistrationForm(
            String gender,
            String firstName,
            String lastName,
            String email,
            String company,
            String password,
            String confirmPassword,
            int day, // Continue to use int for day
            String month,
            int year // Continue to use int for year
    ) {
        if ("male".equalsIgnoreCase(gender)) {
            clickButton(genderMaleRadio);
        } else if ("female".equalsIgnoreCase(gender)) {
            clickButton(genderFemaleRadio);
        }

        setTextElementText(firstNameInput, firstName);
        setTextElementText(lastNameInput, lastName);

        // Handle the date of birth dropdowns with provided parameters
        new Select(dateOfBirthDayDropdown).selectByValue(String.valueOf(day)); // Convert int day to String
        new Select(dateOfBirthMonthDropdown).selectByVisibleText(month);
        new Select(dateOfBirthYearDropdown).selectByValue(String.valueOf(year)); // Convert int year to String

        setTextElementText(emailInput, email);
        setTextElementText(companyInput, company);
        setTextElementText(passwordInput, password);
        setTextElementText(confirmPasswordInput, confirmPassword);
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }



    public void submitRegistration() {
        waitForElementToBeClickable(registerButton); // Now using the method from PageBase
        registerButton.click(); // Click the button directly or use clickButton method if it's defined in PageBase
    }

    public String getRegistrationResult() {
        // Adjust the waiting strategy as necessary, for example, wait for text to be present:
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("result"), "Your registration completed"));
        return registrationResult.getText();
    }


    public boolean isRegisterPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(registerHeader)).getText().equalsIgnoreCase("Register");
    }

    public void clickRegisterButton() {
        // Locate the button by its ID
        WebElement registerButton = driver.findElement(By.id("register-button"));
        // Click the button
        registerButton.click();
    }

    public void clickOnLinkByText(String linkText) {
        WebElement link = driver.findElement(By.linkText(linkText));
        clickButton(link);
    }

    public void clickLoginButton() {
        clickButton(loginButton);
    }

    public void enterCredentials(String email, String password) {
        setTextElementText(emailInput, email);
        setTextElementText(passwordInput, password);
    }

    public boolean isLogoutLinkDisplayed() {
        return logoutLink.isDisplayed();
    }

    public void clickContinueButton() {
        clickButton(continueButton); // Utilize the clickButton method to interact with the Continue button
    }
}
