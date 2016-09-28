package triton.mechanic.data;

import triton.domain.PointInSpace;

public class DistantPointInSpace<T extends PointInSpace> {
	private final T pointInSpace;
	private Double distance;

	public DistantPointInSpace(T pointInSpace, double distance) {
		super();
		this.pointInSpace = pointInSpace;
		this.distance = distance;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public T getPointInSpace() {
		return pointInSpace;
	}

}
