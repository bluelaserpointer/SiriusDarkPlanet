package buff;

import java.io.Serializable;

import core.GHQ;
import core.GHQObject;
import paint.rect.HasRectPaint;
import paint.rect.RectPaint;
import preset.unit.Unit;

public abstract class Buff extends GHQObject implements HasRectPaint, Serializable{
	private static final long serialVersionUID = -6546281620292643179L;
	final int INITIAL_FRAME;
	protected Unit owner;
	
	public Buff(Unit owner) {
		INITIAL_FRAME = GHQ.nowFrame();
		this.owner = owner;
	}
	public String getName() {
		return "<Not named>";
	}
	public abstract void idle();
	public void setOwner(Unit owner) {
		this.owner = owner;
	}
	public Unit getOwner() {
		return owner;
	}
	public void reset() {};
	public void removed() {};
	public abstract RectPaint getRectPaint();
}
