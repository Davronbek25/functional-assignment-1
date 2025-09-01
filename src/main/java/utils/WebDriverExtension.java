package utils;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

/**
 * JUnit 5 extension to manage WebDriver lifecycle
 */
public class WebDriverExtension implements BeforeEachCallback, AfterEachCallback {
    
    private WebDriver driver;
    
    @Override
    public void beforeEach(ExtensionContext context) {
        // Create a new WebDriver instance before each test
        driver = WebDriverSetup.setupChromeDriver(false);
        
        // Store the driver in the test instance
        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof WebDriverAware) {
            ((WebDriverAware) testInstance).setDriver(driver);
        }
    }
    
    @Override
    public void afterEach(ExtensionContext context) {
        // Quit the WebDriver after each test
        if (driver != null) {
            driver.quit();
        }
    }
    
    /**
     * Interface to be implemented by test classes that need a WebDriver
     */
    public interface WebDriverAware {
        void setDriver(WebDriver driver);
    }
}
