package session2.exercise3_1.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import session2.exercise3_1.base.BasePage;

public class AdvancedActionsPage extends BasePage {
    public AdvancedActionsPage(WebDriver driver) {
        super(driver);
    }

    public void shiftClickDoubleButton() {
        WebElement btn = driver.findElement(By.id("doubleClickBtn"));
        new Actions(driver)
                .keyDown(Keys.SHIFT)
                .click(btn)
                .keyUp(Keys.SHIFT)
                .perform();
    }

    public void ctrlClickRightButton() {
        WebElement btn = driver.findElement(By.id("rightClickBtn"));
        new Actions(driver)
                .keyDown(Keys.CONTROL)
                .click(btn)
                .keyUp(Keys.CONTROL)
                .perform();
    }

    public void typeWithShortcut() {
        driver.navigate().to("https://demoqa.com/text-box");
        WebElement input = driver.findElement(By.id("userName"));
        new Actions(driver)
                .keyDown(Keys.SHIFT)
                .sendKeys(input, "selenium advanced")
                .keyUp(Keys.SHIFT)
                .perform();
    }
}
