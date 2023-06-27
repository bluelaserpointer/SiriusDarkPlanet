package physics;

import java.util.LinkedList;

public class BasePoint extends Point {
	private static final long serialVersionUID = -2585237312428553012L;
	protected LinkedList<Point> relativePoints = new LinkedList<Point>();
	protected HasPoint base;
	
	public BasePoint(HasPoint base) {
		this.base = base;
	}
	public void setBasePoint(HasPoint base) {
		this.base = base;
	}
	public LinkedList<Point> relativePoints(){
		return relativePoints;
	}
	public void addRelativePoint(Point relativePoint) {
		relativePoints.add(relativePoint);
	}
	public void addRelativePoint(HasPoint relativeTarget) {
		addRelativePoint(relativeTarget);
	}
	public HasPoint base() {
		return base;
	}
	public Point basePoint() {
		return base.point();
	}
	@Override
	public BasePoint clone() {
		return new BasePoint(base);
	}
	@Override
	public int intX() {
		return base.intX() + base.intX();
	}
	@Override
	public int intY() {
		return base.intY() + base.intY();
	}
	@Override
	public double doubleX() {
		return base.doubleX() + base.doubleX();
	}
	@Override
	public double doubleY() {
		return base.doubleY() + base.doubleY();
	}
	@Override
	public Point setAll(Point point) {
		basePoint().setAll(point);
		return this;
	}
	@Override
	public void setX(int x) {
		final int DX = intDX(x);
		for(Point point : relativePoints)
			point.addX(DX);
		basePoint().setX(x);
	}
	@Override
	public void setY(int y) {
		final int DY = intDY(y);
		for(Point point : relativePoints)
			point.addY(DY);
		basePoint().setY(y);
	}
	@Override
	public void setX(double x) {
		final double DX = doubleDX(x);
		for(Point point : relativePoints)
			point.addX(DX);
		basePoint().setX(x);
	}
	@Override
	public void setY(double y) {
		final double DY = doubleDY(y);
		for(Point point : relativePoints)
			point.addY(DY);
		basePoint().setY(y);
	}
	@Override
	public void setX(Point p) {
		final double DX = doubleDX(p);
		for(Point point : relativePoints)
			point.addX(DX);
		basePoint().setX(p);
	}
	@Override
	public void setY(Point p) {
		final double DY = doubleDY(p);
		for(Point point : relativePoints)
			point.addY(DY);
		basePoint().setY(p);
	}
}
