package floatGame.engine;

import floatGame.modules.ShieldSupplier;
import floatGame.modules.SpeedSupplier;

public enum FloatUpgrade {
	//army category
	ADD_ARMY_SPEED_RATE20("Army speed +20%", () -> FloatResource.armySpeed *= 1.2),
	ADD_ARMY_ENERGY_RATE50("Army energy +50%", () -> FloatResource.armyEnergy *= 1.5),
	ADD_ARMY_HP_RATE50("Army health +50%", () -> FloatResource.armyHP *= 1.5),
	MODULE_SPEED_SUPPLIER("Player module: <200px friends speed +20% until take first damage.", () -> FloatResource.researchedModules.add(new SpeedSupplier())),
	MODULE_SHILED_SUPPLIER("Player module: <200px friends first damage -50%.", () -> FloatResource.researchedModules.add(new ShieldSupplier())),
	//aircraft carrier category
	ADD_BIRD_SPEED_RATE20("Bird speed +20%", () -> FloatResource.birdSpeed *= 1.2),
	ENHANCE_BIRD_MAKER("Bird product max amount +2", () -> FloatResource.birdSpeed *= 1.0), // TODO
	MODULE_BIRD_MAKER("Player module: holds 4 birds like BirdMother.", null),
	//bomber category
	ADD_BOMBER_SPEED_RATE20("Bomber speed +20%", () -> FloatResource.bomberSpeed *= 1.2),
	ADD_BOMBER_HP_RATE50("Bomber health +50%", () -> FloatResource.bomberSpeed *= 1.0), // TODO
	//basement category
	ENHANCE_STAR_MISSILE("Star Missile shoot twice missiles at once", null);
	private final Upgrade upgrade;
	public final String description;
	private FloatUpgrade(String description, Upgrade upgrade) {
		this.upgrade = upgrade;
		this.description = description;
	}
	public void apply() {
		upgrade.apply();
	}
	
	@FunctionalInterface
	private interface Upgrade {
		abstract void apply();
	}
}
