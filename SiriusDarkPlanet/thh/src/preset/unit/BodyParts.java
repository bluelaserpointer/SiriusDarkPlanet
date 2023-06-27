package preset.unit;

import java.util.LinkedList;

import paint.HasPaint;
import paint.dot.DotPaint;
import paint.dot.DotPaintParameter;
import paint.dot.HasDotPaint;
import physics.Angle;
import physics.HasAngle;
import physics.HitGroup;
import physics.HitInteractable;
import physics.Point;
import physics.RelativePoint;
import physics.hitShape.HitShape;
import preset.item.ItemData;

public class BodyParts implements HasPaint, HasAngle, HasDotPaint, HitInteractable {
	private Body body;
	private final Point point;
	private Angle angle = new Angle();
	private boolean active;
	private final LinkedList<BodyPartsType> types = new LinkedList<BodyPartsType>();
	private UnitAction action; //current action
	protected ItemData equipment; //current equipment
	private DotPaint dotPaint; //current appearance
	private DotPaint baseDotPaint; //base appearance when standing still
	
	protected DotPaintParameter equipmentDisplaySetting = new DotPaintParameter().setPoint(new RelativePoint(this, new Point.IntPoint(), true));
	private HasPaint equipmentLayer = new HasPaint() {
		@Override
		public void paint() {
			if(equipment != null)
				weaponPaint();
		}
	};
	
	public BodyParts(Body body, DotPaint basePaint, BodyPartsType type) {
		this.body = body;
		this.point = new Point.IntPoint();
		dotPaint = this.baseDotPaint = basePaint;
		types.add(type);
	}
	public BodyParts(Body body, Point point, DotPaint basePaint, BodyPartsType...types) {
		this.body = body;
		this.point = point;
		dotPaint = this.baseDotPaint = basePaint;
		for(BodyPartsType type : types)
			this.types.add(type);
	}
	//main role
	@Override
	public void paint() {
		dotPaint.dotPaint_turn(point(), angle.get());
	}
	//extend
	protected void weaponPaint() {
		equipment.getDotPaint().dotPaint(equipmentDisplaySetting);
	}
	//control
	public void setActive(boolean active) {
		this.active = active;
		if(!active && hasAction()) { //stop current action
			body.stopAction(action);
		}
	}
	public boolean equip(ItemData equipment) {
		if(equipment.canEquipTo(this)) {
			if(hasEquipment()) //remove old equipment
				dequip();
			this.equipment = equipment;
			equipment.equipped();
			body.equipments().add(equipment);
			return true;
		}
		return false;
	}
	public void dequip() {
		if(equipment == null)
			return;
		body.equipments().remove(equipment);
		equipment.dequipped();
		equipment = null;
	}
	/**
	 * Cannot call this function from outside, it must through from class Body's action(UnitAction action).
	 * @param action
	 * @param dotPaint
	 */
	protected void setAction(UnitAction action, DotPaint dotPaint) {
		this.action = action;
		this.dotPaint = dotPaint;
	}
	public void removeAction() {
		if(action == null)
			return;
		body.doingActions().remove(action);
		action = null;
		dotPaint = baseDotPaint;
	}
	public void setBasePaint(DotPaint basePaint) {
		baseDotPaint = basePaint;
		if(!hasAction())
			dotPaint = basePaint;
	}
	//information
	public Body body() {
		return body;
	}
	public Unit owner() {
		return body.owner();
	}
	public boolean isActive() {
		return active;
	}
	public boolean hasEquipment() {
		return equipment != null;
	}
	public boolean hasAction() {
		return action != null;
	}
	public boolean canOverWriteAction(UnitAction action) {
		return !hasAction() || action.canOverwrite(this.action);
	}
	public UnitAction action() {
		return action;
	}
	public LinkedList<BodyPartsType> types() {
		return types;
	}
	public ItemData equipment() {
		return equipment;
	}
	@Override
	public DotPaint getDotPaint() {
		return dotPaint;
	}
	@Override
	public HitShape hitShape() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Point point() {
		return point;
	}
	@Override
	public HitGroup hitGroup() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Angle angle() {
		return angle;
	}
	public HasPaint equipmentLayer() {
		return equipmentLayer;
	}
	public DotPaintParameter equipmentDisplaySetting() {
		return equipmentDisplaySetting;
	}
}
