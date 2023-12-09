package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
    // Make driverThreadLocal final since it doesn't need to be modified after initialization
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            System.out.println("Instantiating a new driver instance");
            String browser = System.getProperty("browser", "chrome").toLowerCase();
            boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
            WebDriver driver;

            // Using enhanced switch statement
            switch (browser) {
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                }
                case "edge" -> {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                }
                case "chrome" -> {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    if (headless) {
                        options.addArguments("--headless");
                    }
                    driver = new ChromeDriver(options);
                }
                default -> throw new IllegalStateException("Unexpected value: " + browser);
            }

            driver.manage().window().maximize();
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    public static void closeDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Driver closed successfully");
            } catch (Exception e) {
                System.err.println("An error occurred while closing the WebDriver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        } else {
            System.out.println("Driver was not active, so it was not closed.");
        }
    }


    public static boolean isDriverActive() {
        return driverThreadLocal.get() != null;
    }
}
