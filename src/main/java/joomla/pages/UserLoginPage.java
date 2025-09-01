package joomla.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.BasePage;
import utils.WebDriverUtils;

public class UserLoginPage extends BasePage {

  @FindBy(id = "username")
  private WebElement usernameField;

  @FindBy(id = "password")
  private WebElement passwordField;

  @FindBy(xpath = "//button[contains(text(), 'Log in')]")
  private WebElement btnLogin;

  @FindBy(xpath = "//*[@id=\"top\"]/div/nav/div[2]/ul/li[3]/a")
  private WebElement authLoginLink;

  public UserLoginPage(WebDriver driver) {
    super(driver);
  }

  public UserLoginPage clickAuthorLoginSection() {
    authLoginLink.click();
    return this;
  }

  public UserLoginPage fillForm(String username, String password) {
    usernameField.clear();
    usernameField.sendKeys(username);

    passwordField.clear();
    passwordField.sendKeys(password);

    return this;
  }

  public UserProfilePage submitForm() {
    btnLogin.click();
    try {
      WebDriverUtils.waitForPresence(driver, By.xpath("//legend[contains(text(), 'Edit Your Profile')]"), 2);
    } catch (Exception e) {
      System.out.println("UserProfilePage was not loaded from submitForm");
    }
    return new UserProfilePage(driver);
  }
  
  public boolean isUsernameFieldInvalid() {
    try {
        // Wait for the username field to have the "invalid" class
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(usernameField, "class", "invalid"));

        // Check if the "invalid" class is present
        String classAttribute = usernameField.getAttribute("class");
        System.out.println("Username field class attribute: " + classAttribute);
        return classAttribute != null && classAttribute.contains("invalid");
    } catch (Exception e) {
        System.out.println("Error checking username field: " + e.getMessage());
        return false;
    }
}

  public boolean isErrorMessageDisplayed() {
    try {
      WebElement errorMessage = driver.findElement(By.className("alert-message"));
      return errorMessage.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

}
