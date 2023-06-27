package calculate.adjuster;

import core.HasName;

public class ValueChangeAdjuster implements HasName {
	private String name;
	public ValueChangeAdjuster(String name) {
		this.name = name;
	}
	//event
	public Number increased(Number oldNumber, Number newNumber) {
		return newNumber;
	}
	public Number decreased(Number oldNumber, Number newNumber) {
		return newNumber;
	}
	public Number changed(Number oldNumber, Number newNumber) {
		return newNumber;
	}
	public Number unchanged(Number oldNumber, Number newNumber) {
		return newNumber;
	}
	@Override
	public String name() {
		return name;
	}
}
