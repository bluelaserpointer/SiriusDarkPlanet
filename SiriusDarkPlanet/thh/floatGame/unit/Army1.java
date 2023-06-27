package floatGame.unit;

import core.GHQ;
import floatGame.engine.FloatResource;
import floatGame.modules.RepairStation;
import floatGame.modules.SpitFire;
import paint.ImageFrame;
import physics.HitGroup;
import physics.hitShape.Square;

public class Army1 extends FloatArmy {
	public Army1(HitGroup side) {
		super(ImageFrame.create("floatGame/image/Army.png"),
				side.hitableWith(enemySide) ? FloatResource.armyHP : FloatResource.armyHP_initial,
				side.hitableWith(enemySide) ?  FloatResource.armyEnergy : FloatResource.armyEnergy_initial, side);
		super.physics().setHitShape(new Square(this, 20));
		super.addModule(new SpitFire().setAngleIndicator(() -> point().angleTo(target())));
		super.addModule(new RepairStation());
	}
	@Override
	public void idle() {
		super.idle();
		super.setTarget((FloatUnit)GHQ.stage().getNearstEnemy(this));
		if(super.hasTarget() && super.targetDistance() < 500) {
			lastContactFrame = GHQ.nowFrame();
			shoot();
		}
	}
	@Override
	public void shoot() {
		super.modules().get(0).tryWork();
	}
	@Override
	public double initialSpeed() {
		return isFriend() ? FloatResource.armySpeed : FloatResource.armySpeed_initial;
	}

	@Override
	public String description() {
		return "<<Army>> Repair & recharge nearby units.";
	}
}
