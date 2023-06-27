package preset.item;

import java.util.Collection;

import calculate.Filter;
import calculate.verifier.Verifier;
import calculate.verifier.WhiteList;
import core.GHQ;
import core.GHQObject;
import paint.dot.DotPaint;
import physics.Point;
import physics.hitShape.ImageRectShape;
import preset.unit.BodyParts;
import preset.unit.BodyPartsType;
import preset.unit.Unit;
import storage.StorageWithSpace;

/**
 * A primal class for managing item data.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class ItemData extends GHQObject implements Usable {
	public static final ItemData BLANK_ITEM = new ItemData(DotPaint.BLANK_SCRIPT);
	
	//base info
	protected Unit owner;
	protected int amount;
	protected int stackCap = GHQ.MAX;
	protected DotPaint paintScript;
	private boolean isEquipped;
	//equipment info
	private Verifier<BodyPartsType> equippableBodyPartsTypeVerifier;

	@Override
	public String name() {
		return "<ItemData>" + getClass().getName();
	}
	//init
	public ItemData(DotPaint dotScript) {
		this.paintScript = dotScript == null ? DotPaint.BLANK_SCRIPT : dotScript;
		physics().setHitShape(new ImageRectShape(this, this));
	}
	public ItemData setDotPaint(DotPaint dotPaint) {
		paintScript = dotPaint;
		return this;
	}
	public ItemData setEquippableBodyPartsType(BodyPartsType...whiteList) {
		equippableBodyPartsTypeVerifier = new WhiteList<BodyPartsType>(whiteList);
		return this;
	}
	//main role
	@Override
	public void idle() {
		if(!hasOwner()) { //dropped in stage
			paint();
			point().idle();
		}
	}
	@Override
	public void paint() {
		super.paint();
		paintScript.dotPaint(point());
	}
	//control
	public void setStackCap(int amount) {
		stackCap = amount;
	}
	public void setNonStackable() {
		this.setStackCap(1);
	}
	public void setOwner(Unit unit) {
		owner = unit;
	}
	public void removeOwner() {
		if(owner != null) {
			owner.removedItem(this);
			setOwner(null);
		}
	}
	@Override
	public void use() {}
	//information
	public boolean stackable(ItemData item) {
		return getClass().equals(item.getClass());
	}
	public boolean isNonStackable() {
		return stackCap() == 1;
	}
	public final boolean isEmpty() {
		return amount == 0;
	}
	public boolean keepEvenEmpty() {
		return false;
	}
	public final int getAmount() {
		return amount;
	}
	public final int getLeftSpace() {
		return stackCap != GHQ.MAX ? stackCap - amount : GHQ.MAX;
	}
	@Override
	public final DotPaint getDotPaint() {
		return paintScript;
	}
	//control
	public void clear() {
		this.amount = 0;
	}
	/**
	 * Drop this item form a unit.
	 * Clear its owner information and appear to the current stage.
	 * @param x
	 * @param y
	 * @return dropped item
	 */
	public ItemData drop(int x, int y) {
		removeOwner();
		cancelDeleteFromStage();
		point().setXY(x, y);
		return GHQ.stage().addItem(this);
	}
	public ItemData drop(Point point) {
		return drop(point.intX(), point.intY());
	}
	public ItemData pickup(Unit newOwner) {
		claimDeleteFromStage();
		setOwner(newOwner);
		return this;
	}
	public Object substantialize(int x, int y) {
		return null;
	}
	public Object substantialize(Point point) {
		return substantialize(point.intX(), point.intY());
	}
	public int add(int amount) {
		final int LFET_SPACE = getLeftSpace();
		if(LFET_SPACE > amount) {
			this.amount += amount;
			return amount;
		}else {
			this.amount = stackCap;
			return LFET_SPACE;
		}
	}
	public int consume(int amount) {
		if(this.amount > amount) {
			this.amount -= amount;
			return amount;
		}else {
			final int CONSUMED = this.amount;
			this.amount = 0;
			return CONSUMED;
		}
	}
	/**
	 * 
	 * @param item
	 * @return
	 */
	public int stack(ItemData item) {
		final int ADDED = add(item.amount);
		item.consume(ADDED);
		return ADDED;
	}
	/**
	 * 
	 * @param item
	 * @return
	 */
	public int setOff(ItemData item) {
		final int CONSUMED = consume(item.amount);
		item.consume(CONSUMED);
		return CONSUMED;
	}
	//tell
	public void equipped() {
		isEquipped = true;
	}
	public void dequipped() {
		isEquipped = false;
	}
	//information
	public Unit owner() {
		return owner;
	}
	public boolean hasOwner() {
		return owner != null;
	}
	public int stackCap() {
		return stackCap;
	}
	public double stackRate() {
		return amount/stackCap;
	}
	public boolean isFullStacked() {
		return amount >= stackCap;
	}
	public boolean isOverStacked() {
		return amount > stackCap;
	}
	public static boolean isValid(ItemData item) {
		return item != null && item != ItemData.BLANK_ITEM;
	}
	public boolean canEquipTo(BodyParts bodyParts) {
		for(BodyPartsType type : bodyParts.types()) {
			if(equippableBodyPartsTypeVerifier.verify(type))
				return true;
		}
		return false;
	}
	public Verifier<BodyPartsType> equippableBodyPartsTypeVerifier() {
		return equippableBodyPartsTypeVerifier;
	}
	public boolean isEquipped() {
		return isEquipped;
	}
	///////////////////////////////
	//public tool - add and remove from inventory with stacking
	///////////////////////////////
	/**
	 * Add items to StorageWithSpace with automatic stack.
	 * @param itemStorage
	 * @param targetItem
	 * @return true - successfully added / false - overflowed
	 */
	public static boolean addInInventory(StorageWithSpace<ItemData> itemStorage, ItemData targetItem) {
		for(int i = itemStorage.nextNonspaceIndex();i != -1;i = itemStorage.nextNonspaceIndex(i)) {
			final ItemData ITEM = itemStorage.get(i);
			if(ITEM.stackable(targetItem)) {
				ITEM.stack(targetItem);
				if(targetItem.isEmpty()) {
					if(targetItem.keepEvenEmpty()){
						return itemStorage.add(targetItem);
					}else
						return true;
				}
			}
		}
		//cannot found any more stackable item.
		return itemStorage.add(targetItem);
	}
	public static boolean addInInventory(Collection<ItemData> itemStorage, ItemData targetItem) {
		for(ItemData item : itemStorage) {
			if(item.stackable(targetItem)) {
				item.stack(targetItem);
				if(targetItem.isEmpty()) {
					if(targetItem.keepEvenEmpty()){
						return itemStorage.add(targetItem);
					}else
						return true;
				}
			}
		}
		//cannot found any more stackable item.
		return itemStorage.add(targetItem);
	}
	/**
	 * Remove items from StorageWithSpace.
	 * @param itemStorage
	 * @param targetItem
	 * @return removed amount
	 */
	public static int removeInInventory(StorageWithSpace<? extends ItemData> itemStorage, ItemData targetItem) {
		int removed = 0;
		for(int i = itemStorage.nextNonspaceIndex();i != -1;i = itemStorage.nextNonspaceIndex(i)) {
			final ItemData item = itemStorage.get(i);
			if(item.stackable(targetItem)) {
				removed += item.setOff(targetItem); 
				if(item.isEmpty() && !item.keepEvenEmpty())
					itemStorage.remove(targetItem);
				if(targetItem.isEmpty())
					return removed;
			}
		}
		//not found enough amount of the item.
		return removed;
	}
	public static int removeInInventory(Collection<? extends ItemData> itemStorage, ItemData targetItem) {
		int removed = 0;
		for(ItemData item : itemStorage) {
			if(item.stackable(targetItem)) {
				removed += item.setOff(targetItem);
				if(item.isEmpty() && !item.keepEvenEmpty())
					itemStorage.remove(targetItem);
				if(targetItem.isEmpty())
					return removed;
			}
		}
		//not found enough amount of the item.
		return removed;
	}
	/**
	 * Remove some items from StorageWithSpace with Filter.
	 * @param itemStorage
	 * @param filter
	 * @param amount
	 * @return removed amount
	 */
	public static <T extends ItemData>int removeInInventory(StorageWithSpace<T> itemStorage, Filter<T> filter, int amount) {
		int removed = 0;
		for(int i = itemStorage.nextNonspaceIndex();i != -1;i = itemStorage.nextNonspaceIndex(i)) {
			final T item = itemStorage.get(i);
			if(filter.judge(item)) {
				removed += item.consume(amount);
				if(item.isEmpty())
					return removed;
			}
		}
		for(int i = itemStorage.nextNonspaceIndex();i != -1;i = itemStorage.nextNonspaceIndex(i)) {
			final ItemData ITEM = itemStorage.get(i);
			if(ITEM.isEmpty() && !ITEM.keepEvenEmpty())
				itemStorage.remove(ITEM);
		}
		//not found enough amount of the item.
		return removed;
	}
	///////////////////////////////
	//public tool - count item amount in a inventory
	///////////////////////////////
	public static int countItem(StorageWithSpace<? extends ItemData> itemStorage, ItemData targetItem) {
		int total = 0;
		for(int i = itemStorage.nextNonspaceIndex();i != -1;i = itemStorage.nextNonspaceIndex(i)) {
			if(targetItem.stackable(itemStorage.get(i)))
				total += itemStorage.get(i).getAmount();
		}
		return total;
	}
	public static int countItemByName(StorageWithSpace<? extends ItemData> itemStorage, String targetItemName) {
		int total = 0;
		for(int i = itemStorage.nextNonspaceIndex();i != -1;i = itemStorage.nextNonspaceIndex(i)) {
			if(targetItemName.equals(itemStorage.get(i).name()))
				total += itemStorage.get(i).getAmount();
		}
		return total;
	}
	public static <T extends ItemData>int countItem(StorageWithSpace<T> itemStorage, Filter<T> filter) {
		int total = 0;
		for(int i = itemStorage.nextNonspaceIndex();i != -1;i = itemStorage.nextNonspaceIndex(i)) {
			if(filter.judge(itemStorage.get(i)))
				total += itemStorage.get(i).getAmount();
		}
		return total;
	}
	
	@Override
	public Point point() {
		return hasOwner() ? owner.point() : super.point();
	}
}
