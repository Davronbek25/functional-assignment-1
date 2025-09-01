package joomla;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import joomla.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;
import utils.WebDriverUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

/**
 * Test class for Joomla article management functionality
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArticleManagementTest {
    
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
     * Test case for adding a new article
     * Corresponds to 08_AddNewArticle.feature
     */
    @Test
    @Order(1)
    public void testAddNewArticle() {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "AddNewArticle", "Manual");
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
            
            // And I click on new article
            NewArticleFormPage newArticlePage = profilePage.clickCreatePostLink();
            
            // And I fill in the article details
            String articleTitle = "Test Article 01";
            // String articleTitle = "Test Article " + System.currentTimeMillis();
            newArticlePage.fillForm(articleTitle, "This is the body of the first article for testing the platform");
            
            // And I click save
            ArticlesManagementPage articlesPage = newArticlePage.submitForm();
            
            // Then the article should be created
            assertTrue(articlesPage.isArticleCreated(), "Article should be present in the list");

            profilePage.clickAuthLink();
            profilePage.clickLogOutBtn();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for adding an empty article
     * Corresponds to 09_AddEmptyArticle.feature
     */
    @Test
    @Order(2)
    public void testAddEmptyArticle() {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "AddEmptyArticle", "Manual");
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
            
            // And I click on new article
            NewArticleFormPage newArticlePage = profilePage.clickCreatePostLink();
            
            // And I click save
            ArticlesManagementPage articlesPage = newArticlePage.submitForm();
            
            // Then the article should be created
            assertTrue(newArticlePage.isErrorMessageDisplayed(), "newArticlePage.isErrorMessageDisplayed() is not displayed");

            profilePage.clickAuthLink();
            profilePage.clickLogOutBtn();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
    
    /**
     * Test case for editing an article
     * Corresponds to 10_EditArticle.feature
     */
    @Test
    @Order(3)
    public void testEditArticle() {
        // Start timing
        timingRecorder = new TimingRecorder("Joomla", "EditArticle", "Manual");
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
            
            profilePage.clickArticleLink();
            profilePage.clickSettingsBtn();
            NewArticleFormPage newArticlePage = profilePage.clickEditIcon();
            
            // And I fill in the article details
            String articleTitle = "";
            // String articleTitle = "Test Article " + System.currentTimeMillis();
            newArticlePage.fillEditForm(articleTitle, "EDITED");
            
            // And I click save
            ArticlesManagementPage articlesPage = newArticlePage.submitForm();
            
            // Then the article should be created
            assertTrue(articlesPage.isArticleCreated(), "Article should be present in the list");

            profilePage.clickAuthLink();
            profilePage.clickLogOutBtn();
        } finally {
            // Stop timing
            timingRecorder.stopTimer();
        }
    }
}
