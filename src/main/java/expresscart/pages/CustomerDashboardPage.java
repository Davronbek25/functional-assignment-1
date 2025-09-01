package expresscart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.BasePage;
import utils.WebDriverUtils;


public class CustomerDashboardPage extends BasePage {

  @FindBy(xpath = "//a[@href='/' and contains(., 'Home')]")
  private WebElement homeBtn;

  public CustomerDashboardPage(WebDriver driver) {
    super(driver);
  }

  public CustomerDashboardPage HomeSection() {
    homeBtn.click();
    return this;
  }

  public ReviewsManagementPage clickProduct(String product) {
    try {
      WebDriverUtils.waitAndClick(driver, By.xpath("//a[.//h3[contains(., '" + product + "')]]"), 5);
    } catch (Exception e) {
      System.out.println("clickProduct: " + e);
    }
    return new ReviewsManagementPage(driver);
  }

  public boolean isCustomerDashboardPageLoaded() {
    try {
      return WebDriverUtils.waitForVisibility(driver, By.xpath("//h5[@class='card-heading' and contains(., 'Customer details')]"), 5).isDisplayed();
    } catch (Exception e) {
      System.out.println("isCustomerDashboardPageLoaded: " + e);
      return false;
    }
  }
}
