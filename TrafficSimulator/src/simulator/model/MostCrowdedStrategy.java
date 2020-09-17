package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int timeSlot; //Número de ticks consecutivos durante los cuales, la carretera puede mantener el semáforo en verde.
	
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int
			currGreen, int lastSwitchingTime, int currTime) {
		int index = -1;
		
		if(!roads.isEmpty()) {
			if(currGreen == -1) {
				int max = 0;
				for (int i = 0; i < qs.size(); i++) {
					if(qs.get(i).size() > max) {
						max = qs.indexOf(qs.get(i));
					}
				}
				index = max;
			}
			else if((currTime-lastSwitchingTime) < timeSlot) {
				index = currGreen;
			}
			else {
				//TODO
				int max = 0;
				for (int i = ((currGreen+1) % roads.size()); i < qs.size(); i++) {
					if(qs.get(i).size() > max) {
						max = qs.indexOf(qs.get(i));
					}
				}
				if(max == 0) {
					for (int i = 0; i <= currGreen; i++) {
						if(qs.get(i).size() > max) {
							max = qs.indexOf(qs.get(i));
						}
					}
				}
				index = max;
			}
		}
		
		return index;
		
	}

}
