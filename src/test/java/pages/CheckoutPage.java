package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends PageBase {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement firstNameInput;

    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement lastNameInput;

    @FindBy(id = "BillingNewAddress_Email")
    private WebElement emailInput;

    @FindBy(id = "BillingNewAddress_Company")
    private WebElement companyInput;

    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement countrySelect;

    @FindBy(id = "BillingNewAddress_StateProvinceId")
    private WebElement stateSelect;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement cityInput;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement address1Input;

    @FindBy(id = "BillingNewAddress_Address2")
    private WebElement address2Input;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement zipCodeInput;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement phoneNumberInput;

    @FindBy(id = "BillingNewAddress_FaxNumber")
    private WebElement faxNumberInput;


    public CheckoutPage setFirstName(String firstName) {
        firstNameInput.sendKeys(firstName);
        return this;
    }

    public CheckoutPage setLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
        return this;
    }

    public CheckoutPage setEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public CheckoutPage setCompany(String company) {
        companyInput.sendKeys(company);
        return this;
    }

    public CheckoutPage selectCountry(String country) {
        countrySelect.sendKeys(country);
        return this;
    }

    public CheckoutPage selectState(String state) {
        stateSelect.sendKeys(state);
        return this;
    }

    public CheckoutPage setCity(String city) {
        cityInput.sendKeys(city);
        return this;
    }

    public CheckoutPage setAddress1(String address1) {
        address1Input.sendKeys(address1);
        return this;
    }

    public CheckoutPage setAddress2(String address2) {
        address2Input.sendKeys(address2);
        return this;
    }

    public CheckoutPage setZipCode(String zipCode) {
        zipCodeInput.sendKeys(zipCode);
        return this;
    }

    public CheckoutPage setPhoneNumber(String phoneNumber) {
        phoneNumberInput.sendKeys(phoneNumber);
        return this;
    }

    public CheckoutPage setFaxNumber(String faxNumber) {
        faxNumberInput.sendKeys(faxNumber);
        return this;
    }

    // ... add methods for other form interactions as needed
}

