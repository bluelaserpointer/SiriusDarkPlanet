package floatGame.modules;

import core.GHQ;
import floatGame.modules.StarDust.NarrowBullet;
import paint.ImageFrame;
import preset.bullet.Bullet;

public class SpitFire extends FloatWeapon {

	public SpitFire() {
		super(50, 0.1, 5);
		icon = ImageFrame.create("floatGame/image/MeteorIcon.png");
		autoWork = true;
	}

	@Override
	public SpitFire replicate() {
		return new SpitFire();
	}

	@Override
	public boolean canWork() {
		return super.canWork() && owner.targetDistance() < 300;
	}
	@Override
	protected void work() {
		Bullet bullet1 = GHQ.stage().addBullet(new NarrowBullet(owner, attackAngle()));
		Bullet bullet2 = GHQ.stage().addBullet(new NarrowBullet(owner, attackAngle()));
		bullet1.point().setSpeed_DA(10, attackAngle() + Math.PI/36);
		bullet2.point().setSpeed_DA(10, attackAngle() - Math.PI/36);
		bullet1.limitFrame = 20;
		bullet2.limitFrame = 20;
	}
}
