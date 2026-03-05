package session3.exercise5_1.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import session3.exercise5_1.base.BasePage;

public abstract class AuthenticatedPage extends BasePage {
    private final By logoutButton = By.xpath("//a[contains(.,'Log out')]");

    protected AuthenticatedPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLogoutVisible() {
        return waitVisible(logoutButton).isDisplayed();
    }
}
