package exsampleGame.unit;

import static java.lang.Math.*;

import core.GHQ;
import paint.dot.DotPaint;
import physics.Point;
import preset.unit.Unit;
import weapon.Weapon;

public class EnemyWeaponLibrary{
	public static final int lightBall_S = 0,lightBall_ROUND = 1,BLACK_SLASH_BURST = 2,HEAL_SHOTGUN = 3;
	
	public static Weapon getWeaponController(int bulletKind) {
		final Weapon WEAPON = new Weapon();
		switch(bulletKind) {
		case lightBall_S:
		case lightBall_ROUND:
			WEAPON.name = "lightBall_S";
			WEAPON.setMagazineSize(6);
			WEAPON.setCoolTime(5);
			WEAPON.setReloadTime(100);
			break;
		case HEAL_SHOTGUN:
			WEAPON.name = "HEAL_SHOTGUN";
			WEAPON.setMagazineSize(3);
			WEAPON.setCoolTime(5);
			WEAPON.setReloadTime(20);
			break;
		case BLACK_SLASH_BURST:
			WEAPON.name = "BLACK_SLASH_BURST";
			WEAPON.setMagazineSize(1);
			WEAPON.setCoolTime(5);
			WEAPON.setReloadTime(40);
			break;
		}
		return WEAPON;
	}
	public static void inputBulletInfo(Unit user,int bulletKind,DotPaint paintScript,Unit targetChara) {
		switch(bulletKind) {
		case lightBall_S:
			GHQ.stage().addBullet(new THH_BulletLibrary.LightBall(user));
			break;
		case lightBall_ROUND:
			Point.split_Burst(() -> new THH_BulletLibrary.LightBall(user), 5, 10, 12.0);
			break;
		case HEAL_SHOTGUN:
			Point.split_Burst(() -> new THH_BulletLibrary.HealShotgun(user), 10, 16, 15.0);
			break;
		case BLACK_SLASH_BURST:
			final double DEG10 = PI/18;
			Point.split_NWay(() -> new THH_BulletLibrary.BlackSlashBurst(user), 10, new double[] {-DEG10, 0.0, +DEG10}, 20);
			break;
		}
	}
}
