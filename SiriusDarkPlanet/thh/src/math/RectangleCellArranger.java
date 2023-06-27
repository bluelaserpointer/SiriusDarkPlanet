package math;

public class RectangleCellArranger extends CellArranger{
	final int MARGIN_W, MARGIN_H;
	public int cellXAmount, cellYAmount;
	
	public RectangleCellArranger(int marginW, int marginH, int generalW, int generalH, int cellXAmount, int cellYAmount) {
		MARGIN_W = marginW;
		MARGIN_H = marginH;
		this.generalW = generalW;
		this.generalH = generalH;
		this.cellXAmount = cellXAmount;
		this.cellYAmount = cellYAmount;
	}
	
	public int cellW() {
		return generalW / cellXAmount;
	}
	public int cellH() {
		return generalH / cellYAmount;
	}
	public int marginW() {
		return MARGIN_W;
	}
	public int marginH() {
		return MARGIN_H;
	}
}