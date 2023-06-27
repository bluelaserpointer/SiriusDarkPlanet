package paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import core.GHQ;
import paint.line.LinePaint;

public class ColorLine extends LinePaint {
	private final Color COLOR;
	private final Stroke STROKE;
	
	public ColorLine(Color color, Stroke stroke) {
		COLOR = color;
		STROKE = stroke;
	}
	public static void linePaint(Color color, Stroke stroke, int x1, int y1, int x2, int y2) {
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(color);
		G2.setStroke(stroke);
		G2.drawLine(x1, y1, x2, y2);
	}
	@Override
	public void linePaint(int x1, int y1, int x2, int y2) {
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(COLOR);
		G2.setStroke(STROKE);
		G2.drawLine(x1, y1, x2, y2);
	}
}
