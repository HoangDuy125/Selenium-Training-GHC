package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.time.Duration;

public class PracticeFormPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // --- Locators ---
    private By firstName = By.id("firstName");
    private By lastName = By.id("lastName");
    private By email = By.id("userEmail");
    private By genderMale = By.cssSelector("label[for='gender-radio-1']");
    private By mobile = By.id("userNumber");

    private By dateInput = By.id("dateOfBirthInput");
    private By monthSelect = By.className("react-datepicker__month-select");
    private By yearSelect = By.className("react-datepicker__year-select");

    private By hobbiesSports = By.cssSelector("label[for='hobbies-checkbox-1']");
    private By currentAddress = By.id("currentAddress");

    private By stateContainer = By.id("state");
    private By cityContainer = By.id("city");

    private By submitBtn = By.id("submit");
    private By successModal = By.id("example-modal-sizes-title-lg");


    public void testSelectDropdown() {
        driver.get("https://demoqa.com/select-menu");

        WebElement dropdownElement = driver.findElement(By.id("oldSelectMenu"));
        Select select = new Select(dropdownElement);

        // Select by visible text
        select.selectByVisibleText("Blue");

        // Verify selection
        WebElement selectedOption = select.getFirstSelectedOption();
        Assert.assertEquals(selectedOption.getText(), "Blue");

        // Select by value
        select.selectByValue("1");

        // Select by index
        select.selectByIndex(2);
    }

    public PracticeFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }
    public void selectGender() {
        WebElement maleRadio = driver.findElement(genderMale);
        maleRadio.click();

        // Verify selection
        Assert.assertTrue(maleRadio.isSelected(), "Male radio should be selected");
    }

    public void selectHobby() {
        WebElement sportsCheckbox = driver.findElement(hobbiesSports);
        js.executeScript("arguments[0].scrollIntoView(true);", sportsCheckbox);
        sportsCheckbox.click();

        // Verify selection
        Assert.assertTrue(sportsCheckbox.isSelected(), "Sports checkbox should be selected");
    }
    public void fillBasicInfo(String fName, String lName, String mail, String phone) {
        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(email).sendKeys(mail);
        driver.findElement(genderMale).click();
        driver.findElement(mobile).sendKeys(phone);
    }

    public void selectDateOfBirth(String month, String year, String day) {
        driver.findElement(dateInput).click(); // Mở lịch

        wait.until(ExpectedConditions.visibilityOfElementLocated(monthSelect)).sendKeys(month);
        wait.until(ExpectedConditions.visibilityOfElementLocated(yearSelect)).sendKeys(year);

        String dayXpath = String.format("//div[contains(@class,'react-datepicker__day--0%s') and not(contains(@class,'outside-month'))]", day);
        driver.findElement(By.xpath(dayXpath)).click();
    }


    public void selectStateAndCity(String stateName, String cityName) {
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(stateContainer));


        driver.findElement(stateContainer).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + stateName + "']"))).click();

        driver.findElement(cityContainer).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + cityName + "']"))).click();
    }

    public void submitForm(String address) {
        driver.findElement(hobbiesSports).click();
        driver.findElement(currentAddress).sendKeys(address);

        js.executeScript("arguments[0].click();", driver.findElement(submitBtn));
    }

    public String getVerifyMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successModal)).getText();
    }
}