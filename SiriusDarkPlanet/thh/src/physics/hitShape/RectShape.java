package physics.hitShape;

import core.GHQ;
import physics.HasPoint;

public class RectShape extends AbstractRectShape {
	private static final long serialVersionUID = 3869237032416439346L;

	protected int width, height;
	public RectShape(HasPoint owner, int w, int h) {
		super(owner);
		super.setBoundsSize(w, h);
	}
	public RectShape(int x, int y, int w, int h) {
		super(HasPoint.generate(x, y));
		super.setBoundsSize(w, h);
	}
	//init
	@Override
	public RectShape setWidth(int w) {
		width = w;
		return this;
	}
	@Override
	public RectShape setHeight(int h) {
		height = h;
		return this;
	}
	//tool
	@Override
	public HitShape clone(HasPoint newOwner) {
		return new RectShape(newOwner, width(), height());
	}
	
	//information
	@Override
	public int width() {
		return width != AbstractRectShape.MATCH_SCREEN_SIZE ? width : GHQ.screenW();
	}
	@Override
	public int height() {
		return height != AbstractRectShape.MATCH_SCREEN_SIZE ? height : GHQ.screenH();
	}
}
