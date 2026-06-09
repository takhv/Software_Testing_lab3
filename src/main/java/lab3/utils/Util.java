package lab3.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public final class Util {
  private Util() {}

  public static final String BASE_URL = "https://www.lamoda.ru";

  public static WebDriver createDriver() {
    String browser = "chrome"; // firefox chrome
    WebDriver driver;

    switch(browser.toLowerCase()){
      case "chrome":
        driver = new ChromeDriver();
        break;
      case "firefox": 
      default:
        driver = new FirefoxDriver();
        break;
    }
    
    driver.manage().window().maximize();
    return driver;
  }

  public static void quitDriver(WebDriver driver) {
    if (driver != null) {
      driver.quit();
    }
  }

  public static WebElement getElementBySelector(WebDriver driver, By selector, Duration timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    return wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
  }

  public static WebElement getElementBySelector(WebDriver driver, By selector) {
    return getElementBySelector(driver, selector, Duration.ofSeconds(10));
  }

  public static void waitUntilPageLoads(WebDriver driver, Duration timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    wait.until(webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete"));
  }

  private static final By[] OVERLAY_CLOSE_BUTTONS = {
      By.cssSelector("button[data-qa='accept-cookies']"),
      By.xpath("//button[contains(normalize-space(), 'Принять') ]"),
      By.xpath("//button[contains(normalize-space(), 'Соглас') ]"),
      By.xpath("//button[contains(normalize-space(), 'Понятно') ]"),
      By.xpath("//button[contains(@aria-label, 'Закрыть') ]")
  };

  public static void acceptCookies(WebDriver driver) {
    try {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

      WebElement btn = wait.until(
        ExpectedConditions.elementToBeClickable(
          By.xpath("//section[contains(@class,'_notifyMessage_')]//button")
        )
      );

      btn.click();

      wait.until(ExpectedConditions.invisibilityOf(btn));

      System.out.println("Cookie banner closed");

    } catch (Exception e) {
      System.out.println("Cookie banner not found");
    }
  }

  public static void dismissCommonOverlays(WebDriver driver) {
    for (By closeButton : OVERLAY_CLOSE_BUTTONS) {
      List<WebElement> elements = driver.findElements(closeButton);
      for (WebElement element : elements) {
        if (element.isDisplayed() && element.isEnabled()) {
          try {
            element.click();
          } catch (RuntimeException ignored) {
          }
        }
      }
    }
  }
}
