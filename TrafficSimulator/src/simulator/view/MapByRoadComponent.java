package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_SOURCE_COLOR = Color.BLUE;
	//private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	private RoadMap _map;

	private Image _car;
	private Image _sunny;
	private Image _cloudy;
	private Image _rainy;
	private Image _windy;
	private Image _storm;
	private Image _cont0;
	private Image _cont1;
	private Image _cont2;
	private Image _cont3;
	private Image _cont4;
	private Image _cont5;

	MapByRoadComponent(Controller ctrl) {
		setPreferredSize (new Dimension (300, 200));
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		_car = loadImage("car.png");
		_sunny = loadImage("sun.png");
		_cloudy = loadImage("cloud.png");
		_rainy = loadImage("rain.png");
		_windy = loadImage("wind.png");
		_storm = loadImage("storm.png");
		_cont0 = loadImage("cont_0.png");
		_cont1 = loadImage("cont_1.png");
		_cont2 = loadImage("cont_2.png");
		_cont3 = loadImage("cont_3.png");
		_cont4 = loadImage("cont_4.png");
		_cont5 = loadImage("cont_5.png");
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		drawRoads(g);
		drawVehicles(g);
		//drawJunctions(g);
	}

	private void drawRoads(Graphics g) {
		for (int i = 0; i < _map.getRoads().size(); i++) {
			
			// the road goes from (x1,y1) to (x2,y2)
			int x1 = _map.getRoads().get(i).getJSoruce().getX();
			int y1 = _map.getRoads().get(i).getJSoruce().getY();
			int x2 = _map.getRoads().get(i).getJDest().getX();
			int y2 = _map.getRoads().get(i).getJDest().getY();

			// choose a color for the arrow depending on the traffic light of the road
			Color arrowColor = _RED_LIGHT_COLOR;
			int idx = _map.getRoads().get(i).getJDest().getGreenLightIndex();
			if (idx != -1 && _map.getRoads().get(i).equals(_map.getRoads().get(i).getJDest().getInRoads().get(idx))) {
				arrowColor = _GREEN_LIGHT_COLOR;
			}

			// choose a color for the road depending on the total contamination, the darker
			// the
			// more contaminated (wrt its co2 limit)
			int roadColorValue = 200 - (int) (200.0 * Math.min(1.0, (double) _map.getRoads().get(i).getTotalCO2() / (1.0 + (double) _map.getRoads().get(i).getCO2Limit())));
			Color roadColor = new Color(roadColorValue, roadColorValue, roadColorValue);

			// draw line from (x1,y1) to (x2,y2) with arrow of color arrowColor and line of
			// color roadColor. The size of the arrow is 15px length and 5 px width
			drawLine(g, x1, y1, x2, y2, 15, 5, roadColor, arrowColor, i);
		}

	}

	private void drawVehicles(Graphics g) {
		for (int i = 0; i < this._map.getVehicles().size(); i++) {
			if (this._map.getVehicles().get(i).getStatus() != VehicleStatus.ARRIVED) {
				
				// The calculation below compute the coordinate (vX,vY) of the vehicle on the
				// corresponding road. It is calculated relativly to the length of the road, and
				// the location on the vehicle.
				Road r = this._map.getVehicles().get(i).getRoad();
				int numRoad = Integer.parseInt(r.getId().substring(1));

				int x1 = 50;
				int y1 = 50;
				int x2 = getWidth()-100;
				int y2 = 50;
				double roadLength = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
				double alpha = Math.atan(((double) Math.abs(x1 - x2)) / ((double) Math.abs(y1 - y2)));
				double relLoc = roadLength * ((double) this._map.getVehicles().get(i).getLocation()) / ((double) r.getLenght());
				@SuppressWarnings("unused")
				double x = Math.sin(alpha) * relLoc;
				double y = Math.cos(alpha) * relLoc;
				//int xDir = x1 < x2 ? 1 : -1;
				int yDir = y1 < y2 ? 1 : -1;

				int vX = (int) (x = x1 + (int) ((x2 - x1) * ((double) this._map.getVehicles().get(i).getLocation() / (double) r.getLenght())));
				int vY = (y1 + yDir * ((int) y)) * numRoad;

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) this._map.getVehicles().get(i).getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car (with circle as background) and it identifier
				g.fillOval(vX - 1, vY - 6, 14, 14);
				g.drawImage(_car, vX, vY - 6, 16, 16, this);
				g.drawString(this._map.getVehicles().get(i).getId(), vX, vY - 6);
			}
		}
	}

	/*
	private void drawJunctions(Graphics g) {	
	 for (int i = 0; i < _map.getJunctions().size(); i++) {

			// (x,y) are the coordinates of the junction
			int x = 50;
			int y = (i+1)*50;

			// draw a circle with center at (x,y) with radius _JRADIUS
			g.setColor(_JUNCTION_SOURCE_COLOR);
			g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

			// draw the junction's identifier at (x,y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(_map.getJunctions().get(i).getId(), x, y);
		}
	}
	*/

	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		//setPreferredSize(new Dimension(maxW, maxH));
		//setSize(new Dimension(maxW, maxH));
		if (maxW > getWidth() || maxH > getHeight()) {
		    setPreferredSize(new Dimension(maxW, maxH));
		    setSize(new Dimension(maxW, maxH));
		}

	}

	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line
	private void drawLine(//
			Graphics g, //
			int x1, int y1, //
			int x2, int y2, //
			int w, int h, //
			Color lineColor, Color arrowColor, int i) {
		
		g.setColor(Color.BLACK);
		g.drawString(_map.getRoads().get(i).getId(), 30, (i+1)*50);
		
		/*
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };
		*/

		g.setColor(lineColor);
		//g.drawLine(x1, y1, x2, y2);
		g.drawLine(50, (i+1)*50, getWidth()-100, (i+1)*50);
		g.setColor(arrowColor);
		//g.fillPolygon(xpoints, ypoints, 3);
		g.setColor(_JUNCTION_SOURCE_COLOR);
		g.fillOval(50, (i+1)*48, 9, 9);
		int idx = _map.getRoads().get(i).getJDest().getGreenLightIndex();
		if (idx != -1 && _map.getRoads().get(i).equals(_map.getRoads().get(i).getJDest().getInRoads().get(idx))) {
			g.setColor(_GREEN_LIGHT_COLOR);
		}
		else {
			g.setColor(_RED_LIGHT_COLOR);
		}
		g.fillOval(getWidth()-100, (i+1)*48, 9, 9);
		
		switch (_map.getRoads().get(i).getWeather()) {
		case SUNNY:
			g.drawImage(_sunny, getWidth()-90, (i+1)*45, 32, 32, this);
			break;
		case CLOUDY:
			g.drawImage(_cloudy, getWidth()-90, (i+1)*45, 32, 32, this);
			break;
		case RAINY:
			g.drawImage(_rainy, getWidth()-90, (i+1)*45, 32, 32, this);
			break;
		case WINDY:
			g.drawImage(_windy, getWidth()-90, (i+1)*45, 32, 32, this);
			break;
		case STORM:
			g.drawImage(_storm, getWidth()-90, (i+1)*45, 32, 32, this);
			break;
		}
				
		switch ((int) Math.floor(Math.min((double)_map.getRoads().get(i).getTotalCO2()/(1.0 + (double)_map.getRoads().get(i).getCO2Limit()),1.0) / 0.19)) {
		case 0:
			g.drawImage(_cont0, getWidth()-50, (i+1)*45, 32, 32, this);
			break;
		case 1:
			g.drawImage(_cont1, getWidth()-50, (i+1)*45, 32, 32, this);
			break;
		case 2:
			g.drawImage(_cont2, getWidth()-50, (i+1)*45, 32, 32, this);
			break;
		case 3:
			g.drawImage(_cont3, getWidth()-50, (i+1)*45, 32, 32, this);
			break;
		case 4:
			g.drawImage(_cont4, getWidth()-50, (i+1)*45, 32, 32, this);
			break;
		case 5:
			g.drawImage(_cont5, getWidth()-50, (i+1)*45, 32, 32, this);
			break;
		}		
	}

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		_map = map;
		repaint();
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
	}

}
