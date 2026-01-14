package session1.exercise1_2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class Exercise1_2Advanced {

    @Test
    public void dynamicXPathTest() throws Exception {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);
        String url = props.getProperty("url1.2"); // Đảm bảo key là url1.2

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // 1. Xử lý menu "Text Box" (Dynamic XPath theo text)
            WebElement textBoxMenu = driver.findElement(By.xpath("//span[contains(text(),'Text Box')]"));
            js.executeScript("arguments[0].scrollIntoView(true);", textBoxMenu); // Cuộn tới menu
            textBoxMenu.click();

            // 2. Kỹ thuật "Parent-to-Child" & "Following Sibling" cho nhiều phần tử giống nhau
            // Tìm ô input nằm trong div có nhãn là 'Full Name'
            WebElement inputName = driver.findElement(By.xpath("//div[label[text()='Full Name']]//input"));
            inputName.sendKeys("Hoang Duy Advanced");

            // 3. Dynamic XPath sử dụng Placeholder (Xử lý khi ID bị thay đổi)
            WebElement inputEmail = driver.findElement(By.xpath("//input[@placeholder='name@example.com']"));
            inputEmail.sendKeys("advanced@example.com");

            // 4. Kỹ thuật "Following" để tìm textarea ngay sau một label bất kỳ
            WebElement currentAddress = driver.findElement(By.xpath("//label[@id='currentAddress-label']/following::textarea[1]"));
            currentAddress.sendKeys("123 Dynamic Street");

            // 5. Click Submit bằng XPath kết hợp (AND) để tránh nhầm lẫn với các nút khác
            WebElement submitBtn = driver.findElement(By.xpath("//button[@id='submit' and contains(@class,'btn-primary')]"));
            js.executeScript("arguments[0].click();", submitBtn); // Click bằng JS để tránh bị quảng cáo che

            // 6. Kiểm tra kết quả hiển thị bằng text pattern
            WebElement result = driver.findElement(By.xpath("//p[contains(@id,'name') and contains(text(),'Name:')]"));
            if (result.isDisplayed()) {
                System.out.println("PASS: Đã thực thi thành công Dynamic XPath!");
            }

        } catch (Exception e) {
            System.out.println("LỖI CHI TIẾT: " + e.getMessage());
            throw e;
        } finally {
            Thread.sleep(3000);
            driver.quit();
        }
    }
}