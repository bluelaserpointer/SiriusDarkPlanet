package physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.LinkedList;

import core.GHQ;
import preset.effect.Effect;
import preset.effect.debugEffect.DebugEffect;

public class Route {
	final LinkedList<Point> points;
	final LinkedList<Effect> debugEffects = new LinkedList<Effect>();
	public Route(LinkedList<Point> points) {
		this.points = points;
	}
	public Point removeNext() {
		return points.isEmpty() ? null : points.removeLast();
	}
	public Point peek() {
		return points.isEmpty() ? null : points.peekLast();
	}
	public boolean isEmpty() {
		return points.isEmpty();
	}
	public void clear() {
		points.clear();
	}
	public void setDebugEffect(Color color, Stroke stroke) {
		Point prevPoint = null;
		for(Point nowPoint : points) {
			if(prevPoint == null) {
				debugEffects.add(DebugEffect.setLine(color, stroke, nowPoint.intX(), nowPoint.intY(), nowPoint.intX(), nowPoint.intY() - 10));
			}else {
				debugEffects.add(DebugEffect.setLine(color, stroke, prevPoint, nowPoint));
			}
			prevPoint = nowPoint;
		}
	}
	public void removeDebugEffect() {
		while(!debugEffects.isEmpty())
			debugEffects.remove().claimDeleteFromStage();
	}
	public void debugPaint(Color color, Stroke stroke) {
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(color);
		G2.setStroke(stroke);
		Point prevPoint = null;
		for(Point nowPoint : points) {
			if(prevPoint == null) {
				G2.drawLine(nowPoint.intX(), nowPoint.intY(), nowPoint.intX(), nowPoint.intY() - 10);
			}else {
				G2.drawLine(prevPoint.intX(), prevPoint.intY(), nowPoint.intX(), nowPoint.intY());
			}
			prevPoint = nowPoint;
		}
	}
}
