package joomla.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Joomla users management page
 */
public class UsersManagementPage extends BasePage {
    
    @FindBy(xpath = "//button[contains(@onclick, 'user.add')]")
    private WebElement newUserButton;
    
    /**
     * Constructor for the users management page
     * 
     * @param driver WebDriver instance
     */
    public UsersManagementPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Clicks the new user button
     * 
     * @return the new user form page
     */
    public NewUserFormPage clickNewUser() {
        newUserButton.click();
        try {
            WebDriverUtils.waitForVisibility(driver, By.id("jform_name"), 5);
        } catch (Exception e) {
            System.out.println("NewUserFormPage clickNewUser(): " + e);
        }
        return new NewUserFormPage(driver);
    }
    
    /**
     * Checks if a user with the given name or username exists
     * 
     * @param searchTerm the term to check
     * @return true if the user exists, false otherwise
     */
    public boolean isUserPresent(String searchTerm) {
        try {
            WebElement userRow = WebDriverUtils.waitForPresence(driver, 
                By.xpath("//table[@id='userList']//td[@class='break-word' and contains(text(), '" + searchTerm + "')]"), 5);
            return userRow.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
