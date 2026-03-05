package session3.exercise5_2.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import session3.exercise5_2.factories.LoginDataFactory;

import java.time.Duration;

public class DataDrivenLoginTest {
    private static final String LOGIN_URL = "https://practicetestautomation.com/practice-test-login/";

    private static final By USERNAME_INPUT = By.id("username");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By SUBMIT_BUTTON = By.id("submit");
    private static final By LOGOUT_LINK = By.xpath("//a[contains(.,'Log out')]");
    private static final By ERROR_MESSAGE = By.id("error");

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][]{
                {"student", "Password123", true},
                {"invalid", "invalid", false},
                {"student", "", false}
        };
    }

    @DataProvider(name = "loginDataCsv")
    public Object[][] getLoginDataFromCsv() {
        return LoginDataFactory.csvData();
    }

    @DataProvider(name = "loginDataJson")
    public Object[][] getLoginDataFromJson() {
        return LoginDataFactory.jsonData();
    }

    @DataProvider(name = "loginDataExcel")
    public Object[][] getLoginDataFromExcel() {
        return LoginDataFactory.excelData();
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean expected) {
        executeLoginAndAssert(username, password, expected);
    }

    @Test(dataProvider = "loginDataCsv")
    public void testLoginUsingCsvData(String username, String password, boolean expected) {
        executeLoginAndAssert(username, password, expected);
    }

    @Test(dataProvider = "loginDataJson")
    public void testLoginUsingJsonData(String username, String password, boolean expected) {
        executeLoginAndAssert(username, password, expected);
    }

    @Test(dataProvider = "loginDataExcel")
    public void testLoginUsingExcelData(String username, String password, boolean expected) {
        executeLoginAndAssert(username, password, expected);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void executeLoginAndAssert(String username, String password, boolean expected) {
        driver.get(LOGIN_URL);
        driver.findElement(USERNAME_INPUT).sendKeys(username);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        driver.findElement(SUBMIT_BUTTON).click();

        waitForLoginOutcome();
        boolean actualSuccess = !driver.findElements(LOGOUT_LINK).isEmpty();

        Assert.assertEquals(
                actualSuccess,
                expected,
                "Unexpected result for username='" + username + "', password='" + password + "'");
    }

    private void waitForLoginOutcome() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(d -> !d.findElements(LOGOUT_LINK).isEmpty() || !d.findElements(ERROR_MESSAGE).isEmpty());
    }
}
