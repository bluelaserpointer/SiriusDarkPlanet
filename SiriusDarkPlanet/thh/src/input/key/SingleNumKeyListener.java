package input.key;

import java.awt.event.KeyEvent;

import core.GHQ;

/**
 * A primal class for managing keyboard number keys input event.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class SingleNumKeyListener extends KeyListenerEx{
	private static final long serialVersionUID = -7351066034418156232L;
	public SingleNumKeyListener(int maxNumber) {
		super(new int[Math.min(maxNumber + 1, 10)]);
		for(int i = 0;i <= targetKeys.length;i++)
			super.targetKeys[i] = KeyEvent.VK_0 + i;
	}
	public SingleNumKeyListener() {
		super(new int[10]);
		for(int i = 0;i <= 9;i++)
			super.targetKeys[i] = KeyEvent.VK_0 + i;
	}
	@Override
	public void pressEvent_index(int index) {
		super.hasKeyEvents.set(index);
	}
	@Override
	public void releaseEvent_index(int index) {
		super.hasKeyEvents.clear(index);
	}
	public final int getHasEventKeyNum() {
		for(int i = 0;i < targetKeys.length;i++) {
			if(hasKeyEvents.get(i))
				return i;
		}
		return GHQ.NONE;
	}
	public final int pullHasEventKeyNum() {
		for(int i = 0;i < targetKeys.length;i++) {
			if(hasKeyEvents.get(i)) {
				hasKeyEvents.clear(i);
				return i;
			}
		}
		return GHQ.NONE;
	}
	public static final int keyNumToLocationNum(int keyNum) {
		return keyNum == GHQ.NONE ? GHQ.NONE : (keyNum == 0 ? 9 : keyNum - 1);
	}
}
