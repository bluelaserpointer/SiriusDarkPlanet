package paint.line;

/**
 * Subclasses of this has a accessible {@link LinePaint} instance.
 * Mainly this interface is implemented by {@link GUIParts}.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public interface HasLinePaint {
	/**
	 * Return the RectPaint instance of this object.
	 * @return {@link LinePaint}
	 */
	public abstract LinePaint getPaintScript();
}