package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.exceptions.DestJuncException;
import simulator.exceptions.ItineraryException;
import simulator.exceptions.SrcJuncException;

public class Junction extends SimulatedObject {
	//Atributos:
	protected List<Road> inRoads; //Lista de carreteras entrantes.
	protected Map<Junction, Road> outRoads; //Mapa de carreteras salientes.
	protected List<List<Vehicle>> queues; //Lista de colas de veh�culos esperando para entrar (usar en RECOORRIDOS).
	protected Map<Road, List<Vehicle>> queuesRoad; //Mapa de colas de veh�culos (usar en las B�SQUEDAS).
	protected int greenLightIndex; //�ndice de la carretera con el sem�foro en verde.
	protected int lastLightTime; //Paso en el cu�l el �ndice del sem�foro en verde ha cambiado.
	protected LightSwitchingStrategy lss; //Estrategia de cambio de sem�foro.
	protected DequeuingStrategy dqs; //Estrategia para eliminar veh�culos de las colas.
	protected int x, y; //Coordenadas (para la siguiente pr�ctica).
	
	//Constructor:
	public Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy
			dqStrategy, int xCoor, int yCoor) {
		super(id);
		this.inRoads = new LinkedList<Road>();
		this.outRoads = new HashMap<Junction, Road>();
		this.queues = new ArrayList<List<Vehicle>>();
		this.queuesRoad = new HashMap<Road, List<Vehicle>>();
		this.greenLightIndex = -1;
		this.lastLightTime = 0;
		if(lsStrategy != null) { this.lss = lsStrategy; }
		else { throw new NullPointerException("Estrategia de cambio de sem�foro es nula."); }
		if(dqStrategy != null) { this.dqs = dqStrategy; }
		else { throw new NullPointerException("Estrategia de eliminaci�n de veh�culos nula."); }
		if(xCoor >= 0) { this.x = xCoor; }
		else { throw new NumberFormatException("La coordenada 'X' deber ser positiva."); }
		if(yCoor >= 0) { this.y = yCoor; }
		else { throw new NumberFormatException("La coordenada 'Y' deber ser positiva."); }
	}

	//M�todos:
	
	@Override
	protected void advance(int time) {
		if(this.greenLightIndex != -1) {
			//Calcula la lista de veh�culos que deben avanzar:
			List<Vehicle> listVehicles = new ArrayList<Vehicle>();
			listVehicles = this.dqs.dequeue(this.queues.get(greenLightIndex));
			
			//Mueve los veh�culos que deben avanzar y los elimina de las colas correspondientes:
			for (int i = 0; i < this.queues.get(this.greenLightIndex).size(); i++) {
				//listVehicles.get(i).advance(time);
				try {
					listVehicles.get(i).moveToNextRoad();
				} catch (ItineraryException e) {
					e.getMessage();
				}
				this.queuesRoad.remove(listVehicles.get(i).getRoad());
				this.queues.get(this.greenLightIndex).remove(i);
			}
		}
		//Cambia el �ndice de la carretera de la que hay que poner el sem�foro en verde:
		int nextGreen = this.lss.chooseNextGreen(this.inRoads, this.queues, 
						this.greenLightIndex, this.lastLightTime, time);
		if(nextGreen != this.greenLightIndex) {
			this.greenLightIndex = nextGreen;
			this.lastLightTime = time;
		}
	}

	protected void addInconmmigRoad(Road r) throws DestJuncException {
		if(r.getJDest() == this) {
			//A�ade la carretera al final de la lista de carreteras entrantes:
			this.inRoads.add(r);
			//Crea una cola para la carretera:
			List<Vehicle> q = new LinkedList<Vehicle>();
			//A�ade la nueva cola al final de la lista de colas:
			this.queues.add(q);
			//A�ade el par carretera-cola al mapa de colas de veh�culos:
			this.queuesRoad.put(r, this.queues.get(this.queues.size()-1));
			//this.queuesRoad.put(r, q);
		}
		else {
			throw new DestJuncException("Esta no es una carretera entrante al cruce.");
		}
	}
	
	protected void addOutGoingRoad(Road r) throws SrcJuncException {
		if(r.getJSoruce() == this) { //&& this.outRoads.get(r.getJSoruce()) != null
			this.outRoads.put(r.getJDest(), r);
		}
		else {
			throw new SrcJuncException("No se puede a�adir la carretera saliente.");
		}
	}
	
	protected void enter(Vehicle v) {
		this.queuesRoad.get(v.getRoad()).add(v);
	}
	
	protected Road roadTo(Junction j) {
		return this.outRoads.get(j);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getGreenLightIndex() {
		return this.greenLightIndex;
	}
	
	public List<Road> getInRoads() {
		return this.inRoads;
	}
	
	public List<List<Vehicle>> getQueues(){
		return this.queues;
	}
	
	@Override
	public JSONObject report() {
		JSONObject js = new JSONObject();
		JSONObject queuesJS = new JSONObject();
		JSONArray vehiclesJS = new JSONArray();
		
		js.put("id", this._id);
		if(this.greenLightIndex != 0) {
			js.put("green", this.greenLightIndex);
		}
		else {
			js.put("green", "none");
		}
		for (int i = 0; i < this.queues.size(); i++) {
			queuesJS.put("road", this.inRoads.get(i).getId());
			for (int j = 0; j < this.queues.get(i).size(); j++) {
				vehiclesJS.put(this.queues.get(i).get(j).getId());
			}	
			queuesJS.put("vehicles", vehiclesJS);
			
		}
		js.put("queues", queuesJS);
		
		return js;
	}
}