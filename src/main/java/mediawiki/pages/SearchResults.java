package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

public class SearchResults extends BasePage{
  
  public SearchResults(WebDriver driver) {
    super(driver);
  }

  public SearchResults chooseResult(String result) {
    driver.findElement(By.xpath("//a[contains(., '" + result + "')]")).click();
    return this;
  }

  public CreatePage createPage(String pageName) {
    driver.findElement(By.xpath("//a[contains(., '" + pageName + "')]")).click();
    try {
      WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"content\"]/div/div[1]/div/div[3]/div/div[2]/div[2]/div[1]/div[1]/span[3]/a"), 5);
    } catch (Exception e) {
      System.out.println("error while page creation: " + e);
    }
    return new CreatePage(driver);
  }

  public EditPage editPage() {
    try {
      WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"ca-ve-edit\"]/a"), 5);
    } catch (Exception e) {
      System.out.println("error finding editBtn: " + e);
    }
    return new EditPage(driver);
  }

  public boolean isResultPresent(String result) {
    try {
      return WebDriverUtils.waitForPresence(driver, By.xpath("//*[@id=\"mw-content-text\"]/div[2]/div[2]/ul[1]/li/div[1]/a[contains(., '" + result + "')]"), 5).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }
}
