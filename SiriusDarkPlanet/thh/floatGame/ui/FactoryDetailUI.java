package floatGame.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import core.GHQ;
import floatGame.modules.FloatModule;
import floatGame.unit.FloatFactory;

public class FactoryDetailUI {
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
		int width = 625;//, height = 125;
		int left = - width/2, top = + 130;
		//GHQ.getG2D(Color.WHITE).drawRect(left, top, width, height);
		List<FloatModule> modules = lastFactory.modules();
		for(int i = 0; i < lastFactory.module_max(); ++i) {
			int cellW = 75, cellH = 75;
			int sectorW = cellW + 25;
			int cellLeft = left + 25 + sectorW*i, cellTop = top + 25;
			int cellCX = cellLeft + cellW/2;//, cellCY = cellTop + cellH/2;
			int cellRight = cellLeft + cellW, cellBottom = cellTop + cellH;
			GHQ.getG2D(Color.WHITE).drawRect(cellLeft, cellTop, cellW, cellH);
			if(i < modules.size()) {
				FloatModule module = modules.get(i);
				//draw rectangle progress circuit
				module.icon().rectPaint(cellLeft, cellTop, cellW, cellH);
				double rate = module.cooldownRate();
				Graphics2D g2 = GHQ.getG2D(Color.BLUE, GHQ.stroke3);
				if(rate < 0.125) {
					g2.drawLine(cellCX, cellTop, cellCX + (int)(rate/0.125*cellW/2), cellTop);
				} else if(rate < 0.125 + 0.25) {
					rate -= 0.125;
					g2.drawLine(cellCX, cellTop, cellCX + cellW/2, cellTop);
					g2.drawLine(cellRight , cellTop, cellRight, cellTop + (int)(rate/0.25*cellH));
				} else if(rate < 0.125 + 0.25*2) {
					rate -= 0.125 + 0.25;
					g2.drawLine(cellCX, cellTop, cellCX + cellW/2, cellTop);
					g2.drawLine(cellRight , cellTop, cellRight, cellTop + cellH);
					g2.drawLine(cellRight , cellBottom, cellRight - (int)(rate/0.25*cellW), cellBottom);
				} else if(rate < 0.125 + 0.25*3) {
					rate -= 0.125 + 0.25*2;
					g2.drawLine(cellCX, cellTop, cellCX + cellW/2, cellTop);
					g2.drawLine(cellRight , cellTop, cellRight, cellTop + cellH);
					g2.drawLine(cellLeft , cellBottom, cellRight, cellBottom);
					g2.drawLine(cellLeft , cellBottom, cellLeft, cellBottom - (int)(rate/0.25*cellH));
				} else {
					rate -= 0.125 + 0.25*3;
					g2.drawLine(cellCX, cellTop, cellCX + cellW/2, cellTop);
					g2.drawLine(cellRight , cellTop, cellRight, cellTop + cellH);
					g2.drawLine(cellLeft , cellBottom, cellRight, cellBottom);
					g2.drawLine(cellLeft , cellTop, cellLeft, cellBottom);
					g2.drawLine(cellLeft, cellTop, cellLeft + (int)(rate/0.125*cellW/2), cellTop);
				}
				//draw recently worked marking
				if(module.cooldown_max != 0) {
					final int framesFromLastWork = GHQ.passedFrame(module.lastWorkedFrame());
					if(framesFromLastWork < 10) {
						final int grow = framesFromLastWork*4;
						GHQ.getG2D(new Color(0F, 0F, 1F, (float)(1.0 - framesFromLastWork/10.0)), GHQ.stroke1)
							.drawRect(cellLeft - grow/2, cellTop - grow/2, cellW + grow, cellH + grow);
					}
				}
				GHQ.getG2D(Color.BLACK, GHQ.stroke1);
			}
		}
		GHQ.getG2D().scale(1.0 / closeupRate, 1.0 / closeupRate);
		GHQ.getG2D().translate(-tx, -ty);
		doCloseup = false;
	}
}
