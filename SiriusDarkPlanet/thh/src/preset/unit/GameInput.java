package preset.unit;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import core.GHQ;

public class GameInput {
	protected boolean enabled, consumed;
	protected int lastPressedFrame;
	protected int lastReleasedFrame;
	private String name;
	public GameInput(String name) {
		this.name = name;
	}
	protected final void pressed() {
		enabled = true;
		lastPressedFrame = GHQ.nowFrame();
	}
	protected final void released() {
		enabled = consumed = false;
		lastReleasedFrame = GHQ.nowFrame();
	}
	public final boolean hasEvent() {
		return enabled && !consumed;
	}
	public final boolean hasEnabled() {
		return enabled;
	}
	public final boolean consume() {
		if(hasEvent()) {
			consumed = true;
			return true;
		}else
			return false;
	}
	public final int lastPressedFrame() {
		return lastPressedFrame;
	}
	public final int passedFrame() {
		return GHQ.passedFrame(lastPressedFrame);
	}
	public final String name() {
		return name;
	}
	public static class Keyboard extends GameInput {
		private int keyCode;
		public Keyboard(String name, int keyCode) {
			super(name);
			this.keyCode = keyCode;
		}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == keyCode)
				pressed();
		}
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == keyCode)
				released();
		}
	}
	public static class Mouse extends GameInput {
		private int mouseButton;
		public Mouse(String name, int mouseButton) {
			super(name);
			this.mouseButton = mouseButton;
		}
		public void mousePressed(MouseEvent e) {
			if(e.getButton() == mouseButton)
				pressed();
		}
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() == mouseButton)
				released();
		}
	}
}
