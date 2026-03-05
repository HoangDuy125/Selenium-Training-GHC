package session2.exercise4_3.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import session2.exercise4_3.pages.LocatorPage;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocatorTest {
    private WebDriver driver;
    private LocatorPage page;

    @BeforeMethod
    public void setup() {
        driver = createDriver();
        driver.manage().window().maximize();
        page = new LocatorPage(driver);
    }

    @Test(description = "Exercise 4.3: Validate required XPath and CSS locators on DemoQA")
    public void testLocatorsOnDemoQa() {
        page.open();
        page.ensureExerciseFixture();

        Assert.assertEquals(page.findXpathText().getText(), "Submit");
        Assert.assertTrue(page.findXpathContains().size() >= 1,
                "Expected at least one input with id containing 'user'.");
        Assert.assertEquals(page.findXpathNormalizeSpace().getText().trim(), "Match");
        Assert.assertTrue(page.findXpathStartsWith().getAttribute("class").startsWith("error"),
                "Expected class to start with 'error'.");
        Assert.assertEquals(page.findXpathPosition().getTagName(), "button");

        Assert.assertTrue(page.findCssAttributeSelector().size() >= 1,
                "Expected at least one text input.");
        Assert.assertTrue(page.findCssChildCombinator().size() >= 1,
                "Expected at least one direct input child of a form.");
        Assert.assertTrue(page.findCssClassSelector().isDisplayed(),
                "Expected .error-message element to be visible.");
    }

    @Test(description = "Exercise 4.3 Advanced: complex XPath and optimized CSS should match same targets")
    public void testAdvancedXpathVsOptimizedCss() {
        page.open();
        int xpathCount = page.countAdvancedComplexXpath();
        int cssCount = page.countOptimizedCss();

        Assert.assertTrue(xpathCount >= 1, "Complex XPath should find at least one matching element.");
        Assert.assertEquals(cssCount, xpathCount,
                "Optimized CSS and complex XPath should return same number of elements.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebDriver createDriver() {
        String chromeDriverPath = System.getProperty("webdriver.chrome.driver");
        if (chromeDriverPath == null || chromeDriverPath.isBlank()) {
            chromeDriverPath = System.getenv("CHROMEDRIVER_PATH");
        }

        if (chromeDriverPath != null && !chromeDriverPath.isBlank()) {
            Path path = Path.of(chromeDriverPath);
            if (!Files.exists(path)) {
                throw new IllegalStateException("chromedriver not found at: " + path.toAbsolutePath());
            }

            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(path.toFile())
                    .build();
            return new ChromeDriver(service);
        }

        return new ChromeDriver();
    }
}
