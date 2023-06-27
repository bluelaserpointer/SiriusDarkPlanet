package storage;

import java.util.ArrayList;

import core.GHQ;

/**
 * An original class for managing various array.
 * @author bluelaserpointer
 * @since alpha1.0
 * @param <T> the type of elements in this list
 */
public class Storage<T> extends ArrayList<T> {
	private static final long serialVersionUID = -5269899299275879135L;
	protected int amount,max_amount;
	
	public Storage() {
		max_amount = GHQ.MAX;
	}
	public Storage(int max_amount) {
		this.max_amount = max_amount;
	}
	//control
	protected void initFill(T element) {
		super.clear();
		for(int i = 0;i < max_amount;i++) {
			super.add(element);
		}
	}
	public boolean add(T element) {
		if(amount == max_amount)
			return false;
		amount++;
		super.add(element);
		return true;
	}
	public final int addMany(T element, int amount) {
		final int prevAmount = amount;
		while(amount-- > 0 && add(element));
		return amount - prevAmount;
	}
	public final int addMany(T element) {
		final int prevAmount = amount;
		while(add(element));
		return amount - prevAmount;
	}
	public void fill(T element) {
		amount = max_amount;
		for(int i = 0;i < max_amount;i++)
			super.set(i,element);
	}
	public T remove(int index) {
		amount--;
		return super.remove(index);
	}
	@Override
	public boolean remove(Object element) {
		if(super.remove(element)) {
			amount--;
			return true;
		}
		return false;
	}
	public void clear() {
		amount = 0;
		super.clear();
	}
	
	//information
	public final int traverseFirst() {
		return traverseNext(-1);
	}
	public int traverseNext(int currentIndex) {
		return currentIndex + 1 < size() ? currentIndex + 1 : -1;
	}
	public final double fillRate() {
		return amount/max_amount;
	}
	public final boolean hasSpace() {
		return amount < max_amount;
	}
	public final boolean isEmpty() {
		return amount == 0;
	}
	public final int getAmount() {
		return amount;
	}
	public final int getMaxSize() {
		return max_amount;
	}
}
