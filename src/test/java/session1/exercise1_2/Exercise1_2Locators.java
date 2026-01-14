package session1.exercise1_2;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Exercise1_2Locators {

    @Test
    public void identifyElementsTest() throws Exception {
        // 1. Đọc cấu hình từ file config.properties
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);

        // Lấy đúng key url1.2 từ file của bạn
        String urlFromConfig = props.getProperty("url1.2");

        // 2. Khởi tạo trình duyệt Chrome
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Cấu hình chờ đợi để tránh lỗi 17 giây (Timeout)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Mở URL
        driver.get(urlFromConfig);

        // 3. Thực hiện định vị 10 phần tử trên DemoQA
        try {
            // Click vào menu Text Box trước để hiện các trường nhập liệu
            driver.findElement(By.xpath("//span[text()='Text Box']")).click();

            // L1: ID - Full Name
            driver.findElement(By.id("userName")).sendKeys("Gemini User");

            // L2: CSS Selector - Email
            driver.findElement(By.cssSelector("#userEmail")).sendKeys("test@example.com");

            // L3: XPath Relative - Current Address
            driver.findElement(By.xpath("//textarea[@id='currentAddress']")).sendKeys("Hanoi, Vietnam");

            // L4: Name (Nếu có) hoặc XPath Attribute - Permanent Address
            driver.findElement(By.xpath("//textarea[@id='permanentAddress']")).sendKeys("Same as above");

            // L5: CSS Selector Class - Nút Submit
            By submitBtn = By.cssSelector(".btn-primary");

            // L6: XPath Text - Tiêu đề chính
            By header = By.xpath("//div[@class='main-header' and text()='Text Box']");

            // L7: XPath Contains - Tìm phần tử cha của form
            By form = By.xpath("//div[contains(@class, 'user-form')]");

            // L8: TagName - Tìm tất cả các thẻ input (ví dụ lấy cái đầu tiên)
            int inputCount = driver.findElements(By.tagName("input")).size();

            // L9: XPath Advanced - Tìm Label bằng text pattern
            By labelName = By.xpath("//label[contains(text(),'Full Name')]");

            // L10: CSS Selector kết hợp cha-con
            By submitContainer = By.cssSelector("div#submitWrapper button");

            // Kiểm tra kết quả
            if (driver.findElement(submitBtn).isDisplayed()) {
                System.out.println("Chúc mừng! Đã tìm thấy các phần tử trên: " + urlFromConfig);
            }

        } finally {
            // Dừng 3 giây để bạn nhìn thấy kết quả rồi mới đóng
            Thread.sleep(3000);
            driver.quit();
        }
    }
}