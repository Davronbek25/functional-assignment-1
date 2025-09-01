package kanboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import kanboard.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;
import utils.WebDriverUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Kanboard task management functionality
 * Using AI-assisted approach
 */
public class TaskManagementTest {
    
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
     * Test case for adding a new task
     * Corresponds to 03_AddNewTask.feature
     */
    @Test
    public void testAddNewTask() {
        // Start timing
        timingRecorder = new TimingRecorder("Kanboard", "AddNewTask", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in as an admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("admin", "admin");
            DashboardPage dashboardPage = loginPage.clickLoginButton();
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");

            ProjectBoardPage boardPage = dashboardPage.clickProject("Test 2");
            // When I click on new task
            boardPage.clickCogIcon();
            NewTaskPage newTaskPage = boardPage.navigateToNewTask();
            
            // And I fill in the task details
            String taskTitle = "task 3";
            newTaskPage.fillForm(taskTitle);
            
            // And I click save
            boardPage = newTaskPage.submitForm();
            
            // Then the task should be created
            assertTrue(boardPage.isTaskCreated(taskTitle), "boardPage.isTaskCreated() failed");

            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for adding an empty task
     * Corresponds to 04_AddEmptyTask.feature
     */
    @Test
    public void testAddEmptyTask() {
        // Start timing
        timingRecorder = new TimingRecorder("Kanboard", "AddEmptyTask", "AI-Assisted");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in as an admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("admin", "admin");
            DashboardPage dashboardPage = loginPage.clickLoginButton();
            assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");

            ProjectBoardPage boardPage = dashboardPage.clickProject("Test 2");
            // When I click on new task
            boardPage.clickCogIcon();
            NewTaskPage newTaskPage = boardPage.navigateToNewTask();
            
            // And I click save
            newTaskPage.submitForm();
            
            // Then I should see an error message
            assertTrue(newTaskPage.isErrorMessageDisplayed(), "Error message should be displayed");

            newTaskPage.clickCloseButton();
            logOut();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
