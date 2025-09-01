package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard new swimlane page
 */
public class NewSwimlanePage extends BasePage {
    
    @FindBy(id = "form-name")
    private WebElement nameField;
    
    @FindBy(xpath = "//*[@id=\"modal-content\"]/form/div[1]/div/div[2]/textarea")
    private WebElement descriptionField;
    
    @FindBy(xpath = "//*[@id=\"modal-content\"]/form/div[2]/div/button")
    private WebElement saveButton;

    @FindBy(id = "modal-close-button")
    private WebElement closeBtn;
    
    /**
     * Constructor for the new swimlane page
     * 
     * @param driver WebDriver instance
     */
    public NewSwimlanePage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the new swimlane form
     * 
     * @param name swimlane name
     * @param description swimlane description
     * @return this page object
     */
    public NewSwimlanePage fillForm(String name) {
        try {
            WebDriverUtils.clearAndSendKeys(driver, By.id("form-name"), name, 5);
        } catch (Exception e) {
            System.out.println("NewSwimlanePage fillForm failed: " + e);
        }
        return this;
    }

    public ProjectBoardPage clickSaveBtn() {
        try {
            WebDriverUtils.waitForVisibility(driver, By.xpath("//*[@id=\"modal-content\"]/form/div[2]/div/button"), 5);
            saveButton.click();
        } catch (Exception e) {
            System.out.println("Error clicking save button: " + e.getMessage());
        }
        return new ProjectBoardPage(driver);
    }

    public void clickCloseBtn() {
        closeBtn.click();
    }

    public boolean confirmNewSwimlane (String swimlane) {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.xpath("//td[contains(., '" + swimlane +"')]"), 5).isDisplayed();
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
            return WebDriverUtils.waitForPresence(driver, By.className("form-error"), 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
