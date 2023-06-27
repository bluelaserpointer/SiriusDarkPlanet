package core;

import java.util.LinkedList;

public class MyLinkedList<T> extends LinkedList<T>{
	private static final long serialVersionUID = 4915276430420245522L;

	public boolean addIfNotNull(T element) {
		return element == null ? false : super.add(element);
	}
	public boolean addIfNotNullNotDup(T element) {
		return (element == null || contains(element)) ? false : super.add(element);
	}
}
