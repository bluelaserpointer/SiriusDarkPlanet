package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import core.GHQ;
import preset.item.ItemData;

/**
 * A {@link GUIParts} subclass for managing Excel-like {@link ItemData} display, additionally draws their amount at the right-bottom corner.
 * Mainly used for inventory display.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class ItemStorageViewer extends TableStorageViewer<ItemData> {
	protected ClickMenu<ItemData> itemRCMenu;
	
	//init
	public ItemStorageViewer() {
		super(ItemData.class);
	}
	public ItemStorageViewer setRCMenu(ClickMenu<ItemData> rcMenu) {
		super.addLast(itemRCMenu = rcMenu).disable();
		return this;
	}
	@Override
	public void paintOfCell(int id, ItemData object, int x,int y) {
		cellPaint.rectPaint(x, y, cellSize);
		final Graphics2D G2 = GHQ.getG2D();
		G2.setFont(GHQ.basicFont);
		G2.setColor(Color.GRAY);
		if(object == null) {
			G2.drawString("Empty", x + cellSize - 23, y + cellSize - 9);
			return;
		}
		object.getDotPaint().dotPaint_capSize(x + cellSize/2, y + cellSize/2, (int)(cellSize*0.8));
		if(isSpaceElement(object)) {
			G2.drawString("Empty", x + cellSize - 23, y + cellSize - 9);
		}
		if(object != ItemData.BLANK_ITEM && !object.isNonStackable()) {
			final int AMOUNT = object.getAmount();
			G2.drawString(String.valueOf(AMOUNT), x + cellSize - 23, y + cellSize - 9);
			G2.setColor(Color.BLACK);
			G2.drawString(String.valueOf(AMOUNT), x + cellSize - 24, y + cellSize - 10);
		}
	}

	@Override
	public boolean clicked(MouseEvent e) {
		final ItemData ELEMENT = getMouseHoveredElement();
		if(isSpaceElement(ELEMENT) && e.getButton() == MouseEvent.BUTTON3) {
			if(itemRCMenu != null)
				itemRCMenu.tryOpen(ELEMENT);
			else
				return false;
		} else
			super.clicked(e);
		return true;
	}
	@Override
	public void dragOut(GUIParts targetUI, Object dropObject, Object swapObject) {
		final int id = storage.indexOf(dropObject);
		//remove owner info from item
		if(targetUI != this)
			super.receptingClass.cast(dropObject).setOwner(null);
		if(swapObject == null) { //move
			storage.remove(id);
		} else //swap
			storage.set(id, super.receptingClass.cast(swapObject));
	}
}
