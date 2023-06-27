package floatGame.modules;

import core.GHQ;
import floatGame.unit.FloatStatus;
import floatGame.unit.FloatUnit;
import preset.unit.Unit;

public class SpeedSupplier extends FloatWeapon {

	public SpeedSupplier() {
		super(100, 50, 10);
	}

	@Override
	public SpeedSupplier replicate() {
		return new SpeedSupplier();
	}

	@Override
	protected void work() {
		for(Unit unit : GHQ.stage().units) {
			if(!unit.hitableGroup(owner) && unit.point().inRange(owner, 200)) {
				((FloatUnit)unit).addStatus(FloatStatus.SPEED_BOOST);
			}
		}
	}

}
