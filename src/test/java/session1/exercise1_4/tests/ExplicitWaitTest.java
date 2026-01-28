package session1.exercise1_4.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import session1.base.BaseTest;
import session1.exercise1_4.locators.ExplicitWaitLocators;

import java.time.Duration;

public class ExplicitWaitTest extends BaseTest {

    //
    @Test
    public void testDynamicLoading() {

        // URL
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        // Click Start button
        driver.findElement(ExplicitWaitLocators.START_BUTTON).click();

        // Explicit wait with CUSTOM ExpectedCondition
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement finishText = wait.until(new FinishTextVisibleCondition());

        // Verification
        Assert.assertEquals(
                finishText.getText(),
                "Hello World!",
                "Finish text không đúng sau khi dynamic loading!"
        );
    }

    private static class FinishTextVisibleCondition implements ExpectedCondition<WebElement> {

        @Override
        public WebElement apply(WebDriver driver) {
            WebElement finishElement = driver.findElement(ExplicitWaitLocators.FINISH_TEXT);

            if (finishElement.isDisplayed()) {
                return finishElement;
            }

            return null;
        }

        @Override
        public String toString() {
            return "Finish text (Hello World!) to be visible";
        }
    }
}
