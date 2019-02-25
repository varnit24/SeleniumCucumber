package com.tajawal.test.stepDefinitions;

import com.tajawal.base.TestBase;
import com.tajawal.pages.FlightListingPage;
import com.tajawal.pages.HomePage;
import com.tajawal.pages.TravellerDetailsPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

import java.io.IOException;

public class PriceSortCsvFlow extends TestBase {

    HomePage homePage;
    FlightListingPage flightListingPage;
    TravellerDetailsPage travellerDetailsPage;

    public PriceSortCsvFlow() throws Exception {
        initialize();
    }

    @Given("^user navigates to \"([^\"]*)\"$")
    public void user_navigates_to(String url) {
        driver.get(url);
        homePage = new HomePage();
    }

    @When("^title of home page is displayed$")
    public void title_of_home_page_is_displayed() {
        Assert.assertEquals("Book Cheap Flights, Airline Tickets and Hotels Online at tajawal", homePage.getTitle(), "home page title not matched");
    }


    @Then("^user chooses the origin from random array \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
    public void user_choose_the_origin_from_random_array(String origin1, String origin2, String origin3, String origin4, String origin5) {
        homePage.selectOneWayFlight();
        String origin = homePage.enterRandomOrigin(origin1, origin2, origin3, origin4, origin5);
        data.put("origin", origin);
    }

    @Then("^user chooses the destination from random array \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
    public void user_choose_the_destination_from_random_array(String dest1, String dest2, String dest3, String dest4, String dest5) {
        String destination = homePage.enterRandomDestination(dest1, dest2, dest3, dest4, dest5);
        data.put("destination", destination);
    }

    @Then("^user enters random date, passengers and \"([^\"]*)\" class$")
    public void user_enters_random_date(String cabin) {
        homePage.enterDepartureDate();
        String randomDateEntered = homePage.returnDate();
        data.put("randomDateEntered", randomDateEntered);
        homePage.addAnAdult();
        String passengersEntered = homePage.paxCount();
        data.put("passengersEntered", passengersEntered);
        homePage.selectCabinClass(cabin);
        data.put("enteredCabinClass", cabin);
    }

    @Then("^user search the flights$")
    public void user_search_for_flights() throws IOException {
        flightListingPage = homePage.searchForFlights();
    }

    @Then("^Assert title of listing page$")
    public void assert_the_title_of_listing_page() {
        Assert.assertEquals("tajawal | Online Booking for Cheap Flights & Airline Tickets", flightListingPage.getTitle(), "Listing page not matched");
    }

    @Then("^sort the price in ascending order$")
    public void sort_the_price_in_ascending_order() throws IOException {
        flightListingPage.sortByPrice();
    }

    @Then("^fetch the first flight price$")
    public void fetch_the_first_flight_price() throws Exception {
        flightListingPage.fetchFirstPrice();
    }

    @Then("^Assert that first price INTs are the lowest comparing to other prices$")
    public void assert_that_first_price_INTs_are_the_lowest_comparing_to_other_prices() {
        Assert.assertTrue(flightListingPage.isPriceListSorted(),"price are not in sorted order");
    }

    @Then("^Fetch all listing prices$")
    public void fetch_all_listing_prices() {
        flightListingPage.getIntPriceList();
    }

    @Then("^save listing prices in a \\.CSV file$")
    public void save_listing_prices_in_a_CSV_file() throws IOException {
        flightListingPage.saveListingPriceToCSV();
    }

    @Then("^close the browser$")
    public void close_the_browser() {
        driver.close();
    }
}
