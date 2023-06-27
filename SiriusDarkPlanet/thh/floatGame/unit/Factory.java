package floatGame.unit;

import paint.ImageFrame;
import physics.HitGroup;

public class Factory extends FloatFactory {

	public Factory(int hp, double energy, HitGroup side) {
		super(ImageFrame.create("floatGame/image/Factory.png"), hp, energy, side);
	}
	@Override
	public String description() {
		return "<<Basement>> Products units and repair & recharge neaby units.";
	}
}
