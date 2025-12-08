package com.example.stepdefinitions;
import com.example.Utility.ExcelUtil;

import com.example.base.BaseClass;
import com.example.Utility.ExcelUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OpenOrangeHRMSteps {

    WebDriver driver = BaseClass.driver;


    @When("I login to OrangeHRM with users from Excel")
    public void i_login_to_orangehrm_with_users_from_excel() throws Exception {
        ExcelUtil.setExcelFile("OrangeHRMUsers.xlsx", "Sheet1");

        int rowCount = ExcelUtil.getRowCount();

        // 1) Excel load (file only – path ExcelUtil lo add chestundi)
       // ExcelUtil.setExcelFile("OrangeHRMUsers.xlsx", "Sheet1");
       // int rowCount = ExcelUtil.getRowCount();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 2) Row 1 nundi start (row 0 header)
        for (int i = 1; i < rowCount; i++) {

            String username = ExcelUtil.getCellData(i, 0);
            String password = ExcelUtil.getCellData(i, 1);

            System.out.println("➡ Trying login: " + username + " / " + password);

            // 3) OrangeHRM login page open
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // username & password fields wait
            WebElement userField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("username"))
            );
            WebElement passField = driver.findElement(By.name("password"));
            WebElement loginBtn = driver.findElement(By.cssSelector("button[type='submit']"));

            // 4) Values enter cheyyi
            userField.clear();
            userField.sendKeys(username);

            passField.clear();
            passField.sendKeys(password);

            loginBtn.click();

            // konchem time ivvadaniki
            Thread.sleep(2000);

            // 5) Login success ayithe logout cheyyi (next user kosam clean state)
            try {
                WebElement userMenu = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector("span.oxd-userdropdown-tab")
                        )
                );
                userMenu.click();

                WebElement logoutLink = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//a[normalize-space()='Logout']"))
                );
                logoutLink.click();

                System.out.println("✅ Logout done for user: " + username);
            } catch (TimeoutException e) {
                System.out.println("❌ Login failed for user: " + username + " (no logout menu)");
            }

            // next iteration mundu small pause
            Thread.sleep(1000);
        }

        // Excel file close
        ExcelUtil.closeWorkbook();
    }

    @Then("I close the browser")
    public void i_close_the_browser() {
        if (driver != null) {
            driver.quit();
        }
        System.out.println("✔ Browser closed from step");
    }



}
