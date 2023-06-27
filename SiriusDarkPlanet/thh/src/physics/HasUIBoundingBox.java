package physics;

public interface HasUIBoundingBox extends HasBoundingBox {
	@Override
	public default int cx() {
		return point().intX() + width()/2;
	}
	@Override
	public default int cy() {
		return point().intY() + height()/2;
	}
}
