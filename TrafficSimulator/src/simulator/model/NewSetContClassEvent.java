package simulator.model;

import java.util.List;

import simulator.exceptions.ContClassException;
import simulator.exceptions.ItineraryException;
import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {
	List<Pair<String,Integer>> cs;
	
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) throws ContClassException {
		super(time);
		if(cs != null) {
			this.cs = cs;
		}
		else {
			throw new ContClassException();
		}
	}


	@Override
	void execute(RoadMap map) {
		Vehicle v;
		for (int i = 0; i < this.cs.size(); i++) {
			v = map.getVehicle(this.cs.get(i).getFirst());
			try {
				setContamination(v, i);
			} catch (ItineraryException e) {
				e.getMessage();
			}
			
		}
	}
	
	private void setContamination(Vehicle v, int i) throws ItineraryException {
		if (v != null) {
			try {
				v.setContaminationClass(this.cs.get(i).getSecond());
			} catch (ContClassException e) {
				e.getMessage();
			}
		}
		else {
			throw new ItineraryException("El vehículo no existe.");
		}
	}


	@Override
	public String toString() {
		String events = "";
		for (int i = 0; i < this.cs.size(); i++) {
			events += "(" + this.cs.get(i).getFirst() + ", "
					+ this.cs.get(i).getSecond() + ")";
		}
		return "New SetContaminationClass" + "[" + events + "]";
	}

}
