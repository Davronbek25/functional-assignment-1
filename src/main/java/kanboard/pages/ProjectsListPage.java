package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard projects list page
 */
public class ProjectsListPage extends BasePage {
    
    @FindBy(id = "form-search")
    private WebElement searchField;
    
    /**
     * Constructor for the projects list page
     * 
     * @param driver WebDriver instance
     */
    public ProjectsListPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Searches for a project by name
     * 
     * @param name the name to search for
     * @return this page object
     */
    public ProjectsListPage searchProject(String name) {
        searchField.clear();
        searchField.sendKeys(name + Keys.RETURN);
        return this;
    }
    
    /**
     * Checks if a project with the given name exists
     * 
     * @param name the name to check
     * @return true if the project exists, false otherwise
     */
    public boolean isProjectPresent(String name) {
        System.out.println(name);
        try {
            WebElement projectRow = WebDriverUtils.waitForPresence(driver, By.xpath("//a[contains(text(), '" + name + "')]"), 5);
            return projectRow.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Opens a project by name
     * 
     * @param name the name of the project to open
     * @return the project board page
     */
    public ProjectBoardPage openProject(String name) {
        driver.findElement(By.xpath("//a[contains(text(), '" + name + "')]")).click();
        return new ProjectBoardPage(driver);
    }
}
