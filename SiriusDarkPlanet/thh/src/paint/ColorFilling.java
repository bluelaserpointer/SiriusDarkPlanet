package paint;

import java.awt.Color;
import java.awt.Graphics2D;

import core.GHQ;
import paint.rect.RectPaint;

/**
 * A minor PaintScript subclass which fill a rectangle with specified color.
 * Useful when debugging.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class ColorFilling extends RectPaint {
	private final Color COLOR;
	/**
	 * @param color The rectangle color.
	 */
	public ColorFilling(Color color) {
		COLOR = color;
	}
	public static void rectPaint(Color color, int x, int y, int w, int h) {
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(color);
		G2.fillRect(x, y, w, h);
	}
	@Override
	public void rectPaint(int x, int y, int w, int h) {
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(COLOR);
		G2.fillRect(x, y, w, h);
	}
}
