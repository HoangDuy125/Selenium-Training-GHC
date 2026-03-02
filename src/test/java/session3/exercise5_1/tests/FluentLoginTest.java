package session3.exercise5_1.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import session3.exercise5_1.models.LoginCredentials;
import session3.exercise5_1.pages.DashboardPage;
import session3.exercise5_1.pages.LoginPage;

import java.nio.file.Files;
import java.nio.file.Path;

public class FluentLoginTest {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = createDriver();
        driver.manage().window().maximize();
    }

    @Test(description = "Exercise 5.1: Fluent API login flow")
    public void testLoginWithFluentApi() {
        DashboardPage dashboard = new LoginPage(driver)
                .enterUsername("student")
                .enterPassword("Password123")
                .clickSubmit();

        Assert.assertTrue(dashboard.isAt(), "Expected to be on Dashboard page after login.");
    }

    @Test(description = "Exercise 5.1 Advanced: Builder pattern with page hierarchy")
    public void testLoginWithBuilderPattern() {
        LoginCredentials credentials = LoginCredentials.builder()
                .username("student")
                .password("Password123")
                .build();

        DashboardPage dashboard = new LoginPage(driver).login(credentials);

        Assert.assertEquals(dashboard.getTitleText(), "Logged In Successfully");
        Assert.assertTrue(dashboard.isLogoutVisible(), "Logout button should be visible for authenticated user.");
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
