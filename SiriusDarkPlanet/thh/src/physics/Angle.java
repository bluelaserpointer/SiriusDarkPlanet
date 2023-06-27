package physics;

import physics.direction.DirectionLR;

public class Angle {
	protected double angle;
	public static final Angle NULL_ANGLE = new Angle() {
		public double get() {
			return 0.0;
		}
	};
	
	//initialization
	public Angle() {
		this.angle = 0.0;
	}
	public Angle(double angle) {
		this.angle = angle;
	}
	//control
	public void set(double angle) {
		this.angle = angle;
	}
	public void set(Angle sample) {
		set(sample.angle);
	}
	public void set(HasAngle sample) {
		set(sample.angle());
	}
	public void set(double dx, double dy) {
		set(Math.atan2(dy, dx));
	}
	public void spin(double angle) {
		set(get() + angle);
	}
	public double spinTo_ConstSpd(double targetAngle, double angularSpeed) {
		final double D_ANGLE = diff(targetAngle);
		if(D_ANGLE < 0) {
			if(D_ANGLE < -angularSpeed) {
				spin(-angularSpeed);
				return Math.abs(D_ANGLE + angularSpeed);
			} else {
				set(targetAngle);
				return 0.0;
			}
		}else if(D_ANGLE > 0){
			if(D_ANGLE > angularSpeed) {
				spin(angularSpeed);
				return Math.abs(D_ANGLE - angularSpeed);
			} else {
				set(targetAngle);
				return 0.0;
			}
		}else
			return 0.0;
	}
	public double spinTo_Suddenly(double targetAngle, double turnFrame) {
		final double D_ANGLE = diff(targetAngle);
		final double TURN_ANGLE = D_ANGLE/turnFrame;
		spin(TURN_ANGLE);
		return Math.abs(D_ANGLE - TURN_ANGLE);
	}
	public static double spinTo_ConstSpd(double nowAngle, double targetAngle, double angularSpeed) {
		final double D_ANGLE = targetAngle - nowAngle;
		if(D_ANGLE < 0) {
			if(D_ANGLE < -angularSpeed) {
				return nowAngle - angularSpeed;
			} else {
				return targetAngle;
			}
		}else if(D_ANGLE > 0){
			if(D_ANGLE > angularSpeed) {
				return nowAngle + angularSpeed;
			} else {
				return targetAngle;
			}
		}else
			return nowAngle;
	}
	public static double spinTo_Suddenly(double nowAngle, double targetAngle, double turnFrame) {
		return nowAngle + (targetAngle - nowAngle)/turnFrame;
	}
	//tool
	public static double random() {
		return Math.random()*Math.PI*2;
	}
	public static double formatToAbsSmallest(double angle) {
		angle %= Math.PI*2;
		if(angle > Math.PI) {
			return Math.PI*2 - angle;
		} else if(angle < -Math.PI) {
			return Math.PI*2 + angle;
		} else {
			return angle;
		}
	}
	public static double formatToSmallestAbs(double angle) {
		angle %= Math.PI*2;
		if(angle < -Math.PI) {
			return Math.PI*2 + angle;
		} else {
			return angle;
		}
	}
	//information
	public double get() {
		return angle;
	}
	public double sin() {
		return Math.sin(get());
	}
	public double cos() {
		return Math.cos(get());
	}
	public double sin(double r) {
		return r*sin();
	}
	public double cos(double r) {
		return r*cos();
	}
	public double diff(double angle) {
		return angle - get();
	}
	public double dist(double angle) {
		return diff(angle) % (Math.PI*2);
	}
	public double absDist(double angle) {
		final double rawAngle = Math.abs(diff(angle));
		return rawAngle < Math.PI ? rawAngle : Math.PI*2 - rawAngle;
	}
	public boolean isDiffSmaller(double angle, double range) {
		return absDist(angle) < range;
	}
	public DirectionLR directionLR() {
		return cos() > 0 ? DirectionLR.RIGHT : DirectionLR.LEFT;
	}
	public static double absDist(double angle1, double angle2) {
		final double rawAngle = Math.abs((angle1 - angle2) % (Math.PI*2));
		return rawAngle < Math.PI ? rawAngle : Math.PI*2 - rawAngle;
	}
	public static boolean isDiffSmaller(double angle1, double angle2, double range) {
		return absDist(angle1, angle2) < range;
	}
}
