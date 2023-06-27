package floatGame.unit;

import core.GHQ;
import floatGame.engine.FloatGame;
import floatGame.engine.FloatResource;
import floatGame.modules.Meteor;
import floatGame.modules.SpitFire;
import paint.ImageFrame;
import physics.HitGroup;
import physics.hitShape.Square;
import preset.unit.Unit;

public class Bomber extends FloatSpaceship {

	int randomY;
	boolean seeEnemy;
	
	public Bomber(HitGroup side) {
		super(ImageFrame.create("floatGame/image/Bomber.png"), side.hitableWith(enemySide) ? FloatResource.bomberHP : FloatResource.bomberHP_initial, 500, side);
		super.physics().setHitShape(new Square(this, 50));
		super.addModule(new Meteor());
		super.addModule(new SpitFire().setAngleIndicator(() -> super.targetAngle()));
	}

	@Override
	public Bomber respawn(int spawnX, int spawnY) {
		super.respawn(spawnX, spawnY);
		randomY = GHQ.random2((int)(FloatGame.STAGE_H*0.1), (int)(FloatGame.STAGE_H*0.3));
		return this;
	}
	
	@Override
	public void idle() {
		final Unit closestEnemy = GHQ.stage().getNearstEnemy(this);
		super.setTarget((FloatUnit)closestEnemy);
		int dstY;
		if(closestEnemy != null && closestEnemy.point().distance(this) < 600) {
			//evading action
			//dstY = randomY + (int)(FloatGame.STAGE_H*0.07*Math.sin(GHQ.nowFrame()/30));
			dstY = randomY;
		} else {
			dstY = randomY;
		}
		seeEnemy = false;
		for(Unit unit : GHQ.stage().units) {
			if(unit.hitGroup().equals(hitGroup()))
				continue;
			if((unit instanceof FloatFactory || unit instanceof FloatArmy) && unit.point().intAbsDX(cx()) < 25) {
				if(unit instanceof FloatFactory)
					point().approach(unit.cx(), dstY, speed());
				else //ignore normal army unit
					point().approach(cx() + enemyDirection().ex()*5, dstY, speed());
				shoot();
				seeEnemy = true;
				break;
			}
		}
		if(!seeEnemy)
			point().approach(cx() + enemyDirection().ex()*5, dstY, speed());
		super.idle();
	}

	@Override
	public void shoot() {
		modules.get(0).tryWork();
	}

	@Override
	public double initialSpeed() {
		return isFriend() ? FloatResource.bomberSpeed : FloatResource.bomberSpeed_initial;
	}
	@Override
	public String description() {
		return "<<Bomber>> Bomb ground forces.";
	}
}
