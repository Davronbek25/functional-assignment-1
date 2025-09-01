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
 * Test class for Kanboard column management functionality
 * Using AI-assisted approach
 */
public class ColumnManagementTest {
    
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
     * Test case for adding a new column
     * Corresponds to 06_AddNewColumn.feature
     */
    @Test
    public void testAddNewColumn() {
        // Start timing
        timingRecorder = new TimingRecorder("Kanboard", "AddNewColumn", "AI-Assisted");
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
            
            boardPage.clickColumnsLink();
            // When I click on new column
            NewColumnPage newColumnPage = boardPage.navigateToNewColumn();
            
            // And I fill in the column details
            // And I click save
            String columnTitle = "New Column 3";
            newColumnPage.fillForm(columnTitle);
            boardPage = newColumnPage.clickSaveBtn();
            
            
            // Then the column should be created
            assertTrue(boardPage.confirmNewColumn(columnTitle), "Project board should be loaded after column creation");
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for adding an empty column
     * Corresponds to 07_AddEmptyColumn.feature
     */
    @Test
    public void testAddEmptyColumn() {
        // Start timing
        timingRecorder = new TimingRecorder("Kanboard", "AddEmptyColumn", "AI-Assisted");
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
            
            boardPage.clickColumnsLink();
            // When I click on new column
            NewColumnPage newColumnPage = boardPage.navigateToNewColumn();
            newColumnPage.clickSaveBtn();
            
            // Then the column should be created
            assertTrue(newColumnPage.isErrorMessageDisplayed(), "newColumnPage.isErrorMessageDisplayed() failed");
            newColumnPage.clickCloseBtn();
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
