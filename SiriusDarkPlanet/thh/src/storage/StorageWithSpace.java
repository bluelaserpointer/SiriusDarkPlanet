package storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * A subclass of ArrayList that able to insert "space".
 * The function add(T e) will firstly choose those empty space to insert the element.
 * @author bluelaserpointer
 * @since alpha1.0
 * @param <T> the type of elements in this list
 */
public class StorageWithSpace<T> extends ArrayList<T> {
	private static final long serialVersionUID = -5269899299275879135L;
	protected int amount;
	protected T spaceElement;
	
	public StorageWithSpace() {
	}
	public StorageWithSpace(int initialSize) {
		this.expand(initialSize);
	}
	public StorageWithSpace(int initialSize, T spaceElement) {
		this.spaceElement = spaceElement;
		this.expand(initialSize);
	}
	
	/////////
	//init
	/////////
	/**
	 * Make size bigger.
	 * Newly added space will be filled with space element.
	 * @param size
	 * @return
	 */
	public StorageWithSpace<T> expand(int size) {
		if(size <= 0)
			return this;
		for(int i = 0; i < size; ++i)
			super.add(spaceElement);
		return this;
	}
	/**
	 * Define an element means this index is empty.
	 * It can recount number of valid elements if required.
	 * @param emptyToken
	 * @param needRecountAmount
	 * @return
	 */
	public StorageWithSpace<T> setEmptyToken(T emptyToken, boolean needRecountAmount) {
		this.spaceElement = emptyToken;
		if(needRecountAmount) {
			amount = 0;
			for(T ver : this) {
				if(ver == emptyToken)
					++amount;
			}
		}
		return this;
	}
	
	/////////
	//control
	/////////
	/**
	 * Make size smaller.
	 * Deleted elements will be returned as a LinkedList.
	 * @param size
	 * @return list of deleted elements (not including empty space)
	 */
	public LinkedList<T> shrink(int size) {
		final LinkedList<T> deletedList = new LinkedList<T>();
		if(size <= 0)
			return deletedList;
		for(int i = 0; i < size && !super.isEmpty(); ++i) {
			final T deleted = super.remove(super.size() - 1);
			if(deleted != spaceElement) {
				deletedList.add(deleted);
				--amount;
			}
		}
		return deletedList;
	}
	public void fillSpace(T element) {
		if(element != spaceElement) {
			amount = super.size();
			while(add(element));
		}
	}
	public void fillAll(T element) {
		amount = (element == spaceElement ? 0 : super.size());
		for(int i = 0; i < super.size(); ++i)
			super.set(i, element);
	}
	@Override
	public boolean add(T element) {
		if(isFull()) {
			System.out.println("! -- StorageWithSpace cannot add more element. current size: " + size());
			return false;
		}
		++amount;
		super.set(this.nextSpaceIndex(), element);
		return true;
	}
	public boolean addWithEventCall(T element, Object elementSrc) {
		final boolean b = add(element);
		if(b)
			added(element, elementSrc);
		return b;
	}
	@Override
	public T set(int index, T element) {
		final T prevElement = super.set(index, element);
		if(prevElement == spaceElement) {
			if(element != spaceElement)
				amount++;
		} else {
			if(element == spaceElement)
				amount--;
		}
		return prevElement;
	}
	public T setWithEventCall(int index, T element, Object addElementSrc, Object removedElementDst) {
		final T prevElement = set(index, element);
		added(element, addElementSrc);
		if(prevElement != null)
			removed(prevElement, removedElementDst);
		return prevElement;
	}
	/**
	 * Add same element multiple times
	 * @param element
	 * @param amount
	 * @return added amount
	 */
	public int addMany(T element, int amount) {
		final int prevAmount = amount;
		while(amount > 0 && add(element))
			--amount;
		return prevAmount - amount;
	}
	@Override
	public boolean addAll(Collection<? extends T> collection) {
		if(collection != null) {
			for(T ver : collection) {
				if(!add(ver))
					return false;
			}
		}
		return true;
	}
	public boolean addAll(T[] ts) {
		for(T ver : ts) {
			if(!add(ver))
				return false;
		}
		return true;
	}
	@Override
	public T remove(int index) {
		if(!isSpaceIndex(index)) {
			--amount;
			final T removedElement = super.set(index, spaceElement);
			return removedElement;
		}else
			return spaceElement;
	}
	@Override
	public boolean remove(Object element) {
		if(element == spaceElement)
			return false;
		final int index = super.indexOf(element);
		if(index == -1)
			return false;
		--amount;
		super.set(index, spaceElement);
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean removeWithEventCall(Object element, Object elementDst) {
		final boolean b = remove(element);
		if(b)
			removed((T)element, elementDst);
		return b;
	}
	public T removeWithEventCall(int id, Object elementDst) {
		final T removedElement = remove(id);
		if(removedElement != null)
			removed(removedElement, elementDst);
		return removedElement;
	}
	public void clear() {
		fillAll(spaceElement);
	}

	/////////
	//event
	/////////
	protected void added(T element, Object elementSrc) {}
	protected void removed(T element, Object elementDst) {}

	/////////
	//information
	/////////
	public T spaceElement() {
		return spaceElement;
	}
	public boolean isSpaceElement(Object element) {
		return spaceElement == element;
	}
	public int nextSpaceIndex() {
		return nextSpaceIndex(-1);
	}
	public int nextSpaceIndex(int prevIndex) {
		final int newIndex = prevIndex + 1;
		if(newIndex == size())
			return -1;
		return isSpaceIndex(newIndex) ? newIndex : nextSpaceIndex(newIndex);
	}
	public int nextNonspaceIndex() {
		return nextNonspaceIndex(-1);
	}
	public int nextNonspaceIndex(int prevIndex) {
		final int newIndex = prevIndex + 1;
		if(newIndex == size())
			return -1;
		return !isSpaceIndex(newIndex) ? newIndex : nextNonspaceIndex(newIndex);
	}
	public boolean isValidIndex(int index) {
		return 0 <= index && index < super.size();
	}
	public final double fillRate() {
		return amount/super.size();
	}
	public final boolean hasSpace() {
		return amount < super.size();
	}
	/**
	 * Note that this method also returns true when all elements it has are space element. 
	 * @return
	 */
	public boolean isEmpty() {
		return amount == 0;
	}
	public boolean isFull() {
		return amount == super.size();
	}
	public int amount() {
		return amount;
	}
	public boolean isSpaceIndex(int index) {
		return isValidIndex(index) && super.get(index) == spaceElement;
	}
}
