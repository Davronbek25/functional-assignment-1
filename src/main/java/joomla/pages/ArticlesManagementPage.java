package joomla.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Joomla articles management page
 */
public class ArticlesManagementPage extends BasePage {    
    /**
     * Constructor for the articles management page
     * 
     * @param driver WebDriver instance
     */
    public ArticlesManagementPage(WebDriver driver) {
        super(driver);
    }

    public boolean isArticleCreated() {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.className("alert-success"), 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
