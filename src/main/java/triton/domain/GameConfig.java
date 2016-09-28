package triton.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameConfig {

	@Expose
	private Integer researchCostExperimentation;
	@Expose
	private Integer darkGalaxy;
	@Expose
	private Integer developmentCostIndustry;
	@Expose
	private Integer researchCostManufacturing;
	@Expose
	private Integer startingTechHyperspace;
	@Expose
	private String starScatter;
	@Expose
	private Integer developmentCostEconomy;
	@Expose
	private Integer startingTechTerraforming;
	@Expose
	private Integer anonymity;
	@Expose
	private Integer startingStars;
	@Expose
	private Integer alliances;
	@Expose
	private Integer researchCostWeapons;
	@Expose
	private Integer researchCostHyperspace;
	@Expose
	private Integer researchCostScanning;
	@Expose
	private Integer starsForVictory;
	@Expose
	private Integer naturalResources;
	@Expose
	private Integer startingTechManufacturing;
	@Expose
	private String customStarfield;
	@Expose
	private Integer startingTechExperimentation;
	@Expose
	private Integer startingCash;
	@Expose
	private Integer turnTime;
	@Expose
	private Integer randomGates;
	@Expose
	private Integer startingInfEconomy;

	@SerializedName("non_default_settings")
	private List<String> nonDefaultSettings = new ArrayList<String>();
	
	@Expose
	private Integer tickRate;
	@Expose
	private Integer buildGates;
	@Expose
	private String description;
	@Expose
	private Integer startingInfIndustry;
	@Expose
	private Integer startingInfScience;
	@Expose
	private Integer homeStarDistance;
	@Expose
	private Integer startingTechWeapons;
	@Expose
	private Integer starsPerPlayer;
	@Expose
	private Integer startingTechBanking;
	@Expose
	private String password;
	@Expose
	private Integer researchCostBanking;
	@Expose
	private String name;
	@Expose
	private Integer developmentCostScience;
	@Expose
	private Integer playerType;
	@Expose
	private String adminUserId;
	@Expose
	private Integer turnJumpTicks;
	@Expose
	private Integer turnBased;
	@Expose
	private Integer researchCostTerraforming;
	@Expose
	private Integer players;
	@Expose
	private Integer startingShips;
	@Expose
	private Integer startingTechScanning;
	@Expose
	private String starfield;

	public Integer getResearchCostExperimentation() {
		return researchCostExperimentation;
	}

	public void setResearchCostExperimentation(Integer researchCostExperimentation) {
		this.researchCostExperimentation = researchCostExperimentation;
	}

	public Integer getDarkGalaxy() {
		return darkGalaxy;
	}

	public void setDarkGalaxy(Integer darkGalaxy) {
		this.darkGalaxy = darkGalaxy;
	}

	public Integer getDevelopmentCostIndustry() {
		return developmentCostIndustry;
	}

	public void setDevelopmentCostIndustry(Integer developmentCostIndustry) {
		this.developmentCostIndustry = developmentCostIndustry;
	}

	public Integer getResearchCostManufacturing() {
		return researchCostManufacturing;
	}

	public void setResearchCostManufacturing(Integer researchCostManufacturing) {
		this.researchCostManufacturing = researchCostManufacturing;
	}

	public Integer getStartingTechHyperspace() {
		return startingTechHyperspace;
	}

	public void setStartingTechHyperspace(Integer startingTechHyperspace) {
		this.startingTechHyperspace = startingTechHyperspace;
	}

	public String getStarScatter() {
		return starScatter;
	}

	public void setStarScatter(String starScatter) {
		this.starScatter = starScatter;
	}

	public Integer getDevelopmentCostEconomy() {
		return developmentCostEconomy;
	}

	public void setDevelopmentCostEconomy(Integer developmentCostEconomy) {
		this.developmentCostEconomy = developmentCostEconomy;
	}

	public Integer getStartingTechTerraforming() {
		return startingTechTerraforming;
	}

	public void setStartingTechTerraforming(Integer startingTechTerraforming) {
		this.startingTechTerraforming = startingTechTerraforming;
	}

	public Integer getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(Integer anonymity) {
		this.anonymity = anonymity;
	}

	public Integer getStartingStars() {
		return startingStars;
	}

	public void setStartingStars(Integer startingStars) {
		this.startingStars = startingStars;
	}

	public Integer getAlliances() {
		return alliances;
	}

	public void setAlliances(Integer alliances) {
		this.alliances = alliances;
	}

	public Integer getResearchCostWeapons() {
		return researchCostWeapons;
	}

	public void setResearchCostWeapons(Integer researchCostWeapons) {
		this.researchCostWeapons = researchCostWeapons;
	}

	public Integer getResearchCostHyperspace() {
		return researchCostHyperspace;
	}

	public void setResearchCostHyperspace(Integer researchCostHyperspace) {
		this.researchCostHyperspace = researchCostHyperspace;
	}

	public Integer getResearchCostScanning() {
		return researchCostScanning;
	}

	public void setResearchCostScanning(Integer researchCostScanning) {
		this.researchCostScanning = researchCostScanning;
	}

	public Integer getStarsForVictory() {
		return starsForVictory;
	}

	public void setStarsForVictory(Integer starsForVictory) {
		this.starsForVictory = starsForVictory;
	}

	public Integer getNaturalResources() {
		return naturalResources;
	}

	public void setNaturalResources(Integer naturalResources) {
		this.naturalResources = naturalResources;
	}

	public Integer getStartingTechManufacturing() {
		return startingTechManufacturing;
	}

	public void setStartingTechManufacturing(Integer startingTechManufacturing) {
		this.startingTechManufacturing = startingTechManufacturing;
	}

	public String getCustomStarfield() {
		return customStarfield;
	}

	public void setCustomStarfield(String customStarfield) {
		this.customStarfield = customStarfield;
	}

	public Integer getStartingTechExperimentation() {
		return startingTechExperimentation;
	}

	public void setStartingTechExperimentation(Integer startingTechExperimentation) {
		this.startingTechExperimentation = startingTechExperimentation;
	}

	public Integer getStartingCash() {
		return startingCash;
	}

	public void setStartingCash(Integer startingCash) {
		this.startingCash = startingCash;
	}

	public Integer getTurnTime() {
		return turnTime;
	}

	public void setTurnTime(Integer turnTime) {
		this.turnTime = turnTime;
	}

	public Integer getRandomGates() {
		return randomGates;
	}

	public void setRandomGates(Integer randomGates) {
		this.randomGates = randomGates;
	}

	public Integer getStartingInfEconomy() {
		return startingInfEconomy;
	}

	public void setStartingInfEconomy(Integer startingInfEconomy) {
		this.startingInfEconomy = startingInfEconomy;
	}

	public Integer getTickRate() {
		return tickRate;
	}

	public void setTickRate(Integer tickRate) {
		this.tickRate = tickRate;
	}

	public Integer getBuildGates() {
		return buildGates;
	}

	public void setBuildGates(Integer buildGates) {
		this.buildGates = buildGates;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStartingInfIndustry() {
		return startingInfIndustry;
	}

	public void setStartingInfIndustry(Integer startingInfIndustry) {
		this.startingInfIndustry = startingInfIndustry;
	}

	public Integer getStartingInfScience() {
		return startingInfScience;
	}

	public void setStartingInfScience(Integer startingInfScience) {
		this.startingInfScience = startingInfScience;
	}

	public Integer getHomeStarDistance() {
		return homeStarDistance;
	}

	public void setHomeStarDistance(Integer homeStarDistance) {
		this.homeStarDistance = homeStarDistance;
	}

	public Integer getStartingTechWeapons() {
		return startingTechWeapons;
	}

	public void setStartingTechWeapons(Integer startingTechWeapons) {
		this.startingTechWeapons = startingTechWeapons;
	}

	public Integer getStarsPerPlayer() {
		return starsPerPlayer;
	}

	public void setStarsPerPlayer(Integer starsPerPlayer) {
		this.starsPerPlayer = starsPerPlayer;
	}

	public Integer getStartingTechBanking() {
		return startingTechBanking;
	}

	public void setStartingTechBanking(Integer startingTechBanking) {
		this.startingTechBanking = startingTechBanking;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getResearchCostBanking() {
		return researchCostBanking;
	}

	public void setResearchCostBanking(Integer researchCostBanking) {
		this.researchCostBanking = researchCostBanking;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDevelopmentCostScience() {
		return developmentCostScience;
	}

	public void setDevelopmentCostScience(Integer developmentCostScience) {
		this.developmentCostScience = developmentCostScience;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public Integer getTurnJumpTicks() {
		return turnJumpTicks;
	}

	public void setTurnJumpTicks(Integer turnJumpTicks) {
		this.turnJumpTicks = turnJumpTicks;
	}

	public Integer getTurnBased() {
		return turnBased;
	}

	public void setTurnBased(Integer turnBased) {
		this.turnBased = turnBased;
	}

	public Integer getResearchCostTerraforming() {
		return researchCostTerraforming;
	}

	public void setResearchCostTerraforming(Integer researchCostTerraforming) {
		this.researchCostTerraforming = researchCostTerraforming;
	}

	public Integer getPlayers() {
		return players;
	}

	public void setPlayers(Integer players) {
		this.players = players;
	}

	public Integer getStartingShips() {
		return startingShips;
	}

	public void setStartingShips(Integer startingShips) {
		this.startingShips = startingShips;
	}

	public Integer getStartingTechScanning() {
		return startingTechScanning;
	}

	public void setStartingTechScanning(Integer startingTechScanning) {
		this.startingTechScanning = startingTechScanning;
	}

	public String getStarfield() {
		return starfield;
	}

	public void setStarfield(String starfield) {
		this.starfield = starfield;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
