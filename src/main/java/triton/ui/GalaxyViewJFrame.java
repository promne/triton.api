package triton.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellRenderer;

public class GalaxyViewJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private final GameToGalaxyViewInterface gameToUiInterface;

	private UniverseMapComponent universeMap;
	private PlayersInfoTableModel playersInfoTableModel;
	private JButton startButton;
	private JButton stopButton;
	private JPanel panel;

	public GalaxyViewJFrame(GameToGalaxyViewInterface gameToUiInterface) throws Exception {
		this.gameToUiInterface = gameToUiInterface;
		this.setSize(600, 300);
		this.setMinimumSize(new Dimension(200, 200));
		this.setTitle("Known universe view");
		createUI();
	}

	private void createUI() {
		playersInfoTableModel = new PlayersInfoTableModel();

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.8);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		universeMap = new UniverseMapComponent();
		splitPane.setLeftComponent(universeMap);
		JTable playersInfoTable = new JTable(playersInfoTableModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
				JComponent component = (JComponent) super.prepareRenderer(renderer, rowIndex, columnIndex);
				if (columnIndex == 0) {
					Integer playerId = Integer.decode(getValueAt(rowIndex, 0).toString());
					component.setBackground(PlayerColors.getColor(playerId));
				} else {
					component.setBackground(this.getBackground());
				}
				return component;
			}
		};

		JScrollPane playersScrollPane = new JScrollPane(playersInfoTable);
		splitPane.setRightComponent(playersScrollPane);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		startButton = new JButton();
		panel.add(startButton);
		startButton.setAction(new AbstractAction("Play") {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				runAi();
			}
		});

		stopButton = new JButton();
		panel.add(stopButton);
		stopButton.setAction(new AbstractAction("Pause") {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopButton.setEnabled(false);
			}
		});
		stopButton.setEnabled(false);

	}

	private void runAi() {
		new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				universeMap.setUniverseMapData(gameToUiInterface.getStars(), gameToUiInterface.getFleets());
				playersInfoTableModel.setData(gameToUiInterface.getPlayers());

				try {
					while (stopButton.isEnabled() && gameToUiInterface.isTurnReady()) {
						gameToUiInterface.playOneRound();
						universeMap.setUniverseMapData(gameToUiInterface.getStars(), gameToUiInterface.getFleets());
						playersInfoTableModel.setData(gameToUiInterface.getPlayers());
						Thread.sleep(1000L);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				startButton.setEnabled(true);
				return null;
			}
		}.execute();
	}

}
