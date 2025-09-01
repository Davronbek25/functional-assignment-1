package mediawiki;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import mediawiki.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;
import utils.WebDriverUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MediaWiki article management functionality
 * Using Manual approach
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PageManagementTest {
    
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
     * Corresponds to 02_CloseInitialEditorPopupTest.feature
     */
    // @Test
    // public void testCloseInitialEditorPopup() {
    //     TimingRecorder timingRecorder = new TimingRecorder("MediaWiki", "CloseInitialEditorPopupTes", "Manual");
    //     timingRecorder.startTimer();
        
    //     try {
    //         MainPage mainPage = new MainPage(driver);
    //         mainPage.navigateTo();

    //         LoginPage loginPage = mainPage.clickLoginLink();
    //         AdminDashboardPage adminDashboardPage = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");

    //         assertTrue(adminDashboardPage.isLoggedIn(), "admin is supposed to be logged in");

    //         String pageTitle = "Test Article " + System.currentTimeMillis();
    //         SearchResults searchResults = adminDashboardPage.navigateToSearchResults(pageTitle);
            
    //         CreatePage createPage = searchResults.createPage(pageTitle);
    //         assertFalse(createPage.isPopUpPresent(), "Pop should not be present");
    //     } catch (Exception e) {
    //         timingRecorder.stopTimer();
    //     }
    // }
    
    /**
     * Corresponds to 03_CreatePageTest.feature
     */
    @Order(1)
    @Test
    public void testCreatePage() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "CreatePageTest", "Manual");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in
           MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();

            AdminDashboardPage adminDashboard = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(adminDashboard.isLoggedIn(), "User should be logged in");
            
            String pageTitle = "Software testing";
            // String pageTitle = "Test Article " + System.currentTimeMillis();
            SearchResults searchResults = adminDashboard.navigateToSearchResults(pageTitle);
            
            CreatePage createPage = searchResults.createPage(pageTitle);
            String pageContent = "Test Content testCreatePage";            
            createPage.fillForm(pageContent);

            Page page = createPage.submitForm("New Page created");

            assertTrue(page.isPagePresent(pageContent), "Page should be created");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Corresponds to 04_CreateAndLinkPageTest.feature
     */
    @Order(2)
    @Test
    public void testCreateAndLinkPage() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "CreateAndLinkPageTest", "Manual");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in
            MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();

            AdminDashboardPage adminDashboard = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(adminDashboard.isLoggedIn(), "User should be logged in");
            
            String pageTitle = "E2E Web Testing";
            // String pageTitle = "Test Article " + System.currentTimeMillis();
            SearchResults searchResults = adminDashboard.navigateToSearchResults(pageTitle);
            
            CreatePage createPage = searchResults.createPage(pageTitle);
            String pageContent = "Test Content testCreateAndLinkPage";
            createPage.fillForm(pageContent);
            createPage.createLink("Software testing");

            String pageSummary = "Page created";
            Page page = createPage.submitForm(pageSummary);
            assertTrue(page.isPagePresent(pageContent), "Page should be created");
            
            page.clickPage("Software testing");
            assertTrue(page.confirmPage("Test Content testCreatePage"), "page.isPagePresent failed");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Corresponds to 06_EditPageTest.feature
     */
    @Order(3)
    @Test
    public void testEditPage() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "EditPageTest", "Manual");
        timingRecorder.startTimer();
        
        try {
            MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();

            AdminDashboardPage adminDashboard = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(adminDashboard.isLoggedIn(), "User should be logged in");
            
            String pageTitle = "Software testing";
            SearchResults searchResults = adminDashboard.navigateToSearchResults(pageTitle);
            
            CreatePage createPage = new CreatePage(driver);

            searchResults.chooseResult(pageTitle);
            EditPage editPage = searchResults.editPage();
            String extraContent = " edited!";
            editPage.enterContent(extraContent);

            String pageSummary = "Page expanded";
            Page page = createPage.submitForm(pageSummary);
            assertTrue(page.isPagePresent(extraContent), "Page should be created");  
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Corresponds to 07_CreateTemplateTest.feature
     */
    @Order(4)
    @Test
    public void testCreateTemplate() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "CreateTemplateTest", "Manual");
        timingRecorder.startTimer();
        
        try {
            MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();

            AdminDashboardPage adminDashboard = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(adminDashboard.isLoggedIn(), "User should be logged in");
            
            // String pageTitle = "Template:Software " + System.currentTimeMillis();
            String pageTitle = "Template:Software";
            SearchResults searchResults = adminDashboard.navigateToSearchResults(pageTitle);
            
            CreatePage createPage = searchResults.createPage(pageTitle);

            CreateTemplatePage templatePage = new CreateTemplatePage(driver);
            String pageContent = "Developer: {{{dev}}} Latest version: {{{ver}}}";
            String summary = "Page created";
            templatePage.fillTemplate(pageContent, summary);

            Page page = new Page(driver);

            assertTrue(page.isPagePresent(pageContent), "Page should be created");  
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Corresponds to 08_CreatePageFromSourceTest.feature
     */
    @Order(5)
    @Test
    public void testCreatePageFromSource() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "CreatePageFromSourceTest", "Manual");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in
           MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();

            AdminDashboardPage adminDashboard = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(adminDashboard.isLoggedIn(), "User should be logged in");
            
            String pageTitle = "Selenium WebDriver";
            // String pageTitle = "Test Article " + System.currentTimeMillis();
            SearchResults searchResults = adminDashboard.navigateToSearchResults(pageTitle);
            
            CreatePage createPage = searchResults.createPage(pageTitle);
            String pageContent = "Test Content " + System.currentTimeMillis();
            
            CreateTemplatePage templatePage = createPage.pressCreateSrc();

            String summary = "Page created";
            templatePage.fillTemplate(pageContent, summary);

            Page page = new Page(driver);

            assertTrue(page.isPagePresent(pageContent), "Page should be created");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }

    /**
     * Corresponds to 09_ApplyTemplateTest.feature
     */
    @Order(6)
    @Test
    public void testApplyTemplate() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "ApplyTemplateTest", "Manual");
        timingRecorder.startTimer();
        
        try {
            // Given I am logged in
            MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();

            AdminDashboardPage adminDashboard = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(adminDashboard.isLoggedIn(), "User should be logged in");
            
            String pageTitle = "Selenium WebDriver";
            // String pageTitle = "Test Article " + System.currentTimeMillis();
            SearchResults searchResults = adminDashboard.navigateToSearchResults(pageTitle);
            searchResults.chooseResult(pageTitle);
            
            try {
                WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"ca-edit\"]/a"), 5);
            } catch (Exception e) {
                System.out.println("error pressing edit source: " + e);
            }
            String pageContent = "{{Software|dev=Selenium|ver=3.141.59}}";
            // String pageContent = "Test Content " + System.currentTimeMillis();
            
            CreateTemplatePage templatePage = new CreateTemplatePage(driver);

            String summary = "Page updated";
            templatePage.fillTemplate(pageContent, summary);

            Page page = new Page(driver);

            assertTrue(page.isPagePresent("Developer: Selenium Latest version: 3.141.59"), "Page should be created");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }

    /**
     * Corresponds to 10_AddCategoryTest.feature
     */
    @Order(7)
    @Test
    public void testAddCategory() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "AddCategoryTest", "Manual");
        timingRecorder.startTimer();
        
        try {
            MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();

            AdminDashboardPage adminDashboard = loginPage.login("admin", "e2eW3Bt3s71nGB3nchM4rK");
            assertTrue(adminDashboard.isLoggedIn(), "User should be logged in");
            
            String pageTitle = "Selenium WebDriver";
            SearchResults searchResults = adminDashboard.navigateToSearchResults(pageTitle);
            
            CreatePage createPage = new CreatePage(driver);

            searchResults.chooseResult(pageTitle);
            EditPage editPage = searchResults.editPage();

            String category = "Browser automation tools";
            CategoryPage categoryPage = editPage.navigateToCategory();
            categoryPage.putCategory(category);

            String pageSummary = "Added category";
            Page page = createPage.submitForm(pageSummary);
            assertTrue(categoryPage.isCategoryPresent(category), "Page should be created");  
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
}
