package expresscart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import expresscart.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ExpressCart user management functionality
 */
public class UserManagementTest {
    
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

    public void logOut() {
        driver.findElement(By.xpath("//a[@href='/admin/logout']")).click();
    }
    
    /**
     * Test case for adding a new user
     * Corresponds to 01_AddUserTest.feature
     */
    @Test
    public void testAddUser() {
        // Start timing
        timingRecorder = new TimingRecorder("ExpressCart", "AddUserTest", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given the user is on the administrative home page (/admin)
            AdminLoginPage loginPage = new AdminLoginPage(driver);
            loginPage.navigateTo();
            AdminDashboardPage dashboardPage = loginPage.login("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");
            
            // And I click on new user
            NewUserFormPage newUserPage = dashboardPage.clickNewUser();
            
            // And I fill in the user details
            // String name = "test.user" + System.currentTimeMillis();
            String name = "TestUser000";
            // String email = name + "@example.com";
            String email = "test000@test.com";
            String password = "e2eW3Bt3s71nGB3nchM4rK";
            // String password = "password";
            newUserPage.fillForm(name , email, password);
            
            // And I click create
            newUserPage.submitForm();
            UsersManagementPage usersPage = newUserPage.navigateToUsersManagementPage();
            
            // Then the user should be created
            assertTrue(usersPage.isUserPresent(email), "User should be present in the list");
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for adding an empty user
     * Corresponds to 02_AddEmptyUserTest.feature
     */
    @Test
    public void testAddEmptyUser() {
        // Start timing
        timingRecorder = new TimingRecorder("ExpressCart", "AddEmptyUserTest", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in as an admin
            AdminLoginPage loginPage = new AdminLoginPage(driver);
            loginPage.navigateTo();
            AdminDashboardPage dashboardPage = loginPage.login("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");
        
            // And I click on new user
            NewUserFormPage newUserPage = dashboardPage.clickNewUser();
            
            // And I leave the user details empty
            // (No form filling)
            
            // And I click create
            newUserPage.submitForm();
            
            // Then I should see an error message
            assertTrue(newUserPage.isErrorMessageDisplayed(), "Error message should be displayed");
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for logging in as a user
     * Corresponds to 03_LoginUserTest.feature
     */
    @Test
    public void testLoginUser() {
        // Start timing
        timingRecorder = new TimingRecorder("ExpressCart", "LoginUserTest", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given I am on the admin login page
            AdminLoginPage loginPage = new AdminLoginPage(driver);
            loginPage.navigateTo();
            
            // When I enter valid credentials
            AdminDashboardPage dashboardPage = loginPage.login("test000@test.com", "e2eW3Bt3s71nGB3nchM4rK");
            // Then I should be logged in
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for adding an existing user
     * Corresponds to 04_AddExistingUserFailsTest.feature
     */
    @Test
    public void testAddExistingUserFails() {
        // Start timing
        timingRecorder = new TimingRecorder("ExpressCart", "AddExistingUserFailsTest", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in as an admin
            AdminLoginPage loginPage = new AdminLoginPage(driver);
            loginPage.navigateTo();
            AdminDashboardPage dashboardPage = loginPage.login("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");
            
            // And I click on new user
            NewUserFormPage newUserPage = dashboardPage.clickNewUser();
            
            // And I fill in the user details with an existing email
            // String name = "non-owner";
            String name = "TestUser000";
            // String email = "non-owner@test.com";
            String email = "test000@test.com";
            String password = "e2eW3Bt3s71nGB3nchM4rK";
            // String password = "password";
            newUserPage.fillForm(name, email, password);
            
            // And I click create
            newUserPage.submitForm();
            
            // Then I should see an error message
            assertTrue(newUserPage.isAlertMessageDisplayed(), "Alert message should be displayed");
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
