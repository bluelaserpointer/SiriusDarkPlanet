package gui;

import java.awt.event.MouseEvent;
import java.util.List;

import core.GHQ;
import paint.ImageFrame;
import paint.dot.HasDotPaint;
import paint.rect.RectPaint;
import storage.StorageWithSpace;
import storage.TableStorage;

/**
 * A GUIParts subclass for managing Excel-like object display.The Objects to display needs implements {@link HasDotPaint}.<br>
 * It can link to a already exist {@link StorageWithSpace} and display its contents.
 * @author bluelaserpointer
 * @since alpha1.0
 * @param <T> the type of elements in the storage
 */
public abstract class TableStorageViewer<T extends HasDotPaint> extends GUIParts {
	/**
	 * The data of this TableStorageViewer.
	 */
	public List<T> storage;
	public final Class<T> receptingClass;
	protected int cellSize = 50;
	protected RectPaint cellPaint = RectPaint.BLANK_SCRIPT;
	public static final int WRAP_CONTENTS = -1;
	private int xCells = WRAP_CONTENTS, yCells = WRAP_CONTENTS;
	
	public TableStorageViewer(Class<T> receptingClass) {
		this.receptingClass = receptingClass;
	}
	//init
	public TableStorageViewer<T> setStorage(List<T> storage) {
		this.storage = storage;
		updateBoundingBox();
		return this;
	}
	public TableStorageViewer<T> setCellSize(int cellSize) {
		this.cellSize = cellSize;
		updateBoundingBox();
		return this;
	}
	public void updateBoundingBox() {
		if(storage != null) {
			super.setBoundsSize(xCells()*cellSize, yCells()*cellSize);
		}
	}
	//main role
	@Override
	public void paint() {
		super.paint();
		//paint
		final int storageW = xCells();
		final int storageH = yCells();
		for(int xi = 0; xi < storageW; ++xi) {
			for(int yi = 0; yi < storageH; ++yi) {
				final int index = posToIndex(xi, yi);
				paintOfCell(index, isValidIndex(index) ? storage.get(index) : null, point().intX() + xi*cellSize, point().intY() + yi*cellSize);
			}
		}
	}
	
	//extends
	/**
	 * Describes paint of a cells.Override this to quickly change cells appearance.
	 * @param object - the content in this cell({@link HasDotPaint})
	 * @param x - the x coordinate of the upper-left corner of this cell
	 * @param y - the y coordinate of the upper-left corner of this cell
	 */
	protected void paintOfCell(int index, T object, int x, int y) {
		cellPaint.rectPaint(x, y, cellSize);
		if(object != null)
			object.getDotPaint().dotPaint_capSize(x + cellSize/2, y + cellSize/2, (int)(cellSize*0.8));
	}
	
	//control
	public TableStorageViewer<T> setCellPaint(RectPaint paintScript) {
		cellPaint = paintScript;
		return this;
	}
	public TableStorageViewer<T> setCellImage(String imageURL) {
		return setCellPaint(ImageFrame.create(imageURL));
	}
	public TableStorageViewer<T> setXCells(int xCells) {
		this.xCells = xCells;
		updateBoundingBox();
		return this;
	}
	public TableStorageViewer<T> setYCells(int yCells) {
		this.yCells = yCells;
		updateBoundingBox();
		return this;
	}
	public TableStorageViewer<T> setXYCells(int xCells, int yCells) {
		setXCells(xCells);
		return setYCells(yCells);
	}
	public TableStorageViewer<T> setXYCells_YWrapContents(int xCells) {
		return setXYCells(xCells, WRAP_CONTENTS);
	}
	
	//event
	@Override
	public boolean clicked(MouseEvent e) {
		super.clicked(e);
		final T element = getMouseHoveredElement();
		if(GHQ.mouseHook.isEmpty() && !isSpaceElement(element)) {
			GHQ.mouseHook.hook(element, this);
		}
		return true;
	}
	@Override
	public boolean checkDragIn(GUIParts sourceUI, Object dropObject) {
		//only check if the position is legal
		final int id = getMouseHoveredIndex();
		if(isValidIndex(id) && receptingClass.isInstance(dropObject)) {
			final T overWrittenObject = storage.get(id);
			//when swap, only accept if the original element is accepted by the source UI.
			return isSpaceElement(overWrittenObject) || sourceUI instanceof TableStorageViewer || sourceUI.checkDragIn(this, overWrittenObject);
		}
		return false;
	}
	@Override
	public boolean checkDragOut(GUIParts targetUI, Object dropObject) {
		//check nothing
		return true;
	}
	@Override
	public void dragIn(GUIParts sourceUI, Object dropObject) {
		final int id = getMouseHoveredIndex();
		if(storage instanceof StorageWithSpace || (dropObject != null && storage.size() > id)) {
			storage.set(id, receptingClass.cast(dropObject));
		} else {
			if(dropObject == null) { //delete
				if(storage.size() > id) { 	//remove
					storage.remove(id);
				}							//ignore out of bounds deletion
			} else { //add
				storage.add(receptingClass.cast(dropObject));
			}
		}
	}
	@Override
	public Object peekDragObject() {
		final T originalObj = storage.get(getMouseHoveredIndex());
		if(storage instanceof StorageWithSpace && ((StorageWithSpace<T>)storage).isSpaceElement(originalObj))
			return null; //automatically convert space element to null when it is going outside
		else
			return originalObj;
	}
	@Override
	public void dragOut(GUIParts targetUI, Object dropObject, Object swapObject) {
		final int id = storage.indexOf(dropObject);
		if(swapObject == null) //move
			storage.remove(id);
		else //swap
			storage.set(id, receptingClass.cast(swapObject));
	}
	
	//information
	public int xCells() {
		if(xCells == WRAP_CONTENTS) {
			if(storage instanceof TableStorage)
				return ((TableStorage<T>)storage).xCells();
			else
				return storage.size();
		}
		return xCells;
	}
	public int yCells() {
		if(yCells == WRAP_CONTENTS) {
			if(storage instanceof TableStorage)
				return ((TableStorage<T>)storage).yCells();
			else if(xCells == WRAP_CONTENTS)
				return 1;
			else
				return storage.size()/xCells + 1;
		}
		return yCells;
	}
	/**
	 * Get　the index of the cell which mouse is hovering.
	 * @return index of the hovered cell, or -1 if mouse is not hovering any cell.
	 */
	public int getMouseHoveredIndex() {
		return xyToIndex(GHQ.mouseScreenX(), GHQ.mouseScreenY());
	}
	public int xyToIndex(int x, int y) {
		final int posX = (x - point().intX())/cellSize, posY = (y - point().intY())/cellSize;
		return posToIndex(posX, posY);
	}
	public int posToIndex(int posX, int posY) {
		return isValidPos(posX, posY) ? (posX + posY*xCells()) : -1;
	}
	public boolean isValidPos(int xPos, int yPos) {
		return 0 <= xPos && xPos < xCells() && 0 <= yPos && yPos < yCells();
	}
	public boolean isValidIndex(int index) {
		return 0 <= index && index < storage.size();
	}
	public boolean isSpaceElement(T element) {
		return element == null || (storage instanceof StorageWithSpace && ((StorageWithSpace<T>)storage).isSpaceElement(element));
	}
	/**
	 * Get Object of the cell which mouse is hovering.
	 * @return Object of the hovered cell, or null if mouse is not hovering any cell.
	 */
	public T getMouseHoveredElement() {
		final int index = getMouseHoveredIndex();
		return isValidIndex(index) ? storage.get(index) : null;
	}
}
