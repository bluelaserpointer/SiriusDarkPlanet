package physics.hitShape;

public interface HasArea {
	public static HasArea blankArea = new HasArea() {
		@Override
		public int width() {
			return 0;
		}
		@Override
		public int height() {
			return 0;
		}
	};

	public abstract int width();
	public abstract int height();
	public default int maxSide() {
		return Math.max(width(), height());
	}
	public default int minSide() {
		return Math.min(width(), height());
	}
}
