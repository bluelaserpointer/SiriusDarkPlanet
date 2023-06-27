package input.key;

import java.util.Arrays;

import core.GHQ;

public class DoubleNumKeyListener extends SingleNumKeyListener{
	private static final long serialVersionUID = 2536692073666623325L;
	private final int keyInputedFrame[];
	private final int LIMIT_SPAN;

	public DoubleNumKeyListener(int maxNumber,int limitSpan) {
		super(maxNumber);
		keyInputedFrame = new int[targetKeys.length];
		Arrays.fill(keyInputedFrame, GHQ.NONE);
		LIMIT_SPAN = limitSpan;
	}
	public DoubleNumKeyListener(int limitSpan) {
		keyInputedFrame = new int[targetKeys.length];
		Arrays.fill(keyInputedFrame, GHQ.NONE);
		LIMIT_SPAN = limitSpan;
	}
	@Override
	public void pressEvent_index(int index) {
		if(!GHQ.isExpired_frame(keyInputedFrame[index],LIMIT_SPAN))
			super.hasKeyEvents.set(index);
		else
			keyInputedFrame[index] = GHQ.nowFrame();
	}
	@Override
	public void reset() {
		super.reset();
		Arrays.fill(keyInputedFrame, GHQ.NONE);
	}
}
