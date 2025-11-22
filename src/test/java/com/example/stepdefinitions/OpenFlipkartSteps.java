package com.example.stepdefinitions;

import com.example.base.BaseClass;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


public class OpenFlipkartSteps {

    @When("I navigate to Flipkart")
    public void i_navigate_to_flipkart() {
        // Browser already open from "I open Chrome browser" step
        BaseClass.driver.get("https://www.flipkart.com");
    }

//    @When("I click on Login button")
//    public void i_click_on_login_button() {
//        BaseClass.driver.findElement(By.xpath("//a[@title='Login']")).click();
//        // or: BaseClass.driver.findElement(By.xpath("//span[text()='Login']")).click();
//    }
//
    @Then("I should see Flipkart page title")
    public void i_should_see_flipkart_page_title() {
        System.out.println("Flipkart Title = " + BaseClass.driver.getTitle());

    }

    @And("I click on Electronics")
    public void iClickOnElectronics() {
        WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(10));

        WebElement electronics = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Electronics']"))
        );
        electronics.click();
    }



        }




//    @And("I click on Mobiles & Tablets")
//    public void iClickOnMobilesTablets() {
//        WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(10));
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.xpath("//span[normalize-space()='Mobiles & Tablets']")
//                )
//        ).click();
//    }
//


