package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import core.GHQ;
import core.GHQObject;
import paint.ColorFilling;
import paint.ImageFrame;
import paint.rect.RectPaint;
import physics.HasBoundingBox;
import physics.HasUIBoundingBox;
import physics.Point;
import physics.hitShape.AbstractRectShape;
import physics.hitShape.HasArea;
import physics.hitShape.RectShape;

/**
 * A primely class for managing UI.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class GUIParts extends GHQObject implements DragIO, HasUIBoundingBox {
	public static final GUIParts NULL_GUIPARTS = new GUIParts();
	protected boolean isEnabled;
	protected boolean isClicking;
	protected RectPaint backGroundPaint;
	protected LinkedList<GUIParts> childList = null;
	public GUIParts() {
		backGroundPaint = RectPaint.BLANK_SCRIPT;
		physics().setPoint(new Point.IntPoint());
		physics().setHitShape(new RectShape(this, AbstractRectShape.MATCH_SCREEN_SIZE, AbstractRectShape.MATCH_SCREEN_SIZE));
	}
	@Override
	public GUIParts setName(String name) {
		super.setName(name);
		return this;
	}
	//tool-getClildren
	public LinkedList<GUIParts> getChildren() {
		if(childList == null)
			childList = new LinkedList<GUIParts>();
		return childList;
	}
	public GUIParts lastChild() {
		return getChildren().getLast();
	}
	public GUIParts firstChild() {
		return getChildren().getFirst();
	}
	//init-background paint
	public GUIParts setBGPaint(RectPaint paintScript) {
		backGroundPaint = paintScript;
		return this;
	}
	public GUIParts setBGColor(Color color) {
		backGroundPaint = new ColorFilling(color);
		return this;
	}
	public GUIParts setBGImage(String imageURL) {
		backGroundPaint = ImageFrame.create(this, imageURL);
		return this;
	}
	public GUIParts setBGBlank() {
		backGroundPaint = RectPaint.BLANK_SCRIPT;
		return this;
	}
	public RectShape rectShape() {
		return ((RectShape)hitShape());
	}
	public GUIParts setBoundsSize(int w, int h) {
		rectShape().setBoundsSize(w, h);
		return this;
	}
	public GUIParts setBoundsSize(HasArea area) {
		rectShape().setBoundsSize(area);
		return this;
	}
	public GUIParts setBounds(int x, int y, int w, int h) {
		setXY(x, y);
		setBoundsSize(w, h);
		return this;
	}
	public GUIParts setBounds(HasBoundingBox sample) {
		return setBounds(sample.left(), sample.top(), sample.width(), sample.height());
	}
	public GUIParts setXY(int x, int y) {
		point().setXY(x, y);
		return this;
	}
	public GUIParts setBounds_center(int centerX, int centerY, int w, int h) {
		point().setXY(centerX - w/2, centerY - h/2);
		setBoundsSize(w, h);
		return this;
	}
	public GUIParts setBounds_center(Point centerPoint, int w, int h) {
		return setBounds_center(centerPoint.intX(), centerPoint.intY(), w, h);
	}
	//init-children
	public GUIParts appendFirst(GUIParts childParts) {
		if(childParts == this)
			System.out.println("! -- GUIParts.addFirst: cannot add itself to childParts");
		else {
			getChildren().addFirst(childParts);
			childParts.enable();
		}
		return this;
	}
	public <T extends GUIParts>T addFirst(T childParts) {
		if(childParts == this)
			System.out.println("! -- GUIParts.addFirst: cannot add itself to childParts");
		else {
			getChildren().addFirst(childParts);
			childParts.enable();
		}
		return childParts;
	}
	public GUIParts appendLast(GUIParts childParts) {
		if(childParts == this)
			System.out.println("! -- GUIParts.addFirst: cannot add itself to childParts");
		else {
			getChildren().addLast(childParts);
			childParts.enable();
		}
		return this;
	}
	public <T extends GUIParts>T addLast(T childParts) {
		if(childParts == this)
			System.out.println("! -- GUIParts.addFirst: cannot add itself to childParts");
		else {
			getChildren().addLast(childParts);
			childParts.enable();
		}
		return childParts;
	}
	public <T extends GUIParts>T cloneDown(int padding, T childParts) {
		final GUIParts lastChild = lastChild();
		childParts.setBounds(lastChild).addXY(0, lastChild.height() + padding);
		return addLast(childParts);
	}
	public <T extends GUIParts>T cloneUp(int padding, T childParts) {
		final GUIParts lastChild = lastChild();
		childParts.setBounds(lastChild).addXY(0, -lastChild.height() - padding);
		return addLast(childParts);
	}
	public <T extends GUIParts>T cloneRight(int padding, T childParts) {
		final GUIParts lastChild = lastChild();
		childParts.setBounds(lastChild).addXY(lastChild.width() + padding, 0);
		return addLast(childParts);
	}
	public <T extends GUIParts>T cloneLeft(int padding, T childParts) {
		final GUIParts lastChild = lastChild();
		childParts.setBounds(lastChild).addXY(-lastChild.width() - padding, 0);
		return addLast(childParts);
	}
	public <T extends GUIParts>T cloneDown(T childParts) {
		return cloneDown(0, childParts);
	}
	public <T extends GUIParts>T cloneUp(T childParts) {
		return cloneUp(0, childParts);
	}
	public <T extends GUIParts>T cloneRight(T childParts) {
		return cloneRight(0, childParts);
	}
	public <T extends GUIParts>T cloneLeft(T childParts) {
		return cloneLeft(0, childParts);
	}
	
	public <T extends GUIParts>T remove(T childParts) {
		getChildren().remove(childParts);
		return childParts;
	}
	public void removeAll() {
		getChildren().clear();
	}
	//control-coordinate
	public void addXY(int dx, int dy) {
		point().addXY(dx, dy);
		if(childList != null) {
			for(GUIParts ver : childList)
				ver.addXY(dx, dy);
		}
	}
	//main role
	@Override
	public void idle() {
		super.idle();
		childIdle();
	}
	public void childIdle() {
		if(childList != null) {
			final Iterator<GUIParts> ite = childList.descendingIterator();
			while(ite.hasNext())
				ite.next().idleIfEnabled();
		}
	}
	public final void idleIfEnabled() {
		if(isEnabled)
			idle();
	}
	@Override
	public void paint() {
		backGroundPaint.rectPaint(point().intX(), point().intY(), width(), height());
	}
	//control-uiEvent
	public GUIParts enable() {
		isEnabled = true;
		return this;
	}
	public GUIParts disable() {
		isClicking = false;
		isEnabled = false;
		return this;
	}
	public boolean isMouseEntered() {
		return GHQ.screenMouseInArea(point().intX(), point().intY(), width(), height());
	}
	/**
	 * 
	 * @param e
	 * @return If consume this signal.
	 */
	public boolean clicked(MouseEvent e) {
		GHQ.setLastClickedUI(this);
		isClicking = true;
		return true;
	}
	public void checkOutsideClicked() {
		isClicking = false;
		if(!isScreenMouseOvered())
			outsideClicked();
		else {
			if(childList != null) {
				for(GUIParts parts : childList) {
					parts.checkOutsideClicked();
				}
			}
		}
	}
	public void outsideClicked() {
		if(GHQ.lastClickedUI() == this)
			return;
		isClicking = false;
		if(childList != null) {
			for(GUIParts parts : childList) {
				if(parts.isEnabled())
					parts.outsideClicked();
			}
		}
	}
	public void released(MouseEvent e) {
		isClicking = false;
	}
	public void outsideReleased() {
		isClicking = false;
		if(childList != null) {
			for(GUIParts parts : childList) {
				if(parts.isEnabled())
					parts.outsideReleased();
			}
		}
	}
	public void mouseOver() {
	}
	public void mouseOut() {
		if(childList != null) {
			for(GUIParts parts : childList) {
				if(parts.isEnabled())
					parts.mouseOut();
			}
		}
	}
	public boolean mouseWheelMoved(MouseWheelEvent e) {
		return false;
	}
	public final Stack<GUIParts> uiAtCursur() {
		Stack<GUIParts> stack = new Stack<GUIParts>();
		stack = uiAtCursur(stack);
		return stack;
	}
	public Stack<GUIParts> uiAtCursur(Stack<GUIParts> stack) {
		stack.push(this);
		if(childList != null) {
			for(GUIParts parts : childList) {
				if(parts.isEnabled() && parts.isMouseEntered()) {
					//System.out.println("give to child: " + parts.name());
					parts.uiAtCursur(stack);
					break;
				}
			}
		}
		return stack;
	}
	public void flip() {
		if(isEnabled)
			disable();
		else
			enable();
	}
	public void clickingReleased() {
		isClicking = false;
	}
	//information
	public final boolean isEnabled() {
		return isEnabled;
	}
	public final boolean isClicking() {
		return isClicking;
	}
	
	//tool
	public boolean clicked_debugMode(MouseEvent e) {
		boolean clickAbsorbed = false, clicked = false;
		for(GUIParts parts : childList) {
			if(parts.isEnabled()) {
				if(clickAbsorbed || !parts.isMouseEntered()) {
					System.out.println(parts.name() + " is outside clicked.");
					parts.outsideClicked();
				}else {
					parts.clicked(e);
					System.out.println(parts.name() + " is clicked.");
					clicked = true;
				}
			}
		}
		return clicked;
	}
}