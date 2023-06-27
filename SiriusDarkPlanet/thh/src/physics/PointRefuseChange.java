package physics;

public interface PointRefuseChange {
	public default void setX(int x) {}
	public default void setY(int y) {}
	public default void setX(double x) {}
	public default void setY(double y) {}
	public default void setX(Point p) {}
	public default void setY(Point p) {}
}
