package paint.dot;

import physics.Angle;
import physics.HasPoint;
import physics.Point;

public class DotPaintParameter implements HasPoint {
	public Point point;
	public Angle angle;
	public int sizeCap;
	public boolean doXFlip, doYFlip;
	public DotPaintParameter setPoint(Point point) {
		this.point = point;
		return this;
	}
	public DotPaintParameter setAngle(Angle angle) {
		this.angle = angle;
		return this;
	}
	public DotPaintParameter setSizeCap(int sizeCap) {
		this.sizeCap = sizeCap;
		return this;
	}
	public DotPaintParameter setFlip(boolean doXFlip, boolean doYFlip) {
		this.doXFlip = doXFlip;
		this.doYFlip = doYFlip;
		return this;
	}
	@Override
	public Point point() {
		return point;
	}
}
