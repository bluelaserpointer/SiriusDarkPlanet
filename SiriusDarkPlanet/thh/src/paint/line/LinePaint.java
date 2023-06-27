package paint.line;

import core.GHQObject;
import paint.PaintScript;
import paint.dot.DotPaint;
import physics.HasPoint;
import physics.Point;

/**
 * One of the major PaintScript's direct subinterfaces.Describes a paint method which draw a line with specified coordinates.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class LinePaint extends PaintScript {
	public abstract void linePaint(int x1, int y1, int x2, int y2);
	public void linePaint(Point startCoordinate, Point endCoordinate) {
		linePaint(startCoordinate.intX(), startCoordinate.intY(), (int)endCoordinate.intX(), (int)endCoordinate.intY());
	}
	public DotPaint convertToDotPaint(GHQObject owner, int endX, int endY) {
		return new DotPaint(owner) {
			final int END_X = endX, END_Y = endY;
			@Override
			public void dotPaint(int x, int y) {
				linePaint(x, y, END_X, END_Y);
			}
			@Override
			public void dotPaint_capSize(int x, int y, int maxDistance) {
				final double DX = END_X - x, DY = END_Y - y;
				final double DISTANCE_SQ = DX*DX + DY*DY;
				if(DISTANCE_SQ < maxDistance*maxDistance)
					linePaint(x, y, END_X, END_Y);
				else{
					double RATE = maxDistance/Math.sqrt(DISTANCE_SQ);
					linePaint(x, y, x + (int)(DX*RATE), y + (int)(DY*RATE));
				}
			}
			@Override
			public void dotPaint_rate(int x, int y, double rate) {
				final double DX = END_X - x, DY = END_Y - y;
				linePaint(x, y, x + (int)(DX*rate), y + (int)(DY*rate));
			}
			@Override
			public int width() {
				return 0;
			}
			@Override
			public int height() {
				return 0;
			}
		};
	}
	public DotPaint convertToDotPaint(GHQObject owner, HasPoint endCoordinateSource) {
		return new DotPaint(owner) {
			final HasPoint SOURCE = endCoordinateSource;
			@Override
			public void dotPaint(int x, int y) {
				if(SOURCE == null)
					return;
				final Point COORDINATE = SOURCE.point();
				linePaint(x, y, COORDINATE.intX(), COORDINATE.intY());
			}
			@Override
			public void dotPaint_capSize(int x, int y, int maxDistance) {
				if(SOURCE == null)
					return;
				final Point COORDINATE = SOURCE.point();
				final double DX = COORDINATE.doubleDX(x), DY = COORDINATE.doubleDY(y);
				final double DISTANCE_SQ = DX*DX + DY*DY;
				if(DISTANCE_SQ < maxDistance*maxDistance)
					linePaint(x, y, COORDINATE.intX(), COORDINATE.intY());
				else{
					double RATE = maxDistance/Math.sqrt(DISTANCE_SQ);
					linePaint(x, y, x + (int)(DX*RATE), y + (int)(DY*RATE));
				}
			}
			@Override
			public void dotPaint_rate(int x, int y, double rate) {
				if(SOURCE == null)
					return;
				final Point COORDINATE = SOURCE.point();
				final double DX = COORDINATE.doubleDX(x), DY = COORDINATE.doubleDY(y);
				linePaint(x, y, x + (int)(DX*rate), y + (int)(DY*rate));
			}
			@Override
			public int width() {
				return 0;
			}
			@Override
			public int height() {
				return 0;
			}
		};
	}
}