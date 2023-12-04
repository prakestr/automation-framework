package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends PageBase {

    // Locators
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
            int day, // Changed to int
            String month,
            int year // Changed to int
    ) {
        if ("male".equalsIgnoreCase(gender)) {
            clickButton(genderMaleRadio);
        } else if ("female".equalsIgnoreCase(gender)) {
            clickButton(genderFemaleRadio);
        }

        setTextElementText(firstNameInput, firstName);
        setTextElementText(lastNameInput, lastName);

        // Handle the date of birth dropdowns with provided parameters
        select = new Select(dateOfBirthDayDropdown);
        select.selectByValue(String.valueOf(day)); // Convert int to String

        select = new Select(dateOfBirthMonthDropdown);
        select.selectByVisibleText(month);

        select = new Select(dateOfBirthYearDropdown);
        select.selectByValue(String.valueOf(year)); // Convert int to String

        setTextElementText(emailInput, email);
        setTextElementText(companyInput, company);
        setTextElementText(passwordInput, password);
        setTextElementText(confirmPasswordInput, confirmPassword);
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
}
