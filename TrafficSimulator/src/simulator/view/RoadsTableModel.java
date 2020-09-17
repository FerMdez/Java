package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TableModel, TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Road> _roads;
	private String[] _colNames = {"Id", "Lenght", "Weather", "Max Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	public RoadsTableModel(Controller ctrl) {
		this._roads = new ArrayList<Road>();
		ctrl.addObserver(this);
	}

	public void update() {
		fireTableDataChanged();	
	}
	
	public void setRoadsList(List<Road> roads) {
		this._roads = roads;
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
		return this._roads == null ? 0 : this._roads.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		
		switch (columnIndex) {
		case 0:
			s = _roads.get(rowIndex).getId();
			break;
		case 1:
			s = _roads.get(rowIndex).getLenght();
			break;
		case 2:
			s = _roads.get(rowIndex).getWeather();
			break;
		case 3:
			s = _roads.get(rowIndex).getMaxSpeed();
			break;
		case 4:
			s = _roads.get(rowIndex).getCurrentSpeedLimit();
			break;
		case 5:
			s = (int) _roads.get(rowIndex).getTotalCO2();
			break;
		case 6:
			s = (int) _roads.get(rowIndex).getCO2Limit();
			break;
		}
		
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
