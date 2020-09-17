package simulator.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.exceptions.ContClassException;
import simulator.exceptions.DestJuncException;
import simulator.exceptions.ItineraryException;
import simulator.exceptions.LeghtException;
import simulator.exceptions.MaxSpeedException;
import simulator.exceptions.SrcJuncException;
import simulator.exceptions.WeatherException;
import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject {
	//Atributos:
	protected Junction source; //Cruce origen
	protected Junction destination; //Cruce destino
	protected int lenght; //Longitud de la carretera
	protected int maxSpeed; //Velocidad máxima permitida en la carretera
	protected int curentSpeedLimit; //Velocidad máxima permitida actualmente
	protected int contLimit; //Límite de contaminación
	protected Weather weather; //Condiciones meteorológicas actuales
	protected int totalCont; //Contaminación actual en la carretera
	protected List<Vehicle> list; //Lista ORDENADA de vehículos que circulan por la carretera
	
	//Constructor:
	protected Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, 
			int contLimit, int length, Weather weather) 
					throws SrcJuncException, DestJuncException, MaxSpeedException, 
					ContClassException, LeghtException, WeatherException {
		super(id);
		if(destJunc != null) {
			this.destination = destJunc;
			this.destination.addInconmmigRoad(this);
		}
		else {
			throw new DestJuncException();
		}
		if(srcJunc != null) { 
			this.source = srcJunc;
			this.source.addOutGoingRoad(this);
		}
		else {
			throw new SrcJuncException();
		}
		if(maxSpeed >= 0) {
			this.maxSpeed = maxSpeed;
			this.curentSpeedLimit = maxSpeed;
		}
		else {
			throw new MaxSpeedException();
		}
		if(contLimit >= 0) {
			this.contLimit =  contLimit;
			this.totalCont = 0;
		}
		else {
			throw new ContClassException();
		}
		if(lenght >= 0) {
			this.lenght = length;
		}
		else {
			throw new LeghtException();
		}
		if(weather != null) {
			this.weather = weather;
		}
		else {
			throw new WeatherException();
		}
		this.list = new SortedArrayList<Vehicle>(); //new LinkedList<Vehicle>();
	}
	
	//Métodos:
	protected void enter(Vehicle v) throws ItineraryException {
		if(v.getLocation() == 0 && v.getCurrentSpeed() == 0) {
			this.list.add(v);
		}
		else {
			throw new ItineraryException("El vehículo no puede entrar en la carretera.");
		}
	}
	
	protected void exit(Vehicle v) {
		if(!this.list.isEmpty()) {
			this.list.remove(v);
			v.setRoad(null);
		}
	}
	
	protected void setWeather(Weather w) throws WeatherException {
		if(w != null) {
			this.weather = w; 
		}
		else {
			throw new WeatherException();
		}
	}
	
	public Weather getWeather() {
		return this.weather;
	}
	
	protected void addContamination(int c) throws ContClassException {
		if(c >= 0) {
			this.totalCont += c;
		}
		else {
			throw new ContClassException();
		}
	}
	
	protected abstract void reduceTotalContamination();
	protected abstract void updateSpeedLimit();
	protected abstract int calculateVehicleSpeed(Vehicle v);
	
	@Override
	protected void advance(int time) {
		//Estable el límite de velocidad de la carretera:
		updateSpeedLimit();
		//Reduce la contaminación total:
		reduceTotalContamination();
		//Recorre la lista de vehículos:
		for (int i = 0; i < this.list.size(); i++) {
			//Actualiza la velocidad del vehículo:
			try {
				this.list.get(i).setSpeed(calculateVehicleSpeed(this.list.get(i)));
			} catch (MaxSpeedException e) {
				e.getMessage();
			}
			//Llama al método advance del vehículo:
			this.list.get(i).advance(time);
		}
	}
	
	public int getLenght() {
		return this.lenght;
	}
	
	public Junction getJDest() {
		return this.destination;
	}
	
	public Junction getJSoruce() {
		return this.source;
	}
	
	protected void setTotalContaminatio(int c) {
		this.totalCont += c;
	}
	
	public double getTotalCO2() {
		return this.totalCont;
	}
	
	public double getCO2Limit() {
		return this.contLimit;
	}
	
	public int getMaxSpeed() {
		return this.maxSpeed;
	}
	
	public int getCurrentSpeedLimit() {
		return this.curentSpeedLimit;
	}
	
	@Override
	public JSONObject report() { 
		JSONObject js = new JSONObject();
		JSONArray vehiclesJS = new JSONArray();
		
		js.put("id", this._id);
		js.put("speedlimit", this.curentSpeedLimit);
		js.put("weather", this.weather);
		js.put("co2", this.totalCont);
		for (int i = 0; i < this.list.size(); i++) {
			//vehiclesJS.put(this.list.get(i).report());
			vehiclesJS.put(this.list.get(i).getId());
		}
		js.put("vehicles", vehiclesJS);
		
		return js;
	}
}