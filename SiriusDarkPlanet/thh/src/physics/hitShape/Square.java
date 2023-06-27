package physics.hitShape;

import core.GHQ;
import physics.HasPoint;

public class Square extends AbstractRectShape {
	private static final long serialVersionUID = 8168254451812660305L;
	public static final Square SQUARE_10 = new Square(10);
	protected int side;
	public Square(HasPoint owner, int side) {
		super(owner);
		this.side = side;
	}
	public Square(HasPoint owner) {
		super(owner);
	}
	public Square(int side) {
		this.side = side;
	}
	public Square() {}
	
	//tool
	@Override
	public HitShape clone(HasPoint newOwner) {
		return new Square(newOwner, side);
	}
	
	//information
	public int side() {
		return side != AbstractRectShape.MATCH_SCREEN_SIZE ? side : Math.min(GHQ.screenW(), GHQ.screenH());
	}
	@Override
	public int width() {
		return side();
	}
	@Override
	public int height() {
		return side();
	}
	public Square setSide(int side) {
		this.side = side;
		return this;
	}
	@Override
	public Square setWidth(int w) {
		return setSide(w);
	}
	@Override
	public Square setHeight(int h) {
		return setSide(h);
	}
}
