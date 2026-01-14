package session1.exercise1_1.tests;

import java.io.FileInputStream;
import java.util.Properties;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class FirstTest {
    @Test
    public void openBrowserTest() throws Exception {

        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);

        String urlFromConfig = props.getProperty("url");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get(urlFromConfig);

        driver.quit();
    }
}