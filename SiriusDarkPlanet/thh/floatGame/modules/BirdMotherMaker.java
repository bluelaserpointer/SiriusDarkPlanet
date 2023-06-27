package floatGame.modules;

import floatGame.unit.BirdMother;
import floatGame.unit.FloatUnit;
import paint.ImageFrame;

public class BirdMotherMaker extends FloatFactoryModule {

	public BirdMotherMaker() {
		super(150, 50, 1000);
		icon = ImageFrame.create("floatGame/image/birdMotherMakerIcon.png");
	}

	@Override
	protected void work() {
		FloatUnit.quickSpawn(new BirdMother(hitGroup()), owner.point());
	}

	@Override
	public BirdMotherMaker replicate() {
		return new BirdMotherMaker();
	}

}
