package physics;

import physics.hitShape.HitShape;

public interface HasPhysics extends HitInteractable, HasAnglePoint{
	public abstract Physics physics();
	@Override
	public default HitShape hitShape() {
		return physics().hitShape();
	}
	@Override
	public default HitGroup hitGroup() {
		return physics().hitGroup();
	}
	@Override
	public default Angle angle() {
		return physics().angle();
	}
	@Override
	default Point point() {
		return physics().point();
	}
}
