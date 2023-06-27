package calculate;

import core.GHQObject;

public abstract class IntDamage extends Damage {
	protected int damageValue;
	public IntDamage(int damageValue) {
		this.damageValue = damageValue;
	}
	public IntDamage(IntDamage intDamage) {
		super(intDamage);
		this.damageValue = intDamage.damageValue;
	}
	@Override
	public abstract void doDamage(GHQObject target);
	public void setDamage(int value) {
		damageValue = value;
	}
	public int damageValue() {
		return damageValue;
	}
	public IntDamage unify(IntDamage anotherDamage) {
		damageValue += anotherDamage.damageValue;
		return this;
	}
	@Override
	public abstract IntDamage clone();
}
