package gui;

import java.awt.Shape;
import java.awt.event.MouseWheelEvent;

import core.GHQ;
import paint.ImageFrame;

public class ScrollBar extends GUIParts {
	private final BasicButton upBtn = new BasicButton(), downBtn = new BasicButton();
	private int scrollSpd = 1;
	{ //button images
		upBtn.setBGPaint(ImageFrame.create("thhimage/gui_editor/ScrollUp.png"));
		downBtn.setBGPaint(ImageFrame.create("thhimage/gui_editor/ScrollDown.png"));
		addLast(upBtn);
		addLast(downBtn);
	}
	private int scrollPos;
	
	private GUIParts objectUI;
	public ScrollBar(GUIParts objectUI) {
		setObject(objectUI, false);
		adjustControlButtonsPosition();
		adjustObjectUIPosition();
	}
	@Override
	public void idle() {
		final Shape DEFAULT_CLIP = GHQ.getG2D().getClip();
		GHQ.getG2D().setClip(point().intX(), point().intY() + 2, width(), height());
		super.idle();
		GHQ.getG2D().setClip(DEFAULT_CLIP);
		//scroll by up&down button
		if(upBtn.isClicking()) {
			scroll(scrollSpd);
		}else if(downBtn.isClicking()) {
			scroll(-scrollSpd);
		}
	}
	@Override
	public boolean mouseWheelMoved(MouseWheelEvent e) {
		//scroll by mouse wheel
		if(e.getWheelRotation() == 1)
			scroll(-scrollSpd);
		else if(e.getWheelRotation() == -1)
			scroll(scrollSpd);
		return true;
	}
	private void adjustObjectUIPosition() {
		objectUI.setBounds(point().intX(), point().intY() + scrollPos, objectUI.width(), objectUI.height());
	}
	private void adjustControlButtonsPosition() {
		upBtn.setBounds(point().intX() + width() - 25, point().intY(), 25, 25);
		downBtn.setBounds(point().intX() + width() - 25, point().intY() + height() - 25, 25, 25);
	}
	private void scroll(int value) {
		scrollPos += value;
		if(value > 0) { //over up scrolled
			if(scrollPos > 0)
				scrollPos = 0;
		}else if(scrollPos + objectUI.height() < 1) //over down scrolled
			scrollPos = 1 - objectUI.height();
		adjustObjectUIPosition();
	}
	//control
	public void setObject(GUIParts parts, boolean keepCurrentPosition) {
		super.addLast(objectUI = parts);
		if(keepCurrentPosition)
			setBounds(point().intX(), point().intY(), parts.width(), parts.height());
		else
			setBounds(parts.point().intX(), parts.point().intY(), parts.width(), parts.height());
	}
	public ScrollBar setScrollSpd(int speed) {
		scrollSpd = speed;
		return this;
	}
	@Override
	public GUIParts setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w + 25, h + 25);
		adjustControlButtonsPosition();
		adjustObjectUIPosition();
		return this;
	}
}
