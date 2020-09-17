package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {
	protected int timeslot;

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		this.timeslot = 1;
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		if(data.has("timeslot")) {
			return new RoundRobinStrategy(data.getInt("timeslot"));
		}
		else {
			return new RoundRobinStrategy(this.timeslot);
		}
	}

}
