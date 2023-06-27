package physics.direction;

public interface MonoDirection {
	public abstract boolean isNone();
	public abstract double angle();
	public abstract double ex();
	public abstract double ey();
	public default int intEx() {
		return (int)ex();
	}
	public default int intEy() {
		return (int)ey();
	}
}