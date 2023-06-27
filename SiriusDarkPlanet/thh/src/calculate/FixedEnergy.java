package calculate;

public class FixedEnergy extends Energy {
	public static final FixedEnergy ZERO_FIXED_ENERGY = new FixedEnergy(0);
	public FixedEnergy(Number number) {
		super(number);
	}
	@Override
	public double consume(Number amount) {
		return 0;
	}
	@Override
	public void setNumber(Number amount) {}
}
