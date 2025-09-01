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
 * Test class for MediaWiki search functionality
 * Using Manual approach
 */
public class SearchTest {
    
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
     * Test case for searching for an article
     * Corresponds to 05_SearchPageTest.feature
     */
    @Test
    public void testSearchPage() {
        // Start timing
        timingRecorder = new TimingRecorder("MediaWiki", "SearchPageTest", "Manual");
        timingRecorder.startTimer();
        
        try {
            // Given I am on the main page
            MainPage mainPage = new MainPage(driver);
            mainPage.navigateTo();

            AdminDashboardPage adminDashboard = new AdminDashboardPage(driver);

            String pageTitle = "Software testing";
            SearchResults searchResults = adminDashboard.navigateToSearchResults(pageTitle);

            assertTrue(searchResults.isResultPresent(pageTitle), "Result is supposed to be present");
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
