package floatGame.structure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.LinkedList;

import core.GHQ;
import floatGame.engine.FloatGame;
import physics.HitGroup;
import physics.Point;
import physics.hitShape.RectShape;
import preset.structure.Structure;

public class FloatPlatform extends Structure {
	
	protected static final int SMOOTHNESS = 100;
	protected LinkedList<Slope> slopes = new LinkedList<>();
	
	static class Slope {
		final int length;
		final int height;
		public Slope(int length) {
			this.length = length;
			height = GHQ.random2(-2, +1);
		}
	}
	
	public FloatPlatform(int width, int height) {
		physics().setPoint(new Point.IntPoint());
		physics().setHitShape(new RectShape(this, width, height));
		physics().setHitRule(HitGroup.HIT_ALL);
		point().setXY(FloatGame.STAGE_W/2, 500);
		// make slopes
		int lengthSum = 0;
		while(true) {
			int length = (int)(Math.random()*SMOOTHNESS);
			if(lengthSum + length >= width()) {
				slopes.add(new Slope(width() - lengthSum));
				break;
			} else {
				slopes.add(new Slope(length));
				lengthSum += length;
			}
		}
	}
	
	public void paint() {
		super.paint();
		super.fillBoundingBox(Color.GRAY);
		GHQ.getG2D(Color.GRAY).fillRect(left(), top() + 2, width(), height() - 2);
		int lengthSum = 0;
		for(Slope slope : slopes) {
			final int length = slope.length;
			GHQ.getG2D(Color.DARK_GRAY, new BasicStroke(5)).drawLine(lengthSum, top() + slope.height, lengthSum += length, top() + slope.height);
		}
		GHQ.getG2D(Color.BLACK, GHQ.stroke1);
	}
}
