package floatGame.modules;

import paint.ImageFrame;

public class Generator extends FloatFactoryModule {

	public double energyProductSpeed = 1.0;
	
	public Generator() {
		super(100, 0, 0);
		icon = ImageFrame.create("floatGame/image/GeneratorIcon.png");
	}

	@Override
	protected void work() {
		owner.addEnergy(energyProductSpeed);
	}

	@Override
	public Generator replicate() {
		return new Generator();
	}

}
