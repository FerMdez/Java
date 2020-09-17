package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.exceptions.ContClassException;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		try {
			return new NewSetContClassEvent(data.getInt("time"), this.createArray(data.getJSONArray("info")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ContClassException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			return null;
		}
	}
	
	private List<Pair<String, Integer>> createArray(JSONArray infoArray){
		List<Pair<String, Integer>> info = new ArrayList<Pair<String, Integer>>();
		
		for (int i = 0; i < infoArray.length(); i++) {
			info.add(i, new Pair<String, Integer>(infoArray.getJSONObject(i).getString("vehicle"), 
					infoArray.getJSONObject(i).getInt("class")));
		}
		
		return info;
	}
}
