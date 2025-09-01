package expresscart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for ExpressCart new review form page
 */
public class NewReviewFormPage extends BasePage {
    
    @FindBy(id = "review-title")
    private WebElement titleField;
    
    @FindBy(id = "review-description")
    private WebElement descriptionField;
    
    @FindBy(id = "review-rating")
    private WebElement ratingField;
    
    @FindBy(id = "addReview")
    private WebElement createButton;
    
    /**
     * Constructor for the new review form page
     * 
     * @param driver WebDriver instance
     */
    public NewReviewFormPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Fills the new review form
     * 
     * @param title review title
     * @param description review description
     * @param rating review rating
     * @return this ReviewsManagementPage object
     */
    public ReviewsManagementPage fillForm(String title, String description, String rating) {
        titleField.clear();
        titleField.sendKeys(title);
        
        descriptionField.clear();
        descriptionField.sendKeys(description);
        
        ratingField.clear();
        ratingField.sendKeys(rating);
        
        return new ReviewsManagementPage(driver);
    }
    
    /**
     * Submits the form to create a new review
     * 
     * @return the reviews management page
     */
    public ReviewsManagementPage submitForm() {
        createButton.click();
        return new ReviewsManagementPage(driver);
    }

    public boolean isProductReviewLoaded() {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.xpath("//h5[contains(., 'Product review')]"), 5).isDisplayed();
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
            WebElement errorMessage = driver.findElement(By.className("alert-danger"));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
