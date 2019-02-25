package com.tajawal.pages;

import com.tajawal.base.TestBase;
import com.tajawal.util.TestUtil;
import com.tajawal.helper.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TravellerDetailsPage extends TestBase {
    private final Logger log = LoggerHelper.getLogger(TravellerDetailsPage.class);
    @FindBy(xpath = "//div[@class='www-fare-price fare-summary']//child::span[@class='tj-rate__price']")
    List<WebElement> fareSummary;

    public TravellerDetailsPage() {
        PageFactory.initElements(driver, this);
    }

    /**
     * this method returns the title of traveller page
     * @return
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * this method returns the list of individual passenger fare on traveller page
     * @return
     * @throws IOException
     */
    public List<Float> PassengersFare() throws IOException {
        List<Float> list = new ArrayList<Float>();
        TestUtil.explicitWaitForVisibility(fareSummary.get(0));
        for (WebElement w : fareSummary) {
            String fare = w.getText().replaceAll(",", "");
            Float f = Float.parseFloat(fare);
            list.add(f);
        }
        takeScreenShot("PassengerFareOnTravellerPage");
        return list;
    }

    /**
     * this method enters the traveller details
     * @param passengers
     * @param firstPaxTitle
     * @param firstPaxFirstName
     * @param firstPaxLastName
     * @param secondPaxTitle
     * @param secondPaxFirstName
     * @param secondPaxLastName
     * @throws InterruptedException
     * @throws IOException
     */
    public void enterTravellerDetails(String passengers, String firstPaxTitle, String firstPaxFirstName, String firstPaxLastName, String secondPaxTitle, String secondPaxFirstName, String secondPaxLastName) throws InterruptedException, IOException {
        List<WebElement> chooseTitle;
        for (int i = 0; i < Integer.parseInt(passengers); i++) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement title = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='flights_summary_travelers_form_title_" + i + "_chosen']")));
            TestUtil.clickAction(title);
            chooseTitle = driver.findElements(By.xpath("//div[@id='flights_summary_travelers_form_title_" + i + "_chosen']//child::li[contains(@class,'active-result')]"));
            WebElement firstName = driver.findElement(By.xpath("//input[@id='flights-summary-travelers-form-first-name-" + i + "']"));
            WebElement lastName = driver.findElement(By.xpath("//input[@id='flights-summary-travelers-form-last-name-" + i + "']"));

            if (i == 0) {
                for (WebElement e : chooseTitle) {
                    if (e.getText().equals(firstPaxTitle)) {
                        TestUtil.clickAction(e);
                        break;
                    }
                }
                firstName.sendKeys(firstPaxFirstName);
                lastName.sendKeys(firstPaxLastName);

            }
            if (i == 1) {
                for (WebElement e1 : chooseTitle) {
                    if (e1.getText().equals(secondPaxTitle)) {
                        TestUtil.clickAction(e1);
                        break;
                    }
                }
                firstName.sendKeys(secondPaxFirstName);
                lastName.sendKeys(secondPaxLastName);
                takeScreenShot("TravellerDetails");
            }
        }
        log.info("");
    }

}
