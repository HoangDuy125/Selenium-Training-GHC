package session2.exercise4_3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LocatorPage {
    private static final String TEXT_BOX_URL = "https://demoqa.com/text-box";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By xpathText = By.xpath("//button[text()='Submit']");
    private final By xpathContains = By.xpath("//input[contains(@id, 'user')]");
    private final By xpathNormalizeSpace = By.xpath("//span[normalize-space()='Match']");
    private final By xpathStartsWith = By.xpath("//div[starts-with(@class, 'error')]");
    private final By xpathPosition = By.xpath("(//button)[3]");
    private final By cssAttributeSelector = By.cssSelector("input[type='text']");
    private final By cssChildCombinator = By.cssSelector("form > input");
    private final By cssClassSelector = By.cssSelector(".error-message");
    private final By advancedComplexXpath = By.xpath(
            "//form[@id='userForm']//input[contains(@id,'user') and @type='text']");
    private final By optimizedCss = By.cssSelector("#userForm input[id*='user'][type='text']");

    public LocatorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get(TEXT_BOX_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userForm")));
    }

    public void ensureExerciseFixture() {
        String script =
                "if (!document.getElementById('exercise43-fixture')) {" +
                "  const host = document.createElement('section');" +
                "  host.id = 'exercise43-fixture';" +
                "  host.innerHTML =" +
                "    '<form id=\"exercise43-form\">' +" +
                "    '  <input id=\"fixture-input\" type=\"text\" value=\"fixture\" />' +" +
                "    '</form>' +" +
                "    '<button type=\"button\">Fixture Button 1</button>' +" +
                "    '<button type=\"button\">Fixture Button 2</button>' +" +
                "    '<button type=\"button\">Fixture Button 3</button>' +" +
                "    '<span>   Match   </span>' +" +
                "    '<div class=\"error-state\">Fixture error container</div>' +" +
                "    '<p class=\"error-message\">Fixture error message</p>';" +
                "  document.body.appendChild(host);" +
                "}";
        ((JavascriptExecutor) driver).executeScript(script);
    }

    public WebElement findXpathText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(xpathText));
    }

    public List<WebElement> findXpathContains() {
        return driver.findElements(xpathContains);
    }

    public WebElement findXpathNormalizeSpace() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(xpathNormalizeSpace));
    }

    public WebElement findXpathStartsWith() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(xpathStartsWith));
    }

    public WebElement findXpathPosition() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(xpathPosition));
    }

    public List<WebElement> findCssAttributeSelector() {
        return driver.findElements(cssAttributeSelector);
    }

    public List<WebElement> findCssChildCombinator() {
        return driver.findElements(cssChildCombinator);
    }

    public WebElement findCssClassSelector() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cssClassSelector));
    }

    public int countAdvancedComplexXpath() {
        return driver.findElements(advancedComplexXpath).size();
    }

    public int countOptimizedCss() {
        return driver.findElements(optimizedCss).size();
    }
}
