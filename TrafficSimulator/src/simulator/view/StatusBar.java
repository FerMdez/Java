package simulator.view;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar.Separator;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Atributos:
	private JLabel time;
	private JLabel actualTime;
	private JLabel event;
	private JLabel lastEventAdded;
	private JLabel calendar;
	
	private final static Calendar _calendario = new GregorianCalendar();
	private final static int _hour = _calendario.get(Calendar.HOUR_OF_DAY); 
	private final static int _minutes = _calendario.get(Calendar.MINUTE);
	
	private List<Event> _events;
	
	//Constructor:
	public StatusBar(Controller ctrl) {
		this._events = new ArrayList<Event>();
		this.InitGUI();
		ctrl.addObserver(this);
	}
	
	private void InitGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		this.time = new JLabel("Time: ");
		this.actualTime = new JLabel();
		this.event = new JLabel("Event Added: ");
		if(this._events.size() > 0) {
			this.lastEventAdded = new JLabel(this._events.get(this._events.size()-1).toString());
		}
		else {
			this.lastEventAdded = new JLabel();
		}
		this.calendar = new JLabel("Local time: " + _hour + " : " + _minutes); //Aunque no se pide en la práctica, añado la hora de forma opcional.
		
		this.add(this.calendar);
		this.add(new Separator());
		this.add(this.time);
		this.add(this.actualTime);
		this.add(new Separator());
		this.add(this.event);
		this.add(this.lastEventAdded);
		
	}

	//Métodos:
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this._events = events;
		this.actualTime.setText(String.valueOf(time));
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this._events = events;
		this.actualTime.setText(String.valueOf(time));
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._events = events;
		this.lastEventAdded.setText(String.valueOf(e));
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._events = events;
		this.actualTime.setText(String.valueOf(0));
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._events = events;
		this.actualTime.setText(String.valueOf(time));
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
