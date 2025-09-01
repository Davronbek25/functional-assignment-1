package joomla.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

public class UserProfilePage extends BasePage {

  @FindBy(xpath = "//a[@href='/index.php/log-out']")
  private WebElement logOutLink;

  @FindBy(xpath = "//a[@href='/index.php/author-login']")
  private WebElement authLink;

  @FindBy(xpath = "//a[@href='/index.php/create-a-post']")
  private WebElement createPostLink;

  @FindBy(xpath = "//a[@href='/administrator' and contains(., 'Site Administrator')]")
  private WebElement siteAdminLink;

  @FindBy(xpath = "//a[contains(text(), 'Test Article 01')]")
  private WebElement articleLink;

  @FindBy(xpath = "//*[@id=\"content\"]/div[3]/div[2]/div/ul/li[2]/a")
  private WebElement editIcon;

  public UserProfilePage(WebDriver driver) {
    super(driver);
  }

  public UserProfilePage clickLogOutLink() {
    logOutLink.click();
    return this;
  }

  public UserProfilePage clickAuthLink() {
    authLink.click();
    return this;
  }

  public UserProfilePage clickArticleLink() {
    articleLink.click();
    return this;
  }

  public UserProfilePage clickSettingsBtn() {
    try {
      WebDriverUtils.waitAndClick(driver, By.className("dropdown-toggle"), 5);
    } catch (Exception e) {
      System.out.println("UserProfilePage clickSettingsBtn(): " + e);
    }
    return this;
  }

  public NewArticleFormPage clickCreatePostLink() {
    createPostLink.click();
    return new NewArticleFormPage(driver);
  }

  public NewArticleFormPage clickEditIcon() {
    editIcon.click();
    return new NewArticleFormPage(driver);
  }

  public AdminLoginPage clickSiteAdminLink() {
    siteAdminLink.click();
    try {
      WebDriverUtils.waitForVisibility(driver, By.id("mod-login-username"), 5);
    } catch (Exception e) {
      System.out.println("clickSiteAdminLink: " + e);
    }
    return new AdminLoginPage(driver);
  }

  public void clickLogOutBtn() {
    try {
      WebDriverUtils.waitAndClick(driver, By.xpath("//button[contains(., 'Log out')]"), 5);
    } catch (Exception e) {
      System.out.println("clickLogOutLink: " + e);
    }
  }

  public boolean isUserProfilePageLoaded(String name) {
    try {
      return WebDriverUtils.waitForVisibility(driver, By.xpath("//input[@value='" + name +"']"), 5).isDisplayed();
    } catch (Exception e) {
      System.out.println("isUserProfilePageLoaded: " + e);
      return false;
    }
  }
}
