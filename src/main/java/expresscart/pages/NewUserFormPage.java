package expresscart.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for ExpressCart new user form page
 */
public class NewUserFormPage extends BasePage {

    @FindBy(id = "usersName")
    private WebElement nameField;

    @FindBy(id = "userEmail")
    private WebElement emailField;

    @FindBy(id = "userPassword")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"userNewForm\"]/div[4]/input")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//*[@id=\"container\"]/div/nav/div/ul[1]/li[6]/a[1]")
    private WebElement usersButton;

    @FindBy(xpath = "//button[contains(text(), 'Create')]")
    private WebElement createButton;

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
     * @param email    user email
     * @param password user password
     * @param isAdmin  whether the user is an admin
     * @return this page object
     */
    public NewUserFormPage fillForm(String name, String email, String password) {
        nameField.clear();
        nameField.sendKeys(name);

        emailField.clear();
        emailField.sendKeys(email);

        passwordField.clear();
        passwordField.sendKeys(password);

        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(password);

        return this;
    }

    /**
     * Submits the form to create a new user
     * 
     * @return the users management page
     */
    public NewUserFormPage submitForm() {
        createButton.click();
        return this;
    }
    
    public UsersManagementPage navigateToUsersManagementPage() {
        usersButton.click();
        return new UsersManagementPage(driver);
    }

    /**
     * Checks if an error message is displayed
     * 
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return !driver.findElements(By.className("has-error")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isAlertMessageDisplayed() {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.className("alert-danger"), 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
