package calculate.adjuster;

import calculate.HasNumber;

public class RangeChecker extends ValueChangeAdjuster {
	private final HasNumber min, max;
	public RangeChecker(String name, HasNumber min, HasNumber max) {
		super(name);
		this.min = min;
		this.max = max;
	}
	@Override
	public Number increased(Number oldNumber, Number newNumber) {
		return newNumber.doubleValue() < max.doubleValue() ? newNumber : max.getNumber();
	}
	@Override
	public Number decreased(Number oldNumber, Number newNumber) {
		return newNumber.doubleValue() > min.doubleValue() ? newNumber : min.getNumber();
	}
}