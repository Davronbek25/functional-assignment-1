package expresscart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.BasePage;
import utils.WebDriverUtils;


public class CustomerLoginPage extends BasePage {
  
  @FindBy(xpath = "//a[@href='/customer/account']")
  private WebElement accountIcon;
  
  @FindBy(id = "email")
  private WebElement emailField;
  
  @FindBy(id = "password")
  private WebElement passwordField;
  
  @FindBy(id = "customerloginForm")
  private WebElement signInBtn;
  

  public CustomerLoginPage(WebDriver driver) {
    super(driver);
  }

  public CustomerLoginPage loginPage() {
    accountIcon.click();
    return this;
  }

  public CustomerLoginPage fillForm(String email, String password) {
    emailField.clear();
    emailField.sendKeys(email);

    passwordField.clear();
    passwordField.sendKeys(password);

    return this;
  }

  public CustomerDashboardPage submitBtn () {
    signInBtn.click();
    return new CustomerDashboardPage(driver);
  }

  public boolean isCustomerLoginPageLoaded() {
    try {
      return WebDriverUtils.waitForVisibility(driver, By.xpath("//h2[contains(., 'Please sign in')]"), 5).isDisplayed();
    } catch (Exception e) {
      System.out.println("isCustomerLoginPageLoaded: " + e);
      return false;
    }
  }

}
