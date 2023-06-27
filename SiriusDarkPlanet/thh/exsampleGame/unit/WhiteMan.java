package exsampleGame.unit;

import calculate.ConsumableEnergy;
import core.GHQ;
import paint.ImageFrame;
import physics.HasPoint;
import preset.unit.Unit;

public class WhiteMan extends THH_BasicEnemy {
	public WhiteMan(int initialGroup) {
		super(120, initialGroup);
	}
	{
		charaSpeed = 1;
	}
	@Override
	public final String name() {
		return "WhiteMan";
	}
	
	@Override
	public final void loadImageData(){
		super.loadImageData();
		charaPaint = ImageFrame.create("thhimage/WhiteBall.png");
		bulletPaint[0] = ImageFrame.create("thhimage/LightBallA.png");
	}
	@Override
	public void idle() {
		super.idle();
		final Unit blackManAdress = GHQ.stage().units.forName("BlackMan");
		if(blackManAdress == null)
			return;
		dstPoint.setXY(blackManAdress);
		final ConsumableEnergy THE_HP = ((THH_BasicUnit)blackManAdress).HP;
		if(!THE_HP.isMin() && !THE_HP.isMax() && point().inRange(dstPoint, 200)){
			THE_HP.consume(-100);
		}
		approachIfNoObstacles(dstPoint, charaSpeed);
	}
	@Override
	public void setBullet(int kind, HasPoint source) {}
}
