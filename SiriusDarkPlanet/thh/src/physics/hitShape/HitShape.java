package physics.hitShape;

import java.awt.Color;
import java.awt.Stroke;
import java.io.Serializable;

import physics.HasBoundingBox;
import physics.HasPoint;
import physics.HitInteractable;
import physics.Point;

/**
 * A primal class for managing object hit area.Need {@link Point} to create this instance.<br>
 * The {@link HitShape#intersects(DoublePoint, HasHitShape, DoublePoint)} method provide geometry hit judge,
 * while {@link HitInteractable#intersects(HitInteractable)} can perform grouped hit judge.
 * 
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class HitShape implements HasBoundingBox, Serializable {
	private static final long serialVersionUID = -4544439590281786292L;
	public static final HitShape NULL_HITSHAPE = new HitShape() {
		private static final long serialVersionUID = -4252823940204998087L;
		@Override
		public int width() {
			return 0;
		}
		@Override
		public int height() {
			return 0;
		}
		@Override
		public boolean boundingBoxIntersectsDot(int x, int y) {
			return false;
		}
		@Override
		public boolean intersectsDot(int x, int y) {
			return false;
		}
		@Override
		public boolean intersectsLine(int x1, int y1, int x2, int y2) {
			return false;
		}
		@Override
		public HitShape clone(HasPoint newOwner) {
			return this;
		}
		@Override
		public void fill(Color color) {}
		@Override
		public void draw(Color color, Stroke stroke) {}
		@Override
		public int preciseIntersects(HitShape shape) {
			return -1;
		}
	};

	protected HasPoint owner;
	public HitShape() {
		owner = null; //if no owner has set and intersect judges are executed, NullPointerException will be thrown.
	}
	public HitShape(HasPoint owner) {
		this.owner = owner;
	}
	
	//control
	public HitShape setOwner(HasPoint owner) {
		this.owner = owner;
		return this;
	}
	
	//information
	public final boolean intersects(HitShape shape) {
		switch(this.preciseIntersects(shape)) {
		case 0:
			return false;
		case 1:
			return true;
		default:
			switch(shape.preciseIntersects(this)) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				return boundingBoxIntersects(shape);
			}
		}
	}
	/**
	 * Provide more precise intersection judge than boundingBoxIntersects if it is defined.
	 * @param shape
	 * @return 1: hit, 0: not hit, -1: undefined(do boundingBoxIntersects)
	 */
	public abstract int preciseIntersects(HitShape shape);
	public final boolean intersects(HasHitShape target) {
		final HitShape SHAPE = target.hitShape();
		return SHAPE == null ? false : intersects(SHAPE);
	}
	public final boolean intersects_atNewPoint(double dx, double dy, HitShape shape) {
		point().addXY(dx, dy);
		final boolean JUDGE = intersects(shape);
		point().addXY(-dx, -dy);
		return JUDGE;
	}
	public final boolean intersects_atNewPoint(double dx, double dy, HasHitShape target) {
		final HitShape SHAPE = target.hitShape();
		return SHAPE == null ? false : intersects_atNewPoint(dx, dy, SHAPE);
	}
	public abstract boolean intersectsDot(int x, int y);
	public abstract boolean intersectsLine(int x1, int y1, int x2, int y2);
	public final boolean intersectsLine(Point p1, Point p2) {
		return intersectsLine(p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	
	//tool
	public abstract HitShape clone(HasPoint newOwner);
	public abstract void fill(Color color);
	public abstract void draw(Color color, Stroke stroke);
	
	//information
	@Override
	public Point point() {
		return owner.point();
	}
	public HasPoint owner() {
		return owner;
	}
	public boolean hasOwner() {
		return owner() != null;
	}
}
