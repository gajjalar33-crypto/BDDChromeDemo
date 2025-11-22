package com.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseClass {

    // static driver – hooks & steps rendu use cheyyadaniki
    public static WebDriver driver;

    // common method – driver create / maximize / return
    public static WebDriver initializeDriver() {
        if (driver == null) {                  // already open ayite malli open cheyyadu
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            System.out.println("Chrome browser launched successfully.");
        }
        return driver;
    }
}
