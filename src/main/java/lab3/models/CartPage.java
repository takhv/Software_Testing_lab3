package lab3.models;

import lab3.utils.Util;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends Page {
  public CartPage(WebDriver webDriver) {
    super(webDriver);
    webDriver.manage().window().maximize();
    webDriver.get(Util.BASE_URL + "/checkout/cart");
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(5));
  }

  public void removeFromCart() {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    WebElement selectAllcheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(
      By.xpath("//div[contains(@class, '_selectionPanel')]//input[@class='x-checkbox__native-checkbox']")));

    if (!selectAllcheckbox.isSelected()){
      selectAllcheckbox.click();
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    // 2. Кнопка удаления корзины
    WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(@class, '_actions')]//button[contains(@class, 'x-button_borderlessPrimary')]")));
    
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", removeBtn);
    System.out.println("Кнопка удаления нажата");
    
    // 3. Ждем появления модального окна
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//div[contains(@class, 'd-modal')]")));
    System.out.println("Модальное окно появилось");
    
    WebElement submitRemove = wait.until(ExpectedConditions.elementToBeClickable(
      By.xpath("//button[@class='x-button x-button_primaryFilledWeb7184 x-button_32 x-button_intrinsic-width _actionButton_4stt4_7']")));
    
    if (submitRemove != null) {
      submitRemove.click();
    }
    
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
