package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;


public class CategoryPage extends BasePage {

  @FindBy(xpath = "/html/body/div[5]/div/div/div[1]/div[2]/div[1]/div/div[1]/span/a")
  private WebElement applyBtn;
  
  public CategoryPage(WebDriver driver) {
    super(driver);
  }

  public EditPage putCategory(String category) {

    try {
      WebDriverUtils.clearAndSendKeys(driver, By.xpath("/html/body/div[5]/div/div/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/fieldset[1]/div/div/div/div/div/div[1]/div/input"), category, 5);
      WebDriverUtils.waitForPresence(driver, By.xpath("/html/body/div[5]/div/div/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/fieldset[1]/div/div/div/div/div/div[1]/div/input"), 5).sendKeys(Keys.RETURN);;
    } catch (Exception e) {
      System.out.println("EditPage putCategory failed: " + e);
    }

    applyBtn.click();
    return new EditPage(driver);
  }

  public boolean isCategoryPresent(String category) {
    try {
      return WebDriverUtils.waitForPresence(driver, By.xpath("//a[contains(., '" + category + "')]"), 5).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }
}
