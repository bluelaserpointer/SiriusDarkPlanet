package calculate;

public class Add extends Calculation{
	private final HasNumber add;
	public Add(HasNumber hasAdd) {
		add = hasAdd;
	}
	public Add(Number add) {
		this.add = HasNumber.generateHasNumber(add);
	}
	@Override
	protected Number calculate(Number value) {
		return value.intValue() + add.intValue();
	}
	@Override
	public String name() {
		return "NotNamedAdd";
	}
}