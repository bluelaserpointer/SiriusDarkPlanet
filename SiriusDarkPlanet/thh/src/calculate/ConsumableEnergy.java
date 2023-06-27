package calculate;

import calculate.adjuster.RangeChecker;

public class ConsumableEnergy implements Consumables {
	protected ValueWithCalculation value = new ValueWithCalculation(),
			min = new ValueWithCalculation(Integer.MIN_VALUE),
			max = new ValueWithCalculation(Integer.MAX_VALUE),
			defaultValue = new ValueWithCalculation();
	{
		defaultValue.addCalculation(new Calculation() {
			@Override
			public Number calculate(Number number) {
				return value.doubleValue();
			}
		});
		value.pushAdjuster(new RangeChecker("RangeChecker", new HasNumber() {
			@Override
			public Number getNumber() {
				return min.getNumber();
			}
		}, new HasNumber() {
			@Override
			public Number getNumber() {
				return max.getNumber();
			}
		}));
	}
	public ConsumableEnergy() {}
	public ConsumableEnergy(Number initialValue) {
		value.setValue(initialValue);
	}
	public ConsumableEnergy(Calculation...calculations) {
		value.addCalculation(calculations);
	}
	//Set_Value
	public ConsumableEnergy directSetValue(Number value) {
		this.value.directSetValue(value);
		return this;
	}
	public ConsumableEnergy directSetValue(HasNumber hasNumber) {
		return directSetValue(hasNumber.getNumber());
	}
	public ConsumableEnergy setValue(Number value) {
		this.value.setValue(value);
		return this;
	}
	public ConsumableEnergy setValue(HasNumber hasNumber) {
		return setValue(hasNumber.getNumber());
	}
	@Override
	public void setNumber(Number newValue) {
		value.setValue(newValue);
	}
	@Override
	public double consume(Number amount) {
		final double oldValue = value.doubleValue();
		value.setValue(value.doubleValue() - amount.doubleValue());
		return oldValue - value.doubleValue();
	}
	public ConsumableEnergy reset() {
		value.setValue(defaultValue.getNumber());
		return this;
	}
	//Set_MinMax
	public ConsumableEnergy setMin(Number value) {
		this.min = new ValueWithCalculation(value);
		return this;
	}
	public ConsumableEnergy setMin(Calculation formula) {
		this.min = new ValueWithCalculation(formula);
		return this;
	}
	public ConsumableEnergy setMax(Number value) {
		this.max = new ValueWithCalculation(value);
		return this;
	}
	public ConsumableEnergy setMax(Calculation formula) {
		this.max = new ValueWithCalculation(formula);
		//System.out.println("max: " + max());
		return this;
	}
	public ConsumableEnergy setToMin() {
		value.setValue(min.getNumber());
		return this;
	}
	public ConsumableEnergy setToMax() {
		value = max;
		return this;
	}
	@Override
	public Number min() {
		return min.getNumber();
	}
	@Override
	public Number max() {
		return max.getNumber();
	}
	public ValueWithCalculation minCalculation() {
		return min;
	}
	public ValueWithCalculation maxCalculation() {
		return max;
	}
	public ConsumableEnergy setMinMax(Number min, Number max) {
		return setMin(min).setMax(max);
	}
	//Set-DefaultValue
	public ConsumableEnergy setDefault(Number defaultValue) {
		this.defaultValue = new ValueWithCalculation(defaultValue);
		return this;
	}
	public ConsumableEnergy setDefault(Calculation defaultValue) {
		this.defaultValue = new ValueWithCalculation(defaultValue);
		return this;
	}
	public ConsumableEnergy setDefaultToMin() {
		defaultValue = min;
		return this;
	}
	public ConsumableEnergy setDefaultToMax() {
		defaultValue = max;
		return this;
	}
	public Number defaultValue() {
		return defaultValue != null ? defaultValue.getNumber() : null;
	}
	public ValueWithCalculation defaultCalculation() {
		return defaultValue;
	}
	//information
	public double getRate() {
		if(max == null || min == null) {
			System.out.println("! --  getRate() called with null max or min");
			return 0.0;
		}
		return (double)(value.doubleValue() - min.doubleValue())/(max.doubleValue() - min.doubleValue());
	}
	public int lastIncreasedFrame() {
		return value.lastIncreaseFrame();
	}
	public int lastDecreasedFrame() {
		return value.lastDecreaseFrame();
	}
	public int lastUnchangedFrame() {
		return value.lastUnchangeFrame();
	}
	public int lastChangedFrame() {
		return Math.max(lastIncreasedFrame(), lastDecreasedFrame());
	}
	public int lastSettedFrame() {
		return Math.max(lastChangedFrame(), lastUnchangedFrame());
	}
	public double lastChangedAmount() {
		return value.lastChangeAmount();
	}
	public double lastIncreaseAmount() {
		return value.lastIncreaseAmount();
	}
	public double lastDecreaseAmount() {
		return value.lastDecreaseAmount();
	}
	public double lastChangeAmount() {
		return value.lastChangeAmount();
	}
	public double lastSetDiff() {
		return value.lastSetDiff();
	}
	public double lastSetDiff_underZero() {
		final double VALUE = lastSetDiff();
		return VALUE < 0 ? -VALUE : 0;
	}
	public double lastSetDiff_overZero() {
		final double VALUE = lastSetDiff();
		return VALUE > 0 ? VALUE : 0;
	}
	public void clearLastChange() {
		value.clearLastChange();
	}
	public void clearLastIncrease() {
		value.clearLastIncrease();
	}
	public void clearLastDecrease() {
		value.clearLastDecrease();
	}
	public void clearLastUnchange() {
		value.clearLastUnchange();
	}
	public void clearLastSet() {
		value.clearLastSet();
	}
	@Override
	public Number getNumber() {
		return value.getNumber();
	}
	@Override
	public Number minNumber() {
		return min.getNumber();
	}
	@Override
	public Number maxNumber() {
		return max.getNumber();
	}
	@Override
	public Number defaultNumber() {
		return defaultValue.getNumber();
	}
	public ValueWithCalculation getValueWithCalculation_value() {
		return value;
	}
	public ValueWithCalculation getValueWithCalculation_min() {
		return min;
	}
	public ValueWithCalculation getValueWithCalculation_max() {
		return max;
	}
	public ValueWithCalculation getValueWithCalculation_default() {
		return defaultValue;
	}
}
