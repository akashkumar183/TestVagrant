package pageFile;

import java.io.Serializable;

public class bean implements Serializable {

	private static final long serialVersionUID = 1L;
	private float tempNDTV = 0;
	private int humidityNDTV = 0;
	private float windSpeedNDTV = 0;
	private float tempOpenWeather = 0;
	private int humidityOpenWeather = 0;
	private float windSpeedOpenWeather = 0;

	public float getTempNDTV() {
		return tempNDTV;
	}

	public void setTempNDTV(float tempNDTV) {
		this.tempNDTV = tempNDTV;
	}

	public int getHumidityNDTV() {
		return humidityNDTV;
	}

	public void setHumidityNDTV(int humidityNDTV) {
		this.humidityNDTV = humidityNDTV;
	}

	public float getWindSpeedNDTV() {
		return windSpeedNDTV;
	}

	public void setWindSpeedNDTV(float windSpeedNDTV) {
		this.windSpeedNDTV = windSpeedNDTV;
	}

	public float getTempOpenWeather() {
		return tempOpenWeather;
	}

	public void setTempOpenWeather(float tempOpenWeather) {
		this.tempOpenWeather = tempOpenWeather;
	}

	public int getHumidityOpenWeather() {
		return humidityOpenWeather;
	}

	public void setHumidityOpenWeather(int humidityOpenWeather) {
		this.humidityOpenWeather = humidityOpenWeather;
	}

	public float getWindSpeedOpenWeather() {
		return windSpeedOpenWeather;
	}

	public void setWindSpeedOpenWeather(float windSpeedOpenWeather) {
		this.windSpeedOpenWeather = windSpeedOpenWeather;
	}

	private static bean myobj = null;

	public static bean getInstance() {
		if (myobj == null) {
			myobj = new bean();
		}
		return myobj;
	}

	public bean() {
	}

}
