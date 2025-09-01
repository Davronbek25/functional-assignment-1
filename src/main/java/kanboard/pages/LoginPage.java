package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard login page
 */
public class LoginPage extends BasePage {
    
    private static final String LOGIN_URL = "http://localhost:8081/";
    
    @FindBy(id = "form-username")
    private WebElement usernameField;
    
    @FindBy(id = "form-password")
    private WebElement passwordField;
    
    @FindBy(css = "button[type='submit']")
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
     * @return the dashboard page
     */
    public LoginPage login(String username, String password) {
        usernameField.clear();
        usernameField.sendKeys(username);
        
        passwordField.clear();
        passwordField.sendKeys(password);
        
        return this;
    }

    public DashboardPage clickLoginButton() {
        
        loginButton.click();
        
        return new DashboardPage(driver);
    }
    
    /**
     * Checks if an error message is displayed
     * 
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorMessage = driver.findElement(By.className("alert-error"));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
