package session1.exercise1_4.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import session1.exercise1_4.locators.ExplicitWaitLocators;
import session1.utils.ConfigReader;
import session1.utils.WaitUtils;

public class ExplicitWaitTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get(ConfigReader.get("saucedemo.url"));


            WaitUtils.waitForElementVisible(driver, ExplicitWaitLocators.USERNAME_FIELD);
            driver.findElement(ExplicitWaitLocators.USERNAME_FIELD).sendKeys(ConfigReader.get("sauce.username"));

            driver.findElement(ExplicitWaitLocators.PASSWORD_FIELD).sendKeys(ConfigReader.get("sauce.password"));

            WaitUtils.waitForElementClickable(driver, ExplicitWaitLocators.LOGIN_BUTTON);
            driver.findElement(ExplicitWaitLocators.LOGIN_BUTTON).click();

            WaitUtils.waitForElementVisible(driver, ExplicitWaitLocators.SUCCESS_TITLE);
            System.out.println("Login thành công: " + driver.findElement(ExplicitWaitLocators.SUCCESS_TITLE).getText());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}