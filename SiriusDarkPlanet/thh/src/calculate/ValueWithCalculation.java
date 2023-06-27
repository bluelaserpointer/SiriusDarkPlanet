package calculate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import calculate.adjuster.ValueChangeAdjuster;
import core.GHQ;
import core.HasName;

public class ValueWithCalculation implements Serializable, HasNumber, HasName {
	private static final long serialVersionUID = -282173078001032176L;
	private Number value;
	private CompoundCalculation calculations;
	private final LinkedList<ValueChangeAdjuster> adjusters = new LinkedList<ValueChangeAdjuster>();
	private int lastIncreaseFrame, lastDecreaseFrame, lastUnchangeFrame;
	private double lastIncreaseAmount, lastDecreaseAmount;
	private double lastChangeAmount;
	
	public ValueWithCalculation() {
		value = 0;
		calculations = new CompoundCalculation();
	}
	public ValueWithCalculation(Number initialValue) {
		value = initialValue;
		calculations = new CompoundCalculation();
	}
	public ValueWithCalculation(Calculation...calculations) {
		value = 0;
		this.calculations = new CompoundCalculation(calculations);
	}
	public ValueWithCalculation(Number initialValue, Calculation...calculations) {
		value = initialValue;
		this.calculations = new CompoundCalculation(calculations);
	}
	//control
	public void directSetValue(Number newValue) {
		value = newValue;
	}
	public void setValue(Number newValue) {
		double diff;
		//adjust value change
		for(ValueChangeAdjuster adjuster : adjusters) {
			diff = newValue.doubleValue() - value.doubleValue();
			if(diff > 0) { //increased event
				newValue = adjuster.changed(value, newValue);
				newValue = adjuster.increased(value, newValue);
			}else if(diff < 0) { //decreased event
				newValue = adjuster.changed(value, newValue);
				newValue = adjuster.decreased(value, newValue);
			}else { //unchanged event
				newValue = adjuster.unchanged(value, newValue);
			}
		}
		//record value change
		diff = newValue.doubleValue() - value.doubleValue();
		if(diff > 0) { //increased record
			lastIncreaseFrame = GHQ.nowFrame();
			lastChangeAmount = lastIncreaseAmount = diff;
		}else if(diff < 0) { //decreased record
			lastDecreaseFrame = GHQ.nowFrame();
			lastChangeAmount = diff;
			lastDecreaseAmount = -diff;
		}else { //unchanged record
			lastChangeAmount = 0;
			lastUnchangeFrame = GHQ.nowFrame();
		}
		this.directSetValue(newValue);
	}
	public void addCalculation(Calculation...calculations) {
		this.calculations.add(calculations);
	}
	public void removeCalculation(Calculation calculation) {
		calculations.remove(calculation);
	}
	public void pushAdjuster(ValueChangeAdjuster adjuster) {
		adjusters.push(adjuster);
	}
	public void addAdjuster(ValueChangeAdjuster adjuster) {
		adjusters.add(adjuster);
	}
	public void removeOneAdjuster(String name) {
		final Iterator<ValueChangeAdjuster> ite = adjusters.iterator();
		while(ite.hasNext()) {
			if(ite.next().name() == name) {
				ite.remove();
				break;
			}
		}
	}
	public void removeAllAdjuster(String name) {
		final Iterator<ValueChangeAdjuster> ite = adjusters.iterator();
		while(ite.hasNext()) {
			if(ite.next().name() == name)
				ite.remove();
		}
	}
	public void clearLastIncrease() {
		lastIncreaseFrame = GHQ.nowFrame();
		lastIncreaseAmount = 0;
	}
	public void clearLastDecrease() {
		lastDecreaseFrame = GHQ.nowFrame();
		lastDecreaseAmount = 0;
	}
	public void clearLastUnchange() {
		lastUnchangeFrame = GHQ.nowFrame();
	}
	public void clearLastChange() {
		clearLastIncrease();
		clearLastDecrease();
	}
	public void clearLastSet() {
		clearLastChange();
		clearLastUnchange();
	}
	//information
	public CompoundCalculation calculation() {
		return calculations;
	}
	public LinkedList<ValueChangeAdjuster> adjusters() {
		return adjusters;
	}
	public int lastIncreaseFrame() {
		return lastIncreaseFrame;
	}
	public int lastDecreaseFrame() {
		return lastDecreaseFrame;
	}
	public int lastUnchangeFrame() {
		return lastUnchangeFrame;
	}
	public int lastChangeFrame() {
		return Math.max(lastIncreaseFrame(), lastDecreaseFrame());
	}
	public int lastSetFrame() {
		return Math.max(lastChangeFrame(), lastUnchangeFrame());
	}
	public double lastIncreaseAmount() {
		return lastIncreaseAmount;
	}
	public double lastDecreaseAmount() {
		return lastDecreaseAmount;
	}
	public double lastChangeAmount() {
		return lastIncreaseFrame() > lastDecreaseFrame() ? lastIncreaseAmount() : lastDecreaseAmount();
	}
	public double lastSetDiff() {
		return lastChangeAmount;
	}
	@Override
	public String name() {
		return "NotNamedValueWithCalculation";
	}
	@Override
	public Number getNumber() {
		return calculations.calculate(value);
	}
}
