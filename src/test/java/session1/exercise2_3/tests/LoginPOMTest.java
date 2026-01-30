package session1.exercise2_3.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import session1.base.BaseTest;
import session1.exercise2_3.pages.LoginPage;
import session1.exercise2_3.pages.SuccessPage;

public class LoginPOMTest extends BaseTest {

    @Test
    public void testLoginSuccessfullyWithPOM() {
        // Thay driver.get() bằng cách gọi .open() từ object của LoginPage
        LoginPage loginPage = new LoginPage(driver).open();
        SuccessPage successPage = new SuccessPage(driver);

        loginPage.login("student", "Password123");

        Assert.assertTrue(successPage.isLoggedIn(),
                "User should be logged in successfully using POM!");
    }

    @Test
    public void testInvalidLoginWithPOM() {
        // Thay driver.get() bằng cách gọi .open() từ object của LoginPage
        LoginPage loginPage = new LoginPage(driver).open();

        loginPage.login("invalid", "invalid");

        Assert.assertTrue(driver.getCurrentUrl().contains("practice-test-login"),
                "Invalid login should not navigate to success page");
    }
}