package kanboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;
import utils.WebDriverUtils;

/**
 * Page object for Kanboard login page
 */
public class EditProjectPage extends BasePage {
  
  @FindBy(xpath = "//*[@id=\"main\"]/section/div[2]/form/fieldset[1]/div/div/div[2]/textarea")
  private WebElement descriptionField;
  
  @FindBy(xpath = "//*[@id=\"main\"]/section/div[2]/form/div/div/button")
  private WebElement saveBtn;
  
  public EditProjectPage(WebDriver driver) {
    super(driver);
  }

  public EditProjectPage fillForm(String description) {
    descriptionField.clear();
    descriptionField.sendKeys(description);
    return this;
  }

  public EditProjectPage clickSaveBtn() {
    saveBtn.click();
    return this;
  }

  public ProjectBoardPage clickSummaryLink() {
    try {
      WebDriverUtils.waitAndClick(driver, By.xpath("//a[contains(text(), 'Summary')]"), 5);
    } catch (Exception e) {
      System.out.println("ProjectBoardPage clickSummaryLink() failed: " + e);
    }
    return new ProjectBoardPage(driver);
  }
}
