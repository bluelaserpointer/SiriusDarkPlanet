package gui;

public class AutoResizeMenu extends GUIParts {
	private final int DEFAULT_LINE_H;
	private final int MARGIN;
	private int lineAmount;
	public AutoResizeMenu(int x, int y, int w, int defaultLineH) {
		super.setBounds(x, y, w, 1);
		DEFAULT_LINE_H = defaultLineH;
		MARGIN = 0;
	}
	public AutoResizeMenu(int x, int y, int w, int defaultLineH, int partsMargin) {
		super.setBounds(x, y, w, 1);
		DEFAULT_LINE_H = defaultLineH;
		MARGIN = partsMargin;
	}
	public AutoResizeMenu(int w, int defaultLineH) {
		super.setBounds(0, 0, w, 1);
		DEFAULT_LINE_H = defaultLineH;
		MARGIN = 0;
	}
	public AutoResizeMenu(int w, int defaultLineH, int partsMargin) {
		super.setBounds(0, 0, w, 1);
		DEFAULT_LINE_H = defaultLineH;
		MARGIN = partsMargin;
	}
	public AutoResizeMenu addNewLine(GUIParts...guiParts) {
		if(guiParts.length > 0) {
			if(MARGIN == 0) {
				final int AMOUNT = guiParts.length;
				final int PARTS_W = width() / AMOUNT;
				for(int i = 0;i < guiParts.length;i++) {
					guiParts[i].physics().setPointBase(this);
					guiParts[i].setBounds(PARTS_W*i, point().intY() + DEFAULT_LINE_H*lineAmount, PARTS_W, DEFAULT_LINE_H);
					super.addLast(guiParts[i]);
				}
			}else {
				final int AMOUNT = guiParts.length;
				final int PARTS_W = width() / AMOUNT;
				for(int i = 0;i < guiParts.length;i++) {
					guiParts[i].physics().setPointBase(this);
					guiParts[i].setBounds(PARTS_W*i + MARGIN, point().intY() + DEFAULT_LINE_H*lineAmount + MARGIN, PARTS_W - MARGIN*2, DEFAULT_LINE_H - MARGIN*2);
					super.addLast(guiParts[i]);
				}
			}
		}
		lineAmount++;
		rectShape().setHeight(height() + DEFAULT_LINE_H);
		return this;
	}
	public AutoResizeMenu addEmptyLine() {
		lineAmount++;
		rectShape().setHeight(height() + DEFAULT_LINE_H);
		return this;
	}
	public <T extends GUIParts>T remove(T childParts) {
		super.remove(childParts);
		--lineAmount;
		rectShape().setHeight(height() - DEFAULT_LINE_H);
		return childParts;
	}
	public void removeAll() {
		super.removeAll();
		lineAmount = 0;
		rectShape().setHeight(1);
	}
}
