package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Base class for Page Object Model implementation
 */
public abstract class BasePage {
    
    protected WebDriver driver;
    
    /**
     * Constructor for the base page
     * 
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Gets the page title
     * 
     * @return the page title
     */
    public String getTitle() {
        return driver.getTitle();
    }
    
    /**
     * Gets the current URL
     * 
     * @return the current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
