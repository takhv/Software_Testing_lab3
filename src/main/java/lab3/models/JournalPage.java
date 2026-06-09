package lab3.models;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lab3.utils.Util;

public class JournalPage extends Page {
  public JournalPage(WebDriver webDriver) {
    super(webDriver);
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(5));
  }

  public boolean isOpened() {
    return webDriver.getCurrentUrl().contains("/blog/");
  }

  public void openFirstArticle() {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

    WebElement firstArticle = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("(//a[contains(@href,'/blog/article/')])[1]")
      )
    );

    firstArticle.click();
  }
}
