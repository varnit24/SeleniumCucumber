Feature: Validate the sorting order of price

Scenario Outline: Second Scenario

Given user navigates to "<url>"
When title of home page is displayed
Then user chooses the origin from random array "<origin1>","<origin2>","<origin3>","<origin4>","<origin5>"
And user chooses the destination from random array "<dest1>","<dest2>","<dest3>","<dest4>","<dest5>"
Then user enters random date, passengers and "<cabin>" class
Then user search the flights
And Assert title of listing page
Then sort the price in ascending order
Then fetch the first flight price
Then Assert that first price INTs are the lowest comparing to other prices
Then Fetch all listing prices
And save listing prices in a .CSV file
Then close the browser

Examples:

|       url              |   origin1     |   origin2     |   origin3 |   origin4     |   origin5 |   dest1   |   dest2   |   dest3   |   dest4   |   dest5   |   cabin   |
|https://www.tajawal.com/ |     DXB       |     AUH       |     LON   |      YYZ      |     SIN   |    DEL    |   BLR     |     MAA   |   HYD     |   IXC     |  Economy  |