package session3.exercise5_2.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import session3.exercise5_2.models.LoginTestData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class DataReader {
    private DataReader() {
    }

    public static List<LoginTestData> readCsvFromResource(String resourcePath) {
        List<LoginTestData> rows = new ArrayList<>();
        try (InputStream stream = openResource(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            boolean headerSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Invalid CSV row: " + line);
                }
                rows.add(new LoginTestData(
                        parts[0].trim(),
                        parts[1],
                        LoginTestData.parseExpectedResult(parts[2])));
            }
            return rows;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read CSV resource: " + resourcePath, e);
        }
    }

    public static List<LoginTestData> readJsonFromResource(String resourcePath) {
        try (InputStream stream = openResource(resourcePath)) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(stream);
            if (!root.isArray()) {
                throw new IllegalArgumentException("JSON root must be an array: " + resourcePath);
            }

            List<LoginTestData> rows = new ArrayList<>();
            for (JsonNode node : root) {
                rows.add(new LoginTestData(
                        node.path("username").asText(""),
                        node.path("password").asText(""),
                        LoginTestData.parseExpectedResult(node.path("expectedResult").asText("FAILURE"))));
            }
            return rows;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read JSON resource: " + resourcePath, e);
        }
    }

    public static List<LoginTestData> readExcel(Path filePath) {
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("Excel file does not exist: " + filePath.toAbsolutePath());
        }

        try (InputStream stream = Files.newInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(stream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            if (!iterator.hasNext()) {
                return List.of();
            }
            iterator.next();

            List<LoginTestData> rows = new ArrayList<>();
            DataFormatter formatter = new DataFormatter();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                String username = getCellText(row.getCell(0), formatter);
                String password = getCellText(row.getCell(1), formatter);
                String expectedResult = getCellText(row.getCell(2), formatter);
                if (username.isBlank() && password.isBlank() && expectedResult.isBlank()) {
                    continue;
                }
                rows.add(new LoginTestData(
                        username,
                        password,
                        LoginTestData.parseExpectedResult(expectedResult)));
            }
            return rows;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read Excel file: " + filePath.toAbsolutePath(), e);
        }
    }

    public static Object[][] toDataProviderArray(List<LoginTestData> rows) {
        Object[][] data = new Object[rows.size()][3];
        for (int i = 0; i < rows.size(); i++) {
            LoginTestData row = rows.get(i);
            data[i][0] = row.getUsername();
            data[i][1] = row.getPassword();
            data[i][2] = row.isExpectedSuccess();
        }
        return data;
    }

    private static InputStream openResource(String resourcePath) {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return stream;
    }

    private static String getCellText(Cell cell, DataFormatter formatter) {
        if (cell == null) {
            return "";
        }
        return formatter.formatCellValue(cell).trim();
    }
}
