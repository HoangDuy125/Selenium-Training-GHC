package session2.exercise3_1.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/buttons");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
