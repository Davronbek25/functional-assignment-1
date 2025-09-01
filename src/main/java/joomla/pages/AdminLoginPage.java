package joomla.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Joomla admin login page
 */
public class AdminLoginPage extends BasePage {

    @FindBy(id = "mod-login-username")
    private WebElement usernameField;

    @FindBy(id = "mod-login-password")
    private WebElement passwordField;

    @FindBy(tagName = "button")
    private WebElement loginButton;

    /**
     * Constructor for the admin login page
     * 
     * @param driver WebDriver instance
     */
    public AdminLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Logs in with the provided credentials
     * 
     * @param username admin username
     * @param password admin password
     * @return the dashboard page
     */
    public AdminLoginPage login(String username, String password) {
        usernameField.clear();
        usernameField.sendKeys(username);

        passwordField.clear();
        passwordField.sendKeys(password);

        return this;
    }

    public AdminDashboardPage clickLoginBtn() {
        loginButton.click();

        try {
            // Wait for the dashboard page to load
            WebDriverUtils.waitForVisibility(driver, By.xpath("//h1[contains(string(.), 'Control Panel')]"), 2);
        } catch (Exception e) {
            System.out.println("clickLoginBtn: " + e);
        }

        return new AdminDashboardPage(driver);
    }

    public boolean isLoaded() {
        try {
            WebElement isLoad = WebDriverUtils.waitForVisibility(driver, By.id("mod-login-username"), 5);
            return isLoad.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if an error message is displayed
     * 
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorMessage = driver.findElement(By.className("alert-message"));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
