package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

public class SpecialPage extends BasePage {
  
  public SpecialPage(WebDriver driver) {
    super(driver);
  }

  public boolean isUserPresent(String user) {
    try {
      return WebDriverUtils.waitForVisibility(driver, By.xpath("//a[contains(., '" + user + "')]"), 5).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }
}
