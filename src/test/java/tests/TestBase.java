// TestBase.java
package tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class TestBase {

    @Parameters({"browser"})
    @BeforeSuite(alwaysRun = true)
    public void setUp(String browser) {
        System.setProperty("browser", browser);
        // Additional setup can go here
    }

    // If you need to perform setup before each test instead of before the suite,
    // you can use the following:
    // @Parameters({"browser"})
    // @BeforeTest(alwaysRun = true)
    // public void setUpTest(String browser) {
    //     System.setProperty("browser", browser);
    //     // Additional setup for each test can go here
    // }
}
