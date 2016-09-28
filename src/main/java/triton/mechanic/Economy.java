package triton.mechanic;

import triton.domain.GameConfig;
import triton.domain.Star;

public class Economy {

	private static Integer getStarResources(Star star, int terraformingLevel) {
		return star.getNaturalResources() + 5 * terraformingLevel;
	}
	
	public static Integer getPriceForStarEconomy(GameConfig gameConfig, Star star, int terraformingLevel, int level) {
		int starResources = getStarResources(star, terraformingLevel);
		double price = Math.floor((2.5 * gameConfig.getDevelopmentCostEconomy() * level)/(starResources / 100.0)); 
		return new Integer((int)price);
	}
	
	public static Integer getPriceForStarIndustry(GameConfig gameConfig, Star star, int terraformingLevel, int level) {
		int starResources = getStarResources(star, terraformingLevel);
		double price = Math.floor((5 * gameConfig.getDevelopmentCostIndustry() * level)/(starResources / 100.0)); 
		return new Integer((int)price);
	}
	
	public static Integer getPriceForStarScience(GameConfig gameConfig, Star star, int terraformingLevel, int level) {
		int starResources = getStarResources(star, terraformingLevel);
		double price = Math.floor((20 * gameConfig.getDevelopmentCostScience() * level)/(starResources / 100.0)); 
		return new Integer((int)price);
	}
	
	public static Integer getPriceForStarGateway(GameConfig gameConfig, Star star, int terraformingLevel) {
		int starResources = getStarResources(star, terraformingLevel);
		double price = Math.floor((100 * gameConfig.getBuildGates())/(starResources / 100.0)); 
		return new Integer((int)price);
	}
}
