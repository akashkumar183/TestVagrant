package pageFile;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class weather {

	WebDriver driver;
	WebDriverWait wait;
	bean bean = pageFile.bean.getInstance();

	public weather(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[text()='No Thanks']")
	private WebElement noThanksAlert;

	@FindBy(id = "h_sub_menu")
	private WebElement subMenu;

	@FindBy(xpath = "//a[text()='WEATHER']")
	private WebElement weatherBtn;

	@FindBy(id = "searchBox")
	private WebElement searchBox;

	@FindBy(css = ".leaflet-popup-content-wrapper span.heading")
	private List<WebElement> descriptionList;

	public void clickWithJavaScript(WebElement ele) throws Throwable {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
	}

	public void highLight(WebElement ele) throws Throwable {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
	}

	public void goToNDTVUrl() throws Throwable {
		driver.get("https://www.ndtv.com/");
		wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(subMenu));
		clickWithJavaScript(subMenu);
		highLight(weatherBtn);
		wait.until(ExpectedConditions.elementToBeClickable(weatherBtn));
		clickWithJavaScript(weatherBtn);
	}

	public void enterCityName(String city) throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		searchBox.click();
		searchBox.sendKeys(city);
		Thread.sleep(1500);
		WebElement result = driver.findElement(By.cssSelector("label[for='" + city + "']>input"));
		highLight(result);
		String marked = result.getAttribute("class");
		if (marked.equals("defaultChecked")) {
			return;
		} else
			clickWithJavaScript(result);
	}

	public void copyDetailsFromNDTV(String city) throws Throwable {
		List<String> details = new ArrayList<String>();
		WebElement loc = driver.findElement(By.xpath("//div[@class='cityText'][text()='" + city + "']"));
		highLight(loc);
		clickWithJavaScript(loc);
		wait.until(ExpectedConditions.elementToBeClickable(descriptionList.get(0)));
		for (int i = 0; i < descriptionList.size(); i++) {
			System.out.println(descriptionList.get(i).getText());
			highLight(descriptionList.get(i));
			details.add(descriptionList.get(i).getText());
		}

		float temp = Float.valueOf(getIntFromString(details.get(4)));
		bean.setTempNDTV(temp);
		int humidity = Integer.valueOf(getIntFromString(details.get(2)));
		bean.setHumidityNDTV(humidity);
		String w = details.get(1);
		float wind = Float.valueOf(w.substring(w.indexOf(":") + 1, w.indexOf("KPH") - 1));
		bean.setWindSpeedNDTV(wind);

	}

	public String getIntFromString(String str) throws Throwable {
		str = str.replaceAll("[^\\d]", " ");
		str = str.trim();
		str = str.replaceAll(" +", " ");
		if (str.equals(""))
			return "-1";

		return str;
	}

	public void fetchTempFromApi(String city) throws Throwable {
		RestAssured.baseURI = "https://api.openweathermap.org/data/2.5/";
		String response = given().queryParam("q", city).queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea").when()
				.get("/weather").then().assertThat().statusCode(200).header("Server", "openresty").extract().response()
				.asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		System.out.println("Weather : " + js.getString("weather[0].main"));
		System.out.println("Description : " + js.getString("weather[0].description"));
		System.out.println("Temp : " + js.get("main.temp"));
		System.out.println("Min Temp : " + js.get("main.temp_min"));
		System.out.println("Humidity : " + js.get("main.humidity"));
		System.out.println("Max Temp : " + js.get("main.temp_max"));
		System.out.println("Wind Speed : " + js.getString("wind.speed"));
		float tempInFarenhit = Float.valueOf(js.getString("main.temp"));
		tempInFarenhit = (tempInFarenhit - (float) 273.15) * 9 / 5 + 32;
		bean.setTempOpenWeather(tempInFarenhit);
		bean.setHumidityOpenWeather(Integer.valueOf(js.getString("main.humidity")));
		bean.setWindSpeedOpenWeather(Float.valueOf(js.getString("wind.speed")));
	}

	public void compareTemp(int diff) throws Throwable {
		float ndtvTemp = bean.getTempNDTV();
		float openWeatherTemp = bean.getTempOpenWeather();

		float size = Math.abs(ndtvTemp - openWeatherTemp);
		if (diff < size)
			Assert.fail("Difference is temperature is greater than " + diff);
	}

	public void compareHumidity(int diff) throws Throwable {
		int ndtvHumidity = bean.getHumidityNDTV();
		int openWeatherHumidity = bean.getHumidityOpenWeather();

		int size = Math.abs(ndtvHumidity - openWeatherHumidity);
		if (diff < size)
			Assert.fail("Difference of humidity is greater than " + diff);
	}

	public void compareWindSpeed(float diff) throws Throwable {
		float ndtvWindSpeed = bean.getWindSpeedNDTV();
		float openWeatherWindSpeed = bean.getWindSpeedOpenWeather();

		float size = Math.abs(ndtvWindSpeed - openWeatherWindSpeed);
		if (diff < size)
			Assert.fail("Difference of wind speed is greater than " + diff);
	}

}
