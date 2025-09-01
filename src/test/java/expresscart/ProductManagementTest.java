package expresscart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import expresscart.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ExpressCart product management functionality
 */
public class ProductManagementTest {
    
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
     * Test case for adding a new product
     * Corresponds to 05_AddProductTest.feature
     */
    @Test
    public void testAddProduct() {
        // Start timing
        timingRecorder = new TimingRecorder("ExpressCart", "AddProductTest", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in as an admin
            AdminLoginPage loginPage = new AdminLoginPage(driver);
            loginPage.navigateTo();
            AdminDashboardPage dashboardPage = loginPage.login("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");
            
            // And I click on new product
            NewProductFormPage newProductPage = dashboardPage.clickNewProduct();
            
            // And I fill in the product details
            // String productTitle = "Test Product " + System.currentTimeMillis();
            String productTitle = "NewProduct000";
            newProductPage.fillForm(productTitle, "15.95", "Description for product 000 ");
            
            // And I click create
            newProductPage.submitForm();
            ProductsManagementPage productsPage = newProductPage.clickProductsLink();
            
            // Then the product should be created
            assertTrue(productsPage.isProductPresent(productTitle), "Product should be present in the list");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for adding an empty product
     * Corresponds to 06_AddEmptyProductTest.feature
     */
    @Test
    public void testAddEmptyProduct() {
        // Start timing
        timingRecorder = new TimingRecorder("ExpressCart", "AddEmptyProductTest", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in as an admin
            AdminLoginPage loginPage = new AdminLoginPage(driver);
            loginPage.navigateTo();
            AdminDashboardPage dashboardPage = loginPage.login("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");
            
            // And I click on new product
            NewProductFormPage newProductPage = dashboardPage.clickNewProduct();
            
            // And I leave the product details empty
            // (No form filling)
            
            // And I click create
            newProductPage.submitForm();
            
            // Then I should see an error message
            assertTrue(newProductPage.isErrorMessageDisplayed(), "Error message should be displayed");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
