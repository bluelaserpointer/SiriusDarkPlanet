package floatGame.modules;

import floatGame.unit.Bomber;
import floatGame.unit.FloatUnit;
import paint.ImageFrame;

public class BomberMaker extends FloatFactoryModule {

	public BomberMaker() {
		super(150, 50, 1250);
		icon = ImageFrame.create("floatGame/image/bomberMaker.png");
	}

	@Override
	protected void work() {
		FloatUnit.quickSpawn(new Bomber(hitGroup()), owner.point());
	}

	@Override
	public BomberMaker replicate() {
		return new BomberMaker();
	}

}
