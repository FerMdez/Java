package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.RoadMap;
import simulator.model.SimulatedObject;
import simulator.model.Vehicle;

public class ChangueCO2Dialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Atributos:
	private int _status;
	
	private JComboBox<Vehicle> _vehicles;
	private JSpinner _timeSpinner;
	private JComboBox<Integer> _co2Class;
	
	private final String _HELPMSG1 = "Selecciona un vehículo";
	private final String _HELPMSG2 = "Selecciona una clase de contaminación";
	
	//private Controller _ctrl;
	private RoadMap _map;
	
	//Construcotor:
	public ChangueCO2Dialog() {
		super();
		initGUI();
	}
	
	public ChangueCO2Dialog(Frame parent) {
		super(parent, true);
		initGUI();
	}
		
	//Métodos:
	private void initGUI() {
		_status = 0;
		
		setTitle("Changue CO2 Class");
		setBounds(500, 500, 500, 250);
		JPanel mainPanel = new JPanel();
		
		mainPanel.setLayout(new GridLayout(3, 1, 0 , 15));
		
		setContentPane(mainPanel);
		
		JPanel labelPanel = new JPanel(new GridLayout(2, 1));
				
		JLabel help1 = new JLabel(_HELPMSG1);
		JLabel help2 = new JLabel(_HELPMSG2);
		
		labelPanel.add(help1);
		labelPanel.add(help2);
		
		
		mainPanel.add(labelPanel);
		
		JPanel viewPanel = new JPanel(new FlowLayout());
		
		mainPanel.add(viewPanel);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		_vehicles = new JComboBox<Vehicle>();
		_vehicles.setPreferredSize(new Dimension(100, 25));
		
		_timeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		_timeSpinner.setToolTipText("Tiempo a partir de ahora para programar el evento");
		_timeSpinner.setPreferredSize(new Dimension(80, 25));
		
		_co2Class = new JComboBox<Integer>();
		_co2Class.setPreferredSize(new Dimension(80, 25));
		for (int i = 0; i < 11; i++) {
			_co2Class.addItem(i);
		}
		
		viewPanel.add(new JLabel("Vehicle: "));
		viewPanel.add(_vehicles);
		viewPanel.add(new JLabel("CO2 Class: "));
		viewPanel.add(_co2Class);
		viewPanel.add(new JLabel("Ticks: "));
		viewPanel.add(_timeSpinner);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangueCO2Dialog.this.setVisible(false);					
			}
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(_vehicles.getSelectedItem() != null) {
					_status = 1;	
				}
				else {
					_status = 0;
					JOptionPane.showMessageDialog(new JFrame(), "Ningún vehículo seleccionado.");
				}
				ChangueCO2Dialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);
		
	}

	public SimulatedObject getVehicle() {
		return (SimulatedObject) this._vehicles.getSelectedItem();
	}
	
	public int getCO2Class() {
		return this._co2Class.getSelectedIndex();
	}

	public int getTicks() {
		return (int) this._timeSpinner.getValue();
	}

	public int open(RoadMap map) {
		this._map = map;
		
		for (int i = 0; i < _map.getVehicles().size(); i++) {
			if(_map.getVehicles().get(i) != null) {
				_vehicles.addItem(_map.getVehicles().get(i));
			}
		}

		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		setVisible(true);
		
		return _status;
	}
	
}
