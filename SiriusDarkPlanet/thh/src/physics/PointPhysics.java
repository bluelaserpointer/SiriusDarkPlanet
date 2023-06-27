package physics;

import physics.hitShape.HitShape;

public class PointPhysics extends Physics{
	protected Point point;
	protected Angle angle;
	public PointPhysics(Point point, Angle angle) {
		this.point = point;
		this.angle = angle;
	}
	public PointPhysics(Point point) {
		this.point = point;
	}
	@Override
	public Point point() {
		return point;
	}
	@Override
	public HitShape hitShape() {
		return HitShape.NULL_HITSHAPE;
	}
	@Override
	public HitGroup hitGroup() {
		return HitGroup.HIT_ALL;
	}
	@Override
	public Angle angle() {
		return Angle.NULL_ANGLE;
	}
}
