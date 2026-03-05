package session2.exercise4_2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class WindowAndFramePage {
    private static final String WINDOWS_URL = "https://the-internet.herokuapp.com/windows";
    private static final String IFRAME_URL = "https://the-internet.herokuapp.com/iframe";
    private static final String NESTED_FRAMES_URL = "https://the-internet.herokuapp.com/nested_frames";

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebDriverWait shortWait;

    private final By clickHereLink = By.linkText("Click Here");
    private final By newWindowHeading = By.tagName("h3");
    private final By editorBody = By.id("tinymce");

    public WindowAndFramePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void openWindowsPage() {
        driver.get(WINDOWS_URL);
    }

    public void openIframePage() {
        driver.get(IFRAME_URL);
    }

    public void openNestedFramesPage() {
        driver.get(NESTED_FRAMES_URL);
    }

    public String getMainWindowHandle() {
        return driver.getWindowHandle();
    }

    public Set<String> getAllWindowHandles() {
        return driver.getWindowHandles();
    }

    public void clickOpenNewWindow() {
        wait.until(ExpectedConditions.elementToBeClickable(clickHereLink)).click();
    }

    public String waitAndSwitchToNewWindow(String mainHandle) {
        wait.until(d -> d.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainHandle)) {
                driver.switchTo().window(handle);
                return handle;
            }
        }
        throw new NoSuchWindowException("Cannot find new window handle.");
    }

    public String getNewWindowHeadingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(newWindowHeading)).getText();
    }

    public void switchToWindow(String handle) {
        driver.switchTo().window(handle);
    }

    public boolean closeWindowIfNotMain(String mainHandle) {
        if (!driver.getWindowHandle().equals(mainHandle)) {
            driver.close();
            return true;
        }
        return false;
    }

    public void switchToFrameByIndex(int index) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));

        if (!isEditorBodyPresent()) {
            driver.switchTo().defaultContent();
            WebElement editorFrame = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("mce_0_ifr")));
            driver.switchTo().frame(editorFrame);
        }
    }

    public void switchToFrameByNameOrId(String nameOrId) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
    }

    public String getEditorText() {
        WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(editorBody));
        String raw = (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].textContent;", body);
        return normalizeText(raw);
    }

    public void typeEditorText(String value) {
        WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(editorBody));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", body);
        js.executeScript("arguments[0].textContent = arguments[1];", body, value);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getNestedTopLeftFrameText() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frame-top"));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frame-left"));
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))).getText();
        driver.switchTo().defaultContent();
        return text;
    }

    public String openDynamicWindowAndSwitch(String mainHandle) {
        ((JavascriptExecutor) driver).executeScript(
                "window.open('https://the-internet.herokuapp.com/windows/new','_blank');");

        wait.until(d -> d.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainHandle)) {
                driver.switchTo().window(handle);
                return handle;
            }
        }
        throw new NoSuchWindowException("Dynamic window was not created.");
    }

    public boolean waitForWindowCount(int expectedCount) {
        try {
            wait.until(d -> d.getWindowHandles().size() == expectedCount);
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    private boolean isEditorBodyPresent() {
        try {
            shortWait.until(ExpectedConditions.presenceOfElementLocated(editorBody));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    private String normalizeText(String value) {
        if (value == null) {
            return "";
        }
        return value.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
    }
}
