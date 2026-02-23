package session2.exercise3_3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

public class UploadDownloadPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By downloadButton = By.id("downloadButton");
    private By uploadInput = By.id("uploadFile");
    private By uploadedFilePathResult = By.id("uploadedFilePath");

    // Constructor
    public UploadDownloadPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions

    public void clickDownload() {
        driver.findElement(downloadButton).click();
    }

    public void uploadFile(String absolutePath) {

        driver.findElement(uploadInput).sendKeys(absolutePath);
    }

    // Advanced Actions
    public void uploadFiles(String... absolutePaths) {
        if (absolutePaths == null || absolutePaths.length == 0) {
            throw new IllegalArgumentException("absolutePaths must not be empty");
        }

        WebElement input = driver.findElement(uploadInput);
        boolean supportsMultiple = input.getAttribute("multiple") != null;

        if (!supportsMultiple && absolutePaths.length > 1) {
            throw new IllegalStateException(
                    "This input does NOT support multiple uploads (missing attribute 'multiple'). " +
                    "Please upload sequentially using uploadFile(path)."
            );
        }

        String joined = String.join("\n", absolutePaths);
        input.sendKeys(joined);
    }

    public String getUploadedResultText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(uploadedFilePathResult)).getText();
    }

    public boolean isFileDownloaded(String fileName) {
        String home = System.getProperty("user.home");
        File file = new File(Paths.get(home, "Downloads", fileName).toString());
        return file.exists();
    }

    public long getDownloadedFileSize(String fileName) {
        String home = System.getProperty("user.home");
        File file = new File(Paths.get(home, "Downloads", fileName).toString());
        return file.length();
    }
}