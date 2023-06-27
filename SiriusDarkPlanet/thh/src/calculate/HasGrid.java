package calculate;

import physics.direction.DirectionVH;

public interface HasGrid {
	public abstract int gridSize();
	public abstract int xGrids();
	public abstract int yGrids();
	public default DirectionVH lineDirection() {
		return DirectionVH.HORZ;
	}
	public default int toGridIndex(int xPos, int yPos) {
		return lineDirection().isHorz() ? xPos + yPos*xGrids() : yPos + xPos*yGrids();
	}
	public default int toXPosition(int index) {
		return lineDirection().isHorz() ? index % xGrids() : index / yGrids();
	}
	public default int toYPosition(int index) {
		return lineDirection().isHorz() ? index / xGrids() : index % yGrids();
	}
	public default int toCoordinate(int pos) {
		return pos*gridSize() + gridSize()/2;
	}
	public default int toPosition(int coordinate) {
		return coordinate/gridSize();
	}
	public default int gridAmount() {
		return xGrids()*yGrids();
	}
	public default boolean inBounds(int index) {
		return 0 <= index && index < gridAmount();
	}
	public default boolean inBounds(int xPos, int yPos) {
		return inHorzBounds(xPos) && inVertBounds(yPos);
	}
	public default boolean inHorzBounds(int xPos) {
		return 0 <= xPos && xPos < yGrids();
	}
	public default boolean inVertBounds(int yPos) {
		return 0 <= yPos && yPos < yGrids();
	}
}
