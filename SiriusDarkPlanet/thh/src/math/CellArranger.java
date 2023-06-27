package math;

public abstract class CellArranger {
	protected int generalW, generalH;
	public static class Cell {
		public final int X, Y, W, H;
		protected Cell(int x, int y, int w, int h) {
			X = x;
			Y = y;
			W = w;
			H = h;
		}
		public final int centerX() {
			return X + W/2;
		}
		public final int centerY() {
			return Y + H/2;
		}
	}
	public int width() {
		return generalW;
	}
	public int height() {
		return generalH;
	}
	public abstract int cellW();
	public abstract int cellH();
	public abstract int marginW();
	public abstract int marginH();
	public Cell getBasicCell(int xPos, int yPos) {
		final int CELL_W = cellW(), CELL_H = cellH();
		return new Cell(xPos*CELL_W + marginW(), yPos*CELL_H + marginH(), CELL_W - marginW()*2, CELL_H - marginH()*2);
	}
	public Cell getBigCell(int xPos, int yPos, int wCells, int hCells) {
		final int CELL_W = cellW(), CELL_H = cellH();
		return new Cell(xPos*CELL_W + marginW(), yPos*CELL_H + marginH(), CELL_W*wCells - marginW()*2, CELL_H*hCells - marginH()*2);
	}
	public Cell getFreeCell(int x, int y, int w, int h) {
		return new Cell(x, y, w, h);
	}
}
