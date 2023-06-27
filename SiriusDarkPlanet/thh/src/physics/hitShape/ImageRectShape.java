package physics.hitShape;

import paint.dot.HasDotPaint;
import physics.HasPoint;

public class ImageRectShape extends RectShape {
	private static final long serialVersionUID = 1238409585756263924L;
	
	protected HasDotPaint paintOwner;
	public ImageRectShape(HasPoint pointOwner, HasDotPaint paintOwner) {
		super(pointOwner, paintOwner.getDotPaint().width(), paintOwner.getDotPaint().height());
		this.paintOwner = paintOwner;
	}
	
	//tool
	@Override
	public HitShape clone(HasPoint newOwner) {
		return new ImageRectShape(newOwner, paintOwner);
	}
	
	//information
	@Override
	public int width() {
		return paintOwner.getDotPaint().width();
	}
	@Override
	public int height() {
		return paintOwner.getDotPaint().height();
	}
}
