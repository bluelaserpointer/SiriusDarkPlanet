package floatGame.modules;

import java.awt.BasicStroke;
import java.awt.Color;

import core.GHQ;
import core.GHQObject;
import floatGame.unit.FloatDamage;
import floatGame.unit.FloatUnit;
import paint.ImageFrame;
import paint.dot.DotPaint;
import physics.hitShape.Circle;
import preset.bullet.Bullet;
import preset.unit.Unit;

public class StarDust extends FloatWeapon {

	public StarDust() {
		super(20, 0.1, 4);
		icon = ImageFrame.create("floatGame/image/starDustIcon.png");
	}

	@Override
	public void work() {
		Bullet bullet = GHQ.stage().addBullet(new NarrowBullet(owner, attackAngle()));
		bullet.point().addY_allowsAngle(owner.getDotPaint().width()/2, attackAngle());
		bullet.point().setSpeed_DA(50, attackAngle());
		bullet.point().addSpeed(owner.point().xSpeed(), owner.point().ySpeed());
	}
	public static class NarrowBullet extends Bullet {
		private final DotPaint paint = new DotPaint() {
			@Override
			public void dotPaint(int x, int y) {
				GHQ.getG2D(FloatUnit.teamColor(hitGroup()), new BasicStroke(2))
					.drawLine(x, y, x + width(), y);
				GHQ.getG2D(Color.BLACK, GHQ.stroke1);
			}
			@Override
			public int width() {
				return 10;
			}
			@Override
			public int height() {
				return 1;
			}
		};
		boolean setInitialAngle;
		double initialAngle;
		public NarrowBullet(Unit shooterUnit, double bulletDrawAngle) {
			super(shooterUnit);
			name = "MAGIC_MISSILE";
			initialAngle = bulletDrawAngle;
			physics().setHitShape(new Circle(this, 20));
			accel = 1.07;
			setDamage(new FloatDamage(this, 2));
			limitFrame = 15;
			paintScript = paint;
		}
		@Override
		public void hitObject(GHQObject object) {
			//GHQ.stage().addEffect(new THH_EffectLibrary.MissileHitEF(this, true));
		}
		@Override
		public void paint() {
			paintScript.dotPaint_turn(point(), initialAngle);
		}
	}
	@Override
	public StarDust replicate() {
		return new StarDust();
	}
}
