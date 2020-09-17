package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	public MoveFirstStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> aux = new ArrayList<Vehicle>();
		if(!q.isEmpty()) {
			aux.add(q.get(0));
		}
		return aux;
	}

}
