package calculate;

import java.util.Arrays;
import java.util.List;

import core.GHQ;
import core.HasName;

public abstract class Calculation implements HasNumber, HasName {
	public static final Calculation NULL_CALCULATION = new Calculation("EmptyCalculation") {
		@Override
		protected Number calculate(Number value) {
			return value;
		}
	};
	protected final int generatedFrame;
	protected final String name;
	
	public Calculation() {
		generatedFrame = GHQ.nowFrame();
		this.name = "NotNamedCalculation";
	}
	public Calculation(String name) {
		generatedFrame = GHQ.nowFrame();
		this.name = name;
	}
	
	public void deleted() {}
	
	protected abstract Number calculate(Number value);
	public final Calculation find(String...names) {
		return find(Arrays.asList(names));
	}
	public Calculation find(List<String> names) {
		return null;
	}
	public boolean add(Calculation calculation) {
		return false;
	}
	public boolean remove(Calculation calculation) {
		return false;
	}
	public final boolean insert(Calculation calculation, String...names) {
		final Calculation target = find(names);
		if(target == null)
			return false;
		target.add(calculation);
		return true;
	}
	public final boolean remove(Calculation calculation, String...names) {
		final Calculation target = find(names);
		if(target == null)
			return false;
		target.remove(calculation);
		return true;
	}
	@Override
	public Number getNumber() {
		return calculate(0);
	}
	public int generatedFrame() {
		return generatedFrame;
	}

	@Override
	public String name() {
		return name;
	}
}
