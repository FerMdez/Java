package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TableModel, TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Junction> _junctions;
	private String[] _colNames = {"Id", "Green", "Queues"};
	
	public JunctionsTableModel(Controller ctrl) {
		this._junctions = new ArrayList<Junction>();
		ctrl.addObserver(this);
	}
	
	public void update() {
		fireTableDataChanged();	
	}
	
	public void setJunctionsList(List<Junction> junctions) {
		this._junctions = junctions;
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
		return _junctions == null ? 0 : _junctions.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		
		switch (columnIndex) {
		case 0:
			s = _junctions.get(rowIndex).getId();
			break;
		case 1:
			if(_junctions.get(rowIndex).getGreenLightIndex() == -1) {
				s = "NONE";
			}
			else {
				s = _junctions.get(rowIndex).getInRoads().get(_junctions.get(rowIndex).getGreenLightIndex());
			}
			break;
		case 2:
			s = _junctions.get(rowIndex).getQueues();
			break;
		}
		
		return s;
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setJunctionsList(map.getJunctions());
			}
		});

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setJunctionsList(map.getJunctions());
			}
		});

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setJunctionsList(map.getJunctions());
			}
		});

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setJunctionsList(map.getJunctions());
			}
		});

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setJunctionsList(map.getJunctions());
			}
		});

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}
}
