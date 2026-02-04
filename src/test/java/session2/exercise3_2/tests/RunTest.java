package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.PracticeFormPage;

public class RunTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("https://demoqa.com/automation-practice-form");
            PracticeFormPage formPage = new PracticeFormPage(driver);

            // 1. Điền thông tin cơ bản
            formPage.fillBasicInfo("Duy", "Hoang", "hoangduy@gmail.com", "0987654321");

            // 2. Advanced: Chọn ngày tháng năm sinh
            formPage.selectDateOfBirth("May", "2003", "12");

            // 3. Advanced: Chọn State và City động
            formPage.selectStateAndCity("NCR", "Delhi");

            // 4. Submit
            formPage.submitForm("Hanoi, Vietnam");

            // 5. Verify & Close
            String result = formPage.getVerifyMessage();
            System.out.println("Result: " + result);

            if(result.equals("Thanks for submitting the form")) {
                System.out.println("==> TEST CASE PASSED!");
            }

            // Tạm dừng 2 giây để quan sát trước khi đóng
            Thread.sleep(2000);

        } catch (Exception e) {
            System.err.println("Lỗi trong quá trình chạy test: " + e.getMessage());
        } finally {

            if (driver != null) {
                driver.quit();
                System.out.println("Chrome has been closed. Execution Finished.");
            }
        }
    }
}