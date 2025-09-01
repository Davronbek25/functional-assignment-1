package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for ExpressCart admin dashboard page
 */
public class AdminDashboardPage extends BasePage {

    @FindBy(xpath = "//a[contains(., 'Admin')]")
    private WebElement admin;
    
    @FindBy(id = "searchInput")
    private WebElement searchField;
    
    /**
     * Constructor for the admin dashboard page
     * 
     * @param driver WebDriver instance
     */
    public AdminDashboardPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigates to the special pages
     * 
     * @return the special pages
     */
    public SpecialPages navigateToSpecialPages() {
        try {
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"t-specialpages\"]/a"), 5);
        } catch (Exception e) {
            System.out.println("SpecialPages navigateToSpecialPages() failed: " + e);
        }
        return new SpecialPages(driver);
    }
    
    /**
     * Navigates to the Search results page
     * 
     * @return the Search results page
     */
    public SearchResults navigateToSearchResults(String searchTerm) {
        searchField.clear();
        searchField.sendKeys(searchTerm + Keys.RETURN);
        return new SearchResults(driver);
    }

    /**
     * Checks if the dashboard is loaded
     * 
     * @return true if the dashboard is loaded, false otherwise
     */
    public boolean isLoggedIn(){
        try {
            return admin.isDisplayed() && driver.getTitle().contains("E2E Web Testing wiki");
        } catch (Exception e) {
            System.err.println("Error in isDashboardLoaded: " + e.getMessage());
            return false;
        }
    }
}
