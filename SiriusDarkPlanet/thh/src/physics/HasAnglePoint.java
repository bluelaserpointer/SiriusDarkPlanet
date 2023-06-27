package physics;

public interface HasAnglePoint extends HasPoint, HasAngle{
	public abstract Point point();
	public abstract Angle angle();
	public default boolean inWidthOfFieldView_degree(HasPoint target, double degreeWidth) {
		return target != null && angle().isDiffSmaller(point().angleTo(target.point()), Math.toRadians(degreeWidth));
	}
	public default boolean inWidthOfFieldView_degree(Point point, double degreeWidth) {
		return angle().isDiffSmaller(point().angleTo(point), Math.toRadians(degreeWidth));
	}
}
