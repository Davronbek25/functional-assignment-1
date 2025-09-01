package mediawiki.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

public class CreateTemplatePage extends BasePage {
  
  @FindBy(xpath = "//*[@id=\"wpSummary\"]")
  private WebElement summaryField;

  @FindBy(id = "wpSave")
  private WebElement saveBtn;
  
  public CreateTemplatePage(WebDriver driver) {
    super(driver);
  }

  public Page fillTemplate(String content, String summary) {
    try {
      WebDriverUtils.clearAndSendKeys(driver, By.id("wpTextbox1"), content, 5);
      summaryField.clear();
      summaryField.sendKeys(summary);
  
      saveBtn.click();
    } catch (Exception e) {
      System.out.println("Page fillTemplate failed: " + e);
    }
    return new Page(driver);
  }
}
