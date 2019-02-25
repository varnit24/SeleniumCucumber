# SeleniumCucumber

## Prerequisites
 - JDK 1.8 or later
 - Maven
 
## Description
 
 This is Behavior Data Driven Framework which is created using Selenium and Cucumber.
 Programming language: Java
 TestNG is used as testing framework.
 This framework follows the Page Object Model approach. 
 Cucumber Extent report is used for the generation of Test reports.
 
 - Report would be generated under target/extent-cucumber-reports folder.
 - Screenshots would be generated under target/Screenshots folder.
 - CSV file would be generated under target/ListingPriceCSV folder.
 
 Config.properties file can be used to pass the below parameters:
  - Operating system either Windows or Mac (os)
  - Browser either chrome or firefox (browserName)
  - Define deleteScreenshots/deletePreviousCSV as 'Yes' if you want to delete the previous screenshots/csv before any run.
  
Note: Browser drivers are placed for mac inside resources/macDriver folder and for Windows(32 bit) under resources/windowsDriver folder. If you use win 64 bit, then replace the windows browser driver


 Screenshots are added on all the page and screenshot will be triggered in case of any test failure as well.
 
 
  - To run this project, just clone this repo into your local machine and run the TestNG.xml file.