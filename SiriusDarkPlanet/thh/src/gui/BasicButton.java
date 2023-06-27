package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import core.GHQ;
import paint.ColorFraming;
import paint.rect.RectPaint;
import paint.text.StringPaint;

public class BasicButton extends GUIParts {
	protected StringPaint strPaint;
	protected RectPaint paintWhenHovered;
	protected RectPaint paintWhenPressed;
	protected Consumer<MouseEvent> clickEvent;
	
	//init
	public BasicButton() {}
	public BasicButton(String text) {
		super.setBGPaint(new ColorFraming(Color.BLACK, GHQ.stroke1));
		this.strPaint = new StringPaint(text);
	}
	public BasicButton(RectPaint basePaint, String text) {
		super.setBGPaint(basePaint);
		this.strPaint = new StringPaint(text);
	}
	public BasicButton setText(StringPaint strPaint) {
		this.strPaint = strPaint;
		return this;
	}
	public BasicButton setText(String text) {
		this.strPaint.words = text;
		return this;
	}
	public BasicButton setPaintWhenHovered(RectPaint paint) {
		this.paintWhenHovered = paint;
		return this;
	}
	public BasicButton setPaintWhenPressed(RectPaint paint) {
		this.paintWhenPressed = paint;
		return this;
	}
	public BasicButton setClickEvent(Consumer<MouseEvent> clickEvent) {
		this.clickEvent = clickEvent;
		return this;
	}
	
	//role
	@Override
	public void paint() {
		if(super.isMouseEntered()) {
			if(paintWhenPressed != null && super.isClicking()) {
				paintWhenPressed.rectPaint(left(), top(), width(), height());
			} else if(paintWhenHovered != null) {
				paintWhenHovered.rectPaint(left(), top(), width(), height());
			} else {
				super.paint();
			}
		} else {
			super.paint();
		}
		if(strPaint != null)
			strPaint.dotPaint(cx(), cy());
	}
	@Override
	public boolean clicked(MouseEvent e) {
		super.clicked(e);
		if(clickEvent != null)
			clickEvent.accept(e);
		return true;
	}
}
