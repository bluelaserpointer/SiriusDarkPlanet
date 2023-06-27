package floatGame.unit;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;

import core.GHQ;
import floatGame.modules.FloatModule;
import physics.Point;

public enum ShipDesign {
	M1W1(
		1, 1,
		2, 1),
	M3W2(
		1, 1.5,
		2, 1, 2, 2,
		3, 1, 3, 2);
	public static final int SLOT_C_INTERVAL = 100;
	public static final int SLOT_SIZE = 75;
	public final Point[] slotPositions;
	public final int width, height;
	private ShipDesign(double... xys) {
		slotPositions = new Point[xys.length/2];
		int max_cx = 0, max_cy = 0;
		for(int i = 0; i < slotPositions.length; ++i) {
			int cx = (int)(SLOT_C_INTERVAL/2 + SLOT_C_INTERVAL*(xys[i*2] - 1.0));
			int cy = (int)(SLOT_C_INTERVAL/2 + SLOT_C_INTERVAL*(xys[i*2 + 1] - 1.0));
			if(cx > max_cx)
				max_cx = cx;
			if(cy > max_cy)
				max_cy = cy;
			slotPositions[i] = new Point.IntPoint(cx, cy);
		}
		width = SLOT_C_INTERVAL/2 + max_cx;
		height = SLOT_C_INTERVAL/2 + max_cy;
	}
	public void dotPaint(int x, int y, List<FloatModule> modules) {
		final Graphics2D g2 =  GHQ.getG2D(Color.BLUE);
		final int left = x - width/2, top = y - height/2;
		final Iterator<FloatModule> modulesIte = modules.iterator();
		for(Point point : slotPositions) {
			final int cellLeft = left + point.intX() - SLOT_SIZE/2;
			final int cellTop = top + point.intY() - SLOT_SIZE/2;
			g2.drawRect(cellLeft, cellTop, SLOT_SIZE, SLOT_SIZE);
			if(modulesIte.hasNext()) {
				final FloatModule module = modulesIte.next();
				if(module != null)
					module.icon().rectPaint(cellLeft, cellTop, SLOT_SIZE, SLOT_SIZE);
			}
		}
	}
	public int getSlotID(int rx, int ry) {
		int id = 0;
		for(Point point : slotPositions) {
			if(point.inRangeXY(rx, ry, SLOT_SIZE, SLOT_SIZE)) {
				return id;
			}
			++id;
		}
		return (id == slotPositions.length) ? -1 : id;
	}
	public int moduleAmount() {
		return slotPositions.length;
	}
}
