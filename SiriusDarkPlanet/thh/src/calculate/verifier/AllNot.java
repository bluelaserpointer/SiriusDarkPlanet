package calculate.verifier;

public class AllNot<T> extends Verifier<T> {
	@Override
	public boolean verify(T t) {
		return false;
	}
}
