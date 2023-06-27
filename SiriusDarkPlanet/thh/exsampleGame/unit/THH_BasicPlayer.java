package exsampleGame.unit;

import core.GHQ;

public abstract class THH_BasicPlayer extends THH_BasicUnit {
	public THH_BasicPlayer(int charaSize, int initialGroup) {
		super(charaSize, initialGroup);
	}

	@Override
	public void idle() {
		super.idle();
		final int mouseX = GHQ.mouseX(), mouseY = GHQ.mouseY();
		////////////
		//main
		////////////
		// angle
		angle().set(point().angleToMouse());
		// dodge
		if (dodgeOrder)
			dodge(mouseX, mouseY);
		// attack
		if (attackOrder) {
			final int weaponID = weaponSlot[slot_weapon];
			if (weaponID != GHQ.NONE && useWeapon(weaponID))
				setBullet(weaponID,this);
		}
		// spell
		if (spellOrder) {
			final int spell = spellSlot[slot_spell];
			if (spell != GHQ.NONE && useWeapon(spell))
				setBullet(spell,this);
		}
		approachIfNoObstacles(dstPoint, charaSpeed);
		// weaponChange
		int roll = weaponChangeOrder;
		if (roll != 0) {
			int target = slot_spell;
			if (roll > 0) {
				while (target < spellSlot_max - 1) {
					if (spellSlot[++target] != GHQ.NONE) {
						if (--roll == 0)
							break;
					}
				}
			} else {
				while (target > 0) {
					if (spellSlot[--target] != GHQ.NONE) {
						if (++roll == 0)
							break;
					}
				}
			}
			slot_spell = target;
		}
		////////////
		//input
		////////////
		resetSingleOrder();
	}
}
