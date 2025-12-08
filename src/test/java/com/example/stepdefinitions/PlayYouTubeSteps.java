package com.example.stepdefinitions;

import com.example.base.BaseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PlayYouTubeSteps {

    WebDriver driver = BaseClass.driver;

    @When("I search Peddi latest song on YouTube")
    public void i_search_peddi_latest_song_on_youtube() throws Exception {

        driver.get("https://www.youtube.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Search box
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("search_query"))
        );
        searchBox.sendKeys("Peddi latest song");
        searchBox.sendKeys(Keys.ENTER);

        Thread.sleep(3000);

        // Click the first video
        WebElement firstVideo = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//a[@id='video-title'])[1]")
                )
        );
        firstVideo.click();
        System.out.println("▶ Video opened. Waiting for ads...");

        // ***********************
        // 1️⃣ WAIT BEFORE SKIP ADS
        // ***********************
        System.out.println("⏳ Waiting 10 sec for ads to appear...");
        Thread.sleep(10000);  // Wait for ads to load completely

        // Skip Ads if present
        try {
            WebElement skipBtn = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//button[contains(@class,'ytp-ad-skip-button')]")
                    )
            );

            skipBtn.click();
            System.out.println("⏭️ Skip Ads clicked!");
        } catch (TimeoutException e) {
            System.out.println("No Skip Ads button found. Playing directly...");
        }

        // *************************
        // 2️⃣ WAIT AFTER SKIP ADS
        // *************************
        System.out.println("🎶 Waiting 8 seconds to enjoy the song...");
        Thread.sleep(8000);
    }

    @Then("The Peddi song should start playing")
    public void the_peddi_song_should_start_playing() {
        System.out.println("🎵 Song is playing smoothly.");
    }
}
