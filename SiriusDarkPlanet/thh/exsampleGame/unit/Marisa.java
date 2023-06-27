package exsampleGame.unit;

import core.GHQ;
import paint.ImageFrame;
import physics.HasPoint;
import physics.Point;
import weapon.Weapon;

public class Marisa extends THH_BasicPlayer {
	
	public Marisa(int initialGroup) {
		super(20, initialGroup);
	}
	@Override
	public final String name() {
		return "Marisa";
	}
	
	//weapon&bullet kind name
	static final int
		MILLKY_WAY = 0,NARROW_SPARK = 1,REUSE_BOMB = 2,MAGIC_MISSILE = 3;
	
	//GUI
		
	//Sounds
	
	@Override
	public final void loadImageData(){
		super.loadImageData();
		charaPaint = ImageFrame.create("thhimage/Marisa.png");
		iconPaint = ImageFrame.create("thhimage/MarisaIcon.png");

		//test area
		//weaponSlot[0] = REUSE_BOMB;
		weaponSlot[0] = MAGIC_MISSILE;
		spellSlot[0] = NARROW_SPARK;
		spellSlot[1] = REUSE_BOMB;
		////weaponLoad
		//MILLKY_WAY
		weapon[MILLKY_WAY] = new Weapon();
		weapon[MILLKY_WAY].name = "MILLKY_WAY";
		weapon[MILLKY_WAY].setCoolTime(10);
		//NARROW_SPARK
		weapon[NARROW_SPARK] = new Weapon();
		weapon[NARROW_SPARK].name = "NARROW_SPARK";
		weapon[NARROW_SPARK].setCoolTime(150);
		weapon[NARROW_SPARK].setMagazineSize(1);
		//REUSE_BOMB
		weapon[REUSE_BOMB] = new Weapon();
		weapon[REUSE_BOMB].name = "REUSE_BOMB";
		weapon[REUSE_BOMB].setCoolTime(10);
		//MAGIC_MISSILE
		weapon[MAGIC_MISSILE] = new Weapon();
		weapon[MAGIC_MISSILE].name = "MAGIC_MISSILE";
		weapon[MAGIC_MISSILE].setCoolTime(25);
		/////////////////////
		slot_spell = 0;
	}
	@Override
	public final void setBullet(int kind, HasPoint user) {
		switch(kind){
		case MILLKY_WAY:
			System.out.println("marisa MILLKY_WAY!");
			Point.split_Round(() -> GHQ.stage().addBullet(new THH_BulletLibrary.MillkyWay(this)), 50, 8);
			break;
		case NARROW_SPARK:
			System.out.println("marisa NARROW_SPARK!");
			//message
			//if(this == source) {
			//	THH.addMessage(this,charaID,"KOIFU [MasterSpark]");
			//	THH.addMessage(this,charaID,"TEST MESSAGE 2");
			//}
			GHQ.stage().addBullet(new THH_BulletLibrary.NarrowSpark(this));
			break;
		case REUSE_BOMB:
			System.out.println("marisa REUSE_BOMB!");
			Point.split_Round(() -> GHQ.stage().addBullet(new THH_BulletLibrary.ReuseBomb(this)), 50, 3);
			break;
		case MAGIC_MISSILE:
			System.out.println("marisa MAGIC_MISSILE!");
			GHQ.stage().addBullet(new THH_BulletLibrary.MagicMissile(this));
			break;
		}
	}
}
