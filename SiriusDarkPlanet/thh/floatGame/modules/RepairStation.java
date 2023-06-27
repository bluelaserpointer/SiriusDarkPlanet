package floatGame.modules;

import core.GHQ;
import floatGame.unit.FloatFactory;
import floatGame.unit.FloatUnit;
import paint.ImageFrame;
import preset.unit.Unit;

public class RepairStation extends FloatFactoryModule {

	protected int repairSpeed = 2;
	protected double chargeSpeed = 2;
	public RepairStation() {
		super(100, 0, 0);
		icon = ImageFrame.create("floatGame/image/RepairIcon.png");
	}

	@Override
	protected void work() {
		for(Unit unit : GHQ.stage().units) {
			if(!unit.hitGroup().hitableWith(hitGroup()) && !owner.getClass().isInstance(unit) && !(unit instanceof FloatFactory) && unit.point().distance(owner) < 100) { //include myself
				FloatUnit funit = (FloatUnit)unit;
				int neededHP = Math.min(repairSpeed, funit.hpMax() - funit.hp());
				if(owner.energy() > neededHP) {
					funit.addHP(neededHP);
					owner.addEnergy(-neededHP);
				}
				double neededEnergy = Math.min(chargeSpeed, funit.energyMax() - funit.energy());
				if(owner.energy() > neededEnergy) {
					funit.addEnergy(neededEnergy);
					owner.addEnergy(-neededEnergy);
				}
				if(owner instanceof FloatFactory)
					funit.setBaseFactory((FloatFactory)owner);
			}
		}
	}

	@Override
	public RepairStation replicate() {
		return new RepairStation();
	}

}
