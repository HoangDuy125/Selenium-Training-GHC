package session1.exercise2_3.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import session1.base.BaseTest;
import session1.exercise2_3.pages.LoginPage;
import session1.exercise2_3.pages.SuccessPage;

public class LoginPOMTest extends BaseTest {

    @Test
    public void testLoginSuccessfullyWithPOM() {

        SuccessPage successPage = new LoginPage(driver)
                .open()
                .enterUsername("student")
                .enterPassword("Password123")
                .clickSubmit();

        Assert.assertTrue(successPage.isLoggedIn(), "Login failed!");
        Assert.assertEquals(successPage.getSuccessText(), "Logged In Successfully");
    }
}