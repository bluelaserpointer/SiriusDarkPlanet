package calculate;

public class Setter extends Calculation{
	public static final Setter ZERO_SETTER = new Setter(0);
	private HasNumber value;
	public Setter(HasNumber hasValue) {
		value = hasValue;
	}
	public Setter(Number value) {
		this.value = HasNumber.generateHasNumber(value);
	}
	public Setter() {}
	public void setNumber(HasNumber hasValue) {
		this.value = hasValue;
	}
	public void setNumber(Number value) {
		this.value = HasNumber.generateHasNumber(value);
	}
	@Override
	protected final Number calculate(Number value) {
		return this.value.getNumber();
	}
	@Override
	public String name() {
		return "NotNamedSetter";
	}
}
