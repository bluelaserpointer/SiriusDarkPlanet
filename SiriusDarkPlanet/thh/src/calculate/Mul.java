package calculate;

public class Mul extends Calculation{
	private final HasNumber mul;
	public Mul(HasNumber hasMul) {
		mul = hasMul;
	}
	public Mul(Number mul) {
		this.mul = HasNumber.generateHasNumber(mul);
	}
	@Override
	protected Number calculate(Number value) {
		return value.doubleValue()*mul.doubleValue();
	}
	@Override
	public String name() {
		return "NotNamedMul";
	}
}