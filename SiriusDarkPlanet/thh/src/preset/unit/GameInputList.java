package preset.unit;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import core.HasName;

public class GameInputList {
	private final LinkedList<GameInput.Keyboard> inputList_key = new LinkedList<GameInput.Keyboard>();
	private final LinkedList<GameInput.Mouse> inputList_mouse = new LinkedList<GameInput.Mouse>();
	public GameInput addInput(GameInput input) {
		if(input instanceof GameInput.Keyboard)
			inputList_key.add((GameInput.Keyboard)input);
		else if(input instanceof GameInput.Mouse)
			inputList_mouse.add((GameInput.Mouse)input);
		return input;
	}
	public void addKeyInput(String inputName, int keyCode) {
		inputList_key.add(new GameInput.Keyboard(inputName, keyCode));
	}
	public void addMouseInput(String inputName, int buttonID) {
		inputList_mouse.add(new GameInput.Mouse(inputName, buttonID));
	}
	public void addKeyInput(Enum<?> inputEnum, int keyCode) {
		addKeyInput(inputEnum.name(), keyCode);
	}
	public void addMouseInput(Enum<?> inputEnum, int buttonID) {
		addMouseInput(inputEnum.name(), buttonID);
	}
	public GameInput find(String inputName) {
		for(GameInput input : inputList_key) {
			if(input.name().equals(inputName)) {
				return input;
			}
		}
		for(GameInput input : inputList_mouse) {
			if(input.name().equals(inputName)) {
				return input;
			}
		}
		System.out.println("GameInputList: GameInput \"" + inputName + "\" does not exist.");
		return null;
	}
	public GameInput find(Enum<?> inputEnum) {
		return find(inputEnum.name());
	}
	public boolean hasEvent(String name) {
		final GameInput input = find(name);
		return input == null ? false : input.hasEvent();
	}
	public boolean hasEvent(HasName input) {
		return hasEvent(input.name());
	}
	public boolean consume(String name) {
		final GameInput input = find(name);
		return input == null ? false : input.consume();
	}
	public boolean hasEvent(String name, boolean doConsume) {
		return doConsume ? consume(name): hasEvent(name);
	}
	public boolean hasEvent(HasName input, boolean doConsume) {
		return hasEvent(input.name(), doConsume);
	}
	public boolean hasEventOne(String...names) {
		for(String name : names) {
			if(hasEvent(name))
				return true;
		}
		return false;
	}
	public boolean hasEventIgnoreConsume(String name) {
		final GameInput input = find(name);
		return input == null ? false : input.enabled;
	}
	public boolean consumeIgnoreConsume(String name) {
		final GameInput input = find(name);
		if(input == null)
			return false;
		if(input.enabled) {
			input.consumed = true;
			return true;
		}else
			return false;
	}
	public void keyPressed(KeyEvent e) {
		for(GameInput.Keyboard input : inputList_key)
			input.keyPressed(e);
	}
	public void keyReleased(KeyEvent e) {
		for(GameInput.Keyboard input : inputList_key)
			input.keyReleased(e);
	}
	public void mousePressed(MouseEvent e) {
		for(GameInput.Mouse input : inputList_mouse)
			input.mousePressed(e);
	}
	public void mouseReleased(MouseEvent e) {
		for(GameInput.Mouse input : inputList_mouse)
			input.mouseReleased(e);
	}
}
