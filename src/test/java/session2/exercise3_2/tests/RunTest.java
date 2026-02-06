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

    public void testMultiSelectDropdown() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("https://demoqa.com/select-menu");

            WebElement multiSelectElement = driver.findElement(By.id("cars"));
            Select multiSelect = new Select(multiSelectElement);

            // Verify it's a multi-select
            Assert.assertTrue(multiSelect.isMultiple(), "Should be multi-select dropdown");

            // Select multiple options
            multiSelect.selectByVisibleText("Volvo");
            multiSelect.selectByVisibleText("Opel");
            multiSelect.selectByVisibleText("Saab");

            // Verify all selected options
            List<WebElement> selectedOptions = multiSelect.getAllSelectedOptions();
            Assert.assertEquals(selectedOptions.size(), 3, "Should have 3 cars selected");

            // Deselect specific option
            multiSelect.deselectByVisibleText("Opel");

            // Verify deselection
            selectedOptions = multiSelect.getAllSelectedOptions();
            Assert.assertEquals(selectedOptions.size(), 2, "Should have 2 cars remaining");

            // Deselect all
            multiSelect.deselectAll();

            // Verify all deselected
            selectedOptions = multiSelect.getAllSelectedOptions();
            Assert.assertEquals(selectedOptions.size(), 0, "Should have no cars selected");
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