package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import core.GHQ;
import paint.rect.RectPaint;

/**
 * A minor PaintScript subclass which draw a rectangle's border line with specified color.
 * Useful when debugging.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class ColorFraming extends RectPaint {
	private final Color COLOR;
	private final Stroke STROKE;
	/**
	 * @param color The line color.
	 */
	public ColorFraming(Color color, Stroke stroke) {
		COLOR = color;
		STROKE = stroke;
	}
	public ColorFraming(Color color, float strokeSize) {
		COLOR = color;
		STROKE = new BasicStroke(strokeSize);
	}
	public static void rectPaint(Color color, Stroke stroke, int x, int y, int w, int h) {
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(color);
		G2.setStroke(stroke);
		G2.drawRect(x, y, w, h);
	}
	@Override
	public void rectPaint(int x, int y, int w, int h) {
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(COLOR);
		G2.setStroke(STROKE);
		G2.drawRect(x, y, w, h);
	}
}
