package physics.stage;

import java.util.BitSet;

import physics.HasBoundingBox;

public class GridBitSet extends Grids {
	final BitSet bitSet;
	public GridBitSet(HasBoundingBox stage, int gridSize) {
		super(stage, gridSize);
		bitSet = new BitSet(super.xGrids*super.yGrids);
	}
	public GridBitSet(int xGrids, int yGrids, int gridSize, boolean... bits) {
		super(xGrids, yGrids, gridSize);
		bitSet = new BitSet(xGrids*yGrids);
		for(int i = 0; i < bits.length; ++i) {
			bitSet.set(i, bits[i]);
		}
	}
	
	//control
	public void set_cellPos(int xPos, int yPos, boolean bool) {
		if(0 <= xPos && xPos < xGrids && 0 <= yPos && yPos < yGrids)
			bitSet.set(xPos + yPos*xGrids, bool);
	}
	public void set_stageCod(int x, int y, boolean bool) {
		set_cellPos(x/gridSize, y/gridSize, bool);
	}
	public void set_cellPos(int xPos, int yPos) {
		set_cellPos(xPos, yPos, true);
	}
	public void set_stageCod(int x, int y) {
		set_stageCod(x, y, true);
	}
	public void clear() {
		bitSet.clear();
	}
	
	//information
	public BitSet bitSet() {
		return bitSet;
	}
	public boolean get_cellPos(int xPos, int yPos, boolean valueWhenOutOfBounds) {
		if(0 <= xPos && xPos < xGrids && 0 <= yPos && yPos < yGrids)
			return bitSet.get(xPos + yPos*xGrids);
		else
			return valueWhenOutOfBounds;
	}
	public boolean get_stageCod(int x, int y, boolean valueWhenOutOfBounds) {
		return get_cellPos(x/gridSize, y/gridSize, valueWhenOutOfBounds);
	}
}
