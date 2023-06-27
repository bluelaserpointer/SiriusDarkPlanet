package paint.dot;

/**
 * Subclasses of this has a accessible {@link DotPaint} instance.
 * Mainly this interface is implemented by stage objects.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public interface HasDotPaint {
	public static final HasDotPaint NULL_HAS_DOT_PAINT = new HasDotPaint() {
		@Override
		public DotPaint getDotPaint() {
			return DotPaint.BLANK_SCRIPT;
		}
	};
	/**
	 * Return the DotPaint instance of this object.
	 * @return {@link DotPaint}
	 */
	public abstract DotPaint getDotPaint();
}
