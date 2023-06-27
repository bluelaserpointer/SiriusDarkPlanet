package physics;

public class RelativeAngle extends Angle {
	public HasAngle base;
	public RelativeAngle(HasAngle base) {
		this.base = base;
	}
	public HasAngle base() {
		return base;
	}
	public Angle baseAngle() {
		return base().angle();
	}
	public double baseAngleValue() {
		return baseAngle().get();
	}
	public double relativeAngleValue() {
		return super.get();
	}
	@Override
	public void set(double angle) {
		this.angle = angle - baseAngleValue();
	}
	@Override
	public double get() {
		return baseAngleValue() + relativeAngleValue();
	}
}
