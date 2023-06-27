package floatGame.modules;

import floatGame.unit.Army1;
import floatGame.unit.FloatUnit;
import paint.ImageFrame;

public class PlatoonCamp extends FloatFactoryModule {

	public PlatoonCamp() {
		super(20, 0.5, 500);
		icon = ImageFrame.create("floatGame/image/CampIcon.png");
	}

	@Override
	public PlatoonCamp replicate() {
		return new PlatoonCamp();
	}

	@Override
	protected void work() {
		FloatUnit.quickSpawn(new Army1(hitGroup()), owner.point(), 30, 0);
		FloatUnit.quickSpawn(new Army1(hitGroup()), owner.point(), 0, 0);
		FloatUnit.quickSpawn(new Army1(hitGroup()), owner.point(), -30, 0);
	}

}
