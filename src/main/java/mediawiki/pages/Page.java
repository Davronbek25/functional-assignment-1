package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

public class Page extends BasePage {
  
  // Constructor
  public Page(WebDriver driver) {
    super(driver);
  }

  public Page clickPage(String page) {
    driver.findElement(By.xpath("//a[contains(., '" + page + "')]")).click();
    return this;
  } 

  public boolean isPagePresent(String page) {
    try {
      WebDriverUtils.waitForPresence(driver, By.xpath("//*[@id=\"mw-notification-area\"]/div/div/div/span[2]"), 5);
      return WebDriverUtils.waitForPresence(driver, By.xpath("//p[contains(., '" + page + "')]"), 5).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public boolean confirmPage(String page) {
    try {
      return WebDriverUtils.waitForPresence(driver, By.xpath("//p[contains(., '" + page + "')]"), 5).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

}
