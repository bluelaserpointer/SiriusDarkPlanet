package troubleCrasher.jigsaw;

import physics.stage.GridBitSet;

public enum JigsawEnum {
	SQ1(1, 1, true),
	SQ2(2, 2, true, true, true, true),
	BAR2(2, 1, true, true),
	BAR3(3, 1, true, true, true),
	HOLLOW1(3, 3, true, true, true, true, false, true, true, true, true),
	L22(2, 2, true, false, true, true),
	L32(3, 2, true, true, true, true, false, false);
	
	public final GridBitSet gridBitSet;
	public static final int JIGSAW_GRID_SIZE = 50;
	JigsawEnum(int xTiles, int yTiles, boolean... tileBits) {
		this.gridBitSet = new GridBitSet(xTiles, yTiles, JIGSAW_GRID_SIZE, tileBits);
	}
}