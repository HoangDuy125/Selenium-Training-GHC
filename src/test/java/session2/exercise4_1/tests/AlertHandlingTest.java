package session2.exercise4_1.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import session2.exercise4_1.pages.JavaScriptAlertsPage;

public class AlertHandlingTest {
    private WebDriver driver;
    private JavaScriptAlertsPage alertsPage;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        alertsPage = new JavaScriptAlertsPage(driver);
    }

    @Test(description = "Exercise 4.1: Handle JS Alert, JS Confirm, JS Prompt and verify results")
    public void testHandleDifferentAlertTypes() {
        alertsPage.openJsAlertsPage();

        alertsPage.clickSimpleAlertButton();
        String simpleAlertText = alertsPage.acceptAlertAndGetText();
        Assert.assertTrue(simpleAlertText.contains("JS Alert"), "Unexpected simple alert text.");
        Assert.assertEquals(alertsPage.getResultText(), "You successfully clicked an alert");

        alertsPage.clickConfirmButton();
        String confirmAlertText = alertsPage.dismissAlertAndGetText();
        Assert.assertTrue(confirmAlertText.contains("JS Confirm"), "Unexpected confirm alert text.");
        Assert.assertEquals(alertsPage.getResultText(), "You clicked: Cancel");

        String promptValue = "Selenium Exercise 4.1";
        alertsPage.clickPromptButton();
        String promptAlertText = alertsPage.sendKeysToPromptAndAccept(promptValue);
        Assert.assertTrue(promptAlertText.toLowerCase().contains("prompt"), "Unexpected prompt alert text.");
        Assert.assertEquals(alertsPage.getResultText(), "You entered: " + promptValue);
    }

    @Test(description = "Advanced: multiple alerts with timing and unexpected popup guard")
    public void testMultipleAlertsWithTimingIssues() {
        alertsPage.openJsAlertsPage();

        for (int i = 1; i <= 3; i++) {
            alertsPage.clickSimpleAlertButton();
            String text = alertsPage.acceptAlertAndGetText();
            Assert.assertTrue(text.contains("JS Alert"), "Simple alert text mismatch at cycle " + i);
            Assert.assertEquals(alertsPage.getResultText(), "You successfully clicked an alert");

            alertsPage.clickConfirmButton();
            if (i % 2 == 0) {
                alertsPage.acceptAlertAndGetText();
                Assert.assertEquals(alertsPage.getResultText(), "You clicked: Ok");
            } else {
                alertsPage.dismissAlertAndGetText();
                Assert.assertEquals(alertsPage.getResultText(), "You clicked: Cancel");
            }
        }

        boolean hadUnexpected = alertsPage.dismissUnexpectedJsAlertIfPresent();
        Assert.assertFalse(hadUnexpected, "Unexpected JS alert was present and had to be dismissed.");
    }

    @Test(description = "Topic: JavaScript alerts vs HTML dialogs (modal window handling)")
    public void testHtmlModalWindowHandling() {
        alertsPage.openEntryAdPage();
        boolean wasModalShown = alertsPage.closeHtmlModalIfVisible();
        Assert.assertTrue(wasModalShown, "HTML modal was not shown or could not be closed.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
