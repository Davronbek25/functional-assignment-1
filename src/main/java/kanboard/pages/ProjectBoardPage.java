package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard project board page
 */
public class ProjectBoardPage extends BasePage {
    
    @FindBy(xpath = "//*[@id=\"main\"]/div/div[1]/div/a")
    private WebElement newTaskButton;
    
    @FindBy(xpath = "//*[@id=\"main\"]/section/div[2]/div/ul/li/a")
    private WebElement newColumnButton;
    
    @FindBy(xpath = "//*[@id=\"main\"]/section/div[1]/ul/li[9]/a")
    private WebElement newSwimlaneButton;
    
    @FindBy(xpath = "//*[@id=\"main\"]/section/div[1]/ul/li[10]/a")
    private WebElement newCategoryButton;

    @FindBy(xpath = "(//div[@class='dropdown'])[1]")
    private WebElement dropdown;

    @FindBy(xpath = "//a[contains(text(), 'Edit project')]")
    private WebElement editLink;

    @FindBy(xpath = "//*[@id=\"main\"]/div[1]/div[1]/div/a")
    private WebElement cogIcon;

    @FindBy(xpath = "//*[@id=\"main\"]/section/div[1]/ul/li[8]/a")
    private WebElement columnsLink;

    @FindBy(xpath = "//*[@id=\"main\"]/section/div[1]/ul/li[9]/a")
    private WebElement swimlaneLink;

    @FindBy(xpath = "//*[@id=\"main\"]/section/div[1]/ul/li[10]/a")
    private WebElement categoriesLink;
    
    /**
     * Constructor for the project board page
     * 
     * @param driver WebDriver instance
     */
    public ProjectBoardPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Checks if the project board is loaded
     * 
     * @return true if the project board is loaded, false otherwise
     */
    public boolean isBoardLoaded(String projectName) {
        try {
            return driver.findElement(By.xpath("//span[@class='title' and contains(text(), '" + projectName + "')]")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTaskCreated(String taskName) {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.xpath("//a[contains(text(), '" + taskName + "')]"), 3).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProjectEdited(String description) {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.xpath("//article[@class='markdown']/p[contains(text(), '" + description + "')]"), 3).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public ProjectBoardPage clickCogIcon() {
        cogIcon.click();
        return this;
    }

    public ProjectBoardPage clickColumnsLink() {
        columnsLink.click();
        return this;
    }

    public ProjectBoardPage clickSwimlaneLink() {
        swimlaneLink.click();
        return this;
    }

    public ProjectBoardPage clickCategoriesLink() {
        categoriesLink.click();
        return this;
    }

    public ProjectsListPage navProjectsListPage() {
        dropdown.click();
        WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"dropdown\"]/ul/li[4]/a"), 5);
        return new ProjectsListPage(driver);
    }

    public EditProjectPage clickEditLink() {
        editLink.click();
        return new EditProjectPage(driver);
    }
    
    /**
     * Navigates to the new task page
     * 
     * @return the new task page
     */
    public NewTaskPage navigateToNewTask() {
        try {
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"dropdown\"]/ul/li[3]/a"), 5);
        } catch (Exception e) {
            System.out.println("NewTaskPage navigateToNewTask(): " + e);
        }
        return new NewTaskPage(driver);
    }
    
    /**
     * Navigates to the new column page
     * 
     * @return the new column page
     */
    public NewColumnPage navigateToNewColumn() {
        try {
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"main\"]/section/div[2]/div/ul/li/a"), 5);
        } catch (Exception e) {
            System.out.println("NewColumnPage navigateToNewColumn(): " + e);
        }
        return new NewColumnPage(driver);
    }

    public boolean confirmNewColumn (String column) {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.xpath("//td[contains(., '" + column +"')]"), 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Navigates to the new swimlane page
     * 
     * @return the new swimlane page
     */
    public NewSwimlanePage navigateToNewSwimlane() {
        try {
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"main\"]/section/div[2]/div/ul/li/a"), 5);
        } catch (Exception e) {
            System.out.println("NewSwimlanePage navigateToNewSwimlane() failed: " + e);
        }
        return new NewSwimlanePage(driver);
    }
    
    public NewCategoryPage navigateToNewCategory() {
        try {
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"main\"]/section/div[2]/div/ul/li/a"), 5);
        } catch (Exception e) {
            System.out.println("NewCategoryPage navigateToNewSwimlane() failed: " + e);
        }
        return new NewCategoryPage(driver);
    }
}
