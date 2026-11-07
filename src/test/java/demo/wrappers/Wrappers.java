package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    private static final Duration WAIT_TIME = Duration.ofSeconds(15);

    public static void navigate(ChromeDriver driver, String url) {
        driver.navigate().to(url);
        System.out.println("Navigate: " + url);
    }

    public static void clickElement(ChromeDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        System.out.println("ClickElement: " + locator);
    }

    public static void typeElement(ChromeDriver driver, By locator, String value) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        element.sendKeys(Keys.CONTROL, "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
        System.out.println("TypeElement: " + value);
    }

    public static String getElementText(ChromeDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        System.out.println("GetElementText: " + text);
        return text;
    }
}
