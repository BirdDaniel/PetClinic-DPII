package org.springframework.samples.petclinic.ui.Parks;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateParkUITest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    String pathToGeckoDriver="C:/Users/Aug/Documents/DevStuff/Geckodriver";

    System.setProperty("webdriver.gecko.driver",
                        pathToGeckoDriver +
                        "\\geckodriver.exe");

    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreateParkUI() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("owner3");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("0wn3r");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//a[contains(@href, '/parks')]")).click();
    driver.findElement(By.xpath("//a[contains(@href, '/parks/new')]")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("Parque de Grazalema");
    driver.findElement(By.id("address")).clear();
    driver.findElement(By.id("address")).sendKeys("c/ Grazalema, 7");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    try {
      assertEquals("6 Parque de Grazalema", driver.findElement(By.xpath("//table[@id='parksTable']/tbody/tr[6]/td")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
