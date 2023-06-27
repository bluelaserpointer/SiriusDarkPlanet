package gui;

/**
 * This is a basic interface for GUIParts, helps managing object dragging.
 * @author bluelaserpointer
 *
 */
public interface DragIO {
	public default boolean checkDragIn(GUIParts sourceUI, Object dragObject) {
		return false;
	}
	public default boolean checkDragOut(GUIParts targetUI, Object dragObject) {
		return true;
	}
	/**
	 * Drag in a object from another UI.
	 * @param sourceUI
	 * @param dragObject
	 */
	public default void dragIn(GUIParts sourceUI, Object dragObject) {}
	/**
	 * Drag out a object, or swap a object with another UI.
	 * @param targetUI The another UI in this drag operation.
	 * @param dragObject The object this UI need to remove.
	 * @param swapObject If not null it means this is a swap operation.
	 */
	public default void dragOut(GUIParts targetUI, Object dragObject, Object swapObject) {}
	/**
	 * Tell current drag operation is finished.
	 * This method used to switch off guidance or something appears during drag operation.
	 */
	public default void dragFinished() {}
	/**
	 * Peek the object able to drag in / out at current mouse cursor.
	 * This method is used by over write checking (by drag operation) and swap operation.
	 * @return object at mouse cursor.
	 */
	public default Object peekDragObject() {
		return null;
	}
	/**
	 * Claim this UI just drag in / out object link so that some behaviors are special.
	 * @return true - drag in / out object link, false - drag in / out real object.
	 */
	public default boolean doLinkDrag() {
		return false;
	}
}
