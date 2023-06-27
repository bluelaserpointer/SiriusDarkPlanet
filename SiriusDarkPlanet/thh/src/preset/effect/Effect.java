package preset.effect;

import static java.lang.Math.PI;

import core.GHQ;
import core.GHQObject;
import paint.dot.DotPaint;
import paint.dot.HasDotPaint;
import physics.DstCntDynam;
import physics.HasPoint;

/**
 * A primal class for managing effect.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class Effect extends GHQObject implements HasPoint, HasDotPaint{
	public final int UNIQUE_ID;
	public static int nowMaxUniqueID = -1;

	protected GHQObject shooter; //an information source of user
	
	public int
		limitFrame = GHQ.MAX,
		limitRange = GHQ.MAX;
	public double
		accel;
	public DotPaint
		paintScript;
	
	public Effect() {
		physics().setPoint(new DstCntDynam().setMouseStageXY());
		shooter = GHQObject.NULL_GHQ_OBJECT;
		UNIQUE_ID = ++nowMaxUniqueID;
		accel = 0.0;
		paintScript = DotPaint.BLANK_SCRIPT;
	}
	public Effect(GHQObject shooter) {
		physics().setPoint(new DstCntDynam().setXY(shooter));
		point().setMoveAngleByBaseAngle(shooter);
		this.shooter = shooter;
		UNIQUE_ID = ++nowMaxUniqueID;
		accel = 0.0;
		paintScript = DotPaint.BLANK_SCRIPT;
	}
	public Effect(Effect effect) {
		point().setAll(effect.point());
		shooter = effect.shooter;
		UNIQUE_ID = ++nowMaxUniqueID;
		name = effect.name;
		limitFrame = effect.limitFrame;
		limitRange = effect.limitRange;
		accel = effect.accel;
		paintScript = effect.paintScript;
	}

	@Override
	public void idle() {
		super.idle();
		//LifeSpan & Range
		if(isOutOfLifeSpan() && outOfLifeSpanProcess())
			return;
		if(isOutOfRange() && outOfRangeProcess())
			return;
		//OutOfStage
		if(!point().inStage() && outOfStageProcess())
			return;
		//Speed & Acceleration
		point().moveBySpeed();
		point().addSpeed(accel,true);
	}
	@Override
	public void paint() {
		defaultPaint();
	}
	public final void defaultPaint() {
		paintScript.dotPaint_turn(point(), point().moveAngle());
	}
	
	//extends
	public boolean isOutOfLifeSpan() {
		return limitFrame <= passedFrame();
	}
	public boolean isOutOfRange() {
		return limitRange != GHQ.MAX && limitRange <= ((DstCntDynam)point()).getMovedDistance();
	}
	public boolean outOfLifeSpanProcess() {
		claimDeleteFromStage();
		return true;
	}
	public boolean outOfRangeProcess() {
		claimDeleteFromStage();
		return true;
	}
	public boolean outOfStageProcess() {
		claimDeleteFromStage();
		return true;
	}
	
	//tool
	//////////////
	//paint
	//////////////
	public final void setImageAlphaByLimitFrame() {
		GHQ.setImageAlpha((float)(1.0 - lifePercent()));
	}
	public final void fadingPaint() {
		setImageAlphaByLimitFrame();
		defaultPaint();
		GHQ.setImageAlpha();
	}
	public final void fadingPaint(int delay) {
		final int PASSED_FRAME = passedFrame();
		if(PASSED_FRAME < delay)
			defaultPaint();
		else {
			GHQ.setImageAlpha((float)(1.0 - (double)(PASSED_FRAME - delay)/(limitFrame - delay)));
			defaultPaint();
			GHQ.setImageAlpha();
		}
	}
	
	//////////////
	//clone and split
	//////////////
	public Effect getOriginal() {
		return new Effect(this);
	}
	public Effect getClone() {
		final Effect EFFECT = getOriginal();
		EFFECT.point().setAll(point());
		return EFFECT;
	}
	public final Effect addCloneToGHQ() {
		return GHQ.stage().addEffect(getClone());
	}
	public void split_xMirror(double dx,double dy) {
		this.point().addXY_allowsMoveAngle(-dx/2,dy);
		addCloneToGHQ().point().addX_allowsMoveAngle(dx);
	}
	public void split_yMirror(double dx,double dy) {
		this.point().addXY_allowsMoveAngle(dx,-dy/2);
		addCloneToGHQ().point().addY_allowsMoveAngle(dy);
	}
	public void split_Round(int radius,int amount) {
		final double D_ANGLE = 2*PI/amount;
		for(int i = 1;i < amount;i++)
			addCloneToGHQ().point().addXY_DA(radius, D_ANGLE*i);
		this.point().addXY_DA(radius, 0);
	}
	public void clone_Round(int radius,int amount) {
		final double D_ANGLE = 2*PI/amount;
		for(int i = 0;i < amount;i++)
			addCloneToGHQ().point().addXY_DA(radius, D_ANGLE*i);
	}
	public void split_Burst(int radius,int amount,double speed) {
		final double D_ANGLE = 2*PI/amount;
		for(int i = 1;i < amount;i++)
			addCloneToGHQ().point().fastParaAdd_DASpd(radius, D_ANGLE*i, speed);
		this.point().fastParaAdd_DASpd(radius, 0 ,speed);
	}
	public void clone_Burst(int radius,int amount,double speed) {
		final double D_ANGLE = 2*PI/amount;
		for(int i = 0;i < amount;i++)
			addCloneToGHQ().point().fastParaAdd_DASpd(radius, D_ANGLE*i, speed);
	}
	
	//////////////
	//information
	//////////////
	public int passedFrame() {
		return GHQ.passedFrame(initialFrame);
	}
	public double lifePercent() {
		return (double)passedFrame()/limitFrame;
	}
	@Override
	public final DotPaint getDotPaint() {
		return paintScript;
	}
	@Override
	public String name() {
		return "[Effect]" + name;
	}
}
