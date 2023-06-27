package physics;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.function.Supplier;

import core.GHQ;
import physics.direction.Direction4;

/**
 * A major class for managing object coordinate.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class Point implements Serializable {
	private static final long serialVersionUID = -4012705097956450689L;
	public static final Point NULL_POINT = new Point.IntPoint();
	
	@Override
	public abstract Point clone();
	
	///////////////
	//XY
	///////////////
	
	public abstract int intX();
	public abstract int intY();
	public abstract double doubleX();
	public abstract double doubleY();
	
	///////////////
	//update
	///////////////
	
	///////////////setAll
	public abstract Point setAll(Point point);
	public Point setAll(HasPoint hasPoint) {
		if(hasPoint != null)
			return setAll(hasPoint.point());
		return this;
	}
	
	///////////////setX&Y&XY
	public abstract void setX(int x);
	public abstract void setY(int y);
	public abstract void setX(double x);
	public abstract void setY(double y);
	public abstract void setX(Point p);
	public abstract void setY(Point p);
	public Point setXY(int x, int y) {
		setX(x);
		setY(y);
		return this;
	}
	public Point setXY(double x, double y) {
		setX(x);
		setY(y);
		return this;
	}
	public Point setXY(Point p) {
		setX(p);
		setY(p);
		return this;
	}
	public Point setXY(HasPoint hasPoint){
		if(hasPoint != null)
			setXY(hasPoint.point());
		return this;
	}
	public Point setMouseScreenXY() {
		return setXY(GHQ.mouseScreenX(), GHQ.mouseScreenY());
	}
	public Point setMouseStageXY() {
		return setXY(GHQ.mouseX(), GHQ.mouseY());
	}
	///////////////addX&Y&XY
	public Point addX(int x) {
		setX(intX() + x);
		return this;
	}
	public Point addY(int y) {
		setY(intY() + y);
		return this;
	}
	public Point addX(double x) {
		setX(doubleX() + x);
		return this;
	}
	public Point addY(double y) {
		setY(doubleY() + y);
		return this;
	}
	public Point addXY(int x, int y) {
		setX(intX() + x);
		setY(intY() + y);
		return this;
	}
	public Point addXY(double x, double y) {
		//System.out.println();
		setX(doubleX() + x);
		setY(doubleY() + y);
		return this;
	}
	///////////////shift
	public Point shift(Direction4 direction, int distance) {
		switch(direction) {
		case W:
			addY(-distance);
			break;
		case S:
			addY(distance);
			break;
		case A:
			addX(-distance);
			break;
		case D:
			addX(distance);
			break;
		}
		return this;
	}
	public Point rectCircuit(int centerX, int centerY, int speed, boolean clockwise) {
		final int DX = -intDX(centerX), DY = -intDY(centerY);
		final int AbsDX = Math.abs(DX), AbsDY = Math.abs(DY);
		if(AbsDX > AbsDY) {
			if(DX > 0 ^ clockwise)
				addY(-speed);
			else
				addY(speed);
		}else if(AbsDX < AbsDY) {
			if(DY > 0 ^ clockwise)
				addX(speed);
			else
				addX(-speed);
		}else if((DX > 0 == DY > 0) ^ clockwise) {
			if(DX < 0)
				addX(speed);
			else
				addX(-speed);
		}else {
			if(DY < 0)
				addY(speed);
			else
				addY(-speed);
			
		}
		return this;
	}
	public Point rectCircuit(Point centerPoint, int speed, boolean clockwise) {
		return rectCircuit(centerPoint.intX(), centerPoint.intY(), speed, clockwise);
	}
	public Point diaCircuit(int centerX, int centerY, int speed, boolean clockwise) {
		return this;
	}
	public Point diaCircuit(Point centerPoint, int speed, boolean clockwise) {
		return rectCircuit(centerPoint.intX(), centerPoint.intY(), speed, clockwise);
	}
	///////////////clone
	public Point cloneAt(int dx, int dy) {
		return clone().addXY(dx, dy);
	}
	public Point cloneAt(double dx, double dy) {
		return clone().addXY(dx, dy);
	}
	//////////////
	//split
	//////////////
	public static void split_xMirror(Supplier<? extends HasPoint> supplier, double dx, double dy) {
		supplier.get().point().addXY_allowsMoveAngle(-dx/2, dy);
		supplier.get().point().addXY_allowsMoveAngle(+dx/2, dy);
	}
	public static void split_yMirror(Supplier<? extends HasPoint> supplier, double dx, double dy) {
		supplier.get().point().addXY_allowsMoveAngle(dx, -dy/2);
		supplier.get().point().addXY_allowsMoveAngle(dx, +dy/2);
	}
	public static void split_Round(Supplier<? extends HasPoint> supplier, int radius, int amount) {
	final double D_ANGLE = 2*PI/amount;
		for(int i = 0;i < amount;i++)
			supplier.get().point().addXY_DA(radius, D_ANGLE*i);
	}
	public static void split_Burst(Supplier<? extends HasPoint> supplier, int radius, int amount, double speed) {
	final double D_ANGLE = 2*PI/amount;
		for(int i = 0;i < amount;i++)
			supplier.get().point().fastParaAdd_DASpd(radius, D_ANGLE*i, speed);
	}
	public static void split_NWay(Supplier<? extends HasPoint> supplier, int radius, double[] angles, double speed) {
		for(int i = 0;i < angles.length;i++)
			supplier.get().point().fastParaAdd_DASpd(radius, angles[i], speed);
	}
	public static void split_NWay(Supplier<? extends HasPoint> supplier, int radius, double marginAngle, double amount, double speed) {
		for(int i = 0;i < amount;i++)
			supplier.get().point().fastParaAdd_DASpd(radius, marginAngle*i, speed);
	}
	///////////////
	//information
	///////////////
	
	///////////////toString
	@Override
	public String toString() {
		return toString_int();
	}
	public String toString_int() {
		return intX() + ", " + intY();
	}
	public String toString_double() {
		return doubleX() + ", " + doubleY();
	}
	public String toString_double(DecimalFormat deciamlFormat) {
		return deciamlFormat.format(doubleX()) + ", " + deciamlFormat.format(doubleY());
	}
	///////////////angleTo
	public double angleTo(int x,int y) {
		return atan2(intDY(y), intDX(x));
	}
	public double angleTo(double x,double y) {
		return atan2(doubleDY(y), doubleDX(x));
	}
	public double angleTo(Point point) {
		if(this == point) {
			System.out.println("<!>Point.angleTo(Point point) received itself.");
			return 0.0;
		}
		return atan2(doubleDY(point), doubleDX(point));
	}
	public double angleTo(HasPoint hasPoint) {
		return hasPoint == null ? GHQ.NONE : angleTo(hasPoint.point());
	}
	///////////////angleToMouse
	public double angleToMouse() {
		return angleTo(GHQ.mouseX(), GHQ.mouseY());
	}
	///////////////intDX&DY
	public final int intDX(int x) {
		return x - intX();
	}
	public final int intDY(int y) {
		return y - intY();
	}
	public final int intDX(Point p) {
		return p.intX() - intX();
	}
	public final int intDY(Point p) {
		return p.intY() - intY();
	}
	///////////////intAbsDX&DY
	public final int intAbsDX(int x) {
		return Math.abs(intDX(x));
	}
	public final int intAbsDY(int y) {
		return Math.abs(intDY(y));
	}
	public final int intAbsDX(Point p) {
		return Math.abs(intDX(p));
	}
	public final int intAbsDY(Point p) {
		return Math.abs(intDY(p));
	}
	///////////////doubleDX&DY
	public final double doubleDX(double x) {
		return x - doubleX();
	}
	public final double doubleDY(double y) {
		return y - doubleY();
	}
	public final double doubleDX(Point p) {
		return p.doubleX() - doubleX();
	}
	public final double doubleDY(Point p) {
		return p.doubleY() - doubleY();
	}
	///////////////doubleAbsDX&DY
	public final double doubleAbsDX(double x) {
		return Math.abs(doubleDX(x));
	}
	public final double doubleAbsDY(double y) {
		return Math.abs(doubleDY(y));
	}
	public final double doubleAbsDX(Point p) {
		return Math.abs(doubleDX(p));
	}
	public final double doubleAbsDY(Point p) {
		return Math.abs(doubleDY(p));
	}
	///////////////Rectangle2D.Double
	public final Rectangle2D.Double rectangleAtCenter(double width, double height) {
		return new Rectangle2D.Double(doubleX() - width/2, doubleY() - height/2, width, height);
	}
	public final Rectangle2D.Double rectangleAtCorner(double width, double height) {
		return new Rectangle2D.Double(doubleX(), doubleY(), width, height);
	}
	///////////////inRangeX&Y&XY
	public final boolean inRangeX(int x, int xDistance) {
		return intAbsDX(x) < xDistance;
	}
	public final boolean inRangeY(int y, int yDistance) {
		return intAbsDY(y) < yDistance;
	}
	public final boolean inRangeX(Point point, int xDistance) {
		return intAbsDX(point) < xDistance;
	}
	public final boolean inRangeY(Point point, int yDistance) {
		return intAbsDY(point) < yDistance;
	}
	public final boolean inRangeX(Point point, double xDistance) {
		return doubleAbsDX(point) < xDistance;
	}
	public final boolean inRangeY(Point point, double yDistance) {
		return doubleAbsDY(point) < yDistance;
	}
	public final boolean inRangeXY(int x, int y, int xDistance, int yDistance) {
		return inRangeX(x, xDistance) && inRangeY(y, yDistance);
	}
	public final boolean inRangeXY(Point point, int xDistance, int yDistance) {
		return inRangeX(point, xDistance) && inRangeY(point, yDistance);
	}
	public final boolean inRangeXY(Point point, int distance) {
		return inRangeXY(point, distance, distance);
	}
	public final boolean inRangeXY(Point point, double xDistance, double yDistance) {
		return inRangeX(point, xDistance) && inRangeY(point, yDistance);
	}
	public final boolean inRangeXY(Point point, double distance) {
		return inRangeXY(point, distance, distance);
	}
	///////////////distance&distanceSq
	public final double distanceSq(double x, double y) {
		final double DX = doubleDX(x),DY = doubleDY(y);
		return DX*DX + DY*DY;
	}
	public final double distanceSq(Point point) {
		final double DX = doubleDX(point),DY = doubleDY(point);
		return DX*DX + DY*DY;
	}
	public final double distanceSq(HasPoint hasPoint) {
		return hasPoint == null ? GHQ.MAX : distanceSq(hasPoint.point());
	}
	public final double distance(double x, double y) {
		return Math.sqrt(distanceSq(x, y));
	}
	public final double distance(Point point) {
		return Math.sqrt(distanceSq(point));
	}
	public final double distance(HasPoint hasPoint) {
		return hasPoint == null ? GHQ.MAX : Math.sqrt(distanceSq(hasPoint.point()));
	}
	///////////////inRange&inRangeSq
	public final boolean inRangeSq(double x, double y, double distanceSq) {
		return distanceSq(x, y) < distanceSq;
	}
	public final boolean inRangeSq(Point point, double distanceSq) {
		return distanceSq(point) < distanceSq;
	}
	public final boolean inRangeSq(HasPoint hasPoint, double distanceSq) {
		return hasPoint == null ? false : inRangeSq(hasPoint.point(), distanceSq);
	}
	public final boolean inRange(double x, double y, double distance) {
		return inRangeSq(x, y, distance*distance);
	}
	public final boolean inRange(Point point, double distance) {
		return inRangeSq(point, distance*distance);
	}
	public final boolean inRange(HasPoint hasPoint, double distance) {
		return hasPoint == null ? false : inRangeSq(hasPoint.point(), distance*distance);
	}
	///////////////inStage
	public boolean inStage() {
		return GHQ.stage().inStage(this);
	}
	///////////////isBlocked
	public boolean isBlocked() {
		return !GHQ.stage().inStage(this) || GHQ.stage().structures.shapeIntersected_dot(this);
	}
	///////////////isVisible
	public boolean isVisible(int x, int y, double viewDistance) {
		final double dist = distance(x, y);
		return !(dist > viewDistance) && dist + GHQ.stage().visibility(intX(), intY(), x, y) < viewDistance;
	}
	public boolean isVisible(Point point, double viewDistance) {
		return isVisible(point.intX(), point.intY(), viewDistance);
	}
	public boolean isVisible(HasPoint hasPoint, double viewDistance) {
		return hasPoint == null ? false : isVisible(hasPoint.point(), viewDistance);
	}
	public boolean isVisible(int x, int y) {
		return GHQ.stage().visibility(intX(), intY(), x, y) == 0.0;
	}
	public boolean isVisible(Point point) {
		return isVisible(point.intX(), point.intY());
	}
	public boolean isVisible(HasPoint hasPoint) {
		return hasPoint == null ? false : isVisible(hasPoint.point());
	}
	public double visiblity(int x, int y) {
		return GHQ.stage().visibility(intX(), intY(), x, y);
	}
	///////////////equals
	public boolean equals(int x, int y) {
		return intX() == x && intY() == y;
	}
	public boolean equals(double x, double y) {
		return doubleX() == x && doubleY() == y;
	}
	public boolean equals_int(Point point) {
		return equals(point.intX(), point.intY());
	}
	public boolean equals_double(Point point) {
		return equals(point.doubleX(), point.doubleY());
	}
	///////////////checkVertical&checkHorizontal
	public boolean isVert(Point point) {
		return point == null ? false : intX() == point.intX();
	}
	public boolean isHorz(Point point) {
		return point == null ? false : intY() == point.intY();
	}
	///////////////checkDirection
	public boolean checkDirection(Point point, Direction4 direction) {
		if(point == null)
			return false;
		switch(direction) {
		case W:
			return point.intY() < intY();
		case S:
			return point.intY() > intY();
		case A:
			return point.intX() < intX();
		case D:
			return point.intX() > intX();
		default:
			return false;
		}
	}
	///////////////isFront
	public boolean checkDirection_just(Point point, Direction4 direction) {
		return (direction.isVert() ? isVert(point) : isHorz(point)) && checkDirection(point, direction);
	}
	///////////////approach&approachIfNoObstacles
	public void approach(double dstX, double dstY, double speed) {
		final double DX = doubleDX(dstX), DY = doubleDY(dstY);
		final double DISTANCE = sqrt(DX*DX + DY*DY);
		if(DISTANCE <= speed)
			setXY(dstX, dstY);
		else {
			final double RATE = speed/DISTANCE;
			addXY(DX*RATE, DY*RATE);
		}
	}
	public void approach(Point point, double speed) {
		approach(point.doubleX(), point.doubleY(), speed);
	}
	public void approach(HasPoint target, double speed) {
		if(target != null)
			approach(target.point(), speed);
	}
	public void approach_rate(double dstX, double dstY, double rate) {
		this.addXY((dstX - doubleX())*rate, (dstY - doubleY())*rate);
	}
	///////////////
	//dynam
	///////////////
	public void addX_allowsAngle(double dx, double angle) {
		addXY(dx*sin(angle), -dx*cos(angle));
	}
	public void addY_allowsAngle(double dy, double angle) {
		addXY(dy*cos(angle), dy*sin(angle));
	}
	public void addXY_allowsAngle(double dx, double dy, double angle) {
		final double cos = cos(angle), sin = sin(angle);
		addXY(dx*sin + dy*cos, -dx*cos + dy*sin);
	}
	public void addX_allowsMoveAngle(double dx) {
		addX_allowsAngle(dx, moveAngle());
	}
	public void addY_allowsMoveAngle(double dy) {
		addX_allowsAngle(dy, moveAngle());
	}
	public void addXY_allowsMoveAngle(double dx, double dy) {
		addXY_allowsAngle(dx, dy, moveAngle());
	}
	public void addXY_DA(double distance, double angle) {
		addXY(distance*cos(angle), distance*sin(angle));
	}
	public void fastParaAdd_ADXYSpd(double angle, double dx, double dy, double speed) {}
	public void fastParaAdd_DASpd(double distance, double angle, double speed) {}
	public void fastParaAdd_DSpd(double distance, double speed) {}
	public double moveAngle() {
		return GHQ.NONE;
	}
	/**
	 * Waring: Don't use this method only for turning paint angle.
	 * @param angle
	 */
	public void setMoveAngle(double angle) {}
	/**
	 * Waring: Don't use this method only for turning paint angle.
	 * @param angle
	 */
	public void setMoveAngle(Angle angle) {
		if(angle == null)
			return;
		setMoveAngle(angle.get());
	}
	public void setMoveAngleByBaseAngle(HasAngle sample) {
		if(sample == null)
			return;
		setMoveAngle(sample.angle());
	}
	public void setMoveAngleByMoveAngle(Point sample) {
		if(sample == null)
			return;
		setMoveAngle(sample.moveAngle());
	}
	public void setMoveAngleByMoveAngle(HasPoint sample) {
		if(sample == null)
			return;
		setMoveAngleByMoveAngle(sample.point());
	}
	public void setMoveAngleToTarget(HasPoint target) {}
	public void spinMoveAngleToTargetCapped(HasPoint target, double maxTurnAngle) {}
	public void spinMoveAngleToTargetSuddenly(HasPoint target, double turnFrame) {}
	public void spinMoveAngle(double angle) {}
	public boolean isMovable() {
		return false;
	}
	public boolean isStop() {
		return true;
	}
	public double xSpeed() {
		return 0.0;
	}
	public double ySpeed() {
		return 0.0;
	}
	public double speed() {
		return 0.0;
	}
	public void setXSpeed(double xSpeed) {}
	public void setYSpeed(double ySpeed) {}
	public void setSpeed(double xSpeed, double ySpeed) {}
	public void setSpeed(double speed) {}
	public void setSpeed_DA(double distance, double angle) {}
	public void addSpeed(double xSpeed, double ySpeed) {}
	public void addSpeed(double accel) {}
	public void addSpeed(double accel, boolean brakeMode) {}
	public void addSpeed_DA(double distance, double angle) {}
	public void mulSpeed(double rate) {}
	public void stop() {}
	/**
	 * Move forward.
	 */
	public void idle() {
		moveBySpeed();
	}
	public void moveBySpeed() {}
	public void moveIfNoObstacles(HitInteractable source) {}
	/**
	 * Move forward with a limited length.
	 * @param lengthCap
	 * @return lest length need to go
	 */
	public double move(double lengthCap) {
		return lengthCap;
	}

	///////////////
	//other
	///////////////
	public Rectangle2D getRectangle2D(int width, int height) {
		return new Rectangle2D.Double(intX() - width/2, intY() - height/2, intX() + width/2, intY() + height/2);
	}
	//////////////////////////////classes

	///////////////
	//IntVersionPoint
	///////////////
	public static class IntPoint extends Point{
		private static final long serialVersionUID = -7032532124881792105L;
		protected int x, y;
		public IntPoint() {}
		public IntPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public IntPoint(Point point) {
			this.x = point.intX();
			this.y = point.intY();
		}
		@Override
		public IntPoint setAll(Point point) {
			setXY(point);
			return this;
		}
		@Override
		public int intX() {
			return x;
		}
		@Override
		public int intY() {
			return y;
		}
		@Override
		public double doubleX() {
			return intX();
		}
		@Override
		public double doubleY() {
			return intY();
		}
		@Override
		public void setX(int x){
			this.x = x;
		}
		@Override
		public void setY(int y){
			this.y = y;
		}
		@Override
		public void setX(double x){
			setX((int)x);
		}
		@Override
		public void setY(double y){
			setY((int)y);
		}
		@Override
		public void setX(Point p){
			setX(p.doubleX());
		}
		@Override
		public void setY(Point p){
			setY(p.doubleY());
		}
		@Override
		public Point.IntPoint clone() {
			return new Point.IntPoint(this);
		}
	}
	
	///////////////
	//DoubleVersionPoint
	///////////////
	public static class DoublePoint extends Point{
		private static final long serialVersionUID = 2505276574895896202L;
		protected double x, y;
		public DoublePoint() {}
		public DoublePoint(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public DoublePoint(Point point) {
			this.x = point.doubleX();
			this.y = point.doubleY();
		}
		@Override
		public DoublePoint setAll(Point point) {
			setXY(point);
			return this;
		}
		@Override
		public int intX() {
			return (int)doubleX();
		}
		@Override
		public int intY() {
			return (int)doubleY();
		}
		@Override
		public double doubleX() {
			return x;
		}
		@Override
		public double doubleY() {
			return y;
		}
		@Override
		public void setX(int x){
			setX((double)x);
		}
		@Override
		public void setY(int y){
			setY((double)y);
		}
		@Override
		public void setX(double x){
			this.x = x;
		}
		@Override
		public void setY(double y){
			this.y = y;
		}
		@Override
		public void setX(Point p){
			setX(p.doubleX());
		}
		@Override
		public void setY(Point p){
			setY(p.doubleY());
		}
		@Override
		public Point.DoublePoint clone() {
			return new Point.DoublePoint(this);
		}
	}
}