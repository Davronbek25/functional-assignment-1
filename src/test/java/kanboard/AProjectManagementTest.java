package kanboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import kanboard.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;
import utils.WebDriverUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

/**
 * Test class for Kanboard project management functionality
 * Using AI-assisted approach
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AProjectManagementTest {

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
     * Test case for adding a new project
     * Corresponds to 01_AddNewProject.feature
     */
    @Test
    @Order(1)
    public void testAddNewProject() {
        // Start timing
        timingRecorder = new TimingRecorder("Kanboard", "AddNewProject", "AI-Assisted");
        timingRecorder.startTimer();

        try {
            // Given I am logged in as an admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("admin", "admin");
            DashboardPage dashboardPage = loginPage.clickLoginButton();
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");

            // And I click on new project
            NewProjectPage newProjectPage = dashboardPage.clickNewProjectLink();

            // And I fill in the project details
            String projectName = "Test 2";
            // String projectName = "Test Project " + System.currentTimeMillis();
            newProjectPage.fillForm(projectName);

            // And I click save
            ProjectBoardPage boardPage = newProjectPage.submitForm();

            // Then the project should be created
            assertTrue(boardPage.isBoardLoaded(projectName), "Project board should be loaded after creation");

            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }

    /**
     * Test case for adding an empty project
     * Corresponds to 02_AddEmptyProject.feature
     */
    @Test
    @Order(2)
    public void testAddEmptyProject() {
        // Start timing
        timingRecorder = new TimingRecorder("Kanboard", "AddEmptyProject", "AI-Assisted");
        timingRecorder.startTimer();

        try {
            // Given I am logged in as an admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("admin", "admin");
            DashboardPage dashboardPage = loginPage.clickLoginButton();
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");

            // And I click on new project
            NewProjectPage newProjectPage = dashboardPage.clickNewProjectLink();

            // And I click save
            newProjectPage.submitForm();

            // Then the project should be created
            assertTrue(newProjectPage.isErrorMessageDisplayed(), "newProjectPage.isErrorMessageDisplayed() failed");

            newProjectPage.clickCloseModal();
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
