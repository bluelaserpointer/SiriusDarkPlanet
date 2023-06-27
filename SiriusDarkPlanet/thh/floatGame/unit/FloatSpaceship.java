package floatGame.unit;

import calculate.Damage;
import core.GHQ;
import floatGame.engine.FloatAchievement;
import floatGame.engine.FloatGame;
import floatGame.ui.HUD;
import paint.dot.DotPaint;
import physics.Dynam;
import physics.HitGroup;
import physics.direction.DirectionLR;
import physics.hitShape.Square;
import preset.unit.Unit;

public abstract class FloatSpaceship extends FloatUnit {

	protected boolean isGrounded;
	
	protected DotPaint friendLightDotPaint, enemyLightDotPaint;
	
	protected boolean engineCrushAchievementPosted;
	
	public FloatSpaceship(DotPaint dotPaint, int hp, double energy, HitGroup side) {
		super(dotPaint, hp, energy, side);
		physics().setPoint(new Dynam());
		physics().setHitShape(new Square(this, 75));
	}
	
	@Override
	public void idle() {
		//coordinate change
		super.idle();
		//movement and gravity
		final double xSpd = point().xSpeed(), ySpd = point().ySpeed();
		if (!GHQ.stage().hitObstacle_atNewPoint(this, xSpd, ySpd)) {
			point().moveBySpeed();
			processGravity();
		} else { //touch
			if (!GHQ.stage().hitObstacle_atNewPoint(this, -xSpd, ySpd)) {
				point().setXSpeed(-point().xSpeed());
			} else if (ySpd < 0) {
				point().setYSpeed(-point().ySpeed());
			} else { // vertical collisions makes damage
				isGrounded = true;
				double spd = point().speed();
				if(spd > 5.0) {
					this.damage(new FloatDamage(null, (int)(spd)));
				}
				//must do damage before stop velocity, for meteor achievement
				point().stop();
			}
		}
	}
	
	@Override
	protected void paintExtention() {
		super.paintExtention();
		if(friendLightDotPaint != null && enemyLightDotPaint != null) {
			GHQ.setImageAlpha((float)(Math.sin(GHQ.nowFrame()/10)/4 + 0.75));
			(hitGroup().equals(FloatUnit.friendSide) ? friendLightDotPaint : enemyLightDotPaint).dotPaint(point());
			GHQ.setImageAlpha();
		}
	}
	
	public static double engineCrushHPMaxRate = 0.6;
	protected final void processGravity() {
		point().addSpeed(0.0, Math.max(engineCrushHPMaxRate - hpRate(), 0.0)*2.0); //gravity
	}
	public void moveLeft() {
		point().addSpeed(-speed(), 0);
		lastDirectionLR = DirectionLR.LEFT;
	}
	
	public void moveRight() {
		point().addSpeed(speed(), 0);
		lastDirectionLR = DirectionLR.RIGHT;
	}
	
	public final void hover() {
		point().addSpeed(0, -speed());
		isGrounded = false;
	}

	public final void down() {
		if(!isGrounded)
			point().addSpeed(0, speed());
	}
	
	public final void brake() {
		point().mulSpeed(0.8);
	}
	
	public boolean isGrounded() {
		return isGrounded;
	}
	
	@Override
	public Unit respawn(int spawnX, int spawnY) {
		engineCrushAchievementPosted = false;
		return super.respawn(spawnX, spawnY);
	}

	@Override
	public int addHP(int value) {
		final int ret = super.addHP(value);
		if(hpRate() > engineCrushHPMaxRate)
			engineCrushAchievementPosted = false;
		return ret;
	}
	@Override
	public void damage(Damage damage) {
		super.damage(damage);
		if(!engineCrushAchievementPosted && isAlive() && hpRate() < engineCrushHPMaxRate) {
			if(lastDamage != null && lastDamage.bullet.shooter().equals(FloatGame.player)) {
				//only take achievement when player's shot lead health to < 60%
				HUD.postAchievement(FloatAchievement.ENGINE_CRUSH);
			}
			engineCrushAchievementPosted = true;
		}
	}
}
