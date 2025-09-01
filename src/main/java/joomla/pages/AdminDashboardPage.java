package joomla.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;

/**
 * Page object for Joomla admin dashboard page
 */
public class AdminDashboardPage extends BasePage {
    
    @FindBy(xpath = "//ul[contains(@class, 'nav-list')]//a[contains(@href, 'option=com_users')]")
    private WebElement usersLink;
    
    /**
     * Constructor for the admin dashboard page
     * 
     * @param driver WebDriver instance
     */
    public AdminDashboardPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigates to the users management page
     * 
     * @return the users management page
     */
    public UsersManagementPage navigateToUsers() {
        usersLink.click();
        return new UsersManagementPage(driver);
    }
    
    /**
     * Checks if the dashboard is loaded
     * 
     * @return true if the dashboard is loaded, false otherwise
     */
    public boolean isDashboardLoaded() {
        try {
            return driver.getTitle().contains("Control Panel") || driver.getTitle().contains("Dashboard");
        } catch (Exception e) {
            return false;
        }
    }
}
