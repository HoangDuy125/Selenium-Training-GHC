package session1.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtils {


    public static WebDriverWait getWait(WebDriver driver) {
        String timeoutStr = ConfigReader.get("explicit.wait");
        int timeout = Integer.parseInt(timeoutStr != null ? timeoutStr : "15");
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }


    public static void waitForElementVisible(WebDriver driver, By locator) {
        getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForElementClickable(WebDriver driver, By locator) {
        getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }
}