package physics.direction;

import physics.HasPoint;
import physics.Point;

public enum DirectionLR {
	LEFT, RIGHT;
	
	public int getID() {
		switch(this) {
		case LEFT:
			return 0;
		case RIGHT:
			return 1;
		default:
			return -1;
		}
	}
	public boolean isRight() {
		return this == RIGHT;
	}
	public boolean isLeft() {
		return this == LEFT;
	}
	public int plusMinusR() {
		return this == RIGHT ? +1 : -1;
	}
	public int plusMinusL() {
		return this == LEFT ? +1 : -1;
	}
	public double angle() {
		return isRight() ? 0.0 : -Math.PI;
	}
	public int ex() {
		return isRight() ? +1 : -1;
	}
	public int ey() {
		return 0;
	}
	public static DirectionLR directionTo(Point myPoint, Point targetPoint) {
		return myPoint.intX() < targetPoint.intX() ? RIGHT : LEFT;
	}
	public static DirectionLR directionTo(HasPoint me, HasPoint target) {
		return directionTo(me.point(), target.point());
	}
	public DirectionLR flip() {
		return this == RIGHT ? LEFT : RIGHT;
	}
}
