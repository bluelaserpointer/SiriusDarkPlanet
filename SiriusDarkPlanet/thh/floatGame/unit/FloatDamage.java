package floatGame.unit;

import calculate.IntDamage;
import core.GHQObject;
import preset.bullet.Bullet;

public class FloatDamage extends IntDamage {

	public final Bullet bullet;
	
	public FloatDamage(Bullet bullet, int damageValue) {
		super(damageValue);
		this.bullet = bullet;
	}
	@Override
	public void doDamage(GHQObject target) {
		if(target instanceof FloatUnit) {
			final FloatUnit funit = (FloatUnit)target;
			int resultDamage = damageValue;
			if(funit.hasStatus(FloatStatus.HALF_SHIELD)) {
				resultDamage /= 2;
				funit.removeStatus(FloatStatus.HALF_SHIELD);
			}
			funit.removeStatus(FloatStatus.SPEED_BOOST);
			funit.addHP(-resultDamage);
		}
	}
	@Override
	public IntDamage clone() {
		return new FloatDamage(bullet, damageValue);
	}

}
