package physics;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import core.GHQ;
import physics.hitShape.HasArea;

public interface HasBoundingBox extends HasPoint, HasArea {
	public default int cx() {
		return point().intX();
	}
	public default int cy() { 
		return point().intY();
	}
	public default int left() {
		return cx() - width()/2;
	}
	public default int top() {
		return cy() - height()/2;
	}
	public default int right() {
		return cx() + width()/2;
	}
	public default int bottom() {
		return cy() + height()/2;
	}
	public default boolean boundingBoxIntersectsDot(int x, int y) {
		return left() <= x && x <= right() && top() <= y && y <= bottom();
	}
	public default boolean boundingBoxIntersectsDot(Point point) {
		return boundingBoxIntersectsDot(point.intX(), point.intY());
	}
	public default boolean isCameraMouseOveredBoundingBox() {
		return boundingBoxIntersectsDot(GHQ.mouseX(), GHQ.mouseY());
	}
	public default boolean isScreenMouseOveredBoundingBox() {
		return boundingBoxIntersectsDot(GHQ.mouseScreenX(), GHQ.mouseScreenY());
	}
	public default boolean isCameraMouseOvered() {
		return boundingBoxIntersectsDot(GHQ.mouseX(), GHQ.mouseY());
	}
	public default boolean isScreenMouseOvered() {
		return boundingBoxIntersectsDot(GHQ.mouseScreenX(), GHQ.mouseScreenY());
	}
	public default boolean boundingBoxIntersectsLine(int x1, int y1, int x2, int y2) {
		return new Rectangle2D.Double(left(), top(), width(), height()).intersectsLine(x1, y1, x2, y2);
	}
	public default boolean boundingBoxIntersectsLine(Point p1, Point p2) {
		return boundingBoxIntersectsLine(p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	public default boolean boundingBoxIntersects(HasBoundingBox target) {
		return Math.abs(cx() - target.cx()) < (width() + target.width())/2
				&& Math.abs(cy() - target.cy()) < (height() + target.height())/2;
	}
	public default void drawBoundingBox(Color color, Stroke stroke) {
		GHQ.getG2D(color, stroke).drawRect(left(), top(), width(), height());
	}
	public default void fillBoundingBox(Color color) {
		GHQ.getG2D(color).fillRect(left(), top(), width(), height());
	}
	public default void drawBiggerBoundingBox(Color color, Stroke stroke, int sizeAdd) {
		GHQ.getG2D(color, stroke).drawRect(left() - sizeAdd/2, top() - sizeAdd/2, width() + sizeAdd, height() + sizeAdd);
	}
	public default void fillBiggerBoundingBox(Color color, Stroke stroke, int sizeAdd) {
		GHQ.getG2D(color, stroke).fillRect(left() - sizeAdd/2, top() - sizeAdd/2, width() + sizeAdd, height() + sizeAdd);
	}
	public default void boundingBoxFill(Color color) {
		GHQ.getG2D(color).fillRect(left(), top(), width(), height());
	}
	public default void boundingBoxDraw(Color color, Stroke stroke) {
		GHQ.getG2D(color).drawRect(left(), top(), width(), height());
	}
}