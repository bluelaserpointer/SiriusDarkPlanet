package troubleCrasher.effect;

import java.awt.Color;

import core.GHQ;
import paint.ImageFrame;
import paint.dot.DotPaint;

public class HealingEffect extends DotPaint {
	final ImageFrame healingPlusIF = ImageFrame.create("thhimage/effects/healing1.png");

	private int animationStartFrame;
	private int yPos;
	
	@Override
	public void dotPaint(int x, int y) {
		final int passedFrame = GHQ.passedFrame(animationStartFrame);
		if(passedFrame < 15) {
			yPos += passedFrame*2;
			healingPlusIF.dotPaint(x, y + 50 - yPos);
			healingPlusIF.dotPaint_rate(x + 75, y + 80 - yPos, 0.4);
			healingPlusIF.dotPaint_rate(x - 82, y + 90 - yPos, 0.4);
			GHQ.getG2D(Color.GREEN);
			GHQ.drawString_center("HEAL", x, y - yPos/3, 50);
		} else if(passedFrame < 25) {
			GHQ.getG2D(Color.GREEN);
			GHQ.drawString_center("HEAL", x, y - yPos/3, 50);
		}
	}

	@Override
	public int width() {
		return 200;
	}
	
	@Override
	public int height() {
		return 200;
	}
	
	public void startAnimation() {
		animationStartFrame = GHQ.nowFrame();
		yPos = 0;
	}
}
