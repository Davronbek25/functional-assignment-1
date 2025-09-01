package joomla.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;

/**
 * Page object for Joomla new user form page
 */
public class NewUserFormPage extends BasePage {
    
    @FindBy(id = "jform_name")
    private WebElement nameField;
    
    @FindBy(id = "jform_username")
    private WebElement usernameField;
    
    @FindBy(id = "jform_password")
    private WebElement passwordField;
    
    @FindBy(id = "jform_password2")
    private WebElement confirmPasswordField;
    
    @FindBy(id = "jform_email")
    private WebElement emailField;
    
    @FindBy(xpath = "//*[@id=\"toolbar-save\"]/button")
    private WebElement saveButton;
    
    /**
     * Constructor for the new user form page
     * 
     * @param driver WebDriver instance
     */
    public NewUserFormPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the new user form
     * 
     * @param name user's full name
     * @param username user's username
     * @param password user's password
     * @param email user's email
     * @return this page object
     */
    public NewUserFormPage fillForm(String name, String username, String password, String email) {
        nameField.clear();
        nameField.sendKeys(name);
        
        usernameField.clear();
        usernameField.sendKeys(username);
        
        passwordField.clear();
        passwordField.sendKeys(password);
        
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(password);
        
        emailField.clear();
        emailField.sendKeys(email);
        
        return this;
    }
    
    /**
     * Submits the form to create a new user
     * 
     * @return the users management page
     */
    public UsersManagementPage submitForm() {
        saveButton.click();
        return new UsersManagementPage(driver);
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
