package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.exceptions.DestJuncException;

public class RoadMap {
	//Atributos:
	protected List<Junction> junctionList;
	protected List<Road> roadList;
	protected List <Vehicle> vehicleList;
	protected Map<String, Junction> junctionMap; //Mapa de identificadores de cruces.
	protected Map<String, Road> roadMap; //Mapa de identificadores de carreteras.
	protected Map<String, Vehicle> vehicleMap; //Mapa de identificadores de vehículos.

	protected RoadMap() {
		this.junctionList = new ArrayList<Junction>();
		this.roadList = new ArrayList<Road>();
		this.vehicleList = new ArrayList<Vehicle>();
		this.junctionMap = new HashMap<String, Junction>();
		this.roadMap = new HashMap<String, Road>();
		this.vehicleMap = new HashMap<String, Vehicle>();
	}

	protected void addJunction(Junction j) {
		if(!this.junctionMap.containsKey(j.getId())) {
			this.junctionMap.put(j.getId(), j);
			this.junctionList.add(j);
		}
	}
	
	protected void addRoad(Road r) throws DestJuncException {
		if(!this.roadMap.containsKey(r.getId()) 
				&& this.junctionMap.containsKey(r.getJDest().getId())
				&& this.junctionMap.containsKey(r.getJSoruce().getId())) {
			this.roadMap.put(r.getId(), r);
			this.roadList.add(r);
		}
		else {
			throw new DestJuncException("Los cruces que conectan a la carretera no existen en el mapa de carreteras.");
		}
	}
	
	protected void addVehicle(Vehicle v) throws Exception {
		int i = 0;
		boolean addVehicle = true;
		
		if(!this.vehicleMap.containsKey(v.getId())){
			while(i < v.getItinerary().size()-1 && addVehicle){
				if(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)) != null) {
					i++;
				}
				else {
					addVehicle = false;
				}
			}
			
			if(addVehicle) {
				this.vehicleMap.put(v.getId(), v);
				this.vehicleList.add(v);
			}
		}
		else {
			throw new Exception("No se puede añadir el vehículo");
		}
		
	}
	
	public Junction getJunction(String id) {
		return this.junctionMap.getOrDefault(id, null);
	}
	
	public Road getRoad(String id) {
		return this.roadMap.getOrDefault(id, null);
	}
	
	public Vehicle getVehicle(String id) {
		return this.vehicleMap.getOrDefault(id, null);
	}
	
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(new ArrayList<>(this.junctionList));
	}
	
	public List<Road> getRoads(){
		return Collections.unmodifiableList(new ArrayList<>(this.roadList));
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(new ArrayList<>(this.vehicleList));
	}
	
	protected void reset() {
		this.junctionList.clear();
		this.junctionMap.clear();
		this.roadList.clear();
		this.roadMap.clear();
		this.vehicleList.clear();
		this.vehicleMap.clear();
	}
	
	public JSONObject report() {
		JSONObject js = new JSONObject();
		JSONArray junctionsJS = new JSONArray();
		JSONArray roadsJS = new JSONArray();
		JSONArray vehiclesJS = new JSONArray();
		
		for (int i = 0; i < this.junctionList.size(); i++) {
			 junctionsJS.put(this.junctionList.get(i).report());
		}
		js.put("junctions", junctionsJS);
		
		for (int i = 0; i < this.roadList.size(); i++) {
			 roadsJS.put(this.roadList.get(i).report());
		}
		js.put("road", roadsJS);
		
		for (int i = 0; i < this.vehicleList.size(); i++) {
			 vehiclesJS.put(this.vehicleList.get(i).report());
		}
		js.put("vehicles", vehiclesJS);
		
		return js;
	}
}
