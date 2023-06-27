package floatGame.unit;

import java.awt.Color;
import java.util.LinkedList;

import calculate.Damage;
import core.GHQ;
import floatGame.engine.FloatAchievement;
import floatGame.engine.FloatGame;
import floatGame.modules.FloatModule;
import floatGame.ui.HUD;
import paint.dot.DotPaint;
import physics.HitGroup;
import physics.Point;
import physics.direction.DirectionLR;
import preset.unit.Unit;

public abstract class FloatUnit extends Unit {

	public static final HitGroup friendSide = new HitGroup(1), enemySide = new HitGroup(2);
	
	public int hpMax = 100;
	protected int hp;
	public double energyMax = 100.0;
	protected double energy;
	
	protected final LinkedList<FloatStatus> statusList = new LinkedList<>();
	
	protected FloatFactory baseFactory;

	protected DirectionLR lastDirectionLR = DirectionLR.RIGHT;
	
	private FloatUnit target;
	protected int lastContactFrame = -1000;
	
	protected FloatDamage lastDamage;
	
	public DotPaint dotPaint;
	
	protected LinkedList<FloatModule> modules = new LinkedList<>();
	protected int module_max = 6;
	
	public FloatUnit(DotPaint dotPaint, int hp, double energy, HitGroup side) {
		this.dotPaint = dotPaint;
		hpMax = hp;
		energyMax = energy;
		physics().setHitRule(side);
	}
	
	@Override
	public void idle() {
		super.idle();
		//module idle
		for(FloatModule module : modules) {
			module.idleIfEnabled();
		}
	}
	@Override
	public final void paint() {
		if(doDirectionLRFlip() && lastDirectionLR.isLeft()) {
			GHQ.setFlip(true, false);
			paintExtention();
			GHQ.setFlip(true, false);
		} else {
			paintExtention();
		}
	}
	
	protected void paintExtention() {
		super.paint();
		dotPaint.dotPaint(point());
		paintStatusBar();
	}

	@Override
	public DotPaint getDotPaint() {
		return dotPaint;
	}

	@Override
	public Unit respawn(int spawnX, int spawnY) {
		hp = hpMax;
		energy = energyMax;
		point().setXY(spawnX, spawnY);
		lastDirectionLR = enemyDirection();
		disbandTarget();
		lastDamage = null;
		return this;
	}

	@Override
	public boolean isAlive() {
		return hp > 0;
	}
	
	public int hp() {
		return hp;
	}
	public double energy() {
		return energy;
	}
	public int hpMax() {
		return hpMax;
	}
	public double energyMax() {
		return energyMax;
	}
	public int addHP(int value) {
		final int newVal = hp + value;
		int effect;
		if(newVal < 0) {
			effect = -hp;
			hp = 0;
		} else if(newVal > hpMax) {
			effect = hpMax - hp;
			hp = hpMax;
		} else {
			effect = value;
			hp = newVal;
		}
		return effect;
	}
	public double addEnergy(double value) {
		final double newVal = energy + value;
		double effect;
		if(newVal < 0) {
			effect = -energy;
			energy = 0;
		} else if(newVal > energyMax) {
			effect = energyMax - energy;
			energy = energyMax;
		} else {
			effect = value;
			energy = newVal;
		}
		return effect;
	}
	public double hpRate() {
		return (double)hp/(double)hpMax;
	}
	public double energyRate() {
		return energy / energyMax;
	}

	protected void paintStatusBar() {
		final int barH = 2;
		GHQ.getG2D(Color.BLUE).fillRect(left(), top() - barH*2, (int)(energyRate()*width()), barH);
		GHQ.getG2D(Color.RED).fillRect(left(), top() - barH*1, (int)(hpRate()*width()), barH);
	}

	public DirectionLR lastDirectionLR() {
		return lastDirectionLR;
	}
	public DirectionLR enemyDirection() {
		return hitGroup().equals(friendSide) ? DirectionLR.RIGHT : DirectionLR.LEFT;
	}
	public Color teamColor() {
		return teamColor(hitGroup());
	}
	public static Color teamColor(HitGroup hitGroup) {
		return hitGroup.equals(FloatUnit.friendSide) ? Color.GREEN : new Color(1F, 1F, 0.1F);
	}

	public static <T extends FloatUnit>T quickSpawn(T unit, Point point) {
		return GHQ.stage().addUnit(Unit.initialSpawn(unit, point.intX(), point.intY() + 60));
	}
	public static <T extends FloatUnit>T quickSpawn(T unit, Point point, int dx, int dy) {
		return GHQ.stage().addUnit(Unit.initialSpawn(unit, point.intX() + dx, point.intY() + 60 + dy));
	}
	
	public FloatModule addModule(FloatModule module) {
		if(modules.size() < module_max && acceptModule(module)) {
			modules.add(module);
			module.setOwner(this);
		}
		return module;
	}
	
	public void removeModule(Class<? extends FloatModule> moduleClass) {
		for(FloatModule module : modules) {
			if(moduleClass.isInstance(module)) {
				module.setOwner(null);
				modules.remove(module);
				break;
			}
		}
	}
	
	public boolean acceptModule(FloatModule module) {
		return true;
	}
	
	public LinkedList<FloatModule> modules() {
		return modules;
	}
	
	public int module_max() {
		return module_max;
	}
	
	public boolean doDirectionLRFlip() {
		return true;
	}
	
	public FloatUnit target() {
		if(target != null && (!target.isAlive() || target.hasDeleteClaimFromStage())) {
			disbandTarget();
			return null;
		}
		return target;
	}
	public boolean hasTarget() {
		return target() != null;
	}
	public Point targetPoint() {
		return target().point();
	}
	public double targetDistance() {
		return hasTarget() ? point().distance(target()) : Double.POSITIVE_INFINITY;
	}
	public double targetAngle() {
		return point().angleTo(target());
	}
	public void setTarget(FloatUnit target) {
		this.target = target;
	}
	public void disbandTarget() {
		setTarget(null);
	}
	public int lastContactFrame() {
		return lastContactFrame;
	}
	public int framesFromLastContact() {
		return GHQ.passedFrame(lastContactFrame);
	}
	
	public abstract void shoot();
	
	@Override
	public void damage(Damage damage) {
		if(!isAlive())
			return;
		final FloatDamage fdamage = (FloatDamage)damage;
		fdamage.doDamage(this);
//		if(fdamage.bullet == null)
//			System.out.println("ground dmg");
//		else
//			System.out.println("shooot dmg by " + fdamage.bullet.shooter().getClass().getName());
		if(!isAlive()) {
			if(fdamage.bullet == null) { //ground damage
				if(lastDamage != null && lastDamage.bullet.shooter().equals(FloatGame.player)) {
					if(point().speed() > 10.0) {
						HUD.postAchievement(FloatAchievement.SHOT_DOWN, "velocity: " + GHQ.DF0_0.format(point().speed()) + "px/s");
					} else {
						HUD.postAchievement(FloatAchievement.KILL);
					}
				}
			} else if(fdamage.bullet.shooter().equals(FloatGame.player)) {
				HUD.postAchievement(FloatAchievement.KILL);
			}
		}
		if(fdamage.bullet != null) //avoid recording crush ground damage
			lastDamage = fdamage;
	}

	public void setBaseFactory(FloatFactory owner) {
		baseFactory = owner;
	}
	
	public LinkedList<FloatStatus> statusList() {
		return statusList;
	}

	public boolean addStatus(FloatStatus status) {
		if(countStatus(status) < status.limit) {
			statusList.add(status);
			return true;
		}
		return false;
	}
	
	public boolean hasStatus(FloatStatus status) {
		return statusList.contains(status);
	}
	
	public int countStatus(FloatStatus status) {
		int count = 0;
		for(FloatStatus eachStatus : statusList) {
			if(eachStatus.equals(status))
				++count;
		}
		return count;
	}
	
	public void removeStatus(FloatStatus status) {
		statusList.remove(status);
	}
	
	public abstract double initialSpeed();
	public double speed() {
		double speed = initialSpeed();
		for(int i = 0; i < countStatus(FloatStatus.SPEED_BOOST); ++i)
			speed *= 1.5;
		return speed;
	}
	
	public boolean isFriend() {
		return super.hitableGroup(enemySide);
	}
	
	public abstract String description();
}
