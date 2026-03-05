package session2.exercise4_1.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class JavaScriptAlertsPage {
    private static final String JS_ALERTS_URL = "https://the-internet.herokuapp.com/javascript_alerts";
    private static final String ENTRY_AD_URL = "https://the-internet.herokuapp.com/entry_ad";

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebDriverWait shortWait;

    private final By jsAlertButton = By.xpath("//button[text()='Click for JS Alert']");
    private final By jsConfirmButton = By.xpath("//button[text()='Click for JS Confirm']");
    private final By jsPromptButton = By.xpath("//button[text()='Click for JS Prompt']");
    private final By resultText = By.id("result");

    private final By modalOverlay = By.id("modal");
    private final By modalContainer = By.cssSelector("#modal .modal");
    private final By modalCloseButton = By.cssSelector("#modal .modal-footer p");
    private final By restartAdLink = By.id("restart-ad");

    public JavaScriptAlertsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void openJsAlertsPage() {
        driver.get(JS_ALERTS_URL);
    }

    public void openEntryAdPage() {
        driver.get(ENTRY_AD_URL);
    }

    public void clickSimpleAlertButton() {
        wait.until(ExpectedConditions.elementToBeClickable(jsAlertButton)).click();
    }

    public void clickConfirmButton() {
        wait.until(ExpectedConditions.elementToBeClickable(jsConfirmButton)).click();
    }

    public void clickPromptButton() {
        wait.until(ExpectedConditions.elementToBeClickable(jsPromptButton)).click();
    }

    public String acceptAlertAndGetText() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }

    public String dismissAlertAndGetText() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.dismiss();
        return text;
    }

    public String sendKeysToPromptAndAccept(String value) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.sendKeys(value);
        alert.accept();
        return text;
    }

    public String getResultText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(resultText)).getText();
    }

    public boolean closeHtmlModalIfVisible() {
        if (!isModalVisible()) {
            List<WebElement> restartLinks = driver.findElements(restartAdLink);
            if (!restartLinks.isEmpty() && restartLinks.get(0).isDisplayed()) {
                restartLinks.get(0).click();
            }
        }

        if (!isModalVisible()) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(modalOverlay));
                wait.until(ExpectedConditions.visibilityOfElementLocated(modalContainer));
            } catch (TimeoutException ex) {
                return false;
            }
        }

        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(modalCloseButton));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(modalCloseButton)).click();
        } catch (ElementClickInterceptedException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        return true;
    }

    public boolean dismissUnexpectedJsAlertIfPresent() {
        try {
            Alert alert = shortWait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
            return true;
        } catch (TimeoutException | NoAlertPresentException ignored) {
            return false;
        }
    }

    private boolean isModalVisible() {
        List<WebElement> overlays = driver.findElements(modalOverlay);
        if (overlays.isEmpty()) {
            return false;
        }

        for (WebElement overlay : overlays) {
            if (overlay.isDisplayed()) {
                return true;
            }
        }
        return false;
    }
}
