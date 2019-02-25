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

public class FetchCartPriceFlow extends TestBase {

    HomePage homePage;
    FlightListingPage flightListingPage;
    TravellerDetailsPage travellerDetailsPage;

    public FetchCartPriceFlow() throws Exception {
        initialize();
    }

    @Given("^User navigates to \"([^\"]*)\"$")
    public void user_navigates_to(String url) throws Exception {
        driver.get(url);
        homePage = new HomePage();
    }

    @When("^title of home page is verified$")
    public void title_of_home_page_is_verified() {

        Assert.assertEquals("Book Cheap Flights, Airline Tickets and Hotels Online at tajawal", homePage.getTitle(), "home page title not matched");
    }

    @Then("^user choose the origin from random array \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
    public void user_choose_the_origin_from_random_array(String origin1, String origin2, String origin3, String origin4, String origin5) {

        homePage.selectOneWayFlight();
        String origin = homePage.enterRandomOrigin(origin1, origin2, origin3, origin4, origin5);
        data.put("origin", origin);
    }

    @Then("^user choose the destination from random array \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
    public void user_choose_the_destination_from_random_array(String dest1, String dest2, String dest3, String dest4, String dest5) {
        String destination = homePage.enterRandomDestination(dest1, dest2, dest3, dest4, dest5);
        data.put("destination", destination);
    }

    @Then("^user enters random date$")
    public void user_enters_random_date() {

        homePage.enterDepartureDate();
        String randomDateEntered = homePage.returnDate();
        data.put("randomDateEntered", randomDateEntered);
    }

    @Then("^user add two Adults$")
    public void user_add_two_Adults() {

        homePage.addAnAdult();
        String passengersEntered = homePage.paxCount();
        data.put("passengersEntered", passengersEntered);
    }

    @Then("^user enter \"([^\"]*)\" class$")
    public void user_enter_class(String cabin) {

        homePage.selectCabinClass(cabin);
        data.put("enteredCabinClass", cabin);
    }

    @Then("^user search for flights$")
    public void user_search_for_flights() throws IOException {
        flightListingPage = homePage.searchForFlights();
    }

    @Then("^Assert the title of listing page$")
    public void assert_the_title_of_listing_page() {
        Assert.assertEquals("tajawal | Online Booking for Cheap Flights & Airline Tickets", flightListingPage.getTitle(), "Listing page title not matched");
    }

    @Then("^Assert that search query data that user provided is aligned with search summary module$")
    public void assert_that_search_query_data_that_user_provided_is_aligned_with_search_summary_module() throws InterruptedException, IOException {

        if (!flightListingPage.isFlightDisplayedonListingPage()) {
            Assert.fail("No flight are present for given search criteria");
        }

        String originOnListingPage = flightListingPage.originOnListingPage();
        Assert.assertEquals(originOnListingPage, data.get("origin"), "Origin not matched");

        String destinationOnListingPage = flightListingPage.destinationOnListingPage();
        Assert.assertEquals(destinationOnListingPage, data.get("destination"), "Destination not matched");

        String departureDateOnListingPage = flightListingPage.departureDate();
        Assert.assertEquals(departureDateOnListingPage, data.get("randomDateEntered"), "Date not matched");

        String passengerListingPage = flightListingPage.getPassengers();
        Assert.assertEquals(passengerListingPage, data.get("passengersEntered"), "no of passengers not matched");

        String cabinListingPage = flightListingPage.getCabin();
        Assert.assertEquals(cabinListingPage, data.get("enteredCabinClass"), "cabin class not matched");
    }

    @Then("^Filter results to \"([^\"]*)\" carrier if found otherwise select another carrier$")
    public void filter_results_to_carrier_if_found_otherwise_select_another_carrier(String airline) throws Exception {

        String selectedAirline = flightListingPage.filterResultsBasedOnAirline(airline);
        data.put("selectedAirline", selectedAirline);
    }

    @Then("^Assert listing results are only the selected carrier$")
    public void assert_listing_results_are_only_the_selected_carrier() throws InterruptedException, IOException {

        boolean onlySelectedCarriersDisplayed = flightListingPage.isOnlySelectedCarriersDisplayed((String) data.get("selectedAirline"));
        Assert.assertTrue(onlySelectedCarriersDisplayed,"listing results are not matched with selected carrer");

    }

    @Then("^Pick price details of Random flight$")
    public void pick_price_details_of_Random_flight() throws IOException {

        int randomFlight = flightListingPage.selectRandom(flightListingPage.numberOfFlightsOnListingPage());
        data.put("randomFlight", randomFlight);
        String selectedFlightPrice = flightListingPage.getPriceOfRandomlySelectedFlight((Integer) data.get("randomFlight"));
        float randomFlightListingPrice = Float.parseFloat(selectedFlightPrice.replaceAll(",", ""));
        data.put("randomFlightListingPrice", randomFlightListingPrice);

    }

    @Then("^Select that particular flight and navigate to traveller details page$")
    public void select_that_particular_flight_and_navigate_to_traveller_details_page() throws IOException {

        travellerDetailsPage = flightListingPage.selectRandomFlight((Integer) data.get("randomFlight"));
    }

    @Then("^Assert that cart price is same as listing price$")
    public void assert_that_cart_price_is_same_as_listing_price() throws IOException {

        float perPersonExpectedFare = (float) data.get("randomFlightListingPrice") / Float.parseFloat((String) data.get("passengersEntered"));
        if(travellerDetailsPage.PassengersFare().size()<1)
        {
            takeScreenShot("FAILED_Price_not_displayed_on_traveller_page");
            Assert.fail("Price Not displayed on Traveller page");
        }
        for (int i = 0; i < Integer.parseInt((String) data.get("passengersEntered")); i++) {
            data.put("passenger_" + i + "_fare", travellerDetailsPage.PassengersFare().get(i));
            Assert.assertEquals(travellerDetailsPage.PassengersFare().get(i),perPersonExpectedFare,"Individual passenger fare not matched");
        }
    }

    @Then("^Fill Traveller details of \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void fill_Traveller_details_of_and(String title1, String firstname1, String lastname1, String title2, String firstname2, String lastname2) throws InterruptedException, IOException {

        travellerDetailsPage.enterTravellerDetails((String) data.get("passengersEntered"), title1, firstname1, lastname1, title2, firstname2, lastname2);
    }

    @Then("^close then browser$")
    public void close_then_browser() {
        driver.close();
    }

}
