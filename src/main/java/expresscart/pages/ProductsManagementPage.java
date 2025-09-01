package expresscart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for ExpressCart products management page
 */
public class ProductsManagementPage extends BasePage {
    
    @FindBy(xpath = "//a[contains(@href, '/admin/product/new')]")
    private WebElement newProductButton;
    
    @FindBy(id = "product_filter")
    private WebElement productFilter;

    @FindBy(id = "btn_product_filter")
    private WebElement productFilterButton;
    
    /**
     * Constructor for the products management page
     * 
     * @param driver WebDriver instance
     */
    public ProductsManagementPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Clicks the new product button
     * 
     * @return the new product form page
     */
    public NewProductFormPage clickNewProduct() {
        newProductButton.click();
        return new NewProductFormPage(driver);
    }
    
    /**
     * Searches for a product by title
     * 
     * @param title the title to search for
     * @return this page object
     */
    public ProductsManagementPage searchProduct(String title) {
        productFilter.clear();
        productFilter.sendKeys(title);
        productFilterButton.click();
        return this;
    }
    
    /**
     * Checks if a product with the given title exists
     * 
     * @param title the title to check
     * @return true if the product exists, false otherwise
     */
    public boolean isProductPresent(String title) {
        // searchProduct(title);
        try {
            WebElement productRow = WebDriverUtils.waitForPresence(driver, By.xpath("//a[contains(text(), '" + title + "')]"), 2);
            return productRow.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
