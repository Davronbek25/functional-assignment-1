package expresscart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import expresscart.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;
import utils.WebDriverUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ExpressCart shopping cart functionality
 */
public class ShoppingCartTest {
    
    private WebDriver driver;
    private TimingRecorder timingRecorder;
    
    @BeforeEach
    public void setUp() {
        driver = WebDriverSetup.setupChromeDriver(false);
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    /**
     * Test case for adding a new product to cart
     * Corresponds to 07_AddNewProdToCartTest.feature
     */
    @Test
    public void testAddNewProdToCart() {
        // Start timing
        timingRecorder = new TimingRecorder("ExpressCart", "AddNewProdToCartTest", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // When I navigate to the store front
            driver.get("http://localhost:3000/");
            
            String productTitle = "NewProduct000";
            // String productTitle = "t shirt";
            driver.findElement(By.xpath("//a[.//h3[contains(., '" + productTitle + "')]]")).click();
            try {
                WebDriverUtils.waitAndClick(driver, By.xpath("//button[contains(., 'Add to cart')]"), 5);
            } catch (Exception e) {
                System.out.println("Add to cart button error: " + e);
            }
            driver.findElement(By.xpath("//a[@href='/' and contains(., 'Home')]")).click();
            
            // Then the product should be in the cart
            driver.findElement(By.xpath("//a[@href='/checkout/cart']")).click();
            boolean productInCart = driver.findElement(By.xpath("//a[contains(text(), '" + productTitle + "')]")).isDisplayed();
            assertTrue(productInCart, "Product should be in the cart");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for searching a product
     * Corresponds to 08_SearchProductTest.feature
     */
    @Test
    public void testSearchProduct() {
        // Start timing
        timingRecorder = new TimingRecorder("ExpressCart", "SearchProductTest", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // When I navigate to the store front
            driver.get("http://localhost:3000/");
            String productTitle = "NewProduct000";
            
            // And I search for the product
            driver.findElement(By.id("frm_search")).sendKeys(productTitle);
            driver.findElement(By.id("btn_search")).click();
            
            // Then the product should be found
            boolean productFound = driver.findElement(By.xpath("//h3[contains(text(), '" + productTitle + "')]")).isDisplayed();
            assertTrue(productFound, "Product should be found in search results");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
