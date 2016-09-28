package triton.mechanic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CombatTest {

	@Test
	public void getRemainingShipsOnPlanet() {
		assertEquals(6, Combat.getRemainingShipsOnPlanet(10, 1, 10, 1));		

		assertEquals(7, Combat.getRemainingShipsOnPlanet(10, 1, 11, 1));		
		
		assertEquals(1, Combat.getRemainingShipsOnPlanet(10, 1, 5, 1));
		
		
		assertEquals(3, Combat.getRemainingShipsOnPlanet(133, 5, 133, 4));
		
		assertEquals(1, Combat.getRemainingShipsOnPlanet(133, 5, 131, 4));
		assertEquals(0, Combat.getRemainingShipsOnPlanet(133, 5, 130, 4));
		
	}
}
