package calculate;

public class Max extends Calculation {
	private final HasNumber max;
	public Max(HasNumber hasMin) {
		max = hasMin;
	}
	public Max(Number max) {
		this.max = HasNumber.generateHasNumber(max);
	}
	@Override
	public String name() {
		return "NotNamedMin";
	}
	@Override
	protected Number calculate(Number value) {
		return value.doubleValue() <= max.doubleValue() ? value : max.getNumber();
	}

}

