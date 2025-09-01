package expresscart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for ExpressCart admin dashboard page
 */
public class AdminDashboardPage extends BasePage {
    
    @FindBy(xpath = "//a[contains(@href, '/admin/users')]")
    private WebElement usersLink;
    
    @FindBy(xpath = "/html/body/div[2]/div/main/div[1]/h2[contains(., 'Dashboard')]")
    private WebElement header;
    
    @FindBy(xpath = "//a[@href='/admin/user/new']")
    private WebElement newUserBtn;
    
    @FindBy(xpath = "//a[@href='/admin/product/new']")
    private WebElement newProductBtn;
    
    /**
     * Constructor for the admin dashboard page
     * 
     * @param driver WebDriver instance
     */
    public AdminDashboardPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigates to the users management page
     * 
     * @return the users management page
     */
    public UsersManagementPage navigateToUsers() {
        usersLink.click();
        return new UsersManagementPage(driver);
    }

    public NewUserFormPage clickNewUser() {
        newUserBtn.click();
        return new NewUserFormPage(driver);
    }

    public NewProductFormPage clickNewProduct() {
        newProductBtn.click();
        return new NewProductFormPage(driver);
    }

    /**
     * Checks if the dashboard is loaded
     * 
     * @return true if the dashboard is loaded, false otherwise
     */
    public boolean isDashboardLoaded(){
        try {
            return header.isDisplayed() && driver.getTitle().contains("dashboard");
        } catch (Exception e) {
            System.err.println("Error in isDashboardLoaded: " + e.getMessage());
            return false;
        }
    }
}
