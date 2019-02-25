package com.tajawal.pages;

import com.tajawal.base.TestBase;
import com.tajawal.util.TestUtil;
import com.tajawal.helper.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.IOException;
import java.util.List;
import java.util.Random;

public class HomePage extends TestBase {
    private final Logger log = LoggerHelper.getLogger(HomePage.class);
    @FindBy(xpath = "//li[@name='rbFlightTabContent']")
    WebElement flightsTab;

    @FindBy(xpath = "//input[@id='flights-search-type-one-way-inp']")
    WebElement oneWay;

    @FindBy(xpath = "//input[@type='text' and @id='flights-search-origin-1']")
    WebElement origin;

    @FindBy(xpath = "//input[@type='text' and @id='flights-search-destination-1']")
    WebElement destination;

    @FindBy(xpath = "//input[@id='flights-search-departureDate-1']")
    WebElement departureDateBox;

    @FindBy(xpath = "//div[@id='flights-search-open-pax-btn']//i[contains(@class,'www-srchf__opt__icn tj-icon tj-icon--expand-arrow')]")
    WebElement passengerDropDown;

    @FindBy(xpath = "//div[contains(@class,'www-srchf__opt__option__dropcount www-srchf__opt__option__dropcount--pax')]//div[@class='www-srchf__pax-counter']/div[contains(text(),'Adults')]//following-sibling::div//a[@data-action='add']")
    WebElement addAdult;

    @FindBy(xpath = "//span[contains(@class,'js-fs-pax-count pax-count')]")
    WebElement paxCount;

    @FindBy(xpath = "//div[contains(@class,'www-srchf__pax-counter__counter')]//span[contains(@class,'js-fs-adult-count')][contains(text(),'2')]")
    WebElement adultCount;

    @FindBy(xpath = "//span[contains(@class,'js-fs-pax-title')]")
    WebElement paxTitle;

    @FindBy(xpath = "//div[contains(@class,'www-srchf__opt__drop__cabin')]//i[contains(@class,'www-srchf__opt__icn tj-icon tj-icon--expand-arrow')]")
    WebElement economyDropDown;

    @FindBy(xpath = "//a[@id='flights-search-cta']")
    WebElement searchFlights;

    @FindBy(xpath = "//ul[contains(@class,'www-srchf__opt__option__dropcount cabinMenu')]//li/label")
    List<WebElement> cabinType;

    @FindBy(xpath = "//div[@class='www-srchf__dat www-srchf__dat--departure js-fs-departure-date']//span[@class='www-srchf__dat__mnth js-fs-date-month' and @data-key='departureDate']")
    WebElement departureMonth;

    @FindBy(xpath = "//div[@class='www-srchf__dat www-srchf__dat--departure js-fs-departure-date']//span[@class='www-srchf__dat__val js-fs-date-day-number' and @data-key='departureDate']")
    WebElement departureDate;

    @FindBy(xpath = "//div[@class='www-srchf__dat www-srchf__dat--departure js-fs-departure-date']//span[@class='www-srchf__dat__day js-fs-date-day' and @data-key='departureDate']")
    WebElement departureDay;


    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    /**
     * this method selects the one way flight tab option
     */
    public void selectOneWayFlight() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(flightsTab));
        flightsTab.click();
        TestUtil.executeClick(oneWay);
    }

    /**
     * this method enters the random value of origin
     * @param a1
     * @param a2
     * @param a3
     * @param a4
     * @param a5
     * @return
     */
    public String enterRandomOrigin(String a1, String a2, String a3, String a4, String a5) {
        String[] originArr = {a1, a2, a3, a4, a5};
        origin.clear();
        String originPlace = originArr[new Random().nextInt(originArr.length)];
        origin.sendKeys(originPlace);
        return originPlace;

    }

    /**
     * this method enters the random value of destination
     * @param a1
     * @param a2
     * @param a3
     * @param a4
     * @param a5
     * @return
     */
    public String enterRandomDestination(String a1, String a2, String a3, String a4, String a5) {
        String[] destArr = {a1, a2, a3, a4, a5};
        destination.clear();
        String destPlace = destArr[new Random().nextInt(destArr.length)];
        destination.sendKeys(destPlace);
        return destPlace;
    }

    /**
     * this method enters the random departure date
     */
    public void enterDepartureDate() {
        for (int i = 0; i < 5; i++) {
            departureDateBox.click();
            departureDateBox.sendKeys(Keys.ARROW_RIGHT);
        }
        departureDateBox.submit();
        log.info("");
    }

    /**
     * this method returns the title of home page
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * this method add an adult under passengers
     */
    public void addAnAdult() {
        TestUtil.executeClick(passengerDropDown);
        TestUtil.executeClick(addAdult);
    }

    /**
     * this method returns the total passengers count on home page search module
     * @return
     */
    public String paxCount() {
        return paxCount.getText();
    }

    /**
     * this method selects the cabin class on home page search module
     * @param enterFlightClass
     */
    public void selectCabinClass(String enterFlightClass) {
        TestUtil.executeClick(economyDropDown);
        for (WebElement w : cabinType) {
            if (w.getText().contains(enterFlightClass)) {
                TestUtil.executeClick(w);
                break;
            }
        }
        log.info(enterFlightClass);
    }

    /**
     * this method searches the flights based on given params and navigates to listing page
     * @return
     * @throws IOException
     */
    public FlightListingPage searchForFlights() throws IOException {
        takeScreenShot("HomePageSearchDetails");
        TestUtil.executeClick(searchFlights);
        return new FlightListingPage();
    }

    /**
     * this method returns the entered date in specific format to validate the input date on listing page search module
     * @return
     */
    public String returnDate() {
        String month = departureMonth.getText();
        String monthValue = GetMonth.valueOf(month.toUpperCase()).getMonth();
        String date = departureDate.getText();
        String finalDate = departureDay.getText().substring(0, 3) + "," + " " + date + "/" + monthValue;
        log.info("");
        return finalDate;
    }

}

enum GetMonth {
    JANUARY("01"), FEBRUARY("02"), MARCH("03"), APRIL("04"), MAY("05"), JUNE("06"), JULY("07"), AUGUST("08"), SEPTEMBER("09"), OCTOBER("10"), NOVEMBER("11"), DECEMBER("12");
    private String month;

    GetMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

}
