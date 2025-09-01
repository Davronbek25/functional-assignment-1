package joomla.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;

/**
 * Page object for Joomla new article form page
 */
public class NewArticleFormPage extends BasePage {
    
    @FindBy(id = "jform_title")
    private WebElement titleField;
    
    @FindBy(id = "jform_articletext_ifr")
    private WebElement contentFrame;
    
    @FindBy(xpath = "//*[@id=\"adminForm\"]/div/div[1]/button")
    private WebElement saveButton;
    
    /**
     * Constructor for the new article form page
     * 
     * @param driver WebDriver instance
     */
    public NewArticleFormPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the new article form
     * 
     * @param title article title
     * @param content article content
     * @return this page object
     */
    public NewArticleFormPage fillForm(String title, String content) {
        titleField.clear();
        titleField.sendKeys(title);
        
        // Switch to content frame and enter content
        driver.switchTo().frame(contentFrame);
        WebElement body = driver.findElement(By.tagName("body"));
        body.clear();
        body.sendKeys(content);
        driver.switchTo().defaultContent();
        
        return this;
    }

    public NewArticleFormPage fillEditForm(String title, String content) {
        titleField.sendKeys(title);
        
        // Switch to content frame and enter content
        driver.switchTo().frame(contentFrame);
        WebElement body = driver.findElement(By.tagName("body"));
        body.sendKeys(content);
        driver.switchTo().defaultContent();
        
        return this;
    }
    
    /**
     * Submits the form to create a new article
     * 
     * @return the articles management page
     */
    public ArticlesManagementPage submitForm() {
        saveButton.click();
        return new ArticlesManagementPage(driver);
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
