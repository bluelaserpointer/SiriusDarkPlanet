package paint.dot;

import java.awt.Graphics2D;

import core.GHQ;
import core.GHQObject;
import paint.rect.RectPaint;
import physics.HasAnglePoint;
import physics.Point;
import physics.hitShape.HasArea;

/**
 * One of the major PaintScript's direct subinterfaces.Describes a paint method which has original width and height but need specified coordinate
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class DotPaint extends RectPaint implements HasArea {
	//constants
	public static final DotPaint BLANK_SCRIPT = new DotPaint(GHQObject.NULL_GHQ_OBJECT) {
		@Override
		public void dotPaint(int x, int y) {}
		@Override
		public int width() {
			return 0;
		}
		@Override
		public int height() {
			return 0;
		}
	};
	protected GHQObject owner;
	public DotPaint(GHQObject owner) {
		this.owner = owner;
	}
	public DotPaint() {
		owner = GHQObject.NULL_GHQ_OBJECT;
	}
	//main role
	/**
	 * Drawing at specified coordinate.
	 * The width and height is decided by its original value.
	 * @param x
	 * @param y
	 */
	public abstract void dotPaint(int x, int y);
	/**
	 * Drawing at specified coordinate but keep its maxSize lower than a value.
	 * @param x
	 * @param y
	 * @param maxSize
	 */
	public void dotPaint(Point coordinate) {
		dotPaint(coordinate.intX(), coordinate.intY());
	}
	public void dotPaint(DotPaintParameter parameter) {
		GHQ.setFlip(parameter.doXFlip, parameter.doYFlip);
		this.dotPaint_turnAndCapSize(parameter.point, parameter.angle.get(), parameter.sizeCap);
		GHQ.setFlip(parameter.doXFlip, parameter.doYFlip);
	}
	@Override
	public void rectPaint(int x, int y, int w, int h) {
		final double sx = (double)w/width(), sy = (double)h/height();
		GHQ.getG2D().translate(x, y);
		GHQ.getG2D().scale(sx, sy);
		dotPaint(0, 0);
		GHQ.getG2D().scale(1.0/sx, 1.0/sy);
		GHQ.getG2D().translate(-x, -y);
	}
	public void dotPaint_capSize(int x, int y, int maxSize) {
		final int SIZE_BIG = maxSide();
		if(SIZE_BIG == 0 || SIZE_BIG == maxSize)
			dotPaint(x, y);
		else
			dotPaint_rate(x, y, (double)maxSize/SIZE_BIG);
	}
	public void dotPaint_capSize(Point point, int maxSize) {
		dotPaint_capSize(point.intX(), point.intY(), maxSize);
	}
	/**
	 * Drawing at specified coordinate but resize it by a rate.
	 * @param x
	 * @param y
	 * @param rate
	 */
	public void dotPaint_rate(int x, int y, double rate) {
		GHQ.getG2D().translate(x, y);
		GHQ.scale(rate);
		dotPaint(0, 0);
		GHQ.scale(1.0/rate);
		GHQ.getG2D().translate(-x, -y);
	}
	public void dotPaint_rate(Point point, double rate) {
		dotPaint_rate(point.intX(), point.intY(), rate);
	}
	/**
	 * Drawing at specified coordinate but change its angle.
	 * @param x
	 * @param y
	 * @param angle
	 */
	public void dotPaint_turn(int x, int y, double angle) {
		GHQ.rotate(angle, x, y);
		dotPaint(x, y);
		GHQ.rotate(-angle, x, y);
	}
	public void dotPaint_turn(Point point, double angle) {
		dotPaint_turn(point.intX(), point.intY(), angle);
	}
	public void dotPaint_turn(HasAnglePoint anglePoint) {
		dotPaint_turn(anglePoint.point(), anglePoint.angle().get());
	}
	/**
	 * Drawing at specified coordinate but change its angle and keep its maxSize lower than a value.
	 * @param x
	 * @param y
	 * @param angle
	 * @param maxSize
	 */
	public void dotPaint_turnAndCapSize(int x, int y, double angle, int maxSize) {
		final Graphics2D G2 = GHQ.getG2D();
		G2.rotate(angle, x, y);
		dotPaint_capSize(x, y, maxSize);
		G2.rotate(-angle, x, y);
	}
	public void dotPaint_turnAndCapSize(Point point, double angle, int maxSize) {
		dotPaint_turnAndCapSize(point.intX(), point.intY(), angle, maxSize);
	}
	public void dotPaint_turnAndCapSize(HasAnglePoint anglePoint, int maxSize) {
		dotPaint_turnAndCapSize(anglePoint.point(), anglePoint.angle().get(), maxSize);
	}
	//control
	public DotPaint setOwner(GHQObject owner) {
		this.owner = owner;
		return this;
	}
	//information
	public GHQObject owner() {
		return owner;
	}
	@Override
	public abstract int width();
	@Override
	public abstract int height();
	
	//tool
	public static int getMaxSize(Iterable<DotPaint> scripts) {
		int biggestSize = 0;
		for(DotPaint ver : scripts) {
			final int BIGGER_SIZE = ver.maxSide();
			if(biggestSize < BIGGER_SIZE)
				biggestSize = BIGGER_SIZE;
		}
		return biggestSize;
	}
	public static int getMaxSize(DotPaint[] scripts) {
		int biggestSize = 0;
		for(DotPaint ver : scripts) {
			final int BIGGER_SIZE = ver.maxSide();
			if(biggestSize < BIGGER_SIZE)
				biggestSize = BIGGER_SIZE;
		}
		return biggestSize;
	}
}
