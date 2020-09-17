package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {
	protected int timeslot;

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
		this.timeslot = 1;
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		if(data.has("timeslot")) { //!data.isNull("timeslot")?
			return new MostCrowdedStrategy(data.getInt("timeslot"));
		}
		else {
			return new MostCrowdedStrategy(this.timeslot);
		}
	}

}
