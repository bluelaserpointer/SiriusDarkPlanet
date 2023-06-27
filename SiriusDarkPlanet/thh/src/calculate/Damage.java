package calculate;

import static java.lang.Math.PI;

import core.GHQ;
import core.GHQObject;
import paint.text.StringPaint;
import physics.HasPoint;
import preset.bullet.Bullet;
import preset.effect.Effect;

public abstract class Damage {
	protected GHQObject attacker = GHQObject.NULL_GHQ_OBJECT;
	protected Bullet attackerBullet = Bullet.NULL_BULLET;
	public static final Damage NULL_DAMAGE = new Damage() {
		@Override
		public void doDamage(GHQObject target) {}
		@Override
		public Damage clone() {
			return this;
		}
	};
	//init
	public Damage(Damage sample) {
		attacker = sample.attacker;
		attackerBullet = sample.attackerBullet;
	}
	public Damage() {}
	public Damage setAttacker(GHQObject attacker) {
		this.attacker = attacker;
		return this;
	}
	public Damage setAttackingBullet(Bullet bullet) {
		this.attackerBullet = bullet;
		this.attacker = bullet.shooter();
		return this;
	}
	
	public abstract void doDamage(GHQObject target);
	public static Effect getDefaultDmgEffect(HasPoint damagedTarget, StringPaint stringPaint) {
		final Effect effect = new Effect();
		effect.setName("DefaultDamageNumberEffect");
		effect.paintScript = stringPaint;
		effect.limitFrame = 15;
		effect.point().setXY(damagedTarget);
		effect.point().addSpeed_DA(10, GHQ.random2(0, 2*PI));
		return effect;
	}
	
	//information
	public abstract Damage clone();
	public GHQObject attacker() {
		return attacker;
	}
	public Bullet attackerBullet() {
		return attackerBullet;
	}
	public boolean hasAttacker() {
		return attacker != null && attacker != GHQObject.NULL_GHQ_OBJECT;
	}
	public boolean hasAttackingBullet() {
		return attackerBullet != null && attackerBullet != Bullet.NULL_BULLET;
	}
}
