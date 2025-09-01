package expresscart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for ExpressCart new product form page
 */
public class NewProductFormPage extends BasePage {
    
    @FindBy(id = "productTitle")
    private WebElement titleField;
    
    @FindBy(id = "productPrice")
    private WebElement priceField;
    
    @FindBy(className = "note-editable")
    private WebElement descriptionField;
    
    @FindBy(xpath = "//button[contains(text(), 'Add product')]")
    private WebElement createButton;

    @FindBy(xpath = "//*[@id=\"container\"]/div/nav/div/ul[1]/li[3]/a[1]")
    private WebElement productsLink;
    
    /**
     * Constructor for the new product form page
     * 
     * @param driver WebDriver instance
     */
    public NewProductFormPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the new product form
     * 
     * @param title product title
     * @param price product price
     * @param description product description
     * @param published whether the product is published
     * @return this page object
     */
    public NewProductFormPage fillForm(String title, String price, String description) {
        titleField.clear();
        titleField.sendKeys(title);
        
        priceField.clear();
        priceField.sendKeys(price);
        
        descriptionField.clear();
        descriptionField.sendKeys(description);
        
        return this;
    }
    
    /**
     * Submits the form to create a new product
     * 
     * @return the products management page
     */
    public NewProductFormPage submitForm() {
        createButton.click();
        return new NewProductFormPage(driver);
    }

    public ProductsManagementPage clickProductsLink() {
        productsLink.click();
        return new ProductsManagementPage(driver);
    }
    
    /**
     * Checks if an error message is displayed
     * 
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return !driver.findElements(By.className("has-error")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
