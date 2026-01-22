package session1.exercise1_3.tests;

import session1.exercise1_3.locators.LoginLocators;
import session1.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            // 1. Lấy URL từ file config.properties
            String url = ConfigReader.get("saucedemo.url");
            driver.get(url);

            // 2. Lấy dữ liệu đăng nhập từ file config.properties
            String username = ConfigReader.get("sauce.username");
            String password = ConfigReader.get("sauce.password");

            // 3. Thực hiện các thao tác (Action methods)
            WebElement userField = driver.findElement(LoginLocators.USERNAME_FIELD);
            userField.clear();
            userField.sendKeys(username);

            driver.findElement(LoginLocators.PASSWORD_FIELD).sendKeys(password);
            driver.findElement(LoginLocators.LOGIN_BUTTON).click();

            // 4. Kiểm tra trạng thái (Verification methods)
            WebElement successLabel = driver.findElement(LoginLocators.LOGIN_MESSAGE);
            if (successLabel.isDisplayed()) {
                System.out.println("Đăng nhập thành công : " + username);
                System.out.println("Tiêu đề trang: " + successLabel.getText());
            }

        } catch (Exception e) {
            System.err.println("Lỗi thực thi: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}