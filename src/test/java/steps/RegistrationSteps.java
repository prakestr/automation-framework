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
import java.util.List;
import java.util.Map;

public class RegistrationSteps {
    List<Map<String, String>> testData;

    @Before
    public void setup() throws IOException {
        String excelFilePath = "src/test/java/data/UserRegistration.xlsx";
        testData = ExcelDataReader.readExcelData(excelFilePath); // Now testData is correctly typed
    }



//    @Before
//    public void setup() throws IOException {
//        // Assumes registrationData.json is in src/test/resources/utilities
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("registrationData.json");
//        if (inputStream == null) {
//            throw new FileNotFoundException("File not found in resources directory");
//        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        testData = objectMapper.readValue(inputStream, Map.class);
//        inputStream.close();
//    }


    private WebDriver driver;
    private RegistrationPage registrationPage;

    public RegistrationSteps() {
        driver = WebDriverFactory.getDriver();
        registrationPage = new RegistrationPage(driver);
    }

    @When("I click on {string}")
    public void i_click_on(String linkText) {
        if ("Register".equals(linkText)) {
            registrationPage.navigateToRegistrationPage();
            // Additional assertion to ensure the Registration Page is displayed
            Assert.assertTrue(registrationPage.isRegisterPageDisplayed(), "Registration page is not displayed");
        }
    }

    @And("I enter the registration details")
    public void i_enter_the_registration_details() {
        Map<String, String> registrationData = testData.get(0); // If you want to use the first row of the Excel file

        // Dynamic Data Generation
        String firstName = Faker.instance().name().firstName();
        String lastName = Faker.instance().name().lastName();
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";

        // Convert the day and year to integers
        int day = (int)Double.parseDouble(registrationData.get("Day").trim()); // Trim any leading/trailing spaces and parse the double value, then cast to int
        int year = (int)Double.parseDouble(registrationData.get("Year").trim()); // Do the same for the year

        registrationPage.fillRegistrationForm(
                registrationData.get("Gender"),
                firstName,
                lastName,
                email,
                registrationData.get("Company"),
                registrationData.get("Password"),
                registrationData.get("ConfirmPassword"),
                day,
                registrationData.get("Month"),
                year
        );
    }



    @And("I click on Register button")
    public void i_click_on_button() {
        registrationPage.clickRegisterButton();

    }

    @Then("I should see {string} message")
    public void i_should_see_message(String expectedMessage) {
        String actualMessage = registrationPage.getRegistrationResult();
        Assert.assertEquals(actualMessage, expectedMessage, "The expected message was not displayed after registration.");
    }


}
