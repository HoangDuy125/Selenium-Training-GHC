package session2.exercise3_4.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import session2.exercise3_4.pages.CookieSessionPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class CookieSessionTest {
    private static final String LOGIN_URL = "https://the-internet.herokuapp.com/login";
    private static final String SECURE_URL = "https://the-internet.herokuapp.com/secure";
    private static final String USERNAME = "tomsmith";
    private static final String PASSWORD = "SuperSecretPassword!";
    private static final Path COOKIE_FILE = Paths.get("target", "cookies", "session-cookies.txt");
    private static final Path AUTH_FILE = Paths.get("target", "cookies", "auth-token.txt");

    private WebDriver driver;
    private CookieSessionPage cookieSessionPage;

    @Test(description = "Exercise 3.4 - Cookie operations: add, get value, delete")
    public void testCookieOperations() {
        startBrowser();
        driver.get(LOGIN_URL);

        cookieSessionPage.addUsernameCookie();

        Cookie actual = cookieSessionPage.getCookieByName("username");
        Assert.assertNotNull(actual, "Cookie 'username' was not added.");
        Assert.assertEquals(actual.getValue(), "student", "Cookie value is incorrect.");

        cookieSessionPage.deleteCookieByName("username");
        Assert.assertNull(cookieSessionPage.getCookieByName("username"),
                "Cookie 'username' still exists after delete.");
    }

    @Test(description = "Exercise 3.4 - Session persistence: save/load cookies and keep login state")
    public void testSessionPersistenceWithCookieFile() throws IOException {
        startBrowser();
        driver.get(LOGIN_URL);
        cookieSessionPage.login(USERNAME, PASSWORD);
        assertLoggedIn();

        Set<Cookie> cookiesToPersist = cookieSessionPage.getAuthSessionCookies();
        if (cookiesToPersist.isEmpty()) {
            cookiesToPersist = cookieSessionPage.getAllCookies();
        }
        cookieSessionPage.saveCookies(cookiesToPersist, COOKIE_FILE);

        driver.quit();
        driver = null;

        startBrowser();
        driver.get(LOGIN_URL);
        cookieSessionPage.loadCookies(COOKIE_FILE);
        driver.navigate().refresh();
        driver.navigate().to(SECURE_URL);
        assertLoggedIn();
    }

    @Test(description = "Exercise 3.4 - Advanced: auth token/session handling")
    public void testAdvancedAuthTokenHandling() throws IOException {
        startBrowser();
        driver.get(LOGIN_URL);
        cookieSessionPage.login(USERNAME, PASSWORD);
        assertLoggedIn();

        List<Cookie> authLikeCookies = cookieSessionPage.getAuthLikeCookies();

        Assert.assertTrue(!authLikeCookies.isEmpty(),
                "No auth/session-like cookie found after login.");
        cookieSessionPage.saveCookies(Set.copyOf(authLikeCookies), AUTH_FILE);

        Cookie authCookie = authLikeCookies.get(0);
        String maskedValue = cookieSessionPage.mask(authCookie.getValue());
        System.out.println("Auth cookie name: " + authCookie.getName());
        System.out.println("Auth cookie value (masked): " + maskedValue);

        cookieSessionPage.saveTokenToLocalStorage("auth_token", authCookie.getValue());
        String fromStorage = cookieSessionPage.getTokenFromLocalStorage("auth_token");
        Assert.assertEquals(fromStorage, authCookie.getValue(),
                "Token in localStorage does not match cookie token.");
    }

    private void startBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        cookieSessionPage = new CookieSessionPage(driver);
    }

    private void assertLoggedIn() {
        boolean isLoggedIn = cookieSessionPage.waitUntilLoggedIn();
        Assert.assertTrue(isLoggedIn,
                "Expected user to be logged in, but it was not. Current URL: "
                        + cookieSessionPage.getCurrentUrl()
                        + " | Flash: " + cookieSessionPage.getFlashTextIfPresent()
                        + " | Cookies: " + cookieSessionPage.getCookieNames());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
