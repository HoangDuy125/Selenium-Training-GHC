package session1.exercise1_3.locators;

import org.openqa.selenium.By;

public class LoginLocators {

    // Login form
    public static final By USERNAME_FIELD = By.id("username");
    public static final By PASSWORD_FIELD = By.id("password");
    public static final By LOGIN_BUTTON   = By.id("submit");


    public static final By SUCCESS_MESSAGE = By.cssSelector(".post-title");


    public static final By ERROR_MESSAGE = By.id("error");
}
