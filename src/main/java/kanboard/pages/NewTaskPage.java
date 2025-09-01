package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard new task page
 */
public class NewTaskPage extends BasePage {
    
    @FindBy(id = "form-title")
    private WebElement titleField;
    
    @FindBy(xpath = "//*[@id=\"modal-content\"]/form/div/div[1]/div/div/div[2]/textarea")
    private WebElement descriptionField;
    
    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    private WebElement saveButton;
    
    @FindBy(id = "modal-close-button")
    private WebElement closeButton;
    
    /**
     * Constructor for the new task page
     * 
     * @param driver WebDriver instance
     */
    public NewTaskPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the new task form
     * 
     * @param title task title
     * @param description task description
     * @return this page object
     */
    public NewTaskPage fillForm(String title) {
        try {
            WebDriverUtils.clearAndSendKeys(driver, By.id("form-title"), title, 5);
            saveButton.click();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return this;
    }
    
    /**
     * Submits the form to create a new task
     * 
     * @return the project board page
     */
    public ProjectBoardPage submitForm() {
        try {
            WebDriverUtils.waitForVisibility(driver, By.xpath("//button[contains(text(), 'Save')]"), 3);
            saveButton.click();
        } catch (Exception e) {
            System.out.println("ProjectBoardPage submitForm() failed: " + e);
        }
        return new ProjectBoardPage(driver);
    }

    public void clickCloseButton() {
        closeButton.click();
    }
    
    /**
     * Checks if an error message is displayed
     * 
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorMessage = WebDriverUtils.waitForPresence(driver, By.className("form-error"), 5);
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
