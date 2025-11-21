package com.example.hooks;

import com.example.base.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void setUp() {
        // Setup Chrome driver automatically
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        BaseClass.driver = driver;
        System.out.println("Chrome browser launched successfully.");
    }

    @After
    public void tearDown() {
        if (BaseClass.driver != null) {
            BaseClass.driver.quit();
            System.out.println("Browser closed.");
        }
    }
}
