package paint.dot;

import core.GHQObject;

public class DotPaintMultiple extends DotPaint {
	private final DotPaint[] SCRIPTS;
	private final int BIGGEST_SIZE, BIGGEST_W, BIGGEST_H;
	public DotPaintMultiple(GHQObject owner, DotPaint...dotPaints) {
		super(owner);
		BIGGEST_SIZE = DotPaint.getMaxSize(SCRIPTS = dotPaints);
		int wBiggest = 0, hBiggest = 0;
		for(DotPaint ver : SCRIPTS) {
			final int W = ver.width(), H = ver.height();
			if(wBiggest < W)
				wBiggest = W;
			if(hBiggest < H)
				hBiggest = H;
		}
		BIGGEST_W = wBiggest;
		BIGGEST_H = hBiggest;
	}
	public DotPaintMultiple(DotPaint...dotPaints) {
		BIGGEST_SIZE = DotPaint.getMaxSize(SCRIPTS = dotPaints);
		int wBiggest = 0, hBiggest = 0;
		for(DotPaint ver : SCRIPTS) {
			final int W = ver.width(), H = ver.height();
			if(wBiggest < W)
				wBiggest = W;
			if(hBiggest < H)
				hBiggest = H;
		}
		BIGGEST_W = wBiggest;
		BIGGEST_H = hBiggest;
	}
	@Override
	public void dotPaint(int x, int y) {
		System.out.println(SCRIPTS.length);
		for(DotPaint ver : SCRIPTS) {
			System.out.println(ver.getClass().getName());
			ver.dotPaint(x, y);
		}
	}
	@Override
	public int maxSide() {
		return BIGGEST_SIZE;
	}
	@Override
	public int width() {
		return BIGGEST_W;
	}
	@Override
	public int height() {
		return BIGGEST_H;
	}
}
