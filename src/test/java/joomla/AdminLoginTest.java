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
 * Test class for Joomla admin login functionality
 */
public class AdminLoginTest {

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
     * Test case for admin login
     * Corresponds to 01_AdminLoginTest.feature
     */
    @Test
    public void testAdminLogin() {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "AdminLoginTest", "Manual");
        timingRecorder.startTimer();

        try {
            driver.get("http://localhost:8080");
            UserLoginPage signinPage = new UserLoginPage(driver);
            signinPage.clickAuthorLoginSection();

            // And I log in as the new user
            signinPage.fillForm("administrator", "e2eW3Bt3s71nGB3nchM4rK");
            UserProfilePage profilePage = signinPage.submitForm();
            // Then I should be logged in
            assertTrue(profilePage.isUserProfilePageLoaded("Super User"), "Author page should be loaded after login");

            profilePage.clickLogOutLink();
            profilePage.clickLogOutBtn();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }

    /**
     * Test case for bad site admin login
     * Corresponds to 06_BadSiteAdminLogin.feature
     */
    @Test
    public void testBadSiteAdminLogin() throws InterruptedException {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "BadSiteAdminLogin", "Manual");
        timingRecorder.startTimer();

        try {
            driver.get("http://localhost:8080");
            UserLoginPage signinPage = new UserLoginPage(driver);
            signinPage.clickAuthorLoginSection();

            // And I log in as the new user
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
            
            adminLoginPage.login("administrator", "wrongpassword");
            adminLoginPage.clickLoginBtn();

            assertTrue(adminLoginPage.isErrorMessageDisplayed(), "error in case testBadSiteAdminLogin");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }

    /**
     * Test case for empty site admin login
     * Corresponds to 07_EmptySiteAdminLogin.feature
     */
    @Test
    public void testEmptySiteAdminLogin() {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "EmptySiteAdminLogin", "Manual");
        timingRecorder.startTimer();

        try {
            driver.get("http://localhost:8080");
            UserLoginPage signinPage = new UserLoginPage(driver);
            signinPage.clickAuthorLoginSection();

            // And I log in as the new user
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
            
            // adminLoginPage.login("administrator", "wrongpassword");
            adminLoginPage.clickLoginBtn();

            assertTrue(adminLoginPage.isErrorMessageDisplayed(), "error in case testBadSiteAdminLogin");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
