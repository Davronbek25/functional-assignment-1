package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard new project page
 */
public class NewProjectPage extends BasePage {
    
    @FindBy(id = "form-name")
    private WebElement nameField;
    
    @FindBy(id = "form-identifier")
    private WebElement identifierField;
    
    @FindBy(xpath = "//*[@id=\"project-creation-form\"]/div[2]/div/button")
    private WebElement saveButton;
    
    @FindBy(id = "modal-close-button")
    private WebElement closeModal;
    
    /**
     * Constructor for the new project page
     * 
     * @param driver WebDriver instance
     */
    public NewProjectPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the new project form
     * 
     * @param name project name
     * @param identifier project identifier
     * @return this page object
     */
    public NewProjectPage fillForm(String name) {
        nameField.clear();
        nameField.sendKeys(name);
        
        return this;
    }

    public void clickCloseModal() {
        closeModal.click();
    }
    
    /**
     * Submits the form to create a new project
     * 
     * @return the project board page
     */
    public ProjectBoardPage submitForm() {
        saveButton.click();
        try {
            WebDriverUtils.waitForVisibility(driver, By.className("view-board"), 3);
        } catch (Exception e) {
            System.out.println("ProjectBoardPage submitForm() failed: " + e);
        }
        return new ProjectBoardPage(driver);
    }
    
    /**
     * Checks if an error message is displayed
     * 
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorMessage = driver.findElement(By.className("form-errors"));
            System.out.println("isErrorMessageDisplayed(): " + errorMessage.isDisplayed());
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
