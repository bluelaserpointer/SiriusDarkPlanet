package preset.unit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import paint.HasPaint;
import paint.dot.DotPaint;
import physics.Angle;
import physics.HasAnglePoint;
import physics.Point;
import preset.item.ItemData;

public class Body implements HasPaint, HasAnglePoint {
	public static final Body NULL_BODY = new Body(Unit.NULL_UNIT);
	
	protected Unit owner;
	protected LinkedList<BodyParts> bodyParts = new LinkedList<BodyParts>();
	protected HashMap<Class<? extends UnitAction>, HashMap<BodyParts, DotPaint>> doableActionsMap = new HashMap<Class<? extends UnitAction>, HashMap<BodyParts, DotPaint>>();
	protected LinkedList<UnitAction> doingActions = new LinkedList<UnitAction>();
	protected LinkedList<ItemData> equipments = new LinkedList<ItemData>();
	protected UnitAction waitingAction;
	
	public class ActionInfo {
		final DotPaint dotPaint;
		final BodyParts usedBodyParts;
		ActionInfo(DotPaint paint, BodyParts usedBodyParts) {
			dotPaint = paint;
			this.usedBodyParts = usedBodyParts;
		}
	}
	public Body(Unit owner) {
		this.owner = owner;
	}
	//main-role
	public final void idle() {
		this.idle(true, true);
	}
	public void idle(boolean doActionIdle, boolean doEquipmentIdle) {
		if(doActionIdle)
			actionIdle();
		if(doEquipmentIdle)
			equipmentIdle();
	}
	public void actionIdle() {
		final LinkedList<UnitAction> doingActions = new LinkedList<UnitAction>(this.doingActions);
		for(UnitAction action : doingActions)
			action.idle();
	}
	public void equipmentIdle() {
		for(ItemData equipment : equipments)
			equipment.idle();
	}
	@Override
	public void paint() {
		for(BodyParts parts : bodyParts) {
			parts.paint();
		}
	}
	//control
	public BodyParts addBodyParts(BodyParts parts) {
		bodyParts.add(parts);
		return parts;
	}
	public boolean removeBodyParts(BodyParts parts) {
		return bodyParts.remove(parts);
	}
	//control-equipment
	public boolean equip(ItemData equipment) {
		final BodyParts targetParts = equippableBodyParts(equipment, false);
		if(targetParts == null)
			return false;
		targetParts.equip(equipment);
		this.equipped(equipment);
		return true;
	}
	public boolean equip(ItemData equipment, BodyParts targetBodyParts) {
		if(equipment.canEquipTo(targetBodyParts)) {
			targetBodyParts.equip(equipment);
			this.equipped(equipment);
			return true;
		}
		return false;
	}
	public boolean dequip(ItemData equipment) {
		for(BodyParts parts : bodyParts) {
			if(parts.equipment() == equipment) {
				parts.dequip();
				return true;
			}
		}
		this.dequipped(equipment);
		return false;
	}
	//control-action
	public void addDoableActionAnimations(Class<? extends UnitAction> actionClass, HashMap<BodyParts, DotPaint> actionDotPaints) {
		doableActionsMap.put(actionClass, actionDotPaints);
	}
	/**
	 * Try set this action only if its extraPrecondition and canOverwrite is true.
	 * @param action
	 */
	public boolean action(UnitAction action) {
		if(!action.precondition())
			return false;
		final HashMap<BodyParts, DotPaint> actionDotPaints = this.findActionInfo(action.getClass());
		if(actionDotPaints == null) { //Not a doable action.
			System.out.println("!--Body.action(UnitAction action) received undoable action: " + action.getClass().getName());
			return false;
		}
		//check overwrite
		if(!this.canOverwrite(action)) {
			//invoke event
			action.overwriteFailed();
			return false;
		}
		//install new action's animations
		for(BodyParts parts : actionDotPaints.keySet()) {
			//remove overwritten action
			if(parts.hasAction())
				stopAction(parts.action());
			//set new action
			parts.setAction(action, actionDotPaints.get(parts));
		}
		//record this action
		doingActions.add(action);
		//invoke event
		action.activated();
		return true;
	}
	public boolean stopAction(UnitAction action) {
		if(!isDoingAction(action))
			return false;
		for(BodyParts parts : bodyParts) {
			if(parts.action() == action)
				parts.removeAction();
		}
		doingActions.remove(action);
		action.stopped();
		if(waitingAction != null) {
			action(waitingAction);
			waitingAction = null;
		}
		return true;
	}
	public void stopAction(Class<? extends UnitAction> actionClass) {
		for(UnitAction action : doingActions) {
			if(actionClass.isAssignableFrom(action.getClass()))
				stopAction(action);
		}
	}
	public UnitAction getAction(Class<? extends UnitAction> actionClass) {
		final Iterator<UnitAction> iterator = doingActions.iterator();
		while(iterator.hasNext()) {
			final UnitAction action = iterator.next();
			if(action.getClass() == actionClass)
				return action;
		}
		return null;
	}
	public void setActionAppointment(UnitAction action) {
		waitingAction = action;
	}
	public void reset() {
		equipments.clear();
		doingActions.clear();
		waitingAction = null;
	}
	//event
	public void equipped(ItemData equipment) {}
	public void dequipped(ItemData equipment) {}
	//information
	public Unit owner() {
		return owner;
	}
	public LinkedList<BodyParts> bodyParts() {
		return bodyParts;
	}
	public LinkedList<ItemData> equipments() {
		return equipments;
	}
	public Set<Class<? extends UnitAction>> doableActions() {
		return doableActionsMap.keySet();
	}
	public LinkedList<UnitAction> doingActions() {
		return doingActions;
	}
	public boolean hasEquipment(ItemData equipment) {
		return equipments.contains(equipment);
	}
	public boolean isDoableAction(Class<? extends UnitAction> action) {
		return findActionInfo(action) != null;
	}
	public boolean isDoingAction(UnitAction action) {
		return doingActions.contains(action);
	}
	public boolean isDoingAction(Class<?> action) { //TODO: Check if usable
		for(UnitAction ver : doingActions) {
			if(ver.getClass().isAssignableFrom(action))
				return true;
		}
		return false;
	}
	/*public UnitAction getDoingAction(Class<? extends UnitAction> actionClass) {
		for(UnitAction action : doingActions) {
			if(actionClass.isAssignableFrom(action.getClass())) {
				System.out.println("find: " + action.getClass().getName());
				return action;
			}
		}
		return null;
	}*/
	public HashMap<BodyParts, DotPaint> findActionInfo(Class<? extends UnitAction> action) {
		for(Class<? extends UnitAction> ver : doableActionsMap.keySet()) {
			if(ver.isAssignableFrom(action))
				return doableActionsMap.get(ver);
		}
		return null;
	}
	public boolean canOverwrite(UnitAction action) {
		final HashMap<BodyParts, DotPaint> actionDotPaints = this.findActionInfo(action.getClass());
		if(actionDotPaints == null)
			return false;
		//check overwrite
		for(BodyParts parts : actionDotPaints.keySet()) {
			if(!parts.canOverWriteAction(action)) //overwrite fail
				return false;
		}
		return true;
	}
	public BodyParts equippableBodyParts(ItemData equipment, boolean overwrite) {
		if(overwrite) {
			for(BodyParts parts : bodyParts) {
				if(equipment.canEquipTo(parts))
					return parts;
			}
		}else {
			for(BodyParts parts : bodyParts) {
				if(!parts.hasEquipment() && equipment.canEquipTo(parts))
					return parts;
			}
		}
		return null;
	}
	public boolean canEquip(ItemData equipment, boolean overwrite) {
		return equippableBodyParts(equipment, overwrite) != null;
	}
	public boolean containsEquipment(ItemData equipment) {
		return equipments().contains(equipment);
	}
	@Override
	public Point point() {
		return owner.point();
	}
	@Override
	public Angle angle() {
		return owner.angle();
	}
}
