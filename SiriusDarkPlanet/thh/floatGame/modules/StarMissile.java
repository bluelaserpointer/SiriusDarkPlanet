package floatGame.modules;

import static java.lang.Math.PI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import core.GHQ;
import core.GHQObject;
import floatGame.effects.ExplosionEffect;
import floatGame.unit.FloatDamage;
import floatGame.unit.FloatUnit;
import paint.ImageFrame;
import paint.dot.DotPaint;
import physics.HasPoint;
import physics.hitShape.Circle;
import preset.bullet.Bullet;
import preset.unit.Unit;

public class StarMissile extends FloatWeapon {

	public StarMissile() {
		super(50, 0.1, 50);
		icon = ImageFrame.create("floatGame/image/starMissileIcon.png");
		autoWork = true;
	}

	@Override
	public StarMissile replicate() {
		return new StarMissile();
	}

	@Override
	public boolean canWork() {
		return super.canWork() && owner.hasTarget() && owner.targetDistance() < 500;
	}
	@Override
	protected void work() {
		Bullet bullet = GHQ.stage().addBullet(new HomingMissile(owner, owner.target()));
		bullet.point().setSpeed_DA(10, attackAngle());
	}
	public static class HomingMissile extends Bullet {
		private final DotPaint paint = new DotPaint() {
			@Override
			public void dotPaint(int x, int y) {
				final Graphics2D g2 = GHQ.getG2D(FloatUnit.teamColor(hitGroup()), new BasicStroke(2));
				final int w = width(), h = height();
				g2.drawLine(x, y - h/2, x, y + h/2);
				g2.drawLine(x, y - h/2, x + w*4/5, y - h/2);
				g2.drawLine(x, y + h/2, x + w*4/5, y + h/2);
				final int radius = h/2;
				g2.drawArc(x + w*3/5, y - radius, w*2/5, radius*2, 90, -90);
				GHQ.getG2D(Color.BLACK, GHQ.stroke1);
			}
			@Override
			public int width() {
				return 20;
			}
			@Override
			public int height() {
				return 4;
			}
		};
		private HasPoint target;
		public HomingMissile(Unit shooterUnit, HasPoint target) {
			super(shooterUnit);
			name = "StarMissile";
			this.target = target;
			physics().setHitShape(new Circle(this, 20));
			setDamage(new FloatDamage(this, 15));
			limitFrame = 50;
			paintScript = paint;
			point().fastParaAdd_DASpd(10, GHQ.random2(PI/36), 10.0);
		}
		@Override
		public void idle() {
			super.idle();
			final double targetAngle = point().angleTo(target);
			angle().set(targetAngle);
			point().setSpeed_DA(point().speed() + 0.5, targetAngle);
			
		}
		@Override
		public void hitObject(GHQObject object) {
			GHQ.stage().addEffect(new ExplosionEffect(this, 0, 0, 5));
		}
	}
}
