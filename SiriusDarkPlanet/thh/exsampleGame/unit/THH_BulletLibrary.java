package exsampleGame.unit;

import static java.lang.Math.PI;

import core.GHQ;
import core.GHQObject;
import paint.ImageFrame;
import paint.dot.DotPaint;
import physics.hitShape.Circle;
import preset.bullet.Bullet;
import preset.unit.Unit;

public abstract class THH_BulletLibrary extends Bullet{
	public THH_BulletLibrary(Unit shooter) {
		super(shooter);
	}
	/////////////////
	/*	<Parameters and their default values of Bullet>
	 * 
	 *	name = GHQ.NOT_NAMED;
	 *	hitShape = new Square(10);
	 *	setDamage(0;
	 *	limitFrame = GHQ.MAX;
	 *	limitRange = GHQ.MAX;
	 *	penetration = 1;
	 *	reflection = 0;
	 *	accel = 1.0;
	 *	paintScript = DotPaint.BLANK_SCRIPT;
	 *	isLaser = false;
	 */
	/////////////////
	//Reimu - 1
	/////////////////
	public static class FudaKouhaku extends THH_BulletLibrary {
		private static final DotPaint paint = ImageFrame.create("thhimage/KouhakuNoFuda.png");
		public FudaKouhaku(Unit shooterUnit) {
			super(shooterUnit);
			name = "FudaKouhaku";
			point().setSpeed(20);
			physics().setHitShape(new Circle(this, 10));
			setDamage(new THH_damage(50));
			limitFrame = 200;
			paintScript = paint;
		}
		@Override
		public final void hitObject(GHQObject object) {
			GHQ.stage().addEffect(new THH_EffectLibrary.FudaHitEF(this, true));
		}
	}
	/////////////////
	//Reimu - 2
	/////////////////
	public static class FudaShiroKuro extends THH_BulletLibrary{
		private static final DotPaint paint = ImageFrame.create("thhimage/ShirokuroNoFuda.png");
		public FudaShiroKuro(Unit shooterUnit) {
			super(shooterUnit);
			name = "FudaShiroKuro";
			point().fastParaAdd_DASpd(0.0, shooterUnit.angle().get(), 20);
			physics().setHitShape(new Circle(this, 10));
			setDamage(new THH_damage(20));
			limitFrame = 200;
			paintScript = paint;
		}
	}
	/////////////////
	//Reimu - 3
	/////////////////
	public static class FudaSouhaku extends THH_BulletLibrary{
		private static final DotPaint paint = ImageFrame.create("thhimage/SouhakuNoFuda.png");
		public FudaSouhaku(Unit shooterUnit) {
			super(shooterUnit);
			name = "FudaSouhaku";
			point().setSpeed(20);
			physics().setHitShape(new Circle(this, 15));
			setDamage(new THH_damage(25));
			reflection = 1;
			limitFrame = 40;
			accel = -2.0;
			paintScript = paint;
		}
	}
	/////////////////
	//Marisa - 1
	/////////////////
	public static class MillkyWay extends THH_BulletLibrary{
		private static final DotPaint paint = ImageFrame.create("thhimage/MillkyWay.png");
		public MillkyWay(Unit shooterUnit) {
			super(shooterUnit);
			name = "MillkyWay";
			point().fastParaAdd_DASpd(0.0, shooterUnit.angle().get(), 20);
			physics().setHitShape(new Circle(this, 30));
			setDamage(new THH_damage(40));
			limitFrame = 200;
			paintScript = paint;
		}
	}
	/////////////////
	//Marisa - 2
	/////////////////
	public static class NarrowSpark extends THH_BulletLibrary{
		private static final DotPaint paint = ImageFrame.create("thhimage/NarrowSpark_2.png");
		public NarrowSpark(Unit shooterUnit) {
			super(shooterUnit);
			name = "NarrowSpark";
			point().setSpeed(2);
			physics().setHitShape(new Circle(this, 15));
			setDamage(new THH_damage(8));
			penetration = GHQ.MAX;
			reflection = 3;
			limitFrame = 40;
			paintScript = paint;
		}
		@Override
		public void idle() {
			if(defaultDeleteCheck())
				return;
			while(point().inStage()) {
				paint();
				if(!dynamIdle())
					break;
			}
			point().setXY(shooter);
			point().setMoveAngle(shooter.angle().get());
			return;
		}
		@Override
		public void hitObject(GHQObject object) {
			GHQ.stage().addEffect(new THH_EffectLibrary.SparkHitEF(this));
		}
	}
	/////////////////
	//Marisa - 3
	/////////////////
	public static class ReuseBomb extends THH_BulletLibrary{
		private static final DotPaint paint = ImageFrame.create("thhimage/ReuseBomb.png");
		public ReuseBomb(Unit shooterUnit) {
			super(shooterUnit);
			name = "ReuseBomb";
			physics().setHitShape(new Circle(this, 30));
			point().setSpeed(40);
			accel = -1.0;
			setDamage(new THH_damage(10));
			limitFrame = 200;
			paintScript = paint;
		}
		@Override
		public void idle() {
			super.idle();
			if(Math.random() < 0.2)
				GHQ.stage().addEffect(new THH_EffectLibrary.LightningEF(this));
		}
	}
	/////////////////
	//Marisa - 4
	/////////////////
	public static class MagicMissile extends THH_BulletLibrary{
		private static final DotPaint paint = ImageFrame.create("thhimage/MagicMissile.png");
		public MagicMissile(Unit shooterUnit) {
			super(shooterUnit);
			name = "MAGIC_MISSILE";
			physics().setHitShape(new Circle(this, 20));
			accel = 1.07;
			setDamage(new THH_damage(500));
			limitFrame = 2000;
			paintScript = paint;
			point().fastParaAdd_DASpd(10, GHQ.random2(PI/36), 10.0);
		}
		@Override
		public void idle() {
			super.idle();
			for(int i = 0;i < 2;i++)
				GHQ.stage().addEffect(new THH_EffectLibrary.MissileTraceA_EF(this, true));
			GHQ.stage().addEffect(new THH_EffectLibrary.MissileTraceB_EF(this));
		}
		@Override
		public void hitObject(GHQObject object) {
			GHQ.stage().addEffect(new THH_EffectLibrary.MissileHitEF(this, true));
		}
	}
	/////////////////
	//Enemy - 1
	/////////////////
	public static class LightBall extends THH_BulletLibrary{
		private static final DotPaint paint = ImageFrame.create("thhimage/LightBallA.png");
		public LightBall(Unit shooterUnit) {
			super(shooterUnit);
			name = "LightBall";
			physics().setHitShape(new Circle(this, 10));
			point().fastParaAdd_DASpd(10, shooterUnit.angle().get(), 3);
			setDamage(new THH_damage(20));
			paintScript = paint;
		}
	}
	/////////////////
	//Enemy - 2
	/////////////////
	public static class HealShotgun extends THH_BulletLibrary{
		private static final DotPaint paint = ImageFrame.create("thhimage/DarkNiddle2.png");
		public HealShotgun(Unit shooterUnit) {
			super(shooterUnit);
			name = "HealShotgun";
			physics().setHitShape(new Circle(this, 10));
			point().fastParaAdd_DASpd(10, shooterUnit.angle().get(), 3);
			setDamage(new THH_damage(-20));
			limitRange = 150;
			paintScript = paint;
		}
	}
	/////////////////
	//Enemy - 2
	/////////////////
	public static class BlackSlashBurst extends THH_BulletLibrary{
		private static final DotPaint paint = ImageFrame.create("thhimage/DarkNiddle.png");
		public BlackSlashBurst(Unit shooterUnit) {
			super(shooterUnit);
			name = "BlackSlashBurst";
			physics().setHitShape(new Circle(this, 10));
			setDamage(new THH_damage(20));
			reflection = 1;
			limitFrame = 200;
			paintScript = paint;
		}
	}
}
