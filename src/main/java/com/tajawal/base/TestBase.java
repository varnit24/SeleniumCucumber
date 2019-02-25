package com.tajawal.base;

import com.tajawal.helper.LoggerHelper;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private static final Logger log = LoggerHelper.getLogger(TestBase.class);
    public static WebDriver driver;
    public static Properties prop;
    public static Map<String, Object> data = new LinkedHashMap<String, Object>();
    public static String screenShotLocation = System.getProperty("user.dir") + "/target/Screenshots/";
    static File destDir = new File(screenShotLocation);

    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/Config.properties");
            prop.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method set the browser property, initialize the driver, put the implicit wait and get the url from config file
     */
    public static void initialize() throws Exception {
        configureDriverPath();

        if (prop.getProperty("browserType").equalsIgnoreCase("firefox"))
        {
            FirefoxProfile geoDisabled = new FirefoxProfile();
            geoDisabled.setPreference("app.update.enabled", false);
            geoDisabled.setPreference("geo.enabled", false);
            geoDisabled.setPreference("geo.provider.use_corelocation", false);
            geoDisabled.setPreference("geo.prompt.testing", false);
            geoDisabled.setPreference("geo.prompt.testing.allow", false);
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(FirefoxDriver.PROFILE, geoDisabled);
            driver = new FirefoxDriver();

        }
        else if (prop.getProperty("browserType").equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("test-type");
            options.addArguments("enable-strict-powerful-feature-restrictions");
            options.addArguments("disable-geolocation");
            DesiredCapabilities cap = DesiredCapabilities.chrome();
            cap.setCapability(ChromeOptions.CAPABILITY, options);
            cap.merge(DesiredCapabilities.chrome());
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    public static void printHashMap() {
        for (Map.Entry<String, Object> map : data.entrySet()) {
            log.info("key is:" + map.getKey() + " and value is: " + map.getValue());
        }
    }

    public static void quitBrowser()
    {
        driver.quit();
    }

    public static void closeBrowser()
    {
        driver.close();
    }

    public static void deletePreviousScreenshots() throws IOException {

        if(prop.getProperty("deleteScreenshots").equalsIgnoreCase("yes")) {

                if(destDir.exists()){
                FileUtils.cleanDirectory(destDir);}

        }
    }

    public String takeScreenShot(String name) throws IOException {

         destDir = new File(System.getProperty("user.dir") + "/target/Screenshots/");

        if(!destDir.exists())
            FileUtils.forceMkdir(destDir);

        File destPath = new File(destDir.getAbsolutePath()
                + System.getProperty("file.separator") + System.currentTimeMillis() + "_" + name + ".jpg");
        try {
            FileUtils
                    .copyFile(((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE), destPath);
        } catch (IOException e) {
            throw e;
        }
        return destPath.getAbsolutePath();
    }


    public static void configureDriverPath() throws IOException {

        if(prop.getProperty("os").equalsIgnoreCase("Mac")) {
            String firefoxDriverPath = System.getProperty("user.dir")+"/src/main/resources/macDriver/geckodriver";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir")+"/src/main/resources/macDriver/chromedriver";
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
        else if(prop.getProperty("os").equalsIgnoreCase("Windows")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "//src//main//resources//windowsDriver//geckodriver.exe";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir") + "//src//main//resources//windowsDriver//chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
    }

}
