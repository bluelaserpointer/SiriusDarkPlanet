package camera;

import core.GHQ;
import physics.HasPoint;
import physics.Point;

/**
 * A camera that chases a target.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class FixChaseCamera extends Camera {
	protected HasPoint chaseTarget;
	public FixChaseCamera(HasPoint chaseTarget, Point shiftPoint) {
		super(shiftPoint);
		setChaseTarget(chaseTarget);
	}
	public FixChaseCamera(HasPoint chaseTarget) {
		super(Point.NULL_POINT);
		setChaseTarget(chaseTarget);
	}
	public void setChaseTarget(HasPoint chaseTarget) {
		this.chaseTarget = chaseTarget;
	}
	@Override
	public void applyChanges() {
		if(chaseTarget == null)
			return;
		cameraLeft = chaseTarget.intX() - GHQ.screenW()/2 + dstPoint.intX();
		cameraTop = chaseTarget.intY() - GHQ.screenH()/2 + dstPoint.intY();
	}
}
