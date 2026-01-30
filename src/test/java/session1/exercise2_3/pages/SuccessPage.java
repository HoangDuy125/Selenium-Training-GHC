package session1.exercise2_3.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import session1.base.BasePage;

public class SuccessPage extends BasePage {

    @FindBy(className = "has-text-align-center")
    private WebElement successMessage;

    public SuccessPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    public String getSuccessText() {
        return successMessage.getText();
    }

    public boolean isLoggedIn() {
        return successMessage.isDisplayed()
                && successMessage.getText().contains("Congratulations");
    }
}