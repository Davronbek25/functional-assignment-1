package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard new category page
 */
public class NewCategoryPage extends BasePage {
    
    @FindBy(id = "form-name")
    private WebElement nameField;
    
    @FindBy(xpath = "//*[@id=\"modal-content\"]/form/div/div/button")
    private WebElement saveButton;
    
    /**
     * Constructor for the new category page
     * 
     * @param driver WebDriver instance
     */
    public NewCategoryPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the new category form
     * 
     * @param name category name
     * @return this page object
     */
    public NewCategoryPage fillForm(String name) {
        try {
            WebDriverUtils.clearAndSendKeys(driver, By.id("form-name"), name, 5);
        } catch (Exception e) {
            System.out.println("NewCategoryPage fillForm failed: " + e);
        }
        return this;
    }

    public ProjectBoardPage clickSaveBtn() {
        try {
            WebDriverUtils.waitForVisibility(driver, By.xpath("//*[@id=\"modal-content\"]/form/div/div/button"), 5);
            saveButton.click();
        } catch (Exception e) {
            System.out.println("Error clicking save button: " + e.getMessage());
        }
        return new ProjectBoardPage(driver);
    }
    
    /**
     * Submits the form to create a new category
     * 
     * @return the project board page
     */

    public boolean confirmNewCategory(String category) {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.xpath("//td[div and text()[contains(., '" + category +"')]]"), 5).isDisplayed();
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
            WebElement errorMessage = driver.findElement(By.className("alert-error"));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
