package floatGame.unit;

import core.GHQ;
import floatGame.modules.BalloonMaker;
import floatGame.modules.BirdMotherMaker;
import floatGame.modules.BomberMaker;
import floatGame.modules.Generator;
import floatGame.modules.PlatoonCamp;
import paint.ImageFrame;
import physics.HitGroup;
import preset.unit.Unit;

public class FlyingFactory extends FloatFactory {

	protected boolean dstYDecided;
	protected double dstY;
	public FlyingFactory(int hp, double energy, HitGroup side) {
		super(ImageFrame.create("floatGame/image/Ribs.png"), hp, energy, side);
		//additional modules for FlyingFactory
		module_max = 12;
		removeModule(PlatoonCamp.class);
		removeModule(BalloonMaker.class);
		addModule(new BirdMotherMaker());
		addModule(new BirdMotherMaker());
		addModule(new Generator());
		addModule(new Generator());
		addModule(new BomberMaker());
	}
	
	@Override
	public Unit respawn(int spawnX, int spawnY) {
		dstYDecided = false;
		return super.respawn(spawnX, spawnY);
	}
	@Override
	public void idle() {
		super.idle();
		if(!dstYDecided) {
			dstYDecided = true;
			//keep height
			dstY = point().doubleY();
		}
		boolean seeEnemy = false;
		final double mySpeed = 0.5;
		for(Unit unit : GHQ.stage().units) {
			if(unit.hitGroup().equals(hitGroup()))
				continue;
			if((unit instanceof FloatFactory || unit instanceof FloatArmy) && unit.point().intAbsDX(cx()) < 25) {
				if(unit instanceof FloatFactory)
					point().approach(unit.cx(), dstY, mySpeed);
				else //ignore normal army unit
					point().approach(cx() + enemyDirection().ex()*5, dstY, mySpeed);
				seeEnemy = true;
				break;
			}
		}
		if(!seeEnemy)
			point().approach(cx() + enemyDirection().ex()*5, dstY, mySpeed);
		super.idle();
	}
	
	@Override
	public String description() {
		return "<<?>> ?";
	}
}
