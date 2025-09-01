package mediawiki;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import mediawiki.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MediaWiki user account functionality
 * Using Manual approach
 */
public class UserAccountTest {
    
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
     * Test case for creating a new user account
     * Corresponds to 01_CreateUserTest.feature
     */
    @Test
    public void testCreateUser() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "CreateUserTest", "Manual");
        timingRecorder.startTimer();
        
        try {
            // Given I am on the main page
            MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();

            AdminDashboardPage adminDashboard = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");
            
            SpecialPages specialPages = adminDashboard.navigateToSpecialPages();

            CreateAccountPage createAccountPage = specialPages.createAccount(driver);

            String username = "User001";
            // String username = "Test User" + System.currentTimeMillis();
            String password = "e2eW3Bt3s71nGB3nchM4rK";
            createAccountPage.fillForm(username, password, "Real Name 001");
            
            SpecialPage specialPage = createAccountPage.submitForm();

            assertTrue(specialPage.isUserPresent(username), "User is supposed to be present after account creation");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for creating an empty user account
     * Corresponds to 27_CreateEmptyUser_Fails.feature
     */
    @Test
    public void testCreateEmptyUser_Fails() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "CreateEmptyUser_Fails", "Manual");
        timingRecorder.startTimer();
        
        try {
            // Given I am on the main page
            MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();

            AdminDashboardPage adminDashboard = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");
            
            SpecialPages specialPages = adminDashboard.navigateToSpecialPages();

            CreateAccountPage createAccountPage = specialPages.createAccount(driver);
            
            // And I leave the account details empty
            // (No form filling)
            
            // And I click create account
            createAccountPage.submitForm();
            
            // Then I should see an error message
            assertTrue(createAccountPage.isErrorMessageDisplayed(), "Error message should be displayed");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
