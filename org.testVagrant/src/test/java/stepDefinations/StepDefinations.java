package stepDefinations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import pageFile.bean;
import pageFile.weather;

public class StepDefinations {

	bean bean = pageFile.bean.getInstance();
	WebDriver driver;
	weather w = new weather(driver);

	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "D:\\Cd\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@After
	public void quit() {
		driver.close();
	}

	
	@Then("^user go the NDTV Url$")
	public void goToNDTVUrl() throws Throwable {
		w = new weather(driver);
		w.goToNDTVUrl();
	}

	@Then("^user enter the city name as \"(.*)\"$")
	public void enterCityName(String city) throws Throwable {
		w.enterCityName(city);
	}

	@Then("^user copy the weather details from NDTV site of \"(.*)\"$")
	public void copyDetailsFromNDTV(String city) throws Throwable {
		w.copyDetailsFromNDTV(city);
	}

	@Then("^user fetch the temp of \"(.*)\" from open weather API$")
	public void fetchTempFromApi(String city) throws Throwable {
		w.fetchTempFromApi(city);
	}

	@Then("^user compare the temp of both source and pass if difference is less than \"(.*)\"$")
	public void compareTemp(int diff) throws Throwable {
		w.compareTemp(diff);
	}

	@Then("^user compare the humidity of both source and pass if difference is less than \"(.*)\"$")
	public void compareHumidity(int diff) throws Throwable {
		w.compareHumidity(diff);
	}

	@Then("^user compare the wind speed of both source and pass if difference is less than \"(.*)\"$")
	public void compareWindSpeed(float diff) throws Throwable {
		w.compareWindSpeed(diff);
	}

}
