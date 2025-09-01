package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard dashboard page
 */
public class DashboardPage extends BasePage {
    
    @FindBy(xpath = "/html/body/header/div[3]/div[2]")
    private WebElement userMenu;
    
    @FindBy(css = "li.dropdown")
    private WebElement dropdownContainer;
    
    @FindBy(xpath = "//*[@id=\"main\"]/div/ul/li[1]/a")
    private WebElement newProjectLink;
    
    @FindBy(xpath = "//*[@id=\"main\"]/div/ul/li[3]/a")
    private WebElement projectsLink;
    
    @FindBy(xpath = "//*[@id=\"main\"]/div/ul/li[1]/a")
    private WebElement newProjectButton;
    
    /**
     * Constructor for the dashboard page
     * 
     * @param driver WebDriver instance
     */
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigates to the new project page
     * 
     * @return the new project page
     */
    public NewProjectPage clickNewProjectLink() {
        newProjectButton.click();
        try {
            WebDriverUtils.waitForPresence(driver, By.xpath("//*[@id=\"main\"]/div/h2"), 5);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new NewProjectPage(driver);
    }

    public DashboardPage clickRishotkaIcon() {
        try {
            WebDriverUtils.waitAndClick(driver, By.xpath("//a[contains(., '#1')]"), 5);
        } catch (Exception e) {
            System.out.println("DashboardPage clickRishotkaIcon() failed: " + e);
        }
        return this;
    }

    public ProjectBoardPage clickSettings() {
        try {
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id='dropdown']/ul/li[5]/a"), 5);
        } catch (Exception e) {
            System.out.println("ProjectBoardPage clickSettings() failed: " + e);
        }
        return new ProjectBoardPage(driver);
    }
    
    /**
     * Checks if the dashboard is loaded
     * 
     * @return true if the dashboard is loaded, false otherwise
     */
    public boolean isDashboardLoaded() {
        try {
            return driver.findElement(By.cssSelector("div.page-header")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Opens the user menu
     */
    public void openUserMenu() {
        userMenu.click();
    }

    public ProjectBoardPage clickProject(String projectName) {
        driver.findElement(By.xpath("//a[contains(text(),'" + projectName + "')]")).click();
        return new ProjectBoardPage(driver);
    }
    
    /**
     * Navigates to the new project page
     * 
     * @return the new project page
     */
    public NewProjectPage navigateToNewProject() {
        newProjectLink.click();
        return new NewProjectPage(driver);
    }
    
    /**
     * Navigates to the projects list page
     * 
     * @return the projects list page
     */
    public ProjectsListPage navigateToProjects() {
        projectsLink.click();

        try {
            WebDriverUtils.waitForVisibility(driver, By.xpath("//h1/span[contains(text(), 'Projects')]"), 5);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ProjectsListPage(driver);
    }
}
