package expresscart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for ExpressCart users management page
 */
public class UsersManagementPage extends BasePage {
    
    /**
     * Constructor for the users management page
     * 
     * @param driver WebDriver instance
     */
    public UsersManagementPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Checks if a user with the given email exists
     * 
     * @param email the email to check
     * @return true if the user exists, false otherwise
     */
    public boolean isUserPresent(String email) {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.xpath("//li[contains(@class, 'list-group-item') and contains(string(.), '" + email + "')]"), 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
