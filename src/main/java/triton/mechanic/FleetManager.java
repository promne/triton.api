package triton.mechanic;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import triton.domain.Fleet;
import triton.domain.Player;
import triton.domain.Star;

public class FleetManager {

	public static Collection<Fleet> getFleetsOrbitingStar(Collection<Fleet> fleets, Star star) {
		return fleets.stream().filter( f -> star.getId().equals(f.getOrbitingStarId())).collect(Collectors.toList());
	}

	public static Stream<Fleet> getFleetsWithStarOnPath(Collection<Fleet> fleets, Star star) {
		return fleets.stream().filter(fleet -> fleet.getOrders().stream().anyMatch(order -> star.getId().equals(order.getStarId())));
	}	

	public static Collection<Fleet> filterFleetsByPlayer(Collection<Fleet> fleets, Player player) {
		return fleets.stream().filter( f -> player.getId().equals(f.getPlayerId())).collect(Collectors.toList());
	}	
	
	public static Collection<Star> getStarsOnPath(Fleet fleet, Map<Integer, Star> stars) {
		return fleet.getOrders().stream().map(o -> stars.get(o.getStarId())).distinct().collect(Collectors.toList());
	}
}
