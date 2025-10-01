package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Utility class for WebDriver setup and configuration
 */
public class WebDriverSetup {
    
    /**
     * Creates and returns a configured Chrome WebDriver instance
     * 
     * @param headless whether to run in headless mode
     * @return configured WebDriver instance
     */
    public static WebDriver setupChromeDriver(boolean headless) {
        // Automatically setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        
        return new ChromeDriver(options);
    }
    
    /**
     * Creates and returns a configured Firefox WebDriver instance
     * 
     * @param headless whether to run in headless mode
     * @return configured WebDriver instance
     */
    public static WebDriver setupFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }
}
