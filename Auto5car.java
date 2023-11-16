package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

public class Auto5car {

    private static final String SPREADSHEET_ID = "1SrCeROZdJhmiCOIJHwXSHGVdYkeCG69wMW-QQDcAtAw";
    private static final String SERVICE_ACCOUNT_JSON_PATH = "C:\\Program Files\\Webdriver\\car12345678-8f4f89947941.json";
    private static final String XPATH1 = "//*[@id='vec227685']";
    private static final String XPATH2 = "//*[@id='vec227665']";
    private static final String XPATH3 = "//*[@id='vec227687']";
    private static final String XPATH4 = "//*[@id='vec227812']";
    private static final String XPATH5 = "//*[@id='vec228935']";

    //CMU://*[@id="vec227812"]/td[2], RGA://*[@id="vec228935"]/td[2]

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            login(driver);

            while (true) {
                WebElement regionElement1 = driver.findElement(By.xpath(XPATH1));
                WebElement regionElement2 = driver.findElement(By.xpath(XPATH2));
                WebElement regionElement3 = driver.findElement(By.xpath(XPATH3));
                WebElement regionElement4 = driver.findElement(By.xpath(XPATH4));
                WebElement regionElement5 = driver.findElement(By.xpath(XPATH5));
                String elementRegion1 = regionElement1.getText();
                String elementRegion2 = regionElement2.getText();
                String elementRegion3 = regionElement3.getText();
                String elementRegion4 = regionElement4.getText();
                String elementRegion5 = regionElement5.getText();

                System.out.println("Vị trí của xe 1" + elementRegion1);
                System.out.println("Vị trí của xe 2" + elementRegion2);
                System.out.println("Vị trí của xe 3" + elementRegion3);
                System.out.println("Vị trí của xe 4" + elementRegion4);
                System.out.println("Vị trí của xe 5" + elementRegion5);

                writeToGoogleSheets(elementRegion1, elementRegion2, elementRegion3, elementRegion4, elementRegion5);

                // Delay for 2 minutes
                TimeUnit.MINUTES.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void login(WebDriver driver) {
        driver.get("http://quanlyxe.vn/Account.aspx/LogOn?ReturnUrl=%2fMonitor.aspx");

        WebElement userNameInput = driver.findElement(By.id("UserName"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', 'hatangmangmn')", userNameInput);

        WebElement passwordInput = driver.findElement(By.id("Password"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '123456')", passwordInput);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeToBe(userNameInput, "value", "hatangmangmn"));
        wait.until(ExpectedConditions.attributeToBe(passwordInput, "value", "123456"));

        WebElement loginButton = driver.findElement(By.name("login"));
        loginButton.click();
    }

    private static void writeToGoogleSheets(String elementRegion1, String elementRegion2, String elementRegion3, String elementRegion4, String elementRegion5) {
        try {
            Sheets sheetsService = getSheetsService();

            // Tạo danh sách các giá trị để cập nhật
            List<List<Object>> values = Arrays.asList(
                    Collections.singletonList(elementRegion1),
                    Collections.singletonList(elementRegion2),
                    Collections.singletonList(elementRegion3),
                    Collections.singletonList(elementRegion4),
                    Collections.singletonList(elementRegion5)
            );

            // Tạo các đối tượng ValueRange cho từng giá trị
            ValueRange body1 = new ValueRange().setValues(values.subList(0, 1));
            ValueRange body2 = new ValueRange().setValues(values.subList(1, 2));
            ValueRange body3 = new ValueRange().setValues(values.subList(2, 3));
            ValueRange body4 = new ValueRange().setValues(values.subList(3, 4));
            ValueRange body5 = new ValueRange().setValues(values.subList(4, 5));

            // Cập nhật các ô tương ứng trong Google Sheets
            UpdateValuesResponse result1 = sheetsService.spreadsheets().values()
                    .update("1SrCeROZdJhmiCOIJHwXSHGVdYkeCG69wMW-QQDcAtAw", "Sheet1!A1", body1)
                    .setValueInputOption("RAW")
                    .execute();
            UpdateValuesResponse result2 = sheetsService.spreadsheets().values()
                    .update("1SrCeROZdJhmiCOIJHwXSHGVdYkeCG69wMW-QQDcAtAw", "Sheet1!A2", body2)
                    .setValueInputOption("RAW")
                    .execute();
            UpdateValuesResponse result3 = sheetsService.spreadsheets().values()
                    .update("1SrCeROZdJhmiCOIJHwXSHGVdYkeCG69wMW-QQDcAtAw", "Sheet1!A3", body3)
                    .setValueInputOption("RAW")
                    .execute();
            UpdateValuesResponse result4 = sheetsService.spreadsheets().values()
                    .update("1SrCeROZdJhmiCOIJHwXSHGVdYkeCG69wMW-QQDcAtAw", "Sheet1!A4", body4)
                    .setValueInputOption("RAW")
                    .execute();
            UpdateValuesResponse result5 = sheetsService.spreadsheets().values()
                    .update("1SrCeROZdJhmiCOIJHwXSHGVdYkeCG69wMW-QQDcAtAw", "Sheet1!A5", body5)
                    .setValueInputOption("RAW")
                    .execute();

            System.out.println("Updated cells 1: " + result1.getUpdatedCells());
            System.out.println("Updated cells 2: " + result2.getUpdatedCells());
            System.out.println("Updated cells 3: " + result3.getUpdatedCells());
            System.out.println("Updated cells 4: " + result4.getUpdatedCells());
            System.out.println("Updated cells 5: " + result5.getUpdatedCells());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


    private static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        FileInputStream credentialsStream = new FileInputStream("C:\\Program Files\\Webdriver\\car12345678-8f4f89947941.json");
        GoogleCredential credentials = GoogleCredential.fromStream(credentialsStream)
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets"));
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        return new Sheets.Builder(httpTransport, jsonFactory, credentials)
                .setApplicationName("Your Application Name")
                .build();
    }
}
