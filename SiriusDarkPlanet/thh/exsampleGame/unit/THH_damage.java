package exsampleGame.unit;

import calculate.IntDamage;
import core.GHQObject;

public class THH_damage extends IntDamage {
	public THH_damage(int damageValue) {
		super(damageValue);
	}
	@Override
	public void doDamage(GHQObject target) {
		if(target instanceof THH_BasicUnit) {
			((THH_BasicUnit)target).HP.consume(damageValue);
		}
	}
	@Override
	public IntDamage clone() {
		return new THH_damage(damageValue);
	}
}
