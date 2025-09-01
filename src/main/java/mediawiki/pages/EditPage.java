package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for MediaWiki edit page
 */
public class EditPage extends BasePage {
    
    @FindBy(xpath = "//*[@id=\"mwAg\"]")
    private WebElement contentTextArea;
    
    @FindBy(id = "wpSummary")
    private WebElement summaryField;
    
    @FindBy(id = "wpSave")
    private WebElement saveButton;
    
    /**
     * Constructor for the edit page
     * 
     * @param driver WebDriver instance
     */
    public EditPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Enters content in the edit area
     * 
     * @param content the content to enter
     * @return this page object
     */
    public EditPage enterContent(String content) {
        try {
            WebDriverUtils.waitForPresence(driver, By.id("mwAg"), 5).sendKeys(Keys.END);
            WebDriverUtils.waitForPresence(driver, By.id("mwAg"), 5).sendKeys(content);
        } catch (Exception e) {
            System.out.println("error entering content: " + e);
        }
        return this;
    }

    public CategoryPage navigateToCategory() {
        try {
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"content\"]/div/div[1]/div/div[3]/div/div[1]/div[1]/div[2]/span"), 5);
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"content\"]/div/div[1]/div/div[3]/div/div[2]/div[3]/span[2]/a"), 5);
        } catch (Exception e) {
            System.out.println("errror while pressing category button: " + e);
        }
        return new CategoryPage(driver);
    }
    
    /**
     * Enters a summary for the edit
     * 
     * @param summary the summary to enter
     * @return this page object
     */
    public EditPage enterSummary(String summary) {
        summaryField.clear();
        summaryField.sendKeys(summary);
        return this;
    }
    
    /**
     * Checks if the edit form is loaded
     * 
     * @return true if the edit form is loaded, false otherwise
     */
    public boolean isEditFormLoaded() {
        try {
            return contentTextArea.isDisplayed() && saveButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
