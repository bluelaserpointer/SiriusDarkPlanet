package floatGame.modules;

import core.GHQ;
import floatGame.unit.Bird;
import floatGame.unit.Command;
import floatGame.unit.FloatUnit;
import paint.ImageFrame;
import preset.unit.Unit;

public class BirdMaker extends FloatFactoryModule {

	final int docking_max;
	protected final Bird[] dockings;
	int lastDepartFrame = -1000;
	final int DEPART_INTERVAL = 10;
	
	public BirdMaker(int docking_max) {
		super(150, 10, 200);
		icon = ImageFrame.create("floatGame/image/birdMakerIcon.png");
		this.docking_max = docking_max;
		dockings = new Bird[docking_max];
	}

	@Override
	public void idle() {
		super.idle();
		if(aliveShips() > 0 && GHQ.nowFrame() % 10 == 0) {
			// auto depart docking ships
			Unit closestEnemy = GHQ.stage().getNearstEnemy(owner);
			if (closestEnemy != null) {
				final double targetDistance = closestEnemy.point().distance(owner);
				// depart distance
				if (targetDistance < 600) {
					//System.out.println(owner.uniqueID + ": claimed attack");
					boolean airstripOccupied = GHQ.passedFrame(lastDepartFrame) < DEPART_INTERVAL;
					for(Bird bird : dockings) {
						if(bird.hasDeleteClaimFromStage() && bird.isAlive()) {
							if(!airstripOccupied) {
								GHQ.stage().addUnit(bird.respawn(owner.point()));
								airstripOccupied = true;
								lastDepartFrame = GHQ.nowFrame();
							}
						} else {
							bird.setCommand(Command.ATTACK);
						}
					}
				} else if (targetDistance > 1800) { // ignore distance
					//System.out.println(owner.uniqueID + ": claimed return");
					for(Bird bird : dockings) {
						bird.setCommand(Command.RETURN);
					}
				}
			} else {
				for(Bird bird : dockings) {
					bird.setCommand(Command.RETURN);
				}
			}
		}
	}
	
	protected int aliveShips() {
		int count = 0;
		for(Bird docking : dockings) {
			if(docking.isAlive()) {
				++count;
			}
		}
		return count;
	}
	@Override
	public boolean canWork() {
		return aliveShips() < dockings.length && super.canWork();
	}
	
	@Override
	protected void work() {
		for(Bird docking : dockings) {
			if(!docking.isAlive()) {
				docking.addHP(1); // just sign alive, do full charge when depart
				//System.out.println(owner.uniqueID + ": produced to " + aliveShips());
				break;
			}
		}
	}

	@Override
	public BirdMaker replicate() {
		return new BirdMaker(docking_max);
	}
	
	@Override
	public BirdMaker setOwner(FloatUnit owner) {
		super.setOwner(owner);
		for(int i = 0; i < dockings.length; ++i)
			dockings[i] = new Bird(owner, bird -> bird.claimDeleteFromStage());
		return this;
	}
}
