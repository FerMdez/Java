package simulator.model;

public abstract class NewRoadEvent extends Event {
	//Atributos:
	protected String id;
	String srcJun, destJunc;
	protected int length, co2limit, maxSpeed;
	protected Weather weather;

	public NewRoadEvent(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.id = id;
		this.srcJun = srcJun;
		this.destJunc = destJunc;
		this.length = length;
		this.co2limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}
		
	abstract void execute(RoadMap map);

}
