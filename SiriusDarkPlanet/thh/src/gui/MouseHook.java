package gui;

import input.mouse.MouseListenerEx;

/**
 * A subclass of {@link GUIParts} for managing mouse drag&drop actions.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class MouseHook {
	protected final MouseListenerEx mouseLis = new MouseListenerEx();
	protected Object hookingObject;
	protected GUIParts sourceUI;
	
	/**
	 * Hooking a object to this MouseHook.
	 * @param object
	 */
	public void hook(Object object, GUIParts sourceUI) {
		hookingObject = object;
		this.sourceUI = sourceUI;
	}
	public void clear() {
		hookingObject = sourceUI = null;
	}
	/**
	 * Get the hooking object.
	 * @return hooking object
	 */
	public Object get() {
		return hookingObject;
	}
	/**
	 * Get the UI of hooking object source.
	 * @return hooking object
	 */
	public GUIParts sourceUI() {
		return sourceUI;
	}
	/**
	 * Removing the hooking object.
	 * @return hooked object
	 */
	public Object pull() {
		final Object OBJECT = hookingObject;
		hookingObject = null;
		return OBJECT;
	}
	
	//information
	public boolean isEmpty() {
		return hookingObject == null;
	}

	/*public void hookInStorageViewer(TableStorageViewer<T> inventoryViewer) {
		final int HOVERED_ID = inventoryViewer.getMouseHoveredID();
		if(hasObject()) {
			hook(inventoryViewer.storage.set(HOVERED_ID, get()));
		}else {
			hook(inventoryViewer.storage.remove(HOVERED_ID));
		}
	}*/
	/*
		if(hookingObject instanceof ItemData && hookingObject != ItemData.BLANK_ITEM) {
			final String AMOUNT_TEXT = String.valueOf(((ItemData)hookingObject).getAmount());
			GHQ.getG2D(Color.GRAY);
			GHQ.drawStringGHQ(AMOUNT_TEXT, GHQ.mouseScreenX() + SIZE/2 - 23, GHQ.mouseScreenY() + SIZE/2 - 9, GHQ.basicFont);
			GHQ.getG2D(Color.BLACK);
			GHQ.drawStringGHQ(AMOUNT_TEXT, GHQ.mouseScreenX() + SIZE/2 - 24, GHQ.mouseScreenY() + SIZE/2 - 10, GHQ.basicFont);
		}
	 */
}
