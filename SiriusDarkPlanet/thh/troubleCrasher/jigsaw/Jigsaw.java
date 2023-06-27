package troubleCrasher.jigsaw;

import java.awt.Color;
import java.util.function.BiConsumer;

import paint.ColorFilling;
import physics.Point;
import physics.direction.Direction4;
import physics.stage.Grids;

public class Jigsaw {
	public final JigsawEnum type;
	private Direction4 direction = Direction4.D;
	private int jigsawGridX, jigsawGridY;
	
	//init
	public Jigsaw(JigsawEnum type) {
		this.type = type;
	}
	
	public Jigsaw setDirection(Direction4 direction) {
		this.direction = direction;
		return this;
	}
	
	public Jigsaw setGridPos(int gridX, int gridY) {
		this.jigsawGridX = gridX;
		this.jigsawGridY = gridY;
		return this;
	}
	
	//idle
	public void paint(int left, int top) {
		this.iterateTiles_boardOrigin((gridX, gridY) -> type.gridBitSet.paintGrid(new ColorFilling(Color.GRAY), gridX, gridY, left, top));
	}
	
	public void iterateTiles_boardOrigin(BiConsumer<Integer, Integer> consumer) {
		final int xGrids = xGrids(), yGrids = yGrids();
		for(int xi = 0; xi < xGrids; ++xi) {
			for(int yi = 0; yi < yGrids; ++yi) {
				if(hasTile(xi, yi)) {
					consumer.accept(jigsawGridX + xi, jigsawGridY + yi);
				}
			}
		}
	}

	//info
	public Direction4 direction() {
		return direction;
	}
	public boolean hasTile(Point gridPos) {
		return hasTile(gridPos.intX(), gridPos.intY());
	}
	public boolean hasTile(int gridX, int gridY) {
		final int gridYTmp = gridY;
		switch(direction) {
		case A:
			gridX = xGrids() - 1 - gridX;
			gridY = yGrids() - 1 - gridY;
			break;
		case D:
			break;
		case S:
			gridY = xGrids() - 1 - gridX;
			gridX = gridYTmp;
			break;
		case W:
			gridY = gridX;
			gridX = yGrids() - 1 - gridYTmp;
			break;
		}
		return type.gridBitSet.get_cellPos(gridX, gridY, false);
	}
	public Grids gridPainter() {
		return type.gridBitSet;
	}
	public int gridX() {
		return jigsawGridX;
	}
	public int gridY() {
		return jigsawGridY;
	}
	public int xGrids() {
		return direction().isHorz() ? type.gridBitSet.xGrids() : type.gridBitSet.yGrids();
	}
	public int yGrids() {
		return direction().isVert() ? type.gridBitSet.xGrids() : type.gridBitSet.yGrids();
	}
	public int gridSize() {
		return type.gridBitSet.gridSize();
	}
}
