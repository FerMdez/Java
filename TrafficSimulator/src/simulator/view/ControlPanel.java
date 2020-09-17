package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulator.control.Controller;
import simulator.exceptions.UnsupportedFileException;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.NewSetWeatherEvent;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Atributos:
	private JButton loadFile; //Abre un diálogo para seleccionar un fichero de eventos (utilizando JFileChooser).
	private JButton saveFile; //Abre un diálogo para guardar el estado actual (utilizando JFileChooser).
	private JButton changeVehicleContClass; //Abre una ventana de diálogo donde el usuario puede seleccionar un vehículo V, una clase de contaminación C (0-10) y un número de ticks N.
	private JButton changeRoadWeather; //Abre una ventana de diálogo donde el usuario puede seleccionar una carretera R, unas condiciones atmosféricas W, y un número de ticks N
	private JButton run; //Ejecuta el simulador.
	private JButton stop; //Para el simulador.
	private JButton restart; //Reinicia el simulador desde el archivo cargado previamente.
	private JButton exit; //Sale del simulador.
	
	private JSpinner nTicks; //Número de veces que se ejecuta la simulación.
	private JLabel ticks; //Barra para mostrar el número de ticks de la simulación.
	
	private JToolBar toolbar; //Barra de herramientas.
	private JFileChooser fileChooser; //Selector de archivos.
	
	private Controller _ctrl;
	private RoadMap _map;
	private int _time;
	private static boolean _stopped = true;
	private int _nTicks = 10;

	private File file;
	
	
	//Constructor:
	public ControlPanel(Controller ctrl) {
		super();
		this._ctrl = ctrl;
		this._nTicks = 0;
		this.file = null;
		this.InitGUI();
		ctrl.addObserver(this);
	}
	
	//Métodos:
	private void InitGUI() {
		
		//Inicia el panel de control:
		this.toolbar = new JToolBar();
		this.setLayout(new BorderLayout());
		this.add(toolbar, BorderLayout.PAGE_START);
		
		//Añade la carga de ficheros:
		this.initLoadButton();
		//this.toolbar.addSeparator();
		
		//Añade el guardado de la simulación: (no se pide en la práctica, lo añado como parte opcional)
		this.initSaveButton();
		this.toolbar.addSeparator();
								
		//Añade el botón de cambio de clase de contaminación del vehículo:
		this.initChangeVehicleContClassButton();
		this.toolbar.addSeparator();
		
		//Añade el botón de cambio de condiciones atmosféricas en la carretera:
		this.initChangeRoadWeather();
		this.toolbar.addSeparator();
		
		//Añade el botón de incio:
		this.initRunButton();
		
		//Añade el botón de parada:
		this.initStopButton();
		
		//Añade el botón de reinicio: (no se pide en la práctica, lo añado como parte opcional)
		this.initRestartButton();
		this.toolbar.addSeparator();
		
		//Añade el número de ticks de la simulación:
		this.ticks = new JLabel();
		this.ticks.setText("Ticks: ");
		this.toolbar.add(this.ticks);
		this.initTicksSpinner();
		this.toolbar.addSeparator();
		
		//Añade el botón de salir:
		this.initExitButton();

	}

	private void initLoadButton() {
		this.fileChooser = new JFileChooser();
		this.fileChooser.setDialogTitle("Elige el archivo de eventos para la simulación");
		this.fileChooser.setCurrentDirectory(new File("resources/examples/"));
		this.loadFile = new JButton();
		this.loadFile.setToolTipText("Carga el archivo de eventos de la simulación");
		this.loadFile.setIcon(new ImageIcon(this.getClass().getResource("/icons/open.png")));
		
		this.loadFile.addActionListener(new ActionListener() {	
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choseF = fileChooser.showOpenDialog(fileChooser);
				
				if (choseF == JFileChooser.APPROVE_OPTION) {
					System.out.println("Archivo seleccionado: " + fileChooser.getSelectedFile());
					JOptionPane.showMessageDialog(fileChooser, "Has seleccionado el archivo: " + fileChooser.getSelectedFile());
					
					//File file = fileChooser.getSelectedFile();
					file = fileChooser.getSelectedFile();
					InputStream input;
					try {
						input = new FileInputStream(file);
						if(input != null) {
							_ctrl.reset();
							try {
								_ctrl.loadEvents(input);
							} catch (UnsupportedFileException e2) {
								onError("Algo salió mal al cargar el archivo: " +  e2.getMessage());
							}
						}
						try {
							input.close();
						} catch (IOException e1) {
							onError("Algo salió mal al cerrar el archivo: " + e1.getMessage());
						}
					} catch (FileNotFoundException ex) {
						onError("Algo salió mal al cargar el archivo: " +  ex.getMessage());
					}
				} 
				else {
					System.out.println("No se ha cargado ningún archivo.");
					JOptionPane.showMessageDialog(fileChooser, "No se ha cargado ningún archivo.");
				}
								
			}
			
		});		
		
		this.toolbar.add(this.loadFile);
	}
	
	//Añadido como parte opcional:
	private void initSaveButton() {
		this.fileChooser = new JFileChooser();
		this.fileChooser.setDialogTitle("Guarda el estado actual de la simulación");
		this.fileChooser.setCurrentDirectory(new File("resources/examples/"));
		this.saveFile = new JButton();
		this.saveFile.setToolTipText("Guarda en un archivo el estado actual del simulador");
		this.saveFile.setIcon(new ImageIcon(this.getClass().getResource("/icons/save.png")));
		
		this.saveFile.addActionListener(new ActionListener() {	
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choseF = fileChooser.showSaveDialog(fileChooser);
				
				if (choseF == JFileChooser.APPROVE_OPTION) {
					System.out.println("Estado guardado: " + fileChooser.getSelectedFile());
					JOptionPane.showMessageDialog(fileChooser, "Estado guardado en el archivo: " + fileChooser.getSelectedFile());
					
					File saveFile = fileChooser.getSelectedFile();
					OutputStream output;
					try {
						output = new FileOutputStream(saveFile + ".json");
						if(output != null) {
							_ctrl.save(output);
						}
						try {
							output.close();
						} catch (IOException e1) {
							onError("Algo salió mal al cerrar el archivo: " + e1.getMessage());
						}
					} catch (FileNotFoundException ex) {
						onError("Algo salió mal al guardar el archivo: " +  ex.getMessage());
					}
				} 
				else {
					System.out.println("No se ha guardado el archivo.");
					JOptionPane.showMessageDialog(fileChooser, "No se ha guardado el archivo.");
				}
								
			}
			
		});		
		
		this.toolbar.add(this.saveFile);
	}

	private void initChangeVehicleContClassButton() {
		this.changeVehicleContClass = new JButton();
		this.changeVehicleContClass.setToolTipText("Cambia la clase de contaminación del vehículo.");
		this.changeVehicleContClass.setIcon(new ImageIcon(this.getClass().getResource("/icons/co2class.png")));
		
		this.changeVehicleContClass.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				select_CO2Class();
			}
		});
		
		this.toolbar.add(this.changeVehicleContClass);
	}
	
	protected void select_CO2Class() {
		ChangueCO2Dialog dialog = new ChangueCO2Dialog ((Frame) SwingUtilities.getWindowAncestor(this));
		int status = dialog.open(_map);
		
		if (status == 0) {
			System.out.println("Evento de cambio de contaminación de vehículo, cancelado.");
			//JOptionPane.showMessageDialog(new JFrame(), "Cancelado");
		}
		else {
			List<Pair<String, Integer>> cs = new ArrayList<>();
			cs.add(new Pair<String, Integer>((dialog.getVehicle()).getId(), dialog.getCO2Class()));
			try {
				_ctrl.addEvent(new NewSetContClassEvent(_time + dialog.getTicks(), cs));
				System.out.println("Nuevo evento de cambio de contaminación de vehículo añadido.");
			} catch (Exception ex) {
				onError("Algo salió mal: " + ex.getLocalizedMessage());
			}	
		}
		
	}

	private void initChangeRoadWeather() {
		this.changeRoadWeather = new JButton();
		this.changeRoadWeather.setToolTipText("Cambia el tiempo atmosférico de la carretera.");
		this.changeRoadWeather.setIcon(new ImageIcon(this.getClass().getResource("/icons/weather.png")));
		
		this.changeRoadWeather.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				select_Weather();
			}
		});		
		
		this.toolbar.add(this.changeRoadWeather);
	}
	
	protected void select_Weather() {
		ChangueWeatherDialog dialog = new ChangueWeatherDialog ((Frame) SwingUtilities.getWindowAncestor(this));
		int status = dialog.open(_map);
		
		if (status == 0) {
			System.out.println("Evento de cambio de tiempo atmosférico, cancelado.");
			//JOptionPane.showMessageDialog(new JFrame(), "Cancelado");
		}
		else {
			List<Pair<String, Weather>> ws = new ArrayList<>();
			ws.add(new Pair<String, Weather>((dialog.getRoad()).getId(), dialog.getWeather()));
			try {
				_ctrl.addEvent(new NewSetWeatherEvent(_time + dialog.getTicks(), ws));
				System.out.println("Nuevo evento de cambio de tiempo atmosfético añadido.");
			} catch (Exception ex) {
				onError("Algo salió mal: " + ex.getLocalizedMessage());
			}	
		}
	}

	private void initRunButton() {
		this.run = new JButton();
		this.run.setToolTipText("Ejecuta el simulador.");
		this.run.setIcon(new ImageIcon(this.getClass().getResource("/icons/run.png")));	
		
		this.run.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = false;
				enableToolBar(false);
				run_sim(_nTicks);
			}
		});		
		
		this.toolbar.add(this.run);
	}
	
	private void initStopButton() {
		this.stop = new JButton();
		this.stop.setToolTipText("Para la simulación.");
		this.stop.setIcon(new ImageIcon(this.getClass().getResource("/icons/stop.png")));
		
		this.stop.addActionListener(new ActionListener() {	
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				stop();	
			}	
		});
		
		this.toolbar.add(this.stop);
	}
	
	//Añadido como parte opcional:
	private void initRestartButton() {
		this.restart = new JButton();
		this.restart.setToolTipText("Reinicia la simulación.");
		this.restart.setIcon(new ImageIcon(this.getClass().getResource("/icons/reset.png")));
		
		this.restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(file != null) {
					InputStream input = null;
					try {
						input = new FileInputStream(file);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					_ctrl.reset();
					try {
						_ctrl.loadEvents(input);
					} catch (UnsupportedFileException e2) {
						onError("Algo salió mal al cargar el archivo: " +  e2.getMessage());
					}
					System.out.println("Reiniciado el simulador con el archivo: " + file);
					JOptionPane.showMessageDialog(fileChooser, "Se ha reiniciado el simulador.");
					try {
						input.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					onError("Error al reiniciar el simulador: No se ha cargado ningún archivo.");
				}
			}
		});
		
		this.toolbar.add(this.restart);
	}
	
	private void initExitButton() {
		this.exit = new JButton();
		this.exit.setToolTipText("Sale del simulador.");
		this.exit.setIcon(new ImageIcon(this.getClass().getResource("/icons/exit.png")));
		
		this.exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres salir?", "SALIR", JOptionPane.YES_NO_OPTION);
				if (opt == JOptionPane.YES_OPTION) {
					System.out.println("Fin de la simulación.");
					System.exit(0);
				}
			}
		});
		
		this.toolbar.add(Box.createHorizontalGlue());
		this.toolbar.add(this.exit);
		
	}
	
	private void initTicksSpinner() {
		SpinnerNumberModel model = new SpinnerNumberModel(1, 0, 10000, 1);
		this.nTicks = new JSpinner(model);
		this.nTicks.setValue(0);
		this.nTicks.setMinimumSize(new Dimension(70, 20));
		this.nTicks.setMaximumSize(new Dimension(200, 20));
		this.nTicks.setPreferredSize(new Dimension(70, 20));
		this.nTicks.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				_nTicks = Integer.valueOf(nTicks.getValue().toString());
			}
		});
		
		this.toolbar.add(this.nTicks);
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				this._ctrl.run(1);
			} catch (Exception e) {
			// TODO show error message
			e.getCause();
			//e.getMessage("Error al ejecutar el simulador.");
			_stopped = true;
			return;
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} 
		else {
			enableToolBar(true);
			//this.toolbar.setEnabled(true);
			_stopped = true;
		}
	}

	private void stop() {
		_stopped = true;
	}
	
	private void enableToolBar(boolean set) {
		this.loadFile.setEnabled(set);
		this.saveFile.setEnabled(set);
		this.changeVehicleContClass.setEnabled(set);
		this.changeRoadWeather.setEnabled(set);
		this.run.setEnabled(set);
		this.restart.setEnabled(set);
		this.exit.setEnabled(set);
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._map = map;
		this._time = time;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;
	}

	@Override
	public void onError(String err) {
		System.err.println(err);
		JOptionPane.showMessageDialog(new JFrame(), err);
	}

}
