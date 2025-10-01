package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for MediaWiki login page
 */
public class LoginPage extends BasePage {
    
    private static final String LOGIN_URL = "http://localhost:3080/mediawiki/index.php?title=Special:UserLogin";
    
    @FindBy(id = "wpName1")
    private WebElement usernameField;
    
    @FindBy(id = "wpPassword1")
    private WebElement passwordField;
    
    @FindBy(id = "wpLoginAttempt")
    private WebElement loginButton;
    
    /**
     * Constructor for the login page
     * 
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigates to the login page
     * 
     * @return this page object
     */
    public LoginPage navigateTo() {
        driver.get(LOGIN_URL);
        return this;
    }
    
    /**
     * Logs in with the provided credentials
     * 
     * @param username user's username
     * @param password user's password
     * @return the main page
     */
    public AdminDashboardPage login(String username, String password) {
        usernameField.clear();
        usernameField.sendKeys(username);
        
        passwordField.clear();
        passwordField.sendKeys(password);
        
        loginButton.click();
        
        return new AdminDashboardPage(driver);
    }
    
    /**
     * Checks if an error message is displayed
     * 
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorMessage = driver.findElement(By.className("mw-message-box-error"));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
