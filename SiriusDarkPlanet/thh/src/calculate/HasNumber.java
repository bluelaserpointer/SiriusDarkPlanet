package calculate;

import core.GHQ;

public interface HasNumber {
	public abstract Number getNumber();
	
	public default int intValue() {
		return getNumber().intValue();
	}
	public default double doubleValue() {
		return getNumber().doubleValue();
	}
	public default boolean isNONE() {
		return intValue() == GHQ.NONE;
	}
	public default Number minNumber() {
		return Double.NEGATIVE_INFINITY;
	}
	public default Number maxNumber() {
		return Double.POSITIVE_INFINITY;
	}
	public default Number defaultNumber() {
		return 0.0;
	}
	public default int intMin() {
		return minNumber().intValue();
	}
	public default double doubleMin() {
		return minNumber().doubleValue();
	}
	public default int intMax() {
		return maxNumber().intValue();
	}
	public default double doubleMax() {
		return maxNumber().doubleValue();
	}
	public default int intDefault() {
		return defaultNumber().intValue();
	}
	public default double doubleDefault() {
		return defaultNumber().doubleValue();
	}
	public default boolean isMin() {
		return doubleValue() == doubleMin();
	}
	public default boolean isMax() {
		return doubleValue() == doubleMin();
	}
	public default boolean isDefault() {
		return doubleValue() == doubleDefault();
	}
	public static HasNumber generateHasNumber(Number number) {
		return new HasNumber() {
			@Override
			public Number getNumber() {
				return number;
			}
		};
	}
}
