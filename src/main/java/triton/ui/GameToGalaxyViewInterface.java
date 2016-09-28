package triton.ui;

import java.util.Collection;

import triton.domain.Fleet;
import triton.domain.Player;
import triton.domain.Star;

/**
 * Interface between UI and game client/ai
 * 
 * @author a
 *
 */
public interface GameToGalaxyViewInterface {
	void playOneRound();
	Collection<Player> getPlayers();
	Collection<Star> getStars();
	Collection<Fleet> getFleets();
	/**
	 * 
	 * @return !gameOver && playerIsReady
	 */
	boolean isTurnReady();
}