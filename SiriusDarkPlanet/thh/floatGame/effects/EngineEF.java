package floatGame.effects;

import static java.lang.Math.PI;
import static java.lang.Math.random;

import java.awt.Color;

import core.GHQ;
import core.GHQObject;
import paint.dot.DotPaint;
import preset.effect.Effect;

public class EngineEF extends Effect {
	static final DotPaint paint = new DotPaint() {
		@Override
		public void dotPaint(int x, int y) {
			final int w = width(), h = height();
			GHQ.getG2D(Color.BLUE).drawOval(x - w/2, y - h/2, w, h);
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
	public EngineEF(GHQObject source, boolean makeClones, int dx, int dy, int setRadius) {
		super(source);
		name = "EngineEF";
		this.dx = dx;
		this.dy = dy;
		this.setRadius = setRadius;
		paintScript = paint;
		limitFrame = GHQ.random2(10, 40);
		point().addXY(dx, dy);
		point().fastParaAdd_DASpd(setRadius, 2*PI*random(), GHQ.random2(2, 5));
		accel = -0.1;
		if(makeClones) {
			for(int i = 0;i < 3;i++)
				GHQ.stage().addEffect(new EngineEF(source, false, dx, dy, setRadius));
		}
	}
	public EngineEF getOriginal(){
		return new EngineEF(shooter, false, dx, dy, setRadius);
	}
	@Override
	public final void paint() {
		fadingPaint();
	}
}