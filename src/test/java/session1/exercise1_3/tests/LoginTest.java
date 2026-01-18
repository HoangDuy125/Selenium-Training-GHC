package session1.exercise1_3.tests;

import session1.exercise1_3.locators.LoginLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    public static void main(String[] args) {
        // 1. Khởi tạo Browser
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            // 2. Browser Navigation: Điều hướng tới trang thực hành
            driver.get("https://www.saucedemo.com/");

            // 3. Actions: Nhập liệu (Áp dụng findElement, sendKeys, clear)
            WebElement userField = driver.findElement(LoginLocators.USERNAME_FIELD);
            userField.clear();
            userField.sendKeys("standard_user");

            WebElement passField = driver.findElement(LoginLocators.PASSWORD_FIELD);
            passField.sendKeys("secret_sauce");

            // 4. Click login
            driver.findElement(LoginLocators.LOGIN_BUTTON).click();

            // 5. Verification: Kiểm tra trạng thái
            WebElement successLabel = driver.findElement(LoginLocators.LOGIN_MESSAGE);

            if (successLabel.isDisplayed()) {
                System.out.println("KẾT QUẢ: ĐĂNG NHẬP THÀNH CÔNG");
                System.out.println("Nội dung hiển thị: " + successLabel.getText());
            }

        } catch (Exception e) {
            System.err.println("Lỗi trong quá trình thực thi: " + e.getMessage());
        } finally {
            // 6. Đóng trình duyệt
            driver.quit();
        }
    }
}