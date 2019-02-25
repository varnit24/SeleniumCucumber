package com.tajawal.util;

import com.tajawal.base.TestBase;
import com.tajawal.helper.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestUtil extends TestBase {

    private static Logger oLog = LoggerHelper.getLogger(TestUtil.class);


    static JavascriptExecutor js;
    static Actions actions;

    public static void executeClick(WebElement element)
    {
        js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();",element);
    }

    public static void scrollIntoView(WebElement element)
    {
        js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(true);",element);
    }

    public static void moveToElement(WebElement element)
    {
        actions=  new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public static void clickAction(WebElement element)
    {
        actions=  new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    public static void explicitWaitForVisibility(WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(driver,15);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(element));
    }

}
