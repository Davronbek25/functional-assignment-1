package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard new column page
 */
public class NewColumnPage extends BasePage {
    
    @FindBy(id = "form-title")
    private WebElement titleField;
    
    @FindBy(id = "modal-close-button")
    private WebElement closeBtn;
    
    @FindBy(xpath = "//*[@id=\"modal-content\"]/form/div[2]/div/button")
    private WebElement saveButton;
    
    /**
     * Constructor for the new column page
     * 
     * @param driver WebDriver instance
     */
    public NewColumnPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the new column form
     * 
     * @param title column title
     * @param taskLimit column task limit
     * @return this page object
     */
    public NewColumnPage fillForm(String title) {
        try {
            WebDriverUtils.clearAndSendKeys(driver, By.id("form-title"), title, 5);
        } catch (Exception e) {
            System.out.println(e);
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
