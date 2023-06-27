package floatGame.effects;

import static java.lang.Math.PI;
import static java.lang.Math.random;

import java.awt.Color;

import core.GHQ;
import core.GHQObject;
import paint.dot.DotPaint;
import preset.effect.Effect;

public class DamagedEF extends Effect {
	Color color;
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
	public DamagedEF(GHQObject source, Color color) {
		super(source);
		this.color = color;
		name = "DamagedEF";
		paintScript = paint;
		limitFrame = GHQ.random2(10, 80);
		point().fastParaAdd_DASpd(0.5, 2*PI*random(), GHQ.random2(1, 3));
		accel = -0.4;
	}
	public DamagedEF getOriginal(){
		return new DamagedEF(shooter, color);
	}
	@Override
	public final void idle() {
		super.idle();
		point().addY(-1);
	}
	@Override
	public final void paint() {
		fadingPaint();
	}

}
