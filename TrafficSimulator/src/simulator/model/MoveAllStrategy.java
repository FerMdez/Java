package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	public MoveAllStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> aux = new ArrayList<Vehicle>();
		if(!q.isEmpty()) {
			for (int i = 0; i < q.size(); i++) {
				aux.add(q.get(i));
			}
		}
		return aux;
	}

}
