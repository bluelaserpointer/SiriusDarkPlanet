package calculate.verifier;

public class All<T> extends Verifier<T> {
	@Override
	public boolean verify(T t) {
		return true;
	}
}
