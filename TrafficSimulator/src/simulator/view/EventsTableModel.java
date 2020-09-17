package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TableModel, TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Event> _events;
	private String[] _colNames = {"Time", "Desc."};
	
	public EventsTableModel(Controller ctrl) {
		this._events = new ArrayList<Event>();
		ctrl.addObserver(this);
	}
	
	public void update() {
		fireTableDataChanged();	
	}
	
	public void setEventsList(List<Event> events) {
		this._events = events;
		this.update();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _events == null ? 0 : _events.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		
		switch (columnIndex) {
		case 0:
			s = _events.get(rowIndex).getTime();
			//s = _events.get(rowIndex);
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		
		return s;
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setEventsList(events);
			}
		});

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setEventsList(events);
			}
		});

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setEventsList(events);
			}
		});

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setEventsList(events);
			}
		});

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setEventsList(events);
			}
		});

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
