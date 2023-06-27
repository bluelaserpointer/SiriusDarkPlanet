package floatGame.modules;

import core.GHQ;
import floatGame.unit.Balloon;
import floatGame.unit.FloatUnit;
import paint.ImageFrame;

public class BalloonMaker extends FloatFactoryModule {

	final int DEPART_INTERVAL = 10;
	int lastDepartFrame = -1000;
	protected final Balloon[] dockings;
	protected final int production_max;
	
	public BalloonMaker(int production_max) {
		super(150, 50, 500);
		this.production_max = production_max;
		icon = ImageFrame.create("floatGame/image/balloonMakerIcon.png");
		dockings = new Balloon[production_max];
	}

	@Override
	public void idle() {
		super.idle();
		if(aliveShips() > 0 && GHQ.nowFrame() % 10 == 0) {
			// auto depart docking ships
			boolean airstripOccupied = GHQ.passedFrame(lastDepartFrame) < DEPART_INTERVAL;
			for(Balloon ship : dockings) {
				if(ship.hasDeleteClaimFromStage() && ship.isAlive()) {
					if(!airstripOccupied) {
						GHQ.stage().addUnit(ship.respawn(owner.point()));
						airstripOccupied = true;
						lastDepartFrame = GHQ.nowFrame();
					}
				} else {
					//bird.setCommand(Command.ATTACK); //currently no condition for balloons return
				}
			}
		}
	}

	@Override
	public boolean canWork() {
		return aliveShips() < dockings.length && super.canWork();
	}
	
	@Override
	protected void work() {
		for(Balloon docking : dockings) {
			if(!docking.isAlive()) {
				docking.addHP(1); // just sign alive, do full charge when depart
				//System.out.println(owner.uniqueID + ": produced to " + aliveShips());
				break;
			}
		}
	}

	@Override
	public BalloonMaker replicate() {
		return new BalloonMaker(production_max);
	}
	
	private int aliveShips() {
		int count = 0;
		for(Balloon baloon : dockings) {
			if(baloon.isAlive()) {
				++count;
			}
		}
		return count;
	}

	@Override
	public BalloonMaker setOwner(FloatUnit owner) {
		super.setOwner(owner);
		if(owner != null) {
			for(int i = 0; i < dockings.length; ++i)
				dockings[i] = new Balloon(hitGroup());
		}
		return this;
	}
}
