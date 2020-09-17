package simulator.model;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	//Atributos:
	private RoadMap mapaCarreteras; //Mapa de carreteras en el cual se almacenan todos los objetos de la simulación.
	private List<Event> listaEventos; //Lista de eventos a ejectuar, ordenada por tiempo. A igualdad de tiempo, preferencia el que fue añadido antes a la lista.
	private int tiempo; //Paso de la simulación. Inicialmente, vale 0.
	private List<TrafficSimObserver> listaObservadores; //Lista de observadores.
	
	//Constructor:
	public TrafficSimulator() {
		this.mapaCarreteras = new RoadMap();
		this.listaEventos = new SortedArrayList<Event>();
		this.tiempo = 0;
		this.listaObservadores = new ArrayList<TrafficSimObserver>();
	}
	
	//Métodos:
	public void addEvent(Event e) {
		this.listaEventos.add(e);
		this.onEventAdded(this.mapaCarreteras, this.listaEventos, e, this.tiempo);
	}
	
	public void advance() {
		//1.Incrementa el tiempo de la simulación en 1.
		this.tiempo++;
		this.onAdvanceStart(this.mapaCarreteras, this.listaEventos, this.tiempo);
		
		//2.Ejecuta todos los eventos que coincidan con el tiempo actual de la simulación y los elimina de la lista.
		//Después, llama a sus correspondientes métodos "execute".
		while(!this.listaEventos.isEmpty() && this.listaEventos.get(0).getTime() == this.tiempo) {
			this.listaEventos.get(0).execute(this.mapaCarreteras);
			this.listaEventos.remove(this.listaEventos.get(0));
		}
		
		//3.Llama al método "advance" de todos los cruces.
		for (int i = 0; i < this.mapaCarreteras.getJunctions().size(); i++) {
			this.mapaCarreteras.getJunctions().get(i).advance(this.tiempo);
		}
		
		//4.LLama al método "advance" de todas las carreteras.
		for (int i = 0; i < this.mapaCarreteras.getRoads().size(); i++) {
			this.mapaCarreteras.getRoads().get(i).advance(this.tiempo);
		}
		
		this.onAdvanceEnd(this.mapaCarreteras, this.listaEventos, this.tiempo);
	}
	
	public void reset() {
		this.listaEventos.clear();
		this.mapaCarreteras.reset();
		this.tiempo = 0;
		this.onReset(this.mapaCarreteras, this.listaEventos, this.tiempo);
	}
	
	public JSONObject report() {
		JSONObject json = new JSONObject();
		
		json.put("time", this.tiempo);
		json.put("state", this.mapaCarreteras.report());
		
		return json;
		
	}
	
	/*P2*/
	
	private void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		for (TrafficSimObserver o : this.listaObservadores) {
			o.onAdvanceStart(map, events, time);
		}
	}
	
	private void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		for (TrafficSimObserver o : this.listaObservadores) {
			o.onAdvanceEnd(map, events, time);
		}
	}
	
	private void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		if(e == null) {
			this.onError("Error al añadir el evento.");
			throw new IllegalArgumentException();
		}
		
		for (TrafficSimObserver o : this.listaObservadores) {
			o.onEventAdded(map, events, e, time);
		}
	}
	
	private void onReset(RoadMap map, List<Event> events, int time) {
		for (TrafficSimObserver o : this.listaObservadores) {
			o.onReset(map, events, time);
		}
	}
	
	@SuppressWarnings("unused")
	private void onRegister(RoadMap map, List<Event> events, int time) {}
	
	public void onError(String err) {
		System.err.println(err);
		JOptionPane.showMessageDialog(new JFrame(), err);
	}
	
	@Override
	public void addObserver(TrafficSimObserver o) {
		if (!this.listaObservadores.contains(o)) {
			this.listaObservadores.add(o);
		}
		o.onRegister(this.mapaCarreteras, this.listaEventos, this.tiempo);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		this.listaObservadores.remove(o);
	}
	
	//Añadido como parte opcional:
	public void save(OutputStream output) {
		PrintStream p = new PrintStream(output);
		
		p.println("{");
		p.println(" \"events\": ");
		p.println("[");
		p.println(this.mapaCarreteras.report());
		p.println("]");
		p.println("}");
		
	}
		
}
