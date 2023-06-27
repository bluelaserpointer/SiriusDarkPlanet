package physics;

public interface HasAngle {
	public static HasAngle NULL_HAS_ANGLE = new HasAngle() {
		@Override
		public Angle angle() {
			return Angle.NULL_ANGLE;
		}
	};
	
	public abstract Angle angle();
	
	public static HasAngle generate() {
		return new HasAngle() {
			private final Angle angle = new Angle();
			@Override
			public Angle angle() {
				return angle;
			}
		};
	}
}
