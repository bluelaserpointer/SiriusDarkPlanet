package preset.unit;

public abstract class InputProcesser {
	public static final InputProcesser NULL_ACTION_PROCESSER = new InputProcesser(Unit.NULL_UNIT) {
		@Override
		public void process() {}
	};
	protected final Unit UNIT;
	public InputProcesser(Unit unit) {
		UNIT = unit;
	}
	public abstract void process();
}