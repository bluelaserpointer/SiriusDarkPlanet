package physics.hitShape;

public interface HasUnionHitShape<T extends HasHitShape> extends HasHitShape {
	@Override
	public abstract UnionHitShape<T> hitShape();
}
