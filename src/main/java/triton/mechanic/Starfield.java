package triton.mechanic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import triton.domain.Player;
import triton.domain.Star;

public class Starfield {

	public static Collection<Star> filterStarsByPlayers(Collection<Star> stars, Player... players) {
		Set<Integer> playerIds = new HashSet<>();
		for (Player player : players) {
			playerIds.add(player.getId());
		}		
		List<Star> result = new ArrayList<>();
		for (Star star : stars) {
			if (playerIds.contains(star.getPlayerId())) {
				result.add(star);
			}
		}
		return result;
	}

	public static Collection<Star> filterEmptyStars(Collection<Star> stars) {
		List<Star> result = new ArrayList<>();
		for (Star star : stars) {
			if (star.getPlayerId()==-1) {
				result.add(star);
			}
		}
		return result;
	}

	public static List<Star> findStarsInRange(Collection<Star> stars, Star star, Double propulsionValue) {
		return (List)Distance.filterInRange(stars, star, propulsionValue).collect(Collectors.toList());
	}
	
}
