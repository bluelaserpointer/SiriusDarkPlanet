package floatGame.ui;

import java.awt.Color;
import java.util.List;

import core.GHQ;
import floatGame.engine.FloatGame;
import floatGame.modules.FloatModule;
import floatGame.unit.FloatFactory;
import floatGame.unit.Player;
import floatGame.unit.ShipDesign;

public class ShipDetailUI {
	static int closeup = 0;
	static int closeup_max = 10;
	static FloatFactory lastFactory;
	static boolean doCloseup;
	public static void show(FloatFactory factory) {
		lastFactory = factory;
		doCloseup = true;
	}
	public static void idle() {
		if(lastFactory == null || !doCloseup) {
			if(closeup > 0)
				--closeup;
			if(lastFactory == null || closeup == 0)
				return;
		} else if((closeup += 2) > closeup_max) {
			closeup = closeup_max;
		}
		double closeupRate = (double)closeup / (double)closeup_max;
		final int tx = lastFactory.cx() - GHQ.cameraLeft(), ty = lastFactory.cy() - GHQ.cameraTop();
		GHQ.getG2D().translate(tx, ty);
		GHQ.getG2D().scale(closeupRate, closeupRate);
		int width = 625, height = 125;
		int left = - width/2, top = 0;
		GHQ.getG2D(Color.BLACK).drawRect(left, top, width, height);
		final Player player = FloatGame.player;
		List<FloatModule> modules = player.modules();
		final ShipDesign shipDesign = player.shipDesign();
		shipDesign.dotPaint(left + shipDesign.width, top + shipDesign.height, modules);
		GHQ.getG2D().scale(1.0 / closeupRate, 1.0 / closeupRate);
		GHQ.getG2D().translate(-tx, -ty);
		doCloseup = false;
	}
}
