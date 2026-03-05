package session3.exercise5_1.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends AuthenticatedPage {
    private final By successTitle = By.className("post-title");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        return getText(successTitle);
    }

    public boolean isAt() {
        return getTitleText().contains("Logged In Successfully") && isLogoutVisible();
    }
}
