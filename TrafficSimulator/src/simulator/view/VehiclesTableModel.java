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
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TableModel, TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private Controller _ctrl;
	private List<Vehicle> _vehicles;
	private String[] _colNames = {"Id", "Location", "Itinerary", "CO2 Class", "Max Speed", "Speed", "Total CO2", "Distace"};
	
	public VehiclesTableModel(Controller ctrl) {
		this._vehicles = new ArrayList<Vehicle>();
		ctrl.addObserver(this);
	}

	public void update() {
		fireTableDataChanged();	
	}
	
	public void setVehicleList(List<Vehicle> vehicles) {
		this._vehicles = vehicles;
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
		return _vehicles == null ? 0 : _vehicles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		
		switch (columnIndex) {
		case 0:
			s = _vehicles.get(rowIndex).getId();
			break;
		case 1:
			s = _vehicles.get(rowIndex).getRoad() + ":" 
					+ _vehicles.get(rowIndex).getLocation();
			break;
		case 2:
			s = _vehicles.get(rowIndex).getItinerary();
			break;
		case 3:
			s = _vehicles.get(rowIndex).getContClass();
			break;
		case 4:
			s = _vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = _vehicles.get(rowIndex).getCurrentSpeed();
			break;
		case 6:
			s = (int) _vehicles.get(rowIndex).getTotalCont();
			break;
		case 7:
			s = _vehicles.get(rowIndex).getTotalDistance();
			break;
		}
		
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setVehicleList(map.getVehicles());
			}
		});

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setVehicleList(map.getVehicles());
			}
		});

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setVehicleList(map.getVehicles());
			}
		});

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setVehicleList(map.getVehicles());
			}
		});

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setVehicleList(map.getVehicles());
			}
		});

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}
}
