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

    // Nhận parameter từ testng.xml
    @Parameters({"a", "b"})
    @BeforeClass
    public void beforeClass(@Optional("20") int a, @Optional("10") int b) {
        this.a = a;
        this.b = b;
        System.out.println("Initialize data for CalculatorTest from XML: a=" + a + ", b=" + b);
    }

    @BeforeMethod
    public void beforeMethod() {
        softAssert = new SoftAssert();
    }

    // Nhóm kiểm tra các phép tính cơ bản
    @Test(groups = "arithmetic", priority = 1)
    public void testAddition() {
        int result = a + b;
        Assert.assertEquals(result, a + b, "Addition result is incorrect!");
    }

    @Test(groups = "arithmetic", priority = 2)
    public void testSubtraction() {
        int result = a - b;
        Assert.assertEquals(result, a - b, "Subtraction result is incorrect!");
    }

    // Nhóm kiểm tra nâng cao sử dụng SoftAssert
    @Test(groups = "advanced", priority = 3)
    public void testMultipleCalculations() {
        softAssert.assertEquals(a * b, a * b, "Multiplication failed!");
        softAssert.assertEquals(a / b, a / b, "Division failed!");
        softAssert.assertTrue((a + b) > 0, "Sum should be positive!");
        softAssert.assertAll();
    }

    // Test có phụ thuộc
    @Test(groups = "advanced", dependsOnGroups = "arithmetic", priority = 4)
    public void testDependentCalculations() {
        int result = (a + b) * 2;
        Assert.assertEquals(result, (a + b) * 2, "Dependent calculation failed!");
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
