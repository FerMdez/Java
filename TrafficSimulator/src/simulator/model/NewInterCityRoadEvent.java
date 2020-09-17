package simulator.model;

import simulator.exceptions.ContClassException;
import simulator.exceptions.DestJuncException;
import simulator.exceptions.LeghtException;
import simulator.exceptions.MaxSpeedException;
import simulator.exceptions.SrcJuncException;
import simulator.exceptions.WeatherException;

public class NewInterCityRoadEvent extends NewRoadEvent {

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, 
			int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) {
		try {
			map.addRoad(new InterCityRoad(this.id, map.getJunction(this.srcJun), map.getJunction(this.destJunc), 
					this.maxSpeed, this.co2limit, this.length, this.weather));
		} catch (DestJuncException | SrcJuncException | MaxSpeedException | ContClassException | LeghtException
				| WeatherException e) {
			e.getMessage();
		}
	}

	@Override
	public String toString() {
		return "New InterCityRoad '"+id+"'";
	}

}
