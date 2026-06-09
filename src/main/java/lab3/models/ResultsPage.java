package lab3.models;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lab3.utils.Util;

public class ResultsPage extends Page {
  
  public ResultsPage(WebDriver driver) {
    super(driver);
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(3));
  }
  
  public List<WebElement> getSearchResults() {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
      By.xpath("(//div[contains(@class, 'x-product-card__card')])[position() <= 5]")
    ));
  }
  
  public boolean hasSearchResults() {
    return !getSearchResults().isEmpty();
  }
  
  public boolean isEmptyResultsMessageDisplayed() {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

    WebElement emptyMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//h1[contains(text(), 'Ничего не нашли')]")
    ));
    
    return emptyMessage.isDisplayed();
  }

  public boolean resultsContainKeyword(String keyword) {
    List<WebElement> results = getSearchResults();
    if (results.isEmpty()) return false;
    
    int cnt = 0;
    for (int i = 0; i < results.size(); i++) {
      WebElement currentResult = webDriver.findElement(
        By.xpath("(//div[contains(@class, 'x-product-card__card')])[" + (i + 1) + "]")
      );
      String text = currentResult.getText().toLowerCase();
      if (text.contains(keyword.toLowerCase())) {
        cnt++;
      }
    }

    double percent = (cnt * 100.0) / results.size();
    return percent >= 50.0;
  }
}
