package gui;

import java.awt.event.MouseEvent;

public class UIShowHideButton extends GUIParts {
	
	final GUIParts targetUI;
	
	public UIShowHideButton(GUIParts targetUI) {
		this.targetUI = targetUI;
	}
	
	@Override
	public boolean clicked(MouseEvent e) {
		boolean consumed = super.clicked(e);
		this.flipTargetUI();
		return consumed;
	}
	
	public boolean isShowingTargetUI() {
		return targetUI.isEnabled();
	}
	
	public UIShowHideButton showTargetUI() {
		targetUI.enable();
		return this;
	}
	public UIShowHideButton hideTargetUI() {
		targetUI.disable();
		return this;
	}
	public UIShowHideButton flipTargetUI() {
		targetUI.flip();
		return this;
	}
}
