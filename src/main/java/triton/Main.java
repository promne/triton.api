package triton;

import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import triton.ai.DummyExplorerAi;
import triton.api.ClientOrderException;
import triton.api.GameClient;
import triton.api.GameClientImpl;
import triton.domain.Fleet;
import triton.domain.Player;
import triton.domain.Star;
import triton.domain.UniverseReport;
import triton.ui.GalaxyViewJFrame;
import triton.ui.GameToGalaxyViewInterface;

public class Main implements GameToGalaxyViewInterface {

	private static final Logger LOG = Logger.getLogger(Main.class);

	private GameClient gameClient;
	private DummyExplorerAi ai;

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					GalaxyViewJFrame galaxyJFrame = new GalaxyViewJFrame(new Main());
					galaxyJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					galaxyJFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		// to run this, you need to provide authKey for cookies and game id.
		// remember to leave the double quotes and escape \\ to comply with java
		// syntax
	
		String authKey = "\"eyJfdXNlciI6WzY0ODMzNzc4MjE1ODEzMTIsMSwidUlWcWtZRDVDa2hGNVNXMXBCaWpzRCIsMTQyOTc1NDUwMSwxNDI5NzU0NTAxXX0\075|1429757589|47ca10cd78ba00097f711f982e2366aa084ee1f7\"";
		String gameId = "6349427808665600";

		gameClient = new GameClientImpl(authKey, gameId, true);
		ai = new DummyExplorerAi(gameClient);
	}

	@Override
	public void playOneRound() {
		try {
			ai.play(false);
			if (!ai.getReport().getGameOver()) {
				gameClient.submitTurn();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<Player> getPlayers() {
		try {
			return ai.getReport().getPlayers().values();
		} catch (ClientOrderException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Star> getStars() {
		try {
			return ai.getReport().getStars().values();
		} catch (ClientOrderException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Fleet> getFleets() {
		try {
			return ai.getReport().getFleets().values();
		} catch (ClientOrderException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isTurnReady() {
		UniverseReport universeReport;
		try {
			universeReport = ai.getReport();
		} catch (ClientOrderException e) {
			e.printStackTrace();
			return false;
		}
		Player currentPlayer;
		try {
			currentPlayer = ai.getReport().getPlayers().get(ai.getReport().getCurrentPlayerId());
		} catch (ClientOrderException e) {
			throw new RuntimeException(e);
		}
		return !universeReport.getGameOver() || currentPlayer.getReady() != 0;
	}

}
