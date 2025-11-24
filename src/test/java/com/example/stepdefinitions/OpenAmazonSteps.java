package com.example.stepdefinitions;

import com.example.base.BaseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OpenAmazonSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public OpenAmazonSteps() {
        driver = BaseClass.driver;
        wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
        js     = (JavascriptExecutor) driver;
    }

    private void handleAmazonPopup() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            // 1️⃣ Handle "Continue" / login popup
            List<WebElement> continueBtn = driver.findElements(
                    By.xpath("//input[@id='continue' or @id='si_signin_button' or @aria-label='Continue']")
            );

            if (!continueBtn.isEmpty()) {
                continueBtn.get(0).click();
                System.out.println("✔ Amazon Continue popup handled");
                Thread.sleep(1000);
            }

            // 2️⃣ Handle "Continue shopping" popup (button)
            List<WebElement> contShopping = driver.findElements(
                    By.xpath("//button[normalize-space()='Continue shopping']")
            );

            if (!contShopping.isEmpty()) {
                contShopping.get(0).click();
                System.out.println("✔ Amazon Continue Shopping popup handled");
            }

        } catch (Exception e) {
            System.out.println("ℹ No popup found or already handled: " + e.getMessage());
        }
    }

    // 1) Open Amazon
    @When("I navigate to Amazon")
    public void i_navigate_to_Amazon() {
        driver.get("https://www.amazon.in");
        handleAmazonPopup();
        System.out.println("Amazon Title = " + driver.getTitle());
    }

    // 2) Just print title (optional)
    @Then("I should see Amazon page title")
    public void i_should_see_Amazon_page_title() {
        System.out.println("Title: " + driver.getTitle());
    }

    // 3) Confirm we are on home page (simple check)
    @And("I should see Amazon home page")
    public void iShouldSeeAmazonHomePage() {

        wait.until(ExpectedConditions.or(
                ExpectedConditions.titleContains("Amazon.in"),
                ExpectedConditions.titleContains("Online Shopping site in India")
        ));

        System.out.println("Amazon home page is visible. Title = " + driver.getTitle());
    }

    // 4) Click on "Air conditioners"
    @And("I click on Air conditioners")
    public void iClickOnAirConditioners() {

        // Scroll little bit down so cards load
        js.executeScript("window.scrollBy(0, 1200);");

        WebElement airCond = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//span[normalize-space()='Air conditioners']")
                )
        );

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", airCond);
        js.executeScript("arguments[0].click();", airCond);

        System.out.println("'Air conditioners' clicked");
    }

    // 5) Add Voltas AC to cart
    @Then("I add Voltas AC to cart")
    public void i_add_voltas_ac_to_cart() {

        String productXpath =
                "//span[contains(.,'Voltas 2 Ton, 3 Star, Inverter Split AC (Copper, 4-in-1 Adjustable Mode')]"
                        + "/ancestor::div[@data-asin and .//button[@name='submit.addToCart']]";

        // Wait for Voltas product block
        WebElement productBlock = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(productXpath))
        );

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", productBlock);

        WebElement addToCartBtn = productBlock.findElement(
                By.xpath(".//button[@name='submit.addToCart']")
        );

        js.executeScript("arguments[0].click();", addToCartBtn);

        System.out.println("Voltas AC added to cart successfully.");
    }
    @And("I go back to Amazon home page")
    public void iGoBackToThePreviousPage() {
        WebDriver driver = BaseClass.driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Go back one step in browser history
        driver.navigate().back();
        System.out.println("Navigated back to previous page");
    }
    @And("I click the bike carousel right arrow")
    public void clickBikeCarouselRightArrow() {

        WebDriver driver = BaseClass.driver;

        // Unique XPath for RIGHT arrow
        String arrowXpath = "//h2[contains(.,'commutes to weekend')]/following::span[contains(@class,'feed-arrow')][1]";

        // Direct click (JS used only to avoid Amazon blocking)
        WebElement arrow = driver.findElement(By.xpath(arrowXpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", arrow);

        System.out.println("Bike carousel right arrow clicked.");
    }
    @And("I click on the first bike item")
    public void iClickOnTheFirstBikeItem() {

        WebDriver driver = BaseClass.driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String xpath = "//h2[contains(.,'commutes to weekend')]" +
                "/following::li[@class='feed-carousel-card' and @aria-posinset='1'][1]" +
                "//span[@class='a-list-item']";

        // Wait for element
        WebElement item = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

        // Scroll into view
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", item);

        // Click using JS (Amazon blocks normal click sometimes)
        js.executeScript("arguments[0].click();", item);

        System.out.println("✔ First bike item clicked successfully");
    }
    @And("I scroll down and print customer reviews")
    public void scrollDownAndPrintReviews() throws InterruptedException {

        WebDriver driver = BaseClass.driver;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Customer reviews heading ni kanukodaniki xpath (3 variants)
        String headerXpath =
                "//h2[contains(normalize-space(),'Customer reviews')]" +
                        " | //h2[contains(normalize-space(),'Customer Reviews')]" +
                        " | //h2[contains(normalize-space(),'Customer ratings')]";

        WebElement reviewsHeader = null;

        // Page ni slow-ga scroll chestu heading kosam chudu
        for (int i = 0; i < 10; i++) {   // 10 scroll attempts

            // Heading unna? ani check
            java.util.List<WebElement> headers = driver.findElements(By.xpath(headerXpath));

            if (!headers.isEmpty()) {
                reviewsHeader = headers.get(0);
                break;
            }

            // Heading kanipinchakapothe 800px scroll down
            js.executeScript("window.scrollBy(0, 800);");
            Thread.sleep(700);
        }

        if (reviewsHeader == null) {
            System.out.println("❌ Customer reviews section not found even after scrolling.");
            return;
        }

        // Heading ni screen majjilo ki techchi
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", reviewsHeader);
        Thread.sleep(1000);

        System.out.println("----- CUSTOMER REVIEWS -----");

        // 5-star, 4-star percentages table nundi tiskovali
        java.util.List<WebElement> rows = driver.findElements(
                By.xpath("//table[contains(@id,'histogramTable')]//tr")
        );

        for (WebElement row : rows) {
            try {
                String starLabel = row.findElement(By.xpath(".//td[1]")).getText();  // 5 star, 4 star...
                String percent   = row.findElement(By.xpath(".//td[3]")).getText();  // 63%, 20% ...
                System.out.println(starLabel + " = " + percent);
            } catch (Exception ignore) {
                // header / blank rows skip
            }
        }
    }
    @And("I scroll to footer and print footer sections")
    public void scrollToFooterAndPrintFooterSections() throws InterruptedException {
        WebDriver driver = BaseClass.driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1️⃣ Page bottom ki scroll avvalani
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1500); // chinna gap

        // 2️⃣ Footer load ayyindha ani check
        WebElement footer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("navFooter"))
        );

        // 3️⃣ Footer lo unna columns anni teesukuntam
        List<WebElement> columns = footer.findElements(
                By.xpath(".//div[contains(@class,'navFooterLinkCol')]")
        );

        // 4️⃣ Prati column lo heading + links print cheyyadam
        for (WebElement col : columns) {
            String heading = "";
            try {
                heading = col.findElement(By.tagName("h3")).getText();
            } catch (Exception e) {
                // konni designs lo <h3> kakapothe plain text untundi
                heading = col.getText().split("\n")[0];
            }

            System.out.println("===== " + heading + " =====");

            List<WebElement> links = col.findElements(By.tagName("a"));
            for (WebElement link : links) {
                String text = link.getText().trim();
                if (!text.isEmpty()) {
                    System.out.println(" - " + text);
                }
            }
            System.out.println();
        }
    }

}
