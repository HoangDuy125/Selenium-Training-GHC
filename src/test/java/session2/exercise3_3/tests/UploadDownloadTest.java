package session2.exercise3_3.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import session2.exercise3_3.pages.UploadDownloadPage;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadDownloadTest {

    private WebDriver driver;
    private UploadDownloadPage uploadDownloadPage;

    private static final String FILE_NAME = "sampleFile.jpeg";

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://demoqa.com/upload-download");
        uploadDownloadPage = new UploadDownloadPage(driver);
    }

    @Test(priority = 1, description = "Kiểm tra chức năng tải file xuống máy tính")
    public void testFileDownload() throws InterruptedException {
        uploadDownloadPage.clickDownload();

        Thread.sleep(3000);

        boolean isFileExists = uploadDownloadPage.isFileDownloaded(FILE_NAME);
        Assert.assertTrue(isFileExists, "File '" + FILE_NAME + "' không tìm thấy sau khi download!");

        long fileSize = uploadDownloadPage.getDownloadedFileSize(FILE_NAME);
        Assert.assertTrue(fileSize > 0, "File tải về bị trống (0 bytes)!");

        System.out.println("Download OK. Size = " + fileSize + " bytes");
    }

    @Test(priority = 2, description = "Kiểm tra chức năng upload file", dependsOnMethods = "testFileDownload")
    public void testFileUpload() {
        String userHome = System.getProperty("user.home");
        String absoluteFilePath = Paths.get(userHome, "Downloads", FILE_NAME).toString();

        uploadDownloadPage.uploadFile(absoluteFilePath);

        String resultText = uploadDownloadPage.getUploadedResultText();
        Assert.assertTrue(
                resultText.contains(FILE_NAME),
                "Kết quả '" + resultText + "' không chứa tên file '" + FILE_NAME + "'!"
        );

        System.out.println("Upload OK. UI shows: " + resultText);
    }

    @Test(priority = 3, description = "Advanced: Upload tuần tự 2 file khác nhau từ src/test/resources")
    public void testSequentialUploadTwoFilesFromResources() throws Exception {
        Path file1 = getResourceAsPath("upload-files/file1.txt");
        Path file2 = getResourceAsPath("upload-files/file2.txt");

        // Upload file 1
        uploadDownloadPage.uploadFile(file1.toString());
        String result1 = uploadDownloadPage.getUploadedResultText();
        Assert.assertTrue(
                result1.contains(file1.getFileName().toString()),
                "Kết quả '" + result1 + "' không chứa tên file '" + file1.getFileName() + "'!"
        );

        // Upload file 2
        uploadDownloadPage.uploadFile(file2.toString());
        String result2 = uploadDownloadPage.getUploadedResultText();
        Assert.assertTrue(
                result2.contains(file2.getFileName().toString()),
                "Kết quả '" + result2 + "' không chứa tên file '" + file2.getFileName() + "'!"
        );
    }

    private Path getResourceAsPath(String resource) throws URISyntaxException {
        var url = getClass().getClassLoader().getResource(resource);
        if (url == null) {
            throw new IllegalArgumentException("Resource not found: " + resource);
        }
        return Paths.get(url.toURI());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}