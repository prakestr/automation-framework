package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.RegistrationPage;
import utilities.ExcelDataReader;
import utilities.WebDriverFactory;
import com.github.javafaker.Faker;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class RegistrationSteps {
    List<Map<String, String>> testData;

    private static String userEmail;
    private static String userPassword;

    @Before
    public void setup() throws IOException {
        String excelFilePath = "src/test/java/data/UserRegistration.xlsx";
        testData = ExcelDataReader.readExcelData(excelFilePath);
    }

    private WebDriver driver;
    private RegistrationPage registrationPage;

    public RegistrationSteps() {
        driver = WebDriverFactory.getDriver();
        registrationPage = new RegistrationPage(driver);
    }

    @When("I click on {string}")
    public void i_click_on(String linkText) {
        registrationPage.clickOnLinkByText(linkText);
    }

    @And("I enter the registration details")
    public void i_enter_the_registration_details() {
        Map<String, String> registrationData = testData.get(0);

        String firstName = Faker.instance().name().firstName();
        String lastName = Faker.instance().name().lastName();
        // Append a timestamp to the email to make it unique
        userEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + Instant.now().getEpochSecond() + "@example.com";
        userPassword = registrationData.get("Password");

        int day = (int)Double.parseDouble(registrationData.get("Day").trim());
        int year = (int)Double.parseDouble(registrationData.get("Year").trim());

        registrationPage.fillRegistrationForm(
                registrationData.get("Gender"),
                firstName,
                lastName,
                userEmail,
                registrationData.get("Company"),
                userPassword,
                registrationData.get("ConfirmPassword"),
                day,
                registrationData.get("Month"),
                year
        );
    }

    @And("I click on Register button")
    public void i_click_on_register_button() {
        registrationPage.clickRegisterButton();
    }

    @Then("I should see {string} message")
    public void i_should_see_message(String expectedMessage) {
        if (!registrationPage.isErrorMessageDisplayed()) {
            String actualMessage = registrationPage.getRegistrationResult();
            Assert.assertEquals(actualMessage, expectedMessage);
        } else {
            // Handle the case where the error message is displayed
            Assert.fail("The specified email already exists");
        }
    }


    @And("I click on \"Continue\" button")
    public void i_click_on_continue_button() {
        registrationPage.clickContinueButton();
    }

    @And("I click on \"Login in\" link")
    public void i_click_on_log_in_link() {
        // Assuming registrationPage is an instance of a class that extends PageBase
        registrationPage.clickOnLoginLink();
    }


    @And("I enter Email and Password")
    public void i_enter_email_and_password() {
        registrationPage.enterCredentials(userEmail, userPassword);
    }

    @And("I click on \"Log In\" button")
    public void i_click_on_log_in_button() {
        registrationPage.clickLoginButton();
    }

    @Then("I should see \"Log out\" link")
    public void i_should_see_log_out_link() {
        Assert.assertTrue(registrationPage.isLogoutLinkDisplayed(), "Log out link is not displayed");
    }
}
