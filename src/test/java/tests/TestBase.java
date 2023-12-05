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


}
