package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PracticeFormPage;

import java.util.List;

public class RunTest {

    @Test
    public void testMultiSelectDropdown() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("https://demoqa.com/select-menu");

            WebElement multiSelect = driver.findElement(By.id("cars"));
            Select select = new Select(multiSelect);

            // Verify it's a multi-select dropdown
            Assert.assertTrue(select.isMultiple(), "Cars dropdown must be multi-select");

            // Select multiple options
            select.selectByVisibleText("Volvo");
            select.selectByVisibleText("Saab");

            // Verify selections
            List<WebElement> selected = select.getAllSelectedOptions();
            Assert.assertEquals(selected.size(), 2);

            // Deselect methods
            select.deselectByVisibleText("Volvo");
            select.deselectAll();

            // Verify deselect all
            selected = select.getAllSelectedOptions();
            Assert.assertEquals(selected.size(), 0);

        } finally {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("https://demoqa.com/automation-practice-form");
            PracticeFormPage formPage = new PracticeFormPage(driver);

            formPage.fillBasicInfo("Duy", "Hoang", "hoangduy@gmail.com", "0987654321");
            formPage.selectDateOfBirth("May", "2003", "12");
            formPage.selectStateAndCity("NCR", "Delhi");
            formPage.submitForm("Hanoi, Vietnam");

            String result = formPage.getVerifyMessage();
            System.out.println("Result: " + result);

            if (result.equals("Thanks for submitting the form")) {
                System.out.println("==> TEST CASE PASSED!");
            }

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