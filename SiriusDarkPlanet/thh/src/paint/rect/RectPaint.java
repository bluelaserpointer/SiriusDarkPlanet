package paint.rect;

import paint.PaintScript;
import physics.Point;

/**
 * One of the major PaintScript's direct subinterfaces.Describes a paint method which need specified coordinate and size.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class RectPaint extends PaintScript{
	public static final RectPaint BLANK_SCRIPT = new RectPaint() {
		@Override
		public void rectPaint(int x, int y, int w, int h) {}
	};
	public abstract void rectPaint(int x,int y,int w,int h);
	public void rectPaint(int x,int y,int size) {
		rectPaint(x, y, size, size);
	}
	public void rectPaint(Point point, int width, int height) {
		rectPaint(point.intX(), point.intY(), width, height);
	}
}
