package preset.bullet;

import calculate.Damage;
import core.GHQ;
import core.GHQObject;
import paint.dot.DotPaint;
import paint.dot.HasDotPaint;
import physics.DstCntDynam;
import physics.HasPoint;
import physics.hitShape.Square;
import weapon.Weapon;

/**
 * A primal class for managing bullet.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class Bullet extends GHQObject implements HasPoint, HasDotPaint {
	public static final Bullet NULL_BULLET = new Bullet(GHQObject.NULL_GHQ_OBJECT);
	protected Weapon originWeapon;
	protected GHQObject shooter; //an information source of user
	
	protected Damage
		damage;
	public int
		limitFrame;
	public double
		limitRange;
	public int
		penetration,
		reflection;
	public double
		accel;
	public DotPaint
		paintScript;
	public boolean
		hitShooter;
	public Bullet(GHQObject shooter) {
		physics().setPoint(new DstCntDynam().setXY(shooter));
		super.point().setMoveAngleByBaseAngle(shooter);
		physics().setHitShape(new Square(this, 10));
		physics().setHitRule(shooter.hitGroup());
		this.shooter = shooter;
		name = getClass().getName();
		damage = Damage.NULL_DAMAGE;
		limitFrame = GHQ.MAX;
		limitRange = GHQ.MAX;
		penetration = 0;
		reflection = 0;
		accel = 0.0;
		paintScript = DotPaint.BLANK_SCRIPT;
	}
	public Bullet(Bullet sample) {
		physics().setPoint(new DstCntDynam().setAll(sample));
		point().setMoveAngleByBaseAngle(shooter);
		physics().setHitShape(sample.hitShape().clone(this));
		physics().setHitRule(sample.hitGroup());
		originWeapon = sample.originWeapon;
		shooter = sample.shooter;
		name = sample.name;
		damage = sample.damage().clone();
		limitFrame = sample.limitFrame;
		limitRange = sample.limitRange;
		penetration = sample.penetration;
		reflection = sample.reflection;
		accel = sample.accel;
		paintScript = sample.paintScript;
		hitShooter = sample.hitShooter;
	}
	//init
	public Bullet setDamage(Damage damage) {
		this.damage = damage;
		damage.setAttackingBullet(this);
		return this;
	}
	public Bullet setDamage(Damage damage, GHQObject attacker) {
		this.damage = damage;
		damage.setAttackingBullet(this);
		damage.setAttacker(attacker);
		return this;
	}
	@Override
	public void idle() {
		super.idle();
		if(defaultDeleteCheck())
			return;
		if(!dynamIdle())
			return;
	}
	public final boolean defaultDeleteCheck() {
		if(checkIsOutofLifeSpan())
			return outOfLifeSpan();
		if(checkIsOutOfRange())
			return outOfRange();
		if(!point().inStage()) {
			return outOfStage();
		}
		return false;
	}
	public boolean checkIsOutofLifeSpan(){
		return checkIsOutofLifeSpan(limitFrame);
	}
	public boolean checkIsOutofLifeSpan(int lifeSpan) {
		return lifeSpan <= GHQ.passedFrame(super.initialFrame);
	}
	public boolean checkIsOutOfRange() {
		return checkIsOutOfRange(limitRange);
	}
	public boolean checkIsOutOfRange(double range) {
		return range != GHQ.MAX && range <= ((DstCntDynam)point()).getMovedDistance();
	}
	public boolean outOfLifeSpan() {
		claimDeleteFromStage();
		return true;
	}
	public boolean outOfRange() {
		claimDeleteFromStage();
		return true;
	}
	public boolean outOfStage() {
		claimDeleteFromStage();
		return true;
	}
	public final boolean dynamIdle() {
		return dynamIdle(GHQ.MAX);
	}
	public final boolean dynamIdle(int maxGap) {
		double length = GHQ.MAX;
		do {
			////////////
			//speed
			////////////
			length = point().move(Math.min(length, maxGap));
			
			////////////
			//entity collision
			////////////
			if(entityCollision() || !point().inStage())
				return false;
		} while(length > 0);
		
		////////////
		//acceleration
		////////////
		point().addSpeed(accel, true);
		return true;
	}
	@Override
	public Bullet clone() {
		return new Bullet(this);
	}
	/**
	 * Test collision with others and return if this bullet should be deleted.
	 * @return true - need delete, false - not need
	 */
	protected boolean entityCollision() {
		for(GHQObject object : GHQ.stage().bulletCollisionGroup) {
			if(intersects_checkNotDeleted(object)) {
				if(hitObjectDeleteCheck(object))
					return true;
			}
		}
		return false;
	}
	@Override
	public boolean intersects_checkNotDeleted(GHQObject object) {
		return (hitShooter || object != shooter) && super.intersects_checkNotDeleted(object);
	}
	@Override
	public void paint() {
		defaultPaint();
	}
	public final void defaultPaint() {
		paintScript.dotPaint_turn(point(), point().moveAngle());
	}
	//extends
	public boolean hitObjectDeleteCheck(GHQObject object) {
		object.damage(damage);
		hitObject(object);
		if(penetration > 0) {
			if(penetration != GHQ.MAX)
				penetration--;
		}else {
			return outOfPenetration();
		}
		return false;
	}
	public boolean outOfPenetration() {
		claimDeleteFromStage();
		return true;
	}
	public void hitObject(GHQObject object) {
	}
	//////////////
	//control
	//////////////
	public Weapon setOriginWeapon(Weapon weapon) {
		return originWeapon = weapon;
	}

	//////////////
	//information
	//////////////
	public Weapon weapon() {
		return originWeapon;
	}
	public GHQObject shooter() {
		return shooter;
	}
	public int getPassedFrame() {
		return GHQ.passedFrame(initialFrame);
	}
	public final int getPenetration() {
		return penetration;
	}
	public final int getReflection() {
		return reflection;
	}
	@Override
	public final DotPaint getDotPaint() {
		return paintScript;
	}
	@Override
	public String name() {
		return "[Bullet]" + name;
	}
	public Damage damage() {
		return damage;
	}
}
