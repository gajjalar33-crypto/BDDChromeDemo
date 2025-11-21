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

                // HTML Report
                "html:target/cucumber-html-report",

                // Another simple HTML report
                "html:target/cucumber-report.html",

                // JSON Report
                "json:target/cucumber.json",

                // JUnit XML Report
                "junit:target/cucumber.xml"
        },
        monochrome = true
)
public class TestRunner {
    // No code needed here
}
