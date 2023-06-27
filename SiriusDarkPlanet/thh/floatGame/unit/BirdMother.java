package floatGame.unit;

import core.GHQ;
import floatGame.engine.FloatGame;
import floatGame.engine.FloatResource;
import floatGame.modules.BirdMaker;
import floatGame.modules.SpitFire;
import paint.ImageFrame;
import physics.HitGroup;
import physics.hitShape.Square;
import preset.unit.Unit;

public class BirdMother extends FloatSpaceship {
	
	int randomY;
	
	public BirdMother(HitGroup side) {
		super(ImageFrame.create("floatGame/image/spaceship.png"), 200, 1000, side);
		super.physics().setHitShape(new Square(this, 50));
		super.addModule(new BirdMaker(isFriend() ? FloatResource.birdMotherDockMax : FloatResource.birdMotherDockMax_initial));
		super.addModule(new SpitFire().setAngleIndicator(() -> super.targetAngle()));
		super.friendLightDotPaint = ImageFrame.create("floatGame/image/spaceship_greenLight.png");
		super.enemyLightDotPaint = ImageFrame.create("floatGame/image/spaceship_redLight.png");
	}
	
	@Override
	public BirdMother respawn(int spawnX, int spawnY) {
		super.respawn(spawnX, spawnY);
		randomY = GHQ.random2((int)(FloatGame.STAGE_H*0.7), (int)(FloatGame.STAGE_H*0.9));
		return this;
	}
	
	@Override
	public void idle() {
		final Unit closestEnemy = GHQ.stage().getNearstEnemy(this);
		super.setTarget((FloatUnit)closestEnemy);
		if(closestEnemy != null && closestEnemy.point().distance(this) < 600)
			point().approach(cx(), randomY, speed());
		else
			point().approach(cx() + enemyDirection().ex()*5, randomY, speed());
		super.idle();
	}
	
	@Override
	public void shoot() {
	}
	
	@Override
	public double initialSpeed() {
		return isFriend() ? FloatResource.birdMotherSpeed : FloatResource.birdMotherSpeed_initial;
	}

	@Override
	public String description() {
		return "<<BirdMother>> A aircraft carrier attacks medium altitude.";
	}
}
