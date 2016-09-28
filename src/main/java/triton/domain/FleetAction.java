package triton.domain;

public enum FleetAction {

	DO_NOTHING(0),
	COLLECT_ALL(1),
	DROP_ALL(2),
	COLLECT(3),
	DROP(4),
	COLLECT_ALL_BUT(5),
	DROP_ALL_BUT(6),
	GARRISON_STAR(7);
	
	final int id;
	
	private FleetAction(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static FleetAction getFleetAction(int id) {
		for (FleetAction fleetAction : values()) {
			if (fleetAction.id==id) {
				return fleetAction;
			}
		}
		throw new IllegalArgumentException("Unexpected value: " + id);
	}
	
}
