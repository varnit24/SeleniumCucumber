package com.tajawal.test.runner;


import com.cucumber.listener.Reporter;
import com.tajawal.base.TestBase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

@CucumberOptions(
        features = "src/main/resources/Features",
        glue = {"com/tajawal/test/stepDefinitions"},
        format = {"com.cucumber.listener.ExtentCucumberFormatter:target/extent-cucumber-reports/report.html"},
        monochrome = true,
        dryRun = false
)

public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    public void deletePreviousScreenShts() throws IOException {
        TestBase.deletePreviousScreenshots();
    }

    @AfterSuite
    public void createReport() throws IOException {

        Reporter.getExtentHtmlReport();
    }

}
