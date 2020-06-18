Feature: Weather data comparision 

@Test
Scenario Outline: Compare temprature, humidity and wind speed data from NDTV and open weather 
	Given user go the NDTV Url
	When user enter the city name as "<city>"
	Then user copy the weather details from NDTV site of "<city>"
	And user fetch the temp of "<city>" from open weather API
	Then user compare the temp of both source and pass if difference is less than "<Tempdifference>"
	Then user compare the humidity of both source and pass if difference is less than "<Humiditydifference>"
	Then user compare the wind speed of both source and pass if difference is less than "<windSpeeddifference>"
	Examples:
	|city|Tempdifference|Humiditydifference|windSpeeddifference|
	|Bengaluru|5|15|5|
	 