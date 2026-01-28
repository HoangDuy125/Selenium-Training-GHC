package session1.exercise1_3.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import session1.base.BaseTest;
import session1.exercise1_3.locators.LoginLocators;

import java.util.Set;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccessfully_WithWindowHandling() {

        // 1. Open URL
        driver.get("https://practicetestautomation.com/practice-test-login/");

        // 2. Correct credentials
        String username = "student";
        String password = "Password123";

        // 3. Actions: find, sendKeys, click
        driver.findElement(LoginLocators.USERNAME_FIELD).clear();
        driver.findElement(LoginLocators.USERNAME_FIELD).sendKeys(username);

        driver.findElement(LoginLocators.PASSWORD_FIELD).clear();
        driver.findElement(LoginLocators.PASSWORD_FIELD).sendKeys(password);

        // Save current window before click
        String parentWindow = driver.getWindowHandle();

        driver.findElement(LoginLocators.LOGIN_BUTTON).click();

        // 4. Advanced: Handle multiple windows/tabs
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        // 5. Verification after switching window/tab
        WebElement successLabel = driver.findElement(LoginLocators.SUCCESS_MESSAGE);

        Assert.assertEquals(
                successLabel.getText(),
                "Logged In Successfully",
                "Login success message không đúng sau khi switch window!"
        );
    }

    @Test
    public void testLoginWithInvalidCredentials() {

        // 1. Open URL
        driver.get("https://practicetestautomation.com/practice-test-login/");

        // 2. Invalid credentials
        String username = "wrongUser";
        String password = "wrongPassword";

        // 3. Actions
        driver.findElement(LoginLocators.USERNAME_FIELD).clear();
        driver.findElement(LoginLocators.USERNAME_FIELD).sendKeys(username);

        driver.findElement(LoginLocators.PASSWORD_FIELD).clear();
        driver.findElement(LoginLocators.PASSWORD_FIELD).sendKeys(password);

        driver.findElement(LoginLocators.LOGIN_BUTTON).click();

        // 4. Verification error message
        WebElement errorMessage = driver.findElement(LoginLocators.ERROR_MESSAGE);

        Assert.assertTrue(
                errorMessage.isDisplayed(),
                "Error message không hiển thị khi login sai!"
        );

        Assert.assertEquals(
                errorMessage.getText().trim(),
                "Your username is invalid!",
                "Nội dung error message không đúng!"
        );
    }
}
