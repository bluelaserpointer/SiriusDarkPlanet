package input.mouse;

import java.io.Serializable;

import core.GHQ;

/**
 * A primal class for managing mouse input event.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class MouseListenerEx implements Serializable{
	private static final long serialVersionUID = 2878445766577630707L;

	protected boolean button1,button2,button3;
	
	public MouseListenerEx() {
		GHQ.addListenerEx(this);
	}
	/**
	 * [This method only be called from GHQ.]Make a mouse press event of button1.
	 */
	public void pressButton1Event() {
		button1 = true;
	}
	/**
	 * [This method only be called from GHQ.]Make a mouse press event of button2.
	 */
	public void pressButton2Event() {
		button2 = true;
	}
	/**
	 * [This method only be called from GHQ.]Make a mouse press event of button3.
	 */
	public void pressButton3Event() {
		button3 = true;
	}
	/**
	 * Judge if button1 has a event.
	 * @return true - has a event / false - no event
	 */
	public final boolean hasButton1Event() {
		return button1;
	}
	/**
	 * Judge if button2 has a event.
	 * @return true - has a event / false - no event
	 */
	public final boolean hasButton2Event() {
		return button2;
	}
	/**
	 * Judge if button3 has a event.
	 * @return true - has a event / false - no event
	 */
	public final boolean hasButton3Event() {
		return button3;
	}
	/**
	 * Judge if button1 has a event, and if so, remove the event at the same time.
	 * @return true - has a event / false - no event
	 */
	public final boolean pullButton1Event() {
		if(button1) {
			button1 = false;
			return true;
		}
		return false;
	}
	/**
	 * Judge if button2 has a event, and if so, remove the event at the same time.
	 * @return true - has a event / false - no event
	 */
	public final boolean pullButton2Event() {
		if(button2) {
			button2 = false;
			return true;
		}
		return false;
	}
	/**
	 * Judge if button3 has a event, and if so, remove the event at the same time.
	 * @return true - has a event / false - no event
	 */
	public final boolean pullButton3Event() {
		if(button3) {
			button3 = false;
			return true;
		}
		return false;
	}
	/**
	 * Remove all the event.
	 */
	public void reset() {
		button1 = button2 = button3 = false;
	}
}
