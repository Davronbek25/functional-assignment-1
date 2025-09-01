package expresscart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for ExpressCart admin login page
 */
public class AdminLoginPage extends BasePage {
    
    private static final String ADMIN_URL = "http://localhost:3000/admin/login";
    
    @FindBy(id = "email")
    private WebElement emailField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "loginForm")
    private WebElement loginForm;
    
    /**
     * Constructor for the admin login page
     * 
     * @param driver WebDriver instance
     */
    public AdminLoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigates to the admin login page
     * 
     * @return this page object
     */
    public AdminLoginPage navigateTo() {
        driver.get(ADMIN_URL);
        return this;
    }
    
    /**
     * Logs in with the provided credentials
     * 
     * @param email admin email
     * @param password admin password
     * @return the dashboard page
     */
    public AdminDashboardPage login(String email, String password) {
        emailField.clear();
        emailField.sendKeys(email);
        
        passwordField.clear();
        passwordField.sendKeys(password);
        
        loginForm.click();

        // Wait for the dashboard page to load
        WebDriverUtils.waitForVisibility(driver, By.xpath("/html/body/div[2]/div/main/div[1]/h2"), 2);
        
        return new AdminDashboardPage(driver);
    }
    
    /**
     * Checks if an error message is displayed
     * 
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorMessage = driver.findElement(By.className("alert-danger"));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
