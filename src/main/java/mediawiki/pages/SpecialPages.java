package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

public class SpecialPages extends BasePage {

  @FindBy(xpath = "//*[@id=\"mw-content-text\"]/div[5]/ul/li[1]/a")
  private WebElement createAccountLink;

  // Constructor
  public SpecialPages(WebDriver driver) {
    super(driver);
  }

  public CreateAccountPage createAccount(WebDriver driver) {
    createAccountLink.click();
    return new CreateAccountPage(driver);
  }

}