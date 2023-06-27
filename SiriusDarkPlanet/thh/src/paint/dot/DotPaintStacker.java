package paint.dot;

import java.util.ArrayList;

import core.GHQObject;

public class DotPaintStacker extends DotPaint {
	private final ArrayList<DotPaint> SCRIPTS = new ArrayList<DotPaint>();
	private final DotPaint[] LAYER_PAINTS;
	private final int BIGGEST_SIZE, BIGGEST_W, BIGGEST_H;
	
	//init
	public DotPaintStacker(GHQObject owner, DotPaint...layerPaints) {
		super(owner);
		LAYER_PAINTS = layerPaints;
		BIGGEST_SIZE = DotPaint.getMaxSize(SCRIPTS);
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
	//main role
	@Override
	public void dotPaint(int x, int y) {
		for(DotPaint script : SCRIPTS)
			script.dotPaint(x, y);
	}
	
	//control
	public <T extends DotPaint>T addScript(T dotPaint){
		SCRIPTS.add(dotPaint);
		return dotPaint;
	}
	public boolean removeScript(DotPaint dotPaint){
		return SCRIPTS.remove(dotPaint);
	}
	public void clear() {
		SCRIPTS.clear();
	}
	
	//information
	public int getScriptsAmount() {
		return SCRIPTS.size();
	}
	public int getLayerAmount() {
		return LAYER_PAINTS.length;
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
