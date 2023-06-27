package floatGame.modules;

import java.util.function.Supplier;

public abstract class FloatWeapon extends FloatModule {
	
	private Supplier<Double> angleIndicator;
	public FloatWeapon(double productCost, double fireCost, int cooldown) {
		super(productCost, fireCost, cooldown);
		autoWork = false; //Owner will invoke me
	}
	
	public double attackAngle() {
		if(owner() == null)
			return 0.0;
		if(angleIndicator == null) {
			return owner().lastDirectionLR().angle();
		}
		return angleIndicator.get();
	}
	
	public FloatWeapon setAngleIndicator(Supplier<Double> angleIndicator) {
		this.angleIndicator = angleIndicator;
		return this;
	}
	
	@Override
	public abstract FloatWeapon replicate();
}
