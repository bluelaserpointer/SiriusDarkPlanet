package physics;

public interface HasGridPoint extends HasPoint {
	public abstract GridPoint gridPoint();
	@Override
	public default Point point() {
		return gridPoint();
	}
	public default int gridX() {
		return gridPoint().gridX();
	}
	public default int gridY() {
		return gridPoint().gridY();
	}
}
