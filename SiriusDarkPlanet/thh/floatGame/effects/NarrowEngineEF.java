package floatGame.effects;

import java.awt.Color;

import core.GHQ;
import core.GHQObject;
import paint.dot.DotPaint;
import preset.effect.Effect;

public class NarrowEngineEF extends Effect {
	final DotPaint paint = new DotPaint() {
		@Override
		public void dotPaint(int x, int y) {
			final int w = width(), h = height();
			GHQ.getG2D(color).drawOval(x - w/2, y - h/2, w, h);
		}

		@Override
		public int width() {
			return 2;
		}
		@Override
		public int height() {
			return 2;
		}
	};
	int dx, dy, setRadius;
	Color color;
	public NarrowEngineEF(GHQObject source, int dx, int dy, int setRadius, Color color) {
		super(source);
		name = "EngineEF2";
		this.dx = dx;
		this.dy = dy;
		this.setRadius = setRadius;
		this.color = color;
		paintScript = paint;
		limitFrame = GHQ.random2(10, 40);
		point().addXY(dx, dy);
		point().fastParaAdd_DASpd(setRadius, source.point().moveAngle() + Math.PI, GHQ.random2(2, 5));
		accel = -0.1;
	}
	public NarrowEngineEF getOriginal(){
		return new NarrowEngineEF(shooter, dx, dy, setRadius, color);
	}
	@Override
	public final void paint() {
		fadingPaint();
	}
}