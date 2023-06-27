package physics.hitShape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import core.GHQ;
import physics.HasPoint;

public class Circle extends HitShape {
	private static final long serialVersionUID = -1809578801160258098L;
	public int radius;
	
	public Circle(HasPoint owner, int radius) {
		super(owner);
		this.radius = radius;
	}
	//init
	public Circle setRadius(int radius) {
		this.radius = radius;
		return this;
	}
	@Override
	public int preciseIntersects(HitShape shape) {
		if(shape instanceof Circle) {
			final double DR = radius + ((Circle)shape).radius;
			return point().distanceSq(shape.point()) < DR*DR ? 1 : 0;
		}
		return -1;
	}
	@Override
	public boolean intersectsDot(int x, int y) {
		return point().inRange(x, y, radius);
	}
	@Override
	public boolean intersectsLine(int x1, int y1, int x2, int y2) {
		return Line2D.ptLineDistSq(x1, y1, x2, y2, point().intX(), point().intY()) < radius*radius;
	}
	@Override
	public void fill(Color color) {
		final Graphics2D G2 = GHQ.getG2D();
		final int TX = point().intX(), TY = point().intY();
		G2.translate(TX, TY);
		G2.setColor(color);
		G2.fillOval(-radius/2, -radius/2, radius, radius);
		G2.translate(-TX, -TY);
	}
	@Override
	public void draw(Color color, Stroke stroke) {
		final Graphics2D G2 = GHQ.getG2D();
		final int TX = point().intX(), TY = point().intY();
		G2.translate(TX, TY);
		G2.setColor(color);
		G2.setStroke(stroke);
		G2.drawOval(-radius/2, -radius/2, radius, radius);
		G2.translate(-TX, -TY);
	}
	
	//tool
	@Override
	public HitShape clone(HasPoint newOwner) {
		return new Circle(newOwner, radius);
	}
	
	//information
	public int radius() {
		return radius;
	}
	@Override
	public int width() {
		return radius*2;
	}
	@Override
	public int height() {
		return radius*2;
	}
}
