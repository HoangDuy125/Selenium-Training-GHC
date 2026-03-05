package session3.exercise5_1.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import session3.exercise5_1.base.BasePage;
import session3.exercise5_1.models.LoginCredentials;

public class LoginPage extends BasePage {
    private static final String LOGIN_URL = "https://practicetestautomation.com/practice-test-login/";

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By submitButton = By.id("submit");

    public LoginPage(WebDriver driver) {
        super(driver);
        driver.get(LOGIN_URL);
    }

    public LoginPage enterUsername(String username) {
        sendKeys(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        sendKeys(passwordInput, password);
        return this;
    }

    public DashboardPage clickSubmit() {
        click(submitButton);
        return new DashboardPage(driver);
    }

    public DashboardPage login(LoginCredentials credentials) {
        return enterUsername(credentials.getUsername())
                .enterPassword(credentials.getPassword())
                .clickSubmit();
    }
}
