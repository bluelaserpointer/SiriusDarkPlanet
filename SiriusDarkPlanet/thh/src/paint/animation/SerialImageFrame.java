package paint.animation;

import java.util.HashMap;

import core.GHQ;
import core.GHQObject;
import paint.ImageFrame;
import paint.dot.DotPaint;

public class SerialImageFrame extends DotPaint {
	protected final ImageFrame[] images;
	protected int span;
	private static final HashMap<String, ImageFrame> createdList = new HashMap<String, ImageFrame>();
	
	//init
	public SerialImageFrame(GHQObject owner, int spanFrame, String... imageURL) {
		super(owner);
		images = new ImageFrame[imageURL.length];
		for(int i = 0;i < imageURL.length;++i) {
			images[i] = ImageFrame.create(owner, imageURL[i]);
			if(!createdList.containsKey(imageURL[i]))
				createdList.put(imageURL[i], images[i]);
		}
		span = spanFrame;
	}
	public SerialImageFrame(int spanFrame, String... imageURL) {
		images = new ImageFrame[imageURL.length];
		for(int i = 0;i < imageURL.length;++i) {
			images[i] = ImageFrame.create(owner, imageURL[i]);
			if(!createdList.containsKey(imageURL[i]))
				createdList.put(imageURL[i], images[i]);
		}
		span = spanFrame;
	}

	//main role
	protected ImageFrame decideImage() {
		return images[GHQ.nowFrame() % (images.length*span) / span];
	}
	@Override
	public void dotPaint(int x, int y) {
		decideImage().dotPaint(x, y);
	}
	@Override
	public void rectPaint(int x, int y, int w, int h) {
		decideImage().rectPaint(x, y, w, h);
	}
	
	//information
	@Override
	public int width() {
		return decideImage().width();
	}
	@Override
	public int height() {
		return decideImage().height();
	}
}
