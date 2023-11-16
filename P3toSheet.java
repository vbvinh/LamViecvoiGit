package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class P3toSheet {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium jar and driver\\Drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("http://3p.vnpt.vn");

            login(driver, "vinhvb", "V0b@0v1n4cto");

            WebElement OTPInput = driver.findElement(By.xpath("//input[@id='passOTP']"));
            System.out.println("Vui lòng nhập mã OTP:");
            Scanner scanner = new Scanner(System.in);
            String otpValue = scanner.nextLine();
            OTPInput.sendKeys(otpValue);
            scanner.close();

            WebElement loginButton2 = driver.findElement(By.xpath("//*[@id='loginForm']/div[1]/button"));
            loginButton2.click();

            clickElementByXPath(driver, "/html/body/div[2]/div/div/div/div/section[4]/div/div/div[2]/a/div/div[2]/div");
            clickElementByXPath(driver, "//*[@id='table_congvieccanhan_info_wrapper']/div[1]/div[1]/div/button[3]");

            Map<String, String> names = new HashMap<>();
            names.put("Trụ", "2");
            names.put("Long", "3");
            names.put("Giang", "4");
            names.put("Kiên", "5");
            names.put("Minh", "6");
            names.put("Kiệt", "7");
            names.put("Tâm", "8");
            names.put("Cuong", "9");
            names.put("Hùng", "10");
            names.put("Việt", "11");
            names.put("Nghĩa", "12");
            names.put("Thành", "13");
            names.put("Lĩnh", "16");
            names.put("Lộc", "17");
            names.put("Dững", "18");

            // Thêm các tên khác vào Map tương ứng với số thứ tự
            for (Map.Entry<String, String> entry : names.entrySet()) {
                String name = entry.getKey();
                String number = entry.getValue();
                List<List<Object>> valuesToUpdate = new ArrayList<>();
            }

            Sheets sheetsService = getSheetsService();
            String spreadsheetId = "1W8krm2CQd5A1E5kgko7bCT69tSIXRsZzMiEMlBh0MdA";
            String sheetRangeTemplate = "Tonghop!K%d"; // Mẫu range cho từng hàng

            // Loop through your data and update Google Sheets for each entry
            for (Map.Entry<String, String> entry : names.entrySet()) {
                String name = entry.getKey();
                String number = entry.getValue();
                List<List<Object>> valuesToUpdate = new ArrayList<>();

                WebDriverWait wait = new WebDriverWait(driver, 10);
                String xpathTemplate = "//*[@id='table_dsThongkeDiem']/tbody/tr[%s]/td[%s]";
                WebElement tuchamElement2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(xpathTemplate, number, "2"))));
                WebElement tuchamElement3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(xpathTemplate, number, "3"))));

                String elementtucham2 = tuchamElement2.getText();
                String elementtucham3 = tuchamElement3.getText();

                valuesToUpdate.add(Arrays.asList(elementtucham2, elementtucham3));

                int index = getIndexFromName(name);
                if (index != -1) {
                    String sheetRange = String.format(sheetRangeTemplate, index);

                    writeToGoogleSheets(sheetsService, spreadsheetId, sheetRange, valuesToUpdate);
                } else {
                    System.out.println("Không tìm thấy tên trong bản.");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Giữ WebDriver mở để kiểm tra trạng thái sau khi đăng nhập
            //driver.quit();
        }
    }
    private static int getIndexFromName(String name) {
        // Map tên với số hàng tương ứng
        Map<String, Integer> nameToIndexMap = new HashMap<>();
        nameToIndexMap.put("Trụ", 4);
        nameToIndexMap.put("Long", 5);
        nameToIndexMap.put("Giang", 6);
        nameToIndexMap.put("Kiên", 7);
        nameToIndexMap.put("Minh", 8);
        nameToIndexMap.put("Kiệt", 9);
        nameToIndexMap.put("Tâm", 10);
        nameToIndexMap.put("Cuong", 11);
        nameToIndexMap.put("Hùng", 12);
        nameToIndexMap.put("Việt", 13);
        nameToIndexMap.put("Nghĩa", 14);
        nameToIndexMap.put("Thành", 15);
        nameToIndexMap.put("Lĩnh", 16);
        nameToIndexMap.put("Lộc", 17);
        nameToIndexMap.put("Dững", 18);
        // Lấy vị trí từ tên
        Integer index = nameToIndexMap.get(name);

        if (index == null) {
            // Trả -1 hoặc giá trị mặc định nếu tên không tồn tại trong map
            return -1; // Hoặc trả giá trị mặc định tùy thuộc vào yêu cầu của bạn
        }

        return index;
    }

    private static void login(WebDriver driver, String username, String password) {
        WebElement userNameInput = driver.findElement(By.xpath("//input[@id='username']"));
        userNameInput.sendKeys("vinhvb");

        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        passwordInput.sendKeys("V0b@0v1n4cto");

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.attributeToBe(userNameInput, "value", "vinhvb"));
        wait.until(ExpectedConditions.attributeToBe(passwordInput, "value", "V0b@0v1n4cto"));

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"fm1\"]/section/button"));
        loginButton.click();


    }

    private static void clickElementByXPath(WebDriver driver, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        element.click();
    }

    private static void printScore(WebDriver driver, String name, String number) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String xpathTemplate = "//*[@id='table_dsThongkeDiem']/tbody/tr[%s]/td[%s]";
        WebElement tuchamElement2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(xpathTemplate, number, "2"))));
        WebElement tuchamElement3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(xpathTemplate, number, "3"))));

        String elementtucham2 = tuchamElement2.getText();
        String elementtucham3 = tuchamElement3.getText();

        System.out.println("Diểm ca nhân " + name + " tự chấm: " + elementtucham2 + ", Trưởng trạm chấm: " + elementtucham3);
    }

    private static void writeToGoogleSheets(Sheets sheetsService, String spreadsheetId, String range, List<List<Object>> values) {
        ValueRange body = new ValueRange().setValues(values);

        try {
            sheetsService.spreadsheets().values()
                    .update(spreadsheetId, range, body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
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