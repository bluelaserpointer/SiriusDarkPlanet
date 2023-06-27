package floatGame.modules;

public abstract class FloatFactoryModule extends FloatModule {

	public FloatFactoryModule(double productCost, double workCost, int cooldown) {
		super(productCost, workCost, cooldown);
	}
	
	@Override
	public abstract FloatFactoryModule replicate();
}
