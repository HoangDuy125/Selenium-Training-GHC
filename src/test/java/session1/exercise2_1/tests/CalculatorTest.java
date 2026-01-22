package session1.exercise2_1.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class CalculatorTest {
    private int a;
    private int b;
    private SoftAssert softAssert;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("--- Starting Test Suite Execution ---");
    }

    @BeforeClass
    public void beforeClass() {
        // Khởi tạo dữ liệu mẫu cho Class
        a = 20;
        b = 10;
        System.out.println("Initialize data for CalculatorTest: a=" + a + ", b=" + b);
    }

    @BeforeMethod
    public void beforeMethod() {
        // Reset SoftAssert trước mỗi test case để tránh cộng dồn lỗi
        softAssert = new SoftAssert();
    }

    // Nhóm kiểm tra các phép tính cơ bản
    @Test(groups = "arithmetic", priority = 1)
    public void testAddition() {
        int result = a + b;
        Assert.assertEquals(result, 30, "Addition result is incorrect!");
    }

    @Test(groups = "arithmetic", priority = 2)
    public void testSubtraction() {
        int result = a - b;
        Assert.assertEquals(result, 10, "Subtraction result is incorrect!");
    }

    // Nhóm kiểm tra nâng cao sử dụng SoftAssert
    @Test(groups = "advanced", priority = 3)
    public void testMultipleCalculations() {
        softAssert.assertEquals(a * b, 200, "Multiplication failed!");
        softAssert.assertEquals(a / b, 2, "Division failed!");
        softAssert.assertTrue((a + b) > 0, "Sum should be positive!");

        // Tổng hợp kết quả: Nếu có lỗi ở trên, test case mới bị đánh dấu Fail tại đây
        softAssert.assertAll();
    }

    // Test có phụ thuộc: Chỉ chạy nếu nhóm arithmetic thành công
    @Test(groups = "advanced", dependsOnGroups = "arithmetic", priority = 4)
    public void testDependentCalculations() {
        int result = (a + b) * 2;
        Assert.assertEquals(result, 60, "Dependent calculation failed!");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Finished a test method execution.");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("Clean up data for CalculatorTest.");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("--- All Tests Finished. Generating Report ---");
    }
}