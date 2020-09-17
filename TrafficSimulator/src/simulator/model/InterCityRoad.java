package simulator.model;

import simulator.exceptions.ContClassException;
import simulator.exceptions.DestJuncException;
import simulator.exceptions.LeghtException;
import simulator.exceptions.MaxSpeedException;
import simulator.exceptions.SrcJuncException;
import simulator.exceptions.WeatherException;

public class InterCityRoad extends Road {
	
	//Contructor:
	protected InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws SrcJuncException, DestJuncException, MaxSpeedException, ContClassException,
			LeghtException, WeatherException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}
	
	//Métodos:
	@Override
	protected void reduceTotalContamination() {
		int x = 0;
		
		switch (this.weather) {
		case SUNNY: x = 2; break;
		case CLOUDY: x = 3; break;
		case RAINY: x = 10; break;
		case WINDY: x = 15; break;
		case STORM: x = 20; break;
		}
		
		this.totalCont = (int)((100-x)/100)*(this.totalCont);
	}

	@Override
	protected void updateSpeedLimit() {
		if(this.totalCont >= this.contLimit) {
			//this.curentSpeedLimit = (int)(this.maxSpeed * 0.5);
			this.curentSpeedLimit = (int)Math.ceil((this.maxSpeed * 0.5));
		}
		else {
			this.curentSpeedLimit = this.maxSpeed;
		}
	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		int speed = 0;
		
		if(this.weather.equals(Weather.STORM)) {
			//speed = (int)(this.curentSpeedLimit * 0.8);
			speed = (int)Math.ceil(this.maxSpeed * 0.8);
		}
		else {
			speed = this.curentSpeedLimit;
		}
		
		return speed;
	}

}
