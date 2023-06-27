package floatGame.effects;

import static java.lang.Math.PI;
import static java.lang.Math.random;

import java.awt.Color;
import java.util.LinkedList;

import core.GHQ;
import core.GHQObject;
import paint.dot.DotPaint;
import physics.Dynam;
import physics.Point;
import preset.effect.Effect;

public class ExplosionEffect extends Effect {
	final DotPaint paint = new DotPaint() {
		@Override
		public void dotPaint(int x, int y) {
			final int w = width(), h = height();
			GHQ.getG2D(Color.LIGHT_GRAY).drawOval(x - w/2, y - h/2, w, h);
		}

		@Override
		public int width() {
			return 4;
		}
		@Override
		public int height() {
			return 4;
		}
	};
	protected final LinkedList<Point> points = new LinkedList<Point>();
	int dx, dy, setRadius;
	public ExplosionEffect(GHQObject source, int dx, int dy, int setRadius) {
		super(source);
		name = "EngineEF";
		this.dx = dx;
		this.dy = dy;
		this.setRadius = setRadius;
		paintScript = paint;
		limitFrame = 30;
		point().addSpeed(source.point().xSpeed(), source.point().ySpeed());
		point().addXY(dx, dy);
		for(int i = 0; i < 30; ++i) {
			final Point point = new Dynam();
			point.fastParaAdd_DASpd(setRadius, 2*PI*random(), GHQ.random2(2, 5));
			points.add(point);
		}
	}
	@Override
	public void idle() {
		super.idle();
		point().mulSpeed(0.9);
		for(Point point : points) {
			point.idle();
			point.mulSpeed(0.9);
		}
	}
	public ExplosionEffect getOriginal(){
		return new ExplosionEffect(shooter, dx, dy, setRadius);
	}
	@Override
	public final void paint() {
		setImageAlphaByLimitFrame();
		for(Point point : points) {
			paintScript.dotPaint(cx() + point.intX(), cy() + point.intY());
		}
		GHQ.setImageAlpha();
	}
}