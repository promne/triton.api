package triton.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FleetOrder {

	private Integer starId;
	
	private FleetAction action;
	
	private Integer shipsCount;

	public FleetOrder() {
		super();
	}

	public FleetOrder(Star star, FleetAction action, int shipsCount) {
		super();
		this.starId = star.getId();
		this.action = action;
		this.shipsCount = shipsCount;
	}

	public Integer getStarId() {
		return starId;
	}

	public void setStarId(Integer starId) {
		this.starId = starId;
	}

	public FleetAction getAction() {
		return action;
	}

	public void setAction(FleetAction order) {
		this.action = order;
	}

	public Integer getShipsCount() {
		return shipsCount;
	}

	public void setShipsCount(Integer shipsCount) {
		this.shipsCount = shipsCount;
	}
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
