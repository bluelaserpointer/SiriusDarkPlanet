package physics.hitShape;

import core.GHQ;
import physics.HasPoint;

public class ScreenRectShape extends AbstractRectShape {
	private static final long serialVersionUID = 3235473211983843382L;
	public static final int MATCH_SCREEN_SIZE = -1;
	protected int width, height;
	public ScreenRectShape(HasPoint owner) {
		super(owner);
	}
	public ScreenRectShape() {}
	@Override
	public int width() {
		return GHQ.screenW();
	}
	@Override
	public int height() {
		return GHQ.screenH();
	}
	@Override
	public ScreenRectShape setWidth(int w) {
		return this;
	}
	@Override
	public ScreenRectShape setHeight(int h) {
		return this;
	}
	@Override
	public HitShape clone(HasPoint newOwner) {
		return new ScreenRectShape(super.owner());
	}
}
