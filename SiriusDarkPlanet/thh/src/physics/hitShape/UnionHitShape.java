package physics.hitShape;

import java.awt.Color;
import java.awt.Stroke;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import physics.HasPoint;
import physics.Point;

public class UnionHitShape<T extends HasHitShape> extends HitShape {
	private static final long serialVersionUID = 1231284737748383681L;
	
	protected List<T> objects;
	protected int areaWidth = Integer.MAX_VALUE, areaHeight = Integer.MAX_VALUE;
	public UnionHitShape(List<T> objects) {
		this.objects = objects;
		setOwner(HasPoint.generate(0, 0));
	}
	public UnionHitShape(List<T> objects, HasPoint owner) {
		this.objects = objects;
		setOwner(owner);
	}
	public UnionHitShape(List<T> objects, HasPoint owner, int areaWidth, int areaHeight) {
		this.objects = objects;
		setOwner(owner);
		this.areaWidth = areaWidth;
		this.areaHeight = areaHeight;
	}
	//init
	public UnionHitShape<T> setObjects(List<T> objects) {
		this.objects = objects;
		return this;
	}
	
	//information;
	@Override
	public int width() {
		return areaWidth;
	}
	@Override
	public int height() {
		return areaHeight;
	}
	@Override
	public HitShape clone(HasPoint newOwner) {
		return new UnionHitShape<T>(objects, newOwner, areaWidth, areaHeight);
	}
	/////////////
	//lambda for-each
	/////////////
	public List<T> find(Function<T, Boolean> function, int amount) {
		final LinkedList<T> list = new LinkedList<>();
		if(amount <= 0)
			return list;
		for(T object : objects) {
			if(function.apply(object)) {
				list.add(object);
				if(--amount == 0)
					return list;
			}
		}
		return list;
	}
	public List<T> find(Function<T, Boolean> function) {
		final LinkedList<T> list = new LinkedList<>();
		for(T object : objects) {
			if(function.apply(object))
				list.add(object);
		}
		return list;
	}
	public <E> T findBest(Function<T, E> compareMaterialGetter, Comparator<E> comparator) {
		E best = null;
		T bestOwner = null;
		for(T object : objects) {
			final E var = compareMaterialGetter.apply(object);
			if(best == null || comparator.compare(var, best) > 0) {
				best = var;
				bestOwner = object;
			}
		}
		return bestOwner;
	}
	/////////////
	//HitShape intersects
	/////////////
	public T elementIntersects(HitShape shape) {
		for(T object : objects) {
			if(object.hitShape().intersects(shape)) {
				return object;
			}
		}
		return null;
	}
	public List<T> elementsIntersect(HitShape shape) {
		return find((target) -> target.hitShape().intersects(shape));
	}
	public List<T> elementsIntersect(HitShape shape, int amount) {
		return find((target) -> target.hitShape().intersects(shape), amount);
	}
	/////////////
	//Dot Intersects
	/////////////
	public T elementIntersectsDot(int x, int y) {
		for(T object : objects) {
			if(object.hitShape().intersectsDot(x, y))
				return object;
		}
		return null;
	}
	public T elementIntersectsDot(Point point) {
		return elementIntersectsDot(point.intX(), point.intY());
	}
	public T elementIntersectsDot(HasPoint hasPoint) {
		return elementIntersectsDot(hasPoint.point());
	}
	public List<T> elementsIntersectDot(int x, int y) {
		return find((target) -> target.hitShape().intersectsDot(x, y));
	}
	public List<T> elementsIntersectDot(Point point) {
		return elementsIntersectDot(point.intX(), point.intY());
	}
	public List<T> elementsIntersectDot(HasPoint hasPoint) {
		return elementsIntersectDot(hasPoint.point());
	}
	public List<T> elementsIntersectDot(int x, int y, int amount) {
		return find((target) -> target.hitShape().intersectsDot(x, y), amount);
	}
	public List<T> elementsIntersectDot(Point point, int amount) {
		return elementsIntersectDot(point.intX(), point.intY(), amount);
	}
	public List<T> elementsIntersectDot(HasPoint hasPoint, int amount) {
		return elementsIntersectDot(hasPoint.point(), amount);
	}
	/////////////
	//Line Intersects
	/////////////
	public T elementIntersectsLine(int x1, int y1, int x2, int y2) {
		for(T object : objects) {
			if(object.hitShape().intersectsLine(x1, y1, x2, y2))
				return object;
		}
		return null;
	}
	public List<T> elementsIntersectLine(int x1, int y1, int x2, int y2) {
		return find((target) -> target.hitShape().intersectsLine(x1, y1, x2, y2));
	}
	public List<T> elementsIntersectLine(int x1, int y1, int x2, int y2, int amount) {
		return find((target) -> target.hitShape().intersectsLine(x1, y1, x2, y2), amount);
	}
	@Override
	public int preciseIntersects(HitShape shape) {
		return elementIntersects(shape) != null ? 1 : 0;
	}
	@Override
	public boolean intersectsDot(int x, int y) {
		return elementIntersectsDot(x, y) != null;
	}
	@Override
	public boolean intersectsLine(int x1, int y1, int x2, int y2) {
		return elementIntersectsLine(x1, y1, x2, y2) != null;
	}
	@Override
	public void fill(Color color) {
		for(T object : objects) {
			object.hitShape().fill(color);
		}
	}
	@Override
	public void draw(Color color, Stroke stroke) {
		for(T object : objects) {
			object.hitShape().draw(color, stroke);
		}
	}

}
