package floatGame.modules;

import core.GHQ;
import floatGame.unit.FloatUnit;
import paint.dot.DotPaint;
import physics.HitGroup;

public abstract class FloatModule {
	
	protected double productCost;
	protected FloatUnit owner;
	
	protected DotPaint icon;
	
	protected boolean enabled = true;
	protected boolean autoWork = true;
	
	protected double workCost;
	public int cooldown_max;
	protected int cooldown;
	
	protected int lastWorkedFrame = -100;
	
	public FloatModule(double productCost, double workCost, int cooldown) {
		this.productCost = productCost;
		this.workCost = workCost;
		cooldown_max = cooldown;
	}
	
	public FloatModule setOwner(FloatUnit unit) {
		this.owner = unit;
		return this;
	}
	
	public void idleIfEnabled() {
		if(enabled)
			idle();
	}
	protected void idle() {
		if(cooldown < cooldown_max)
			++cooldown;
		if(autoWork) {
			tryWork();
		}
	}
	public boolean canWork() {
		if(owner == null) {
			System.out.println(getClass().getName() + " was required fire but no owner was set");
			return false;
		}
		return cooldown == cooldown_max && owner.energy() > workCost();
	}
	public boolean tryWork() {
		if(canWork()) {
			work();
			lastWorkedFrame = GHQ.nowFrame();
			cooldown = 0;
			owner.addEnergy(-workCost);
			return true;
		}
		return false;
	}
	protected abstract void work();
	public double cooldownRate() {
		return cooldown_max == 0.0 ? 1.0 : (double)cooldown / (double)cooldown_max;
	}
	public double workCost() {
		return workCost;
	}
	
	public FloatUnit owner() {
		return owner;
	}
	public double productCost() {
		return productCost;
	}
	public DotPaint icon() {
		return icon;
	}
	public abstract FloatModule replicate();
	
	public void setEnable(boolean b) {
		enabled = b;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public HitGroup hitGroup() {
		return owner.hitGroup();
	}
	public int lastWorkedFrame() {
		return lastWorkedFrame;
	}
}
