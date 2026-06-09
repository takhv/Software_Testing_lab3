package lab3.models;

import lab3.utils.Util;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends Page {
  public ProductPage(WebDriver webDriver, String path) {
    super(webDriver);
    webDriver.get(Util.BASE_URL + path);
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(10));
    Util.acceptCookies(webDriver);
  }

  public void openReviewsTab() {
    WebElement reviews = Util.getElementBySelector(webDriver, By.xpath("//span[@aria-label='Отзывы']"));

    ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", reviews);
    
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", reviews);
  }

  public void chooseSize(String size) {
    WebElement sizeSelector = Util.getElementBySelector(webDriver, By.xpath("//div[contains(@class,'_placeholder') and .//div[contains(text(),'Выберите размер')]]"));
    sizeSelector.click();
    
    String sizeXPath = "//div[contains(@class, 'ui-product-page-sizes-chooser-item_enabled')]//div[contains(@class, '_firstRow_14ypi_196') and text()='" + size + " RUS']";
    WebElement sizeElement = Util.getElementBySelector(webDriver, By.xpath(sizeXPath));
    sizeElement.click();
  }

  public void addToCart() {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

    WebElement addToCartBtn = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//button[@aria-label='Добавить в корзину']")
      )
    );

    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", addToCartBtn);
  }

  public void goToCart() {
    webDriver.get(Util.BASE_URL + "/checkout/cart");
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(5));
  }

  public void addToFavorites() {
    WebElement favBtn = Util.getElementBySelector(webDriver, By.xpath("//div[contains(@class, 'js-to-favorites') and contains(@class, 'ui-wishes-add-to')]"));

    if (!favBtn.getAttribute("class").contains("ui-heart-added")) {
      favBtn.click();

      WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
      wait.until(ExpectedConditions.attributeContains(
        By.xpath("//div[contains(@class, 'js-to-favorites') and contains(@class, 'ui-wishes-add-to')]"),
        "class",
        "ui-heart-added"
      ));
    }
  }

  public boolean isFavorited() {
    WebElement favBtn = Util.getElementBySelector(webDriver, By.xpath("//div[contains(@class, 'js-to-favorites') and contains(@class, 'ui-wishes-add-to')]"));

    return favBtn.getAttribute("class").contains("ui-heart-added");
  }
}
