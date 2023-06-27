package gui;

import core.GHQ;

public class AnimatedGHQTextArea extends GHQTextArea {
	
	private String currentText;
	private int textSetFrame;
	private int textSpeed = 1;
	
	public AnimatedGHQTextArea setText(String currentText) {
		this.currentText = currentText;
		textSetFrame = GHQ.nowFrame();
		return this;
	}
	public AnimatedGHQTextArea setTextSpeed(int span) {
		this.textSpeed = span;
		return this;
	}
	
	@Override
	public void idle() {
		super.idle();
		if(currentText != null && !currentText.isEmpty()) {
			final int passedFrame = GHQ.passedFrame(textSetFrame);
			final int textAmount = textSpeed == 0 ? currentText.length() : Math.min(passedFrame/textSpeed , currentText.length());
			super.textArea().setText(currentText.substring(0, textAmount));
		}
	}
	
}
