package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for common Selenium WebDriver operations
 */
public class WebDriverUtils {
    
    /**
     * Waits for an element to be clickable and then clicks it
     * 
     * @param driver WebDriver instance
     * @param by locator for the element
     * @param timeoutSeconds maximum time to wait in seconds
     */
    public static void waitAndClick(WebDriver driver, By by, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
        element.click();
    }
    
    /**
     * Waits for an element to be visible
     * 
     * @param driver WebDriver instance
     * @param by locator for the element
     * @param timeoutSeconds maximum time to wait in seconds
     * @return the visible WebElement
     */
    public static WebElement waitForVisibility(WebDriver driver, By by, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    
    /**
     * Waits for an element to be present in the DOM
     * 
     * @param driver WebDriver instance
     * @param by locator for the element
     * @param timeoutSeconds maximum time to wait in seconds
     * @return the present WebElement
     */
    public static WebElement waitForPresence(WebDriver driver, By by, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    
    /**
     * Scrolls to an element using JavaScript
     * 
     * @param driver WebDriver instance
     * @param element the element to scroll to
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitForNewTab(WebDriver driver, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(d -> d.getWindowHandles().size() > 1);
    }
    
    /**
     * Clears a field and enters text
     * 
     * @param driver WebDriver instance
     * @param by locator for the element
     * @param text text to enter
     * @param timeoutSeconds maximum time to wait in seconds
     */
    public static void clearAndSendKeys(WebDriver driver, By by, String text, int timeoutSeconds) {
        WebElement element = waitForVisibility(driver, by, timeoutSeconds);
        element.clear();
        element.sendKeys(text);
    }
}
