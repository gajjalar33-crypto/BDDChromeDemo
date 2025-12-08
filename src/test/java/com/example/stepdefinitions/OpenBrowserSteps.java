package com.example.stepdefinitions;

import com.example.base.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class OpenBrowserSteps {

//    @Given("I open Chrome browser")
//    public void i_open_chrome_browser() {
//        // Browser is already opened in Hooks @Before
//        Assert.assertNotNull("Driver is not initialized", BaseClass.driver);
//        System.out.println("Chrome browser is open.");
//    }

    @When("I navigate to Google")
    public void i_navigate_to_google() {
        BaseClass.driver.get("https://www.google.com");
        System.out.println("Navigated to Google.");
    }

    @Then("I should see the Google page title")
    public void i_should_see_the_google_page_title() {
        String title = BaseClass.driver.getTitle();
        System.out.println("Page title: " + title);
        Assert.assertTrue("Title does not contain 'Google'", title.contains("Google"));
    }
}
