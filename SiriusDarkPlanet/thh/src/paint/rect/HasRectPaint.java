package paint.rect;

/**
 * Subclasses of this has a accessible {@link RectPaint} instance.
 * Mainly this interface is implemented by {@link GUIParts}.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public interface HasRectPaint {
	/**
	 * Return the RectPaint instance of this object.
	 * @return {@link RectPaint}
	 */
	public abstract RectPaint getRectPaint();
}
