package storage;

import java.util.Collection;

/**
 * A subclass of StorageWithSpace.
 * Possible to define cell rows/lines, which helps TableStorageViewer to display its contents. 
 * @author bluelaserpointer
 * @since alpha1.0
 * @param <T> the type of elements in this list
 */
public class TableStorage<T> extends StorageWithSpace<T> {
	private static final long serialVersionUID = -823825080682852987L;
	protected int xCells;
	
	public TableStorage(int w, int h, T nullElement) {
		super(w*h, nullElement);
		xCells = w;
	}
	public TableStorage(Collection<T> initialElements, int wCells, T nullElement) {
		super(initialElements.size(), nullElement);
		xCells = wCells;
		int i = -1;
		for(T ver : initialElements)
			super.set(++i, ver);
	}
	public TableStorage(T[] initialElements, int w, T nullElement) {
		super(initialElements.length, nullElement);
		xCells = w;
		int i = -1;
		for(T ver : initialElements)
			super.set(++i, ver);
	}
	
	/////////
	//init
	/////////
	public TableStorage<T> expand(int xCells, int yCells) {
		this.xCells += xCells;
		super.expand(yCells()*xCells + this.xCells*yCells);
		return this;
	}
	public TableStorage<T> setXCells(int xCells) {
		this.xCells = xCells;
		return this;
	}
	public TableStorage<T> setYCells(int yCells) {
		super.expand(xCells*yCells - super.size());
		return this;
	}
	public TableStorage<T> setXYCells(int xCells, int yCells) {
		setXCells(xCells);
		setYCells(yCells);
		return this;
	}
	//TODO: TableStorage's shrink(int xCells, int yCells)
	
	/////////
	//information
	/////////
	public int xCells() {
		return xCells;
	}
	public int yCells() {
		final int size = size();
		return size == 0 ? 0 : (size - 1)/xCells() + 1;
	}
	public boolean isValidIndex(int xPos, int yPos) {
		return 0 <= xPos && xPos < xCells() && 0 <= yPos && yPos < yCells();
	}
	public int cellIndex(int posX, int posY) {
		return isValidIndex(posX, posY) ? (posX + posY*xCells()) : -1;
	}
	public T cellElement(int posX, int posY) {
		return isValidIndex(posX, posY) ? super.get(posX + posY*xCells()) : null;
	}
}
