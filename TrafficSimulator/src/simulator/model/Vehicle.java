package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import simulator.exceptions.ContClassException;
import simulator.exceptions.ItineraryException;
import simulator.exceptions.MaxSpeedException;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle> {
	//Atributos:
	private List<Junction> itinerary; //Lista de cruces. Itinerario del vehículo.
	private int actualItinerary; //Punto actual del itinerario en el que se encuentra el vehículo.
	private int maxSpeed; //Velocidad máxima
	private int currentSpeed; //Velocidad actual
	private VehicleStatus status; //Estado del vehículo (PENDING, TRAVELING, WAITING, ARRIVED;)
	private Road road; //Carretera. Null en caso de que no esté en ninguna
	private int location; //Distancia recorrida (distancia entre el vehículo y el origen de la carretera, 0)
	private int contClass; //CO2 emitido por el vehículo en cada tick
	private int totalContamination; //CO2 emitido por el vehículo durante la trayectoria recorrida
	private int totalTravelDistance; //Distancia recorrida por el vehículo
	
	//Contructor:
	public Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) 
			throws MaxSpeedException, ContClassException, ItineraryException {
		super(id);
		if(maxSpeed >= 0) {
			this.maxSpeed = maxSpeed;
		}
		else {
			throw new MaxSpeedException("La velocidad no puede ser negativa.");
		}
		if(contClass >= 0 || contClass <= 10) {
			this.contClass = contClass;
		}
		else {
			throw new ContClassException("El valor de contaminación debe estar entre 0 y 10.");
		}
		if(itinerary.size() >= 2) {
		//	this.itinerary = itinerary;
			this.actualItinerary = 0;
			this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		}
		else {
			throw new ItineraryException("Itinerario no válido.");
		}
		this.currentSpeed = 0;
		this.status = VehicleStatus.PENDING;
		this.road = null;
		this.location = 0;
		this.totalContamination = 0;
		this.totalTravelDistance = 0;
	}
	
	
	//Métodos:
	protected void setSpeed(int s) throws MaxSpeedException {
		if(s >= 0) {
			if(this.maxSpeed > s) {
				this.currentSpeed = s;
			}
			else {
				this.currentSpeed = maxSpeed;
			}
		}
		else {
			throw new MaxSpeedException("La velocidad no puede ser negativa.");
		}
	}
	
	protected void setContaminationClass(int c) throws ContClassException {
		if(contClass >= 0 || contClass <= 10) {
			this.contClass = c;
		}
		else {
			throw new ContClassException("El valor de contaminación debe estar entre 0 y 10");
		}
	}

	protected void advance(int time) {
		if(this.status == VehicleStatus.TRAVELING) {
			int oldLocation = this.location;
			
			//Actualiza la localización del vehículo:
			if((this.location + this.currentSpeed) < this.road.getLenght()) {
				this.location += this.currentSpeed;
				this.totalTravelDistance += this.currentSpeed;
			}
			else {
				this.location = this.road.getLenght();
				this.totalTravelDistance += this.road.getLenght();
				
			}
			
			//Calcula la contaminación prooducida:
			this.totalContamination += (this.location - oldLocation) * this.contClass;
			try {
				this.road.addContamination((this.location - oldLocation) * this.contClass);
			} catch (ContClassException e) {
				e.getMessage();
			}
			
			//Si el vehículo ha llegado al final de la carretera, entra en la cola del cruce:
			if(this.location == this.road.getLenght()) {
				this.status = VehicleStatus.WAITING;
				this.currentSpeed = 0;
				this.road.getJDest().enter(this);
			}
		} 
	}
	
	protected void moveToNextRoad() throws ItineraryException {
		if(this.status == VehicleStatus.PENDING || this.status == VehicleStatus.WAITING) {
			if(this.actualItinerary > 0) { this.road.exit(this); }
			this.location = 0;
			this.currentSpeed = 0;
			if(this.itinerary.size()-1 == this.actualItinerary) {
				this.status = VehicleStatus.ARRIVED;
			}
			else {
				try {
					this.road = this.itinerary.get(this.actualItinerary).roadTo(this.itinerary.get(this.actualItinerary+1));
					if(this.road != null) { this.road.enter(this); }
					this.actualItinerary++;
					this.status = VehicleStatus.TRAVELING;	
				} 
				catch (ItineraryException e) {
					e.getMessage();
				}	
			}
		}
		else {
			throw new ItineraryException("El vehículo no ha llegado al final de la carretera.");
		}
	}
	
	public int getContClass() {
		return this.contClass;
	}
	
	public int getTotalCont() {
		return this.totalContamination;
	}
	
	public Road getRoad() {
		return this.road;
	}
	
	public int getLocation() {
		return this.location;
	}
	
	public int getTotalDistance() {
		return this.totalTravelDistance;
	}
	
	public int getCurrentSpeed() {
		return this.currentSpeed;
	}
	
	public int getMaxSpeed() {
		return this.maxSpeed;
	}
	
	public List<Junction> getItinerary(){
		return Collections.unmodifiableList(new ArrayList<>(itinerary));
	}
	
	public VehicleStatus getStatus() {
		return this.status;
	}
	
	public void setRoad(Road r) {
		this.road = r;
	}
	
	@Override
	public int compareTo(Vehicle v) {
		if(this.location == v.getLocation()) {
			return 0;
		}
		else if(this.location < v.getLocation()) {
			return 1;
		}
		else {
			return -1;
		}	
	}
	
	//Devuelve el estado del vehículo en el siguiente formato JSON:
	public JSONObject report() {
		JSONObject js = new JSONObject();
		
		js.put("id", this._id);
		js.put("speed", this.currentSpeed);
		js.put("distance", this.totalTravelDistance);
		js.put("co2", this.totalContamination);
		js.put("class", this.contClass);
		js.put("status", this.status);
		if(this.status != VehicleStatus.PENDING && this.status != VehicleStatus.ARRIVED){
			js.put("road", this.road);
			js.put("location", this.location);
		}
		
		return js;
	}

}