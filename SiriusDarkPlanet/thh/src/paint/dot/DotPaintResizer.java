package paint.dot;

public class DotPaintResizer extends DotPaint {
	final DotPaint basePaint;
	final double sizeRate;
	public DotPaintResizer(DotPaint basePaint, double sizeRate) {
		this.sizeRate = sizeRate;
		this.basePaint = basePaint;
	}
	@Override
	public void dotPaint(int x, int y) {
		basePaint.dotPaint_rate(x, y, sizeRate);
	}
	@Override
	public int width() {
		return (int)(basePaint.width()*sizeRate);
	}
	@Override
	public int height() {
		return (int)(basePaint.height()*sizeRate);
	}
}
