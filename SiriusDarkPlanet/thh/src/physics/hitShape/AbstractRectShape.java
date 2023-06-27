package physics.hitShape;

import java.awt.Color;
import java.awt.Stroke;

import physics.HasPoint;

public abstract class AbstractRectShape extends HitShape {
	private static final long serialVersionUID = 437361374949056136L;
	public static final int MATCH_SCREEN_SIZE = -1;
	public AbstractRectShape(HasPoint owner) {
		super(owner);
	}
	public AbstractRectShape() {}
	public abstract AbstractRectShape setWidth(int w);
	public abstract AbstractRectShape setHeight(int h);
	public AbstractRectShape setBoundsSize(int w, int h) {
		setWidth(w);
		setHeight(h);
		return this;
	}
	public AbstractRectShape setBoundsSize(HasArea sample) {
		return setBoundsSize(sample.width(), sample.height());
	}
	
	//information
	@Override
	public int preciseIntersects(HitShape shape) {
		return -1;
	}
	@Override
	public boolean intersectsDot(int x, int y) {
		return super.boundingBoxIntersectsDot(x, y);
	}
	@Override
	public boolean intersectsLine(int x1, int y1, int x2, int y2) {
		return super.boundingBoxIntersectsLine(x1, y1, x2, y2);
	}
	@Override
	public void fill(Color color) {
		super.boundingBoxFill(color);
	}
	@Override
	public void draw(Color color, Stroke stroke) {
		super.boundingBoxDraw(color, stroke);
	}
}
