package core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

import physics.HasBoundingBox;
import physics.HasPoint;
import physics.HitGroup;
import physics.HitInteractable;
import physics.Point;
import physics.hitShape.HasHitShape;

/**
 * Note: This class does not used for class GUIParts.
 * @author bluelaserpointer
 *
 * @param <T>
 */
public class GHQObjectList<T extends GHQObject> extends LinkedList<T> {
	private static final long serialVersionUID = 1774337313239922412L;
	
	//private final UnionHitShape<T> shape = new UnionHitShape<>(this, this);
	public void traverseIdle() {
		final LinkedList<GHQObject> elements = new LinkedList<GHQObject>();
		elements.addAll(this);
		for(GHQObject element : elements) {
			element.idle();
		}
		//check delete claim
		final Iterator<? extends GHQObject> iterator = descendingIterator();
		while(iterator.hasNext()) {
			GHQObject object = iterator.next();
			if(object.hasDeleteClaimFromStage()) {
				iterator.remove();
			}
		}
	}
	public void traverseIdle(Predicate<GHQObject> includePredicate) {
		//TODO: too long
		final LinkedList<GHQObject> elements = new LinkedList<GHQObject>();
		elements.addAll(this);
		for(GHQObject element : elements) {
			if(includePredicate.test(element))
				element.idle();
		}
		//check delete claim
		final Iterator<? extends GHQObject> iterator = descendingIterator();
		while(iterator.hasNext()) {
			final GHQObject object = iterator.next();
			if(includePredicate.test(object) && object.hasDeleteClaimFromStage()) {
				iterator.remove();
			}
		}
	}
	public void traversePaint() {
		for(GHQObject element : this)
			element.paint();
	}
	public void traversePaint(Predicate<GHQObject> includePredicate) {
		for(GHQObject element : this) {
			if(includePredicate.test(element))
				element.paint();
		}
	}
	public final void defaultTraverse() {
		if(GHQ.isNoStopEvent())
			traverseIdle();
		else
			traversePaint();
	}
	public final void defaultTraverse(Predicate<GHQObject> includePredicate) {
		if(GHQ.isNoStopEvent())
			traverseIdle(includePredicate);
		else
			traversePaint(includePredicate);
	}
	public void forceRemove(T element) {
		remove(element);
	}
	public T forName(String name) {
		for(T element : this) {
			if(element.name().equals(name))
				return element;
		}
		return null;
	}
	public T forMouseOver() {
		if(peek() instanceof HasBoundingBox) {
			for(T element : this) {
				if(((HasBoundingBox)element).isCameraMouseOveredBoundingBox())
					return element;
			}
		}
		return null;
	}
	public T forIntersects(HitInteractable object) {
		for(T element : this) {
			if(element instanceof HitInteractable && ((HitInteractable)element).intersects(object))
				return element;
		}
		return null;
	}
	public T forShapeIntersects(HasHitShape object) {
		for(T element : this) {
			if(element instanceof HasHitShape && ((HasHitShape)element).hitShape().intersects(object))
				return element;
		}
		return null;
	}
	public T forIntersects_atNewPoint(HitInteractable object, double dx, double dy){
		object.point().addXY(dx, dy);
		for(T element : this) {
			if(element instanceof HitInteractable && object.intersects((HitInteractable)element)) {
				object.point().addXY(-dx, -dy);
				return element;
			}
		}
		object.point().addXY(-dx, -dy);
		return null;
	}
	public T forIntersectsDot(HitGroup hitgroup, int x, int y) {
		for(T element : this) {
			if(element instanceof HasHitShape && ((HasHitShape)element).hitShape().boundingBoxIntersectsDot(x, y));
				return element;
		}
		return null;
	}
	public T forShapeIntersectsDot(int x, int y) {
		for(T element : this) {
			if(element instanceof HasHitShape && ((HasHitShape)element).hitShape().intersectsDot(x, y))
				return element;
		}
		return null;
	}
	public T forIntersectsLine(HitGroup hitgroup, int x1, int y1, int x2, int y2) {
		for(T element : this) {
			if(element instanceof HitInteractable && ((HitInteractable)element).intersectsLine(hitgroup, x1, y1, x2, y2))
				return element;
		}
		return null;
	}
	public T forIntersectsLine(int x1, int y1, int x2, int y2) {
		return forIntersectsLine(HitGroup.HIT_ALL, x1, y1, x2, y2);
	}
	public T forIntersectsRect(HitGroup hitgroup, int x, int y, int w, int h) {
		for(T element : this) {
			if(element instanceof HitInteractable && ((HitInteractable)element).intersectsRect(hitgroup, x, y, w, h))
				return element;
		}
		return null;
	}
	public T forIntersectsRect(int x, int y, int w, int h) {
		return forIntersectsRect(HitGroup.HIT_ALL, x, y, w, h);
	}
	public boolean intersected(HitInteractable object) {
		return forIntersects(object) != null;
	}
	public boolean intersected_atNewPoint(HitInteractable object, double dx, double dy) {
		return forIntersects_atNewPoint(object, dx, dy) != null;
	}
	public boolean shapeIntersected_dot(int x, int y) {
		return forShapeIntersectsDot(x, y) != null;
	}
	public boolean shapeIntersected_dot(Point point) {
		return shapeIntersected_dot(point.intX(), point.intY());
	}
	public boolean shapeIntersected_dot(HasPoint hasPoint) {
		return shapeIntersected_dot(hasPoint.point());
	}
	public boolean intersected_line(int x1, int y1, int x2, int y2) {
		return forIntersectsLine(x1, y1, x2, y2) != null;
	}
	public boolean intersected_line(Point p1, Point p2) {
		return intersected_line(p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	public boolean intersected_line(HasPoint p1, HasPoint p2) {
		return intersected_line(p1.point(), p2.point());
	}
	public T getClosest(Point point) {
		T closestElement = null;
		double closestDistance = Double.MAX_VALUE;
		for(T element : this) {
			final double distance = point.distance(element);
			if(distance < closestDistance) {
				closestDistance = distance;
				closestElement = element;
			}
		}
		return closestElement;
	}
	public T getClosestVisible(Point point) {
		T closestElement = null;
		double closestDistance = Double.MAX_VALUE;
		for(T element : this) {
			final double distance = point.distance(element);
			if(distance < closestDistance && point.isVisible(element)) {
				closestDistance = distance;
				closestElement = element;
			}
		}
		return closestElement;
	}
	public T getClosest(T searcher) {
		T closestElement = null;
		double closestDistance = Double.MAX_VALUE;
		for(T element : this) {
			if(element == searcher)
				continue;
			final double distance = searcher.point().distance(element);
			if(distance < closestDistance) {
				closestDistance = distance;
				closestElement = element;
			}
		}
		return closestElement;
	}
	public T getClosestVisible(T searcher) {
		T closestElement = null;
		double closestDistance = Double.MAX_VALUE;
		for(T element : this) {
			if(element == searcher)
				continue;
			final double distance = searcher.point().distance(element);
			if(distance < closestDistance && searcher.point().isVisible(element)) {
				closestDistance = distance;
				closestElement = element;
			}
		}
		return closestElement;
	}
}
