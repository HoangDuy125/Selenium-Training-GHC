package session2.exercise4_2.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import session2.exercise4_2.pages.WindowAndFramePage;

public class WindowAndFrameTest {
    private WebDriver driver;
    private WindowAndFramePage page;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        page = new WindowAndFramePage(driver);
    }

    @Test(description = "Exercise 4.2: Multiple windows (open, switch, action, back, close)")
    public void testMultipleWindowsFlow() {
        page.openWindowsPage();
        String mainHandle = page.getMainWindowHandle();

        page.clickOpenNewWindow();
        String newWindowHandle = page.waitAndSwitchToNewWindow(mainHandle);

        Assert.assertNotEquals(newWindowHandle, mainHandle, "New window handle must be different.");
        Assert.assertEquals(page.getNewWindowHeadingText(), "New Window");

        boolean closed = page.closeWindowIfNotMain(mainHandle);
        Assert.assertTrue(closed, "New window was not closed.");

        page.switchToWindow(mainHandle);
        Assert.assertTrue(page.getCurrentUrl().contains("/windows"),
                "Did not switch back to main windows page.");
        Assert.assertTrue(page.waitForWindowCount(1), "Expected only one window after closing new one.");
    }

    @Test(description = "Exercise 4.2: iFrames by index and name, then nested frames handling")
    public void testIframeAndNestedFramesFlow() {
        page.openIframePage();

        page.switchToFrameByIndex(0);
        page.typeEditorText("Typed by iframe index");
        Assert.assertEquals(page.getEditorText(), "Typed by iframe index");
        page.switchToDefaultContent();

        page.switchToFrameByNameOrId("mce_0_ifr");
        page.typeEditorText("Typed by iframe name");
        Assert.assertEquals(page.getEditorText(), "Typed by iframe name");
        page.switchToDefaultContent();

        page.openNestedFramesPage();
        String leftText = page.getNestedTopLeftFrameText();
        Assert.assertEquals(leftText.trim(), "LEFT", "Nested frame LEFT content mismatch.");
    }

    @Test(description = "Advanced: dynamic window creation and handle switching")
    public void testDynamicWindowCreation() {
        page.openWindowsPage();
        String mainHandle = page.getMainWindowHandle();

        String dynamicHandle = page.openDynamicWindowAndSwitch(mainHandle);
        Assert.assertNotEquals(dynamicHandle, mainHandle, "Dynamic window handle must be different.");
        Assert.assertEquals(page.getNewWindowHeadingText(), "New Window");

        boolean closed = page.closeWindowIfNotMain(mainHandle);
        Assert.assertTrue(closed, "Dynamic window was not closed.");

        page.switchToWindow(mainHandle);
        Assert.assertTrue(page.waitForWindowCount(1), "Expected one window after closing dynamic window.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
