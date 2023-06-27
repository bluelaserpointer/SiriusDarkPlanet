package floatGame.modules;

import java.awt.Color;

import core.GHQ;
import floatGame.effects.ExplosionEffect;
import floatGame.engine.FloatGame;
import floatGame.unit.FloatDamage;
import floatGame.unit.FloatFactory;
import floatGame.unit.FloatUnit;
import paint.ImageFrame;
import paint.dot.DotPaint;
import physics.hitShape.Circle;
import preset.bullet.Bullet;
import preset.unit.Unit;

public class Meteor extends FloatWeapon {

	public Meteor() {
		super(20, 25, 25);
		icon = ImageFrame.create("floatGame/image/MeteorIcon.png");
	}

	@Override
	public void work() {
		GHQ.stage().addBullet(new MeteorBullet(owner)).point().stop();
	}
	public static class MeteorBullet extends Bullet {
		private final DotPaint paint = new DotPaint() {
			@Override
			public void dotPaint(int x, int y) {
				GHQ.getG2D(FloatUnit.teamColor(hitGroup()), GHQ.stroke5)
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
		public MeteorBullet(Unit shooterUnit) {
			super(shooterUnit);
			name = "MetorBullet";
			physics().setHitShape(new Circle(this, 20));
			setDamage(new FloatDamage(this, 50));
			limitFrame = 2000;
			paintScript = paint;
		}
		@Override
		public void idle() {
			super.idle();
			point().addSpeed(0.0, 1.0); //gravity
			//effect
			GHQ.stage().addEffect(new ExplosionEffect(this, 0, 0, 5));
		}
		@Override
		protected boolean entityCollision() {
			boolean hit = point().intY() > FloatGame.STAGE_H*0.95;
			if (!hit) {
				for(Unit unit : GHQ.stage().units) { //needed for bomber hit flyingFactory
					if(unit instanceof FloatFactory && super.intersects(unit)) {
						hit = true;
					}
				}
			}
			if (hit) { // damage in range
				for(Unit unit : GHQ.stage().units) {
					if(unit.hitableGroup(this) && unit.point().inRange(this, 100)) {
						unit.damage(damage);
					}
				}
			}
			return hit;
		}
	}
	@Override
	public Meteor replicate() {
		return new Meteor();
	}
}
