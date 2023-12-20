package com.framework.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public WebDriver getDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            System.out.println("Instantiating a new driver instance for browser: " + browser);
            switch (browser.toLowerCase()) {
                case "firefox" -> {
                    io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
                    driverThreadLocal.set(new FirefoxDriver());
                }
                case "edge" -> {
                    io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
                    driverThreadLocal.set(new EdgeDriver());
                }
                case "chrome" -> {
                    io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                        options.addArguments("--headless");
                    }
                    driverThreadLocal.set(new ChromeDriver(options));
                }
                default -> throw new IllegalArgumentException("The browser " + browser + " is not supported.");
            }
            driverThreadLocal.get().manage().window().maximize();
        }
        return driverThreadLocal.get();
    }

    public void closeDriver() {
        if (driverThreadLocal.get() != null) {
            try {
                driverThreadLocal.get().quit();
                System.out.println("Driver closed successfully");
            } catch (Exception e) {
                System.err.println("An error occurred while closing the WebDriver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    public static boolean isDriverActive() {
        return driverThreadLocal.get() != null;
    }
}
