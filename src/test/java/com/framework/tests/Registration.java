package com.framework.tests;

import com.framework.base.BaseTest;
import com.framework.pages.HomePage;
import com.framework.pages.RegistrationPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.framework.utilities.ExcelDataReader;
import com.github.javafaker.Faker;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class Registration extends BaseTest {
    private List<Map<String, String>> testData;
    private static String userEmail;
    private static String userPassword;
    private RegistrationPage registrationPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupRegistration() throws IOException {
        super.setUp(); // Initializes the driver, wait, and actions from BaseTest
        String excelFilePath = "src/test/resources/data/UserRegistration.xlsx";
        testData = ExcelDataReader.readExcelData(excelFilePath);
        registrationPage = new RegistrationPage(driver);
        homePage = new HomePage(driver); // Initialize HomePage with the driver
    }

    @Test
    public void testRegistrationAndLogin() {
        assertOnHomepage();
        clickRegisterLink();
        enterRegistrationDetails();
        clickRegisterButton();
        assertRegistrationSuccess("Your registration completed");
        clickContinueButton();
        clickLoginLink();
        enterEmailAndPassword();
        clickLoginButton();
        assertLogoutLinkVisible();
    }

    private void assertOnHomepage() {
        Assert.assertTrue(homePage.isOnHomePage(),
                "Expected to be on the homepage but was on " + driver.getCurrentUrl());
    }

    private void clickRegisterLink() {
        registrationPage.clickOnLinkByText("Register");
    }

    private void enterRegistrationDetails() {
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

    }

    private void clickRegisterButton() {
        registrationPage.clickRegisterButton();
    }

    private void assertRegistrationSuccess(String expectedMessage) {
        String actualMessage = registrationPage.getRegistrationResult();
        Assert.assertEquals(actualMessage, expectedMessage,
                "The registration message did not match the expected value.");
    }

    private void clickContinueButton() {
        registrationPage.clickContinueButton();
    }

    private void clickLoginLink() {
        registrationPage.clickOnLoginLink();
    }

    private void enterEmailAndPassword() {
        registrationPage.enterCredentials(userEmail, userPassword);
    }

    private void clickLoginButton() {
        registrationPage.clickLoginButton();
    }

    private void assertLogoutLinkVisible() {
        Assert.assertTrue(registrationPage.isLogoutLinkDisplayed(),
                "Logout link should be displayed after successful login.");
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown(); // Calls the tearDown method from BaseTest
    }
}
