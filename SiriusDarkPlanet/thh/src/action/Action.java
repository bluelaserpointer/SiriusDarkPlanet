package action;

import java.io.Serializable;

public class Action implements Serializable{
	private static final long serialVersionUID = -552460412467611988L;
	/*
	public final ActionSource SOURCE;
	
	public String name;
	public final int initialFrame;
	public final int[]
		frame,
		meaning;
	public final double[]
		x,y;
	
	public Action(ActionSource source) {
		this.SOURCE = source;
		initialFrame = GHQ.getNowFrame();
		name = ActionInfo.name;
		frame = ActionInfo.getFrameArray();
		meaning = ActionInfo.getMeaningArray();
		x = ActionInfo.getXArray();
		y = ActionInfo.getYArray();
	}
	
	public final int getNowPos() {
		final int passedFrame = GHQ.getPassedFrame(initialFrame);
		for(int i = 0;i < frame.length;i++) {
			if(passedFrame > frame[i])
				return i;
		}
		return GHQ.NONE;
	}*/
}
