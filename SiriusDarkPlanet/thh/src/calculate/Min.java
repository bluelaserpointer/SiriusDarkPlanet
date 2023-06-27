package calculate;

public class Min extends Calculation {
	private final HasNumber min;
	public Min(HasNumber hasMin) {
		min = hasMin;
	}
	public Min(Number min) {
		this.min = HasNumber.generateHasNumber(min);
	}
	@Override
	public String name() {
		return "NotNamedMin";
	}
	@Override
	protected Number calculate(Number value) {
		return value.doubleValue() >= min.doubleValue() ? value : min.getNumber();
	}

}
