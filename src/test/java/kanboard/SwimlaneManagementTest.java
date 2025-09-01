package kanboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import kanboard.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;
import utils.WebDriverUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Kanboard swimlane management functionality
 * Using AI-assisted approach
 */
public class SwimlaneManagementTest {
    
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
        driver.findElement(By.className("avatar-letter")).click();
        try {
            WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"dropdown\"]/ul/li[10]/a"), 5);
        } catch (Exception e) {
            System.out.println("Logout error" + e);
        }
    }
    
    /**
     * Test case for adding a new swimlane
     * Corresponds to 08_AddNewSwimlane.feature
     */
    @Test
    public void testAddNewSwimlane() {
        // Start timing
        timingRecorder = new TimingRecorder("Kanboard", "AddNewSwimlane", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in as an admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("admin", "admin");
            DashboardPage dashboardPage = loginPage.clickLoginButton();
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");

            dashboardPage.clickRishotkaIcon();
            ProjectBoardPage boardPage = dashboardPage.clickSettings();
            assertTrue(boardPage.isBoardLoaded("Test 2"), "boardPage.isBoardLoaded() failed");
            
            // When I click on new swimlane
            boardPage.clickSwimlaneLink();
            NewSwimlanePage newSwimlanePage = boardPage.navigateToNewSwimlane();
            
            // And I fill in the swimlane details
            // And I click save
            String swimlaneName = "New Swimlane 3";
            newSwimlanePage.fillForm(swimlaneName);
            boardPage = newSwimlanePage.clickSaveBtn();
            
            
            // Then the swimlane should be created
            assertTrue(newSwimlanePage.confirmNewSwimlane(swimlaneName), "Project board should be loaded after swimlane creation");
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for adding an empty swimlane
     * Corresponds to 09_AddEmptySwimlane.feature
     */
    @Test
    public void testAddEmptySwimlane() {
        // Start timing
        timingRecorder = new TimingRecorder("Kanboard", "AddEmptySwimlane", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in as an admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("admin", "admin");
            DashboardPage dashboardPage = loginPage.clickLoginButton();
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");

            dashboardPage.clickRishotkaIcon();
            ProjectBoardPage boardPage = dashboardPage.clickSettings();
            assertTrue(boardPage.isBoardLoaded("Test 2"), "boardPage.isBoardLoaded() failed");
            
            // When I click on new swimlane
            boardPage.clickSwimlaneLink();
            NewSwimlanePage newSwimlanePage = boardPage.navigateToNewSwimlane();
            boardPage = newSwimlanePage.clickSaveBtn();
            
            
            // Then the swimlane should be created
            assertTrue(newSwimlanePage.isErrorMessageDisplayed(), "newSwimlanePage.isErrorMessageDisplayed() failed");
            newSwimlanePage.clickCloseBtn();
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
