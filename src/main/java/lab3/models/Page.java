package lab3.models;

import org.openqa.selenium.WebDriver;
import lombok.*;

@Getter 
@Setter 
@AllArgsConstructor
public abstract class Page {
  protected WebDriver webDriver;
}
