package weapon;

import java.util.LinkedList;
import java.util.List;

import core.GHQ;
import core.GHQObject;
import physics.HitGroup;
import preset.bullet.Bullet;
import preset.unit.Unit;

/**
 * A class for performing object's attack delay.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class Weapon {
	public static final Weapon NULL_WEAPON = new Weapon();
	public String
		name;
	protected int
		coolTime, //
		coolSpeed, //
		reloadTime, //
		reloadSpeed, //
		magazine,
		magazineSize; //
	protected int
		coolProgress, //
		reloadProgress, //
		magazineConsumptionSpeed;
	protected boolean
		autoReload;
	public Weapon() {
		name = "<Not named>";
		coolProgress = coolTime = 0;
		coolSpeed = 1;
		reloadProgress = reloadTime = 0;
		reloadSpeed = 1;
		magazineSize = GHQ.MAX;
		magazineConsumptionSpeed = 1;
		autoReload = true;
	}
	/**
	 * End current cool and reload process and unload the magazine.
	 */
	public void reset() {
		coolProgress = coolTime;
		reloadProgress = reloadTime;
		if(magazineSize != GHQ.MAX)
			magazine = 0;
	}
	///////////////////
	//idle
	///////////////////
	/**
	 * Process the cool or reloading process.
	 * It does nothing if both of the cool and reloading process is finished.
	 */
	public void coolDownOrReload() {
		if(isReloadFinished()) {
			if(!isCoolFinished()) { //cooling process is on going
				if((coolProgress += coolSpeed) >= coolTime)
					coolEnd();
			}
			//both of the cool and reloading is finished -> check magazine amount and reload.
			if(autoReload && magazine == 0) {
				startReload();
			}
		}else { //reloading process is on going
			if((reloadProgress += reloadSpeed) >= reloadTime)
				reloadEnd();
		}
	}
	///////////////////
	//parameter init
	///////////////////
	public void setCoolTime(int coolTime) {
		coolProgress = this.coolTime = coolTime;
	}
	public void setReloadTime(int reloadTime) {
		reloadProgress = this.reloadTime = reloadTime;
	}
	public void setCoolSpeed(int coolSpeed) {
		this.coolSpeed = coolSpeed;
	}
	public void setReloadSpeed(int reloadSpeed) {
		this.reloadSpeed = reloadSpeed;
	}
	public void setCoolProgress(int coolProgress) {
		this.coolProgress = coolProgress;
	}
	public void setReloadProgress(int reloadProgress) {
		this.reloadProgress = reloadProgress;
	}
	public void setMagazine(int magazine) {
		this.magazine = magazine;
	}
	public void setMagazineSize(int magazineSize) {
		this.magazineSize = magazineSize;
	}
	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
	}
	///////////////////
	//parameter info
	///////////////////
	public int coolTime() {
		return coolTime;
	}
	public int reloadTime() {
		return reloadTime;
	}
	public int coolSpeed() {
		return coolSpeed;
	}
	public int reloadSpeed() {
		return reloadSpeed;
	}
	public int coolProgress() {
		return coolProgress;
	}
	public int reloadProgress() {
		return reloadProgress;
	}
	public int magazine() {
		return magazine;
	}
	public int magazineSize() {
		return magazineSize;
	}
	public boolean autoReload() {
		return autoReload;
	}
	///////////////////
	//fire
	///////////////////
	public boolean magazineReady() {
		return magazine >= magazineConsumptionSpeed;
	}
	/**
	 * Judge if the weapon is ready for next fire.
	 * @return true - ready / false - not ready
	 */
	public boolean canFire() {
		return isCoolFinished() && isReloadFinished() && magazineReady();
	}
	/**
	 * Fire if canFire() returns true.<br>
	 * The bullets' initial Dynam is based on shooter's current dynam.<br>
	 * @param shooter
	 * @param standpoint
	 * @return list of shoot bullets, or null if canFire() returns false
	 */
	public List<Bullet> trigger(GHQObject shooter, HitGroup standpoint) {
		if(canFire()) {
			startCool();
			if(magazine != GHQ.MAX)
				magazine -= magazineConsumptionSpeed;
			return setBullets(shooter, standpoint);
		}
		return null;
	}
	/**
	 * 
	 * @param shooter
	 * @return
	 */
	public final List<Bullet> trigger(Unit shooter) {
		return trigger(shooter, shooter.hitGroup());
	}
	public boolean triggerSuceed(GHQObject shooter, HitGroup standpoint) {
		return trigger(shooter, standpoint) != null;
	}
	public boolean triggerSucceed(GHQObject shooter) {
		return triggerSuceed(shooter, shooter.hitGroup());
	}
	/**
	 * Set bullets.
	 * @param shooter - the shooter of the bullets
	 */
	public List<Bullet> setBullets(GHQObject shooter, HitGroup standpoint) {
		return new LinkedList<Bullet>();
	}
	///////////////////
	//cooldown
	///////////////////
	//*CooldownProcess start automatically on every fire.
	/**
	 * Start / Restart cool process.
	 * CooldownProcess start automatically on every fire.
	 */
	public void startCool() {
		coolProgress = 0;
	}
	public void startCoolIfNotDoing() {
		if(isCoolFinished())
			startCool();
	}
	/**
	 * Boost cool down process.
	 * @param value
	 */
	public void coolBoost(int value) {
		if(!isCoolFinished()) {
			coolProgress += value;
			if(isCoolFinished())
				coolEnd();
		}
	}
	/**
	 * Finish current cool process.
	 */
	public void coolEnd() {
		coolProgress = coolTime;
	}
	/**
	 * Judge if the cool process is on going.<br>
	 * Note that it will returns true until the weapon is reloading.
	 * @return true - finished cooling / false - not finished
	 */
	public final boolean isCoolFinished() {
		return coolProgress >= coolTime;
	}
	public final int getCoolProgress() {
		return coolProgress;
	}
	///////////////////
	//reload
	///////////////////
	//*ReloadProcess does not start automatically when run out magazines.
	//Need to call this startReload() method from external class.
	/**
	 * Try to start / restart reload process.Can set a cap for amount of required ammo.<br>
	 * ReloadProcess does not start automatically when run out magazines.<br>
	 * Need to call this startReload() method from external class.<br>
	 * <br>
	 * It will end the cool process at the same time.
	 * @param ammoLeft - max amount of required ammo
	 * @return amount going to feed
	 */
	public double startReload() {
		final double AMMO_LEFT = getLeftAmmo();
		if(AMMO_LEFT <= 0)
			return 0;
		final int REQUIRED_AMMO = magazineSize - magazine;
		if(REQUIRED_AMMO == 0) //already full
			return 0;
		coolEnd();
		if(reloadTime == 0) //no reload time
			reloadEnd();
		else
			reloadProgress = 0;
		return REQUIRED_AMMO < AMMO_LEFT ? REQUIRED_AMMO : AMMO_LEFT;
	}
	public void startReloadForced() {
		reloadProgress = 0;
	}
	public double startReloadIfNotDoing() {
		if(!isReloadFinished())
			return 0;
		return startReload();
	}
	/**
	 * Boost reload process.
	 * @param value - boost value
	 */
	public void reloadBoost(int value) {
		if(!isReloadFinished()) {
			reloadProgress += reloadSpeed;
			if(isReloadFinished())
				reloadEnd();
		}
	}
	/**
	 * Finish current reload process and feed the magazine to full.
	 * @return feed amount
	 */
	public double reloadEnd() {
		reloadProgress = reloadTime;
		final double FEED_AMOUNT = Math.min(getMagazineEmptySpace(), getLeftAmmo());
		magazine += FEED_AMOUNT;
		consumeAmmo(FEED_AMOUNT);
		return FEED_AMOUNT;
	}
	public void consumeAmmo(double value) {
		
	}
	/**
	 * Cancel current reload process and does not feed the magazine.
	 * @return feed amount (still not feed in)
	 */
	public int reloadCancel() {
		reloadProgress = reloadTime;
		return magazineSize - magazine;
	}
	/**
	 * Unload and return the amount of unfired bullets in the magazine.
	 * @return amount of unfired bullets in the magazine.
	 */
	public int unload() {
		final int temp = magazine;
		magazine = 0;
		return temp;
	}
	/**
	 * Unload and return the amount of unfired bullets in the magazine.
	 * @param amount - amount of magazine to unload.
	 * @return amount of unfired bullets in the magazine.
	 */
	public int unload(int amount) {
		if(magazine > amount) {
			if(magazine != GHQ.MAX) {
				magazine -= amount;
				return amount;
			}
			return GHQ.MAX;
		}else {
			return unload();
		}
	}
	/**
	 * Judge if the reload process is on going.
	 * @return true - finished reloading / false - not finished
	 */
	public boolean isReloadFinished() {
		return reloadProgress >= reloadTime;
	}
	public final int getReloadProgress() {
		return reloadProgress;
	}
	public final void setMagazineConsumptionSpeed(int value) {
		magazineConsumptionSpeed = value;
	}
	///////////////////
	//information about magazine
	///////////////////
	/**
	 * Return how many ammo lefts.<br>
	 * Override this method to describe the amount referring the unit's inventory etc.<br>
	 * By default, it always returns {@link GHQ#MAX}<br>
	 * You can set it always returns a constant number(like 1), to perform separative reloads such as pump-action shotgun.
	 * @return amount of left ammo.
	 */
	public double getLeftAmmo() {
		return GHQ.MAX;
	}
	/**
	 * Get the amount of remained magazine in current cartridge.
	 * @return amount of remained magazine in current cartridge
	 */
	public final int getMagazineFilledSpace() {
		return magazine;
	}
	public final double getMagazineFilledRate() {
		return magazine/magazineSize;
	}
	/**
	 * Get the amount of fired magazine in current cartridge.
	 * @return amount of fired magazine in current cartridge
	 */
	public final int getMagazineEmptySpace() {
		return magazineSize != GHQ.MAX ? magazineSize - magazine : GHQ.MAX;
	}
	public final int getMagazineComsumptionSpeed() {
		return magazineConsumptionSpeed;
	}
	public final double getMagazineEmptySpacePercentage() {
		return (double)magazine/(double)magazineSize;
	}
}