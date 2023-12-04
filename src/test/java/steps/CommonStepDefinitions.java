package steps;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.HomePage;
import utilities.WebDriverFactory;

public class CommonStepDefinitions {
    WebDriver driver = WebDriverFactory.getDriver();
    HomePage homePage = new HomePage(driver);

    @Given("I am on the homepage")
    public void iAmOnTheHomepage() {
        driver.get("https://demo.nopcommerce.com/");
        Assert.assertTrue(homePage.isOnHomePage(),
                "Expected to be on the homepage but was on " + driver.getCurrentUrl());
    }


}
