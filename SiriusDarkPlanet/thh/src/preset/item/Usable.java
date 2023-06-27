package preset.item;

import paint.dot.DotPaint;
import paint.dot.HasDotPaint;

public interface Usable extends HasDotPaint {
	public static final Usable NULL_USABLE = new Usable() {
		@Override
		public void use() {}
		@Override
		public DotPaint getDotPaint() {
			return DotPaint.BLANK_SCRIPT;
		}
	};
	public abstract void use();
}
