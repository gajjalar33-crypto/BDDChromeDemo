package com.example.hooks;

import com.example.base.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.chrome.ChromeDriver;

public class Hooks {

    @Before
    public void setUp() {
        if (BaseClass.driver == null) {
            BaseClass.driver = new ChromeDriver();
            BaseClass.driver.manage().window().maximize();
            System.out.println("Chrome browser launched successfully (Hooks).");
        }
    }

    @After
    public void tearDown() {
        if (BaseClass.driver != null) {
            BaseClass.driver.quit();
            System.out.println("Browser closed.");
            BaseClass.driver = null;
        }
    }
}
