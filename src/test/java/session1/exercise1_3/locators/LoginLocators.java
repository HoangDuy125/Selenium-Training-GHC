package session1.exercise1_3.locators; // Sửa lại đúng đường dẫn thư mục thực tế

import org.openqa.selenium.By;

public class LoginLocators {
    // Lưu các locator dưới dạng biến public static để dùng ở file Test
    public static final By USERNAME_FIELD = By.id("user-name");
    public static final By PASSWORD_FIELD = By.id("password");
    public static final By LOGIN_BUTTON = By.id("login-button");

    // Message sau khi đăng nhập thành công hoặc thất bại
    public static final By LOGIN_MESSAGE = By.className("title");
    public static final By ERROR_MESSAGE = By.xpath("//h3[@data-test='error']");
}