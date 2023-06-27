package floatGame.unit;

import core.GHQ;
import paint.dot.DotPaint;
import physics.HitGroup;

public abstract class FloatArmy extends FloatUnit {

	public FloatArmy(DotPaint dotPaint, int hp, double energy, HitGroup side) {
		super(dotPaint, hp, energy, side);
	}
	
	protected double lastEnergy = 0;

	@Override
	public void idle() {
		super.idle();
		if(GHQ.nowFrame() % 4 == 0) {
			lastDirectionLR = enemyDirection();
			if(lastEnergy < super.energy()) { // charging
				//stay for charging finish
			} else { // if energy lower then 5%, back to nearest charging spot
				point().addX(speed() * (super.energyRate() > 0.05 ? enemyDirection().ex() : -enemyDirection().ex()));
			}
			lastEnergy = super.energy();
		}
	}
}
