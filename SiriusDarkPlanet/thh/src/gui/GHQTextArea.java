package gui;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import core.GHQ;

public class GHQTextArea extends GUIParts {
	private final JTextPane jta = new JTextPane();
	{
		jta.setOpaque(false);
		if(GHQ.initialFont != null)
			jta.setFont(GHQ.initialFont.deriveFont(40F));
	}
	@Override
	public void paint() {
		super.paint();
		final int TX = point().intX(), TY = point().intY();
		GHQ.getG2D().translate(TX, TY);
		jta.paint(GHQ.getG2D());
		GHQ.getG2D().translate(-TX, -TY);
	}
	@Override
	public GHQTextArea setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		jta.setBounds(x, y, w, h);
		return this;
	}
	//information
	public JTextPane textArea() {
		return jta;
	}
	public GHQTextArea setTextColor(Color color) {
		jta.setCharacterAttributes(StyleContext.getDefaultStyleContext().addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color), false);
		return this;
	}
}
