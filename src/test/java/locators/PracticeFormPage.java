package locators;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PracticeFormPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // --- 1. Danh sách Locators (Bộ định vị) ---
    private By firstNameInput = By.id("firstName");
    private By lastNameInput = By.id("lastName");
    private By emailInput = By.id("userEmail");
    private By maleRadio = By.cssSelector("label[for='gender-radio-1']");
    private By mobileInput = By.id("userNumber");
    private By dateInput = By.id("dateOfBirthInput");
    private By monthSelect = By.className("react-datepicker__month-select");
    private By yearSelect = By.className("react-datepicker__year-select");
    private By hobbySports = By.cssSelector("label[for='hobbies-checkbox-1']");
    private By addressArea = By.id("currentAddress");
    private By stateDropdown = By.id("state");
    private By cityDropdown = By.id("city");
    private By submitButton = By.id("submit");
    private By successMsg = By.id("example-modal-sizes-title-lg");

    // Khởi tạo constructor
    public PracticeFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // --- 2. Các Action (Phương thức thực hiện) ---
    public void enterFullName(String fName, String lName) {
        driver.findElement(firstNameInput).sendKeys(fName);
        driver.findElement(lastNameInput).sendKeys(lName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void selectGender() {
        driver.findElement(maleRadio).click();
    }

    public void enterMobile(String number) {
        driver.findElement(mobileInput).sendKeys(number);
    }

    public void selectBirthDate(String month, String year, String day) {
        driver.findElement(dateInput).click();
        driver.findElement(monthSelect).sendKeys(month);
        driver.findElement(yearSelect).sendKeys(year);
        // Chọn ngày động dựa trên tham số truyền vào
        String dayXpath = String.format("//div[contains(@class,'react-datepicker__day--0%s') and not(contains(@class,'outside-month'))]", day);
        driver.findElement(By.xpath(dayXpath)).click();
    }

    public void selectHobby() {
        WebElement element = driver.findElement(hobbySports);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    public void enterAddress(String address) {
        driver.findElement(addressArea).sendKeys(address);
    }

    public void selectStateAndCity(String state, String city) {
        // Chọn State
        driver.findElement(stateDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + state + "']"))).click();
        // Chọn City
        driver.findElement(cityDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + city + "']"))).click();
    }

    public void clickSubmit() {
        js.executeScript("arguments[0].click();", driver.findElement(submitButton));
    }

    public String getConfirmationText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg)).getText();
    }
}