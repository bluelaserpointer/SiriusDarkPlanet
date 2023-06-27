package camera;

import physics.Point;
import physics.hitShape.RectShape;

/**
 * Manages view scrolling.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class Camera {
	protected int cameraLeft, cameraTop;
	protected final Point dstPoint;
	public double zoom = 1.0;
	protected final RectShape projectionArea = new RectShape(0, 0, RectShape.MATCH_SCREEN_SIZE, RectShape.MATCH_SCREEN_SIZE);
	public Camera(Point dstPoint) {
		this.dstPoint = dstPoint;
	}
	public void applyChanges() {
		cameraLeft = dstPoint.intX() - projectionW()/2;
		cameraTop = dstPoint.intY() - projectionH()/2;
	}
	public int x() {
		return cameraLeft + projectionW()/2;
	}
	public int y() {
		return cameraTop + projectionH()/2;
	}
	public int left() {
		return cameraLeft;
	}
	public int top() {
		return cameraTop;
	}
	public int right() {
		return cameraLeft + projectionW();
	}
	public int bottom() {
		return cameraTop + projectionH();
	}
	public Point dstPoint() {
		return dstPoint;
	}
	public Point projectionLeftTopPoint() {
		return projectionArea.point();
	}
	public int projectionW() {
		return projectionArea.width();
	}
	public int projectionH() {
		return projectionArea.height();
	}
}
