package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.exceptions.ContClassException;
import simulator.exceptions.ItineraryException;
import simulator.exceptions.MaxSpeedException;

public class NewVehicleEvent extends Event {
	//Atributos:
	protected int maxSpeed, contClass;
	protected String id;
	protected List<String> itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, int
			contClass, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		Vehicle v;
		List<Junction> juncItinerary = new ArrayList<Junction>();
		
		try {
			for (int i = 0; i < this.itinerary.size(); i++) {
				juncItinerary.add(map.getJunction(this.itinerary.get(i)));
			}
			v = new Vehicle(this.id, this.maxSpeed, this.contClass, juncItinerary);
			try {
				map.addVehicle(v);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v.moveToNextRoad();
		} catch (MaxSpeedException | ContClassException | ItineraryException e) {
			e.getMessage();
		}
	}

	@Override
	public String toString() {
		return "New Vehicle '"+id+"'";
	}

}
