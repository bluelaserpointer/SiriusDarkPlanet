package physics.stage;

import java.awt.Graphics2D;

import calculate.HasGrid;
import core.GHQ;
import paint.rect.RectPaint;
import physics.HasBoundingBox;
import physics.Point;

public class Grids implements HasGrid {
	protected final int xGrids, yGrids;
	protected final int gridSize;
	
	public Grids(HasBoundingBox stage, int gridSize) {
		this.gridSize = gridSize;
		xGrids = stage.width()/gridSize + 1;
		yGrids = stage.height()/gridSize + 1;
	}
	public Grids(int xGrids, int yGrids, int gridSize) {
		this.gridSize = gridSize;
		this.xGrids = xGrids;
		this.yGrids = yGrids;
	}
	//control
	public void drawGrid(Graphics2D g2, int xPos, int yPos) {
		g2.drawRect(xPos*gridSize(), yPos*gridSize(), gridSize(), gridSize());
	}
	public void fillGrid(Graphics2D g2, int xPos, int yPos) {
		g2.fillRect(xPos*gridSize(), yPos*gridSize(), gridSize(), gridSize());
	}
	public void paintGrid(RectPaint rectPaint, int xPos, int yPos) {
		rectPaint.rectPaint(xPos*gridSize(), yPos*gridSize(), gridSize(), gridSize());
	}
	public void drawGrid(Graphics2D g2, int xPos, int yPos, int left, int top) {
		g2.drawRect(left + xPos*gridSize(), top + yPos*gridSize(), gridSize(), gridSize());
	}
	public void fillGrid(Graphics2D g2, int xPos, int yPos, int left, int top) {
		g2.fillRect(left + xPos*gridSize(), top + yPos*gridSize(), gridSize(), gridSize());
	}
	public void paintGrid(RectPaint rectPaint, int xPos, int yPos, int left, int top) {
		rectPaint.rectPaint(left + xPos*gridSize(), top + yPos*gridSize(), gridSize(), gridSize());
	}
	public void drawGrid_fieldCod(Graphics2D g2, int mouseX, int mouseY) {
		drawGrid(g2, mouseX/gridSize(), mouseY/gridSize());
	}
	//information
	public boolean validGridXY(int gridX, int gridY) {
		return 0 <= gridX && gridX < xGrids() && 0 <= gridY && gridY < yGrids();
	}
	public int gridXYToIndex(int gridX, int gridY) {
		return gridX + gridY*xGrids();
	}
	public int indexToGridX(int index) {
		return index % xGrids();
	}
	public int indexToGridY(int index) {
		return index / xGrids();
	}
	public Point getPosPoint(int xPos, int yPos) {
		return new Point.DoublePoint(xPos*gridSize(), yPos*gridSize());
	}
	public int screenLeftXPos() {
		final int screenLeft = GHQ.fieldScreenLeft();
		return screenLeft < 0 ? screenLeft/gridSize() - 1 : screenLeft/gridSize();
	}
	public int screenTopYPos() {
		final int screenTop = GHQ.fieldScreenTop();
		return screenTop < 0 ? screenTop/gridSize() - 1 : screenTop/gridSize();
	}
	public int gridsToFillScreenWidth() {
		return GHQ.fieldScreenW()/gridSize() + 2;
	}
	public int gridsToFillScreenHeight() {
		return GHQ.fieldScreenH()/gridSize() + 2;
	}
	@Override
	public int gridSize() {
		return gridSize;
	}
	@Override
	public int xGrids() {
		return xGrids;
	}
	@Override
	public int yGrids() {
		return yGrids;
	}
}
