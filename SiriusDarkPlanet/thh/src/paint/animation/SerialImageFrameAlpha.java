package paint.animation;

import core.GHQ;
import core.GHQObject;

public class SerialImageFrameAlpha extends SerialImageFrame{
	private int startFrame;
	
	public SerialImageFrameAlpha(GHQObject owner, int spanFrame, String... imageURLs) {
		super(owner, spanFrame, imageURLs);
	}
	
	public void animate() {
		startFrame = GHQ.nowFrame();
	}
	
	private float decideAlpha() {
		if(GHQ.passedFrame(startFrame) >= images.length*span)
			return 1.0f;
		else
			return (float)(GHQ.passedFrame(startFrame)%span)/span;
	}
	private int decideImageID() {
		if(GHQ.passedFrame(startFrame) >= images.length*span)
			return images.length - 1;
		else {
			return GHQ.passedFrame(startFrame) / span;
		}
	}
	@Override
	public void dotPaint(int x, int y) {
		final int ID = decideImageID();
		for(int i = 0;i < ID;++i)
			images[i].dotPaint(x, y);
		GHQ.setImageAlpha(decideAlpha());
		images[ID].dotPaint(x, y);
		GHQ.setImageAlpha();
	}
	@Override
	public void rectPaint(int x, int y, int w, int h) {
		final int ID = decideImageID();
		for(int i = 0;i < ID;++i)
			images[i].rectPaint(x, y, w, h);
		GHQ.setImageAlpha(decideAlpha());
		images[ID].rectPaint(x, y, w, h);
		GHQ.setImageAlpha();
	}
}
