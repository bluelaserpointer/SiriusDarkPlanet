package floatGame.unit;

public enum FloatStatus {
	HALF_SHIELD(1), SPEED_BOOST(1);
	
	public int limit;
	private FloatStatus(int limit) {
		this.limit = limit;
	}
}
