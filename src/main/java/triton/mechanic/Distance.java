package triton.mechanic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import triton.domain.PointInSpace;
import triton.domain.Star;
import triton.mechanic.data.DistantPointInSpace;

public class Distance {

	public static double getDistance(PointInSpace point1, PointInSpace point2) {
		double disX = point1.getPosX() - point2.getPosX();
		double disY = point1.getPosY() - point2.getPosY();
		return Math.sqrt(disX * disX + disY * disY);
	}

	/**
	 * In light years
	 * 
	 * @param star1
	 * @param star2
	 * @return
	 */
	public static double getDistanceLY(double distance) {
		return 8 * distance;
	}

	/**
	 * .. in hours
	 * 
	 * @param distance
	 * @param fleetSpeed
	 * @param warp
	 * @return
	 */
	public static double getTravelTime(double distance, double fleetSpeed, boolean warp) {
		return Math.floor(distance / (fleetSpeed * (warp ? 3 : 1))) + 1;
	}

	/**
	 * Filters points by range and sorts them by closest first.
	 * 
	 * @param stars
	 * @param star
	 * @param propulsionValue
	 * @return
	 */
	public static Stream<PointInSpace> filterInRange(Collection<? extends PointInSpace> stars, Star star, Double propulsionValue) {
		final Map<Integer, DistantPointInSpace<PointInSpace>> distances = new HashMap<>();
		List<PointInSpace> result = new ArrayList<PointInSpace>();

		for (PointInSpace candidate : stars) {
			double starDistance = getDistance(star, candidate);
			if (starDistance < propulsionValue) {
				distances.put(candidate.getId(), new DistantPointInSpace<>(candidate, starDistance));
				result.add(candidate);
			}
		}

		Collections.sort(result, new Comparator<PointInSpace>() {
			public int compare(PointInSpace o1, PointInSpace o2) {
				return distances.get(o1.getId()).getDistance().compareTo(distances.get(o2.getId()).getDistance());
			}
		});
		return result.stream();
	}

	public static <T extends PointInSpace> List<DistantPointInSpace<T>> sortFromClosest(Collection<DistantPointInSpace<T>> points) {
		return points.stream().sorted((o1, o2) -> o1.getDistance().compareTo(o2.getDistance())).collect(Collectors.toList());
	}

	public static <T extends PointInSpace> List<DistantPointInSpace<T>> sortFromClosest(Collection<T> points, PointInSpace from) {
		return points.stream().map(point -> new DistantPointInSpace<>(point, Distance.getDistance(from, point))).sorted((o1, o2) -> o1.getDistance().compareTo(o2.getDistance())).collect(Collectors.toList());
	}
	
}
