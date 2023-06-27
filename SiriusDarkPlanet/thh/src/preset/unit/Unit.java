package preset.unit;

import calculate.Damage;
import core.GHQ;
import core.GHQObject;
import paint.ImageFrame;
import paint.dot.DotPaint;
import paint.dot.HasDotPaint;
import physics.HasAnglePoint;
import physics.HitInteractable;
import physics.Point;
import preset.item.ItemData;

/**
 * A primal class for managing unit.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class Unit extends GHQObject implements HasAnglePoint, HasDotPaint {
	public static final Unit NULL_UNIT = new Unit() {
		private ImageFrame imageFrame = ImageFrame.create(this, "thhimage/gui_editor/Unit.png");
		@Override
		public void paint() {
			super.paint();
			imageFrame.dotPaint(point());
		}
		@Override
		public Unit respawn(int spawnX, int spawnY) {
			return this;
		}
		@Override
		public boolean isAlive() {
			return true;
		}
		@Override
		public String name() {
			return "DummyUnit";
		}
		@Override
		public DotPaint getDotPaint() {
			return DotPaint.BLANK_SCRIPT;
		}
	};
	public String originalName = "";
	
	/////////////
	//Initialization
	/////////////
	public static final <T extends Unit>T initialSpawn(T unit, int spawnX, int spawnY) {
		unit.loadImageData();
		unit.respawn(spawnX,spawnY);
		return unit;
	}
	public static final <T extends Unit>T initialSpawn(T unit, Point point) {
		return initialSpawn(unit, point.intX(), point.intY());
	}
	public abstract Unit respawn(int spawnX, int spawnY);
	public final Unit respawn(Point point) {
		return respawn(point.intX(), point.intY());
	}
	public void loadImageData(){}
	
	@Override
	public void idle() {
		super.idle();
		if(!isAlive()) {
			claimDeleteFromStage();
		}
	}
	
	/////////////
	//control
	/////////////
	public void damagedTarget(Unit targetUnit, Damage damage) {}

	/////////////
	//event
	/////////////
	public void removedItem(ItemData item) {}
	
	/////////////
	//information
	/////////////
	public String name() {
		return "[Unit]" + GHQ.NOT_NAMED;
	}
	@Override
	public boolean intersects(HitInteractable object) {
		return isAlive() && super.intersects(object);
	}
	public abstract boolean isAlive();
}