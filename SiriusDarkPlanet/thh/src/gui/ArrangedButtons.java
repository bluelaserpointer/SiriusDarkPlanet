package gui;

import java.awt.event.MouseEvent;

import math.CellArranger;
import math.SquareCellArranger;
import paint.rect.RectPaint;

public abstract class ArrangedButtons<T> extends GUIParts {
	
	public final CellArranger arranger;
	
	public ArrangedButtons(int x, int y, CellArranger arranger) {
		super.setBounds(x, y, arranger.width(), arranger.height());
		this.arranger = arranger;
	}
	protected abstract void buttonClicked(T buttonValue);
	protected void buttonExtendPaint(T buttonValue, BasicButton button) {}
	//addButtons
	public BasicButton addButton(T buttonValue, RectPaint buttonPaint, CellArranger.Cell cell) {
		return (BasicButton)super.addLast(new BasicButton() {
			@Override
			public boolean clicked(MouseEvent e) {
				super.clicked(e);
				ArrangedButtons.this.buttonClicked(buttonValue);
				return true;
			}
			@Override
			public void paint() {
				super.paint();
				ArrangedButtons.this.buttonExtendPaint(buttonValue, this);
			}
		}).setName(name() + " id: " + (childList.size() - 1))
				.setBounds(intX() + cell.X, intY() + cell.Y, cell.W, cell.H)
					.setBGPaint(buttonPaint);
	}
	public BasicButton addButton(T buttonValue, RectPaint buttonPaint, int xPos, int yPos) {
		return addButton(buttonValue, buttonPaint, arranger.getBasicCell(xPos, yPos));
	}
	public ArrangedButtons<T> appendButton(T buttonValue, RectPaint buttonPaint, SquareCellArranger.Cell cell) {
		addButton(buttonValue, buttonPaint, cell);
		return this;
	}
	public ArrangedButtons<T> appendButton(T buttonValue, RectPaint buttonPaint, int xPos, int yPos) {
		return appendButton(buttonValue, buttonPaint, arranger.getBasicCell(xPos, yPos));
	}
	
	//information
	public CellArranger arranger() {
		return arranger;
	}
}
