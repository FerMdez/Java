package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	public NewVehicleEventBuilder() {
		super("new_vehicle");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		return new NewVehicleEvent(data.getInt("time"), data.getString("id"), 
				data.getInt("maxspeed"), data.getInt("class"), 
				this.createArray(data.getJSONArray("itinerary")));
	}
	
	private List<String> createArray(JSONArray itineraryArray){
		List<String> itinerary = new ArrayList<>();
		
		for (int i = 0; i < itineraryArray.length(); i++) {
			itinerary.add(itineraryArray.getString(i));
		}
		
		return itinerary;
	}

}