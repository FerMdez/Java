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

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SimulatedObject;
import simulator.model.Weather;

public class ChangueWeatherDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		//Atributos:
		private int _status;
		
		private JComboBox<Road> _roads;
		private JSpinner _timeSpinner;
		private JComboBox<Weather> _weather;
		
		private final String _HELPMSG1 = "Selecciona una carretera";
		private final String _HELPMSG2 = "Selecciona un tiempo atmosférico";
		
		//private Controller _ctrl;
		private RoadMap _map;
		
		//Construcotor:
		public ChangueWeatherDialog() {
			super();
			initGUI();
		}
		
		public ChangueWeatherDialog(Frame parent) {
			super(parent, true);
			initGUI();
		}
			
		//Métodos:
		private void initGUI() {
			_status = 0;
			
			setTitle("Changue Road Weather");
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
			
			_roads = new JComboBox<Road>();
			_roads.setPreferredSize(new Dimension(100, 25));
			
			_timeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
			_timeSpinner.setToolTipText("Tiempo a partir de ahora para programar el evento");
			_timeSpinner.setPreferredSize(new Dimension(80, 25));
			
			_weather = new JComboBox<Weather>();
			_weather.setPreferredSize(new Dimension(80, 25));
			_weather.addItem(Weather.SUNNY);
			_weather.addItem(Weather.WINDY);
			_weather.addItem(Weather.CLOUDY);
			_weather.addItem(Weather.RAINY);
			_weather.addItem(Weather.STORM);
			
			viewPanel.add(new JLabel("Road: "));
			viewPanel.add(_roads);
			viewPanel.add(new JLabel("Weather: "));
			viewPanel.add(_weather);
			viewPanel.add(new JLabel("Ticks: "));
			viewPanel.add(_timeSpinner);
			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					_status = 0;
					ChangueWeatherDialog.this.setVisible(false);					
				}
			});
			buttonsPanel.add(cancelButton);
			
			JButton okButton = new JButton("Ok");
			okButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(_roads.getSelectedItem() != null) {
						_status = 1;	
					}
					else {
						_status = 0;
						JOptionPane.showMessageDialog(new JFrame(), "Ninguna carretera seleccionada.");
					}
					ChangueWeatherDialog.this.setVisible(false);
				}
			});
			buttonsPanel.add(okButton);
			
		}

		public SimulatedObject getRoad() {
			return (SimulatedObject) this._roads.getSelectedItem();
		}
		
		public Weather getWeather() {
			return (Weather) this._weather.getSelectedItem();
		}
		
		public int getTicks() {
			return (int) this._timeSpinner.getValue();
		}

		public int open(RoadMap map) {
			this._map = map;
			
			for (int i = 0; i < _map.getRoads().size(); i++) {
				if(_map.getRoads().get(i) != null) {
					_roads.addItem(_map.getRoads().get(i));
				}
			}

			setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

			setVisible(true);
			return _status;
		}

}
