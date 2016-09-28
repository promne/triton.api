package triton.ui;

import java.util.Collection;

import triton.domain.Player;
import triton.ui.ext.BeanTableModel;

public class PlayersInfoTableModel extends BeanTableModel<Player> {

	public PlayersInfoTableModel() {
		super(Player.class);
		
		addColumn("Id", "id");
		addColumn("Alias", "alias");
	    addColumn("Stars", "totalStars");
	    addColumn("Ships", "shipsCount");
	    addColumn("Industry", "totalIndustry");
	    addColumn("Economy", "totalEconomy");
	    addColumn("Science", "totalScience");
	}

	public void setData(Collection<Player> players) {
		super.getRows().clear();
		for (Player player : players) {
			super.addRow(player);
		}
		fireTableDataChanged();
	}

}
