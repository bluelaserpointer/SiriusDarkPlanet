package floatGame.unit;

import java.awt.Color;

import calculate.Damage;
import floatGame.engine.FloatGame;
import floatGame.engine.FloatResource;
import floatGame.modules.BalloonMaker;
import floatGame.modules.BirdMotherMaker;
import floatGame.modules.BomberMaker;
import floatGame.modules.FloatFactoryModule;
import floatGame.modules.FloatModule;
import floatGame.modules.Generator;
import floatGame.modules.PlatoonCamp;
import floatGame.modules.RepairStation;
import floatGame.ui.FactoryDetailUI;
import floatGame.ui.HUD;
import floatGame.ui.ShipDetailUI;
import paint.dot.DotPaint;
import physics.HitGroup;
import physics.Point;
import physics.hitShape.Square;

public abstract class FloatFactory extends FloatUnit {
	
	public FloatFactory(DotPaint dotPaint, int hp, double energy, HitGroup side) {
		super(dotPaint, hp, energy, side);
		physics().setPoint(new Point.IntPoint());
		physics().setHitShape(new Square(this, 75));
		//initial module for all kinds of factory
		addModule(new PlatoonCamp());
		addModule(new RepairStation());
		addModule(new Generator());
		addModule(new BalloonMaker(isFriend() ? FloatResource.balloonDockMax : FloatResource.balloonDockMax_initial));
		addModule(new BirdMotherMaker());
		addModule(new BomberMaker());
		//tell factory count
		if(isFriend()) {
			++HUD.friendBaseCount;
		} else {
			++HUD.enemyBaseCount;
		}
	}
	
	@Override
	public void idle() {
		super.idle();
		//show info for nearby player
		if(isFriend()) {
			if(FloatGame.player.point().distance(cx() - 50, cy()) < 50) {
				FactoryDetailUI.show(this);
			} else if(FloatGame.player.point().distance(cx() + 50, cy()) < 50) {
				ShipDetailUI.show(this);
			}
		}
	}
	
	@Override
	public boolean acceptModule(FloatModule module) {
		return module instanceof FloatFactoryModule;
	}
	
	@Override
	public void shoot() {
		// edit
	}
	
	@Override
	public void damage(Damage damage) {
		super.damage(damage);
		if(isAlive()) {
			if(isFriend())
				HUD.postMessage("base at " + cx() + " is under attack: " + (int)(hpRate()*100) + "%", Color.YELLOW);
		} else {
			if(isFriend()) {
				HUD.postMessage("base at " + cx() + " is destroyed!", Color.RED);
				HUD.decreaseFriendBaseCount();
			} else {
				HUD.postMessage("enemy base at " + cx() + " is destroyed!", Color.WHITE);
				HUD.decreaseEnemyBaseCount();
			}
		}
	}
	
	@Override
	public final double initialSpeed() {
		return 0.0;
	}
}
