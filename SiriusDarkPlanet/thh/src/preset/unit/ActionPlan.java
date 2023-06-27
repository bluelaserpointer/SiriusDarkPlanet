package preset.unit;

import java.io.Serializable;
import java.util.ArrayDeque;

public class ActionPlan implements Serializable{
	private static final long serialVersionUID = -2290085814867461006L;
	private ArrayDeque<String> actionsName = new ArrayDeque<String>();
	private int phase;
	private int passedFrame;
	
	public void addAction(String actionName) {
		actionsName.addLast(actionName);
	}
	public String getAction() {
		passedFrame++;
		if(actionsName.size() > 0)
			return actionsName.peekFirst();
		return "<No Actions>";
	}
	public boolean hasAction(String actionName) {
		return actionsName.contains(actionName);
	}
	public int getActionSize() {
		return actionsName.size();
	}
	public int getPhase() {
		return phase;
	}
	public void nextPhase(int limitFrame) {
		if(passedFrame >= limitFrame) {
			phase++;
			passedFrame = 0;
		}
	}
	public void nextAction(int limitFrame) {
		if(actionsName.size() == 0) {
			System.out.println("nextAction is called illegally because size of actionsName is 0.");
		}else if(passedFrame >= limitFrame) {
			phase = passedFrame = 0;
			actionsName.removeFirst();
		}
	}
	public void clear() {
		actionsName.clear();
		phase = passedFrame = 0;
	}
}
