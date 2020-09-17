package simulator.model;

import java.util.List;

import simulator.exceptions.ItineraryException;
import simulator.exceptions.WeatherException;
import simulator.misc.Pair;

public class NewSetWeatherEvent extends Event {
	protected List<Pair<String,Weather>> ws;
	
	public NewSetWeatherEvent(int time, List<Pair<String,Weather>> ws) throws WeatherException {
		super(time);
		if(ws != null) {
			this.ws = ws;
		}
		else {
			throw new WeatherException("El tiempo atmosférico no puede ser nulo.");
		}
	}

	@Override
	void execute(RoadMap map) {
		Road r;
		for (int i = 0; i < this.ws.size(); i++) {
			r = map.getRoad(this.ws.get(i).getFirst());
			try {
				setWeather(r, i);
			} catch (ItineraryException e) {
				e.getMessage();
			}
		}
	}
	
	private void setWeather(Road r, int i) throws ItineraryException {
		if(r != null) {
			try {
				r.setWeather(this.ws.get(i).getSecond());
			} catch (WeatherException e) {
				e.getMessage();
			}
		}
		else {
			throw new ItineraryException("La carretera no existe.");
		}
	}

	@Override
	public String toString() {
		String events = "";
		for (int i = 0; i < this.ws.size(); i++) {
			events += "(" + this.ws.get(i).getFirst() + ", "
					+ this.ws.get(i).getSecond() + ")";
		}
		return "New SetWeather: " + "[" + events + "]";
	}

}
