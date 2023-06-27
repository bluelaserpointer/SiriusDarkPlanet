package floatGame.ui;

import java.awt.Color;

import core.GHQ;
import floatGame.engine.FloatGame;
import floatGame.engine.FloatStageKind;
import gui.BasicButton;
import gui.GUIParts;
import paint.ImageFrame;
import paint.text.StringPaint;
import physics.Angle;

public class FloatStageSelectScreen extends GUIParts {
	
	private final PlanetViewer planetViewer;
	
	protected static int page = 0;
	
	public FloatStageSelectScreen() {
		super.setBGColor(Color.DARK_GRAY);
		super.addLast(planetViewer = new PlanetViewer())
			.setBounds(GHQ.screenW()/2 - 400, 100, 800, 400);
		super.addLast(new BasicButton().setText(new StringPaint("Next", GHQ.getG2D().getFont(), Color.WHITE))
			.setClickEvent(e -> nextUnitPage()))
			.setBounds(planetViewer.right(), planetViewer.top(), 50, planetViewer.height());
		super.addLast(new BasicButton().setText(new StringPaint("Prev", GHQ.getG2D().getFont(), Color.WHITE))
			.setClickEvent(e -> prevUnitPage()))
			.setBounds(planetViewer.left() - 50, planetViewer.top(), 50, planetViewer.height());
		super.addLast(new BasicButton().setText(new StringPaint("Back", GHQ.getG2D().getFont(), Color.WHITE))
			.setClickEvent(e -> FloatGame.toMainMenu()))
			.setBGColor(Color.BLACK)
			.setBounds(GHQ.screenW()/2 - 100, GHQ.screenH() - 50, 200, 50);
		super.addLast(new BasicButton().setText(new StringPaint("Start", GHQ.getG2D().getFont(), Color.WHITE))
				.setClickEvent(e -> { FloatGame.mainScreen().switchTo(FloatGame.GAME_SCREEN); }))
				.setBGColor(Color.BLACK)
				.setBounds(GHQ.screenW()/2 - 100, GHQ.screenH() - 100, 200, 50);
	}
	
	private void nextUnitPage() {
		if(++page >= FloatStageKind.values().length)
			page = 0;
		planetViewer.planetTargetAngle = Math.PI * 2.0 * (double)page / FloatStageKind.values().length;
	}
	private void prevUnitPage() {
		if(--page < 0)
			page = FloatStageKind.values().length - 1;
		planetViewer.planetTargetAngle = Math.PI * 2.0 * (double)page / FloatStageKind.values().length;
	}
	
	class PlanetViewer extends GUIParts {
		protected final ImageFrame planetIF = ImageFrame.create("floatGame/image/planet.png");
		
		double planetAngle;
		double planetTargetAngle;
		private static final double planetTurnSpeed = 0.2;
		@Override
		public void paint() {
			super.paint();
			planetAngle = Angle.spinTo_ConstSpd(planetAngle, planetTargetAngle, planetTurnSpeed);
			planetIF.dotPaint_turn(cx(), cy() - 300, planetAngle);
			GHQ.getG2D((selectedStageKind() == FloatStageKind.The_Sirius) ? Color.RED : Color.WHITE);
			GHQ.drawString_center(selectedStageKind().name(), cx(), cy() + 50, 20);
			GHQ.drawString_center(selectedStageKind().description, cx(), cy() + 100, 20);
		}
	}
	
	public static FloatStageKind selectedStageKind() {
		return FloatStageKind.values()[page];
	}
}
