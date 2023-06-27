package floatGame.unit;

import core.GHQ;
import floatGame.engine.FloatGame;
import floatGame.engine.FloatResource;
import floatGame.modules.SpitFire;
import floatGame.modules.StarMissile;
import paint.ImageFrame;
import physics.HitGroup;
public class Balloon extends FloatSpaceship {
	
	int randomX, randomY;
	
	public Balloon(HitGroup side) {
		super(ImageFrame.create("floatGame/image/balloon.png"), 150, 100, side);
		super.friendLightDotPaint = ImageFrame.create("floatGame/image/balloon_greenLight.png");
		super.enemyLightDotPaint = ImageFrame.create("floatGame/image/balloon_redLight.png");
		super.addModule(new StarMissile());
		super.addModule(new SpitFire().setAngleIndicator(() -> super.targetAngle()));
	}
	
	@Override
	public void idle() {
		super.idle();
		final FloatUnit enemy = (FloatUnit)GHQ.stage().getNearstEnemy(this);
		super.setTarget(enemy);
		point().approach(randomX, randomY, speed());
	}

	@Override
	public Balloon respawn(int spawnX, int spawnY) {
		super.respawn(spawnX, spawnY);
		randomX = GHQ.random2(-200 + spawnX, +200 + spawnX);
		randomY = GHQ.random2((int)(FloatGame.STAGE_H*0.1), (int)(FloatGame.STAGE_H*0.7));
		return this;
	}
	@Override
	public void shoot() {
		super.modules.get(0).tryWork();
	}
	@Override
	public double initialSpeed() {
		return isFriend() ? FloatResource.balloonSpeed : FloatResource.balloonSpeed_initial;
	}

	@Override
	public String description() {
		return "<<Balloon>> Protect the sky above the base.";
	}
}
