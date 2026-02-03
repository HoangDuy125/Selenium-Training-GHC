package session2.exercise3_1.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import session2.exercise3_1.base.BasePage;

public class ButtonsPage extends BasePage {
    public ButtonsPage(WebDriver driver) {
        super(driver);
    }

    public void doubleClick() {
        WebElement btn = driver.findElement(By.id("doubleClickBtn"));
        new Actions(driver).doubleClick(btn).perform();
    }

    public void rightClick() {
        WebElement btn = driver.findElement(By.id("rightClickBtn"));
        new Actions(driver).contextClick(btn).perform();
    }

    public void dynamicClick() {
        WebElement btn = driver.findElement(By.xpath("//button[text()='Click Me']"));
        new Actions(driver).moveToElement(btn).click().perform();
    }

    public String getDoubleClickMessage() {
        return driver.findElement(By.id("doubleClickMessage")).getText();
    }

    public String getRightClickMessage() {
        return driver.findElement(By.id("rightClickMessage")).getText();
    }

    public String getDynamicClickMessage() {
        return driver.findElement(By.id("dynamicClickMessage")).getText();
    }
}
