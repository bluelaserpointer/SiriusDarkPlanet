package physics.direction;

import java.util.Random;

import physics.Point;

public enum Direction4 implements MonoDirection {
	W, D, S, A;
	
	public boolean isVert() {
		return this == W || this == S;
	}
	public boolean isHorz() {
		return this == A || this == D;
	}
	public boolean isVH(DirectionVH vh) {
		return vh.isVert() ? isVert() : isHorz();
	}
	public int x() {
		switch(this) {
		case D:
			return 1;
		case A:
			return -1;
		default:
			return 0;
		}
	}
	public int y() {
		switch(this) {
		case S:
			return 1;
		case W:
			return -1;
		default:
			return 0;
		}
	}
	public Direction8TC30 left30() {
		switch(this) {
		case W:
			return Direction8TC30.WWA;
		case D:
			return Direction8TC30.DDW;
		case S:
			return Direction8TC30.SSD;
		case A:
			return Direction8TC30.AAS;
		default:
			return null;
		}
	}
	public Direction8TC30 right30() {
		switch(this) {
		case W:
			return Direction8TC30.WWD;
		case D:
			return Direction8TC30.DDS;
		case S:
			return Direction8TC30.SSA;
		case A:
			return Direction8TC30.AAW;
		default:
			return null;
		}
	}
	public Direction4 left() {
		switch(this) {
		case W:
			return A;
		case D:
			return W;
		case S:
			return D;
		case A:
			return S;
		default:
			return null;
		}
	}
	public Direction4 right() {
		switch(this) {
		case W:
			return D;
		case D:
			return S;
		case S:
			return A;
		case A:
			return W;
		default:
			return null;
		}
	}
	public Direction4 back() {
		switch(this) {
		case W:
			return S;
		case D:
			return A;
		case S:
			return W;
		case A:
			return D;
		default:
			return null;
		}
	}
	///////////////
	//getOutermost
	///////////////
	public static Point getOutermost(Direction4 outerDirection, Point... points) {
		Point assumePoint = null;
		for(Point ver : points) {
			if(ver != null && (assumePoint == null || assumePoint.checkDirection(ver, outerDirection)))
				assumePoint = ver;
		}
		return assumePoint;
	}
	///////////////
	//getVertHorzClosest
	///////////////
	public static Point[] getVertHorzClosest_int(Point basePoint, Iterable<? extends Point> points, Direction4...directions) {
		if(points == null)
			return null;
		final Point[] assumePoints = new Point[directions.length];
		for(Point point : points) {
			if(point == null)
				continue;
			if(point.isVert(basePoint)) { //vertical - W or S
				for(int i = 0;i < directions.length;i++) {
					if(directions[i].isVert() && basePoint.checkDirection(point, directions[i]) && (assumePoints[i] == null || point.checkDirection(assumePoints[i], directions[i])))
						assumePoints[i] = point;
				}
			}else if(point.isHorz(basePoint)) { //horizontal - A or D
				for(int i = 0;i < directions.length;i++) {
					if(directions[i].isHorz() && basePoint.checkDirection(point, directions[i]) && (assumePoints[i] == null || point.checkDirection(assumePoints[i], directions[i])))
						assumePoints[i] = point;
				}
			}
		}
		return assumePoints;
	}
	public static <T extends Point>T getVertHorzClosest_int(Point basePoint, Iterable<T> points, Direction4 direction) {
		if(points == null)
			return null;
		T assumePoint = null;
		for(T point : points) {
			if(point != null && basePoint.checkDirection_just(point, direction) && (assumePoint == null || point.checkDirection(assumePoint, direction)))
				assumePoint = point;
		}
		return assumePoint;
	}
	@Override
	public boolean isNone() {
		return false;
	}
	@Override
	public double angle() {
		switch(this) {
		case W:
			return -Math.PI/2;
		case D:
			return 0;
		case S:
			return Math.PI/2;
		case A:
			return Math.PI;
		default:
			return 0;
		}
	}
	@Override
	public double ex() {
		return x();
	}
	@Override
	public double ey() {
		return y();
	}
	public static Direction4 random() {
		return random(new Random());
	}
	public static Direction4 random(Random random) {
		return Direction4.values()[random.nextInt(4)];
	}
}