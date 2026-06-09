package lab3.models;

import lab3.utils.Util;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends Page {
  public MainPage(WebDriver webDriver) {
    super(webDriver);
    webDriver.manage().window().maximize();
    webDriver.get(Util.BASE_URL);
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(5));
  }

  public CartPage getCartPage() {
    WebElement btn = Util.getElementBySelector(webDriver, By.xpath("//a[@href='/checkout/cart/']"));
    btn.click();
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(1));
    return new CartPage(webDriver);
  }

  public void findProduct(String product) {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

    WebElement findField = wait.until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//input[@placeholder='Товар, бренд или артикул']")
    ));
    findField.clear();
    findField.sendKeys(product);

    WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//button[@aria-label='Поиск']")
    ));

    searchButton.click();
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(5));
  }

  public void selectCategory(String categoryName) {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    String xpathcategory = "//nav[@aria-label='Главное меню']//a[normalize-space()='" + categoryName + "']";
    
    WebElement category = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathcategory)));
    category.click();
    Util.waitUntilPageLoads(webDriver, Duration.ofSeconds(5));
  }

  public void openLogin(){
    WebElement loginbtn = Util.getElementBySelector(webDriver, By.xpath("//button[contains(.,\'Войти\')]"));

    loginbtn.click();
  }

  public void writePhoneNumber(String phone) {
    WebElement phoneField = Util.getElementBySelector(webDriver, By.xpath("//input[@name='phone']"));
    phoneField.clear();

    phoneField.sendKeys(phone);

    WebElement submitBtn = Util.getElementBySelector(webDriver, By.xpath("//button[@type='submit' and contains(.,'Войти')]"));
    submitBtn.click();
  }

  public boolean isLoginErrorDisplayed() {
    try {
      WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));

      WebElement errorMessage = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
          By.xpath("//div[contains(@class,'x-form-error') and contains(.,'Неверный формат номера')]")
        )
      );

      return errorMessage.isDisplayed();
    } catch (TimeoutException e) {
      return false;
    }
  }

  public boolean isProfileDisplayed() {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
    WebElement profileLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
      By.xpath("//a[@href='/sales/order/history/']")
    ));
    
    return profileLink.isDisplayed();
  }

  public JournalPage goToJournals() {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));

    WebElement btn = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href,'/blog')]")
        )
    );

    ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
    btn.click();

    return new JournalPage(webDriver);
  }
}
