package triton.mechanic;

public class Combat {

	public static int getRemainingShipsOnPlanet(int attackerShips, int attackerWeaponsLevel, int defenderShips, int defenderWeaponsLevel) {
		int turns = (int) Math.ceil((attackerShips/(defenderWeaponsLevel+1.0))-1);
		return Math.max(defenderShips-turns*attackerWeaponsLevel, 0);
	}

}
