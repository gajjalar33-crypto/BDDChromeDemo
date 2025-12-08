package com.example.stepdefinitions;



import static com.example.Utility.Locators.java.Locators.ADD_BUTTON;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;
import io.cucumber.java.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Assert;

import java.time.Duration;

public class DemoQASteps {

    WebDriver driver;

    // ---------- Common Browser Handling ----------

    @Given("I open Chrome browser")
    public void i_open_chrome_browser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("Chrome browser launched successfully.");
    }

    // Used in first scenario
    @When("I navigate to demoqa")
    public void i_navigate_to_demoqa() {
        ensureDriver();
        driver.get("https://demoqa.com/webtables");
        System.out.println("Navigated to DemoQA WebTables.");
    }

    // Used in second scenario
    @Given("I navigate to DemoQA Web Tables page")
    public void i_navigate_to_demoqa_web_tables_page() {
        ensureDriver();
        driver.get("https://demoqa.com/webtables");
        System.out.println("Navigated to DemoQA WebTables page.");
    }

    // ---------- Verification steps for first scenario ----------

    @Then("I should see demoqa page title")
    public void i_should_see_demoqa_page_title() {
        String title = driver.getTitle();
        System.out.println("Title = " + title);
        Assert.assertTrue("Title not matched!", title.toUpperCase().contains("DEMOQA"));
    }

    @Then("I should see demoqa home page")
    public void i_should_see_demoqa_home_page() {
        String url = driver.getCurrentUrl();
        System.out.println("Current URL = " + url);
        Assert.assertTrue("Home page not loaded!", url.contains("demoqa.com"));
        // Browser close @After lo jarugutundi
    }

    // ---------- Extra steps for WebTable ----------

    @Given("I wait for the WebTable to load")
    public void i_wait_for_the_webtable_to_load() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='rt-table']")
        ));
        System.out.println("WebTable loaded.");
    }

    @Given("I scroll down to the WebTable")
    public void i_scroll_down_to_the_webtable() {
        WebElement table = driver.findElement(By.xpath("//div[@class='rt-table']"));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true); window.scrollBy(0, -150);",
                table
        );
        System.out.println("Scrolled near WebTable.");
    }

    // ---------- Edit step (salary only, no submit here) ----------

    @When("I edit the last row salary to {string}")
    public void i_edit_the_last_row_salary_to(String newSalary) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until at least one Edit button is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@title='Edit']")
        ));

        // Get all edit icons
        java.util.List<WebElement> editButtons = driver.findElements(
                By.xpath("//span[@title='Edit']")
        );

        System.out.println("Total Edit buttons found = " + editButtons.size());

        // Last visible edit button (last data row)
        WebElement lastEditBtn = editButtons.get(editButtons.size() - 1);

        // Bring the button into view and slightly up (avoid ad iframe overlay)
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true); window.scrollBy(0, -100);",
                lastEditBtn
        );

        // Prefer JS click to avoid ElementClickInterceptedException
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lastEditBtn);
            System.out.println("Last row Edit button clicked with JS.");
        } catch (Exception e) {
            System.out.println("JS click failed, trying normal click: " + e.getMessage());
            lastEditBtn.click();
            System.out.println("Last row Edit button clicked with normal click.");
        }

        // Salary field edit – JUST TYPE, no submit here
        WebElement salary = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("salary"))
        );
        salary.clear();
        salary.sendKeys(newSalary);
        System.out.println("Salary typed in form: " + newSalary);
    }

    // ---------- Separate submit step ----------

    @When("I click the submit button")
    public void i_click_the_submit_button() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement submitBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']"))
        );

        // Scroll slightly to avoid ad iframe issues
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true); window.scrollBy(0, -80);",
                submitBtn
        );

        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
            System.out.println("Submit button clicked normally.");
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
            System.out.println("Submit button clicked using JS fallback: " + e.getClass().getSimpleName());
        }
    }

    // ---------- Verification of updated salary ----------

    @Then("I should see the last row salary as {string}")
    public void i_should_see_the_last_row_salary_as(String expectedSalary) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='rt-tr-group']//div[@class='rt-td'][5]")
        ));

        java.util.List<WebElement> salaryCells = driver.findElements(
                By.xpath("//div[@class='rt-tr-group']//div[@class='rt-td'][5]")
        );

        String actual = "";

        // Take LAST NON-EMPTY salary (skip blank virtual row)
        for (int i = salaryCells.size() - 1; i >= 0; i--) {
            actual = salaryCells.get(i).getText().trim();
            if (!actual.isEmpty()) {
                break;
            }
        }

        System.out.println("Last non-empty row salary on UI = " + actual);
        Assert.assertEquals(expectedSalary, actual);
    }

    // ---------- Tear down ----------

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed in @After.");
            driver = null;
        }
    }

    // ---------- Helper ----------

    private void ensureDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            System.out.println("Chrome browser launched from ensureDriver().");
        }
    }

    @When("I click the add button")
    public void iClickTheAddButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Scroll to top so Add button is visible
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

        // 👉 IMPORTANT: use ADD_BUTTON directly, no By.xpath("ADD_BUTTON")
        WebElement addBtn = wait.until(
                ExpectedConditions.elementToBeClickable(ADD_BUTTON)
        );

        addBtn.click();
        System.out.println("Add button clicked");
    }

}
