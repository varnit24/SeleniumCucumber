package com.tajawal.listeners;

import com.cucumber.listener.Reporter;
import com.tajawal.base.TestBase;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;


public class CustomListener extends TestBase implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotName="FAILED_"+result.getName();
        try {
            String sc = takeScreenShot(screenshotName);
            Reporter.addScreenCaptureFromPath(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestBase.closeBrowser();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
