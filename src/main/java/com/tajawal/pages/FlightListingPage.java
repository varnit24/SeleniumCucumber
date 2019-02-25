package com.tajawal.pages;

import com.tajawal.base.TestBase;
import com.tajawal.helper.LoggerHelper;
import com.tajawal.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FlightListingPage extends TestBase {

    private final Logger log = LoggerHelper.getLogger(FlightListingPage.class);

    @FindBy(xpath = "//div[@class='reactooltip-w tooltip__origin']/div")
    List<WebElement> originList;

    @FindBy(xpath = "//div[@class='reactooltip-w tooltip__destination']/div")
    List<WebElement> destinationList;

    @FindBy(xpath = "//div[@class='desktop-summary__label desktop-summary__label--highlighted']")
    List<WebElement> locationSummarySearchModule;

    @FindBy(xpath = "//div[contains(@class,'desktop-summary__block-group pull-leading')]//child::div[@class='desktop-summary__value desktop-summary__value--highlighted']")
    List<WebElement> searchSummaryModuleData;

    @FindBy(xpath = "//div[@class='search-result-item-group-wrapper-main']//div[@class='search-result-item-container search-result-item-container--show']")
    List<WebElement> flightsOnListingPage;

    @FindBy(xpath = "//div[@class='filter-options filter-flights-airlines']/div[@class='form-group']/child::div[@class='checkbox active-hover-comp']/label/span[@class='font-base']")
    List<WebElement> airlinesList;

    @FindBy(xpath = "//div[@class='search-result-item-group-wrapper-main']//child::div[@class='card-airline__details']/div[1]")
    List<WebElement> flightNamesOnListingPage;

    @FindBy(xpath = "//div[@class='search-result-item-group-wrapper-main']//child::div[@class='search-result-card__price']")
    List<WebElement> priceList;

    @FindBy(linkText = "only")
    WebElement selectAirline;

    @FindBy(xpath = "//div[@class='search-result-item-group-wrapper-main']//child::div[@class='search-result-card__action']/button[@class='btn btn-cta']")
    List<WebElement> selectFlightButton;

    @FindBy(xpath = "//div[@class='domestic-sort-by domestic-sort-by--selected domestic-sort-by--light']//span//span[contains(text(),'Price')]/following-sibling::img")
    WebElement priceSort;


    public FlightListingPage()  {

        PageFactory.initElements(driver, this);
    }

    /**
     * this method returns the title of listing page
     * @return title
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * this method returns the origin value on listing page search module
     * @return origin value
     * @throws IOException
     */
    public String originOnListingPage() throws IOException {
        if (locationSummarySearchModule.size() < 1) {
            takeScreenShot("NoOriginOnListingPage");
            return "";
        }
        return locationSummarySearchModule.get(0).getText();
    }

    /**
     * this method returns the destination value on listing page search module
     * @return destination value
     * @throws IOException
     */
    public String destinationOnListingPage() throws IOException {
        if (locationSummarySearchModule.size() < 2) {
            takeScreenShot("NoDestinationOnListingPage");
            return "";
        }
        return locationSummarySearchModule.get(1).getText();
    }

    /**
     * this method returns the departure date on listing page search module
     * @return departure date
     * @throws IOException
     */
    public String departureDate() throws IOException {
        if (searchSummaryModuleData.size() > 0) {
            takeScreenShot("DeptDateOnListingPage");
            return searchSummaryModuleData.get(0).getText();

        } else {
            takeScreenShot("NoDepartureDateFoundOnListing");
            return "";
        }
    }

    /**
     * this method returns the passengers displayed on listing page search module
     * @return no of passengers
     */
    public String getPassengers() {
        if (searchSummaryModuleData.size() > 1) {
            return searchSummaryModuleData.get(1).getText();
        } else
            return "";
    }

    /**
     * this method returns the cabin class type being displayed on listing page search module
     * @return cabin class type
     * @throws IOException
     */
    public String getCabin() throws IOException {
        if (searchSummaryModuleData.size() > 2) {
            takeScreenShot("cabinSearchModule");
            return searchSummaryModuleData.get(2).getText();
        } else
            return "";
    }

    /**
     * this method returns the number of flights being displayed on listing page
     * @return
     */
    public int numberOfFlightsOnListingPage() {
        log.info("number of flights on listing page: " + flightsOnListingPage.size());
        return flightsOnListingPage.size();

    }

    /**
     * this method returns whether any flight is being displayed on listing page
     * @return
     * @throws IOException
     */
    public boolean isFlightDisplayedonListingPage() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOfAllElements(flightsOnListingPage));
        takeScreenShot("isFlightDisplayedonListingPage");
        return flightsOnListingPage.size() > 0;
    }

    /**
     * this method filters the airline based on given param (if found), otherwise it filter the airlines
     * based on first airline that is being displayed on the listing page
     * @param airline
     * @return filtered airline name
     * @throws Exception
     */
    public String filterResultsBasedOnAirline(String airline) throws Exception {
        boolean flag = false;
        String selectedAirline = "";
        for (WebElement e : airlinesList) {
            if (e.getText().contains(airline)) {
                flag = true;
                selectedAirline = airline;
                Actions actions = new Actions(driver);
                actions.moveToElement(e).build().perform();
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.elementToBeClickable(selectAirline));
                takeScreenShot("filterResultsBasedOnAirline");
                TestUtil.executeClick(selectAirline);
                break;
            }
        }
        if (!flag) {
            selectedAirline = getFirstAirlineName();
           // Reporter.log(airline + " is not present under search filter, hence selecting: " + getFirstAirlineName());
            System.out.println(airline + " is not present under search filter, hence selecting: " + getFirstAirlineName());
            filterResultsBasedOnAirline(getFirstAirlineName());
        }
        log.info(airline);
        return selectedAirline;
    }

    /**
     * this method returns the first airline name being displayed on listing page
     * @return
     * @throws Exception
     */
    public String getFirstAirlineName() throws Exception {
        if (flightNamesOnListingPage.size() > 0) {
            TestUtil.moveToElement(flightNamesOnListingPage.get(0));
            takeScreenShot("getFirstAirlineName");
            return flightNamesOnListingPage.get(0).getText();
        }
        throw new Exception("Flight names are not displayed against listing page.");
    }

    /**
     * this method validates whether only the filtered airles are being displayed on the UI
     * @param airlineName
     * @return
     * @throws IOException
     */
    public boolean isOnlySelectedCarriersDisplayed(String airlineName) throws IOException {
        boolean flag = false;
        if (flightNamesOnListingPage.size() > 0) {
            for (WebElement a : flightNamesOnListingPage) {
                TestUtil.moveToElement(a);
                if (a.getText().equals(airlineName)) {
                    flag = true;
                }
            }
            takeScreenShot("isOnlySelectedCarriersDisplayed");
        }
        log.info(airlineName);
        return flag;
    }

    /**
     * this method fetches the price of random flight that is being selected
     * @param random
     * @return
     * @throws IOException
     */
    public String getPriceOfRandomlySelectedFlight(int random) throws IOException {
        String flightPrice = priceList.get(random).getText();
        TestUtil.scrollIntoView(priceList.get(random));
        takeScreenShot("getPriceOfRandomlySelectedFlight");
        log.info(flightPrice);
        return flightPrice;
    }

    /**
     * this method select the random flight and navigates to traveller UI
     * @param random
     * @return
     * @throws IOException
     */
    public TravellerDetailsPage selectRandomFlight(int random) throws IOException {
        TestUtil.scrollIntoView(selectFlightButton.get(random));
        takeScreenShot("selectRandomFlight");
        TestUtil.executeClick(selectFlightButton.get(random));
        return new TravellerDetailsPage();
    }

    /**
     * this method sort the flights based on price
     * @throws IOException
     */
    public void sortByPrice() throws IOException {
        TestUtil.explicitWaitForVisibility(priceSort);
        String className = priceSort.getAttribute("className");
        if (className.contains("reverse")) {
            priceSort.click();
            takeScreenShot("sortByPrice");
        }
    }

    /**
     * this method fetch the price of first flight
     * @return
     * @throws Exception
     */
    public int fetchFirstPrice() throws Exception {
        if (getIntPriceList().size() > 0) {
            takeScreenShot("fetchFirstPrice");
            return getIntPriceList().get(0);
        } else {
            takeScreenShot("No_price_list_found");
            throw new Exception("Price list is empty");
        }

    }

    /**
     * this method validates whether all the prices are sorted on the listing page
     * @return
     */
    public boolean isPriceListSorted() {
        List<Integer> intPriceList = getIntPriceList();
        int prev = intPriceList.get(0);
        for (int j = 1; j < intPriceList.size(); j++) {
            if (intPriceList.get(j) < prev) {
                return false;
            }
            prev = intPriceList.get(j);
        }
        return true;
    }

    /**
     * this method returns the list of price in INTs
     * @return
     */
    public List<Integer> getIntPriceList() {
        List<Integer> intPriceList = new ArrayList<Integer>();
        for (int i = 0; i < priceList.size(); i++) {
            intPriceList.add(Integer.parseInt(priceList.get(i).getText().replaceAll(",", "")));
        }
        log.info("");
        return intPriceList;
    }

    /**
     * this method save the price list to CSV file
     * @throws IOException
     */
    public void saveListingPriceToCSV() throws IOException {
        File destDir = new File(System.getProperty("user.dir") + "/target/ListingPriceCSV/");
        if(prop.getProperty("deletePreviousCSV").equalsIgnoreCase("yes") && destDir.exists())
        {
            FileUtils.cleanDirectory(destDir);
        }
        if (!destDir.exists())
            FileUtils.forceMkdir(destDir);
        File file = new File(destDir.getAbsolutePath() + System.getProperty("file.separator") + System.currentTimeMillis() + ".csv");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        List<String> stringPriceList = new ArrayList<String>();
        for (int i = 0; i < priceList.size(); i++) {
            stringPriceList.add(priceList.get(i).getText().replaceAll(",", ""));
        }
        String collect = stringPriceList.stream().collect(Collectors.joining(","));
        System.out.println("CSV: " + collect);
        bufferedWriter.write(collect);
        bufferedWriter.close();
        log.info("");
    }

    /**
     * this method generates the random number which is less that the given size
     * @param size
     * @return
     */
    public int selectRandom(int size) {
        return new Random().nextInt(size);

    }


}
