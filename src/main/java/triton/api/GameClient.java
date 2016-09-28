package triton.api;

import java.io.Closeable;

import triton.domain.Fleet;
import triton.domain.GameConfig;
import triton.domain.Player;
import triton.domain.Star;
import triton.domain.TechnologyType;
import triton.domain.UniverseReport;

/**
 * Interface allowing to interact with game server.
 * 
 * @author georgeh
 *
 */
public interface GameClient extends Closeable {

	/**
	 * Sends orders that are in the batch.
	 * 
	 * @throws ClientOrderException
	 */
	public abstract void sendBatchOrders() throws ClientOrderException;

	/**
	 * Returns actual intel report.
	 * 
	 * @throws ClientOrderException
	 */
	public abstract void getIntelReport() throws ClientOrderException;

	/**
	 * Returns actual universe state.
	 * 
	 * @return
	 * @throws ClientOrderException
	 */
	public abstract UniverseReport getUniverseReport() throws ClientOrderException;

	/**
	 * Send fleet orders.
	 * 
	 * @param fleet
	 * @throws ClientOrderException
	 */
	public abstract void setFleetOrders(Fleet fleet) throws ClientOrderException;

	/**
	 * Clear fleet orders. Can be achieved as well by {@link #setFleetOrders(Fleet)}
	 * 
	 * @param fleet
	 * @throws ClientOrderException
	 */
	public abstract void clearFleetOrders(Fleet fleet) throws ClientOrderException;

	/**
	 * Set looping for fleet.
	 * 
	 * @param fleet
	 * @param loop
	 * @throws ClientOrderException
	 */
	public abstract void setFleetLoop(Fleet fleet, boolean loop) throws ClientOrderException;

	/**
	 * Transfer ships to fleet from currently orbiting star.
	 * 
	 * @param fleet
	 * @param shipCount Number of ships fleet should have
	 * @throws ClientOrderException
	 */
	public abstract void shipTransfer(Fleet fleet, int shipCount) throws ClientOrderException;

	/**
	 * Submit turn
	 * 
	 * @return
	 * @throws ClientOrderException
	 */
	public abstract UniverseReport submitTurn() throws ClientOrderException;

	/**
	 * Buy a fleet and transfers ship from star on it.
	 * 
	 * @param star
	 * @param shipCount
	 * @return
	 * @throws ClientOrderException
	 */
	public abstract Fleet buyFleet(Star star, int shipCount) throws ClientOrderException;

	/**
	 * Buy economy on planet.
	 * 
	 * @see #sendBatchOrders()
	 * 
	 * @param player
	 * @param star
	 */
	public abstract void buyEconomy(Player player, Star star);

	/**
	 * Buy industry on planet.
	 * 
	 * @see #sendBatchOrders()
	 * 
	 * @param player
	 * @param star
	 */
	public abstract void buyIndustry(Player player, Star star);

	/**
	 * Buy science on planet.
	 * 
	 * @see #sendBatchOrders()
	 * 
	 * @param player
	 * @param star
	 */
	public abstract void buyScience(Player player, Star star);

	/**
	 * Set goal for research.
	 * 
	 * @param technology
	 * @throws ClientOrderException
	 */
	public abstract void setResearching(TechnologyType technology) throws ClientOrderException;

	/**
	 * Set goal for next research.
	 * 
	 * @param technology
	 * @throws ClientOrderException
	 */
	public abstract void setResearchingNext(TechnologyType technology) throws ClientOrderException;

	/**
	 * Returns game configuration.
	 * 
	 * @return
	 * @throws ClientOrderException
	 */
	public abstract GameConfig getGameConfig() throws ClientOrderException;

}