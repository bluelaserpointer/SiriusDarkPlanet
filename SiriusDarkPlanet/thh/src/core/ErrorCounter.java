package core;

import java.util.ArrayList;

public class ErrorCounter {
	private static int count;
	private static ArrayList<String> msg = new ArrayList<String>();
	private static ArrayList<Integer> frame = new ArrayList<Integer>();
	public static final void clear() {
		count = 0;
		msg.clear();
		frame.clear();
	}
	public static final void put() {
		put("<no message>");
	}
	public static final void put(String str) {
		count++;
		msg.add(str);
		frame.add(GHQ.nowFrame());
	}
	public static final void putWithPrint(String str) {
		System.out.println(str);
		put(str);
	}
	public static final int getCount() {
		return count;
	}
	public static final boolean hadError(int oldCount) {
		return oldCount < count;
	}
	public static final String getLatestMsg() {
		return msg.get(count - 1);
	}
	public static final String getMsg(int index) {
		return msg.get(index);
	}
	public static final int getLatestFrame() {
		return frame.get(count - 1);
	}
	public static final int getFrame(int index) {
		return frame.get(index);
	}
	public static final void rewrite(int index,String str) {
		msg.set(index, str);
	}
	public static final void rewrite(String str) {
		msg.set(count - 1, str);
	}
	public static final String[] getMsgArray() {
		return msg.toArray(new String[0]);
	}
	public static final int[] getFrameArray() {
		final Integer[] array =  frame.toArray(new Integer[0]);
		final int[] array2 = new int[array.length];
		for(int i = 0;i < array.length;i++)
			array2[i] = array[i];
		return array2;
	}
}
