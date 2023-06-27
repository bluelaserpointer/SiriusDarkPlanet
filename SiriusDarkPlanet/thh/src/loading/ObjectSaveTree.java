package loading;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

public class ObjectSaveTree implements Serializable {
	private static final long serialVersionUID = -1266720596050293026L;
	public final int VERSION;
	private final LinkedList<Object> DATA_LIST;
	public final ObjectSaveTree[] CHILDS;

	public ObjectSaveTree(int version, Object... datas) {
		VERSION = version;
		DATA_LIST = new LinkedList<Object>(Arrays.asList(datas));
		CHILDS = null;
	}
	/*
	public static final ObjectSaveTree[] convertToSaveTree(ObjectSavable...rawObjs) {
		final ObjectSaveTree[] trees = new ObjectSaveTree[rawObjs.length];
		for(int i = 0;i < rawObjs.length;++i)
			trees[i] = rawObjs[i].save();
		return trees;
	}
	private static final ObjectSaveTree[] convertToSaveTree(ObjectSaveTree supersSaveTree, ObjectSavable...rawObjs) {
		final ObjectSaveTree[] trees = new ObjectSaveTree[rawObjs.length + 1];
		trees[0] = supersSaveTree;
		for(int i = 0;i < rawObjs.length;++i)
			trees[i + 1] = rawObjs[i].save();
		return trees;
	}
	
	public static final ObjectSaveTree onlyData(int version, Object...datas) { //only original data
		return new ObjectSaveTree(version, datas);
	}
	public static final ObjectSaveTree onlyChilds(int version, ObjectSaveTree supersSaveTree, ObjectSavable...childs) { //only child data
		return new ObjectSaveTree(version, null, convertToSaveTree(supersSaveTree, childs));
	}
	public static final ObjectSaveTree onlyChilds(int version, ObjectSavable...childs) { //only child data
		return new ObjectSaveTree(version, null, convertToSaveTree(childs));
	}
	public static final ObjectSaveTree dataAndChilds(int version, Object[] datas, ObjectSaveTree supersSaveTree, ObjectSavable...childs) { //contain both of them
		return new ObjectSaveTree(version, datas, convertToSaveTree(supersSaveTree, childs));
	}
	public static final ObjectSaveTree dataAndChilds(int version, Object[] datas, ObjectSavable...childs) { //contain both of them
		return new ObjectSaveTree(version, datas, convertToSaveTree(childs));
	}
	*/
	//information
	public Object poll() {
		return DATA_LIST.pollFirst();
	}
	public ObjectSaveTree pollSaveTreeForSuper() {
		return (ObjectSaveTree)poll();
	}
	public ObjectSavable pollSavable() {
		return (ObjectSavable)poll();
	}
	public ObjectSaveTree pollToSaveTree() {
		return pollSavable().save();
	}
	public ObjectSavable[] pollSavables() {
		return (ObjectSavable[])poll();
	}
	public int pollInt() {
		return (int)poll();
	}
	public double pollDouble() {
		return (double)poll();
	}
	public boolean pollBoolean() {
		return (boolean)poll();
	}
	public String pollString() {
		return (String)poll();
	}
}