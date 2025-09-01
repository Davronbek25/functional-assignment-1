package joomla;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import joomla.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;
import utils.WebDriverUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

/**
 * Test class for Joomla user management functionality
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
    
    /**
     * Test case for adding a new user
     * Corresponds to 04_AddUser.feature
     */
    @Test
    public void testAddUser() {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "AddUser", "Manual");
        timingRecorder.startTimer();
        
        try {
            driver.get("http://localhost:8080");
            UserLoginPage signinPage = new UserLoginPage(driver);
            signinPage.clickAuthorLoginSection();

            // And I log in as the super user
            signinPage.fillForm("administrator", "e2eW3Bt3s71nGB3nchM4rK");
            UserProfilePage profilePage = signinPage.submitForm();
            // Then I should be logged in
            assertTrue(profilePage.isUserProfilePageLoaded("Super User"), "Author page should be loaded after login");

            AdminLoginPage adminLoginPage = profilePage.clickSiteAdminLink();

            // Get the current window handle
            String originalWindow = driver.getWindowHandle();

            // Wait for the new tab to open
            WebDriverUtils.waitForNewTab(driver, 5); // Custom utility method to wait for new tabs

            // Get all window handles
            Set<String> windowHandles = driver.getWindowHandles();

            // Switch to the new tab
            for (String handle : windowHandles) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            // Verify the new tab is loaded
            assertTrue(adminLoginPage.isLoaded(), "AdminLoginPage is not loaded in the new tab");
            
            adminLoginPage.login("administrator", "e2eW3Bt3s71nGB3nchM4rK");
            AdminDashboardPage dashboardPage = adminLoginPage.clickLoginBtn();
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");
            
            // When I navigate to the users page
            UsersManagementPage usersPage = dashboardPage.navigateToUsers();
            
            // And I click on new user
            NewUserFormPage newUserPage = usersPage.clickNewUser();
            
            // And I fill in the user details
            // String username = "testuser" + System.currentTimeMillis();
            String username = "tuser01";
            // String name = "Test User " + System.currentTimeMillis();
            String name = "Test User";
            // String email = username + "@example.com";
            String email = "testmail@example.com";
            newUserPage.fillForm(name, username, "tpassword", email);
            
            // And I click save
            usersPage = newUserPage.submitForm();
            
            // Then the user should be created
            assertTrue(usersPage.isUserPresent(username), "User should be present in the list");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for logging in as a new user
     * Corresponds to 05_LoginAsNewUser.feature
     */
    @Test
    public void testLoginAsNewUser() {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "LoginAsNewUser", "Manual");
        timingRecorder.startTimer();
        
        try {
            driver.get("http://localhost:8080");
            UserLoginPage signinPage = new UserLoginPage(driver);
            signinPage.clickAuthorLoginSection();

            // And I log in as the new user
            signinPage.fillForm("tuser01", "tpassword");
            UserProfilePage profilePage = signinPage.submitForm();
            // Then I should be logged in
            assertTrue(profilePage.isUserProfilePageLoaded("Test User"), "Author page should be loaded after login");

            profilePage.clickAuthLink();
            profilePage.clickLogOutBtn();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for bad login
     * Corresponds to 02_BadLogin.feature
     */
    @Test
    public void testBadLogin() {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "BadLogin", "Manual");
        timingRecorder.startTimer();
        
        try {
            driver.get("http://localhost:8080");
            UserLoginPage signinPage = new UserLoginPage(driver);
            signinPage.clickAuthorLoginSection();

            signinPage.fillForm("administrator", "wrongpassword");
            signinPage.submitForm();
            assertTrue(signinPage.isErrorMessageDisplayed(), "error in signinPage.isErrorMessageDisplayed()");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for empty login
     * Corresponds to 03_EmptyLogin.feature
     */
    @Test
    public void testEmptyLogin() {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "EmptyLogin", "Manual");
        timingRecorder.startTimer();
        
        try {
            driver.get("http://localhost:8080");
            UserLoginPage signinPage = new UserLoginPage(driver);
            signinPage.clickAuthorLoginSection();

            signinPage.submitForm();
            signinPage.submitForm();
            assertTrue(signinPage.isUsernameFieldInvalid(), "error in signinPage.isUsernameFieldInvalid()");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
