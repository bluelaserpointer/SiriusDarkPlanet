package camera;

import core.GHQ;
import physics.HasPoint;
import physics.Point;

/**
 * A camera that chases a target but slowly moving to the direction of mouse cursor.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class FixChaseSmoothPeekCamera extends FixChaseCamera {
	double peekMovingFrames;
	public FixChaseSmoothPeekCamera(HasPoint chaseTarget, double peekMovingFrames) {
		super(chaseTarget, new Point.DoublePoint());
		this.peekMovingFrames = peekMovingFrames;
	}
	@Override
	public void applyChanges() {
		if(chaseTarget == null)
			return;
		final int dstX = (GHQ.mouseX() + chaseTarget.point().intX())/2, dstY = (GHQ.mouseY() + chaseTarget.point().intY())/2;
		final double dx = (double)(dstX - x())/peekMovingFrames, dy = (double)(dstY - y())/peekMovingFrames;
		dstPoint.addX(dx);
		dstPoint.addY(dy);
		cameraLeft = chaseTarget.intX() - GHQ.screenW()/2 + dstPoint.intX();
		cameraTop = chaseTarget.intY() - GHQ.screenH()/2 + dstPoint.intY();
	}
}
