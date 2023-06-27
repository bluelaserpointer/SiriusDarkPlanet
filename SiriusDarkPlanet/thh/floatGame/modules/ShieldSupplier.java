package floatGame.modules;

import core.GHQ;
import floatGame.unit.FloatStatus;
import floatGame.unit.FloatUnit;
import preset.unit.Unit;

public class ShieldSupplier extends FloatWeapon {

	public ShieldSupplier() {
		super(100, 50, 10);
	}

	@Override
	protected void work() {
		for(Unit unit : GHQ.stage().units) {
			if(!unit.hitableGroup(owner) && unit.point().inRange(owner, 200)) {
				((FloatUnit)unit).addStatus(FloatStatus.HALF_SHIELD);
			}
		}
	}

	@Override
	public ShieldSupplier replicate() {
		return new ShieldSupplier();
	}

}
