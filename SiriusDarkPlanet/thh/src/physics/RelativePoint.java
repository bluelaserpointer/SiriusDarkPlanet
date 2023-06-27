package physics;

public class RelativePoint extends Point{
	private static final long serialVersionUID = -2585237312428553012L;
	protected HasPoint base;
	protected HasAngle angleBase;
	protected Point relativePoint;
	
	public RelativePoint(HasPoint base, Point relativePoint, HasAngle angle) {
		this.base = base;
		this.angleBase = angle;
		this.relativePoint = relativePoint;
	}
	public RelativePoint(HasPoint base, Point relativePoint, boolean useAngle) {
		this.base = base;
		this.angleBase = useAngle ? HasAngle.generate() : HasAngle.NULL_HAS_ANGLE;
		this.relativePoint = relativePoint;
	}
	public void setBasePoint(HasPoint base) {
		this.base = base;
	}
	public void setBaseAngle(HasAngle angle) {
		this.angleBase = angle;
	}
	public void setRelativePoint(Point relativePoint) {
		this.relativePoint = relativePoint;
	}
	public HasPoint baseHasPoint() {
		return base;
	}
	public Point basePoint() {
		return base.point();
	}
	public HasAngle baseHasAngle() {
		return angleBase;
	}
	public Angle baseAngle() {
		return angleBase.angle();
	}
	public Point relativePoint() {
		return relativePoint;
	}
	@Override
	public RelativePoint clone() {
		return new RelativePoint(base, relativePoint.clone(), angleBase);
	}
	@Override
	public int intX() {
		if(baseAngle().get() == 0.0)
			return basePoint().intX() + relativePoint.intX();
		else {
			return (int)doubleX();
		}
	}
	@Override
	public int intY() {
		if(baseAngle().get() == 0.0)
			return basePoint().intY() + relativePoint.intY();
		else
			return (int)doubleY();
	}
	@Override
	public double doubleX() {
		if(baseAngle().get() == 0.0)
			return basePoint().doubleX() + relativePoint.doubleX();
		else
			return basePoint().doubleX() + relativePoint.doubleX()*baseAngle().cos() - relativePoint.doubleY()*baseAngle().sin();
	}
	@Override
	public double doubleY() {
		if(baseAngle().get() == 0.0)
			return basePoint().doubleY() + relativePoint.doubleY();
		else
			return basePoint().doubleY() + relativePoint.doubleX()*baseAngle().sin() + relativePoint.doubleY()*baseAngle().cos();
	}
	@Override
	public Point setAll(Point point) {
		relativePoint.setAll(point);
		return this;
	}
	@Override
	public void setX(int x) {
		setX((double)x);
	}
	@Override
	public void setY(int y) {
		setY((double)y);
	}
	@Override
	public void setX(double x) {
		final double cos = baseAngle().cos(), sin = baseAngle().sin();
		final double dx = doubleDX(x);
		relativePoint.addX(dx*cos);
		relativePoint.addY(-dx*sin);
	}
	@Override
	public void setY(double y) {
		final double cos = baseAngle().cos(), sin = baseAngle().sin();
		final double dy = doubleDY(y);
		relativePoint.addX(dy*sin);
		relativePoint.addY(dy*cos);
	}
	@Override
	public void setX(Point p) {
		relativePoint.setX(basePoint().doubleDX(p));
	}
	@Override
	public void setY(Point p) {
		relativePoint.setY(basePoint().doubleDY(p));
	}
}
