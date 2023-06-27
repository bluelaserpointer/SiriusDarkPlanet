package troubleCrasher.effect;

import java.awt.Color;

import core.GHQ;
import paint.ImageFrame;
import paint.dot.DotPaint;

public class CrowSlashEffect extends DotPaint {
	final ImageFrame crowSlashIF = ImageFrame.create("thhimage/effects/crowSlash.png");

	@Override
	public void dotPaint(int x, int y) {
		crowSlashIF.dotPaint(100, 100);
	}

	@Override
	public int width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}
}
