package session1.exercise2_3.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import session1.base.BasePage;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public LoginPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    // Thêm method open() theo yêu cầu
    public LoginPage open() {
        driver.get("https://practicetestautomation.com/practice-test-login/");
        return this;
    }

    public LoginPage enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
        return this;  // Enable method chaining
    }

    public LoginPage enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public SuccessPage clickSubmit() {
        submitButton.click();
        return new SuccessPage(driver);  // Return new page object
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSubmit();
    }
}