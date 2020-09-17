package simulator.model;

import simulator.exceptions.ContClassException;
import simulator.exceptions.DestJuncException;
import simulator.exceptions.LeghtException;
import simulator.exceptions.MaxSpeedException;
import simulator.exceptions.SrcJuncException;
import simulator.exceptions.WeatherException;

public class CityRoad extends Road {
	
	//Constructor:
	public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws SrcJuncException, DestJuncException, MaxSpeedException, ContClassException,
			LeghtException, WeatherException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}
	
	//Métodos:
	@Override
	protected void reduceTotalContamination() {
		switch (this.weather) {
		case WINDY: this.totalCont -= 10; break;
		case STORM: this.totalCont -= 10; break;
		default: 
			this.totalCont -= 2;
			break;
		}
		if(this.totalCont < 0) { this.totalCont = 0; }
	}

	@Override
	protected void updateSpeedLimit() {}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		//return (int)((11.0-v.getContClass()/11.0)*this.maxSpeed);
		return (int) Math.ceil((((11.0-v.getContClass())/11.0)*this.maxSpeed));
	}

}
