package com.framework.steps;

import com.framework.base.BaseTest;
import com.framework.pages.HomePage;
import com.framework.pages.RegistrationPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.testng.Assert;
import com.framework.utilities.ExcelDataReader;
import com.github.javafaker.Faker;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class RegistrationSteps extends BaseTest {
    private List<Map<String, String>> testData;
    private static String userEmail;
    private static String userPassword;
    private RegistrationPage registrationPage;
    private HomePage homePage;

    @Before
    public void setupRegistration() throws IOException {
        super.setUp(); // Call to BaseTest's setUp
        String excelFilePath = "src/test/resources/data/UserRegistration.xlsx";
        testData = ExcelDataReader.readExcelData(excelFilePath);
        registrationPage = new RegistrationPage(driver);
        homePage = new HomePage(driver); // Initializing the HomePage with the driver from BaseTest
    }

    @Given("I am on the homepage")
    public void iAmOnTheHomepage() {
        Assert.assertTrue(homePage.isOnHomePage(),
                "Expected to be on the homepage but was on " + driver.getCurrentUrl());
    }

    @When("I click on {string}")
    public void i_click_on(String linkText) {
        registrationPage.clickOnLinkByText(linkText);
    }

    @And("I enter the registration details")
    public void iEnterTheRegistrationDetails() {
        Map<String, String> registrationData = testData.get(0);

        String firstName = Faker.instance().name().firstName();
        String lastName = Faker.instance().name().lastName();
        userEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + Instant.now().getEpochSecond() + "@example.com";
        userPassword = registrationData.get("Password");

        String day = String.valueOf((int) Double.parseDouble(registrationData.get("Day")));
        String month = registrationData.get("Month").trim();
        String year = String.valueOf((int) Double.parseDouble(registrationData.get("Year")));

        registrationPage.fillRegistrationForm(
                registrationData.get("Gender"),
                firstName,
                lastName,
                userEmail,
                registrationData.get("Company"),
                userPassword,
                registrationData.get("ConfirmPassword"),
                day,
                month,
                year
        );
        System.out.println("Day from Excel: " + day);
        System.out.println("Year from Excel: " + year);

    }

    @When("I click on Register button")
    public void iClickOnRegisterButton() {
        registrationPage.clickRegisterButton();
    }

    @Then("I should see {string} message")
    public void iShouldSeeMessage(String expectedMessage) {
        if (!registrationPage.isErrorMessageDisplayed()) {
            String actualMessage = registrationPage.getRegistrationResult();
            Assert.assertEquals(actualMessage, expectedMessage);
        } else {
            Assert.fail("The specified email already exists");
        }
    }

    @And("I click on \"Continue\" button")
    public void iClickOnContinueButton() {
        registrationPage.clickContinueButton();
    }

    @And("I click on \"Login in\" link")
    public void iClickOnLogInLink() {
        registrationPage.clickOnLoginLink();
    }

    @And("I enter Email and Password")
    public void iEnterEmailAndPassword() {
        registrationPage.enterCredentials(userEmail, userPassword);
    }

    @And("I click on \"Log In\" button")
    public void iClickOnLogInButton() {
        registrationPage.clickLoginButton();
    }

    @Then("I should see \"Log out\" link")
    public void iShouldSeeLogOutLink() {
        Assert.assertTrue(registrationPage.isLogoutLinkDisplayed(), "Log out link is not displayed");
    }

    @After
    public void cleanUp() {
        super.tearDown(); // Call to BaseTest's tearDown
    }
}
