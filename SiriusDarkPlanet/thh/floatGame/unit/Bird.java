package floatGame.unit;

import java.awt.Color;
import java.util.LinkedList;
import java.util.function.Consumer;

import core.GHQ;
import floatGame.engine.FloatGame;
import floatGame.engine.FloatResource;
import floatGame.modules.StarDust;
import paint.ImageFrame;
import physics.Angle;
import physics.Point;
import physics.hitShape.Square;
import preset.unit.Unit;

public class Bird extends FloatSpaceship {

	protected FloatUnit basement;
	protected Command nowCommand;
	protected final Consumer<Bird> dockProcess;
	
	protected boolean isChaseState;
	protected double recordedAngle;
	protected int fixAngleFrame;
	
	protected static final double INITIAL_YAW_COST = 5;
	protected static final double INITIAL_ACCEL = 1.0;
	
	protected static final int ENGINE_FIRE_LENGTH = 10;
	protected final LinkedList<Point.IntPoint> engineFirePoints = new LinkedList<>();
	
	public Bird(FloatUnit basement, Consumer<Bird> dockProcess) {
		super(ImageFrame.BLANK_SCRIPT, 50, 50, basement.hitGroup());
		this.basement = basement;
		this.dockProcess = dockProcess;
		super.physics().setHitShape(new Square(this, 15));
		super.addModule(new StarDust().setAngleIndicator(() -> point().moveAngle()).setOwner(this));
		super.friendLightDotPaint = ImageFrame.create("floatGame/image/bird_greenLight.png");
		super.enemyLightDotPaint = ImageFrame.create("floatGame/image/bird_redLight.png");
	}
	
	@Override
	public Bird respawn(int spawnX, int spawnY) {
		super.respawn(spawnX, spawnY);
		nowCommand = Command.ATTACK;
		return this;
	}
	
	private static final int avoidCollisionDistance = 200;
	
	@Override
	public void idle() {
		final double moveAngle = point().moveAngle();
		//engine effect
		if(engineFirePoints.size() == ENGINE_FIRE_LENGTH) {
			engineFirePoints.removeFirst();
		}
		engineFirePoints.addLast(new Point.IntPoint(point()));
		int engineEffectAlpha = 255;
		for(Point point : engineFirePoints) {
			Color color = new Color(teamColor().getRed(), teamColor().getGreen(), teamColor().getBlue(), 255 - engineEffectAlpha);
			GHQ.getG2D(color).drawRect(point.intX(), point.intY(), 1, 1);
			engineEffectAlpha *= 0.6;
		}
		//yaw
		if(nowCommand == Command.RETURN || super.energyRate() < 0.1) {
			if(point().distance(basement) < 50) { //dock
				dockProcess.accept(this);
			} else {
				yaw(moveAngle, point().angleTo(basement));
			}
		} else if(point().intY() + avoidCollisionDistance > FloatGame.STAGE_H) { //avoid vertical collision
			yaw(moveAngle, enemyDirection().isRight() ? -Math.PI/6 : -Math.PI/6*5);
			disbandTarget();
		} else if(point().intY() - avoidCollisionDistance < 0) { //avoid vertical collision
			yaw(moveAngle, enemyDirection().isRight() ? Math.PI/6 : Math.PI/6*5);
			disbandTarget();
		} else if(point().intX() - avoidCollisionDistance < 0) { //avoid horizontal collision
			yaw(moveAngle, 0.0);
			disbandTarget();
		} else if(point().intX() + avoidCollisionDistance > FloatGame.STAGE_W) { //avoid horizontal collision
			yaw(moveAngle, Math.PI);
			disbandTarget();
		} else if(nowCommand == Command.ATTACK) {
			if(!hasTarget()) {
				// find target
				for(Unit unit : GHQ.stage().units) {
					if(!super.hitableGroup(unit)) {
						continue;
					}
					// target random one in distance < 600 & angleDifference < 90 degree
					if(point().distance(unit) < 700 && Angle.isDiffSmaller(moveAngle, point().angleTo(unit), Math.PI/2)) {
						setTarget((FloatUnit)unit);
						isChaseState = true;
						fixAngleFrame = 0;
						break;
					}
				}
				// return to basement
				yaw(moveAngle, point().angleTo(basement));
			} else {
				final double targetDistance = point().distance(target());
				final double targetAngle = point().angleTo(target());
				if(targetDistance > 1200) { // ignore target distance
					disbandTarget();
				} else if(isChaseState) { // don't change angle and attack
					if(GHQ.passedFrame(fixAngleFrame) > 60) {
						isChaseState = false;
						fixAngleFrame = GHQ.nowFrame();
						recordedAngle = targetAngle;
					} else { //chase
						shoot();
					}
				} else { // correct angle and don't attack
					if(GHQ.passedFrame(fixAngleFrame) > 20) { // restart chase mode
						isChaseState = true;
						fixAngleFrame = GHQ.nowFrame();
					} else {
						if(GHQ.passedFrame(fixAngleFrame) % 10 == 0) {
							recordedAngle = targetAngle;
						}
						yaw(moveAngle, recordedAngle);
					}
				}
			}
		}
		super.idle();
	}
	
	@Override
	public void shoot() {
		if(isChaseState && hasTarget()) {
			modules.get(0).tryWork();
		}
	}
	
	@Override
	public void paintExtention() {
		double angle = point().moveAngle(), x = cx(), y = cy();
		GHQ.getG2D().rotate(angle, x, y);
		super.paintExtention();
		GHQ.getG2D().rotate(-angle, x, y);
	}
	@Override
	public boolean doDirectionLRFlip() {
		return false;
	}
	public void setCommand(Command command) {
		nowCommand = command;
	}
	public double initialSpeed() {
		return hpRate() * (isFriend() ? FloatResource.birdSpeed : FloatResource.birdSpeed_initial);
	}
	public double yawCost() {
		return (1.0 - hpRate())*100 + INITIAL_YAW_COST;
	}
	public double accelTo(double speed, double target) {
		final double accelPower = hpRate()*INITIAL_ACCEL;
		if(speed > target) {
			return (speed - accelPower > target) ? speed - accelPower : target;
		} else {
			return (speed + accelPower < target) ? speed + accelPower : target;
		}
	}
	private void yaw(double currentAngle, double targetAngle) {
		point().setSpeed_DA(accelTo(point().speed(), speed()), currentAngle + Angle.formatToAbsSmallest(targetAngle - currentAngle)/yawCost());
	}
	@Override
	public String description() {
		return "<<Bird>> Tiny and fast.";
	}
}
