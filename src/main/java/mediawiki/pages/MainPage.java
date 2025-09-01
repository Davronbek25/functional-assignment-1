package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;

/**
 * Page object for MediaWiki main page
 */
public class MainPage extends BasePage {
    
    @FindBy(xpath = "//a[contains(., 'Log in')]")
    private WebElement loginLink;
    
    @FindBy(id = "pt-createaccount")
    private WebElement createAccountLink;
    
    @FindBy(id = "ca-edit")
    private WebElement editLink;
    
    /**
     * Constructor for the main page
     * 
     * @param driver WebDriver instance
     */
    public MainPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigates to the main page
     * 
     * @return this page object
     */
    public MainPage navigateTo() {
        driver.get("http://localhost:5000/mediawiki/index.php?title=Main_Page");
        return this;
    }
    
    /**
     * Clicks the login link
     * 
     * @return the login page
     */
    public LoginPage clickLoginLink() {
        loginLink.click();
        return new LoginPage(driver);
    }
    
    /**
     * Clicks the create account link
     * 
     * @return the create account page
     */
    public CreateAccountPage clickCreateAccountLink() {
        createAccountLink.click();
        return new CreateAccountPage(driver);
    }
    
    /**
     * Clicks the edit link
     * 
     * @return the edit page
     */
    public EditPage clickEditLink() {
        editLink.click();
        return new EditPage(driver);
    }
    
    /**
     * Checks if the user is logged in
     * 
     * @return true if the user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        try {
            return driver.findElement(By.id("pt-userpage")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
