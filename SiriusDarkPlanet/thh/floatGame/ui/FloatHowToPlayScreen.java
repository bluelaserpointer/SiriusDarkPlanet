package floatGame.ui;

import java.awt.Color;

import core.GHQ;
import floatGame.engine.FloatGame;
import gui.BasicButton;
import gui.GUIParts;
import paint.ColorFraming;
import paint.ImageFrame;
import paint.dot.DotPaint;
import paint.text.StringPaint;

public class FloatHowToPlayScreen extends GUIParts {
	
	protected int page = 0;
	protected final DotPaint[] tipsIF;
	
	public FloatHowToPlayScreen() {
		super.setBGColor(Color.BLACK);
		//tips images
		tipsIF = new DotPaint[] {
			ImageFrame.create("floatGame/image/TIPS1.png"),
			ImageFrame.create("floatGame/image/TIPS2.png"),
			ImageFrame.create("floatGame/image/TIPS3.png"),
			ImageFrame.create("floatGame/image/TIPS4.png")
		};
		//buttons
		
		super.addLast(new BasicButton().setText(new StringPaint("Next", GHQ.getG2D().getFont(), Color.WHITE))
			.setClickEvent(e -> nextUnitPage())
			.setBGPaint(new ColorFraming(Color.BLUE, 3)))
			.setBounds(right() - 100, 100, 50, 400);
		super.addLast(new BasicButton().setText(new StringPaint("Prev", GHQ.getG2D().getFont(), Color.WHITE))
			.setClickEvent(e -> prevUnitPage())
			.setBGPaint(new ColorFraming(Color.BLUE, 3)))
		.setBounds(100, 100, 50, 400);
		super.addLast(new BasicButton().setText(new StringPaint("Back", GHQ.getG2D().getFont(), Color.WHITE))
			.setClickEvent(e -> FloatGame.toMainMenu()))
			.setBGColor(Color.BLACK)
			.setBounds(GHQ.screenW()/2 - 100, GHQ.screenH() - 50, 200, 50);
	}
	
	@Override
	public void paint() {
		super.paint();
		//vertical moving horizontal line
		final int twiceNowFrame = GHQ.nowFrame()*2;
		if(twiceNowFrame % (height()*2) < height()) {
			final int horizontalLineY = twiceNowFrame % height();
			GHQ.getG2D(new Color(0F, 0F, 1F, 0.7F), GHQ.stroke7).drawLine(left(), horizontalLineY, right(), horizontalLineY);
		}
		//tips image
		tipsIF[page].dotPaint(cx(), cy());
	}
	private void nextUnitPage() {
		if(++page >= tipsIF.length)
			page = 0;
	}
	private void prevUnitPage() {
		if(--page < 0)
			page = tipsIF.length - 1;
	}
}
