package math;

public class SquareCellArranger extends CellArranger{
	final int MARGIN;
	public int cellXAmount, cellYAmount;
	
	public SquareCellArranger(int margin, int generalW, int generalH, int cellXAmount, int cellYAmount) {
		MARGIN = margin;
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
		return MARGIN;
	}
	public int marginH() {
		return MARGIN;
	}
}