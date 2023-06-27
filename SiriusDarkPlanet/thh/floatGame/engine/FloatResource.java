package floatGame.engine;

import java.util.LinkedList;

import floatGame.modules.FloatModule;
import floatGame.modules.FloatWeapon;
import floatGame.modules.SpitFire;
import floatGame.modules.StarDust;
import floatGame.modules.StarMissile;

public class FloatResource {
	// upgrades for not player units may irreversible
	
	public static final LinkedList<FloatModule> researchedModules = new LinkedList<>(); // only for player
	public static final LinkedList<FloatWeapon> researchedWeapons = new LinkedList<>(); // only for player
	public static final LinkedList<FloatWeapon> researchedArmys = new LinkedList<>();
	
	public static final LinkedList<FloatUpgrade> takenUpgrades = new LinkedList<>();
	
	public static double
		playerSpeed,
		playerSpeed_initial = 5.0, //accelerate
		birdSpeed,
		birdSpeed_initial = 15.0,
		birdMotherSpeed,
		birdMotherSpeed_initial = 5.0,
		balloonSpeed,				//may not used
		balloonSpeed_initial = 5.0,
		bomberSpeed,
		bomberSpeed_initial = 6.0,
		armySpeed,
		armySpeed_initial = 5.0; // one step each 5 frames
	public static int
		armyHP,
		armyHP_initial = 25,
		bomberHP,
		bomberHP_initial = 200;
	
	public static double
		armyEnergy,
		armyEnergy_initial = 50;
	
	public static int
		birdMotherDockMax,
		birdMotherDockMax_initial = 8,
		balloonDockMax,
		balloonDockMax_initial = 8;
	
	public FloatResource() {
		init();
	}
	public void init() {
		//initial technology
		researchedWeapons.add(new StarDust());
		researchedWeapons.add(new SpitFire());
		researchedWeapons.add(new StarMissile());
		//initial unit parameters
		playerSpeed = playerSpeed_initial;
		birdSpeed = birdSpeed_initial;
		birdMotherSpeed = birdMotherSpeed_initial;
		balloonSpeed = balloonSpeed_initial;
		bomberSpeed = bomberSpeed_initial;
		armySpeed = armySpeed_initial;
		armyHP = armyHP_initial;
		bomberHP = bomberHP_initial;
		armyEnergy = armyEnergy_initial;
		birdMotherDockMax = birdMotherDockMax_initial;
		balloonDockMax = balloonDockMax_initial;
	}
	
	public boolean hasTakenUpgrade(FloatUpgrade upgrade) {
		return takenUpgrades.contains(upgrade);
	}
}
