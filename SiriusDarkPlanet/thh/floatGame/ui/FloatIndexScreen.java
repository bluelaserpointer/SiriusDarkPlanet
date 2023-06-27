package floatGame.ui;

import java.awt.Color;

import core.GHQ;
import floatGame.engine.FloatGame;
import floatGame.unit.Army1;
import floatGame.unit.Balloon;
import floatGame.unit.Bird;
import floatGame.unit.BirdMother;
import floatGame.unit.Bomber;
import floatGame.unit.Factory;
import floatGame.unit.FloatUnit;
import floatGame.unit.FlyingFactory;
import floatGame.unit.Player;
import gui.BasicButton;
import gui.GHQTextArea;
import gui.GUIParts;
import paint.text.StringPaint;

public class FloatIndexScreen extends GUIParts {
	
	private final FloatUnitPreviewer previewScreen;
	private final GHQTextArea descriptionTextBox;
	
	private final BirdMother tmpBirdMother = new BirdMother(FloatUnit.friendSide);
	int page = 0;
	private final FloatUnit[] units =
		{
				new Army1(FloatUnit.friendSide),
				new Balloon(FloatUnit.friendSide),
				new Bird(tmpBirdMother, null),
				new BirdMother(FloatUnit.friendSide),
				new Bomber(FloatUnit.friendSide),
				new Factory(1000, 1000.0, FloatUnit.friendSide),
				new Player(),
				new FlyingFactory(10000, 10000, FloatUnit.enemySide)
		};
	
	public FloatIndexScreen() {
		super.setBGColor(Color.DARK_GRAY);
		super.addLast(descriptionTextBox = new GHQTextArea().setTextColor(Color.WHITE))
			.setBGColor(Color.BLACK)
			.setBounds(GHQ.screenW()/2 - 400, 400, 800, 100);
		super.addLast(previewScreen = new FloatUnitPreviewer())
			.setBounds(GHQ.screenW()/2 - 400, 100, 800, 400);
		super.addLast(new BasicButton().setText(new StringPaint("Next", GHQ.getG2D().getFont(), Color.WHITE))
			.setClickEvent(e -> nextUnitPage()))
			.setBounds(previewScreen.right(), previewScreen.top(), 50, previewScreen.height());
		super.addLast(new BasicButton().setText(new StringPaint("Prev", GHQ.getG2D().getFont(), Color.WHITE))
			.setClickEvent(e -> prevUnitPage()))
			.setBounds(previewScreen.left() - 50, previewScreen.top(), 50, previewScreen.height());
		super.addLast(new BasicButton().setText(new StringPaint("Back", GHQ.getG2D().getFont(), Color.WHITE))
			.setClickEvent(e -> FloatGame.toMainMenu()))
			.setBGColor(Color.BLACK)
			.setBounds(GHQ.screenW()/2 - 100, GHQ.screenH() - 50, 200, 50);
		//set units position to center of viewer
		for(FloatUnit unit : units) {
			unit.point().setXY(previewScreen.cx(), previewScreen.cy());
		}
		//set first text
		descriptionTextBox.textArea().setText(units[page].description());
	}
	
	private void nextUnitPage() {
		if(++page >= units.length)
			page = 0;
		descriptionTextBox.textArea().setText(units[page].description());
	}
	private void prevUnitPage() {
		if(--page < 0)
			page = units.length - 1;
		descriptionTextBox.textArea().setText(units[page].description());
	}
	
	class FloatUnitPreviewer extends GUIParts {
//		private DotPaint unitIF;
//		public void setUnitIF(DotPaint unitIF) {
//			this.unitIF = unitIF;
//		}
		@Override
		public void paint() {
			super.paint();
			super.fillBoundingBox(Color.BLACK);
			super.drawBoundingBox(Color.BLUE, GHQ.stroke3);
			final int SPAN = 4;
			for(int i = 0; i < height()/SPAN; ++i) {
				final int y = top() + i*SPAN;
				GHQ.getG2D(new Color(0F, 0F, 1F, 0.5F), GHQ.stroke1).drawLine(left(), y, right(), y);
			}
			//unit.getDotPaint().dotPaint(cx(), cy());
			if(units[page] != null)
				units[page].paint();
		}
	}
}
