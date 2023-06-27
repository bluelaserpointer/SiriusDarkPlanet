package physics.hitShape;

import physics.HasBoundingBox;
import physics.Point;

/**
 * Subclasses of this has a accessible {@link HitShape} instance.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public interface HasHitShape extends HasBoundingBox {
	public abstract HitShape hitShape();
	@Override
	public default int width() {
		return hitShape().width();
	}
	@Override
	public default int height() {
		return hitShape().height();
	}
	@Override
	public abstract Point point();
}
