package paint.dot;

public abstract class DotPaintDeriver extends DotPaint {
	final DotPaint basePaint;
	public DotPaintDeriver(DotPaint basePaint) {
		this.basePaint = basePaint;
	}
	@Override
	public void dotPaint(int x, int y) {
		bottomPaint(x, y);
		basePaint.dotPaint(x, y);
		upperPaint(x, y);
	}
	protected abstract void bottomPaint(int x, int y);
	protected abstract void upperPaint(int x, int y);
	@Override
	public int width() {
		return basePaint.width();
	}
	@Override
	public int height() {
		return basePaint.height();
	}

}
