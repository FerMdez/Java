package simulator.model;

public class NewJunctionEvent extends Event {
	//Atributos:
	protected String id; 
	protected LightSwitchingStrategy lsStrategy; 
	protected DequeuingStrategy dqStrategy;
	protected int xCoor, yCoor;

	public NewJunctionEvent(int time, String id, LightSwitchingStrategy
			lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(time);
		this.id = id;
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}


	@Override
	void execute(RoadMap map) {
		map.addJunction(new Junction(this.id, this.lsStrategy, this.dqStrategy, this.xCoor, this.yCoor));
	}


	@Override
	public String toString() {
		return "New Junction '"+id+"'";
	}

}
