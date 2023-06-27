package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import core.GHQ;
import input.keyType.KeyTypeListener;

public class TitledLabel extends GUIParts{
	private final KeyTypeListener typeListener;
	public TitledLabel() {
		GHQ.addListenerEx(typeListener = new KeyTypeListener(width()/5) {
			@Override
			public void typeEnd(String text) {
				typeEnded(text);
			}
		});
	}
	public TitledLabel(String initialTitle) {
		titleStr = initialTitle;
		GHQ.addListenerEx(typeListener = new KeyTypeListener(width()/5) {
			@Override
			public void typeEnd(String text) {
				typeEnded(text);
			}
		});
	}
	private String titleStr = "";
	public boolean activated;
	public void setTitle(String title) {
		titleStr = title;
	}
	@Override
	public boolean clicked(MouseEvent e) {
		super.clicked(e);
		enableInputMode();
		return true;
	}
	@Override
	public void idle() {
		if(GHQ.isLastClickedUI(this))
			disableInputMode();
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(Color.BLACK);
		G2.setStroke(GHQ.stroke1);
		G2.setFont(GHQ.basicFont);
		G2.drawString(titleStr, intX() + 3, intY() - 8);
		backGroundPaint.rectPaint(point(), width(), height());
		G2.setColor(Color.BLACK);
		G2.setStroke(activated ? GHQ.stroke3 : GHQ.stroke1);
		G2.drawRect(intX(), intY(), width(), height());
		//System.out.println(typeListener.getText());
		if(activated && GHQ.nowTime() % 1000 < 500)
			G2.drawString(typeListener.getText() + "|", intX() + 3, intY() + 20);
		else
			G2.drawString(typeListener.getText(), intX() + 3, intY() + 20);
	}
	public void typeEnded(String text) {}
	public void setText(String text) {
		typeListener.setText(text);
	}
	public String getText() {
		return typeListener.getText();
	}
	public boolean textEquals(String str) {
		return typeListener.textEquals(str);
	}
	public final boolean isInputMode() {
		return activated;
	}
	public void enableInputMode() {
		activated = true;
		typeListener.enable();
	}
	public void disableInputMode() {
		activated = false;
		typeListener.disable();
	}
	@Override
	public GUIParts disable() {
		super.disable();
		typeListener.disable();
		return this;
	}
	public void clear() {
		typeListener.clear();
	}
	public String getGroup() {
		return name();
	}

}
