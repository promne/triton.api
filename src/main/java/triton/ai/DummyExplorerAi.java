package triton.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import triton.api.ClientOrderException;
import triton.api.GameClient;
import triton.domain.Fleet;
import triton.domain.FleetAction;
import triton.domain.FleetOrder;
import triton.domain.Player;
import triton.domain.Star;
import triton.domain.Tech;
import triton.domain.TechnologyType;
import triton.domain.UniverseReport;
import triton.mechanic.Combat;
import triton.mechanic.Distance;
import triton.mechanic.Economy;
import triton.mechanic.FleetManager;
import triton.mechanic.Starfield;
import triton.mechanic.data.DistantPointInSpace;

/**
 * This is really dummy AI exploring empty planets to show that game api just
 * works.
 * 
 * @author georgeh
 *
 */
public class DummyExplorerAi {

	private static final Logger LOG = Logger.getLogger(DummyExplorerAi.class);

	private final GameClient gameClient;

	private int collectShips = 0;
	
	private UniverseReport report;
	private Player currentPlayer;
	
	

	public DummyExplorerAi(GameClient gameClient) {
		super();
		this.gameClient = gameClient;
	}

	public UniverseReport getReport() throws ClientOrderException {
		if (report==null) {
			refreshUniverseReport();
		}
		return report;
	}

	/**
	 * Plays one turn and submits.
	 * 
	 * @param forcePlay
	 *            forces to do move even if the turn is already submitted.
	 * @throws Exception
	 */
	public void play(boolean forcePlay) throws Exception {
		refreshUniverseReport();

		if (!report.getGameOver() && (forcePlay || currentPlayer.getReady() == 0)) {
			colonizeFreePlanets();
			switchResearch();
			
	
			if (Math.round(Math.random()*100)%3==1) {
				collectShips = 5;
			}
			
			if (collectShips > 0) {
				gatherForces();
				collectShips--;
			} else {
				tryAttack();
				scatterFleet();				
			}
			
			buyStuff();
		}

	}

	private void refreshUniverseReport() throws ClientOrderException {
		report = gameClient.getUniverseReport();
		currentPlayer = report.getPlayers().get(report.getCurrentPlayerId());
	}

	private void buyStuff() throws ClientOrderException {
		Collection<Star> currentPlayerStars = Starfield.filterStarsByPlayers(report.getStars().values(), currentPlayer);

		// filter out stars that will be taken
		currentPlayerStars = currentPlayerStars
				.stream()
				.filter(star -> !report.getFleets().values().stream()
						.anyMatch(fleet -> !fleet.getPlayerId().equals(currentPlayer.getId()) && fleet.getOrders().stream().anyMatch(fleetOrder -> fleetOrder.getStarId().equals(star.getId()))))
				.collect(Collectors.toList());

		
		while (currentPlayer.getFleetsCount() < (currentPlayer.getTotalStars()*0.75) && currentPlayer.getCash()>Fleet.FLEET_PRICE) {
			LOG.info("Buying new fleet");
			Optional<Star> randomStarOptional = currentPlayerStars.stream().filter(star -> star.getShipsCount()>1).findAny();
			if (randomStarOptional.isPresent()) {
				Star randomStar = randomStarOptional.get();
				Fleet newFleet = gameClient.buyFleet(randomStar, 1);
				randomStar.setShipsCount(randomStar.getShipsCount()-1);
				report.getFleets().put(newFleet.getId(), newFleet);
				currentPlayer.setCash(currentPlayer.getCash() - Fleet.FLEET_PRICE);
				currentPlayer.setFleetsCount(currentPlayer.getFleetsCount()+ 1);				
			}
		}
		
		
		Integer lastPrice = null;

		do {
			lastPrice = buyScienceStuff(currentPlayerStars);
		} while (lastPrice != null && (lastPrice * 6) < (currentPlayer.getCash()));

		do {
			lastPrice = buyIndustryStuff(currentPlayerStars);
		} while (lastPrice != null && (lastPrice * 4) < (currentPlayer.getCash()));

		do {
			lastPrice = buyEcoStuff(currentPlayerStars);
		} while (lastPrice != null && lastPrice < (currentPlayer.getCash()));

		gameClient.sendBatchOrders();
	}

	private Integer buyEcoStuff(Collection<Star> currentPlayerStars) throws ClientOrderException {
		// find cheapest economy
		Integer terraformingLevel = currentPlayer.getTech().getTerraforming().getLevel();
		Integer lowestPrice = Integer.MAX_VALUE;
		Star starToBuy = null;
		for (Star star : currentPlayerStars) {
			Integer price = Economy.getPriceForStarEconomy(gameClient.getGameConfig(), star, terraformingLevel, star.getEconomyCount() + 1);
			if (starToBuy == null || price.compareTo(lowestPrice) < 0) {
				lowestPrice = price;
				starToBuy = star;
			}
		}

		if (lowestPrice > currentPlayer.getCash()) {
			LOG.debug("Not enough money to buy economy");
			return null;
		}

		LOG.info("Buying economy on " + starToBuy.getName() + " for " + lowestPrice);
		gameClient.buyEconomy(currentPlayer, starToBuy);
		// update my perception of galaxy
		starToBuy.setEconomyCount(starToBuy.getEconomyCount() + 1);
		currentPlayer.setTotalEconomy(currentPlayer.getTotalEconomy() + 1);
		currentPlayer.setCash(currentPlayer.getCash() - lowestPrice);
		return lowestPrice;
	}

	private Integer buyIndustryStuff(Collection<Star> currentPlayerStars) throws ClientOrderException {
		Integer terraformingLevel = currentPlayer.getTech().getTerraforming().getLevel();
		Integer lowestPrice = Integer.MAX_VALUE;
		Star starToBuy = null;
		for (Star star : currentPlayerStars) {
			Integer price = Economy.getPriceForStarIndustry(gameClient.getGameConfig(), star, terraformingLevel, star.getIndustryCount() + 1);
			if (starToBuy == null || price.compareTo(lowestPrice) < 0) {
				lowestPrice = price;
				starToBuy = star;
			}
		}
		if ((lowestPrice * 2) > currentPlayer.getCash()) {
			LOG.debug("Not enough money to buy industry");
			return null;
		}

		LOG.info("Buying industry on " + starToBuy.getName() + " for " + lowestPrice);
		gameClient.buyIndustry(currentPlayer, starToBuy);

		// update my perception of galaxy
		starToBuy.setIndustryCount(starToBuy.getIndustryCount() + 1);
		currentPlayer.setTotalIndustry(currentPlayer.getTotalIndustry() + 1);
		currentPlayer.setCash(currentPlayer.getCash() - lowestPrice);
		return lowestPrice;
	}

	private Integer buyScienceStuff(Collection<Star> currentPlayerStars) throws ClientOrderException {
		Integer terraformingLevel = currentPlayer.getTech().getTerraforming().getLevel();
		Integer lowestPrice = Integer.MAX_VALUE;
		Star starToBuy = null;
		for (Star star : currentPlayerStars) {
			Integer price = Economy.getPriceForStarScience(gameClient.getGameConfig(), star, terraformingLevel, star.getScienceCount() + 1);
			if (starToBuy == null || price.compareTo(lowestPrice) < 0) {
				lowestPrice = price;
				starToBuy = star;
			}
		}

		if ((lowestPrice * 2) > currentPlayer.getCash()) {
			LOG.debug("Not enough money to buy science");
			return null;
		}
		LOG.info("Buying science on " + starToBuy.getName() + " for " + lowestPrice);
		gameClient.buyScience(currentPlayer, starToBuy);

		// update my perception of galaxy
		starToBuy.setScienceCount(starToBuy.getScienceCount() + 1);
		currentPlayer.setTotalScience(currentPlayer.getTotalScience() + 1);
		currentPlayer.setCash(currentPlayer.getCash() - lowestPrice);
		return lowestPrice;
	}

	private void switchResearch() throws ClientOrderException {
		Tech tech = currentPlayer.getTech();

		double travelTimeRange = Distance.getTravelTime(Math.min(tech.getScanning().getValue(), tech.getPropulsion().getValue()), report.getFleetSpeed(), false);
		
		
		if ((travelTimeRange< (gameClient.getGameConfig().getTurnJumpTicks() * 2))) {
			if (tech.getScanning().getLevel().compareTo(tech.getPropulsion().getLevel() + 1) > 0) {
				if (currentPlayer.getResearching() != TechnologyType.PROPULSION) {
					LOG.info("Switching research to propulsion");
					gameClient.setResearching(TechnologyType.PROPULSION);
					gameClient.setResearchingNext(TechnologyType.SCANNING);
				}
			} else {
				if (currentPlayer.getResearching() != TechnologyType.SCANNING) {
					LOG.info("Switching research to scanning");
					gameClient.setResearchingNext(TechnologyType.PROPULSION);
					gameClient.setResearching(TechnologyType.SCANNING);
				}
			}
		} else {
			if (tech.getManufacturing().getLevel() > (tech.getWeapons().getLevel() / 1.3) ) {
				if (currentPlayer.getResearching() != TechnologyType.WEAPONS) {
					LOG.info("Switching research to weapons");
					gameClient.setResearching(TechnologyType.WEAPONS);
					gameClient.setResearchingNext(TechnologyType.MANUFACTURING);
				}
			} else {
				if (currentPlayer.getResearching() != TechnologyType.MANUFACTURING) {
					LOG.info("Switching research to manufacturing");
					gameClient.setResearchingNext(TechnologyType.WEAPONS);
					gameClient.setResearching(TechnologyType.MANUFACTURING);
				}
			}

		}

	}

	private void colonizeFreePlanets() throws ClientOrderException {
		Map<Integer, Star> starmap = report.getStars();
		Collection<Star> currentPlayerStars = Starfield.filterStarsByPlayers(starmap.values(), currentPlayer);

		// don't change orders for these fleets
		Set<Fleet> fleetWithOrders = report.getFleets().values().stream().filter(f -> currentPlayer.getId().equals(f.getPlayerId()) && !f.getOrders().isEmpty()).collect(Collectors.toSet());

		// find star candidates to colonize
		List<DistantPointInSpace<Star>> colonizeCandidatesWithDistance = new ArrayList<>();
		for (Star star : Starfield.filterEmptyStars(starmap.values())) {
			List<Star> playersStarsInRange = Starfield.findStarsInRange(currentPlayerStars, star, currentPlayer.getTech().getPropulsion().getValue());

			// can't go there
			if (playersStarsInRange.isEmpty()) {
				continue;
			}

			// remove the ones I am already traveling to
			if (FleetManager.getFleetsWithStarOnPath(FleetManager.filterFleetsByPlayer(report.getFleets().values(), currentPlayer), star).count() > 0) {
				continue;
			}

			// someone else is already flying there
			if (report.getFleets().values().stream()
					.anyMatch(fleet -> !fleet.getPlayerId().equals(currentPlayer.getId()) && fleet.getOrders().stream().anyMatch(fleetOrder -> fleetOrder.getStarId().equals(star.getId())))) {
				continue;
			}
			;

			colonizeCandidatesWithDistance.add(new DistantPointInSpace<>(star, Distance.getDistance(star, playersStarsInRange.get(0))));
		}

		List<Star> colonizeCandidates = new ArrayList<>();
		for (DistantPointInSpace<Star> distantStar : Distance.sortFromClosest(colonizeCandidatesWithDistance)) {
			colonizeCandidates.add(distantStar.getPointInSpace());
		}

		// figure out, whom to send there
		for (Star emptyStar : colonizeCandidates) {
			LOG.info("Found empty star > " + emptyStar.getName());

			List<Star> playersStarsInRange = Starfield.findStarsInRange(currentPlayerStars, emptyStar, currentPlayer.getTech().getPropulsion().getValue());
			StringBuilder starNames = new StringBuilder();
			for (Iterator<Star> iterator = playersStarsInRange.iterator(); iterator.hasNext();) {
				Star candidate = iterator.next();
				starNames.append(candidate.getName());
				if (iterator.hasNext()) {
					starNames.append(", ");
				}
			}
			LOG.info("Stars in range ordered from closest > " + (playersStarsInRange.isEmpty() ? "NONE" : starNames));
			Star closestStar = playersStarsInRange.get(0);
			Collection<Fleet> fleetsOrbiting = FleetManager.getFleetsOrbitingStar(report.getFleets().values(), closestStar);

			// remove the ones with orders
			fleetsOrbiting.removeAll(fleetWithOrders);

			Fleet discoveryFleet = null;
			if (fleetsOrbiting.size() > 0) {
				// simply get random/first
				discoveryFleet = fleetsOrbiting.iterator().next();
			} else {
				if (currentPlayer.getCash() > Fleet.FLEET_PRICE) {
					if (closestStar.getShipsCount() > 0) {
						discoveryFleet = gameClient.buyFleet(closestStar, 1);
						// update my perception of galaxy
						report.getFleets().put(discoveryFleet.getId(), discoveryFleet);
						closestStar.setShipsCount(closestStar.getShipsCount()-1);
						currentPlayer.setFleetsCount(currentPlayer.getFleetsCount()+ 1);
						currentPlayer.setCash(currentPlayer.getCash() - Fleet.FLEET_PRICE);
					}
				}
			}

			if (discoveryFleet != null) {
				List<FleetOrder> discoveryOrders = new ArrayList<>();
				discoveryOrders.add(new FleetOrder(emptyStar, FleetAction.DO_NOTHING, 0));
				discoveryFleet.setOrders(discoveryOrders);
				gameClient.setFleetOrders(discoveryFleet);
				fleetWithOrders.add(discoveryFleet);
			} else {
				LOG.info("Can't send ship to > " + emptyStar.getName());
			}
		}

	}

	private void gatherForces() throws ClientOrderException {
		Collection<Star> currentPlayerStars = Starfield.filterStarsByPlayers(report.getStars().values(), currentPlayer);
		Collection<Star> othersStars = report.getStars().values().stream().filter(star -> !currentPlayer.getId().equals(star.getPlayerId()) && star.getPlayerId() >= 0).collect(Collectors.toSet());
		Collection<Fleet> currentPlayerFleets = FleetManager.filterFleetsByPlayer(report.getFleets().values(), currentPlayer);
		
		Star jumpStar = null;
		double distance = Double.MAX_VALUE;
		
		for (Star jumpCandidate : currentPlayerStars) {
			for (Star targetCandidate : othersStars) {
				double candidateDistance = Distance.getDistance(jumpCandidate, targetCandidate);
				if (candidateDistance<distance) {
					jumpStar = jumpCandidate;
					distance = candidateDistance;
				}
			}
		}
		
		if (jumpStar==null) {
			collectShips=0;
			return;
		}
		
		for (Fleet fleet : currentPlayerFleets) {

			if (fleet.getOrbitingStarId()!=null) {
				Star supportingStar = report.getStars().get(fleet.getOrbitingStarId());
				if (supportingStar.getShipsCount()>1 && Distance.getDistance(jumpStar, supportingStar) < currentPlayer.getTech().getPropulsion().getValue()) {
					LOG.info(String.format("Sending support ship \"%s\" from \"%s\" to \"%s\"", fleet.getName(), supportingStar.getName(), jumpStar.getName()));
					
					List<FleetOrder> fleetOrders = new ArrayList<>();
					fleetOrders.add(new FleetOrder(jumpStar, FleetAction.DROP_ALL,0));
					fleetOrders.add(new FleetOrder(supportingStar, FleetAction.DO_NOTHING,0));
					fleet.setOrders(fleetOrders);					
					gameClient.shipTransfer(fleet, supportingStar.getShipsCount());
					supportingStar.setShipsCount(0);
					gameClient.setFleetOrders(fleet);					
				}
			}
		}
		
	}
	
	
	private void scatterFleet() throws ClientOrderException {
		Collection<Star> currentPlayerStars = Starfield.filterStarsByPlayers(report.getStars().values(), currentPlayer);
		Collection<Fleet> currentPlayerFleets = FleetManager.filterFleetsByPlayer(report.getFleets().values(), currentPlayer);

		for (Star star : currentPlayerStars) {
			if (FleetManager.getFleetsOrbitingStar(currentPlayerFleets, star).size() == 0 && FleetManager.getFleetsWithStarOnPath(currentPlayerFleets, star).count() == 0) {
				LOG.info("Found star without fleet > " + star.getName());
				for (Star donorCandidate : Starfield.findStarsInRange(currentPlayerStars, star, currentPlayer.getTech().getPropulsion().getValue())) {
					Optional<Fleet> randomFleet = FleetManager.getFleetsOrbitingStar(currentPlayerFleets, donorCandidate).stream().filter(fleet -> fleet.getOrders().size() == 0).findAny();
					if (randomFleet.isPresent()) {
						Fleet fleet = randomFleet.get();
						fleet.getOrders().add(new FleetOrder(star, FleetAction.DROP_ALL, 0));
						LOG.info("Sending fleet \"" + fleet.getName() + "\" to guard > " + star.getName());
						int shipTransfer = (int) Math.round(donorCandidate.getShipsCount() * Math.random());
						if (shipTransfer > 0) {
							gameClient.shipTransfer(fleet, shipTransfer);
							donorCandidate.setShipsCount(donorCandidate.getShipsCount() - shipTransfer);
						}
						gameClient.setFleetOrders(fleet);
						break;
					}
				}
			}
		}
	}

	private void tryAttack() throws ClientOrderException {
		Collection<Star> currentPlayerStars = Starfield.filterStarsByPlayers(report.getStars().values(), currentPlayer);
		Collection<Star> othersStars = report.getStars().values().stream().filter(star -> !currentPlayer.getId().equals(star.getPlayerId()) && star.getPlayerId() >= 0).collect(Collectors.toSet());
		Collection<Fleet> currentPlayerFleets = FleetManager.filterFleetsByPlayer(report.getFleets().values(), currentPlayer);

		for (Star star : currentPlayerStars) {
			Star weakTarget = null;
			for (Star targetCandidate : Starfield.findStarsInRange(othersStars, star, currentPlayer.getTech().getPropulsion().getValue())) {
				if (!"1".equals(targetCandidate.getVisible())) {
					continue;
				}
				Player targetPlayer = report.getPlayers().get(targetCandidate.getPlayerId());
				if (Combat.getRemainingShipsOnPlanet(star.getShipsCount(), currentPlayer.getTech().getWeapons().getLevel(), targetCandidate.getShipsCount(), targetPlayer.getTech().getWeapons().getLevel()) < 1 &&
						!currentPlayerFleets.stream().anyMatch(fleet -> fleet.getOrders().stream().anyMatch(fleetOrder -> fleetOrder.getStarId().equals(targetCandidate.getId())))) {
					weakTarget = targetCandidate;
					break;
				}
			}
			if (weakTarget != null) {
				Fleet attackFleet = null;
				Optional<Fleet> existingFleet = FleetManager.getFleetsOrbitingStar(currentPlayerFleets, star).stream().filter(fleet -> fleet.getOrders().size() == 0).findAny();
				if (existingFleet.isPresent()) {
					attackFleet = existingFleet.get();
					gameClient.shipTransfer(attackFleet, star.getShipsCount());
					star.setShipsCount(0);
				}
				if (attackFleet == null) {
					if (currentPlayer.getCash() > Fleet.FLEET_PRICE) {
						attackFleet = gameClient.buyFleet(star, star.getShipsCount());
						star.setShipsCount(0);
						currentPlayer.setFleetsCount(currentPlayer.getFleetsCount()+ 1);
						report.getFleets().put(attackFleet.getId(), attackFleet);
						currentPlayer.setCash(currentPlayer.getCash() - Fleet.FLEET_PRICE);
					}
				}
				if (attackFleet != null) {
					LOG.info("Sending attack fleet \"" + attackFleet.getName() + "\" to > " + weakTarget.getName());
					attackFleet.getOrders().add(new FleetOrder(weakTarget, FleetAction.DO_NOTHING, 0));
					gameClient.setFleetOrders(attackFleet);
				}
			}
		}

	}

}
