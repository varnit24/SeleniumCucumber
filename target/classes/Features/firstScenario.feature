Feature: Verify the basic flow of application

Scenario Outline: First scenario

Given User navigates to "<url>"
When title of home page is verified
Then user choose the origin from random array "<origin1>","<origin2>","<origin3>","<origin4>","<origin5>"
And user choose the destination from random array "<dest1>","<dest2>","<dest3>","<dest4>","<dest5>"
And user enters random date
And user add two Adults
And user enter "<cabin>" class
Then user search for flights
And Assert the title of listing page
Then Assert that search query data that user provided is aligned with search summary module
Then Filter results to "<carrier>" carrier if found otherwise select another carrier
Then Assert listing results are only the selected carrier
Then Pick price details of Random flight
And Select that particular flight and navigate to traveller details page
Then Assert that cart price is same as listing price
Then Fill Traveller details of "<title1>" "<firstname1>" "<lastname1>" and "<title2>" "<firstname2>" "<lastname2>"
And close then browser

Examples:
|       url              |   origin1     |   origin2     |   origin3 |   origin4     |   origin5 |   dest1   |   dest2   |   dest3   |   dest4   |   dest5   |   cabin   |   carrier |   title1  |   firstname1  |   lastname1   |   title2  |   firstname2  |   lastname2   |
|https://www.tajawal.ae/ |     DXB       |     AUH       |     LON   |      YYZ      |     SIN   |    DEL    |   BLR     |     MAA   |   HYD     |   IXC     |  Economy  | Emirates  |     Mr    |    Varnit     |   Garg        |  Mrs      |  Beautiful    |   Anonymous   |