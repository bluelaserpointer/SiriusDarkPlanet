package preset.unit;

import core.GHQ;
import physics.Angle;
import physics.HasAnglePoint;
import physics.Point;

public abstract class UnitAction implements HasAnglePoint {
	protected int initialFrame;
	private Body body;
	public UnitAction(Body body) {
		this.body = body;
	}
	//main role
	public abstract void idle();
	//event
	protected void activated() {
		initialFrame = GHQ.nowFrame();
	}
	protected void overwriteFailed() {}
	//information
	public boolean precondition() {
		return true;
	}
	public boolean isActivated() {
		return body == null ? false : body.isDoingAction(this);
	}
	public Unit owner() {
		return body.owner();
	}
	public Body body() {
		return body;
	}
	public boolean canOverwrite(UnitAction target) {
		return true;
	}
	public boolean canOverwriteToBody() {
		return body().canOverwrite(this);
	}
	@Override
	public Point point() {
		return body.point();
	}
	@Override
	public Angle angle() {
		return body.angle();
	}
	public int initialFrame() {
		return initialFrame;
	}
	//for child class
	/**
	 * Apply to this body doing the action.
	 * Succeed: activated() will be called.
	 * Failed: activateFailed() will be called.
	 */
	protected final boolean activate() {
		return body().action(this);
	}
	protected final void stopAction() {
		body().stopAction(this);
	}
	protected void stopped() {}
}
