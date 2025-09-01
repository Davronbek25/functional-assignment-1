package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

public class CreatePage extends BasePage {

  @FindBy(xpath = "//*[@id=\"bodyContent\"]/div[5]/div[1]/div[1]/p")
  private WebElement contentField;

  @FindBy(xpath = "//*[@id=\"ca-edit\"]/a")
  private WebElement sourceBtn;
  
  public CreatePage(WebDriver driver) {
    super(driver);
  }

  public CreatePage fillForm(String content) {
    contentField.clear();
    contentField.sendKeys(content);

    return this;
  }

  public CreateTemplatePage pressCreateSrc() {
    sourceBtn.click();
    return new CreateTemplatePage(driver);
  }

  public CreatePage createLink(String link) {
    contentField.sendKeys(" [[");
    try {
      WebDriverUtils.clearAndSendKeys(driver, By.xpath("//*[@id='ooui-3']/div/div/div/div/div/div[1]/div/input"), link + Keys.RETURN, 5);
      contentField.sendKeys(Keys.ARROW_RIGHT);
      contentField.sendKeys(Keys.ARROW_RIGHT);
      contentField.sendKeys(" link added");
    } catch (Exception e) {
      System.out.println("error while creating link: " + e);
    }
    return this;
  }

  public boolean isPopUpPresent() {
    try {
      return driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div/div[3]/div/div[2]/div[2]/div[1]/div[1]/span[3]/a/span[1]")).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public Page submitForm(String summary) {
    try {
      WebDriverUtils.waitAndClick(driver, By.xpath("//*[@id=\"content\"]/div/div[1]/div/div[3]/div/div[1]/div[1]/div[4]/div/span/a"), 5);
      if(summary != null) {
        WebDriverUtils.clearAndSendKeys(driver, By.xpath("/html/body/div[5]/div/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/textarea"), summary, 5);
      }
      WebDriverUtils.waitAndClick(driver, By.xpath("/html/body/div[5]/div/div/div[1]/div[2]/div[1]/div/div[1]/span/a"), 5);
    } catch (Exception e) {
      System.out.println("error while content of page writing: " + e);
    }

    return new Page(driver);
  }
}
