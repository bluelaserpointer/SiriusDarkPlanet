package physics.stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import physics.FixedGridPoint;
import physics.GridPoint;
import physics.HasBoundingBox;
import physics.HasGridPoint;
import physics.direction.Direction4;

public class GridArrayList<T> extends Grids {
	public class Grid implements HasGridPoint, HasBoundingBox {
		private final FixedGridPoint gridPoint;
		public T data;
		public Grid(int gridX, int gridY, T data) {
			gridPoint = new FixedGridPoint(gridSize);
			gridPoint.setGridXY(gridX, gridY);
			this.data = data;
		}
		public Grid neighbor(Direction4 direction) {
			return neighbor(direction, 1);
		}
		public Grid neighbor(Direction4 direction, int distance) {
			return list.get(gridXYToIndex(gridX() + direction.intEx()*distance, gridY() + direction.intEy()*distance));
		}
		@Override
		public GridPoint gridPoint() {
			return gridPoint;
		}
		@Override
		public int width() {
			return gridSize;
		}
		@Override
		public int height() {
			return gridSize;
		}
	}
	
	final ArrayList<Grid> list;
	public GridArrayList(HasBoundingBox stage, int gridSize) {
		super(stage, gridSize);
		list = new ArrayList<Grid>(gridAmount());
		init((T)null);
	}
	public GridArrayList(HasBoundingBox stage, int gridSize, BiFunction<Integer, Integer, T> initObjSupplier) {
		super(stage, gridSize);
		list = new ArrayList<Grid>(gridAmount());
		init(initObjSupplier);
	}
	
	//control
	public void set_cellPos(int xPos, int yPos, T element) {
		if(validGridXY(xPos, yPos))
			list.get(xPos + yPos*xGrids).data = element;
	}
	public void set_stageCod(int x, int y, T element) {
		set_cellPos(x/gridSize, y/gridSize, element);
	}
	public void init(T t) {
		list.clear();
		for(int yi = 0; yi < yGrids(); ++yi) {
			for(int xi = 0; xi < xGrids(); ++xi) {
				list.add(new Grid(xi, yi, t));
			}
		}
	}
	public void init(BiFunction<Integer, Integer, T> initObjSupplier) {
		list.clear();
		for(int yi = 0; yi < yGrids(); ++yi) {
			for(int xi = 0; xi < xGrids(); ++xi) {
				list.add(new Grid(xi, yi, initObjSupplier.apply(xi, yi)));
			}
		}
	}
	//control
	public void clear() {
		list.clear();
	}
	public void iterateInRect(int startGridX, int startGridY, int endGridX, int endGridY, Consumer<Grid> gridConsumer) {
		for(int yi = startGridY; yi <= endGridY; ++yi) {
			for(int xi = startGridX; xi <= endGridX; ++xi) {
				gridConsumer.accept(list.get(super.gridXYToIndex(xi, yi)));
			}
		}
	}
	
	//information
	public final LinkedList<T> getIntersected(HasBoundingBox object) {
		final LinkedList<T> liquids = new LinkedList<T>();
		final int startX = (object.intX() - object.width()/2)/gridSize;
		final int startY = (object.intY() - object.height()/2)/gridSize;
		final int endX = (object.intX() + object.width()/2)/gridSize;
		final int endY = (object.intY() + object.height()/2)/gridSize;
		for(int posX = startX; posX <= endX; ++posX) {
			for(int posY = startY; posY <= endY; ++posY) {
				final T element = get_cellPos(posX, posY);
				if(element != null)
					liquids.add(get_cellPos(posX, posY));
			}
		}
		return liquids;
	}
	public ArrayList<Grid> list() {
		return list;
	}
	public T get_cellPos(int xPos, int yPos) {
		if(validGridXY(xPos, yPos))
			return list.get(gridXYToIndex(xPos, yPos)).data;
		else
			return null;
	}
	public T get_stageCod(int x, int y) {
		return get_cellPos(x/gridSize, y/gridSize);
	}
}
