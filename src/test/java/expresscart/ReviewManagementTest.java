package expresscart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import expresscart.pages.*;
import utils.TimingRecorder;
import utils.WebDriverSetup;

import static org.junit.jupiter.api.Assertions.*;


public class ReviewManagementTest {
  
  private WebDriver driver;
  private TimingRecorder timingRecorder;

  @BeforeEach
  public void setUp() {
    driver = WebDriverSetup.setupChromeDriver(false);
  }

  @AfterEach
  public void tearDown() {
    if(driver != null) {
      driver.quit();
    }
  }

  @Test
  public void testAddReview() {
    
    timingRecorder = new TimingRecorder("ExpressCart", "AddReviewTest", "AI-Assisted");
    timingRecorder.startTimer();

    try {
      driver.get("http://localhost:3000/");
      CustomerLoginPage customerLoginPage = new CustomerLoginPage(driver);
      customerLoginPage.loginPage();
      assertTrue(customerLoginPage.isCustomerLoginPageLoaded(), "CustomerLoginPage was supposed to be loaded");
      
      customerLoginPage.fillForm("test@test.com", "e2eW3Bt3s71nGB3nchM4rK");
      CustomerDashboardPage customerDashboardPage = customerLoginPage.submitBtn();
      assertTrue(customerDashboardPage.isCustomerDashboardPageLoaded(), "CustomerDashboardPage was supposed to be loaded");

      customerDashboardPage.HomeSection();
      // String product = "t shirt";
      String product = "NewProduct000";
      ReviewsManagementPage reviewsManagementPage = customerDashboardPage.clickProduct(product);
      assertTrue(reviewsManagementPage.isReviewsManagementPageLoaded(), "Product was supposed to be loaded");
      
      NewReviewFormPage newReviewFormPage = reviewsManagementPage.clickNewReview();
      assertTrue(newReviewFormPage.isProductReviewLoaded(), "Product review was supposed to be loaded");
      
      String title = "Review000";
      String description = "Description000";
      String rating = "5";
      newReviewFormPage.fillForm(title, description, rating);
      newReviewFormPage.submitForm();

      assertTrue(reviewsManagementPage.isReviewPresent(title, description, rating), "Review was supposed to be present");;
    } finally {
      timingRecorder.stopTimer();
    }
  }
  
  @Test
  public void testAddEmptyReview() {
    
    timingRecorder = new TimingRecorder("ExpressCart", "AddEmptyReviewTest", "AI-Assisted");
    timingRecorder.startTimer();

    try {
      driver.get("http://localhost:3000/");
      CustomerLoginPage customerLoginPage = new CustomerLoginPage(driver);
      customerLoginPage.loginPage();
      assertTrue(customerLoginPage.isCustomerLoginPageLoaded(), "CustomerLoginPage was supposed to be loaded");
      
      customerLoginPage.fillForm("test@test.com", "e2eW3Bt3s71nGB3nchM4rK");
      CustomerDashboardPage customerDashboardPage = customerLoginPage.submitBtn();
      assertTrue(customerDashboardPage.isCustomerDashboardPageLoaded(), "CustomerDashboardPage was supposed to be loaded");

      customerDashboardPage.HomeSection();
      // String product = "t shirt";
      String product = "NewProduct000";
      ReviewsManagementPage reviewsManagementPage = customerDashboardPage.clickProduct(product);
      assertTrue(reviewsManagementPage.isReviewsManagementPageLoaded(), "Product was supposed to be loaded");
      
      NewReviewFormPage newReviewFormPage = reviewsManagementPage.clickNewReview();
      assertTrue(newReviewFormPage.isProductReviewLoaded(), "Product review was supposed to be loaded");
      
      newReviewFormPage.submitForm();

      assertTrue(newReviewFormPage.isErrorMessageDisplayed(), "Error message was supposed to be present");
    } finally {
      timingRecorder.stopTimer();
    }
  }
}
