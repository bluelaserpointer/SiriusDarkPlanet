package physics.direction;

public enum DirectionVH implements MultiDirection {
	VERT, HORZ;
	
	public boolean isVert() {
		return this == VERT;
	}
	public boolean isHorz() {
		return this == HORZ;
	}
	@Override
	public boolean sub(double angle) {
		final double rad90 = Math.PI/2;
		return angle == rad90 || angle == -rad90;
	}
}
