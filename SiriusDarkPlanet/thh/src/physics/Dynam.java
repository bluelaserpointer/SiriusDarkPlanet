package physics;

import static java.lang.Math.*;

import core.GHQ;

/**
 * A major class for managing object physics.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class Dynam extends Point.DoublePoint{
	private static final long serialVersionUID = -3892193817831737958L;
	
	public static final Dynam NULL_DYNAM = new Dynam();
	
	protected double xSpd, ySpd;
	public Dynam() {}
	public Dynam(Point dynam) {
		setAll(dynam);
	}
	public Dynam(double x, double y) {
		setXY(x, y);
	}
	public Dynam(double x, double y, double moveAngle) {
		setXY(x, y);
		setMoveAngle(moveAngle);
	}
	public Dynam(double x, double y, double xSpd, double ySpd) {
		setXY(x, y);
		setSpeed(xSpd, ySpd);
	}
	public Dynam(HasAnglePoint sample) {
		setXYAngle(sample);
	}
	public void clear() {
		setXY(0, 0);
		stop();
	}
	@Override
	public Dynam setAll(Point sample) {
		setXY(sample);
		setXSpeed(sample.xSpeed());
		setYSpeed(sample.ySpeed());
		return this;
	}
	public void setXYAngle(HasAnglePoint sample) {
		setXY(sample);
		setMoveAngleByBaseAngle(sample);
	}
	@Override
	public Dynam clone() {
		return new Dynam(this);
	}
	@Override
	public void fastParaAdd_ADXYSpd(double angle, double dx, double dy, double speed) {
		angle += moveAngle();
		final double cos = cos(angle), sin_angle = sin(angle);
		addXY(dx*sin_angle + dy*cos, -dx*cos + dy*sin_angle);
		setSpeed(speed*cos, speed*sin_angle);
	}
	@Override
	public void fastParaAdd_DASpd(double distance, double angle, double speed) {
		fastParaAdd_ADXYSpd(angle, 0.0, distance, speed);
	}
	@Override
	public void fastParaAdd_DSpd(double distance, double speed) {
		fastParaAdd_DASpd(distance, 0.0, speed);
	}
	@Override
	public double moveAngle() {
		return atan2(ySpeed(), xSpeed());
	}
	@Override
	public void setMoveAngle(double angle) {
		final double speed = speed();
		if(speed == 0.0) //setMoveAngle force stopped point move at speed 1.0.
			setSpeed(cos(angle), sin(angle));
		else
			setSpeed(speed*cos(angle), speed*sin(angle));
	}
	@Override
	public void setMoveAngleToTarget(HasPoint target) {
		if(target != null)
			setMoveAngle(angleTo(target));
	}
	@Override
	public void spinMoveAngleToTargetCapped(HasPoint target, double maxTurnAngle) {
		if(target != null)
			setMoveAngle(Angle.spinTo_ConstSpd(moveAngle(), angleTo(target), maxTurnAngle));
	}
	@Override
	public void spinMoveAngleToTargetSuddenly(HasPoint target, double turnFrame) {
		if(target != null)
			setMoveAngle(Angle.spinTo_Suddenly(moveAngle(), angleTo(target), turnFrame));
	}
	@Override
	public void spinMoveAngle(double angle) {
		setMoveAngle(moveAngle() + angle);
	}
	@Override
	public boolean isMovable() {
		return true;
	}
	@Override
	public boolean isStop() {
		return xSpeed() == 0.0 && ySpeed() == 0.0;
	}
	@Override
	public double xSpeed() {
		return xSpd;
	}
	@Override
	public double ySpeed() {
		return ySpd;
	}
	@Override
	public double speed() {
		if(xSpeed() == 0.0) {
			if(ySpeed() == 0.0)
				return 0.0;
			else
				return absYSpeed();
		}else {
			if(ySpeed() == 0.0)
				return absXSpeed();
			else {
				final double xSpeed = xSpeed(), ySpeed = ySpeed();
				return sqrt(xSpeed*xSpeed + ySpeed*ySpeed);
			}
		}
	}
	public double absXSpeed() {
		return Math.abs(xSpeed());
	}
	public double absYSpeed() {
		return Math.abs(ySpeed());
	}
	@Override
	public void setXSpeed(double xSpeed) {
		xSpd = xSpeed;
	}
	@Override
	public void setYSpeed(double ySpeed) {
		ySpd = ySpeed;
	}
	@Override
	public void setSpeed(double xSpeed, double ySpeed) {
		setXSpeed(xSpeed);
		setYSpeed(ySpeed);
	}
	@Override
	public void setSpeed(double speed) {
		final double oldSpeed = speed();
		if(oldSpeed == 0.0)
			setXSpeed(speed);
		else
			mulSpeed(speed/oldSpeed);
	}
	@Override
	public void setSpeed_DA(double distance, double angle) {
		setSpeed(distance*cos(angle), distance*sin(angle));
	}
	public void addXSpeed(double xSpeed) {
		setXSpeed(xSpeed() + xSpeed);
	}
	public void addYSpeed(double ySpeed) {
		setYSpeed(ySpeed() + ySpeed);
	}
	@Override
	public void addSpeed(double xSpeed, double ySpeed) {
		addXSpeed(xSpeed);
		addYSpeed(ySpeed);
	}
	@Override
	public void addSpeed(double speed) {
		setSpeed(speed() + speed);
	}
	@Override
	public void addSpeed(double accel, boolean brakeMode) {
		if(accel < 0 && brakeMode && speed() + accel <= 0)
			stop();
		else
			addSpeed(accel);
	}
	@Override
	public void addSpeed_DA(double distance, double angle) {
		setSpeed(xSpeed() + distance*cos(angle), ySpeed() + distance*sin(angle));
	}
	public void mulXSpeed(double rate) {
		if(rate < 1.0 && absXSpeed() < 0.1)
			setXSpeed(0.0);
		else
			setXSpeed(xSpeed()*rate);
	}
	public void mulYSpeed(double rate) {
		if(rate < 1.0 && absYSpeed() < 0.1)
			setYSpeed(0.0);
		else
			setYSpeed(ySpeed()*rate);
	}
	@Override
	public void mulSpeed(double rate) {
		mulXSpeed(rate);
		mulYSpeed(rate);
	}
	@Override
	public void stop() {
		setSpeed(0.0, 0.0);
	}
	
	//control
	/**
	 * Move forward.
	 */
	@Override
	public void moveBySpeed() {
		addXY(xSpeed(), ySpeed());
	}
	@Override
	public void moveIfNoObstacles(HitInteractable source) {
		if(!GHQ.stage().hitObstacle_atNewPoint(source, (int)xSpeed(), (int)ySpeed()))
			moveBySpeed();
	}
	/**
	 * Move forward with a limited length.
	 * @param lengthCap
	 * @return lest length need to go
	 */
	@Override
	public double move(double lengthCap) {
		if(isStop())
			return 0;
		final double SPEED = speed();
		if(SPEED <= lengthCap) {
			moveBySpeed();
			return 0;
		}else {
			final double RATE = lengthCap/SPEED;
			addXY(xSpeed()*RATE, ySpeed()*RATE);
			return SPEED - lengthCap;
		}
	}
}