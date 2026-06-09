package lab3.models;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lab3.utils.Util;

public class ArticlePage extends Page {

  public ArticlePage(WebDriver driver) {
    super(driver);
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(5));
  }

  public boolean hasCollectionButton() {
    return !webDriver.findElements(
      By.xpath("//button[contains(., 'Смотреть подборку')]")
    ).isEmpty();
  }

  public boolean hasProductLink() {
    return !webDriver.findElements(
        By.xpath("//a[contains(@href, 'lamoda.ru/p/')]")
    ).isEmpty();
  }

  public boolean hasLink() {
    return hasCollectionButton() || hasProductLink();
  }
}
