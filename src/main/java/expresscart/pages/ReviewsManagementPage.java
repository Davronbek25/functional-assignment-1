package expresscart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for ExpressCart reviews management page
 */
public class ReviewsManagementPage extends BasePage {
    
    @FindBy(id = "add-review")
    private WebElement addReviewButton;
    
    /**
     * Constructor for the reviews management page
     * 
     * @param driver WebDriver instance
     */
    public ReviewsManagementPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Clicks the new review button
     * 
     * @return the new review form page
     */
    public NewReviewFormPage clickNewReview() {
        addReviewButton.click();
        return new NewReviewFormPage(driver);
    }
    
    public boolean isReviewsManagementPageLoaded() {
        try {
            return WebDriverUtils.waitForVisibility(driver, By.className("ratingWrapper"), 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a review with the given title exists
     * 
     * @param title the title to check
     * @return true if the review exists, false otherwise
     */
    public boolean isReviewPresent(String title, String description, String rating) {
        try {
            // Wait and click the reviews section link
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"container\"]/div/div/div[1]/div[2]/div/div[4]/a"), 5);
    
            // Check if each section is present individually
            boolean ratingSection = WebDriverUtils.waitForVisibility(driver, By.xpath("//p[contains(text(), '" + rating + "')]"), 5).isDisplayed();
            boolean titleSection = WebDriverUtils.waitForVisibility(driver, By.xpath("//p[contains(text(), '" + title + "')]"), 5).isDisplayed();
            boolean descriptionSection = WebDriverUtils.waitForVisibility(driver, By.xpath("//p[contains(text(), '" + description + "')]"), 5).isDisplayed();
    
            // Return true only if all sections are present
            return ratingSection && titleSection && descriptionSection;
        } catch (Exception e) {
            e.printStackTrace(); // Log unexpected exceptions
            return false;
        }
    }
}
