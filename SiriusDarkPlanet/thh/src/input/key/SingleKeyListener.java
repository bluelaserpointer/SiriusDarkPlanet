package input.key;

/**
 * A major class for managing keyboard input event.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class SingleKeyListener extends KeyListenerEx{
	private static final long serialVersionUID = -7351066034418156232L;
	public SingleKeyListener(int targetKeys[]) {
		super(targetKeys);
	}
	@Override
	public void pressEvent_index(int index) {
		if(!super.pressingKeyEvents.get(index))
			super.hasKeyEvents.set(index);
	}
	@Override
	public void releaseEvent_index(int index) {
		super.hasKeyEvents.clear(index);
		super.pressingKeyEvents.clear(index);
	}
}
