package physics;

import java.util.List;

import physics.hitShape.UnionHitShape;

public class UnionPhysics<T extends HitInteractable> extends Physics {
	protected Point point;
	protected UnionHitShape<T> hitShape;
	public UnionPhysics(Point point, List<T> objects, int areaWidth, int areaHeight) {
		this.point = point;
		hitShape = new UnionHitShape<T>(objects, this, areaWidth, areaHeight);
	}
	public UnionPhysics<T> setPoint(Point point) {
		this.point = point;
		return this;
	}
	@Override
	public UnionHitShape<T> hitShape() {
		return hitShape;
	}
	@Override
	public Point point() {
		return point;
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
