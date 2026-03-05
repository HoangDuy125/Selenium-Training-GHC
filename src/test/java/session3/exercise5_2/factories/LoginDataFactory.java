package session3.exercise5_2.factories;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import session3.exercise5_2.models.LoginTestData;
import session3.exercise5_2.utils.DataReader;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class LoginDataFactory {
    private static final String CSV_RESOURCE = "session3/exercise5_2/login-data.csv";
    private static final String JSON_RESOURCE = "session3/exercise5_2/login-data.json";

    private LoginDataFactory() {
    }

    public static Object[][] inlineData() {
        return new Object[][]{
                {"student", "Password123", true},
                {"invalid", "invalid", false},
                {"student", "", false}
        };
    }

    public static Object[][] csvData() {
        List<LoginTestData> rows = DataReader.readCsvFromResource(CSV_RESOURCE);
        return DataReader.toDataProviderArray(rows);
    }

    public static Object[][] jsonData() {
        List<LoginTestData> rows = DataReader.readJsonFromResource(JSON_RESOURCE);
        return DataReader.toDataProviderArray(rows);
    }

    public static Object[][] excelData() {
        Path excelPath = ensureSampleExcelExists();
        List<LoginTestData> rows = DataReader.readExcel(excelPath);
        return DataReader.toDataProviderArray(rows);
    }

    private static Path ensureSampleExcelExists() {
        try {
            Path folder = Path.of("target", "test-data");
            Files.createDirectories(folder);

            Path excelFile = folder.resolve("login-data.xlsx");
            if (!Files.exists(excelFile)) {
                writeSampleExcel(excelFile);
            }
            return excelFile;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to prepare sample Excel data file", e);
        }
    }

    private static void writeSampleExcel(Path outputPath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("login-data");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("username");
            header.createCell(1).setCellValue("password");
            header.createCell(2).setCellValue("expectedResult");

            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("student");
            row1.createCell(1).setCellValue("Password123");
            row1.createCell(2).setCellValue("SUCCESS");

            Row row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue("invalid");
            row2.createCell(1).setCellValue("invalid");
            row2.createCell(2).setCellValue("FAILURE");

            Row row3 = sheet.createRow(3);
            row3.createCell(0).setCellValue("student");
            row3.createCell(1).setCellValue("");
            row3.createCell(2).setCellValue("FAILURE");

            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            try (OutputStream output = Files.newOutputStream(outputPath)) {
                workbook.write(output);
            }
        }
    }
}
