package com.example.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.example.stepdefinitions",
                "com.example.hooks"
        },
        plugin = {
                "pretty",

                // HTML report
                "html:target/cucumber-html-report",

                // JSON report
                "json:target/cucumber.json",

                // JUnit XML report
                "junit:target/cucumber.xml",

                // Allure results (for allure-cucumber7-jvm)
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class TestRunner {
    // No code needed here
}
