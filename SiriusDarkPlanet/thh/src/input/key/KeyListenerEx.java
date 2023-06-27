package input.key;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.BitSet;

import core.GHQ;

/**
 * A primal class for managing keyboard input event.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class KeyListenerEx implements Serializable{
	private static final long serialVersionUID = -8033137272230272770L;

	protected final int targetKeys[];
	protected final BitSet hasKeyEvents;
	protected final BitSet pressingKeyEvents;
	private void printNotFoundError(int keyCode) {
		System.out.println("KeyListenerEx not found keyCode: " + keyCode);
	}
	/**
	 * Create KeyListenerEx instance which is able to listen keyboard input of certain keys indicated by "targetKeys[]".
	 * Note that the targetKeys[] can only contains constants member of {@link java.awt.event.KeyEvent}(like {@link KeyEvent#VK_A}, {@link KeyEvent#VK_SPACE}, etc.).
	 * @param targetKeys int array containing constants of {@link KeyEvent}
	 */
	public KeyListenerEx(int targetKeys[]) {
		this.targetKeys = targetKeys;
		hasKeyEvents = new BitSet(targetKeys.length);
		pressingKeyEvents = new BitSet(targetKeys.length);
		GHQ.addListenerEx(this);
	}
	/**
	 * [This method only be called from GHQ.]Make a key press event of "keyCode".
	 * @param keyCode
	 */
	public final void pressEvent(int keyCode) {
		for(int i = 0;i < targetKeys.length;i++) {
			if(targetKeys[i] == keyCode) {
				pressEvent_index(i);
				return;
			}
		}
	}
	protected abstract void pressEvent_index(int index);
	/**
	 * [This method only be called from GHQ.]Make a key release event of "keyCode".
	 * @param keyCode
	 */
	public final void releaseEvent(int keyCode) {
		for(int i = 0;i < targetKeys.length;i++) {
			if(targetKeys[i] == keyCode) {
				releaseEvent_index(i);
			return;
			}
		}
	}
	protected abstract void releaseEvent_index(int index);
	/**
	 * Judge if the key of "keyCode" has a event.
	 * @param keyCode
	 * @return
	 */
	public final boolean hasEvent(int keyCode) {
		for(int i = 0;i < targetKeys.length;i++) {
			if(targetKeys[i] == keyCode)
				return hasKeyEvents.get(i);
		}
		printNotFoundError(keyCode);
		return false;
	}
	public final boolean hasEventOne(int... keyCodes) {
		for(int i = 0;i < targetKeys.length;i++) {
			for(int keyCode : keyCodes) {
				if(targetKeys[i] == keyCode && hasKeyEvents.get(i))
					return true;
			}
		}
		return false;
	}
	/**
	 * Judge if the key of "keyCode" has a event, and if so, remove the event at the same time.
	 * @param keyCode
	 * @return
	 */
	public final boolean pullEvent(int keyCode) {
		for(int i = 0;i < targetKeys.length;i++) {
			if(targetKeys[i] == keyCode) {
				if(hasKeyEvents.get(i)) {
					hasKeyEvents.clear(i);
					pressingKeyEvents.set(i);
					return true;
				}else
					return false;
			}
		}
		printNotFoundError(keyCode);
		return false;
	}
	/**
	 * Remove all the event.
	 */
	public void reset() {
		hasKeyEvents.clear();
	}
}
