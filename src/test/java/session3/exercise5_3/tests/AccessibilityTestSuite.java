package session3.exercise5_3.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import session3.exercise5_3.utils.ColorContrastUtils;

import java.nio.file.Path;
import java.util.List;

public class AccessibilityTestSuite {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.get(Path.of("src", "test", "resources", "session3", "exercise5_3", "a11y-demo.html").toUri().toString());
    }

    @Test(description = "Exercise 5.3: Validate aria-label and role attributes")
    public void testAccessibilityAttributes() {
        WebElement element = driver.findElement(By.id("actionButton"));

        String ariaLabel = element.getAttribute("aria-label");
        Assert.assertNotNull(ariaLabel);
        Assert.assertFalse(ariaLabel.isBlank(), "aria-label should not be blank.");

        String role = element.getAttribute("role");
        Assert.assertEquals(role, "button");
    }

    @Test(description = "Exercise 5.3: Verify keyboard navigation by TAB")
    public void testKeyboardNavigation() {
        Actions actions = new Actions(driver);
        WebElement body = driver.findElement(By.tagName("body"));
        body.click();

        actions.sendKeys(Keys.TAB).perform();
        WebElement focused = driver.switchTo().activeElement();
        String firstFocusedId = focused.getAttribute("id");
        Assert.assertTrue(
                "homeLink".equals(firstFocusedId) || "nameInput".equals(firstFocusedId),
                "First focused element should be a keyboard-focusable control in the form area.");

        boolean reachedActionButton = "actionButton".equals(firstFocusedId);
        for (int i = 0; i < 4 && !reachedActionButton; i++) {
            actions.sendKeys(Keys.TAB).perform();
            focused = driver.switchTo().activeElement();
            reachedActionButton = "actionButton".equals(focused.getAttribute("id"));
        }

        Assert.assertTrue(reachedActionButton, "Keyboard navigation should reach actionButton via TAB.");
    }

    @Test(description = "Exercise 5.3: Check WCAG color contrast ratio for button")
    public void testColorContrast() {
        WebElement button = driver.findElement(By.id("actionButton"));
        String textColor = button.getCssValue("color");
        String backgroundColor = button.getCssValue("background-color");
        double ratio = ColorContrastUtils.contrastRatio(textColor, backgroundColor);

        Assert.assertTrue(ratio >= 4.5, "Expected contrast ratio >= 4.5, actual: " + ratio);
    }

    @Test(description = "Exercise 5.3: Basic screen reader compatibility checks")
    public void testScreenReaderCompatibility() {
        SoftAssert softly = new SoftAssert();

        String pageLang = driver.findElement(By.tagName("html")).getAttribute("lang");
        softly.assertTrue(pageLang != null && !pageLang.isBlank(), "Page should define html lang attribute.");

        WebElement input = driver.findElement(By.id("nameInput"));
        WebElement label = driver.findElement(By.cssSelector("label[for='nameInput']"));
        softly.assertFalse(label.getText().isBlank(), "Input should have visible label text.");
        softly.assertNotNull(input.getAttribute("aria-describedby"), "Input should have aria-describedby.");

        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement image : images) {
            String alt = image.getAttribute("alt");
            softly.assertTrue(alt != null && !alt.isBlank(), "All images must have non-empty alt text.");
        }

        WebElement liveRegion = driver.findElement(By.id("statusMessage"));
        softly.assertEquals(liveRegion.getAttribute("aria-live"), "polite");

        softly.assertAll();
    }

    @DataProvider(name = "criticalA11yElements")
    public Object[][] criticalA11yElements() {
        return new Object[][]{
                {"homeLink", "link"},
                {"nameInput", "input"},
                {"actionButton", "button"},
                {"statusMessage", "region"}
        };
    }

    @Test(dataProvider = "criticalA11yElements", description = "Advanced: comprehensive a11y smoke suite")
    public void testCriticalElementsAccessible(String elementId, String type) {
        WebElement element = driver.findElement(By.id(elementId));
        Assert.assertTrue(element.isDisplayed(), type + " should be visible.");

        String text = element.getText();
        String ariaLabel = element.getAttribute("aria-label");
        String ariaDescribedBy = element.getAttribute("aria-describedby");
        String alt = element.getAttribute("alt");

        boolean hasAccessibleName = (text != null && !text.isBlank())
                || (ariaLabel != null && !ariaLabel.isBlank())
                || (ariaDescribedBy != null && !ariaDescribedBy.isBlank())
                || (alt != null && !alt.isBlank());

        Assert.assertTrue(hasAccessibleName, type + " must have readable accessible metadata.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
