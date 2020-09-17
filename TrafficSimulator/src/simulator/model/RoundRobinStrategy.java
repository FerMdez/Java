package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {
	private int timeSlot; //Número de ticks consecutivos durante los cuales, la carretera puede mantener el semáforo en verde.
	
	public RoundRobinStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int
			currGreen, int lastSwitchingTime, int currTime) {
		int index = -1;
		
		if(!roads.isEmpty()) {
			if(currGreen == -1) {
				index = 0;
			}
			else if((currTime-lastSwitchingTime) < timeSlot) {
				index = currGreen;
			}
			else {
				index = ((currGreen+1) % (roads.size()));
			}
		}
		
		return index;
		
	}

}
