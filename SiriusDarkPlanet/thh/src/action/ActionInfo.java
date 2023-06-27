package action;

import java.util.ArrayList;

import static java.lang.Math.*;

public class ActionInfo {
	private final static int
		NONE = core.GHQ.NONE;
	public final static int
		DST = 0,
		MOVE = 1,
		SPEED = 2,
		ATTACK = 3;
	
	public static String name;
	public static final ArrayList<Integer>
		frame = new ArrayList<Integer>(),
		meaning = new ArrayList<Integer>();
	public static final ArrayList<Double>
		x = new ArrayList<Double>(), 
		y = new ArrayList<Double>();
	public static int
		initialX,
		initialY;
	public static final void clear() {
		name = "<Not named>";
		initialX = NONE;
		initialY = NONE;
		x.clear();
		y.clear();
		meaning.clear();
		frame.clear();
	}
	public static final void setStartPoint(int x,int y) {
		initialX = x;initialY = y;
	}
	public static final void addPlan(int frame,int meaning,double x,double y) {
		ActionInfo.frame.add(frame);
		ActionInfo.meaning.add(meaning);
		ActionInfo.x.add(x);
		ActionInfo.y.add(y);
	}
	public static final void addDstPlan(int frame,double x,double y) {
		addPlan(frame,DST,x,y);
	}
	public static final void addMovePlan(int frame,double x,double y) {
		addPlan(frame,MOVE,x,y);
	}
	public static final void addSpeedPlan(int frame,double x,double y) {
		addPlan(frame,SPEED,x,y);
	}
	public static final void addPlan_RA(int frame,int meaning,double radius,double angle) {
		addPlan(frame,meaning,radius*cos(angle),radius*sin(angle));
	}
	public static final void addDstPlan_RA(int frame,double radius,double angle) {
		addPlan(frame,DST,radius*cos(angle),radius*sin(angle));
	}
	public static final void addMovePlan_RA(int frame,double radius,double angle) {
		addPlan(frame,MOVE,radius*cos(angle),radius*sin(angle));
	}
	public static final void addSpeedPlan_RA(int frame,double radius,double angle) {
		addPlan(frame,SPEED,radius*cos(angle),radius*sin(angle));
	}
}