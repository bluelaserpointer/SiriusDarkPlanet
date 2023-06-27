package exsampleGame.unit;

import core.GHQ;
import exsampleGame.unit.EnemyWeaponLibrary;
import paint.ImageFrame;
import paint.dot.DotPaint;
import physics.HasPoint;
import preset.unit.Unit;

public class Fairy extends THH_BasicEnemy {
	public Fairy(int initialGroup) {
		super(70, initialGroup);
	}
	private DotPaint magicCircleIID = ImageFrame.create("thhimage/MagicCircleBlue.png");
	@Override
	public final String name() {
		return "FairyA";
	}
	
	@Override
	public final void loadImageData(){
		super.loadImageData();
		charaPaint = ImageFrame.create("thhimage/YouseiA.png");
		bulletPaint[0] = ImageFrame.create("thhimage/LightBallA.png");
		weapon[0] = EnemyWeaponLibrary.getWeaponController(EnemyWeaponLibrary.lightBall_S);
	}
	@Override
	public void idle() {
		super.idle();
		final Unit targetEnemy = GHQ.stage().getNearstVisibleEnemy(this);
		if(targetEnemy != null && weapon[0].triggerSucceed(this))
			EnemyWeaponLibrary.inputBulletInfo(this, EnemyWeaponLibrary.lightBall_ROUND,bulletPaint[0], targetEnemy);
	}
	@Override
	public void paint() {
		super.paintMode_magicCircle(magicCircleIID);
		GHQ.paintHPArc(point(), 20, HP);
	}
	@Override
	public void setBullet(int kind, HasPoint source) {}
}
