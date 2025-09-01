package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;

/**
 * Page object for MediaWiki create account page
 */
public class CreateAccountPage extends BasePage {
    
    @FindBy(id = "wpName2")
    private WebElement usernameField;
    
    @FindBy(id = "wpPassword2")
    private WebElement passwordField;
    
    @FindBy(id = "wpRetype")
    private WebElement confirmPasswordField;
    
    @FindBy(id = "wpRealName")
    private WebElement realName;
    
    @FindBy(id = "wpCreateaccount")
    private WebElement createAccountButton;
    
    /**
     * Constructor for the create account page
     * 
     * @param driver WebDriver instance
     */
    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the create account form
     * 
     * @param username user's username
     * @param password user's password
     * @param name user's name
     * @return this page object
     */
    public CreateAccountPage fillForm(String username, String password, String name) {
        usernameField.clear();
        usernameField.sendKeys(username);
        
        passwordField.clear();
        passwordField.sendKeys(password);
        
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(password);
        
        realName.clear();
        realName.sendKeys(name);
        
        return this;
    }
    
    /**
     * Submits the form to create a new account
     * 
     * @return the main page
     */
    public SpecialPage submitForm() {
        createAccountButton.click();
        return new SpecialPage(driver);
    }
    
    /**
     * Checks if an error message is displayed
     * 
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement firstHeading = driver.findElement(By.xpath("//h1[contains(., 'Create account')]"));
            return firstHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
