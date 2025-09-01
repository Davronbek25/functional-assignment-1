package kanboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import kanboard.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;
import utils.WebDriverUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Kanboard project editing functionality
 * Using AI-assisted approach
 */
public class ProjectEditTest {
    
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
     * Test case for editing a project
     * Corresponds to 05_EditProject.feature
     */
    @Test
    public void testEditProject() {
        // Start timing
        timingRecorder = new TimingRecorder("Kanboard", "EditProject", "AI-Assisted");
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
            
            EditProjectPage editProjectPage = boardPage.clickEditLink();
            editProjectPage.fillForm("This is the new description");
            editProjectPage.clickSaveBtn();
            boardPage = editProjectPage.clickSummaryLink();

            assertTrue(boardPage.isProjectEdited("This is the new description"), "boardPage.isProjectEdited failed");
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
