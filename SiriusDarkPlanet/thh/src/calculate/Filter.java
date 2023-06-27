package calculate;

public interface Filter<T> {
	public abstract boolean judge(T object);
}
