package loading;

public interface ObjectSavable {
	public abstract ObjectSaveTree save();
	
	public default void printVersionError(ObjectSaveTree line) {
		System.out.println(this.getClass().getName() + " SaveData version error: " + line.VERSION + " is illegal.");
	}
}