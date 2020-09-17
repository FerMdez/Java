package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.exceptions.UnsupportedFileException;
//import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

/**
 * @author Fernando Méndez Torrubiano
 *
 */

public class Controller {
	private TrafficSimulator trafficSimulator;
	private Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory){
		if(sim != null || eventsFactory != null) {
			this.trafficSimulator = sim;
			this.eventsFactory = eventsFactory;
		}
		else {
			throw new NullPointerException("Atributo nulo.");
		}
	}

	public void loadEvents(InputStream in) throws UnsupportedFileException {
		JSONObject jo = new JSONObject(new JSONTokener(in));
			
		if (jo.has("events")) {
			JSONArray ja = jo.getJSONArray("events");
			for (int i = 0; i < ja.length(); i++) {
				this.trafficSimulator.addEvent(this.eventsFactory.createInstance(ja.getJSONObject(i)));
			}
		}
		else {
			throw new UnsupportedFileException();
		}
		
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		
		p.println("{");
		p.println(" \"states\": [");
		for (int i = 0; i < n; i++) {
			this.trafficSimulator.advance();
			p.println(this.trafficSimulator.report());
			p.print(",");
		}
		p.println("]");
		p.println("}");
	}
	
	public void reset() {
		this.trafficSimulator.reset();
	}
	
	/*P2*/
	
	public void run(int n) {
		for (int i = 0; i < n; i++) {
			this.trafficSimulator.advance();
		}
	}
	
	//A�adido como parte opcional:
	public void save(OutputStream output) {
		this.trafficSimulator.save(output);
	}
	
	public void addObserver(TrafficSimObserver o) {
		this.trafficSimulator.addObserver(o);	
	}

	public void removeObserver(TrafficSimObserver o) {
		this.trafficSimulator.removeObserver(o);		
	}
	
	public void addEvent(Event e) {
		this.trafficSimulator.addEvent(e);
	}

}
