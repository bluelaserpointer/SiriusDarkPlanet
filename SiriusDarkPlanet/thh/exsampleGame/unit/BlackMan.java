package exsampleGame.unit;

import core.GHQ;
import exsampleGame.unit.EnemyWeaponLibrary;
import paint.ImageFrame;
import physics.HasPoint;
import preset.unit.Unit;

public class BlackMan extends THH_BasicEnemy {
	{
		charaSpeed = 2;
	}
	
	public BlackMan(int initialGroup) {
		super(120, initialGroup);
	}
	@Override
	public final String name() {
		return "BlackMan";
	}
	
	@Override
	public final void loadImageData(){
		super.loadImageData();
		charaPaint = ImageFrame.create("thhimage/BlackBall.png");
		bulletPaint[0] = ImageFrame.create("thhimage/DarkNiddle.png");
		bulletPaint[1] = ImageFrame.create("thhimage/DodgeMarker.png");
		weapon[0] = EnemyWeaponLibrary.getWeaponController(EnemyWeaponLibrary.lightBall_S);
	}
	@Override
	public void idle() {
		super.idle();
		final Unit targetEnemy = GHQ.stage().getNearstVisibleEnemy(this);
		if(targetEnemy != null) {
			final double ANGLE = point().angleTo(dstPoint.setXY(targetEnemy));
			angle().set(ANGLE);
			if(weapon[0].triggerSucceed(this)) {
				EnemyWeaponLibrary.inputBulletInfo(this,EnemyWeaponLibrary.BLACK_SLASH_BURST, bulletPaint[0], targetEnemy);
				EnemyWeaponLibrary.inputBulletInfo(this,EnemyWeaponLibrary.BLACK_SLASH_BURST, bulletPaint[1], targetEnemy);
			}
		}
		approachIfNoObstacles(dstPoint, charaSpeed);
	}
	@Override
	public void setBullet(int kind, HasPoint source) {}
}
