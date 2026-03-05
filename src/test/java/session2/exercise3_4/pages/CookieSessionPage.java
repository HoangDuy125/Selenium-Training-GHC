package session2.exercise3_4.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CookieSessionPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By flashMessage = By.id("flash");
    private final By logoutButton = By.cssSelector("a.button.secondary.radius");

    public CookieSessionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void addUsernameCookie() {
        Cookie usernameCookie = new Cookie.Builder("username", "student")
                .domain("the-internet.herokuapp.com")
                .path("/")
                .isSecure(true)
                .build();
        driver.manage().addCookie(usernameCookie);
    }

    public Cookie getCookieByName(String name) {
        return driver.manage().getCookieNamed(name);
    }

    public void deleteCookieByName(String name) {
        driver.manage().deleteCookieNamed(name);
    }

    public void login(String username, String password) {
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getFlashText() {
        return driver.findElement(flashMessage).getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean waitUntilLoggedIn() {
        try {
            wait.until(d -> {
                String currentUrl = d.getCurrentUrl();
                boolean onSecurePage = currentUrl.contains("/secure");

                List<WebElement> logoutButtons = d.findElements(logoutButton);
                boolean hasLogoutButton = !logoutButtons.isEmpty() && logoutButtons.get(0).isDisplayed();

                List<WebElement> flashes = d.findElements(flashMessage);
                boolean hasSuccessFlash = !flashes.isEmpty()
                        && flashes.get(0).getText().contains("You logged into a secure area!");

                return onSecurePage && (hasLogoutButton || hasSuccessFlash);
            });
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public String getFlashTextIfPresent() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage)).getText();
        } catch (TimeoutException ex) {
            return "";
        }
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    public List<String> getCookieNames() {
        return driver.manage().getCookies().stream()
                .map(Cookie::getName)
                .sorted()
                .toList();
    }

    public Set<Cookie> getAuthSessionCookies() {
        return driver.manage().getCookies().stream()
                .filter(cookie -> {
                    String name = cookie.getName().toLowerCase();
                    return name.contains("session")
                            || name.contains("auth")
                            || name.contains("token")
                            || name.contains("remember");
                })
                .collect(Collectors.toSet());
    }

    public void saveCookies(Set<Cookie> cookies, Path outputPath) throws IOException {
        Files.createDirectories(outputPath.getParent());

        List<String> lines = new ArrayList<>();
        for (Cookie cookie : cookies) {
            long expiryEpoch = cookie.getExpiry() == null
                    ? -1L
                    : cookie.getExpiry().toInstant().toEpochMilli();
            String sameSite = cookie.getSameSite() == null ? "" : cookie.getSameSite();
            String line = String.join("\t",
                    b64(cookie.getName()),
                    b64(cookie.getValue()),
                    b64(cookie.getDomain()),
                    b64(cookie.getPath()),
                    String.valueOf(expiryEpoch),
                    String.valueOf(cookie.isSecure()),
                    String.valueOf(cookie.isHttpOnly()),
                    b64(sameSite)
            );
            lines.add(line);
        }
        Files.write(outputPath, lines, StandardCharsets.UTF_8);
    }

    public void loadCookies(Path inputPath) throws IOException {
        List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split("\t", -1);
            if (parts.length < 8) {
                continue;
            }

            String name = unb64(parts[0]);
            String value = unb64(parts[1]);
            String domain = unb64(parts[2]);
            String path = unb64(parts[3]);
            long expiryEpoch = Long.parseLong(parts[4]);
            boolean secure = Boolean.parseBoolean(parts[5]);
            boolean httpOnly = Boolean.parseBoolean(parts[6]);
            String sameSite = unb64(parts[7]);

            String normalizedDomain = normalizeDomain(domain);
            String normalizedPath = (path == null || path.isBlank()) ? "/" : path;

            Cookie.Builder builder = new Cookie.Builder(name, value)
                    .domain(normalizedDomain)
                    .path(normalizedPath)
                    .isSecure(secure)
                    .isHttpOnly(httpOnly);

            if (expiryEpoch > 0) {
                builder.expiresOn(Date.from(Instant.ofEpochMilli(expiryEpoch)));
            }
            if (!sameSite.isBlank()) {
                builder.sameSite(sameSite);
            }
            driver.manage().addCookie(builder.build());
        }
    }

    private String b64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String unb64(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }

    private String normalizeDomain(String domain) {
        if (domain == null || domain.isBlank()) {
            return "the-internet.herokuapp.com";
        }
        if (domain.startsWith(".")) {
            return domain.substring(1);
        }
        return domain;
    }

    public List<Cookie> getAuthLikeCookies() {
        List<Cookie> authLikeCookies = new ArrayList<>();
        for (Cookie cookie : driver.manage().getCookies()) {
            String name = cookie.getName().toLowerCase();
            if (name.contains("auth") || name.contains("token") || name.contains("session")) {
                authLikeCookies.add(cookie);
            }
        }
        return authLikeCookies;
    }

    public void saveTokenToLocalStorage(String key, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    public String getTokenFromLocalStorage(String key) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return window.localStorage.getItem(arguments[0]);", key);
    }

    public String mask(String value) {
        if (value == null || value.length() <= 6) {
            return "******";
        }
        return value.substring(0, 3) + "..." + value.substring(value.length() - 3);
    }
}
