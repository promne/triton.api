package triton.ui;

import java.awt.Color;

public final class PlayerColors {

	static final Color[] PLAYER_COLORS = new Color[] {
			Color.decode("#FF0000"), Color.decode("#00FF00"),
			Color.decode("#0000FF"), Color.decode("#FFFF00"),
			Color.decode("#FF00FF"), Color.decode("#00FFFF"),
			Color.decode("#000000"), Color.decode("#800000"),
			Color.decode("#008000"), Color.decode("#000080"),
			Color.decode("#808000"), Color.decode("#800080"),
			Color.decode("#008080"), Color.decode("#808080"),
			Color.decode("#C00000"), Color.decode("#00C000"),
			Color.decode("#0000C0"), Color.decode("#C0C000"),
			Color.decode("#C000C0"), Color.decode("#00C0C0"),
			Color.decode("#C0C0C0"), Color.decode("#400000"),
			Color.decode("#004000"), Color.decode("#000040"),
			Color.decode("#404000"), Color.decode("#400040"),
			Color.decode("#004040"), Color.decode("#404040"),
			Color.decode("#200000"), Color.decode("#002000"),
			Color.decode("#000020"), Color.decode("#202000"),
			Color.decode("#200020"), Color.decode("#002020"),
			Color.decode("#202020"), Color.decode("#600000"),
			Color.decode("#006000"), Color.decode("#000060"),
			Color.decode("#606000"), Color.decode("#600060"),
			Color.decode("#006060"), Color.decode("#606060"),
			Color.decode("#A00000"), Color.decode("#00A000"),
			Color.decode("#0000A0"), Color.decode("#A0A000"),
			Color.decode("#A000A0"), Color.decode("#00A0A0"),
			Color.decode("#A0A0A0"), Color.decode("#E00000"),
			Color.decode("#00E000"), Color.decode("#0000E0"),
			Color.decode("#E0E000"), Color.decode("#E000E0"),
			Color.decode("#00E0E0"), Color.decode("#E0E0E0")};

	public static Color getColor(int id) {
		return PLAYER_COLORS[id];
	}
}
