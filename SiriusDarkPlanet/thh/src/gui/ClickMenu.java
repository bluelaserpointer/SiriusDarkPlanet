package gui;

import core.GHQ;

public class ClickMenu<T> extends AutoResizeMenu {
	
	protected T targetObject;
	
	public ClickMenu(int w, int defaultLineH) {
		super(0, 0, w, defaultLineH);
	}
	public ClickMenu(int w, int defaultLineH, int partsMargin) {
		super(0, 0, w, defaultLineH, partsMargin);
	}
	
	//control
	public boolean tryOpen(T object) {
		return tryOpen(object, true);
	}
	public boolean tryOpen(T object, boolean condition) {
		if(object == null || !condition) {
			disable();
			return false;
		}else {
			targetObject = object;
			super.enable();
			point().setXY(GHQ.mouseScreenX(), GHQ.mouseScreenY());
			return true;
		}
	}
	public T get() {
		return targetObject;
	}
	public void remove() {
		targetObject = null;
	}
	@Override
	public GUIParts enable() {
		//reject normal enable();
		return this;
	}
	@Override
	public GUIParts disable() {
		super.disable();
		remove();
		return this;
	}
	@Override
	public void outsideClicked() {
		super.outsideClicked();
		disable();
	}
}
