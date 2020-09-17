package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.exceptions.WeatherException;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		try {
			return new NewSetWeatherEvent(data.getInt("time"), this.createArray(data.getJSONArray("info")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (WeatherException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			return null;
		}
	}
	
	private List<Pair<String, Weather>> createArray(JSONArray infoArray){
		List<Pair<String, Weather>> info = new ArrayList<Pair<String, Weather>>();
		
		for (int i = 0; i < infoArray.length(); i++) {
			info.add(i, new Pair<String, Weather>(infoArray.getJSONObject(i).getString("road"),
					Weather.valueOf(infoArray.getJSONObject(i).getString("weather"))));
		}
		
		return info;
	}

}
