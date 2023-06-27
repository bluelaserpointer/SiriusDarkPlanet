package calculate.verifier;

public class SingleVerifier<T> extends Verifier<T> {
	private T acceptElement;
	public SingleVerifier() {}
	public SingleVerifier(T t) {
		acceptElement = t;
	}
	@Override
	public boolean verify(T t) {
		return acceptElement == t;
	}
	public T acceptElement() {
		return acceptElement;
	}
}
